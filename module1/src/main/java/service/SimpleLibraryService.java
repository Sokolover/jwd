package service;

import dao.LibraryDao;
import enums.Genre;
import model.Library;
import model.Publication;

import java.util.List;

import static enums.Genre.fromString;

public class SimpleLibraryService implements LibraryService {

    private Library library = new LibraryDao().buildLibrary();

    @Override
    public String getPageAmountByGenre(String requestParamValue) {

        LibraryDao libraryDao = new LibraryDao();
        library = libraryDao.buildLibrary();

        Genre requestParamType = fromString(requestParamValue);
        int pageAmount = countPagesByGenre(library, requestParamType);

        return generateResponseString(requestParamType, pageAmount);
    }

    @Override
    public List<Publication> findAllPublications() {
        return library.getPublicationList();
    }

    private String generateResponseString(Genre requestParamType, int pageAmount) {

        String result;
        if (pageAmount != 0) {
            result = "Books with genre " + requestParamType + " have " + pageAmount + " pages";
        } else {
            result = "";
        }
        return result;
    }

    private int countPagesByGenre(Library library, Genre requestParamType) {

        int pageAmount = 0;
        for (Publication publication : library.getPublicationList()) {
            if (requestParamType == publication.getGenre()) {
                pageAmount += publication.getPageAmount();
            }
        }
        return pageAmount;
    }

}
