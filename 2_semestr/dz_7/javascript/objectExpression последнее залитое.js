"use strict";

function ParsingError(message) {
    this.message = message;
}
ParsingError.prototype = Object.create(Error.prototype);
ParsingError.prototype.constructor = ParsingError;
ParsingError.prototype.name = "ParsingError";

function ExpressionError(message) {
    this.message = message;
}
ExpressionError.prototype = Object.create(Error.prototype);
ExpressionError.prototype.constructor = ExpressionError;
ExpressionError.prototype.name = "ExpressionError";

function SyntaxError(expected, got) {
    this.message = "Expected " + expected + " got " + got;
}
SyntaxError.prototype = Object.create(Error.prototype);
SyntaxError.prototype.constructor = SyntaxError;
SyntaxError.prototype.name = "SyntaxError";

function Operation(...f) {
    this.f = f;
    this.ch = '';
    this.toString = function () {
        let result = this.f.reduce((sum, cur) => sum + cur.toString() + " ", '');
        return (result + this.ch).trim();
    };
    this.prefix = function () {
        return "(" + this.ch + " " + this.f.reduce((sum, cur) => sum + cur.prefix() + " ", '').trim() + ")";
    };
    this.postfix = function () {
        return "(" + this.f.reduce((sum, cur) => sum + cur.postfix() + " ", '').trim() + " "+ this.ch + ")";
    }
}

let isValue = (obj, num) => obj instanceof Const && obj.f === num;

function Const(value) {
    this.f = value;
}
Const.prototype.evaluate = function (...arg) { return this.f; };
Const.prototype.toString = function () { return "" + this.f; };
Const.prototype.prefix = function () { return "" + this.f; };
Const.prototype.postfix = function () { return "" + this.f; };
Const.prototype.diff = function (arg) {return new Const(0)};
Const.prototype.simplify = function () {return this};

const names = ['x','y','z'];
function Variable(name) {
    this.name = names.indexOf(name);
}
Variable.prototype.evaluate = function (...arg) {
    return arg[this.name];
};
Variable.prototype.diff = function (arg) {
    return new Const(+(arg === names[this.name]));
};
Variable.prototype.toString = function () { return names[this.name]; };
Variable.prototype.prefix = function () { return names[this.name]; };
Variable.prototype.postfix = function () { return names[this.name]; };
Variable.prototype.simplify = function () { return this; };

function Add(...f) {
    Operation.call(this, ...f);
    this.ch = '+';
}
Add.prototype.evaluate = function (...arg) { return this.f.reduce((sum, cur) => sum + cur.evaluate(...arg), 0) };
Add.prototype.diff = function (arg) { return new Add(...this.f.map((cur) => cur.diff(arg))) };
Add.prototype.simplify = function () {
    if (this.f.length === 2) {
        let f0 = this.f[0].simplify();
        let f1 = this.f[1].simplify();
        if (isValue(f0, 0)) {
            return f1;
        } else if (isValue(f1, 0)) {
            return f0;
        } else if (f0 instanceof Const && f1 instanceof Const) {
            return new Const(f0.f + f1.f);
        } else return new Add(f0, f1);
    }else
        return new Add(...this.f.map((cur)=>cur.simplify()).filter(cur => !isValue(cur, 0)));
};

function Subtract(...f) {
    Operation.call(this, ...f);
    this.ch = '-';
}
Subtract.prototype.evaluate = function (...arg) { return this.f[0].evaluate(...arg) - this.f[1].evaluate(...arg); };
Subtract.prototype.diff = function (arg) { return new Subtract(this.f[0].diff(arg), this.f[1].diff(arg)) };
Subtract.prototype.simplify = function () {
    let f0 = this.f[0].simplify();
    let f1 = this.f[1].simplify();
    if (isValue(f0, 0)){
        if (f1 instanceof Const){
            f1.f = -f1.f;
            return f1;
        } else {
            return new Negate(f1);
        }
    } else if (isValue(f1, 0)){
        return f0;
    } else if (f0 instanceof Const && f1 instanceof Const){
        return new Const(f0.f - f1.f);
    } else return new Subtract(f0, f1);
};

function Multiply(...f) {
    Operation.call(this, ...f);
    this.ch = '*';
}
Multiply.prototype.evaluate = function (...arg) {
    return this.f[0].evaluate(...arg) * this.f[1].evaluate(...arg);
};
Multiply.prototype.diff = function (arg) {
    return new Add(
        new Multiply(
            this.f[0].diff(arg),
            this.f[1]
        ), new Multiply(
            this.f[0],
            this.f[1].diff(arg)
        ));
};
Multiply.prototype.simplify = function () {
    let f0 = this.f[0].simplify();
    let f1 = this.f[1].simplify();
    if (isValue(f0, 0) || isValue(f1, 0)){
        return new Const(0);
    } else if (isValue(f0, 1)) {
        return f1;
    }else if (isValue(f1, 1)){
        return f0;
    } else if (f0 instanceof Const && f1 instanceof Const){
        return new Const(f0.f * f1.f);
    } else return new Multiply(f0, f1);
};

