package by.training.sokolov.task2.service;

import by.training.sokolov.task2.command.TypeNotExistException;
import by.training.sokolov.task2.dal.Library;
import by.training.sokolov.task2.enums.Genre;
import by.training.sokolov.task2.enums.PublicationType;
import by.training.sokolov.task2.model.Publication;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.task2.enums.Genre.fromString;

public class SimpleLibraryService implements LibraryService {

    private final static Logger LOGGER = Logger.getLogger(SimpleLibraryService.class.getName());
    private Library library;

    @Override
    public String getPageAmountByGenre(Library library, String requestParamValue) {

        Genre requestParamType = null;
        try {
            requestParamType = fromString(requestParamValue);
        } catch (TypeNotExistException e) {
            LOGGER.error("Invalid genre! " + e);
        }
        LOGGER.info("get request param type");
        int pageAmount = countPagesByGenre(library, requestParamType);
        LOGGER.info("get page amount by genre");
        return generateResponseString(requestParamType, pageAmount);
    }

    @Override
    public List<Publication> findAllPublications(Library library) {
        LOGGER.info("find all publications");
        return library.getPublicationList();
    }

    @Override
    public List<Publication> createPublicationListFromFile(String[] publicationParamList) {

        List<Publication> publicationList = new ArrayList<>();

        for (String publicationParam : publicationParamList) {
            Publication publication = buildPublicationFromFile(publicationParam);
            publicationList.add(publication);
        }

        return publicationList;
    }

    @Override
    public Publication buildPublicationFromFile(String info) {

        //TODO: может сделать тут один большой try-catch?

        String delimiter = ";";
        String[] params = info.split(delimiter);

        long id = 0;
        try {
            id = Long.parseLong(params[0]);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid id format! " + e);
        }

        String name = params[1];

        PublicationType type = null;
        try {
            type = PublicationType.fromString(params[2]);
        } catch (TypeNotExistException e) {
            LOGGER.error("Invalid publication type! " + e);
        }

        Genre genre = null;
        try {
            genre = Genre.fromString(params[3]);
        } catch (TypeNotExistException e) {
            LOGGER.error("Invalid genre! " + e);
        }

        int pageAmount = 0;
        try {
            pageAmount = Integer.parseInt(params[4]);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid id format! " + e);
        }

        return new Publication(id, name, type, genre, pageAmount);
    }

    private String generateResponseString(Genre requestParamType, int pageAmount) {

        String result;
        if (pageAmount != 0) {
            result = "Books with genre " + requestParamType + " have " + pageAmount + " pages";
            LOGGER.info("generateResponseString() return Answer String");
        } else {
            LOGGER.info("generateResponseString() return Empty String");
            result = "";
        }
        return result;
    }

    private int countPagesByGenre(Library library, Genre requestParamType) {
        LOGGER.info("count pages by genre");
        int pageAmount = 0;
        for (Publication publication : library.getPublicationList()) {
            if (requestParamType == publication.getGenre()) {
                pageAmount += publication.getPageAmount();
            }
        }
        return pageAmount;
    }

    @Override
    public Library getLibrary() {
        return library;
    }

    @Override
    public void setLibrary(Library library) {
        this.library = library;
    }
}
