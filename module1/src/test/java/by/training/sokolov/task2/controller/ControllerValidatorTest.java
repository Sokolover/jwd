package by.training.sokolov.task2.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ControllerValidatorTest {

    private ControllerValidator controllerValidator;

    @Before
    public void setUp() {
        this.controllerValidator = new ControllerValidator();
    }

    @Test
    public void isValidateUrl() { // todo:доделать тест
        Map<String, String> map = createMap();

        boolean actual = controllerValidator.isValidateUrl(map);

        assertTrue(actual);
    }

    private Map<String, String> createMap() {
        Map<String, String> map = new HashMap<>();
        map.put("", "D://test.csv");
        return map;
    }
}