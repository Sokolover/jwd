package by.training.sokolov.oop.simple_objects.task2;

public class Main {
    public static void main(String[] args) {
        task2();
    }

    static void task2() {
        System.out.println("2. Создйте класс Test2 двумя переменными. \n" +
                "Добавьте конструктор с входными параметрами. \n" +
                "Добавьте конструктор, инициализирующий члены класса по умолчанию. \n" +
                "Добавьте set- и get- методы для полей экземпляра класса.\n");


        Test2 object1 = new Test2();
        Test2 object2 = new Test2(2, 4);

        System.out.println("object1 values (constructor with param): a = " + object1.getA() + ", b = " + object1.getB());
        System.out.println("object2 values (default constructor)   : a = " + object2.getA() + ", b = " + object2.getB());
    }
}
