package by.training.sokolov.core.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TimeService {

    private static final int HALF_AN_HOUR = 30;
    private static final int ONE_HOUR = 1;
    private static final int ONE_MINUTE = 1;
    private static final String TIME_09_00 = "09:00";
    private static final String TIME_23_00 = "23:00";
    private static final String TIME_23_59 = "23:59";
    private static final String TIME_00_00 = "00:00";

    public static List<LocalDateTime> findTimeVariants() {

        LocalDate currentDate = LocalDate.now();
        final LocalDateTime THIS_DAY_FIRST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_09_00);
        LocalDateTime THIS_DAY_LAST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_23_00);
        final LocalDateTime THIS_DAY_LAST_TIME_FOR_ORDER = THIS_DAY_LAST_TIME_OF_DELIVERY.minusMinutes(HALF_AN_HOUR);
        final LocalDateTime THIS_DAY_MIDNIGHT = LocalDateTime.parse(currentDate + "T" + TIME_00_00);
        final LocalDateTime THIS_DAY_23_59 = LocalDateTime.parse(LocalDate.now() + "T" + TIME_23_59);

        LocalDateTime currentTimeMinutes = LocalDateTime
                .now()
                .truncatedTo(ChronoUnit.MINUTES);

        LocalDateTime currentHourAndAHalf = LocalDateTime
                .now()
                .truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(HALF_AN_HOUR);

        LocalDateTime timeVariant = null;
        if (currentTimeMinutes.isAfter(THIS_DAY_LAST_TIME_FOR_ORDER) && currentTimeMinutes.isBefore(THIS_DAY_23_59)
                || currentTimeMinutes.compareTo(THIS_DAY_23_59) == 0) {
            timeVariant = THIS_DAY_23_59.plusMinutes(1);
        } else if ((currentTimeMinutes.isAfter(THIS_DAY_MIDNIGHT) && currentTimeMinutes.isBefore(THIS_DAY_FIRST_TIME_OF_DELIVERY.minusMinutes(ONE_MINUTE)))
                || currentTimeMinutes.compareTo(THIS_DAY_MIDNIGHT) == 0) {
            timeVariant = THIS_DAY_FIRST_TIME_OF_DELIVERY;
        } else if ((currentTimeMinutes.isAfter(THIS_DAY_FIRST_TIME_OF_DELIVERY) && currentTimeMinutes.isBefore(THIS_DAY_LAST_TIME_FOR_ORDER))
                || currentTimeMinutes.compareTo(THIS_DAY_FIRST_TIME_OF_DELIVERY) == 0
                || currentTimeMinutes.compareTo(THIS_DAY_LAST_TIME_FOR_ORDER) == 0) {
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
            THIS_DAY_LAST_TIME_OF_DELIVERY = THIS_DAY_LAST_TIME_OF_DELIVERY.plusDays(1);
            timeVariant = THIS_DAY_FIRST_TIME_OF_DELIVERY.plusDays(1);
        }

        List<LocalDateTime> result = new ArrayList<>();
        result.add(timeVariant);
        while (timeVariant.isBefore(THIS_DAY_LAST_TIME_OF_DELIVERY)) {
            timeVariant = timeVariant.plusHours(ONE_HOUR);
            result.add(timeVariant);
        }

        return result;
    }
}
