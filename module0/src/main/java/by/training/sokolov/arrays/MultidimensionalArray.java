package by.training.sokolov.arrays;

import java.util.Scanner;

public class MultidimensionalArray {
    public static void main(String[] args) {
        task9();
    }

    static void task1() {
        System.out.println("1. Cоздать матрицу 3 x 4, заполнить ее числами 0 и 1 так,\n" +
                "чтобы в одной строке была ровно одна единица, и\n" +
                "вывести на экран.");
    }

    static void task2() {
        System.out.println("2. Создать и вывести на экран матрицу 2 x 3, заполненную случайными числами из [0, 9].");

        createRandomPositiveMatrix();
    }

    static void task3() {
        System.out.println("3. Дана матрица. Вывести на экран первый и последний столбцы.");

        int[][] matrix = createRandomPositiveMatrix();

        System.out.println("\nПервый\tПоледний");
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%4d\t%4d\n", matrix[i][0], matrix[i][matrix[i].length - 1]);
        }
    }

    static void task4() {
        System.out.println("4. Дана матрица. Вывести на экран первую и последнюю строки.");

        int[][] matrix = createRandomPositiveMatrix();

        System.out.println("\nПервая строка");
        for (int i = 0; i < matrix[0].length; i++) {
            System.out.printf("%4d", matrix[0][i]);
        }
        System.out.println("\nПоледняя строка");
        for (int i = 0; i < matrix[0].length; i++) {
            System.out.printf("%4d", matrix[matrix.length - 1][i]);
        }
    }

    static void task5() {
        System.out.println("5. Дана матрица. Вывести на экран все четные строки, то есть с четными номерами.");

        int[][] matrix = createRandomPositiveMatrix();

        for (int i = 0; i < matrix.length; i += 2) {
            outputRow(matrix[i], i);
            System.out.println("");
        }
    }

    static void task6() {
        System.out.println("6. Дана матрица. Вывести на экран все нечетные столбцы, у которых первый элемент больше последнего.");

        int[][] matrix = createRandomPositiveMatrix();
        boolean notFindAnswer = true;

        for (int j = 1; j < matrix[0].length; j += 2) {
            if (matrix[0][j] > matrix[matrix[0].length - 1][j]) {
                notFindAnswer = false;
                System.out.print("Столбец №" + j + " : ");
                for (int i = 0; i < matrix[j].length; i++) {
                    System.out.printf("%4d", matrix[i][j]);
                }
                System.out.println("");
            }
        }
        if (notFindAnswer) {
            System.out.println("Таких столбцов нет");
        }
    }

    static void task7() {
        System.out.println("7. Дан двухмерный массив 5×5. Найти сумму модулей отрицательных нечетных элементов.");

        int[][] matrix = createRandomSignedMatrix();
        int sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] % 2 != 0 && matrix[i][j] < 0) {
                    sum += Math.abs(matrix[i][j]);
                }
            }
        }
        System.out.println("Сумма модулей отрицательных нечетных элементов = " + sum);
    }

    static void task8() {
        System.out.println("8. Дан двухмерный массив n×m элементов.\n" +
                "Определить, сколько раз встречается число 7 среди элементов массива.");

        int[][] matrix = createRandomPositiveMatrix();
        int counter = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 7) {
                    counter++;
                }
            }
        }
        System.out.println("Ответ = " + counter);
    }

    static void task9() {
        System.out.println("9. Дана квадратная матрица. Вывести на экран элементы, стоящие на диагонали.");

        int[][] matrix = createRandomPositiveMatrix();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%4d", matrix[i][i]);
        }
    }

    static void task10() {
        System.out.println("10. Дана матрица. Вывести k-ю строку и p-й столбец матрицы.");

        Scanner in = new Scanner(System.in);
        System.out.print("Input k: ");
        int k = in.nextInt();
        System.out.print("Input p: ");
        int p = in.nextInt();

        int[][] matrix = createRandomPositiveMatrix();

        outputRow(matrix, k);
        outputColumn(matrix, p);
    }

    private static int[][] createRandomSignedMatrix() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input number of rows (n): ");
        int n = in.nextInt();
        System.out.print("Input number of columns (m): ");
        int m = in.nextInt();
        System.out.println("Matrix has elements from 0 to R: ");
        System.out.println("Input R: ");
        int radix = in.nextInt();

        int[][] matrix = new int[n][m];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = (int) (Math.random() * radix);
                boolean setMinusSign = (int) (Math.random() * 2) == 1;
                if (setMinusSign) {
                    matrix[i][j] = -matrix[i][j];
                }
            }
        }
        outputMatrix(matrix);
        return matrix;
    }

    private static int[][] createRandomPositiveMatrix() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input number of rows (n): ");
        int n = in.nextInt();
        System.out.print("Input number of columns (m): ");
        int m = in.nextInt();
        System.out.println("Matrix has elements from 0 to R: ");
        System.out.println("Input R: ");
        int radix = in.nextInt();

        int[][] matrix = new int[n][m];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = (int) (Math.random() * radix);
            }
        }
        outputMatrix(matrix);
        return matrix;
    }

    static void outputMatrix(int[][] matrix) {
        showRowIndexes(matrix);
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private static void showRowIndexes(int[][] matrix) {
        System.out.println("\n");
        System.out.printf("  ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%4d", i);
        }
        System.out.print("\n   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("____");
        }
        System.out.println("");
    }

    private static void outputColumn(int[][] matrix, int j) {
        System.out.print("Столбец №" + j + " : ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%4d", matrix[i][i]);
        }
    }

    private static void outputRow(int[][] matrix, int i) {
        System.out.print("Строка №" + i + " : ");
        for (int j = 0; j < matrix.length; j++) {
            System.out.printf("%4d", matrix[i][j]);
        }
    }

    private static void outputRow(int[] matrix, int i) {
        System.out.print("Строка №" + i + " : ");
        for (int j = 0; j < matrix.length; j++) {
            System.out.printf("%4d", matrix[j]);
        }
    }
}
