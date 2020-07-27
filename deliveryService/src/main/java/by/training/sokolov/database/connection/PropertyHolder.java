package by.training.sokolov.database.connection;

import org.apache.log4j.Logger;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static by.training.sokolov.database.connection.constants.PropertyName.FILE_NAME;
import static java.util.stream.Collectors.toMap;

final class PropertyHolder {

    private static final Logger LOGGER = Logger.getLogger(PropertyHolder.class.getName());
    private static Map<String, String> dataBaseProperties;

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

            dataBaseProperties = properties
                    .entrySet()
                    .stream()
                    .collect(toMap(
                            entry -> (String) entry.getKey(),
                            entry -> (String) entry.getValue()));

            LOGGER.info("Properties has been loaded");
        } catch (IOException e) {
            LOGGER.error("Properties can't be loaded: " + e);
            throw new InternalServerErrorException(e);
        }
    }
}
