package by.training.sokolov.arrays;

import java.util.ArrayList;
import java.util.Scanner;

public class Arrays {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
    }

    static void task1() {
        System.out.println("1. В массив A [N] занесены натуральные числа. Найти сумму тех элементов, которые кратны данному К.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input K: ");
        int k = in.nextInt();

        int[] array = createRandomPositiveArray();

        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % k == 0) {
                sum += array[i];
            }
        }
        System.out.println("Sum = " + sum);
    }

    static void task2() {
        System.out.println("2. В целочисленной последовательности есть нулевые элементы. Создать массив из номеров этих элементов.");

        int[] startArray = createRandomPositiveArray();
        ArrayList<Integer> resultArray = new ArrayList<Integer>();

        for (int i = 0; i < startArray.length; i++) {
            if (startArray[i] == 0) {
                resultArray.add(i);
            }
        }

        outputArrayList(resultArray);
    }

    static void task3() {
        System.out.println("3. Дана последовательность целых чисел а1 а2,..., аn. \nВыяснить, какое число встречается раньше - положительное или отрицательное.");

        int[] array = createRandomSignedIntegerArray();

        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i] < 0 ? "\nfirst is negative" : "\nfirst is positive");
            return;
        }
    }


    static void task4() {
        System.out.println("4. Дана последовательность действительных чисел а1 а2 ,..., аn . Выяснить, будет ли она возрастающей.");

        int[] array = createRandomPositiveArray();
//        int[] array = {1, 2, 3};
        boolean isIncreasing = true;

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                isIncreasing = false;
                break;
            }
        }

        if (isIncreasing) {
            System.out.println("Возрастающая");
        } else {
            System.out.println("Не возрастающая");
        }
    }

    static void task5() {
        System.out.println("5. Дана последовательность натуральных чисел а1 , а2 ,..., аn. \n" +
                "Создать массив из четных чисел этой последовательности.\n" +
                "Если таких чисел нет, то вывести сообщение об этом факте.");

        boolean hasEven = false;
        int[] startArray = createRandomPositiveArray();
        ArrayList<Integer> resultArray = new ArrayList<Integer>();

        for (int i = 0; i < startArray.length; i++) {
            if (startArray[i] % 2 == 0) {
                hasEven = true;
                break;
            }
        }

        if (hasEven) {
            for (int i = 0; i < startArray.length; i++) {
                if (startArray[i] % 2 == 0) {
                    resultArray.add(startArray[i]);
                }
            }
        } else {
            System.out.println("Нет чётных");
            return;
        }

        outputArrayList(resultArray);
    }

    static void task6() {
        System.out.println("6. Дана последовательность чисел а1 ,а2 ,..., ап. \n" +
                "Указать наименьшую длину числовой оси, содержащую все эти числа.");

        int[] array = createRandomSignedIntegerArray();

        int maxElement = getMaxElement(array);
        int minElement = getMinElement(array);

        System.out.println("\nДлина числовой оси = [" + minElement + " , " + maxElement + "]");
    }


    static void task7() {
        System.out.println("7. Дана последовательность действительных чисел а1 ,а2 ,..., ап.\n" +
                "Заменить все ее члены, большие данного Z, этим числом.\n" +
                "Подсчитать количество замен.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input Z: ");
        int z = in.nextInt();

        int counter = 0;
        double[] array = createRandomSignedDoubleArray();

        for (int i = 0; i < array.length; i++) {
            if (array[i] > z) {
                array[i] = z;
                counter++;
            }
        }

        System.out.println("Ответ:");
        outputArray(array);
        System.out.println("Количество замен = " + counter);
    }

    static void task8() {
        System.out.println("8. Дан массив действительных чисел, размерность которого N. Подсчитать, сколько в нем отрицательных,\n" +
                "положительных и нулевых элементов.");

        int countPositiveElements = 0;
        int countNegativeElements = 0;
        int countZeroElements = 0;
        double[] array = createRandomSignedDoubleArray();

        for (int i = 0; i < array.length; i++) {
            if (array[i] > 0) {
                countPositiveElements++;
            }
            if (array[i] < 0) {
                countNegativeElements++;
            }
            if (array[i] == 0) {
                countZeroElements++;
            }
        }
        System.out.println("Положительных - " + countPositiveElements);
        System.out.println("Отрицательных - " + countNegativeElements);
        System.out.println("Нулевых - " + countZeroElements);
    }

    static void task9() {
        System.out.println("9. Даны действительные числа а1 ,а2 ,..., аn . Поменять местами наибольший и наименьший элементы.");

        double[] array = createRandomSignedDoubleArray();

        double maxElement = getMaxElement(array);
        double minElement = getMinElement(array);

        System.out.printf("\nmax element = %.1f", maxElement);
        System.out.printf("\nmin element = %.1f", minElement);

        for (int i = 0; i < array.length; i++) {
            if (array[i] == maxElement) {
                array[i] = minElement;
            } else {
                if (array[i] == minElement) {
                    array[i] = maxElement;
                }
            }
        }
        outputArray(array);
    }

    static void task10() {
        System.out.println("10. Даны целые числа а1 ,а2 ,..., аn . Вывести на печать только те числа, для которых аi > i.");

        int[] array = createRandomPositiveArray();

        System.out.print("\nОтвет: ");
        for (int i = 0; i < array.length; i++) {
            if (array[i] > i) {
                System.out.print(" " + array[i]);
            }
        }
    }

    private static void outputArrayList(ArrayList<Integer> resultArray) {
        System.out.println("\n");
        for (Integer number : resultArray) {
            System.out.print(number + " ");
        }
    }

    private static void outputArray(double[] array) {
        System.out.println("\n");
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%.1f ", array[i]);
        }
    }

    static void outputArray(int[] array) {
        System.out.println("\n");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    private static int[] createRandomSignedIntegerArray() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input amount of elements N: ");
        int N = in.nextInt();
        System.out.println("Array has elements from 0 to R: ");
        System.out.println("Input R: ");
        int radix = in.nextInt();

        int[] array = new int[N];

        for (int i = 0; i < N; i++) {
            array[i] = (int) (Math.random() * radix);
            boolean setMinusSign = (int) (Math.random() * 2) == 1;
            if (setMinusSign) {
                array[i] = -array[i];
            }
        }
        outputArray(array);
        return array;
    }

    private static double[] createRandomSignedDoubleArray() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input amount of elements N: ");
        int N = in.nextInt();
        System.out.println("Array has elements from 0 to R: ");
        System.out.println("Input R: ");
        int radix = in.nextInt();

        double[] array = new double[N];

        for (int i = 0; i < N; i++) {
            array[i] = Math.random() * radix;
            boolean setMinusSign = (int) (Math.random() * 2) == 1;
            if (setMinusSign) {
                array[i] = -array[i];
            }
        }
        outputArray(array);
        return array;
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

    private static int getMaxElement(int[] array) {
        int maxElement = array[0];

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > maxElement) {
                maxElement = array[i];
            }
        }
        return maxElement;
    }

    private static double getMaxElement(double[] array) {
        double maxElement = array[0];

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > maxElement) {
                maxElement = array[i];
            }
        }
        return maxElement;
    }

    private static int getMinElement(int[] array) {
        int minElement = array[0];

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < minElement) {
                minElement = array[i];
            }
        }
        return minElement;
    }

    private static double getMinElement(double[] array) {
        double minElement = array[0];

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < minElement) {
                minElement = array[i];
            }
        }
        return minElement;
    }
}
