"use strict";

function Operation(...f) {
    this.f = f;
    this.ch = '';
    this.toString = function () {
        let result = this.f.reduce((sum, cur) => sum + cur.toString() + " ", '');
        return (result + this.ch).trim();
    };
}

function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function (...arg) { return this.value; };
Const.prototype.toString = function () { return "" + this.value; };
Const.prototype.diff = function (arg) {return new Const(0)};

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

function Add(...f) {
    Operation.call(this, ...f);
    this.ch = '+';
}
Add.prototype.evaluate = function (...arg) { return this.f[0].evaluate(...arg) + this.f[1].evaluate(...arg); };
Add.prototype.diff = function (arg) { return new Add(this.f[0].diff(arg), this.f[1].diff(arg)) };

function Subtract(...f) {
    Operation.call(this, ...f);
    this.ch = '-';
}
Subtract.prototype.evaluate = function (...arg) { return this.f[0].evaluate(...arg) - this.f[1].evaluate(...arg); };
Subtract.prototype.diff = function (arg) { return new Subtract(this.f[0].diff(arg), this.f[1].diff(arg)) };

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

function Negate(...f) {
    Operation.call(this, ...f);
    this.ch = 'negate';
}
Negate.prototype.evaluate = function (...arg) { return -this.f[0].evaluate(...arg); };
Negate.prototype.diff = function (arg) { return new Negate(this.f[0].diff(arg)); };


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
        } else if (!isNaN(i)) {
            data.push(new Const(parseInt(i)));
        } else if (['x', 'y', 'z'].indexOf(i) !== -1) {
            data.push(new Variable(i));
        }
    }
    return data.pop();
};
let a = new Add(new Const(2), new Const(2)).diff('x');
println(a);
println(a.f[0]);
println(new Add(new Variable('x'), new Const(2)).diff('x').evaluate(2.000,2.000,2.000));
//(3+x)*y
// (y ((3 x +) y *) +)
// y+(3+x)*y