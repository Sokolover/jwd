package by.training.sokolov.task3.model;

// Алмазный фонд,
// Visual parameters -> делаем не просто один класс VisualParameter,
// а под каждый параметр свой + базовая абстракция.
// Этакое искусственное наследование

public class Gem {

    private String id;
    private String name;
    private String preciousness;
    private String origin;
    private VisualParams visualParams;
    private int value;

    public Gem() {
        visualParams = new VisualParams();
    }

    public Gem(String id, String name, String preciousness, String origin, String color, int transparency, int numberOfFaces, int value) {
        this.id = id;
        this.name = name;
        this.preciousness = preciousness;
        this.origin = origin;
        this.visualParams.setColor(color);
        this.visualParams.setTransparency(transparency);
        this.visualParams.setNumberOfFaces(numberOfFaces);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Gem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", preciousness='" + preciousness + '\'' +
                ", origin='" + origin + '\'' +
                ", visualParams=" + visualParams +
                ", value=" + value +
                '}';
    }

    public static class VisualParams {

        private String color;
        private int transparency;
        private int numberOfFaces;

        @Override
        public String toString() {
            return "VisualParams{" +
                    "color='" + color + '\'' +
                    ", transparency=" + transparency +
                    ", numberOfFaces=" + numberOfFaces +
                    '}';
        }

        String getColor() {
            return color;
        }

        void setColor(String color) {
            this.color = color;
        }

        int getTransparency() {
            return transparency;
        }

        void setTransparency(int transparency) {
            this.transparency = transparency;
        }

        int getNumberOfFaces() {
            return numberOfFaces;
        }

        void setNumberOfFaces(int numberOfFaces) {
            this.numberOfFaces = numberOfFaces;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreciousness() {
        return preciousness;
    }

    public void setPreciousness(String preciousness) {
        this.preciousness = preciousness;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getColor() {
        return visualParams.getColor();
    }

    public void setColor(String color) {
        this.visualParams.setColor(color);
    }

    public int getTransparency() {
        return visualParams.getTransparency();
    }

    public void setTransparency(int transparency) {
        this.visualParams.setTransparency(transparency);
    }

    public int getNumberOfFaces() {
        return visualParams.getNumberOfFaces();
    }

    public void setNumberOfFaces(int numberOfFaces) {
        this.visualParams.setNumberOfFaces(numberOfFaces);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
