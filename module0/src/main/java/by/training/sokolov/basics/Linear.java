package by.training.sokolov.basics;

import java.util.Scanner;

import static java.lang.Math.*;

public class Linear {
    public static void main(String[] args) {
        f16();
    }

    static void f1() {

        Scanner in = new Scanner(System.in);
        System.out.print("Input x: ");
        double x = in.nextDouble();
        System.out.print("Input y: ");
        double y = in.nextDouble();

        double sum = x + y;
        double difference = x - y;
        double product = x * y;
        double quotient = x / y;

        System.out.println("x + y = " + sum);
        System.out.println("x - y = " + difference);
        System.out.println("x * y = " + product);
        System.out.println("x / y = " + quotient);

        in.close();
    }

    static void f2() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();

        int result = 3 + a;

        System.out.println("c = 3 + a = " + result);
    }

    static void f3() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input x: ");
        int x = in.nextInt();
        System.out.print("Input y: ");
        int y = in.nextInt();

        int result = 2 * x + (y - 2) * 5;

        System.out.println("z = 2 * x + (y - 2) * 5 = " + result);
    }

    static void f4() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();

        int result = ((a - 3) * b / 2) + c;

        System.out.println("z = ((a - 3) * b / 2) + c = " + result);
    }

    static void f5() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();

        double result = (a + b) / 2;

        System.out.println("Average arithmetic = " + result);
    }

    static void f6() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input n: ");
        int n = in.nextInt();
        System.out.print("Input m: ");
        int m = in.nextInt();
        final int difference = 12;

        int litersInAllSmall = 80;
        double litersInOneSmall = litersInAllSmall / n;
        double litersInOneBig = litersInOneSmall + difference;
        double litersInAllBig = litersInOneBig * m;

        System.out.println("In " + m + " cans there are " + litersInAllBig + " liters of milk");
    }

    static void f7() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input width: ");
        int width = in.nextInt();
        int difference = 2;

        int square = width * (width * 2);

        System.out.println("Square = " + square);
    }

    static void f8() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        double a = in.nextDouble();
        System.out.print("Input b: ");
        double b = in.nextDouble();
        System.out.print("Input c: ");
        double c = in.nextDouble();

        double result = ((b + Math.sqrt(Math.pow(b, 2) +
                4 * a * c)) / 2 * a -
                Math.pow(a, 3) * c +
                Math.pow(b, -2));

        System.out.println("Result = " + result);
    }

    static void f9() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        double a = in.nextDouble();
        System.out.print("Input b: ");
        double b = in.nextDouble();
        System.out.print("Input c: ");
        double c = in.nextDouble();
        System.out.print("Input d: ");
        double d = in.nextDouble();

        double result = ((a / c) * (b / d) - (a * b - c) / c * d);

        System.out.println("Result = " + result);
    }

    static void f10() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input x: ");
        double x = in.nextDouble();
        System.out.print("Input y: ");
        double y = in.nextDouble();

        double result = ((sin(x) + cos(x)) / (cos(x) - sin(y)) * tan(x * y));

        System.out.println("Result = " + result);
    }

    static void f11() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a cathetus: ");
        int aCathetus = in.nextInt();
        System.out.print("Input b cathetus: ");
        int bCathetus = in.nextInt();

        double hypotenuse = sqrt(pow(aCathetus, 2) + pow(bCathetus, 2));

        double perimeter = aCathetus + bCathetus + hypotenuse;
        double square = 0.5 * aCathetus * bCathetus;

        System.out.println("Perimeter = " + perimeter);
        System.out.println("Square = " + square);
    }

    static void f12() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input x1: ");
        int x1 = in.nextInt();
        System.out.print("Input y1: ");
        int y1 = in.nextInt();
        System.out.print("Input x2: ");
        int x2 = in.nextInt();
        System.out.print("Input y2: ");
        int y2 = in.nextInt();

        int xDistance = x2 - x1;
        int yDistance = y2 - y1;
        double dotsDistance = sqrt(pow(xDistance, 2) + pow(yDistance, 2));

        System.out.println("Distance between two dots = " + dotsDistance);
    }

    static void f13() {

        Scanner in = new Scanner(System.in);
        System.out.print("Input x1: ");
        int x1 = in.nextInt();
        System.out.print("Input y1: ");
        int y1 = in.nextInt();
        System.out.print("Input x2: ");
        int x2 = in.nextInt();
        System.out.print("Input y2: ");
        int y2 = in.nextInt();
        System.out.print("Input x3: ");
        int x3 = in.nextInt();
        System.out.print("Input y3: ");
        int y3 = in.nextInt();

        double A = sqrt(pow((x2 - x1), 2) + pow((y2 - y1), 2));
        double B = sqrt(pow((x3 - x2), 2) + pow((y2 - y1), 2));
        double C = sqrt(pow((x1 - x3), 2) + pow((y1 - y3), 2));

        double perimeter = (A + B + C) / 2;
        double square = sqrt(perimeter * (perimeter - A) * (perimeter - B) * (perimeter - C));

        System.out.println("Square = " + square);
        System.out.println("Perimeter = " + perimeter);
    }

    static void f14() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input radius: ");
        int radius = in.nextInt();

        double circleLength = 2 * PI * radius;
        double circleSquare = PI * pow(radius, 2);

        System.out.println("Circle length = " + circleLength);
        System.out.println("Circle square = " + circleSquare);
    }

    static void f15() {
        System.out.println("First 4 powers of PI: ");
        for (int i = 1; i <= 4; i++) {
            System.out.println(pow(PI, i));
        }
    }

    static void f16() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input number: ");
        int number = in.nextInt();
        int product = 1;
        int amountOfNumerals = 4;

        for (int i = 0; i < amountOfNumerals; i++) {
            product *= number % 10;
            number /= 10;
        }
        System.out.println("Product = " + product);
    }

    static void f17() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        double a = in.nextDouble();
        System.out.print("Input b: ");
        double b = in.nextDouble();

        double averageArithmetic = (pow(a, 3) + pow(b, 3)) / 2;
        double averageGeometric = sqrt(abs(a) + abs(b)) / 2;

        System.out.println("Average arithmetic of cubes = " + averageArithmetic);
        System.out.println("Average geometric of modules = " + averageGeometric);
    }

    static void f18() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input cube edge: ");
        int cubeEdge = in.nextInt();

        int planeSquare = (int) pow(cubeEdge, 2);
        int totalSurfaceSquare = 6 * planeSquare;
        int volume = (int) pow(cubeEdge, 3);

        System.out.println("Square of one cube plane = " + planeSquare);
        System.out.println("Cube total surface square = " + totalSurfaceSquare);
        System.out.println("Cube volume = " + volume);
    }

    static void f19() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input side length: ");
        int sideLength = in.nextInt();

        double triangleHeight = sqrt(pow(sideLength, 2) * pow(sideLength / 2, 2));
        double square = 0.5 * sideLength * triangleHeight;
        double innerCircleRadius = sideLength / (2 * sqrt(3));
        double outerCircleRadius = sideLength / sqrt(3);

        System.out.println("Triangle height = " + triangleHeight);
        System.out.println("Triangle square = " + square);
        System.out.println("Inner circle radius = " + innerCircleRadius);
        System.out.println("Outer circle radius = " + outerCircleRadius);
    }

    static void f20() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input circle length: ");
        int circleLength = in.nextInt();

        double radius = circleLength / (2 * PI);
        double circleSquare = PI * pow(radius, 2);

        System.out.println("Square = " + circleSquare);
    }
}
