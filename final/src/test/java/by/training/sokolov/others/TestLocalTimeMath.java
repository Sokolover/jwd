package by.training.sokolov.others;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestLocalTimeMath {

    private static final int HALF_AN_HOUR_TO_ADD = 30;
    private static final int ONE_HOUR_TO_ADD = 1;
    private static final String LAST_TIME_OF_DELIVERY = "23:00";
    private static final String FIRST_TIME_OF_DELIVERY = "09:00";
    private static final String MIDNIGHT = "00:00";

    private static final LocalTime t8_00 = LocalTime.parse("08:00");
    private static final LocalTime _08_59 = LocalTime.parse("08:59");
    private static final LocalTime _09_00 = LocalTime.parse(FIRST_TIME_OF_DELIVERY);
    private static final LocalTime t15_00 = LocalTime.parse("15:00");
    private static final LocalTime _23_00 = LocalTime.parse(LAST_TIME_OF_DELIVERY);
    private static final LocalTime _22_30 = LocalTime.parse("22:30");
    private static final LocalTime _00_00 = LocalTime.parse(MIDNIGHT);
    private static final LocalTime _23_59 = LocalTime.parse("23:59");

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

    public void timeCalculator(LocalTime currentTimeMinutes) {

        System.out.println("time = " + currentTimeMinutes);

        LocalTime currentHourAndAHalf = currentTimeMinutes
                .truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(HALF_AN_HOUR_TO_ADD);

//        LocalTime currentHourAndAHalf = LocalTime.now()
//                .truncatedTo(ChronoUnit.HOURS)
//                .plusMinutes(HALF_AN_HOUR_TO_ADD);

//        LocalTime currentTimeMinutes = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        LocalTime timeVariant = null;
        if (currentTimeMinutes.isAfter(_22_30) && currentTimeMinutes.isBefore(_23_59)) {
            timeVariant = null;
        } else if ((currentTimeMinutes.isAfter(_00_00) && currentTimeMinutes.isBefore(_08_59))
                || currentTimeMinutes.compareTo(_00_00) == 0) {
            timeVariant = LocalTime.parse(FIRST_TIME_OF_DELIVERY);
        } else if ((currentTimeMinutes.isAfter(_09_00) && currentTimeMinutes.isBefore(_22_30))
                || currentTimeMinutes.compareTo(_09_00) == 0
                || currentTimeMinutes.compareTo(_22_30) == 0) {
            if (currentTimeMinutes.isAfter(currentHourAndAHalf)) {
                timeVariant = currentTimeMinutes
                        .truncatedTo(ChronoUnit.HOURS)
                        .plusHours(2 * ONE_HOUR_TO_ADD);
            } else {
                timeVariant = currentTimeMinutes.
                        truncatedTo(ChronoUnit.HOURS)
                        .plusHours(ONE_HOUR_TO_ADD);
            }
        }

        if (Objects.isNull(timeVariant)) {
            System.out.println("no time available");
            return;
        }

        List<LocalTime> result = new ArrayList<>();
        result.add(timeVariant);
        while (timeVariant.isBefore(_23_00)) {
            timeVariant = timeVariant.plusHours(ONE_HOUR_TO_ADD);
            result.add(timeVariant);
        }

        for (LocalTime localTime : result) {
            System.out.println(localTime);
        }
    }
}
