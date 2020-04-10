package by.training.sokolov.oop.basic_of_oop.task3;

public class Main {
    public static void main(String[] args) {
        System.out.println("3) Создать класс Календарь с внутренним классом, \n" +
                "с помощью объектов которого можно \n" +
                "хранить информацию о выходных и праздничных днях.\n");
        Calendar calendar = new Calendar();
        calendar.setWeekends();
        calendar.outputWeekends();
    }
}
