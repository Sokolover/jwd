package by.training.sokolov.oop.basic_of_oop.task5.builder;

import by.training.sokolov.oop.basic_of_oop.task5.model.Composition;
import by.training.sokolov.oop.basic_of_oop.task5.model.Flower;
import by.training.sokolov.oop.basic_of_oop.task5.model.Wrapper;
import by.training.sokolov.oop.basic_of_oop.task5.presentation.CompositionUI;

import java.util.ArrayList;
import java.util.List;

public class CompositionBuilder {

    public static Composition buildComposition(){

        List<Flower> flowerList = createFlowerList();
        List<Wrapper> wrapperList = createWrapperList();

        return new Composition(flowerList, wrapperList);
    }

    private static List<Wrapper> createWrapperList() {
        CompositionUI compositionUI = new CompositionUI();
        List<Wrapper> wrapperList = new ArrayList<>();

        int wrapperAmount = compositionUI.enterWrapperAmount();

        for (int i = 0; i < wrapperAmount; i++) {
            wrapperList.add(WrapperBuilder.generateRandomWrapper());
        }
        return wrapperList;
    }

    private static List<Flower> createFlowerList() {
        CompositionUI compositionUI = new CompositionUI();
        List<Flower> flowerList = new ArrayList<>();

        int flowerAmount = compositionUI.enterFlowerAmount();

        for (int i = 0; i < flowerAmount; i++) {
            flowerList.add(FlowerBuilder.generateRandomFlower());
        }
        return flowerList;
    }
}
