package by.training.sokolov.dal;

import by.training.sokolov.model.Library;
import by.training.sokolov.model.Publication;
import org.apache.log4j.Logger;

import java.util.List;

public class LibraryDao {

    private final static Logger logger = Logger.getLogger(LibraryDao.class.getName());

    public Library buildLibrary() {

        logger.info("created new library");
        List<Publication> publicationList = new DataGenerator().generatePublications();
        return new Library(publicationList);
    }
}
