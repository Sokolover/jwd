package by.training.sokolov.service;

import by.training.sokolov.model.Gem;

import java.util.List;

public interface GemService {

    void saveAll(List<Gem> publicationList);

    List<Gem> findAll();

    void removeAll();

}
