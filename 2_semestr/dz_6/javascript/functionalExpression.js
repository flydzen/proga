"use strict";

function Stack() {
    this._size = 0;
    this._storage = [];
}

Stack.prototype.push = function (data) {
    this._storage[++this._size] = data;
};

Stack.prototype.pop = function () {
    if (this._size) {
        let deletedData = this._storage[this._size];
        delete this._storage[this._size--];
        return deletedData;
    }
};


const cnst = value => x => value;
const variable = () => x => x;
const add = (f, s) => arg => f(arg) + s(arg);
const subtract = (f, s) => arg => f(arg) - s(arg);
const multiply = (f, s) => arg => f(arg) * s(arg);
const divide = (f, s) => arg => f(arg) / s(arg);
const negative = (f) => arg => -f(arg);


const parse = function (s) {
    let data = new Stack();
    for (let i of s.split(" ").filter(j => j !== "")) {
        let popped = data.pop();
        if (i === '+') {
            data.push(add(data.pop(), popped));
        } else if (i === '-') {
            data.push(subtract(data.pop(), popped));
        } else if (i === '*') {
            data.push(multiply(data.pop(), popped));
        } else if (i === '/') {
            data.push(divide(data.pop(), popped));
        } else if (!isNaN(i)) {
            data.push(popped);
            data.push(cnst(parseInt(i)));
        } else if (i === "negative") {
            data.push(negative(popped));
        } else if (['x', 'y', 'z'].indexOf(i) !== -1) {
            data.push(popped);
            data.push(variable(i));
        }
    }
    return data.pop();
};

println(parse(" x        ")(5));