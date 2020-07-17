package by.training.sokolov.util;

import java.time.LocalDateTime;

import static java.time.LocalDate.now;

final class TimeConstants {

    static final int HALF_AN_HOUR = 30;
    static final int ONE_HOUR = 1;
    static final int TWO_HOURS = 2;
    static final int ONE_MINUTE = 1;
    static final String TIME_23_00_PM = "23:00";
    private static final String TIME_09_00_PM = "09:00";
    static final LocalDateTime THIS_DAY_FIRST_TIME_OF_DELIVERY = LocalDateTime.parse(now() + "T" + TIME_09_00_PM);
    private static final String TIME_22_30_PM = "22:30";
    static final LocalDateTime THIS_DAY_LAST_TIME_FOR_ORDER = LocalDateTime.parse(now() + "T" + TIME_22_30_PM);
    private static final String TIME_23_59_PM = "23:59";
    static final LocalDateTime THIS_DAY_23_59_PM = LocalDateTime.parse(now() + "T" + TIME_23_59_PM);
    private static final String TIME_00_00_PM = "00:00";
    static final LocalDateTime THIS_DAY_MIDNIGHT = LocalDateTime.parse(now() + "T" + TIME_00_00_PM);
}
