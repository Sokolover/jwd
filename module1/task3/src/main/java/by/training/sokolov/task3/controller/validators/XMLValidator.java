package by.training.sokolov.task3.controller.validators;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static by.training.sokolov.task3.GemAppConstants.XSD_PATH;

public class XMLValidator {

    private final static Logger LOGGER = Logger.getLogger(XMLValidator.class.getName());

    public static boolean checkXMLbyXSD(String pathXml) {

        try {
            ClassLoader classLoader = XMLValidator.class.getClassLoader();
            File xsd = new File(Objects.requireNonNull(classLoader.getResource(XSD_PATH)).getFile());
            File xml = new File(pathXml);

            if (!xml.exists()) {
                LOGGER.info("haven't found XML " + pathXml);
                return false;
            }

            if (!xsd.exists()) {
                LOGGER.info("haven't found XSD " + xsd);
                return false;
            }

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathXml));
            LOGGER.info("XML corresponds to XSD.");
            return true;

        } catch (SAXException | IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

    }
}
