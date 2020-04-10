package by.training.sokolov.calculator;

public class Operators {
    static void add(Double firstOperand, Double secondOperand) {
        double result = firstOperand + secondOperand;
        outputResult(result);
    }

    static void subtract(Double firstOperand, Double secondOperand) {
        double result = firstOperand - secondOperand;
        outputResult(result);
    }

    static void multiply(Double firstOperand, Double secondOperand) {
        double result = firstOperand * secondOperand;
        outputResult(result);
    }

    static void divide(Double firstOperand, Double secondOperand) {
        double result = firstOperand / secondOperand;
        outputResult(result);
    }

    private static void outputResult(double result){
        System.out.printf("Ответ: %.3f\n", result);
    }
}
