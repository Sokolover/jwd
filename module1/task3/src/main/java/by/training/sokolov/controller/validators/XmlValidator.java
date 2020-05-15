package by.training.sokolov.controller.validators;

import by.training.sokolov.contants.GemAppConstants;
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

public class XmlValidator {

    private final static Logger LOGGER = Logger.getLogger(XmlValidator.class.getName());

    public static boolean checkXMLbyXSD(String pathXml) {

        try {
            ClassLoader classLoader = XmlValidator.class.getClassLoader();
            File xsd = new File(Objects.requireNonNull(classLoader.getResource(GemAppConstants.XSD_PATH)).getFile());
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
