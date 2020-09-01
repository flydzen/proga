"use strict";

// :NOTE: common mistakes: 1, 2, 7
/*
Andrey Plotnikov, [07.05.20 16:05]
Общие замечания по 8 домашке:

1. Объект переменной и константы должен объявляться в таком же виде, как и остальные операции const Const = someFacoryMethod (...)
2. Не должно быть дублирования кода в методах toString, prefix, postifx. Это решается введением нового метода с параметрами, задающими порядок, префикс и постфикс ко всему результату
3. Всё, что объявлено на верхнем уровне должно быть const.
4. Размер парсера не должен превышать 60 непустых строк
5. Объект ошибок должен создаваться через общий factory метод, а не быть написанным на коленке 10 раз по SomeError.prototype... = ...
6. parsePrefix и parsePostfix должны состоять не более чем из 1 значимой строки, всё остальное должно быть в обобщённой функции

Все остальные ошибки будут выноситься в :NOTE:

Andrey Plotnikov, [07.05.20 16:07]
7. Не должно быть дублирования имён переменных и символов операторов. Строки типа "x", "+", "negate" должны упоминаться 1 раз на весь файл
 */
// 8
const makeError = (message, name) => {
    function Err(...args){
        this.message = message(args);
    }
    Err.prototype = Object.create(Error.prototype);
    Err.prototype.constructor = Err;
    Err.prototype.name = name;
    return Err;
};

const ParsingError = makeError((args)=> "At index " + args[0] + ". "+ args[1], "ParsingError");
const ExpressionError = makeError((args)=>args[0], "ExpressionError");
const SyntaxError = makeError((args)=>{return "Expected " + args[0] + " got " + args[1]}, "SyntaxError");

function makeString(f, data, scobe, poschr, chr) { //poschr true - first
    return scobe ? '(' : '' + poschr ? chr + ' ' : '' +
        data.reduce((sum, cur) => sum + cur.f() + " ", '').trim() + !poschr ? chr + ' ' : '' + scobe ? ')' : '';
}

const makeOperation = (cou_, ch_, eval_, diff_, simpl_) => {
    function Oper(...args){
        Operation.call(this, cou_, ch_, ...args);
    }
    Oper.prototype = Object.create(Element.prototype);
    Oper.prototype.constructor = Oper;
    Oper.prototype.evaluate = function(...arg) { return eval_(this.f.map((el)=>{return el.evaluate(...arg)}))};
    Oper.prototype.diff = function (arg) { return diff_(this.f, arg)};
    Oper.prototype.simplify = function (arg) { return simpl_(this.f) };
    Oper.prototype.toString = function () {
        let result = this.f.reduce((sum, cur) => sum + cur.toString() + " ", '');
        return (result + this.ch).trim();
    };

    Oper.prototype.prefix = function () {
        //return "(" + this.ch + " " + this.f.reduce((sum, cur) => sum + cur.prefix() + " ", '').trim() + ")";
        return makeString(Oper.prototype.prefix, this.f, true, true, this.ch);
    };
    Oper.prototype.postfix = function () {
        return "(" + this.f.reduce((sum, cur) => sum + cur.postfix() + " ", '').trim() + " "+ this.ch + ")";
    };
    return Oper;
};

function Element(f) {
    this.f = f;
}

function Operation(cou, ch, ...f) {
    Element.call(this, f);
    if (cou !== -1 && f.length !== cou)
        throw new ExpressionError("Invalid number of arguments, got " + f.length + " need " + cou);
    f.forEach(el => {if (!el instanceof Element) throw new ExpressionError("Invalid argument " + el)});
    this.ch = ch;
}
Operation.prototype = Object.create(Element.prototype);
Operation.prototype.constructor = Operation;

let isConst = (el) => el instanceof Const || el instanceof E || el instanceof PI;
let isValue = (obj, num) => isConst(obj) && obj.f === num;

