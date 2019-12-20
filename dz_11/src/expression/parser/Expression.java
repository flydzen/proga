package expression.parser;

import expression.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Expression {
    private static class Pair{
        Opers op;
        int prior;
        Pair(Opers x, int y){
            op = x;
            prior = y;
        }
    }
    private static class Line{
        Opers op;
        Element el;
        int id;
        Line(Element x, int y){
            el = x;
            id = y;
        }
        Line(Opers x, int y){
            op = x;
            id = y;
        }
    }
    private static boolean last = true;
    private static HashMap<Character, Pair> mapBinOps = new HashMap<>();
    private static HashSet<Opers> isUno = new HashSet<>();
    private static Stack<Line> values;
    private static Stack<Line> opers;
    private static int time;

    public static Element parse(final String source) {
        return parse(new StringSource(source));
    }

    public static Element parse(ExpressionSource source) {
        mapBinOps.put('<', new Pair(Opers.LEFT, 0));
        mapBinOps.put('>', new Pair(Opers.RIGHT, 0));
        mapBinOps.put('+', new Pair(Opers.ADD, 1));
        mapBinOps.put('-', new Pair(Opers.SUB, 1));
        mapBinOps.put('*', new Pair(Opers.MUL, 2));
        mapBinOps.put('/', new Pair(Opers.DIV, 2));
        mapBinOps.put('(', new Pair(Opers.OPEN, -100));
        mapBinOps.put('u', new Pair(Opers.UNO, 3));
        mapBinOps.put('a', new Pair(Opers.ABS, 3));
        mapBinOps.put('s', new Pair(Opers.SQRT, 3));

        isUno.add(Opers.UNO);
        isUno.add(Opers.ABS);
        isUno.add(Opers.SQRT);
        return new ExpressionParser(source).parseExpression();
    }

    private static class ExpressionParser extends BaseParser {
        public ExpressionParser(final ExpressionSource source) {
            super(source);
            nextChar();
        }

        public Element parseExpression() {
            final Element result = parseAll();
            if (test('\0')) {
                return result;
            }
            throw error("End of Expression expected");
        }

        private Element parseAll() {
            values = new Stack<>();
            opers = new Stack<>();
            time = 0;
            last = true;
            opers.add(new Line(Opers.OPEN, time++));
            while (ch != '\0') {

                parseElement();
            }
            unite();
            return values.pop().el;
        }

        private void parseElement() {
            skipWhitespace();
            if (test('(')) {
                opers.add(new Line(Opers.OPEN, time++));
                last = true;
            } else if (test(')')) {
                unite();
                last = false;
            } else if (mapBinOps.containsKey(ch)){
                if (last && ch=='-') ch = 'u';
                Pair cur = mapBinOps.get(ch);
                if (test('a')){
                    expect("bs");
                }else if (test('s')){
                    expect("quare");
                }else if (test('<')){
                    expect('<');
                }else if (test('>')) {
                    expect('>');
                }else if (last && test('u') ){
                        if (between('0', '9')){
                            values.add(new Line(parseConst(new StringBuilder().append('-')), time++));
                            last = false;
                            return;
                        }
                }else{
                    nextChar();
                }
                if (!(isUno.contains(opers.peek().op) && isUno.contains(cur.op))) {
                    while (true) {
                        int old;
                        if (opers.peek().op == Opers.UNO) {
                            old = mapBinOps.get('u').prior;
                        } else {
                            old = mapBinOps.get(opers.peek().op.toString().charAt(0)).prior;
                        }
                        if (cur.prior <= old && (!isUno.contains(opers.peek()) || opers.peek().id > values.peek().id))
                            compare();
                        else
                            break;
                    }
                }
                opers.add(new Line(cur.op, time++));
                last = true;
            } else if (between('x', 'z')) {
                values.add(new Line(new Variable(Character.toString(ch)), time++));
                nextChar();
                last = false;
            } else {
                values.add(new Line(parseConst(), time++));
                last = false;
            }
            skipWhitespace();
        }

        private void unite() {
            while (opers.peek().op != Opers.OPEN) {
                compare();
            }
            opers.pop();
        }

        private void compare(){
            Opers action = opers.pop().op;
            if (isUno.contains(action)){
                Element e = values.pop().el;
                if (action == Opers.UNO){
                    values.add(new Line(new Negative(e),time++));
                }else if (action == Opers.SQRT){
                    values.add(new Line(new Square(e),time++));
                }else if (action == Opers.ABS){
                    values.add(new Line(new Abs(e),time++));
                }
            }else {
                Element e2 = values.pop().el;
                Element e1 = values.pop().el;
                if (action == Opers.ADD) {
                    values.add(new Line(new Add(e1, e2), time++));
                } else if (action == Opers.SUB) {
                    values.add(new Line(new Subtract(e1, e2), time++));
                } else if (action == Opers.DIV) {
                    values.add(new Line(new Divide(e1, e2), time++));
                } else if (action == Opers.MUL) {
                    values.add(new Line(new Multiply(e1, e2), time++));
                } else if (action == Opers.LEFT) {
                    values.add(new Line(new Left(e1, e2), time++));
                } else if (action == Opers.RIGHT) {
                    values.add(new Line(new Right(e1, e2), time++));
                }
            }
        }

        private Const parseConst() {
            return parseConst(new StringBuilder());
        }
        private Const parseConst(StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
            return new Const(Integer.parseInt(sb.toString()));
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
                // skip
            }
        }
    }
}
