package by.training.sokolov.task1.service;

import by.training.sokolov.task1.model.Library;
import by.training.sokolov.task1.model.Publication;

import java.util.List;

public interface LibraryService {

    String getPageAmountByGenre(Library library, String requestParamValue);

    List<Publication> findAllPublications(Library library);
}
