package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.dal.Library;
import by.training.sokolov.task2.model.Publication;
import by.training.sokolov.task2.service.LibraryService;
import by.training.sokolov.task2.service.SimpleLibraryService;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileReadingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(FileReadingCommand.class.getName());
    private final String name = LibraryAppConstants.QUERY_KEY_FILE_READ_COMMAND;

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

        LibraryService libraryService = new SimpleLibraryService();
        List<Publication> publicationList = libraryService.createPublicationListFromFile(publicationParamsStringArray);
        Library library = new Library(publicationList);
        service.setLibrary(library);

        String message = "got info from file: " + path;
        LOGGER.info(message);
        return message;
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
        return name;
    }
}
