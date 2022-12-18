import gui.controllers.AdministrativeController;
import model.BloodBankDatabase;
import model.ERROR_MESSAGES;
import org.junit.Assert;
import org.junit.Test;

public class FilterTests {
    @Test
    public void numberTest() {
        AdministrativeController.validateDate("=27/7/2022 or =7/08/2002 or >=01/12/2002");
        System.out.println(
                AdministrativeController.decomposeString("=27/7/2022 or =7/08/2002 or >=01/12/2002", "doacao.data")
        );
        String text = "2017-08-10";
        System.out.println(text.substring(8, 10) + "/" + text.substring(5, 7) + "/" + text.substring(0, 4));

        System.out.println(BloodBankDatabase.hashString("aaaaaaaaa"));
    }
}
