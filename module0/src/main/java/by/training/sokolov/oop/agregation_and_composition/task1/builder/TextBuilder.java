package by.training.sokolov.oop.agregation_and_composition.task1.builder;

import by.training.sokolov.oop.agregation_and_composition.task1.model.Sentence;
import by.training.sokolov.oop.agregation_and_composition.task1.model.Text;

import java.util.List;

public class TextBuilder {

    public static Text generateText(List<Sentence> sentences) {

        Text text = new Text();
        text.addTextHeader();
        text.setSentences(sentences);

        return text;
    }
}
