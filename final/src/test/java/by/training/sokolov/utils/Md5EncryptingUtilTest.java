package by.training.sokolov.utils;

import by.training.sokolov.util.Md5EncryptingUtil;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class Md5EncryptingUtilTest {

    @Test
    public void shouldEncryptPassword() {
        String hash = "FBD5FC9D0373D3BD981AB6E6905279DD";
        String password = "Qwerty3@123";
        String encryptResult = Md5EncryptingUtil.encrypt(password);

        System.out.println(hash);
        System.out.println(encryptResult);
        assertEquals(encryptResult, hash);
    }
}
