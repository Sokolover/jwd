package by.training.sokolov.core.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeService {

    private static final int HALF_AN_HOUR = 30;
    private static final int ONE_HOUR = 1;
    private static final int ONE_MINUTE = 1;
    private static final String TIME_09_00 = "09:00";
    private static final String TIME_23_00 = "23:00";
    private static final String TIME_00_00 = "00:00";

    private static final LocalTime FIRST_TIME_OF_DELIVERY = LocalTime.parse(TIME_09_00);
    private static final LocalTime LAST_TIME_OF_DELIVERY = LocalTime.parse(TIME_23_00);
    private static final LocalTime LAST_TIME_FOR_ORDER = LAST_TIME_OF_DELIVERY.minusMinutes(HALF_AN_HOUR);
    private static final LocalTime MIDNIGHT = LocalTime.parse(TIME_00_00);

    public static List<LocalTime> countTime() {

        LocalTime currentTimeMinutes = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        LocalTime currentHourAndAHalf = currentTimeMinutes
                .truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(HALF_AN_HOUR);

        LocalTime timeVariant = null;
        if (currentTimeMinutes.isAfter(LAST_TIME_FOR_ORDER) && currentTimeMinutes.isBefore(MIDNIGHT.minusMinutes(ONE_MINUTE))) {
            timeVariant = null;
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

        if (Objects.isNull(timeVariant)) {
            return new ArrayList<>();
        }

        List<LocalTime> result = new ArrayList<>();
        result.add(timeVariant);
        while (timeVariant.isBefore(LAST_TIME_OF_DELIVERY)) {
            timeVariant = timeVariant.plusHours(ONE_HOUR);
            result.add(timeVariant);
        }

        return result;
    }
}
