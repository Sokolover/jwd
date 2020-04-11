package service;

import model.Library;
import model.Publication;

import java.util.List;

public interface LibraryService {

    String getPageAmountByGenre(Library library, String requestParamValue);

    List<Publication> findAllPublications(Library library);
}
