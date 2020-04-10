package by.training.sokolov.basics;

import java.util.Scanner;

import static java.lang.Math.*;

public class Branching {
    public static void main(String[] args) {
        f12();
    }

    static void f1() {
        System.out.println("If number1 < number2 output number 7, else output number 8");

        Scanner in = new Scanner(System.in);
        System.out.print("Input number 1: ");
        int number1 = in.nextInt();
        System.out.print("Input number 2: ");
        int number2 = in.nextInt();

        int output1 = 7;
        int output2 = 8;

        if (number1 < number2) {
            System.out.println(output1);
        } else {
            System.out.println(output2);
        }
    }

    static void f2() {
        System.out.println("If number1 < number2 output 'yes', else output 'no'");

        Scanner in = new Scanner(System.in);
        System.out.print("Input number 1: ");
        int number1 = in.nextInt();
        System.out.print("Input number 2: ");
        int number2 = in.nextInt();

        String output1 = "yes";
        String output2 = "no";

        System.out.println(number1 < number2 ? output1 : output2);
    }

    static void f3() {
        System.out.println("Is your number greater than 3?");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int a = in.nextInt();
        int number = 3;
        String output1 = "yes";
        String output2 = "no";

        System.out.println(a < number ? output1 : output2);
    }

    static void f4() {
        System.out.println("a = b?");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();

        if (a == b) {
            System.out.println("a = b");
        } else {
            System.out.println("a != b");
        }
    }

    static void f5() {
        System.out.println("What number is smaller: a or b?");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();

        if (a > b) {
            System.out.println("smaller is number b");
        } else {
            System.out.println("smaller is number a");
        }
    }

    static void f6() {
        System.out.println("What number is bigger: a or b?");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();

        if (a > b) {
            System.out.println("bigger is number a");
        } else {
            System.out.println("bigger is number b");
        }
    }

    static void f7() {
        System.out.println("Find |a*x*x + b*x + c|");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();
        System.out.print("Input x: ");
        int x = in.nextInt();

        int result = a * (int) pow(x, 2) + b * x + c;
        if (result > 0) {
            System.out.println(result);
        } else {
            System.out.println(-result);
        }
    }

    static void f8() {
        System.out.println("Find which number in power 2 is greater");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();

        if (pow(a, 2) < pow(b, 2)) {
            System.out.println("a^2 < b^2");
        } else {
            System.out.println("a^2 > b^2");
        }
    }

    static void f9() {
        System.out.println("Is triangle has same length of sides?");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();

        if (a == b && b == c) {
            System.out.println("equilateral triangle");
        } else {
            System.out.println("not equilateral triangle");
        }
    }

    static void f10() {
        System.out.println("Square of which circle is smaller?");

        Scanner in = new Scanner(System.in);
        System.out.print("Input r1: ");
        int r1 = in.nextInt();
        System.out.print("Input r2: ");
        int r2 = in.nextInt();

        if (PI * pow(r1, 2) < PI * pow(r2, 2)) {
            System.out.println("Square of first circle is smaller");
        } else {
            System.out.println("Square of second circle is smaller");
        }
    }

