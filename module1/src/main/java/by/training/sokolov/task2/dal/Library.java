package by.training.sokolov.task2.dal;

import by.training.sokolov.task2.model.Publication;

import java.util.List;

public class Library {

    private List<Publication> publicationList;

    public Library(List<Publication> publicationList) {
        this.publicationList = publicationList;
    }

    public void sortPublicationsByNameAscending() {
        publicationList.sort(Publication.PublicationNameComparatorAscending);
    }

    public void sortPublicationsByNameDescending() {
        publicationList.sort(Publication.PublicationNameComparatorDescending);
    }

    public List<Publication> getPublicationList() {
        return publicationList;
    }

}
