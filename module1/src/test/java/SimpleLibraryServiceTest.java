import dal.LibraryDao;
import model.Library;
import org.junit.Assert;
import org.junit.Test;
import service.LibraryService;
import service.SimpleLibraryService;

public class SimpleLibraryServiceTest {

    @Test
    public void getPageAmountByGenreShouldReturnEmptyString() {
        Library library = new LibraryDao().buildLibrary();
        LibraryService simpleLibraryService = new SimpleLibraryService();
        String requestParamValue = "incorrectType";

        Assert.assertEquals("", simpleLibraryService.getPageAmountByGenre(library, requestParamValue));
    }

}
