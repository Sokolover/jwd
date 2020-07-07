package by.training.sokolov.others;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestJavaToSqlTimeDate {

    @Test
    public void test1() {
        String timeOfDeliveryMinutes = "23:00";
        String dateString = LocalDate.now().toString();
        LocalDateTime date = null;
        date = LocalDateTime.parse(dateString + " " + timeOfDeliveryMinutes);
//        LocalDateTime fullTimeOfDelivery = ne(date.getLong());
        System.out.println(date);
    }

}
