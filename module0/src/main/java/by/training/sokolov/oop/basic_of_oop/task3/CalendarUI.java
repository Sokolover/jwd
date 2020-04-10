package by.training.sokolov.oop.basic_of_oop.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;

public class CalendarUI {
    private final static List<String> monthList = new ArrayList<>(asList(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December")
    );

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public static String outputMonthName(int number) {
        for (int i = 0; i < monthList.size(); i++) {
            if (number - 1 == i) {
                return monthList.get(i);
            }
        }
        return "Такого месяца нет";
    }

    public boolean addWeekends() {
        boolean userEnter = true;
        System.out.println("Добавить ещё один праздник? (y/n)");
        Scanner in = new Scanner(System.in);
        String answer = in.next();
        if (answer.equalsIgnoreCase("n")) {
            userEnter = false;
        }
        return userEnter;
    }

    public String enterWeekendName() {
        Scanner in = new Scanner(System.in);
        System.out.print("Название праздника:\t");
        String name = in.next();
        return name;
    }

    public int enterDay() {
        System.out.println("День:\t");
        int day;
        do {
            day = enterInt();
        } while (incorrectDay(day));
        return day;
    }

    public int enterMonth() {
        System.out.println("Месяц:\t");
        int month;
        do {
            month = enterInt();
        } while (incorrectMonth(month));
        return month;
    }

    private boolean incorrectDay(int day) {
        return day < 0 || day > 31;
    }

    private boolean incorrectMonth(int month) {
        return month < 1 || month > 12;
    }
}
