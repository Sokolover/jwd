package by.training.sokolov.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;

public class RegexTest {

    @Test
    public void shouldVerifyPhoneNumber() {

        Pattern pattern2 = Pattern.compile("^\\+\\d{12}$");
        Matcher matcher = pattern2.matcher("+375297577197");
        assertTrue(matcher.matches());
    }
}
