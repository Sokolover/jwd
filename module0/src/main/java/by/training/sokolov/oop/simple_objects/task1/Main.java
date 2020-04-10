package by.training.sokolov.oop.simple_objects.task1;

public class Main {
    public static void main(String[] args) {
        task1();
    }

    static void task1() {
        Test1 test1 = new Test1();
        test1.setA(5);
        test1.outputA();
        test1.setB(10);
        test1.outputB();
        test1.sum(test1.getA(), test1.getB());
        test1.max(test1.getA(), test1.getB());
    }
}
