package by.training.sokolov.task2.enums;

public enum Genre {
    ACTION("action"),
    ADVENTURE("adventure"),
    HUMOR("humor"),
    DEFAULT("");

    private final String genreName;

    Genre(String s) {
        this.genreName = s;
    }

    public static Genre fromString(String name) {

        final Genre[] values = Genre.values();
        for (Genre genre : values) {
            if (genre.genreName.equals(name) || genre.name().equals(name)) {
                return genre;
            }
        }
        return null;
    }
}
