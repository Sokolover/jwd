package by.training.sokolov.task2.dal;

import by.training.sokolov.task2.model.Publication;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LibraryDao {

    private final static Logger LOGGER = Logger.getLogger(LibraryDao.class.getName());
    private List<Publication> publicationList;

    public LibraryDao() {
        this.publicationList = new ArrayList<>();
    }

    public void saveAll(List<Publication> publicationList) {
        LOGGER.info("saveAll() Dao method");
        this.publicationList.addAll(publicationList);
    }

    public void sortPublicationsByNameAscending() {
        publicationList.sort(Publication.PublicationNameComparatorAscending);
    }

    public void sortPublicationsByNameDescending() {
        publicationList.sort(Publication.PublicationNameComparatorDescending);
    }

    public List<Publication> getPublicationList() {
        return publicationList;
    }

    public void setPublicationList(List<Publication> publicationList) {
        this.publicationList = publicationList;
    }

}