function Divide(...f) {
    Operation.call(this, ...f);
    this.ch = '/';
}
Divide.prototype.evaluate = function (...arg) { return this.f[0].evaluate(...arg) / this.f[1].evaluate(...arg); };
Divide.prototype.diff = function (arg) {
    return new Divide(
        new Subtract(
            new Multiply(
                this.f[0].diff(arg),
                this.f[1]
            ), new Multiply(
                this.f[0],
                this.f[1].diff(arg)
            )),
        new Multiply(this.f[1], this.f[1])
    );
};
Divide.prototype.simplify = function () {
    let f0 = this.f[0].simplify();
    let f1 = this.f[1].simplify();
    if (isValue(f0, 0)){
        return new Const(0);
    } else if (isValue(f1, 1)) {
        return f0;
    }else if (isValue(f1, 0)){
        return new Const(Infinity);
    } else if (f0 instanceof Const && f1 instanceof Const){
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
};

function Power(...f){
    Operation.call(this, ...f);
    this.ch = 'pow';
}
Power.prototype.evaluate = function (...arg) { return Math.pow(this.f[0].evaluate(...arg), this.f[1].evaluate(...arg))};
Power.prototype.diff = function (arg) {
    let u = this.f[0];
    let v = this.f[1];
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
    )
};
Power.prototype.simplify = function () {
    let f0 = this.f[0].simplify();
    let f1 = this.f[1].simplify();
    if (isValue(f1, 0)){
        return new Const(1);
    } else if (isValue(f1, 1)){
        return f0;
    } else if (isValue(f0, 0) || isValue(f0, 1)){
        return f0;
    } else {
        return new Power(f0, f1);
    }
};

function E(...f){
    Const.call(this, Math.E);
    this.ch = 'E';
}
E.prototype.evaluate = function (...arg) {return this.f};
E.prototype.diff = function (arg) {return new Const(0);};
E.prototype.simplify = function(arg) {return this; };
E.prototype.toString = function () { return "E"; };
E.prototype.postfix = function () { return "E"; };
E.prototype.prefix = function () { return "E"; };

function Log(...f){
    Operation.call(this, ...f);
    this.ch = 'log';
}
Log.prototype.evaluate = function (...arg) {
    return Math.log(Math.abs(this.f[1].evaluate(...arg))) / Math.log(Math.abs(this.f[0].evaluate(...arg)));
};
Log.prototype.diff = function (arg) {
    if (this.f[0] instanceof Const || this.f[0] instanceof E){ // если основание - это число
        return new Divide(this.f[1].diff(arg), new Multiply(this.f[1], new Log(new E(), this.f[0])));
        //  1 / (параметр * ln(основания)
    }
    let f0 = new Log(new E(),this.f[0]);
    let f1 = new Log(new E(),this.f[1]);
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
    );
};
Log.prototype.simplify = function () {
    let f0 = this.f[0].simplify();
    let f1 = this.f[1].simplify();
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
};

function Negate(...f) {
    Operation.call(this, ...f);
    this.ch = 'negate';
}
Negate.prototype.evaluate = function (...arg) { return -this.f[0].evaluate(...arg); };
Negate.prototype.diff = function (arg) { return new Negate(this.f[0].diff(arg)); };
Negate.prototype.simplify = function () {
    let f0 = this.f[0].simplify();
    if (f0 instanceof Negate){
        return f0.f[0];
    } else if (f0 instanceof Const) {
        f0.f = -f0.f;
        return f0;
    }
};

function Sumexp(...f) {
    Operation.call(this, ...f);
    this.ch = 'sumexp';
}
Sumexp.prototype.evaluate = function (...arg) { return this.f.reduce((sum, cur) => sum + Math.exp(cur.evaluate(...arg)), 0) };
Sumexp.prototype.simplify = function () {
    let f = this.f.map((cur) => cur.simplify());
    return new Add(...f);
};
Sumexp.prototype.diff = function (arg) {
    let f = this.f.map((cur) => new Power(new E(), cur).diff(arg));
    return new Add(...f);
};

function Softmax(...f) {
    Operation.call(this, ...f);
    this.ch = 'softmax';
}
Softmax.prototype.expr = function () {
    return new Divide(new Power(new E(), this.f[0]), new Sumexp(...this.f));
};
Softmax.prototype.evaluate = function (...args) { return this.expr().evaluate(...args) };
Softmax.prototype.diff = function (arg) { return this.expr().diff(arg) };
Softmax.prototype.simplify = function () { return this.expr().simplify() };

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
    for (let i of s.split(" ").filter(j => j !== "")) {
        if (i in opers){
            data.push(new opers[i].fun(...getN(data, opers[i].cou)));
        } else {
            data.push(parseValue(i));
        }
    }
    return data.pop();
};

const isWhitespace = function(c) {
    return c.length === 1 && new RegExp('\\s').test(c) || c === ' ';
};

const isScobe = (c) => c === '(' || c === ')';

const parseBase = function (s, mode) { //prefix - true
    s = s.trim();
    let i = 0;

    const read = function () {
        while(i < s.length && isWhitespace(s[i]))
            i++;
        if (isScobe(s[i]))
            return s[i++];
        let str = [];
        while (i < s.length && !isWhitespace(s[i]) && !isScobe(s[i])){
            str.push(s[i]);
            i++;
        }
        if (str.length === 0)
            throw new ParsingError("Unexpected end of line");
        return str.join('');
    };
    const newExpr = function () {
        let word = read();
        if (word !== "(")
            return parseValue(word);
        let args = [];
        let counter = 0;
        while((word = newExpr()) !== ")") {
            if (i === s.length) throw new ParsingError("Unexpected end of file");
            if (word in opers) counter++;
            if (counter > 1) throw new ExpressionError("A lot of operations");
            args.push(word);
        }
        if (args.length === 0)
            throw new ExpressionError("Expected contents of expression");
        let command = mode ? args.shift() : args.pop();
        if (!(command in opers))
            throw new SyntaxError("operation", command);
        let oc = opers[command];
        if ((oc.cou < 0 && args.length < -oc.cou -1) || (oc.cou >= 0 && oc.cou !== args.length))
            throw new ExpressionError("Invalid number of arguments");
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


//println(parsePrefix("(5)"));