const constantCreator = (ch_ = '', defValue = 0) => {
    function Elem(value = defValue) {
        if (isNaN(value)) throw new ExpressionError("Invalid number " + value);
        Element.call(this, value);
    }
    Elem.prototype = Object.create(Operation.prototype);
    Elem.prototype.ch = ch_;
    Elem.prototype.constructor = Elem;
    Elem.prototype.evaluate = function (...arg) { return this.f; };
    Elem.prototype.toString = function () { return this.ch? this.ch :"" + this.f; };
    Elem.prototype.prefix = Elem.prototype.toString;
    Elem.prototype.postfix = Elem.prototype.toString;
    Elem.prototype.diff = function (arg) {return new Const(0)};
    Elem.prototype.simplify = function () {return this};
    return Elem;
};

const Const = constantCreator();
const E = constantCreator("E", Math.E);
const PI = constantCreator("PI", Math.PI);


const names = ['x','y','z'];

function Variable(name) {
    Element.call(this);
    this.name = names.indexOf(name);
}
Variable.prototype.evaluate = function (...arg) {
    return arg[this.name];
};
Variable.prototype.diff = function (arg) {
    return new Const(+(arg === names[this.name]));
};
Variable.prototype.toString = function () { return names[this.name]; };
Variable.prototype.prefix = Variable.prototype.toString;
Variable.prototype.postfix = Variable.prototype.toString;
Variable.prototype.simplify = function () { return this; };

// :NOTE: arity of operation can be calculated automatically
const Add = makeOperation(-1, "+", (data) => { return data.reduce((sum, cur) => sum + cur, 0) },
    (data, arg) => { return new Add(...data.map((cur) => cur.diff(arg))) },
    (data)  => {
        if (data.length === 2) {
            let f0 = data[0].simplify();
            let f1 = data[1].simplify();
            if (isValue(f0, 0)) {
                return f1;
            } else if (isValue(f1, 0)) {
                return f0;
            } else if (isConst(f0)  && isConst(f1)) {
                return new Const(f0.f + f1.f);
            } else return new Add(f0, f1);
        }else
            return new Add(...data.map((cur)=>cur.simplify()).filter(cur => !isValue(cur, 0)));
        });


const Subtract = makeOperation(2, "-", (data) => {return data[0] - data[1]},
    (data, arg) => { return new Subtract(data[0].diff(arg), data[1].diff(arg)) },
    (data) => {
        let f0 = data[0].simplify();
        let f1 = data[1].simplify();
        if (isValue(f0, 0)){
            if (isConst(f1)){
                f1.f = -f1.f;
                return f1;
            } else {
                return new Negate(f1);
            }
        } else if (isValue(f1, 0)){
            return f0;
        } else if (isConst(f0) && isConst(f1)){
            return new Const(f0.f - f1.f);
        } else return new Subtract(f0, f1);
    });

const Multiply = makeOperation(2, "*", (data) => { return data.reduce((mul, cur) => mul * cur, 1) },
    (data, arg) =>{
        return new Add(
            new Multiply(
                data[0].diff(arg),
                data[1]
            ), new Multiply(
                data[0],
                data[1].diff(arg)
            ))},
    (data)=>{
        let f0 = data[0].simplify();
        let f1 = data[1].simplify();
        if (isValue(f0, 0) || isValue(f1, 0)){
            return new Const(0);
        } else if (isValue(f0, 1)) {
            return f1;
        }else if (isValue(f1, 1)){
            return f0;
        } else if (isConst(f0) && isConst(f1)){
            return new Const(f0.f * f1.f);
        } else return new Multiply(f0, f1);
    });

