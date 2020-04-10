package dao;

import model.Library;
import model.Publication;

import java.util.List;

public class LibraryDao {

    public Library buildLibrary(){

        List<Publication> publicationList = new DataGenerator().generatePublications();
        return new Library(publicationList);
    }
}
