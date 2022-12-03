import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class DatabaseTest {
    private BloodBankDatabase blood_bank_database;


    @Before
    public void setUp() throws SQLException {
        blood_bank_database = new BloodBankDatabase();

        //clear all the tables
        blood_bank_database.executeUpdate("delete from utilizador");
        blood_bank_database.executeUpdate("delete from transfusao");
        blood_bank_database.executeUpdate("delete from receptor;");
        blood_bank_database.executeUpdate("delete from armazenamento;");
        blood_bank_database.executeUpdate("delete from tipo_sangue_encomenda");
        blood_bank_database.executeUpdate("delete from encomenda_sangue;");
        blood_bank_database.executeUpdate("delete from encomenda;");
        blood_bank_database.executeUpdate("delete from doacao;");
        blood_bank_database.executeUpdate("delete from stock;");
        blood_bank_database.executeUpdate("delete from hospital");
        blood_bank_database.executeUpdate("delete from sangue;");
        blood_bank_database.executeUpdate("delete from doador;");


        //insert users
        blood_bank_database.executeUpdate(
                "insert into utilizador(id_utilizador, username, password, is_administrative) values(1, 'bingus', '" + BloodBankDatabase.hashString("admin") + "', 1);"
        );
        blood_bank_database.executeUpdate(
                "insert into utilizador(id_utilizador, username, password, is_administrative) values(2, 'floppa', '" + BloodBankDatabase.hashString("data") + "', 0);"
        );


        //insert data into the database
        blood_bank_database.executeUpdate("insert into doador(id_doador, nome, idade, sexo, contacto, morada) values(1, 'Diogo Pinto', 20, 'M', '964993876', 'Rua do Brasil Nº259 3ºD');");
        blood_bank_database.executeUpdate("insert into sangue(id_sangue, tipo_sangue, preco, id_doador) values(1, 'O', 10.5, 1);");
        blood_bank_database.executeUpdate("insert into hospital(id_hospital, nome, morada, contacto) values(1, \"HUC\", \"Rua do Brasil Nº260\",969146234);");
        blood_bank_database.executeUpdate("insert into stock(id_stock, morada, id_hospital) values(1, \"Rua do Pinheiro Nº267\", 1);");
        blood_bank_database.executeUpdate("insert into doacao(id_doacao, id_sangue, id_stock, id_doador, data, quantidade) values(1, 1, 1, 1, STR_TO_DATE(\"August 10 2017\", \"%M %d %Y\"), 5);");
        blood_bank_database.executeUpdate("insert into doador(id_doador, nome, idade, sexo, contacto, morada) values(2, 'Mariana Azevedo', 20, 'F', '964993876', 'Rua do Brasil Nº259 3ºD')");
        blood_bank_database.executeUpdate("insert into doador(id_doador, nome, idade, sexo, contacto, morada) values(3, 'Joao Pedro', 21, 'M', '964993876', 'Rua do Brasil Nº259 3ºD')");

        blood_bank_database.executeUpdate("insert into sangue(id_sangue, tipo_sangue, preco, id_doador) values(4, 'O', 10.5, 1);");
        blood_bank_database.executeUpdate("insert into sangue(id_sangue, tipo_sangue, preco, id_doador) values(2, 'A', 10.5, 2);");
        blood_bank_database.executeUpdate("insert into sangue(id_sangue, tipo_sangue, preco, id_doador) values(3, 'O+', 10.5, 3);");
        blood_bank_database.executeUpdate("insert into doacao(id_doacao, id_sangue, id_stock, id_doador, data, quantidade) values(2, 2, 1, 2, STR_TO_DATE(\"August 10 2017\", \"%M %d %Y\"), 5);");
        blood_bank_database.executeUpdate("insert into doacao(id_doacao, id_sangue, id_stock, id_doador, data, quantidade) values(3, 3, 1, 3, STR_TO_DATE(\"August 10 2017\", \"%M %d %Y\"), 5);");

        blood_bank_database.executeUpdate("insert into receptor(id_receptor,nome,idade,sexo,tipo_sangue) values(1, 'Arménio', 65, 'M', 'O+')");
        blood_bank_database.executeUpdate("insert into transfusao(id_transfusao, data, id_receptor, id_hospital, id_stock, id_sangue) values (1, STR_TO_DATE('August 10 2017', '%M %d %Y'), 1, 1, 1, 1);");
        blood_bank_database.executeUpdate("insert into transfusao(id_transfusao, data, id_receptor, id_hospital, id_stock, id_sangue) values (1, STR_TO_DATE('August 10 2017', '%M %d %Y'), 1, 1, 1, 1);");
        blood_bank_database.executeUpdate("insert into transfusao(id_transfusao, data, id_receptor, id_hospital, id_stock, id_sangue) values (1, STR_TO_DATE('August 10 2018', '%M %d %Y'), 1, 1, 1, 1);");
    }



    @Test
    public void loginTest() {
        Assert.assertEquals(ERROR_MESSAGES.USER_IS_ADMINISTRATIVE, blood_bank_database.login("bingus",  "admin"));
        Assert.assertEquals(ERROR_MESSAGES.USER_IS_DATA_ANALYST, blood_bank_database.login("floppa",  "data"));
    }


    @Test
    public void testGetBloodDonationsByAge() throws SQLException {
        List<Pair<Integer, Integer>> blood_donations_by_age = blood_bank_database.getBloodDonationsByAge();
        Assert.assertEquals(2, blood_donations_by_age.size());

        Assert.assertEquals(20, (long)blood_donations_by_age.get(0).first);
        Assert.assertEquals(2, (long)blood_donations_by_age.get(0).second);


        Assert.assertEquals(21, (long)blood_donations_by_age.get(1).first);
        Assert.assertEquals(1, (long)blood_donations_by_age.get(1).second);
    }

    @Test
    public void testGetBloodDonationsByAgeAndGender() throws SQLException {
        List<List<Pair<Integer, Integer>>> blood_donations_by_age_and_gender = blood_bank_database.getBloodDonationsByAgeAndGender();
        Assert.assertEquals(2, blood_donations_by_age_and_gender.size());


        //get the male donations
        List<Pair<Integer, Integer>> male_donations = blood_donations_by_age_and_gender.get(0);
        Assert.assertEquals(20, (long)male_donations.get(0).first);
        Assert.assertEquals(1, (long)male_donations.get(0).second);
        Assert.assertEquals(21, (long)male_donations.get(1).first);
        Assert.assertEquals(1, (long)male_donations.get(1).second);

        //get the female donations
        List<Pair<Integer, Integer>> female_donations = blood_donations_by_age_and_gender.get(0);
        Assert.assertEquals(20, (long)female_donations.get(0).first);
        Assert.assertEquals(1, (long)female_donations.get(0).second);
    }

    @Test
    public void testGetMostDonatedTypesOfBlood() throws SQLException {
        List<Pair<String,Integer>> most_donated_types_of_blood = blood_bank_database.getMostDonatedTypesOfBlood();

        Assert.assertEquals("O", most_donated_types_of_blood.get(0).first);
        Assert.assertEquals(2, (long)most_donated_types_of_blood.get(0).second);

        Assert.assertEquals("A", most_donated_types_of_blood.get(1).first);
        Assert.assertEquals(1, (long)most_donated_types_of_blood.get(1).second);

        Assert.assertEquals("O+", most_donated_types_of_blood.get(2).first);
        Assert.assertEquals(1, (long)most_donated_types_of_blood.get(2).second);
    }

    @Test
    public void testGetNumberOfBloodTransactionsByYear() throws SQLException {
        List<Pair<Integer,Integer>> number_of_transactions_by_year = blood_bank_database.getNumberOfBloodTransactionsByYear();

        Assert.assertEquals(2017, (long)number_of_transactions_by_year.get(0).first);
        Assert.assertEquals(2, (long)number_of_transactions_by_year.get(0).second);


        Assert.assertEquals(2018, (long)number_of_transactions_by_year.get(1).first);
        Assert.assertEquals(1, (long)number_of_transactions_by_year.get(1).second);
    }

    @Test
    public void testGetBloodDonationsByYear() throws SQLException {
        List<Pair<Integer,Integer>> number_of_donations_by_year = blood_bank_database.getBloodDonationsByYear();

        Assert.assertEquals(2017, (long)number_of_donations_by_year.get(0).first);
        Assert.assertEquals(3, (long)number_of_donations_by_year.get(0).second);
    }
}