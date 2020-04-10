package by.training.sokolov.oop.basic_of_oop.task2;

public class Main {
    public static void main(String[] args) {
        PaymentUI paymentUI = new PaymentUI();
        Payment payment1 = new Payment("Первая покупка");
        payment1.collectProducts();
        Payment payment2 = new Payment("Вторая покупка");
        payment2.collectProducts();

        paymentUI.printCheque(payment1);
        paymentUI.printCheque(payment2);
    }
}
