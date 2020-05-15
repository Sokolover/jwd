package by.training.sokolov.dal;

import by.training.sokolov.model.Gem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GemDao {
    private final static Logger LOGGER = Logger.getLogger(GemDao.class.getName());
    private List<Gem> gemList;

    public GemDao() {
        gemList = new ArrayList<>();
    }

    public GemDao(List<Gem> gemList) {
        this.gemList = gemList;
    }

    public void saveAll(List<Gem> gems) {
        LOGGER.info("saveAll() GemDao method");
        this.gemList.addAll(gems);
    }

    public void setGemList(List<Gem> gemList) {
        this.gemList = gemList;
    }

    public List<Gem> getGemList() {
        return gemList;
    }
}
