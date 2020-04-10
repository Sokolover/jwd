package service;

import model.Publication;

import java.util.List;

public interface LibraryService {

    String getPageAmountByGenre(String requestParamValue);

    List<Publication> findAllPublications();
}
