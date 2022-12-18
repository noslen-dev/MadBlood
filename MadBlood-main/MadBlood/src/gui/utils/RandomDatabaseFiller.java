package gui.utils;

import model.BloodBankDatabase;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.*;

public class RandomDatabaseFiller {

    private BloodBankDatabase _model;
    private List<String> _peoples_given_names;
    private List<String> _peoples_surnames;
    private final List<String> _bloodtypes = new ArrayList<>(Arrays.asList("O", "A", "AB", "B"));
    private List<String> _addresses;
    private List<String> _hospitals;

    public RandomDatabaseFiller(BloodBankDatabase model){
        _peoples_given_names= new ArrayList<>(Arrays.asList(
                "Aiden", "Jackson", "Ethan", "Liam", "Mason", "Noah", "Lucas", "Jacob", "Jayden", "Jack",
                "Logan", "Ryan", "Caleb", "Benjamin", "William", "Michael", "Alexander", "Elijah", "Matthew", "Dylan",
                "James", "Owen", "Connor", "Brayden", "Landon", "Carson", "Adam", "Leo", "Miles", "Nathan",
                "Oliver", "Abigail", "Emma", "Olivia", "Sophia", "Isabella", "Mia", "Ava", "Emily", "Abby",
                "Ella", "Madison", "Charlotte", "Harper", "Avery", "Elizabeth", "Sofia", "Aubrey", "Addison", "Evelyn",
                "Natalie", "Grace", "Hannah", "Aria", "Chloe", "Scarlett", "Zoe", "Victoria", "Lily", "Aaliyah",
                "Kaylee", "Lillian", "Lauren", "Layla", "Makayla", "Allison", "Kaitlyn", "Leah", "Peyton", "Molly",
                "Riley", "Samantha", "Alexis", "Hazel", "Violet", "Claire", "Sadie", "Paige", "Brielle", "Brooklyn",
                "Marley", "Caroline", "Nora", "Leila", "Anna", "Avery", "Ellie", "Julia", "Lila", "Gianna",
                "Gia", "Ryleigh", "Aubree", "Aurora", "Savannah", "Luna", "Kylie", "Stella", "Madeline", "Skylar"
        ));
        _peoples_surnames= new ArrayList<>(Arrays.asList(
                "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
                "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
                "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "King", "Wright",
                "Scott", "Green", "Baker", "Adams", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner",
                "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers",
                "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper", "Richardson", "Cox",
                "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson", "Brooks", "Kelly",
                "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross", "Henderson", "Coleman", "Jenkins", "Perry",
                "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler", "Simmons", "Foster", "Gonzales",
                "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes"
        ));
        _addresses= new ArrayList<>(Arrays.asList("123 Main Street, Anytown, USA 12345",
                "456 Maple Avenue, Anytown, USA 12345",
                "789 Oak Boulevard, Anytown, USA 12345",
                "321 Pine Street, Anytown, USA 12345",
                "654 Cedar Avenue, Anytown, USA 12345",
                "987 Spruce Drive, Anytown, USA 12345",
                "246 Birch Road, Anytown, USA 12345",
                "369 Willow Lane, Anytown, USA 12345",
                "159 Maple Avenue, Anytown, USA 12345",
                "753 Oak Boulevard, Anytown, USA 12345"));
        _hospitals= new ArrayList<>();
        _hospitals.add("St. Marys Hospital");
        _hospitals.add("Community Hospital");
        _hospitals.add("Memorial Hospital");
        _hospitals.add("General Hospital");
        _hospitals.add("Childrens Hospital");
        _hospitals.add("City Hospital");
        _hospitals.add("Regional Medical Center");
        _hospitals.add("Private Hospital");
        _hospitals.add("University Hospital");
        _hospitals.add("Veterans Hospital");
        this._model=model;
        try{
            _generate_Random_Donators();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    private void _generate_Random_Donators() throws SQLException {

        int id = 1;

        while (id <= 10000) {
            String query = String.format("Insert into doador values (%d,'%s %s',%d,'%s','%s','%s')",
                    id++, _peoples_given_names.get((int) Math.round(Math.abs(Math.random() * _peoples_given_names.size() - 1))),
                    _peoples_surnames.get((int) Math.round(Math.abs(Math.random() * _peoples_surnames.size() - 1))),
                    (int) Math.round(Math.abs(Math.random() * (65 - 18 - 1)) + 18),
                    Math.random() > 0.5 ? "F" : "M", (long) Math.round(Math.abs(Math.random() * 89999999) + 100000000), _addresses.get((int) Math.round(Math.abs(Math.random() * _addresses.size() - 1))));


            _model.executeUpdate(query);


        }
        id = 1;
        while (id <= 10) {
            String query = String.format("Insert into hospital values(%d,'%s','%s','%s')",
                    id, _hospitals.get(-1 + id++), _addresses.get((int) Math.round(Math.abs(Math.random() * _addresses.size() - 1))),
                    (long) Math.round(Math.abs(Math.random() * 89999999) + 100000000));

            _model.executeUpdate(query);

        }
        id = 1;
        while (id <= 20) {
            String query = String.format("Insert into stock values(%d,'%s',%d)",
                    id++, _addresses.get((int) Math.round(Math.abs(Math.random() * _addresses.size() - 1))), (int) Math.round(Math.abs(Math.random() * 10 - 1) + 1));

            _model.executeUpdate(query);

        }


        id = 1;

        while (id <= 100000) {
            int doacao=(int) Math.round(Math.abs(Math.random() * 10000 - 1) + 1);
            String query = String.format("Insert into sangue values(%d,'%s',%d,%d)"
                    , id++, _bloodtypes.get((int) Math.round(Math.abs(Math.random() * _bloodtypes.size() - 1))
                    ), (int) Math.round(Math.abs(Math.random() * 20 - 1)), doacao);
            Random rand = new Random();
            long dateInMillis =rand.nextLong(((long) System.currentTimeMillis()));
            var date = new Date(dateInMillis);

// Convert the date to a string
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, (int)(-Math.random() * 365*8));
            Date randomDate = calendar.getTime();
            String dateString = date.toString();

            String query_Donation= String.format("Insert into doacao values(%d,'%s',%d,%d,%d,%d)",1,new java.sql.Date(randomDate.getTime()),doacao,(int) Math.round(Math.abs(Math.random() * 20 - 1) + 1),
                    id-1,id-1);


            _model.executeUpdate(query);
            _model.executeUpdate(query_Donation);

        }

        id=1;
        while (id<=10000){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, (int)(-Math.random() * 365*8));
            Date randomDate = calendar.getTime();

            String query=String.format("Insert into encomenda values(%d,%d,'%s','%s')",id++,(int) Math.round(Math.abs(Math.random() * 100000 - 1) + 1),
                    _bloodtypes.get((int) Math.round(Math.abs(Math.random() * _bloodtypes.size() - 1))),new java.sql.Date(randomDate.getTime()));
            _model.executeUpdate(query);
        }
        id=1;
        while (id<=10000){
            String query=String.format("Insert into encomenda_sangue values(%d,%d,%d,%d,%d)",id++,(int) Math.round(Math.abs(Math.random() * 10000 -1) + 1),
                    (int)Math.round(Math.abs(Math.random() * 10 - 1) + 1), (int)Math.round(Math.abs(Math.random() * 20 - 1) + 1),(int)Math.round(Math.abs(Math.random() * 100000 - 1) + 1));

            _model.executeUpdate(query);

        }

        id=1;
        while (id<=10000){
            String query=String.format("Insert into armazenamento values(%d,%d,%d)",(int) Math.round(Math.abs(Math.random() * 100000 - 1) + 1)
                    , (int) Math.round(Math.abs(Math.random() * 20 - 1) + 1), (int)Math.round(Math.abs(Math.random() * 50000 - 1) + 1));

            _model.executeUpdate(query);
            id++;
        }

        id=1;
        while (id <= 1000) {
            String query = String.format("Insert into receptor values (%d,'%s %s',%d,'%s','%s')",
                    id++, _peoples_given_names.get((int) Math.round(Math.abs(Math.random() * _peoples_given_names.size() - 1))),
                    _peoples_surnames.get((int) Math.round(Math.abs(Math.random() * _peoples_surnames.size() - 1))),
                    (int) Math.round(Math.abs(Math.random() * (65 - 18 - 1)) + 18),
                    Math.random() > 0 ? "F" : "M", _bloodtypes.get((int) Math.round(Math.abs(Math.random() * _bloodtypes.size() - 1))));

            _model.executeUpdate(query);

        }

        id=1;
        while (id <= 1000) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, (int)(-Math.random() * 365*8));
            Date randomDate = calendar.getTime();


            String query = String.format("Insert into transfusao values (%d,'%s',%d,%d,%d,%d)",
                    id++,new java.sql.Date(randomDate.getTime()),(int)Math.round(Math.abs(Math.random() * 1000 - 1) + 1),(int)Math.round(Math.abs(Math.random() * 10 - 1) + 1),
                    (int)Math.round(Math.abs(Math.random() * 20 - 1) + 1), (int)Math.round(Math.abs(Math.random() * 100000 - 1) + 1));

            _model.executeUpdate(query);

        }


    }



}


