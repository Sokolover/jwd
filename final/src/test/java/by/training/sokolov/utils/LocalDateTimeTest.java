package by.training.sokolov.utils;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTest {

    private static final Logger LOGGER = Logger.getLogger(LocalDateTimeTest.class.getName());

    private static final int HALF_AN_HOUR = 30;
    private static final int ONE_HOUR = 1;
    private static final int ONE_MINUTE = 1;
    private static final String TIME_09_00 = "09:00";
    private static final String TIME_23_00 = "23:00";
    private static final String TIME_00_00 = "00:00";

    @Test
    public void shouldParseWithoutExceptions() {

        String now = "2020-07-07 10:30";

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("MM.dd HH:mm");

        LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter1);

        LOGGER.info("Before : " + now);
        LOGGER.info("After formatter1 : " + formatDateTime);
        LOGGER.info("After formatter2 : " + formatDateTime.format(formatter2));
        LOGGER.info("After formatter3 : " + formatDateTime.format(formatter3));
        LOGGER.info("After formatter4 : " + formatDateTime.format(formatter4));
        LOGGER.info(LocalDate.now().toString());

        LocalDate currentDate = LocalDate.now();
        LocalDateTime FIRST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_09_00);
        LOGGER.info(FIRST_TIME_OF_DELIVERY);
        LocalDateTime localDateTime = LocalDateTime.parse(currentDate + "T" + TIME_00_00);
        LOGGER.info(localDateTime.plusDays(1));
    }

    @Test
    public void equTest() {
        String str1 = "pizza";
        String str2 = "pizza";

        System.out.println(str1.equals(str2));
        System.out.println(str1 == str2);

    }
}
