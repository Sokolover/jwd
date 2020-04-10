package by.training.sokolov.oop.agregation_and_composition.task1.model;


//1. Создать объект класса Текст,
// используя классы Предложение, Слово.
// Методы:
//  - дополнить текст,
//  - вывести на консоль текст,
//  - заголовок текста.

import by.training.sokolov.oop.agregation_and_composition.task1.builder.WordBuilder;
import by.training.sokolov.oop.agregation_and_composition.task1.presentation.TextUI;
import by.training.sokolov.oop.agregation_and_composition.task1.service.TextService;

import java.util.List;

public class Text implements TextService {

    private List<Sentence> sentences;
    private Word header;

    @Override
    public void completeText(List<Sentence> sentences) {
        this.sentences.addAll(sentences);
    }

    @Override
    public void addTextHeader() {

        TextUI textUI = new TextUI();
        String headerContent = textUI.enterHeader();
        this.header = WordBuilder.generateWord(headerContent);
    }


    public Word getHeader() {
        return header;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }
}
