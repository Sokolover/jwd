package by.training.sokolov.task2.service;

import by.training.sokolov.task2.command.TypeNotExistException;
import by.training.sokolov.task2.dal.LibraryDao;
import by.training.sokolov.task2.enums.Genre;
import by.training.sokolov.task2.enums.PublicationType;
import by.training.sokolov.task2.model.Publication;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static by.training.sokolov.task2.enums.Genre.fromString;
import static java.util.Objects.isNull;

public class SimpleLibraryService implements LibraryService {

    private final static Logger LOGGER = Logger.getLogger(SimpleLibraryService.class.getName());
    private LibraryDao libraryDao;

    public SimpleLibraryService() {
        this.libraryDao = new LibraryDao();
    }

    @Override
    public String getPageAmountByGenre(LibraryDao libraryDao, String requestParamValue) {

        LOGGER.info("get request param type");
        Genre requestParamType = fromString(requestParamValue);

        LOGGER.info("get page amount by genre");
        int pageAmount = countPagesByGenre(libraryDao, requestParamType);

        return generateResponseString(requestParamType, pageAmount);
    }

    @Override
    public String findInvalidPublicationNumbers(List<Publication> publicationList) {

        int indexCounter = 0;
        List<Integer> indexList = new ArrayList<>();
        Iterator<Publication> iterator = publicationList.iterator();
        while (iterator.hasNext()) {
            Publication next = iterator.next();
            indexCounter++;
            if (next.equals(new Publication())) {
                indexList.add(indexCounter);
                iterator.remove();
            }
        }

        if (indexList.isEmpty()) {
            return "";
        }

        StringBuilder answer = new StringBuilder("Records with numbers [ ");
        for (Integer index : indexList) {
            answer.append(index).
                    append(", ");

        }
        answer
                .append("] have invalid params!");

        return new String(answer);
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

        String delimiter = ";";
        String[] params = info.split(delimiter);

        long id;
        try {
            id = Long.parseLong(params[0]);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid id format! " + e);
            return new Publication();
        }

        String name = params[1];

        PublicationType type;
        try {
            type = PublicationType.fromString(params[2]);
        } catch (TypeNotExistException e) {
            LOGGER.error("Invalid publication type! " + e);
            return new Publication();
        }

        Genre genre;
        if (isNull(genre = fromString(params[3]))) {
            LOGGER.error("Invalid genre in file!");
            return new Publication();
        }

        int pageAmount;
        try {
            pageAmount = Integer.parseInt(params[4]);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid id format! " + e);
            return new Publication();
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

    private int countPagesByGenre(LibraryDao libraryDao, Genre requestParamType) {

        LOGGER.info("count pages by genre");
        int pageAmount = 0;
        for (Publication publication : libraryDao.getPublicationList()) {
            if (requestParamType == publication.getGenre()) {
                pageAmount += publication.getPageAmount();
            }
        }
        return pageAmount;
    }

    @Override
    public LibraryDao getLibraryDao() {
        return libraryDao;
    }

    @Override
    public void saveAll(List<Publication> publicationList) {
        libraryDao.saveAll(publicationList);
    }

    @Override
    public void removeAllPublicationsFromLibraryDao() {
        libraryDao.setPublicationList(new ArrayList<>());
    }
}
