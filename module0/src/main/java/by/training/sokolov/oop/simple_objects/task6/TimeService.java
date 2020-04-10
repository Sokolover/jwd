package by.training.sokolov.oop.simple_objects.task6;

import java.util.Scanner;

class TimeService {

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    void showTime(Time time) {

        int hours = time.getHours();
        int minutes = time.getMinutes();
        int seconds = time.getSeconds();

        String hoursString = formatTimeElement(hours);
        String minutesString = formatTimeElement(minutes);
        String secondsString = formatTimeElement(seconds);

        System.out.println(hoursString + ":" + minutesString + ":" + secondsString);
    }

    private String formatTimeElement(int intElement) {
        String stringElement;
        if (intElement < 10) {
            stringElement = "0" + intElement;
        } else {
            stringElement = "" + intElement;
        }
        return stringElement;
    }

    void changeTime(Time time) {
        changeHour(time);
        changeMinutes(time);
        changeSeconds(time);
    }

    private void changeHour(Time time) {
        System.out.println("Введите, сколько часов вы хотите прибавить");
        int addHours = enterHour();
        time.setHours(time.getHours() + addHours);
        if (time.getHours() > 23) {
            time.setHours((time.getHours() - 24));
        }
    }

    private void changeMinutes(Time time) {
        System.out.println("Введите, сколько минут вы хотите прибавить");
        int addMinutes = enterMinutes();
        time.setMinutes(time.getMinutes() + addMinutes);
        if (time.getMinutes() > 59) {
            time.setMinutes((time.getMinutes() - 24));
        }
    }

    private void changeSeconds(Time time) {
        System.out.println("Введите, сколько минут вы хотите прибавить");
        int addSeconds = enterSeconds();
        time.setSeconds(time.getSeconds() + addSeconds);
        if (time.getSeconds() > 59) {
            time.setSeconds((time.getSeconds() - 24));
        }
    }

    void setupTime(Time time) {

        int hour = enterHour();
        time.setHours(hour);

        int minute = enterMinutes();
        time.setMinutes(minute);

        int second = enterSeconds();
        time.setSeconds(second);
    }

    private int enterSeconds() {
        System.out.println("Введите секунды ");
        int second = enterInt();
        if (second > 59 || second < 0) {
            second = 0;
        }
        return second;
    }

    private int enterMinutes() {
        System.out.println("Введите минуты ");
        int minute = enterInt();
        if (minute > 59 || minute < 0) {
            minute = 0;
        }
        return minute;
    }

    private int enterHour() {
        System.out.println("Введите час ");
        int hour = enterInt();
        if (hour > 23 || hour < 0) {
            hour = 0;
        }
        return hour;
    }
}
