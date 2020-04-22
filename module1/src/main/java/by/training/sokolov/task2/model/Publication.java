package by.training.sokolov.task2.model;

import by.training.sokolov.task2.enums.Genre;
import by.training.sokolov.task2.enums.PublicationType;

import java.util.Comparator;

public class Publication {

    public static Comparator<Publication> PublicationNameComparatorDescending = (publication1, publication2) -> {

        String publicationName1 = publication1.name.toUpperCase();
        String publicationName2 = publication2.name.toUpperCase();
//            descending order
        return publicationName2.compareTo(publicationName1);

    };

    public static Comparator<Publication> PublicationNameComparatorAscending = (publication1, publication2) -> {

        String publicationName1 = publication1.name.toUpperCase();
        String publicationName2 = publication2.name.toUpperCase();
//            ascending order
        return publicationName1.compareTo(publicationName2);
    };

    private long id;
    private String name;
    private PublicationType type;
    private Genre genre;
    private int pageAmount;

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
