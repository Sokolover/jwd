package by.training.sokolov.oop.simple_objects.task5;

class DecimalCounter {

    private int counter;
    private int leftBound;
    private int rightBound;
//            System.out.println("5. Опишите класс, реализующий десятичный счетчик, \n"+
//                    "который может увеличивать или уменьшать свое значение на единицу в заданном диапазоне. \n"+
//                    "Предусмотрите инициализацию счетчика значениями по умолчанию и произвольными значениями. \n"+
//                    "Счетчик имеет методы увеличения и уменьшения состояния, и метод позволяющее получить его текущее состояние. \n"+
//                    "Написать код, демонстрирующий все возможности класса.\n");

    DecimalCounter() {
        counter = 0;
        leftBound = -10;
        rightBound = 10;
    }

    DecimalCounter(int counter, int leftBound, int rightBound) {
        this.counter = counter;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    int getCounter() {
        return counter;
    }

    void setCounter(int counter) {
        this.counter = counter;
    }

    int getLeftBound() {
        return leftBound;
    }

    int getRightBound() {
        return rightBound;
    }

}