// :NOTE: too many code for division
const Divide = makeOperation(2, "/", (data) => {return data[0] / data[1]},
    (data, arg) => {
        return new Divide(
            new Subtract(
                new Multiply(
                    data[0].diff(arg),
                    data[1]
                ), new Multiply(
                    data[0],
                    data[1].diff(arg)
                )),
            new Multiply(data[1], data[1])
        )},
    (data) =>{
        let f0 = data[0].simplify();
        let f1 = data[1].simplify();
        if (isValue(f0, 0)){
            return new Const(0);
        } else if (isValue(f1, 1)) {
            return f0;
        }else if (isValue(f1, 0)){
            return new Const(Infinity);
        } else if (isConst(f0) && isConst(f1)){
            return new Const(f0.f / f1.f);
        } else if (f0.toString() === f1.toString()) {
            return new Const(1);
        } else if (f0 instanceof Multiply) {
            if (f0.f[0].toString() === f1.toString()) {
                return f0.f[1];
            } else if (f0.f[1].toString() === f1.toString()) {
                return f0.f[0];
            }
            if (f1 instanceof Multiply){
                for (let i = 0; i < 2; i++){
                    for (let j = 0; j < 2; j++){
                        if (f0.f[i].toString() === f1.f[j].toString()){
                            return new Divide(f0.f[1-i], f1.f[1-j]);
                        }
                    }
                }
            }
            return new Divide(f0, f1);
        }else if (f1 instanceof Multiply) {
            if (f1.f[0].toString() === f0.toString()) {
                return f1.f[1];
            } else if (f1.f[1].toString() === f0.toString()) {
                return f1.f[0];
            }
            return new Divide(f0, f1);
        } else {
            return new Divide(f0, f1);
        }
    });

const Power = makeOperation(2, "pow", (data) =>  { return Math.pow(data[0], data[1])},
    (data, arg) => {
        let u = data[0];
        let v = data[1];
        return new Add(
            new Multiply(
                new Multiply(
                    v,
                    new Power(
                        u,
                        new Subtract(
                            v,
                            new Const(1)
                        )
                    )
                ),
                u.diff(arg)
            ),
            new Multiply(
                new Multiply(
                    new Power(
                        u,
                        v),
                    new Log(
                        new Const(Math.E),
                        u)
                ),
                v.diff(arg)
            )
        )},
    (data) =>{
        let f0 = data[0].simplify();
        let f1 = data[1].simplify();
        if (isValue(f1, 0)){
            return new Const(1);
        } else if (isValue(f1, 1)){
            return f0;
        } else if (isValue(f0, 0) || isValue(f0, 1)){
            return f0;
        } else {
            return new Power(f0, f1);
        }
    });

const Log = makeOperation(2, "log",
    (data) => {return Math.log(Math.abs(data[1])) / Math.log(Math.abs(data[0])) },
    (data, arg) => {
        if (isConst(data[0])){ // если основание - это число
            return new Divide(data[1].diff(arg), new Multiply(data[1], new Log(new E(), data[0])));
            //  1 / (параметр * ln(основания)
        }
        let f0 = new Log(new E(),data[0]);
        let f1 = new Log(new E(),data[1]);
        // f0 - основание
        // f1 - параметр
        // раскладываю логарифм по новому основанию и делаю по формуле производной деления
        return new Divide(
            new Subtract(
                new Multiply(
                    f1.diff(arg),
                    f0
                ), new Multiply(
                    f1,
                    f0.diff(arg)
                )),
            new Multiply(f0, f0)
        );},
    (data) => {
        let f0 = data[0].simplify();
        let f1 = data[1].simplify();
        if (isValue(f1, 1)){
            return new Const(0);
        } else if(JSON.stringify(f0.f) === JSON.stringify(f1.f)){
            return new Const(1);
        } else if (isValue(f1, 0)){ //
            return new Const(Infinity);
        } else if (isValue(f0, 0)){
            return new Const(0);
        } else if (isValue(f0, 1)){
            return new Const(Infinity);
        } else {
            return new Log(f0, f1);
        }
    });

const Negate = makeOperation(1, "negate",
    (data) => {return -data[0]},
    (data, arg) => { return new Negate(data[0].diff(arg)); },
    (data) => {
        let f0 = data[0].simplify();
        if (f0 instanceof Negate){
            return f0.f[0];
        } else if (isConst(f0)) {
            f0.f = -f0.f;
            return f0;
        }
    });

