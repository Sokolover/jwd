package by.training.sokolov.oop.simple_objects.task5;

class SimpleCounterService {

    void getCurrentCounterCondition(DecimalCounter decimalCounter) {
        System.out.println("текущее значение счётчика = " + decimalCounter.getCounter());
        System.out.println("левая ганица = " + decimalCounter.getLeftBound());
        System.out.println("правая ганица = " + decimalCounter.getRightBound());
    }

    void incrementValue(DecimalCounter decimalCounter, int step) {

        decimalCounter.setCounter(decimalCounter.getCounter() + step);
        fixIncrementValue(decimalCounter);
    }

    void decrementValue(DecimalCounter decimalCounter, int step) {

        decimalCounter.setCounter(decimalCounter.getCounter() - step);
        fixDecrementValue(decimalCounter);
    }

    private void fixIncrementValue(DecimalCounter decimalCounter) {

        if (decimalCounter.getCounter() > decimalCounter.getRightBound()) {
            decimalCounter.setCounter(decimalCounter.getRightBound());
        }
    }

    private void fixDecrementValue(DecimalCounter decimalCounter) {

        if (decimalCounter.getCounter() < decimalCounter.getLeftBound()) {
            decimalCounter.setCounter(decimalCounter.getLeftBound());
        }
    }

}
