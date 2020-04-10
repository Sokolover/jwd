package by.training.sokolov.basics;

import java.util.Scanner;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Cycles {
    public static void main(String[] args) {
        f14();
    }

    static void f1() {
        System.out.println("Numbers from 1 to 5: ");
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
    }

    static void f2() {
        System.out.println("Numbers from 5 to 1: ");
        for (int i = 5; i >= 1; i--) {
            System.out.println(i);
        }
    }

    static void f3() {
        System.out.println("Multiplication table by 3:");
        for (int i = 1; i <= 10; i++) {
            System.out.println(i * 3);
        }
    }

    static void f4() {
        System.out.println("Output even numbers from 2 to 100: ");
        int i = 2;
        while (i <= 100) {
            System.out.println(i);
            i += 2;
        }
    }

    static void f5() {
        int i = 1;
        int sum = 0;
        while (i <= 99) {
            sum += i;
            i += 2;
        }
        System.out.println("Sum of odd numbers from 1 to 99 = " + sum);
    }

    static void f6() {
        Scanner in = new Scanner(System.in);
        int number;

        do {
            System.out.print("Input a number: ");
            number = in.nextInt();
        } while (number <= 0);

        if (number == 1) {
            System.out.println("1");
            return;
        }

        int sum = 0;
        for (int i = 1; i <= number; i++) {
            sum += i;
        }

        System.out.println("Sum from 1 to " + number + " = " + sum);

        in.close();
    }

    static void f7() {
        System.out.println("Function y values on the interval [a,b] with step h:");
        System.out.println("y = { x,  x > 2");
        System.out.println("    {-x, x <= 2");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input h: ");
        int h = in.nextInt();

        int condition = 2;

        for (int x = a; x <= condition; x += h) {
            System.out.println(-x);
        }
        for (int x = condition + 1; x <= b; x += h) {
            System.out.println(x);
        }
    }

    static void f8() {
        System.out.println("Function y values on the interval [a,b] with step h:");
        System.out.println("y = { (x + c) * 2, x == 15");
        System.out.println("    { (x - c) + 6, x != 15");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();
        System.out.print("Input h: ");
        int h = in.nextInt();

        int condition = 15;
        int y;

        for (int x = a; x <= b; x += h) {
            if (x == condition) {
                y = (x + c) * 2;
                System.out.println(y);
            }
            if (x != condition) {
                y = (x - c) + 6;
                System.out.println(y);
            }
        }
    }

    static void f9() {
        int sum = 0;
        for (int i = 1; i < 100; i++) {
            sum += (int) pow(i, 2);
        }
        System.out.println("Sum of squares of first 100 numbers = " + sum);
    }

    static void f10() {
        int product = 1;
        for (int i = 1; i < 200; i++) {
            product *= (int) pow(i, 2);
        }
        System.out.println("Product of first 200 numbers in power 2 = " + product);
    }

    static void f11() {
        int difference = 0;
        for (int i = 1; i < 200; i++) {
            difference -= (int) pow(i, 3);
        }
        System.out.println("Difference of first 200 numbers in power 3 = " + difference);
    }

    static void f12() {
        int mul = 1;
        for (int n = 1, anPrevious = 1; n < 10; n++) {
            mul *= n;
        }
    }

    static void f13() {
        System.out.println("Function y = 5 - x^2/2 value table on the interval [-5,5] with step 0.5: ");
        int leftBound = -5;
        int rightBound = 5;
        double step = 0.5;
        for (int x = -5; x < 5; x += step) {
            System.out.println(5 - pow(x, 2) / 2);
        }
    }

    static void f14() {
        System.out.println("Find sum of 1+1/2+1/3+...+1/n");

        Scanner in = new Scanner(System.in);
        System.out.print("Input n: ");
        int n = in.nextInt();
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += 1 / i;
        }
        System.out.println("Sum of 1+1/2+1/3+...+1/" + n + " = " + sum);
    }

    static void f15() {
        System.out.println("Find 1+2+4+8...+2 in power 10:");

        int middleOfRange = 8;
        int rightBound = 2;
        int leftBound = 1;
        int sum = 0;

        for (int i = leftBound; i < middleOfRange; i *= 2) {
            sum += i;
        }
        for (int i = middleOfRange; i > rightBound; i /= 2) {
            sum += i;
        }

        System.out.println("Result sum in power 10 = " + pow(sum, 10));
    }

    static void f16() {
        System.out.println("Find (1+2)*(1+2+3)*...*(1+2+...+10):");

        int leftBound = 2;
        int rightBound = 10;
        int product = 1;
        int sum = 0;
        for (int i = leftBound; i < rightBound; i++) {
            for (int j = 1; j <= i; j++) {
                sum += j;
            }
            product *= sum;
        }
        System.out.println("Result = " + product);
    }

    static void f17() {
        System.out.println("Find a(a+1)...(a+n-1):");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        double a = in.nextDouble();
        System.out.print("Input n: ");
        int n = in.nextInt();

        int product = 1;

        for (int i = 0; i < n; i++) {
            product *= a + i;
        }
        System.out.println("Result = " + product);
    }

    static void f18() {
        System.out.println("Find sum of range members that are greater in magnitude than number e");
        System.out.println("Range: an = -1^(n - 1) / n");

        Scanner in = new Scanner(System.in);
        System.out.print("Input e: ");
        double e = in.nextDouble();
        System.out.print("Input amount of members (n): ");
        int n = in.nextInt();
        int sum = 0;

        for (int i = 0; i < n; i++) {
            double an = pow(-1, i - 1) / i;
            if (abs(an) >= e) {
                sum += an;
            }
        }
        System.out.println("Result = " + sum);
    }

    static void f19() {
        System.out.println("Find sum of range members that are greater in magnitude than number e");
        System.out.println("Range: an = 1/(2^n) + 1/(3^n)");

        Scanner in = new Scanner(System.in);
        System.out.print("Input e: ");
        double e = in.nextDouble();
        System.out.print("Input amount of members (n): ");
        int n = in.nextInt();
        int sum = 0;

        for (int i = 0; i < n; i++) {
            double an = 1 / pow(2, i) + 1 / pow(3, i);
            if (abs(an) >= e) {
                sum += an;
            }
        }
        System.out.println("Result = " + sum);
    }

    static void f20() {
        System.out.println("Find sum of range members that are greater in magnitude than number e");
        System.out.println("Range: an = 1 / ((3 * n - 2) * (3 * n + 1))");

        Scanner in = new Scanner(System.in);
        System.out.print("Input e: ");
        double e = in.nextDouble();
        System.out.print("Input amount of members (n): ");
        int n = in.nextInt();
        int sum = 0;

        for (int i = 0; i < n; i++) {
            double an = 1 / ((3 * i - 2) * (3 * i + 1));
            if (abs(an) >= e) {
                sum += an;
            }
        }
        System.out.println("Result = " + sum);
    }
}
