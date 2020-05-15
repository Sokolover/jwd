package by.training.sokolov.controller.commands;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static by.training.sokolov.contants.GemAppConstants.TMP_DIR;

class GemParsingCommand {
    private final static Logger LOGGER = Logger.getLogger(GemParsingCommand.class.getName());

    String getFilePath(HttpServletRequest request) {
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
        return filePath;
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
