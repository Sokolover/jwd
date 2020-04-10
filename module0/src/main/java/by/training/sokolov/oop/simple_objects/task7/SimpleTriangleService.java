package by.training.sokolov.oop.simple_objects.task7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SimpleTriangleService implements TriangleService {

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    private static FlatPoint createPoint() {
        System.out.println("X = ");
        int x = enterInt();
        System.out.println("Y = ");
        int y = enterInt();
        return new FlatPoint(x, y);
    }

    public Triangle createTriangle() {

        System.out.println("Введите x1, y1: ");
        FlatPoint flatPoint1 = createPoint();
        System.out.println("Введите x2, y2: ");
        FlatPoint flatPoint2 = createPoint();
        System.out.println("Введите x3, y3: ");
        FlatPoint flatPoint3 = createPoint();

        List<FlatPoint> coordinates = new ArrayList<>(Arrays.asList(flatPoint1, flatPoint2, flatPoint3));

        return new Triangle(coordinates);
    }

    private double[] calculateSidesLength(List<FlatPoint> coordinates) {

        FlatPoint flatPoint1 = coordinates.get(0);
        FlatPoint flatPoint2 = coordinates.get(1);
        FlatPoint flatPoint3 = coordinates.get(2);

        int x1 = flatPoint1.getX();
        int y1 = flatPoint1.getY();
        int x2 = flatPoint2.getX();
        int y2 = flatPoint2.getY();
        int x3 = flatPoint3.getX();
        int y3 = flatPoint3.getY();

        double A = sqrt(pow((x2 - x1), 2) + pow((y2 - y1), 2));
        double B = sqrt(pow((x3 - x2), 2) + pow((y2 - y1), 2));
        double C = sqrt(pow((x1 - x3), 2) + pow((y1 - y3), 2));

        return new double[]{A, B, C};
    }

    @Override
    public double calculateSquare(Triangle triangle) {
        double[] sideLengthList = calculateSidesLength(triangle.getCoordinateList());

        double halfPerimeter = calculatePerimeter(triangle) / 2;

        return sqrt(halfPerimeter *
                (halfPerimeter - sideLengthList[0]) *
                (halfPerimeter - sideLengthList[1]) *
                (halfPerimeter - sideLengthList[2]));
    }

    @Override
    public double calculatePerimeter(Triangle triangle) {

        double[] sideLengthList = calculateSidesLength(triangle.getCoordinateList());

        int perimeter = 0;
        for (double sideLength : sideLengthList) {
            perimeter += sideLength;
        }

        return perimeter;
    }

    @Override
    public void calculateMedianIntersectionPoint(Triangle triangle) {

        FlatPoint flatPoint1 = triangle.getCoordinateList().get(0);
        FlatPoint flatPoint2 = triangle.getCoordinateList().get(1);
        FlatPoint flatPoint3 = triangle.getCoordinateList().get(2);

        int x1 = flatPoint1.getX();
        int y1 = flatPoint1.getY();
        int x2 = flatPoint2.getX();
        int y2 = flatPoint2.getY();
        int x3 = flatPoint3.getX();
        int y3 = flatPoint3.getY();

        double intersectionX = (x1 + x2 + x3) / 3;
        double intersectionY = (y1 + y2 + y3) / 3;

        System.out.println("Точка пересечения медиан: [" + intersectionX + " , " + intersectionY + "]");
    }
}
