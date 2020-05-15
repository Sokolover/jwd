package by.training.sokolov.controller.commands;

import by.training.sokolov.controller.validators.XmlValidator;
import by.training.sokolov.model.Gem;
import by.training.sokolov.service.GemService;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Collection;
import java.util.List;

import static by.training.sokolov.contants.GemAppConstants.TMP_DIR;

public class DomParsingCommand implements Command {


    private final static Logger LOGGER = Logger.getLogger(DomParsingCommand.class.getName());
    private GemService service;

    DomParsingCommand(GemService service) {
        this.service = service;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("*** DOM parser works ***");
        //todo сделать наследование GemParsingCommand и вынести общие методы для считывания файла и валидации xsd
        String filePath = null;
        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals("uploadfile")) {
                    String fileName = getFileName(part);
                    filePath = TMP_DIR + File.separator + fileName;
                    part.write(filePath);
                }
            }
        } catch (IOException e) {
            String msg = "IOException during getting file name";
            LOGGER.error(msg, e);
            return msg;
        } catch (ServletException e) {
            String msg = "ServletException during getting file name";
            LOGGER.error(msg, e);
            return msg;
        }

        if (!XmlValidator.checkXMLbyXSD(filePath)) {
            String msg = "XML is NOT corresponds to XSD";
            LOGGER.error(msg);
            return msg;
        }

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            String msg = "cannot find file";
            LOGGER.error(msg, e);
            return msg;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            String msg = "newDocumentBuilder() exception in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        }

        try {
            document = builder.parse(inputStream);
        } catch (SAXException e) {
            String msg = "SAXException while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (IOException e) {
            String msg = "IOException while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (Exception e) {
            String msg = "Exception while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        }

        List<Gem> gems;
        try {
            gems = service.inMemoryDom(document);
        } catch (SAXException e) {
            String msg = "SAXException while parsing document in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (IOException e) {
            String msg = "IOException while parsing document in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (Exception e) {
            String msg = "Exception while parsing document in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        }

        LOGGER.info("remove all records from table");
        service.removeAll();
        LOGGER.info("add new records to table");
        service.saveAll(gems);

        String msg = "got info from file";
        LOGGER.info(msg);

        return createGemHtmlTable();
    }

    private String createGemHtmlTable() {

        StringBuilder response = new StringBuilder();

        response.append("<table border=\"1\">\n" +
                "    <caption>Gems table</caption>\n" +
                "    <tr>\n" +
                "        <th rowspan=\"2\" class=\"first\">id</th>\n" +
                "        <th rowspan=\"2\">Name</th>\n" +
                "        <th rowspan=\"2\">Preciousness</th>\n" +
                "        <th rowspan=\"2\">Origin</th>\n" +
                "        <th colspan=\"3\">Visual parameters</th>\n" +
                "        <th rowspan=\"2\">Value</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th class=\"first\">Color</th>\n" +
                "        <th class=\"first\">Transparency</th>\n" +
                "        <th class=\"first\">Number of faces</th>\n" +
                "    </tr>\n");

        List<Gem> gems = service.findAll();

        for (Gem gem : gems) {
            response.append("<tr><td>")
                    .append(gem.getId())
                    .append("</td><td>")
                    .append(gem.getName())
                    .append("</td><td>")
                    .append(gem.getPreciousness())
                    .append("</td><td>")
                    .append(gem.getOrigin())
                    .append("</td><td>")
                    .append(gem.getColor())
                    .append("</td><td>")
                    .append(gem.getTransparency())
                    .append("</td><td>")
                    .append(gem.getNumberOfFaces())
                    .append("</td><td>")
                    .append(gem.getValue())
                    .append("</td></tr>");
        }

        return new String(response);
    }

}
