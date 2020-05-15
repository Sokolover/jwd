package by.training.sokolov.task1.model;

import by.training.sokolov.task1.enums.Genre;
import by.training.sokolov.task1.enums.PublicationType;

public class Publication {

    private long id;
    private String name;
    private PublicationType type;
    private Genre genre;
    private int pageAmount;

    public Publication(){

    }

    public Publication(long id, String name, PublicationType type, Genre genre, int pageAmount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.genre = genre;
        this.pageAmount = pageAmount;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", genre=" + genre +
                ", pageAmount=" + pageAmount +
                '}';
    }

    public Genre getGenre() {
        return genre;
    }

    public int getPageAmount() {
        return pageAmount;
    }

}
