package task2.controller;

import by.training.sokolov.task2.controller.ControllerValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ControllerValidatorTest {

    private ControllerValidator controllerValidator;

    @Before
    public void setUp() {
        this.controllerValidator = new ControllerValidator();
    }

    @Test
    public void isValidateUrl() {
        Map<String, String> map = createValidateMap();

        boolean actual = controllerValidator.isValidateUrl(map);

        assertTrue(actual);
    }

    @Test
    public void isNotValidateUrl() {
        Map<String, String> map = createNotValidateMap();

        boolean actual = controllerValidator.isValidateUrl(map);

        assertFalse(actual);
    }

    private Map<String, String> createNotValidateMap() {
        Map<String, String> map = new HashMap<>();
        map.put("PATH", "D://test.csv");
        map.put("GENRE_COUNT","GENRE_COUNT");
        map.put("SORT_BY","SORT_BY_NAME");
        map.put("GENRE","humor");
        map.put("SORT_DIRECTION","descAnding");
        map.put("FILE_READ","FILE_READ");
        return map;
    }

    private Map<String, String> createValidateMap() {
        Map<String, String> map = new HashMap<>();
        map.put("PATH", "D://test.csv");
        map.put("GENRE_COUNT","GENRE_COUNT");
        map.put("SORT_BY","SORT_BY_NAME");
        map.put("GENRE","humor");
        map.put("SORT_DIRECTION","descEnding");
        map.put("FILE_READ","FILE_READ");
        return map;
    }
}