const Sumexp = makeOperation(-1, "sumexp",
    (data)=>{ return data.reduce((sum, cur) => sum + Math.exp(cur), 0) },
    (data, arg) => {
        let f = data.map((cur) => new Power(new E(), cur).diff(arg));
        return new Add(...f); },
    (data) => {
        let f = data.map((cur) => cur.simplify());
        return new Add(...f);
    });

const expr_ = function (data) {
    return new Divide(new Power(new E(), data[0]), new Sumexp(...data));
};
const Softmax = makeOperation(-1, "softmax",
    (data) => { return Math.pow(Math.E, data[0]) /  data.reduce((sum, cur) => sum + Math.pow(Math.E, cur), 0)},
    (data, arg) => { return expr_(data).diff(arg) },
    (data) => { return expr_(data).simplify() }
    );

const getN = (data, n) => data.splice(data.length - n, n);
const opers = {
    '+' : {fun: Add, cou: 2},
    '-' : {fun: Subtract, cou: 2},
    '*' : {fun: Multiply, cou: 2},
    '/' : {fun: Divide, cou: 2},
    'pow' : {fun: Power, cou: 2},
    'log' : {fun: Log, cou: 2},
    'negate' : {fun: Negate, cou: 1},
    'e' : {fun: E, cou: 0},
    'sumexp' : {fun: Sumexp, cou: -1},
    'softmax' : {fun: Softmax, cou: -2},
};

const parseValue = function (s) {
    if (!isNaN(s))
        return new Const(parseInt(s));
    else if (names.indexOf(s) !== -1)
        return new Variable(s);
    else if (s in opers || s === ")")
        return s;
    else
        throw new SyntaxError("argument" ,s);
};

const parse = function (s) {
    let data = [];
    s.split(" ").filter(j => j !== "").forEach(
        (i)=>{data.push(i in opers? new opers[i].fun(...getN(data, opers[i].cou)) : parseValue(i));});
    return data.pop();
};

const isWhitespace = (c)=> c.length === 1 && new RegExp('\\s').test(c) || c === ' ';

const isScobe = (c) => c === '(' || c === ')';

const parseBase = function (s, mode) { //prefix - true
    s = s.trim();
    let i = 0;

    const read = function () {
        while(i < s.length && isWhitespace(s[i])) i++;
        if (isScobe(s[i]))
            return s[i++];
        let str = [];
        while (i < s.length && !isWhitespace(s[i]) && !isScobe(s[i]))
            str.push(s[i++]);
        if (str.length === 0)
            throw new ParsingError(i, "Unexpected end of line");
        return str.join('');
    };
    const newExpr = function () {
        let word = read();
        if (word !== "(")
            return parseValue(word);
        let args = [];
        let counter = 0;
        while((word = newExpr()) !== ")") {
            if (i === s.length) throw new ParsingError(i, "Missing )");
            if (word in opers) counter++;
            if (counter > 1) throw new ExpressionError("A lot of operations at index " + i);
            args.push(word);
        }
        if (args.length === 0)
            throw new ExpressionError("Expected contents of expression at index " + i);
        let command = mode ? args.shift() : args.pop();
        if (!(command in opers))
            throw new SyntaxError("operation at index " + i, command);
        let oc = opers[command];
        if ((oc.cou < 0 && args.length < -oc.cou -1) || (oc.cou >= 0 && oc.cou !== args.length))
            throw new ExpressionError("Invalid number of arguments. Expected " +
                oc.cou + " got " + args.length + " at index " + i);
        return new oc.fun(...args);
    };
    let parsed = newExpr();
    if (i < s.length)
        throw new ParsingError("Invalid line. Parsing completion not expected");
    return parsed;
};

const parsePrefix = function (s) {
    return parseBase(s, true);
};

const parsePostfix = function(s) {
    return parseBase(s, false);
};

