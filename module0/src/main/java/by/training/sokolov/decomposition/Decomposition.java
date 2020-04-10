package by.training.sokolov.decomposition;

import java.util.ArrayList;
import java.util.Scanner;

public class Decomposition {
    final static int X_COORDINATE = 0;
    final static int Y_COORDINATE = 1;

    public static void main(String[] args) {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
//        task15();
//        task16();
//        task17();
//        task18();
//        task19();
//        task20();
    }

    static void task1() {
        System.out.println("1. Треугольник задан координатами своих вершин. Написать метод для вычисления его площади.");
//        A(1,3), B(2,-5), C(-8,4)

        int[][] coordinates = inputCoordinates();

        System.out.println("Площадь треугольника = " + calculateSquare(coordinates));
    }

    private static double calculateSquare(int[][] coordinates) {
        int x1 = coordinates[0][0];
        int y1 = coordinates[0][1];
        int x2 = coordinates[1][0];
        int y2 = coordinates[1][1];
        int x3 = coordinates[2][0];
        int y3 = coordinates[2][1];
        double square = Math.abs((x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3)) / 2;
        return square;
    }


    private static int[][] inputCoordinates() {
        final int DIMENSION = 2;
        final int AMOUNT_OF_ANGLES = 3;
        int[][] coordinates = new int[AMOUNT_OF_ANGLES][DIMENSION];

        Scanner in = new Scanner(System.in);

        System.out.print("x1: ");
        coordinates[0][0] = in.nextInt();
        System.out.print("y1: ");
        coordinates[0][1] = in.nextInt();
        System.out.print("x2: ");
        coordinates[1][0] = in.nextInt();
        System.out.print("y2: ");
        coordinates[1][1] = in.nextInt();
        System.out.print("x3: ");
        coordinates[2][0] = in.nextInt();
        System.out.print("y3: ");
        coordinates[2][1] = in.nextInt();

        return coordinates;
    }

    static void task2() {
        System.out.println("2. Написать метод(методы) для нахождения наибольшего общего делителя и наименьшего общего кратного двух\n" +
                "натуральных чисел");

        int[] inputValues = input2Values();
        int a = inputValues[0];
        int b = inputValues[1];

        System.out.println("НОД = " + nod(a, b));
        System.out.println("НОК = " + nok(a, b));
    }

    static void task3() {
        System.out.println("3. Написать метод(методы) для нахождения наибольшего общего делителя четырех натуральных чисел.");
//        НОД(78, 294, 570, 36)=6.

        int[] inputValues = input4Values();
        System.out.println("НОД 4 чисел = " + nod(inputValues));
    }

    static void task4() {
        System.out.println("4. Написать метод(методы) для нахождения наименьшего общего кратного трех натуральных чисел.");
//        НОК (32; 66; 89) = 93984

        int[] inputValues = input3Values();
        System.out.println("НОК 3 чисел = " + nok(inputValues));
    }

    static void task5() {
        System.out.println("5. Написать метод(методы) для нахождения суммы большего и меньшего из трех чисел.");

        int[] inputValues = input3Values();
        System.out.println("Max of 3 = " + maxOf3Values(inputValues));
        System.out.println("Min of 3 = " + minOf3Values(inputValues));
        System.out.println("Sum of min and max element = " + maxOf3Values(inputValues) + minOf3Values(inputValues));
    }

    static void task6() {
        System.out.println("6. Вычислить площадь правильного шестиугольника со стороной а, используя метод вычисления площади\n" +
                "треугольника.");

        int side = 6;

        System.out.println("Длина стороны = " + side);
        System.out.println("Площадь треугольника = " + triangleSquare(side));
        System.out.println("Площадь шестиугольника = " + hexagonSquare(side));
    }

    private static int hexagonSquare(int side) {
        return 6 * triangleSquare(side);
    }

    private static int triangleSquare(int side) {
        return (side * side) / 2;
    }


