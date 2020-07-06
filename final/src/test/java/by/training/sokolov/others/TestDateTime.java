package by.training.sokolov.others;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TestDateTime {

    @Test
    public void testLocalTime() {
//        String date = LocalDate.now().toString();
//        System.out.println(date);

        String minutesHours = "15:00";
        String seconds = ":00";
        String dateString = LocalDate.now().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(dateString + " " + minutesHours + seconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Timestamp timestamp = new Timestamp(date.getTime());

        System.out.println(timestamp);
        System.out.println(date);
    }
}
