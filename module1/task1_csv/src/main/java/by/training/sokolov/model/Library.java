package by.training.sokolov.model;

import java.util.List;

public class Library {

    private List<Publication> publicationList;

    public Library(List<Publication> publicationList) {
        this.publicationList = publicationList;
    }

    public List<Publication> getPublicationList() {
        return publicationList;
    }

}
