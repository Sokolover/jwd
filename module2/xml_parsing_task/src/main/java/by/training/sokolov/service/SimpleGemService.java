package by.training.sokolov.service;

import by.training.sokolov.dal.GemDao;
import by.training.sokolov.model.Gem;
import org.apache.log4j.Logger;

import java.util.List;

public class SimpleGemService implements GemService {

    private final static Logger LOGGER = Logger.getLogger(SimpleGemService.class.getName());
    private GemDao gemDao;

    public SimpleGemService(GemDao gemDao) {
        this.gemDao = gemDao;
    }

    @Override
    public void saveAll(List<Gem> gems) {

        LOGGER.info("saveAll() - SimpleGemService");
        this.gemDao.saveAll(gems);
    }

    @Override
    public List<Gem> findAll() {

        LOGGER.info("findAll() - SimpleGemService");
        return gemDao.findAll();
    }

    @Override
    public void removeAll() {

        LOGGER.info("removeAll() - SimpleGemService");
        gemDao.deleteAll();
    }

}
