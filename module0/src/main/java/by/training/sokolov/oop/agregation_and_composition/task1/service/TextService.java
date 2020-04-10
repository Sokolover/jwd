package by.training.sokolov.oop.agregation_and_composition.task1.service;

import by.training.sokolov.oop.agregation_and_composition.task1.model.Sentence;

import java.util.List;

public interface TextService {

    void completeText(List<Sentence> sentence);

    void addTextHeader();
}
