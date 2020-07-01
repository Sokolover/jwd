package by.training.sokolov.db;

import org.apache.log4j.Logger;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static by.training.sokolov.db.constants.PropertyName.FILE_NAME;
import static java.util.stream.Collectors.toMap;

final class PropertyHolder {

    private static Map<String, String> dataBaseProperties;
    private final static Logger LOGGER = Logger.getLogger(PropertyHolder.class.getName());

    static Map<String, String> getProperties() {
        if (dataBaseProperties == null) {
            loadProperties();
        }
        return dataBaseProperties;
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertyHolder.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            dataBaseProperties = properties.entrySet()
                    .stream()
                    .collect(toMap(
                            entry -> (String) entry.getKey(),
                            entry -> (String) entry.getValue()));

            LOGGER.info("Properties has been loaded");
        } catch (IOException ex) {
            LOGGER.error("Properties can't be loaded");
            throw new InternalServerErrorException(ex);
        }
    }
}