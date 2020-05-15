package by.training.sokolov.service;

import by.training.sokolov.dal.LibraryDao;
import by.training.sokolov.model.Publication;

import java.util.List;

public interface LibraryService {

    String findInvalidPublicationNumbers(List<Publication> publicationList);

    String getPageAmountByGenre(LibraryDao libraryDao, String requestParamValue);

    List<Publication> createPublicationListFromFile(String[] publicationParams);

    Publication buildPublicationFromFile(String info);

    LibraryDao getLibraryDao();

    void saveAll(List<Publication> publicationList);

    void removeAllPublicationsFromLibraryDao();
}
