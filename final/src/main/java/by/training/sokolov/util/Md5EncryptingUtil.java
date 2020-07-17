package by.training.sokolov.util;

import by.training.sokolov.core.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5EncryptingUtil {

    private static final Logger LOGGER = Logger.getLogger(ApplicationContext.class);

    public static String encrypt(String password) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(Md5EncryptingUtil.class.getName() + " - " + e.getMessage());
        }
        messageDigest.reset();
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String passwordHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        return passwordHash;
    }
}
