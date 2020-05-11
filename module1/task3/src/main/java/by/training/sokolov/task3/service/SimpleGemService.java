package by.training.sokolov.task3.service;

import by.training.sokolov.task3.dal.GemDao;
import by.training.sokolov.task3.model.Gem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SimpleGemService implements GemService {

    private final static Logger LOGGER = Logger.getLogger(SimpleGemService.class.getName());
    private GemDao gemDao;

    public SimpleGemService() {
        this.gemDao = new GemDao();
    }

    @Override
    public List<Gem> createGemListFromFile(String[] publicationParams) {
        return null;
    }

    @Override
    public Gem buildGemFromFile(String info) {
        return null;
    }

    @Override
    public GemDao getGemDao() {
        return null;
    }

    @Override
    public void saveAll(List<Gem> gems) {
        gemDao.saveAll(gems);
    }

    @Override
    public List<Gem> findAll() {
        LOGGER.info("findAll()");
        return gemDao.getGemList();
    }

    @Override
    public void removeAll() {
        LOGGER.info("removeAll()");
        gemDao.setGemList(new ArrayList<>());
    }
}
