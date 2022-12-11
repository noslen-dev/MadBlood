import gui.controllers.AdministrativeController;
import model.ERROR_MESSAGES;
import org.junit.Assert;
import org.junit.Test;

public class FilterTests {
    @Test
    public void numberTest() {
        AdministrativeController.validateNumber("<12 and >=17");
        AdministrativeController.validateText("\"Mariah\" or \"Jonah\"");
        AdministrativeController.validateDate("=27/7/2022 or =7/08/2002 or >=01/12/2002");
    }
}
