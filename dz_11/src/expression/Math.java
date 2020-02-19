package expression;

import expression.exceptions.ComputingException;
import expression.exceptions.OverflowException;

public class Math {
    static int multiply(int a, int b) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        } // a < b
        if (a > 0 && b > 0 && Integer.MAX_VALUE / a < b)
            throw new ComputingException("Overflow on multiplication");
        if (a < 0 && b < 0 && Integer.MAX_VALUE / a > b)
            throw new ComputingException("Overflow on multiplication");
        if (a < 0 && b < 0 && Integer.MAX_VALUE / b > a)
            throw new ComputingException("Overflow on multiplication");
        if (a < 0 && b > 0 && Integer.MIN_VALUE / b > a)
            throw new ComputingException("Overflow on multiplication");
        return a*b;
    }

    static int pow(int a, int x){
        if (x == 1)
            return a;
        if (x == 0 && a != 0)
            return 1;
        if (a == 0 && x > 0)
            return 0;
        if (x < 0)
            throw new ComputingException("Negative argument");
        if (a == 0 && x == 0) {
            throw new ComputingException("0^0 if undefined");
        }
        if (x % 2 == 1)
            return Math.multiply(pow(a, x-1), a);
        else {
            int b = pow(a, x/2);
            return Math.multiply(b, b);
        }
    }

    static int log(int x, int y){
        if (x <= 0)
            throw new ComputingException("Log with negative argument");
        if (y <= 0)
            throw new ComputingException("Base must be positive");
        if (y == 1)
            throw new ComputingException("Base cant be equal 1");
        if (x == 1) {
            return 0;
        }
        int counter = 0;
        while (x >= y){
            x /= y;
            counter++;
        }
        return counter;
    }


    static double abs(double a) {
        if (a == Integer.MIN_VALUE)
            throw new OverflowException("Overflow Exception");
        return a > 0 ? a : -a;
    }
    static int abs(int a) {
        return abs(a);
    }
}
