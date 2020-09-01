"use strict";


const getN = (data, n) => data.splice(data.length - n, n);
const cnst = value => (...arg) => value;
const names = ['x', 'y', 'z'];
const variable = (name) => (...arg) => {
    return arg[names.indexOf(name)];
};
const pi = cnst(Math.PI);
const e = cnst(Math.E);
const add = (...f) => (...arg) => f[0](...arg) + f[1](...arg);
const subtract = (...f) => (...arg) => f[0](...arg) - f[1](...arg);
const multiply = (...f) => (...arg) => f[0](...arg) * f[1](...arg);
const divide = (...f) => (...arg) => f[0](...arg) / f[1](...arg);
const negate = (...f) => (...arg) => -f[0](...arg);
const avg5 = (...f) => (...arg) => sumN(f, ...arg) / 5;
const med3 = (...f) => (...arg) => mid(f[0](...arg), f[1](...arg), f[2](...arg));

let mid = (...args) => {
    args.sort((a, b) => (a - b));
    return args[Math.floor(args.length/2)];
};

const sumN = (f, ...args) => f.reduce(function (sum, current) {
    return sum + current(...args);
}, 0);

const opers = {
    '+' : {fun: add, cou: 2},
    '-' : {fun: subtract, cou: 2},
    '*' : {fun: multiply, cou: 2},
    '/' : {fun: divide, cou: 2},
    'avg5' : {fun: avg5, cou: 5},
    'med3' : {fun: med3, cou: 3},
    'negate' : {fun: negate, cou: 1},
};
const values = {
    'pi' : pi,
    'e' : e,
};

const parseValue = function (s) {
    if (!isNaN(s)) {
        return cnst(parseInt(s));
    } else if (s in values){
        return values[s];
    }else if (names.indexOf(s) !== -1) {
        return variable(s);
    } else {
        throw new Error("Expected argument, got " + s);
    }
};

const parse = function (s) {
    let data = [];
    for (let i of s.split(" ").filter(j => j !== "")) {
        if (i in opers){
            data.push(opers[i].fun(...getN(data, opers[i].cou)));
        } else {
            data.push(parseValue(i));
        }
    }
    return data.pop();
};
println(parse("pi x +"));
println(parse("pi x +")(2,3,4));