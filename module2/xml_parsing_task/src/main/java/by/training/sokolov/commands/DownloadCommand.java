package by.training.sokolov.commands;

import by.training.sokolov.service.GemService;
import by.training.sokolov.service.GemsXmlBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownloadCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DownloadCommand.class.getName());
    private GemService service;

    DownloadCommand(GemService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        GemsXmlBuilder builder = new GemsXmlBuilder(service.findAll());
        String answer = builder.build();
        String fileName = "gems.xml";
        response.setContentType("text/xml");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        try {
            response.getOutputStream().print(answer);
        } catch (IOException e) {
            String msg = "error in sending response";
            LOGGER.error(msg);
            return msg;
        }
        return "successful download";
    }
}
