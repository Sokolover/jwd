package dal;

import enums.Genre;
import enums.PublicationType;
import model.Publication;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static enums.Genre.*;
import static enums.PublicationType.*;

class DataGenerator {

    private final static Logger logger = Logger.getLogger(DataGenerator.class.getName());

    List<Publication> generatePublications() {
        List<Publication> publicationList = new ArrayList<>();
        logger.info("generate new publication list");
        for (int i = 0; i < 10; i++) {
            logger.info("create " + i + " publication");
            long id = i + 1;
            String name = "Publication_" + id;
            PublicationType type;
            switch (i % PublicationType.values().length) {
                case 0:
                    type = BOOK;
                    break;
                case 1:
                    type = ALBUM;
                    break;
                case 2:
                    type = MAGAZINE;
                    break;
                default:
                    type = null;
            }
            Genre genre;
            switch (i % Genre.values().length) {
                case 0:
                    genre = ACTION;
                    break;
                case 1:
                    genre = ADVENTURE;
                    break;
                case 2:
                    genre = HUMOR;
                    break;
                default:
                    genre = null;
            }
            int pageAmount = i * 10;
            publicationList.add(new Publication(id, name, type, genre, pageAmount));
        }

        return publicationList;
    }
}
