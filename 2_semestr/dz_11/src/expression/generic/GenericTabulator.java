package expression.generic;

import expression.Mode;
import expression.TripleExpression;
import expression.exceptions.ParsingException;
import expression.parser.Expression;
import expression.parser.Parser;

public class GenericTabulator implements Tabulator {
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws ParsingException {
        Parser parser = new Expression.ExpressionParser();
        TripleExpression exp;
        Mode _mode;
        if (mode.equals("i")){
            _mode = Mode.I;
        } else if (mode.equals("d")){
            _mode = Mode.D;
        } else {
            _mode = Mode.BI;
        }
        //exp = parser.parse(expression, _mode);
        Object[][][] result = new Object[x2-x1][y2-y1][z2-z1];
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    //result[x-x1][y-y1][z-z1] = exp.evaluate(x, y, z);  // throws CalculationException
                }
            }
        }
        return result;
    }
}