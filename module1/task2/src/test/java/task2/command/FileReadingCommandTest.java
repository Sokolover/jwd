package by.training.sokolov.task2.command;

import by.training.sokolov.task2.service.LibraryService;
import by.training.sokolov.task2.service.SimpleLibraryService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;

public class FileReadingCommandTest {

    private FileReadingCommand fileReadingCommand;

    @Before
    public void setUp() {
        LibraryService libraryService = new SimpleLibraryService();
        this.fileReadingCommand = new FileReadingCommand(libraryService);
    }

    @Test
    public void isValidFileContent() {
        Map<String, String> map = createMapWithValidFileRecords();

        String actual = fileReadingCommand.execute(map);

        assertThat(actual, endsWith(""));
    }

    @Test
    public void isNotValidFileContent() {
        Map<String, String> map = createMapWithInvalidFileRecords();

        String actual = fileReadingCommand.execute(map);

        assertThat(actual, endsWith("have invalid params!"));
    }

    private Map<String, String> createMapWithValidFileRecords() {

        Map<String, String> map = new HashMap<>();
        map.put("PATH", "D://testWithAllValidRecords.csv");
        map.put("GENRE_COUNT", "GENRE_COUNT");
        map.put("SORT_BY", "SORT_BY_NAME");
        map.put("GENRE", "humor");
        map.put("SORT_DIRECTION", "descEnding");
        map.put("FILE_READ", "FILE_READ");
        return map;
    }

    private Map<String, String> createMapWithInvalidFileRecords() {

        Map<String, String> map = new HashMap<>();
        map.put("PATH", "D://testWithInvalidRecords.csv");
        map.put("GENRE_COUNT", "GENRE_COUNT");
        map.put("SORT_BY", "SORT_BY_NAME");
        map.put("GENRE", "humor");
        map.put("SORT_DIRECTION", "descEnding");
        map.put("FILE_READ", "FILE_READ");
        return map;
    }


}