    static void f11() {
        System.out.println("Square of which triangle is greater?");
        System.out.println("Coordinates of dots of first triangle");
        System.out.println("x1 = 1");
        System.out.println("y1 = 1");
        System.out.println("x2 = 2");
        System.out.println("y2 = 3");
        System.out.println("x3 = 3");
        System.out.println("y3 = 1");
        System.out.println("Coordinates of dots of second triangle");
        System.out.println("x1 = 1");
        System.out.println("y1 = 1");
        System.out.println("x2 = 3");
        System.out.println("y2 = 5");
        System.out.println("x3 = 5");
        System.out.println("y3 = 1");

        int x1 = 1;
        int y1 = 1;
        int x2 = 2;
        int y2 = 3;
        int x3 = 3;
        int y3 = 1;

        int square1 = abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)) / 2;

        x1 = 1;
        x2 = 1;
        x3 = 3;
        y1 = 5;
        y2 = 5;
        y3 = 1;

        int square2 = abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)) / 2;

        System.out.println(square1);
        System.out.println(square2);

        if (square1 < square2) {
            System.out.println("square1 < square2");
        } else {
            System.out.println("square1 > square2");
        }
    }

    static void f12() {
        System.out.println("Даны три действительных числа.");
        System.out.println("Возвести в квадрат те из них, значения которых неотрицательны, ");
        System.out.println("и в четвертую степень — отрицательные.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        double a = in.nextDouble();
        System.out.print("Input b: ");
        double b = in.nextDouble();
        System.out.print("Input c: ");
        double c = in.nextDouble();

        double[] array = new double[]{a, b, c};

        for (int i = 0; i < array.length; i++) {
            if (array[i] <= 0) {
                array[i] = pow(array[i], 4);
            } else {
                array[i] = pow(array[i], 2);
            }
        }
    }

    static void f13() {
        System.out.println("Даны две точки А(х1, у1) и В(х2, у2). \nСоставить алгоритм, определяющий, \nкоторая из точек находится ближе к началу координат.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input x1: ");
        int x1 = in.nextInt();
        System.out.print("Input y1: ");
        int y1 = in.nextInt();
        System.out.print("Input x2: ");
        int x2 = in.nextInt();
        System.out.print("Input y2: ");
        int y2 = in.nextInt();

        float distance1 = (float) sqrt(pow(y1, 2) + pow(x1, 2));
        float distance2 = (float) sqrt(pow(y2, 2) + pow(x2, 2));
        if (distance1 < distance2) {
            System.out.println("A is closer than B");
        } else {
            System.out.println("B is closer than A");
        }
    }

    static void f14() {
        System.out.println("Даны два угла треугольника (в градусах). \nОпределить, существует ли такой треугольник, \nи если да, то будет ли он прямоугольным.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input angle1: ");
        int angle1 = in.nextInt();
        System.out.print("Input angle2: ");
        int angle2 = in.nextInt();

        int angle3 = 180 - angle1 - angle2;

        if (angle3 < 60) {
            System.out.println("don't exist");
        } else {
            if (angle1 == 90 || angle2 == 90 || angle3 == 90) {
                System.out.println("right-angled triangle");
            } else {
                System.out.println("exist");
            }
        }
    }

    static void f15() {
        System.out.println("Даны действительные числа х и у, не равные друг другу. \nМеньшее из этих двух чисел заменить половиной их суммы, \nа большее — их удвоенным произведением.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input x: ");
        double x = in.nextDouble();
        System.out.print("Input y: ");
        double y = in.nextDouble();

        double sum = x + y;
        double product = x * y;
        if (x < y) {
            x = sum / 2;
            y = product * 2;
        } else {
            y = sum / 2;
            x = product * 2;
        }
        System.out.println("x = " + x + " y = " + y);
    }

    static void f16() {
        System.out.println("На плоскости ХОY задана своими координатами точка А. \nУказать, где она расположена \n(на какой оси или в каком координатном угле).");

        Scanner in = new Scanner(System.in);
        System.out.print("Input x: ");
        double x = in.nextDouble();
        System.out.print("Input y: ");
        double y = in.nextDouble();

        if (x > 0 && y > 0) {
            System.out.println("1 coordinate area");
        } else if (x > 0 && y < 0) {
            System.out.println("2 coordinate area");
        } else if (x < 0 && y < 0) {
            System.out.println("3 coordinate area");
        } else if (x < 0 && y > 0) {
            System.out.println("4 coordinate area");
        } else if (x == 0) {
            System.out.println("on X axis");
        } else if (y == 0) {
            System.out.println("on Y axis");
        }
    }

    static void f17() {
        System.out.println("Даны целые числа т, п. Если числа не равны, \nто заменить каждое из них одним и тем же числом, \nравным большему из исходных, а если равны, \nто заменить числа нулями.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input n: ");
        int n = in.nextInt();
        System.out.print("Input m: ");
        int m = in.nextInt();

        if (m == n) {
            m = 0;
            n = 0;
            System.out.println("m = " + m + "n = " + n);
            return;
        }

        int max;
        if (n > m) {
            max = n;
        } else {
            max = m;
        }

        if (n != m) {
            n = max;
            m = max;
        }
        System.out.println("m = " + m + "n = " + n);
    }

    static void f18() {
        System.out.println("Подсчитать количество отрицательных среди чисел а, b, с.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();
        int counter = 0;

        int[] array = new int[]{a, b, c};

        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                counter++;
            }
        }
        System.out.println("Result = " + counter);
    }

    static void f19() {
        System.out.println("Подсчитать количество положительных среди чисел а, b, с.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();
        int counter = 0;

        int[] array = new int[]{a, b, c};

        for (int i = 0; i < array.length; i++) {
            if (array[i] > 0) {
                counter++;
            }
        }
        System.out.println("Result = " + counter);
    }

    static void f20() {
        System.out.println("Определить, делителем каких чисел а, b, с является число k.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();
        System.out.print("Input k: ");
        int k = in.nextInt();

        System.out.println(a % k == 0 ? "k is a divisor of a" : "k is not a divisor of a");
        System.out.println(b % k == 0 ? "k is a divisor of b" : "k is not a divisor of b");
        System.out.println(c % k == 0 ? "k is a divisor of a" : "k is not a divisor of c");
    }
}
