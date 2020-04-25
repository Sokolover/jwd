package by.training.sokolov.task2.service;

import by.training.sokolov.task2.dal.LibraryDao;
import by.training.sokolov.task2.model.Publication;

import java.util.List;

public interface LibraryService {

    String findInvalidPublicationNumbers(List<Publication> publicationList);

    String getPageAmountByGenre(LibraryDao libraryDao, String requestParamValue);

    List<Publication> findAll(LibraryDao libraryDao);

    List<Publication> createPublicationListFromFile(String[] publicationParams);

    Publication buildPublicationFromFile(String info);

    LibraryDao getLibraryDao();

    void setLibraryDao(LibraryDao libraryDao);

    void saveAll(List<Publication> publicationList);
}
