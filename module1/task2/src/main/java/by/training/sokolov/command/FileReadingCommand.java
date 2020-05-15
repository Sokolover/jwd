package by.training.sokolov.command;

import by.training.sokolov.LibraryAppConstants;
import by.training.sokolov.model.Publication;
import by.training.sokolov.service.LibraryService;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileReadingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(FileReadingCommand.class.getName());

    private LibraryService service;

    public FileReadingCommand() {

    }

    public FileReadingCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {

        String path = requestGetMap.get(LibraryAppConstants.QUERY_KEY_PATH);
        String[] publicationParamsStringArray = readParamsFromFile(path);
        List<Publication> publicationList = saveNewPublicationListInDao(publicationParamsStringArray);
        String invalidPublicationNumbersAnswer = service.findInvalidPublicationNumbers(publicationList);

        String message = "got info from file: " + path + ". " + invalidPublicationNumbersAnswer;
        LOGGER.info(message);

        return invalidPublicationNumbersAnswer;
    }

    private List<Publication> saveNewPublicationListInDao(String[] publicationParamsStringArray) {
        service.removeAllPublicationsFromLibraryDao();
        List<Publication> publicationList = service.createPublicationListFromFile(publicationParamsStringArray);
        service.saveAll(publicationList);
        return publicationList;
    }


    private String[] readParamsFromFile(String path) {

        List<String> publicationParams = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                publicationParams.add(readLine);
            }

            fileReader.close();
        } catch (IOException e) {
            LOGGER.error("no such file on your system! " + path + " - " + e);
        }

        String[] publicationStringArray = new String[publicationParams.size()];
        for (int i = 0; i < publicationParams.size(); i++) {
            publicationStringArray[i] = publicationParams.get(i);
        }

        return publicationStringArray;
    }

    @Override
    public String getName() {
        return LibraryAppConstants.QUERY_KEY_FILE_READ_COMMAND;
    }
}
