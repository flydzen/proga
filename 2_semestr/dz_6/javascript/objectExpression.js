"use strict";

function Operation(...f) {
    this.f = f;
    this.ch = '';
    this.toString = function () {
        let result = this.f.reduce((sum, cur) => sum + cur.toString() + " ", '');
        return (result + this.ch).trim();
    };
}

let isValue = (obj, num) => obj instanceof Const && obj.value === num;

function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function (...arg) { return this.value; };
Const.prototype.toString = function () { return "" + this.value; };
Const.prototype.diff = function (arg) {return new Const(0)};
Const.prototype.simplify = function () {return this};

function Variable(name) {
    this.name = name;
}
Variable.prototype.evaluate = function (...arg) {
    switch (this.name) {
        case 'x':
            return arg[0];
        case 'y':
            return arg[1];
        case 'z':
            return arg[2];
    }
};
Variable.prototype.diff = function (arg) {
    return new Const(+(arg === this.name));
};
Variable.prototype.toString = function () { return this.name; };
Variable.prototype.simplify = function () { return this; };

function Add(...f) {
    Operation.call(this, ...f);
    this.ch = '+';
}
Add.prototype.evaluate = function (...arg) { return this.f[0].evaluate(...arg) + this.f[1].evaluate(...arg); };
Add.prototype.diff = function (arg) { return new Add(this.f[0].diff(arg), this.f[1].diff(arg)) };
Add.prototype.simplify = function () {
    let f0 = this.f[0].simplify();
    let f1 = this.f[1].simplify();
    if (isValue(f0, 0)){
        return f1;
    } else if (isValue(f1, 0)){
        return f0;
    } else if (f0 instanceof Const && f1 instanceof Const){
        return new Const(f0.value + f1.value);
    } else return new Add(f0, f1);
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
            f1.value = -f1.value;
            return f1;
        } else {
            return new Negate(f1);
        }
    } else if (isValue(f1, 0)){
        return f0;
    } else if (f0 instanceof Const && f1 instanceof Const){
        return new Const(f0.value - f1.value);
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
        return new Const(f0.value * f1.value);
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
        return new Const(f0.value / f1.value);
    } else return new Divide(f0, f1);
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
    let f0 = this.f[0];
    let f1 = this.f[1];
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

function Log(...f){
    Operation.call(this, ...f);
    this.ch = 'log';
}
Log.prototype.evaluate = function (...arg) { return Math.log(this.f[1].evaluate(...arg))/ Math.log(this.f[0].evaluate(...arg))};
Log.prototype.diff = function (...arg) {
    return new Divide(
        new Log(
            new Const(Math.E),
            this.f[1]
        ),
        new Log(
            new Const(Math.E),
            this.f[0]
        )
    ).diff(arg);
};
Log.prototype.simplify = function () {
    let f0 = this.f[0];
    let f1 = this.f[1];
    if (isValue(f1, 1)){
        return new Const(0);
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
        f0.value = -f0.value;
        return f0;
    }
};


const getN = (data, n) => data.splice(data.length - n, n);

const parse = function (s) {
    let data = [];
    for (let i of s.split(" ").filter(j => j !== "")) {
        if (i === '+') {
            data.push(new Add(...getN(data, 2)));
        } else if (i === '-') {
            data.push(new Subtract(...getN(data, 2)));
        } else if (i === '*') {
            data.push(new Multiply(...getN(data, 2)));
        } else if (i === '/') {
            data.push(new Divide(...getN(data, 2)));
        } else if (i === "negate") {
            data.push(new Negate(...getN(data, 1)));
        } else if (i === 'pow') {
            data.push(new Power(...getN(data, 2)));
        }else if (i === 'log') {
            data.push(new Log(...getN(data, 2)));
        } else if (!isNaN(i)) {
            data.push(new Const(parseInt(i)));
        } else if (['x', 'y', 'z'].indexOf(i) !== -1) {
            data.push(new Variable(i));
        }
    }
    return data.pop();
};