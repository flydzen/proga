"use strict";


const getN = (data, n) => data.splice(data.length - n, n);
const cnst = value => (...arg) => value;
const variable = (name) => (...arg) => {
    switch (name) {
        case 'x':
            return arg[0];
        case 'y':
            return arg[1];
        case 'z':
            return arg[2];
    }
};
const pi = () => Math.PI;
const e = () => Math.E;
const add = (...f) => (...arg) => f[0](...arg) + f[1](...arg);
const subtract = (...f) => (...arg) => f[0](...arg) - f[1](...arg);
const multiply = (...f) => (...arg) => f[0](...arg) * f[1](...arg);
const divide = (...f) => (...arg) => f[0](...arg) / f[1](...arg);
const negate = (...f) => (...arg) => -f[0](...arg);
const avg5 = (...f) => (...arg) => sumN(f, ...arg) / 5;
const med3 = (...f) => (...arg) => mid(f[0](...arg), f[1](...arg), f[2](...arg));
// в мед3 можно сократить применяя ко всем f;

let mid = (...args) => {
    args.sort((a, b) => (a - b));
    return args[Math.floor(args.length/2)];
};


const sumN = (f, ...args) => f.reduce(function (sum, current) {
    return sum + current(...args);
}, 0);


const parse = function (s) {
    let data = [];
    for (let i of s.split(" ").filter(j => j !== "")) {
        if (i === '+') {
            data.push(add(...getN(data, 2)));
        } else if (i === '-') {
            data.push(subtract(...getN(data, 2)));
        } else if (i === '*') {
            data.push(multiply(...getN(data, 2)));
        } else if (i === '/') {
            data.push(divide(...getN(data, 2)));
        } else if (i === "negate") {
            data.push(negate(...getN(data, 1)));
        } else if (i === "avg5") {
            data.push(avg5(...getN(data, 5)));
        } else if (i === "med3") {
            data.push(med3(...getN(data, 3)));
        } else if (!isNaN(i)) {
            data.push(cnst(parseInt(i)));
        } else if (i === "pi") {
            data.push(pi);
        } else if (i === "e") {
            data.push(e);
        } else if (['x', 'y', 'z'].indexOf(i) !== -1) {
            data.push(variable(i));
        }
    }
    return data.pop();
};