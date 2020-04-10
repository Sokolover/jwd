package by.training.sokolov.oop.basic_of_oop.task5;

import by.training.sokolov.oop.basic_of_oop.task5.builder.CompositionBuilder;
import by.training.sokolov.oop.basic_of_oop.task5.model.Composition;
import by.training.sokolov.oop.basic_of_oop.task5.presentation.CompositionUI;

public class Main {

    public static void main(String[] args) {

        CompositionUI compositionUI = new CompositionUI();
        compositionUI.showTask();

        Composition composition = CompositionBuilder.buildComposition();
        compositionUI.presentComposition(composition);
    }

}
