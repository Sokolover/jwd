package by.training.sokolov.oop.simple_objects.task7;

public class Main {

    public static void main(String[] args) {
        System.out.println("7. Описать класс, представляющий треугольник. \n" +
                "Предусмотреть методы для: \n" +
                " - создания объектов, \n" +
                " - вычисления площади, \n" +
                " - периметра \n" +
                " - точки пересечения медиан.\n");

        TriangleService triangleService = new SimpleTriangleService();

        Triangle triangle = triangleService.createTriangle();

        System.out.println("Периметр = " + triangleService.calculatePerimeter(triangle));
        System.out.println("Площадь = " + triangleService.calculateSquare(triangle));
        triangleService.calculateMedianIntersectionPoint(triangle);
    }
}
