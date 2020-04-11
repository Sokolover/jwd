package service;

import enums.Genre;
import model.Library;
import model.Publication;
import org.apache.log4j.Logger;

import java.util.List;

import static enums.Genre.fromString;

public class SimpleLibraryService implements LibraryService {

    private final static Logger logger = Logger.getLogger(SimpleLibraryService.class.getName());

    @Override
    public String getPageAmountByGenre(Library library, String requestParamValue) {

        Genre requestParamType = fromString(requestParamValue);
        logger.info("get request param type");
        int pageAmount = countPagesByGenre(library, requestParamType);
        logger.info("get page amount by genre");
        return generateResponseString(requestParamType, pageAmount);
    }

    @Override
    public List<Publication> findAllPublications(Library library) {
        logger.info("find all publications");
        return library.getPublicationList();
    }

    private String generateResponseString(Genre requestParamType, int pageAmount) {

        String result;
        if (pageAmount != 0) {
            result = "Books with genre " + requestParamType + " have " + pageAmount + " pages";
            logger.info("generateResponseString() return Answer String");
        } else {
            logger.info("generateResponseString() return Empty String");
            result = "";
        }
        return result;
    }

    private int countPagesByGenre(Library library, Genre requestParamType) {
        logger.info("count pages by genre");
        int pageAmount = 0;
        for (Publication publication : library.getPublicationList()) {
            if (requestParamType == publication.getGenre()) {
                pageAmount += publication.getPageAmount();
            }
        }
        return pageAmount;
    }

}
