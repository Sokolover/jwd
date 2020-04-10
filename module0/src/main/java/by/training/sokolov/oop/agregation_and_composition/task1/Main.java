package by.training.sokolov.oop.agregation_and_composition.task1;


//1. Создать объект класса Текст,
// используя классы Предложение, Слово.
// Методы:
//  - дополнить текст,
//  - вывести на консоль текст,
//  - заголовок текста.

import by.training.sokolov.oop.agregation_and_composition.task1.builder.SentenceBuilder;
import by.training.sokolov.oop.agregation_and_composition.task1.builder.TextBuilder;
import by.training.sokolov.oop.agregation_and_composition.task1.builder.WordBuilder;
import by.training.sokolov.oop.agregation_and_composition.task1.model.Sentence;
import by.training.sokolov.oop.agregation_and_composition.task1.model.Text;
import by.training.sokolov.oop.agregation_and_composition.task1.model.Word;
import by.training.sokolov.oop.agregation_and_composition.task1.presentation.TextUI;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TextUI textUI = new TextUI();
        textUI.showTask();

        List<Sentence> sentenceList = generateSentenceList();
        Text text = TextBuilder.generateText(sentenceList);

        completeText(text);
        textUI.outputTextHeader(text);
        textUI.outputText(text);
    }

    private static void completeText(Text text) {
        if (TextUI.completeTextMenu()) {
            List<Sentence> sentenceList = generateSentenceList();
            text.completeText(sentenceList);
        }
    }

    private static List<Sentence> generateSentenceList() {

        TextUI textUI = new TextUI();

        List<Sentence> sentenceList = new ArrayList<>();
        int sentenceAmount = textUI.enterSentenceAmount();

        for (int i = 0; i < sentenceAmount; i++) {
            Sentence sentence = SentenceBuilder.generateSentence(generateWordList());
            sentenceList.add(sentence);
        }

        return sentenceList;
    }

    private static List<Word> generateWordList() {

        TextUI textUI = new TextUI();

        List<Word> wordList = new ArrayList<>();
        int wordAmount = textUI.enterWordAmount();

        for (int i = 0; i < wordAmount; i++) {
            Word word = WordBuilder.generateWord(textUI.enterWordContent());
            wordList.add(word);
        }

        return wordList;
    }
}

