package by.training.sokolov.oop.agregation_and_composition.task1.builder;

import by.training.sokolov.oop.agregation_and_composition.task1.model.Sentence;
import by.training.sokolov.oop.agregation_and_composition.task1.model.Word;

import java.util.List;

public class SentenceBuilder {

    public static Sentence generateSentence(List<Word> words) {

        Sentence sentence = new Sentence();
        Word firstWord = words.get(0);
        firstWord.firstUpperCase();

        for (int i = 0; i < words.size(); i += 2) {
//            sentence.setContent(sentence.getContent().concat(" "));
            words.add(i, new Word(" "));
//            sentence.setContent(sentence.getContent().concat(word.getContent()));

        }
//        sentence.setContent(sentence.getContent().concat("."));
        words.add(new Word(".\n"));
        sentence.setWords(words);

        return sentence;
    }

}
