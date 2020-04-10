import org.junit.Assert;
import org.junit.Test;
import service.SimpleLibraryService;

public class SimpleLibraryServiceTest {

    @Test
    public void getPageAmountByGenreShouldReturnEmptyString() {
        SimpleLibraryService simpleLibraryService = new SimpleLibraryService();
        String requestParamValue = "incorrectType";

        Assert.assertEquals("", simpleLibraryService.getPageAmountByGenre(requestParamValue));
    }

}
