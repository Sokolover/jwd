package by.training.sokolov.jdbc;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    private static Timestamp convert(Date date) {
        return new Timestamp(date.getTime());
    }

    private static Date convert1(Timestamp timestamp) {
        return new Date(String.valueOf(timestamp.toString()));
    }

//    @Test
//    public void test1(){
//
//        Timestamp timestamp = new Timestamp(new Date().getTime());
//        System.out.println(timestamp);
//        System.out.println(convert1(timestamp));
//    }

    @Test
    public void test() {


        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat datetimeFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        datetimeFormatter.format(date);
        System.out.println(datetimeFormatter.format(date));
        Timestamp timestamp = convert(date);
        System.out.println(timestamp);
    }
}
