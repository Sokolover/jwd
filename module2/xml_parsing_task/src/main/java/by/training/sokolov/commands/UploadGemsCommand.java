package by.training.sokolov.commands;

import by.training.sokolov.model.Gem;
import by.training.sokolov.parser.*;
import by.training.sokolov.service.GemService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.util.List;

import static by.training.sokolov.contants.GemAppConstants.QUERY_KEY_PARSER_NAME;
import static by.training.sokolov.contants.GemAppConstants.TMP_DIR;

public class UploadGemsCommand extends AbstractCommand {

    private final static Logger LOGGER = Logger.getLogger(UploadGemsCommand.class.getName());

    private GemService gemService;

    public UploadGemsCommand(GemService gemService) {
        this.gemService = gemService;
    }

    @Override
    protected String executeWrapped(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Part xmlFile = req.getPart("uploadfile");
        String fileName = getFileName(xmlFile);
        String filePath = TMP_DIR + File.separator + fileName;
        xmlFile.write(filePath);

        if (!XmlValidator.isValid(filePath)) {
            String msg = "XML is NOT corresponds to XSD";
            LOGGER.error(msg);
            return msg;
        }

        String parserStringName = req.getParameter(QUERY_KEY_PARSER_NAME);
        ParserName parserName = ParserName.fromString(parserStringName);

        List<Gem> gems;
        FileParser<Gem> parser = null;
        switch (parserName) {
            case DOM:
                parser = new DomFileParser();
                break;
            case SAX:
                parser = new SaxFileParser();
                break;
            case STAX:
                parser = new StaxFileParser();
                break;
        }

        ParseResult<Gem> parseResult = parser.parse(filePath);
        gems = parseResult.getParseResults();
        String messageFromParser = parseResult.getMessage();

        LOGGER.info("add new records to table");
        this.gemService.saveAll(gems);

        return messageFromParser;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
