package by.training.sokolov.oop.simple_objects.task7;

import java.util.List;

class Triangle {

    private List<FlatPoint> coordinateList;

    Triangle(List<FlatPoint> coordinateList) {
        this.coordinateList = coordinateList;
    }

    List<FlatPoint> getCoordinateList() {
        return coordinateList;
    }
}
