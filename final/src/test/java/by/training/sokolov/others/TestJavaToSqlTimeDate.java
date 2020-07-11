package by.training.sokolov.others;

import by.training.sokolov.core.service.TimeService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestJavaToSqlTimeDate {

    private static final int HALF_AN_HOUR = 30;
    private static final int ONE_HOUR = 1;
    private static final int ONE_MINUTE = 1;
    private static final String TIME_09_00 = "09:00";
    private static final String TIME_23_00 = "23:00";
    private static final String TIME_00_00 = "00:00";

    @Test
    public void test1() {
        String timeOfDeliveryMinutes = "23:00";
        String dateString = LocalDate.now().toString();
        LocalDateTime date = null;
        date = LocalDateTime.parse(dateString + " " + timeOfDeliveryMinutes);
//        LocalDateTime fullTimeOfDelivery = ne(date.getLong());
        System.out.println(date);
    }

    @Test
    public void test2() {
//        LocalDateTime localDateTime = LocalDateTime.now();
//        localDateTime.truncatedTo(ChronoUnit.MINUTES);
//        System.out.println(localDateTime.truncatedTo(ChronoUnit.MINUTES));
//        System.out.println(localDateTime.getHour());


        String now = "2016-11-09 10:30";

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter1);

        System.out.println("Before : " + now);

        System.out.println("After : " + formatDateTime);

        System.out.println("After : " + formatDateTime.format(formatter2));

        System.out.println("After : " + formatDateTime.format(formatter3));

        System.out.println(LocalDate.now().toString());

        LocalDate currentDate = LocalDate.now();
        LocalDateTime FIRST_TIME_OF_DELIVERY = LocalDateTime.parse(currentDate + "T" + TIME_09_00);

        System.out.println(FIRST_TIME_OF_DELIVERY);

//        LocalDate currentDate = LocalDate.now();

        LocalDateTime localDateTime = LocalDateTime.parse(currentDate + "T" + TIME_00_00);

        System.out.println(localDateTime.plusDays(1));
    }

    @Test
    public void test4() {
//        List<LocalDateTime> localDateTimeList = TimeService.findTimeVariants();
//        List<LocalDateTime> formatLocalDateTimeList = new ArrayList<>();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//        for(LocalDateTime localDateTime : localDateTimeList){
//            LocalDateTime format = localDateTime.format(formatter);
//            formatLocalDateTimeList.add(format);
//        }
//        for (LocalDateTime localDateTime : formatLocalDateTimeList){
//            System.out.println(localDateTime);
//        }

        List<LocalDateTime> localDateTimeList = TimeService.findTimeVariants();
        List<LocalDateTime> formatLocalDateTimeList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (LocalDateTime localDateTime : localDateTimeList) {
            formatLocalDateTimeList.add(LocalDateTime.parse(localDateTime.toString(), formatter));
        }
    }

}
