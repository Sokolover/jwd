package by.training.sokolov.service;

import by.training.sokolov.model.Library;
import by.training.sokolov.model.Publication;

import java.util.List;

public interface LibraryService {

    String getPageAmountByGenre(Library library, String requestParamValue);

    List<Publication> findAllPublications(Library library);
}
