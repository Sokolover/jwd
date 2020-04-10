package by.training.sokolov.oop.basic_of_oop.task3;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

    private int daysCounter;
    private List<Weekend> weekends;

    public void outputWeekends() {
        System.out.println("\nВаш календарь праздников:");
        for (Weekend weekend : weekends) {
            System.out.println(CalendarUI.outputMonthName(weekend.month) + " " + weekend.day + " - " + weekend.name);
        }
        System.out.println("Всего праздников в году - " + daysCounter);
    }

    public void setWeekends() {
        System.out.println();
        weekends = new ArrayList<>();
        daysCounter = 0;
        boolean userEnter = true;
        while (userEnter) {
            System.out.println("Напишите название праздника и его дату");
            CalendarUI calendarUI = new CalendarUI();
            Weekend weekend = new Weekend();

            weekend.setName(calendarUI.enterWeekendName());
            weekend.setMonth(calendarUI.enterMonth());
            weekend.setDay(calendarUI.enterDay());
            weekends.add(weekend);
            daysCounter++;

            userEnter = calendarUI.addWeekends();
        }
    }


    class Weekend {
        private int day;
        private int month;
        private String name;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
