package by.training.sokolov.oop.simple_objects.task6;

public class Main {

    public static void main(String[] args) {
        System.out.println("6. Составьте описание класса для представления времени. \n" +
                "Предусмотрте возможности установки времени и изменения его отдельных полей (час, минута, секунда) \n" +
                "с проверкой допустимости вводимых значений. \n" +
                "В случае недопустимых значений полей поле устанавливается в значение 0. \n" +
                "Создать методы изменения времени на заданное количество часов, минут и секунд. \n");

        TimeService timeService = new TimeService();
        Time time = new Time();

        timeService.setupTime(time);
        timeService.showTime(time);
        timeService.changeTime(time);
        timeService.showTime(time);
    }
}
