package by.training.sokolov.oop.simple_objects.task1;

public class Test1 {
    {
        System.out.println("1. Создайте класс Test1 с двумя переменными. \n" +
                "Добавьте метод вывода на экран и методы изменения этих переменных. \n" +
                "Добавьте метод, который находит сумму значений этих переменных, \n" +
                "и метод, который находит наибольшее значение из этих двух переменных.");
    }

    private int a;
    private int b;

    public void max(int a, int b) {
        if (a > b) {
            System.out.println("a > b");
        } else if (a < b) {
            System.out.println("a < b");
        } else {
            System.out.println("a = b");
        }
    }

    public void sum(int a, int b) {
        int sum = a + b;
        System.out.println("a + b = " + sum);
    }

    public void outputA() {
        System.out.println("a: " + this.a);
    }

    public void outputB() {
        System.out.println("b: " + this.b);
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
