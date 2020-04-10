package by.training.sokolov.oop.basic_of_oop.task5.model;

import java.util.List;

public class Composition {

    private List<Flower> flowerList;
    private List<Wrapper> wrapperList;

    public Composition(List<Flower> flowerList, List<Wrapper> wrapperList) {
        this.flowerList = flowerList;
        this.wrapperList = wrapperList;
    }

    public List<Flower> getFlowerList() {
        return flowerList;
    }


    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

}
