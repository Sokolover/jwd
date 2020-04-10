package by.training.sokolov.oop.agregation_and_composition.task1.builder;

import by.training.sokolov.oop.agregation_and_composition.task1.model.Word;

public class WordBuilder {

    public static Word generateWord(String content) {

        return new Word(content);
    }
}
