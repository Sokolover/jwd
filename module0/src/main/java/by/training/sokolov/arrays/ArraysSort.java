package by.training.sokolov.arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ArraysSort {
    public static void main(String[] args) {
        task4();
    }

    static void task1() {
        System.out.println("1. Заданы два одномерных массива с различным количеством элементов и натуральное число k. Объединить их в\n" +
                "один массив, включив второй массив между k-м и (k+1) - м элементами первого, при этом не используя\n" +
                "дополнительный массив.");

        List<Integer> arrayList1 = createRandomArrayList();
        List<Integer> arrayList2 = createRandomArrayList();

        Scanner in = new Scanner(System.in);
        System.out.println("Input k");
        int kIndex = in.nextInt();

        for (Integer element : arrayList2) {
            arrayList1.add(kIndex++, element);
        }

//        or use
//        arrayList1.addAll(k, arrayList2);

        outputArrayList(arrayList1);
    }

    static void task2() {
        System.out.println("2. Даны две последовательности\n" +
                "a1<=a2<=...<=an и b1<=b2<=...<=bm" +
                "Образовать из них новую последовательность чисел\n" +
                "так, чтобы она тоже была неубывающей.\n" +
                "Примечание. Дополнительный массив не использовать.");

        List<Integer> arrayListA = createIncreasingArrayList();
        List<Integer> arrayListB = createIncreasingArrayList();

        int lastElementListA = arrayListA.size() - 1;
        int firstElementListB = 0;

        if (arrayListA.get(lastElementListA) <= arrayListB.get(firstElementListB)) {
            arrayListA.addAll(arrayListB);
            outputArrayList(arrayListA);
            return;
        }

        int loopCounter = Math.max(arrayListA.size(), arrayListB.size());

        for (int i = 0; i < arrayListA.size(); i++) {
            for (int j = 0; j < arrayListB.size(); j++) {
                if (arrayListA.get(i) > arrayListB.get(j)) {
                    arrayListA.add(i, arrayListB.get(j));
                    arrayListB.remove(j);
                }
            }
        }

        arrayListA.addAll(arrayListB);

        outputArrayList(arrayListA);
    }

    static void task3() {
        System.out.println("3. Сортировка выбором. Дана последовательность чисел\n" +
                "a1<=a2<=...<=an\n" +
                "Требуется переставить элементы так,\n" +
                "чтобы они были расположены по убыванию. Для этого в массиве, начиная с первого, выбирается наибольший элемент\n" +
                "и ставится на первое место, а первый - на место наибольшего. Затем, начиная со второго, эта процедура повторяется.\n" +
                "Написать алгоритм сортировки выбором.");


        List<Integer> arrayList = createDecreasingArrayList();

        for (int i = 0; i < arrayList.size() - 1; i++) {
            for (int j = i; j < arrayList.size(); j++) {
                if (arrayList.get(j) < arrayList.get(i)) {
                    int temp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, temp);
                }
            }
        }

        outputArrayList(arrayList);
    }

    static void task4() {
        System.out.println("4. Сортировка обменами. Дана последовательность чисел\n" +
                "a1<=a2<=...<=an\n" +
                "Требуется переставить числа в порядке\n" +
                "возрастания. Для этого сравниваются два соседних числа\n" +
                "ai и ai+1. Если\n" +
                "ai > ai+1, то делается перестановка. Так\n" +
                "продолжается до тех пор, пока все элементы не станут расположены в порядке возрастания. Составить алгоритм\n" +
                "сортировки, подсчитывая при этом количества перестановок.");

        List<Integer> arrayList = createDecreasingArrayList();
        int counter = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size() - 1; j++) {
                if (arrayList.get(j) > arrayList.get(j + 1)) {
                    int temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set(j + 1, temp);
                    counter++;
                }
            }
        }

        outputArrayList(arrayList);
        System.out.println("Количество перестоновок = " + counter);
    }


    static List<Integer> createRandomArrayList() {

        Scanner in = new Scanner(System.in);
        System.out.print("Input amount of elements N: ");
        int N = in.nextInt();
        System.out.println("Array has elements from 0 to R: ");
        System.out.print("Input R: ");
        int radix = in.nextInt();

        List<Integer> arrayList = new ArrayList<Integer>();

        for (int i = 0; i < N; i++) {
            arrayList.add((int) (Math.random() * radix));
        }
        outputArrayList(arrayList);
        return arrayList;
    }

    static List<Integer> createDecreasingArrayList() {

        Scanner in = new Scanner(System.in);
        System.out.print("Input amount of elements N: ");
        int N = in.nextInt();
        System.out.println("Array has elements from 0 to R: ");
        System.out.print("Input R: ");
        int radix = in.nextInt();

        List<Integer> arrayList = new ArrayList<Integer>();

        for (int i = 0; i < N; i++) {
            arrayList.add((int) (Math.random() * radix));
        }

        Collections.sort(arrayList);
        Collections.reverse(arrayList);

        outputArrayList(arrayList);
        return arrayList;
    }

    static List<Integer> createIncreasingArrayList() {

        Scanner in = new Scanner(System.in);
        System.out.print("Input amount of elements N: ");
        int N = in.nextInt();
        System.out.println("Array has elements from 0 to R: ");
        System.out.print("Input R: ");
        int radix = in.nextInt();

        List<Integer> arrayList = new ArrayList<Integer>();

        for (int i = 0; i < N; i++) {
            arrayList.add((int) (Math.random() * radix));
        }

        Collections.sort(arrayList);

        outputArrayList(arrayList);
        return arrayList;
    }

    static void outputArrayList(List<Integer> array) {
        System.out.println("\n");
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i) + " ");
        }
        System.out.println("");
    }
}
