package by.training.sokolov.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestLocalTimeMath {
    private static final int HALF_AN_HOUR = 30;
    private static final int ONE_HOUR = 1;
    private static final int ONE_MINUTE = 1;
    private static final String TIME_09_00 = "09:00";
    private static final String TIME_23_00 = "23:00";
    private static final String TIME_23_59 = "23:59";
    private static final String TIME_00_00 = "00:00";

    private static final int HALF_AN_HOUR_TO_ADD = 30;
    private static final int ONE_HOUR_TO_ADD = 1;
    private static final String LAST_TIME_OF_DELIVERY = "23:00";
    private static final String FIRST_TIME_OF_DELIVERY = "09:00";
    private static final String MIDNIGHT = "00:00";

    private static final LocalDateTime t8_00 = LocalDateTime.parse(LocalDate.now() + "T" + "08:00");
    private static final LocalDateTime _08_59 = LocalDateTime.parse(LocalDate.now() + "T" + "08:59");
    private static final LocalDateTime _09_00 = LocalDateTime.parse(LocalDate.now() + "T" + FIRST_TIME_OF_DELIVERY);
    private static final LocalDateTime t15_00 = LocalDateTime.parse(LocalDate.now() + "T" + "15:00");
    private static final LocalDateTime _23_00 = LocalDateTime.parse(LocalDate.now() + "T" + LAST_TIME_OF_DELIVERY);
    private static final LocalDateTime _22_30 = LocalDateTime.parse(LocalDate.now() + "T" + "22:30");
    private static final LocalDateTime _00_00 = LocalDateTime.parse(LocalDate.now() + "T" + MIDNIGHT);
    private static final LocalDateTime _23_59 = LocalDateTime.parse(LocalDate.now() + "T" + TIME_23_59);

    @Test
    public void time_8_00() {
        timeCalculator(t8_00);
    }

    @Test
    public void time_9_00() {
        timeCalculator(_09_00);
    }

    @Test
    public void time_15_00() {
        timeCalculator(t15_00);
    }

    @Test
    public void time_15_29() {
        timeCalculator(t15_00.plusMinutes(29));
    }

    @Test
    public void time_15_31() {
        timeCalculator(t15_00.plusMinutes(31));
    }

    @Test
    public void time_22_29() {
        timeCalculator(_23_00.minusMinutes(31));
    }

    @Test
    public void time_22_31() {
        timeCalculator(_23_00.minusMinutes(29));
    }

    @Test
    public void time_23_00() {
        timeCalculator(_23_00);
    }

    @Test
    public void time_23_59() {
        timeCalculator(_23_59.minusMinutes(0));
    }

    @Test
    public void time_23_01() {
        timeCalculator(_23_00.plusMinutes(1));
    }

    @Test
    public void time_00_00() {
        timeCalculator(_00_00);
    }

    @Test
    public void time_00_01() {
        timeCalculator(_00_00.plusMinutes(1));
    }

    public void timeCalculator(LocalDateTime currentTimeMinutes) {

        System.out.println("time = " + currentTimeMinutes);

        LocalDate currentDate = LocalDate.now();
        LocalDateTime FIRST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_09_00);
        LocalDateTime LAST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_23_00);
        LocalDateTime LAST_TIME_FOR_ORDER = LAST_TIME_OF_DELIVERY.minusMinutes(HALF_AN_HOUR);
        LocalDateTime MIDNIGHT = LocalDateTime.parse(currentDate + "T" + TIME_00_00);

        LocalDateTime currentHourAndAHalf = currentTimeMinutes
                .truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(HALF_AN_HOUR);

        LocalDateTime timeVariant = null;
        if (currentTimeMinutes.isAfter(LAST_TIME_FOR_ORDER) && currentTimeMinutes.isBefore(_23_59)
                || currentTimeMinutes.compareTo(_23_59) == 0) {
            timeVariant = _23_59.plusMinutes(1);
        } else if ((currentTimeMinutes.isAfter(MIDNIGHT) && currentTimeMinutes.isBefore(FIRST_TIME_OF_DELIVERY.minusMinutes(ONE_MINUTE)))
                || currentTimeMinutes.compareTo(MIDNIGHT) == 0) {
            timeVariant = FIRST_TIME_OF_DELIVERY;
        } else if ((currentTimeMinutes.isAfter(FIRST_TIME_OF_DELIVERY) && currentTimeMinutes.isBefore(LAST_TIME_FOR_ORDER))
                || currentTimeMinutes.compareTo(FIRST_TIME_OF_DELIVERY) == 0
                || currentTimeMinutes.compareTo(LAST_TIME_FOR_ORDER) == 0) {
            if (currentTimeMinutes.isAfter(currentHourAndAHalf)) {
                timeVariant = currentTimeMinutes
                        .truncatedTo(ChronoUnit.HOURS)
                        .plusHours(2 * ONE_HOUR);
            } else {
                timeVariant = currentTimeMinutes.
                        truncatedTo(ChronoUnit.HOURS)
                        .plusHours(ONE_HOUR);
            }
        }

        if (timeVariant.truncatedTo(ChronoUnit.DAYS).isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) {
            LAST_TIME_OF_DELIVERY = LAST_TIME_OF_DELIVERY.plusDays(1);
            timeVariant = FIRST_TIME_OF_DELIVERY.plusDays(1);
        }

        List<LocalDateTime> result = new ArrayList<>();
        result.add(timeVariant);
        while (timeVariant.isBefore(LAST_TIME_OF_DELIVERY)) {
            timeVariant = timeVariant.plusHours(ONE_HOUR);
            result.add(timeVariant);
        }

        for (LocalDateTime localTime : result) {
            System.out.println(localTime);
        }
    }

    @Test
    public void test3() {
        System.out.println(_23_59.plusMinutes(1));

        LocalDate currentDate = LocalDate.now();
        LocalDateTime FIRST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_09_00);
        LocalDateTime LAST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_23_00);
        LocalDateTime LAST_TIME_FOR_ORDER = LAST_TIME_OF_DELIVERY.minusMinutes(HALF_AN_HOUR);
        LocalDateTime MIDNIGHT = LocalDateTime.parse(currentDate + "T" + TIME_00_00);

        LocalDateTime timeVariant = _23_59.plusMinutes(1);

        if (timeVariant.isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) {
            LAST_TIME_OF_DELIVERY.plusDays(1);
        }

        System.out.println(LAST_TIME_OF_DELIVERY.plusDays(1));
    }
}