    static void task7() {
        System.out.println("7. На плоскости заданы своими координатами n точек. Написать метод(методы), определяющие, между какими из\n" +
                "пар точек самое большое расстояние. Указание: координаты точек занести в массив.");

//        (1;1)(3;1)(3;3)

        int[][] points = inputPointArray();

        int point1Index = 0;
        int point2Index = 1;

        double maxDistance = calculateDistance(points, point1Index, point2Index);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = calculateDistance(points, i, j);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    point1Index = i;
                    point2Index = j;
                }
            }
        }

        System.out.println("Max distance between " + point1Index + " and " + point2Index + " = " + maxDistance);
    }

    static void task8() {
        System.out.println("8. Составить программу, которая в массиве A[N] находит второе по величине число (вывести на печать число,\n" +
                "которое меньше максимального элемента массива, но больше всех других элементов).");

        int[] array = createRandomPositiveArray();

        selectionSort(array);

        System.out.println("Второе по велечине число = " + array[array.length - 2]);
    }

    static void task9() {
        System.out.println("9. Написать метод(методы), проверяющий, являются ли данные три числа взаимно простыми.");

//        НОД(14, 105, 2 107, 91) = 7
//        НОД(84, 275) = 1
//        Являются ли числа 331, 463 и 733 взаимно простыми? ДА

        int[] inputValues = input3Values();
        relative3Numbers(nod(inputValues));
    }

    private static void relative3Numbers(int nod) {
        if (nod == 1) {
            System.out.println("НОД 3 чисел = " + nod + ", значит они являются взаимнопростыми");
        } else {
            System.out.println("НОД 3 чисел = " + nod + ", значит они НЕ являются взаимнопростыми");
        }
    }

    static void task10() {
        System.out.println("10. Написать метод(методы) для вычисления суммы факториалов всех нечетных чисел от 1 до 9.");

        for (int i = 1; i <= 9; i += 2) {
            System.out.println(calculateFactorial(i));
        }
    }

    static void task11() {
        System.out.println("11. Задан массив D. Определить следующие суммы: D[l] + D[2] + D[3]; D[3] + D[4] + D[5]; D[4] + D[5] + D[6].\n" +
                "Пояснение. Составить метод(методы) для вычисления суммы трех последовательно расположенных элементов\n" +
                "массива с номерами от k до m.");

        calculateSumSequence(createRandomPositiveArray());
    }

    static void task12() {
        System.out.println("12. Даны числа X, Y, Z, Т — длины сторон четырехугольника. Написать метод(методы) вычисления его площади,\n" +
                "если угол между сторонами длиной X и Y — прямой.");

        System.out.println("x = 5\n" +
                "y = 5\n" +
                "z = 6\n" +
                "t = 7");

        int x = 5;
        int y = 5;
        int z = 6;
        int t = 7;

        System.out.println("Ответ: " + quadSquare(x, y, z, t));
    }

    private static double quadSquare(int x, int y, int z, int t) {
        double hypotenuse = calculateHypotenuse(x, y);
        double square1 = calculateRightTriangleSquare(x, y);
        double halfPerimeter = calculateHalfPerimeter(z, t, hypotenuse);
        double square2 = calculateHeronExpression(z, t, hypotenuse, halfPerimeter);
        return square1 + square2;
    }

    private static double calculateHeronExpression(int z, int t, double hypotenuse, double halfPerimeter) {
        return Math.sqrt(halfPerimeter * (halfPerimeter - t) * (halfPerimeter - z) * (halfPerimeter - hypotenuse));
    }

    private static double calculateHalfPerimeter(int z, int t, double hypotenuse) {
        return (hypotenuse + z + t) / 2;
    }

    private static int calculateRightTriangleSquare(int x, int y) {
        return (x * y) / 2;
    }

    private static double calculateHypotenuse(int x, int y) {
        return Math.sqrt(x * x + y * y);
    }

    static void task13() {
        System.out.println("13. Дано натуральное число N. Написать метод(методы) для формирования массива, элементами которого являются\n" +
                "цифры числа N (N = 123456789).");

        int N = 123456789;

        outputArray(createArrayFromDigits(N));
    }

    static void task14() {
        System.out.println("14. Написать метод(методы), определяющий, в каком из данных двух чисел больше цифр.");

        int[] inputValues = input2Values();

        if (getCountsOfDigits(inputValues[0]) < getCountsOfDigits(inputValues[1])) {
            System.out.println("В числе a больше цифр");
        } else {
            System.out.println("В числе b больше цифр");
        }
    }

    static void task15() {
        System.out.println("15. Даны натуральные числа К и N. Написать метод(методы) формирования массива А, элементами которого\n" +
                "являются числа, сумма цифр которых равна К и которые не большее N.");

        int[] inputArray = input2Values();

        int K = inputArray[0];
        int N = inputArray[1];

        System.out.println("Числа для создания будут выбраны из диапазона [0 .. 999]");
        int amount = 1000;

        ArrayList<Integer> outputArray = new ArrayList<Integer>();

        for (int i = 0; i < amount; i++) {
            if (arraySum(createArrayFromDigits(i)) == K && i <= N) {
                outputArray.add(i);
            }
        }
        outputArrayList(outputArray);
    }

    static void task16() {
        System.out.println("16. Два простых числа называются «близнецами», если они отличаются друг от друга на 2 (например, 41 и 43). Найти\n" +
                "и напечатать все пары «близнецов» из отрезка [n,2n], где n - заданное натуральное число больше 2. Для решения\n" +
                "задачи использовать декомпозицию.");

        int n = getAmountOfNumbers();

        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        collectSimpleNumbers(n, arrayList);

        outputNumbersTwins(arrayList);
    }

    private static void outputNumbersTwins(ArrayList<Integer> arrayList) {
        for (int i = 0; i < arrayList.size() - 1; i++) {
            if (arrayList.get(i) + 2 == arrayList.get(i + 1)) {
                System.out.print(" " + arrayList.get(i));
                System.out.println(" " + arrayList.get(i + 1));
            }
        }
    }

    private static void collectSimpleNumbers(int n, ArrayList<Integer> arrayList) {
        for (int i = n; i <= 2 * n; i++) {
            if (isSimpleNumber(i)) {
                arrayList.add(i);
            }
        }
    }

    private static boolean isSimpleNumber(int num) {
        boolean isSimple = true;

        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                isSimple = false;
                break;
            }
        }
        return isSimple;
    }

    static void task17() {
        System.out.println("17. Натуральное число, в записи которого n цифр, называется числом Армстронга, если сумма его цифр, возведенная\n" +
                "в степень n, равна самому числу. Найти все числа Армстронга от 1 до k. Для решения задачи использовать\n" +
                "декомпозицию.");

        int k = getAmountOfNumbers();

        for (int i = 1; i < k; i++) {
            if (arraySumWithElementsInPower(createArrayFromDigits(i), getCountsOfDigits(i)) == i) {
                System.out.println(i);
            }
        }
    }

    static void task18() {
        System.out.println("18. Найти все натуральные n-значные числа, цифры в которых образуют строго возрастающую последовательность\n" +
                "(например, 1234, 5789). Для решения задачи использовать декомпозицию.");

        int n = getAmountOfNumbers();

        for (int i = 0; i < n; i++) {
            if (isIncreasing(createArrayFromDigits(i))) {
                System.out.println(i);
            }
        }
    }

    static void task19() {
        System.out.println("19. Написать программу, определяющую сумму n-значных чисел, содержащих только нечетные цифры. Определить\n" +
                "также, сколько четных цифр в найденной сумме. Для решения задачи использовать декомпозицию.");

        int n = getAmountOfNumbers();
        int sum = 0;

        System.out.println("Числа с нечётными цифрами от 0 до " + n);
        for (int i = 0; i < n; i++) {
            if (allDigitsOdd(i)) {
                System.out.println(i);
                sum += i;
            }
        }
        System.out.println("Сумма = " + sum + ". Количество четных цифр в найденной сумме = " + countEvenDigits(sum));
    }

    static void task20() {
        System.out.println("20. Из заданного числа вычли сумму его цифр. Из результата вновь вычли сумму его цифр и т.д. Сколько таких\n" +
                "действий надо произвести, чтобы получился нуль? Для решения задачи использовать декомпозицию.");

        Scanner in = new Scanner(System.in);
        System.out.println("Input number:");
        int n = in.nextInt();

        int loopCounter = 0;
        while (n != 0) {
            System.out.print("" + n + " - " + arraySum(createArrayFromDigits(n)) + " = ");
            n -= arraySum(createArrayFromDigits(n));
            System.out.println(n);
            loopCounter++;
        }
        System.out.println("Ответ: " + loopCounter);
    }

    private static int countEvenDigits(int n) {
        int counter = 0;
        int amountOfNDigits = getCountsOfDigits(n);

        for (int i = 0; i < amountOfNDigits; i++) {
            if ((n % 10) % 2 == 0) {
                counter++;
            }
            n /= 10;
        }
        return counter;
    }

    private static boolean allDigitsOdd(int n) {
        boolean oddDigit = true;
        int amountOfNDigits = getCountsOfDigits(n);

        for (int i = 0; i < amountOfNDigits; i++) {
            if ((n % 10) % 2 == 0) {
                oddDigit = false;
                break;
            }
            n /= 10;
        }
        return oddDigit;
    }

    private static int getAmountOfNumbers() {
        Scanner in = new Scanner(System.in);
        System.out.println("Input amount of numbers:");
        return in.nextInt();
    }

    private static boolean isIncreasing(int[] array) {
        boolean isIncreasing = true;

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] <= array[i]) {
                isIncreasing = false;
                break;
            }
        }
        return isIncreasing;
    }

    private static void outputArrayList(ArrayList<Integer> resultArray) {
        System.out.println("\n");
        for (Integer number : resultArray) {
            System.out.print(number + " ");
        }
    }

    private static int arraySumWithElementsInPower(int[] array, int power) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += Math.pow(array[i], power);
        }
        return sum;
    }

    private static int arraySum(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    private static int[] createArrayFromDigits(int n) {
        int[] array = new int[getCountsOfDigits(n)];

        for (int i = array.length - 1; i >= 0; i--) {
            array[i] = (n % 10);
            n /= 10;
        }
        return array;
    }

    private static int getCountsOfDigits(int number) {
        int count = (number == 0) ? 1 : 0;
        while (number != 0) {
            count++;
            number /= 10;
        }
        return count;
    }

    private static void calculateSumSequence(int[] array) {
        int k = 1;
        int m = 6;
        final int elementsInSum = 3;
        final int amountOfSums = 3;
        int[] sumArray = new int[amountOfSums];

        int sumArrayIndex = 0;
        for (int i = k; i <= m; i += 2) {
            for (int j = i; j < elementsInSum + i; j++) {
                sumArray[sumArrayIndex] += array[j];
            }
            sumArrayIndex++;
        }

        outputArray(sumArray);
    }


    static int calculateFactorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }
        return result;
    }

    private static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[i]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        outputArray(array);
    }

    static void outputArray(int[] array) {
        System.out.println("\n");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    private static int[] createRandomPositiveArray() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input amount of elements N: ");
        int N = in.nextInt();
        System.out.println("Array has elements from 0 to R: ");
        System.out.print("Input R: ");
        int radix = in.nextInt();

        int[] array = new int[N];

        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * radix);
        }
        outputArray(array);
        return array;
    }


    static double calculateDistance(int[][] points, int currentPoint, int nextPoint) {
        return
                Math.sqrt(Math.pow(points[currentPoint][X_COORDINATE] - points[nextPoint][X_COORDINATE], 2) +
                        Math.pow(points[currentPoint][Y_COORDINATE] - points[nextPoint][Y_COORDINATE], 2));

    }

    static int[][] inputPointArray() {
        final int DIMENSION = 2;
        Scanner in = new Scanner(System.in);
        System.out.print("Input amount of points (n): ");
        int n = in.nextInt();

        int[][] points = new int[n][DIMENSION];

        for (int i = 0; i < points.length; i++) {
            System.out.printf("Input %d point\n", i + 1);
            int[] pointCoordinates = inputPointCoordinates();
            for (int j = 0; j < DIMENSION; j++) {
                points[i][j] = pointCoordinates[j];
            }
        }
        return points;
    }

    static int[] inputPointCoordinates() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input x: ");
        int x = in.nextInt();
        System.out.print("Input y: ");
        int y = in.nextInt();
        return new int[]{x, y};
    }

    private static int minOf3Values(int[] array) {
        int min = array[0];
        for (int element : array) {
            if (element < min) {
                min = element;
            }
        }
        return min;
    }

    private static int maxOf3Values(int[] array) {
        int max = array[0];
        for (int element : array) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    static int maxOf2Values(int a, int b) {
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        } else {
            return 0;
        }
    }

    private static int nok(int[] inputValues) {

        int result = inputValues[0];
        for (int i = 1; i < inputValues.length; i++) {
            result = nok(result, inputValues[i]);
        }
        return result;
    }

    private static int nod(int[] inputValues) {

        int result = inputValues[0];
        for (int i = 1; i < inputValues.length; i++) {
            result = nod(result, inputValues[i]);
        }
        return result;
    }

    private static int[] input2Values() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a (K): ");
        int a = in.nextInt();
        System.out.print("Input b (N): ");
        int b = in.nextInt();
        return new int[]{a, b};
    }

    private static int[] input3Values() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();
        return new int[]{a, b, c};
    }

    private static int[] input4Values() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a: ");
        int a = in.nextInt();
        System.out.print("Input b: ");
        int b = in.nextInt();
        System.out.print("Input c: ");
        int c = in.nextInt();
        System.out.print("Input d: ");
        int d = in.nextInt();
        return new int[]{a, b, c, d};
    }

    static int nok(int a, int b) {
        return (a * b) / nod(a, b);
    }

    static int nod(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}
