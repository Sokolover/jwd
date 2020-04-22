package by.training.sokolov.task2.enums;

import by.training.sokolov.task2.command.TypeNotExistException;

public enum Genre {
    ACTION("action"),
    ADVENTURE("adventure"),
    HUMOR("humor");

    private final String genreName;

    Genre(String s) {
        this.genreName = s;
    }

    public static Genre fromString(String name) throws TypeNotExistException {

        final Genre[] values = Genre.values();
        for (Genre genre : values) {
            if (genre.genreName.equals(name) || genre.name().equals(name)) {
                return genre;
            }
        }
        return null;
//        throw new TypeNotExistException("error in method fromString() - " + Genre.class.getName());
    }
}
