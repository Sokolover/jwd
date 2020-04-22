package by.training.sokolov.task2.service;

import by.training.sokolov.task2.dal.Library;
import by.training.sokolov.task2.model.Publication;

import java.util.List;

public interface LibraryService {

    String getPageAmountByGenre(Library library, String requestParamValue);

    List<Publication> findAllPublications(Library library);

    List<Publication> createPublicationListFromFile(String[] publicationParams);

    Publication buildPublicationFromFile(String info);

    Library getLibrary();

    void setLibrary(Library library);
}
