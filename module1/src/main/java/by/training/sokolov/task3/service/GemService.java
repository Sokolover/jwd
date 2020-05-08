package by.training.sokolov.task3.service;

import by.training.sokolov.task3.dal.GemDao;
import by.training.sokolov.task3.model.Gem;

import java.util.List;

public interface GemService {

    List<Gem> createGemListFromFile(String[] publicationParams);

    Gem buildGemFromFile(String info);

    GemDao getGemDao();

    void saveAll(List<Gem> publicationList);

    List<Gem> findAll();

    void removeAll();
}
