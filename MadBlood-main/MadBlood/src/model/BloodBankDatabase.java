package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * model.BloodBankDatabase is the class that encapsulates the database used by the blood bank. It posseses methods to
 * query database information and shields the caller from the part of estabilishing a database connection and
 * knowing any SQL.
 */
public class BloodBankDatabase {
    /**
     * The connection to the database.
     */
    private Connection database_connection;

    /**
     * Constructs a model.BloodBankDatabase instance.
     * @throws SQLException in the case that the database server is not running.
     */
    public BloodBankDatabase() throws SQLException{
        database_connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bloodbank", "root", "onigirisnack"
        );
    }

    /**
     * Login function, this will receive the data the user inputed on loginview username and password fields, and will check if the user exists,
     * then if the password matches, and then returns the type of user the account belongs to
     * @param username Username inputed by the user
     * @param password Password inputed by the user
     * @return a message compiling the success/problem with the leggin and the type of credentials the user possess in case of success
     */
    public ERROR_MESSAGES login(String username,String password)
    {
        try {

            //first the login will check if the username exists
            String query = "select * from utilizador where UPPER(username)=UPPER(?)";

            //create a prepared statement and add the received username to it
            PreparedStatement statement = database_connection.prepareStatement(query);

            //set the username to the ? prompt on the query to avoid sql injections
            statement.setString(1, username.toUpperCase());

            //execute the query and save the results in a ResultSet
            ResultSet query_result = statement.executeQuery();

            //check if the user exists, otherwise, return errormessage informing that the user is invalid
            if (!query_result.isBeforeFirst()) {
                return ERROR_MESSAGES.INVALID_USERNAME;
            }
            query_result.next();

           //query the db for the user's hashed password
            String hashed_password=query_result.getString(3);


            //check if the password is valid
            if(!hashed_password.equals(hashString(password)))
            {
                return ERROR_MESSAGES.INVALID_PASSWORD;
            }

            //given the username and password are currect, we shall return the type of user the username is classified as

            //4th value of the user table is a boolean field that is true for administratives and false for data_analists
            if(query_result.getBoolean(4))
            {
                return ERROR_MESSAGES.USER_IS_ADMINISTRATIVE;
            }
            else
            {
                return ERROR_MESSAGES.USER_IS_DATA_ANALYST;
            }


        }
        catch (SQLException dbError)
        {
            //the connection to the database failed, the user needs to be informed
            return ERROR_MESSAGES.SQL_ERROR;
        }
    }

    /**
     * This function will hash the passwords to compare to the passwords stored on the db
     * @param string_to_hash the password to be hashed
     * @return hashed password
     */
    public static String hashString (String string_to_hash){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed_string = digest.digest(string_to_hash.getBytes(StandardCharsets.UTF_8));
            return new String(hashed_string);
        }catch (NoSuchAlgorithmException ignored){return null;}
    }

    /**
     * This fun will receive a pre rendered query and try to execute it, shoud it succeed, will return the resulting dataset
     * shoud it fail, will raise an exception informing of the problem with it
     * @param query query to be executed
     * @return the resulting dataset of the query
     */
    public ResultSet executeQuery(String query) throws SQLException
    {

        //create a prepared statement and add the received username to it
        PreparedStatement statement = database_connection.prepareStatement(query);

        //execute the query and returns the resulting ResultSet
        return statement.executeQuery();
    }

    /**
     * This function will query the database for a count of all the donations made by each type of blood
     * @return a list of pair <type of blood,number of blood donations> correspond to each different type of blood,
     * and the value beeing the ammount of  donnation of sayd type
     * @throws SQLException if there's a problem with the database connection an exception is thrown so the user is informed
     */
    public List<Pair<String,Integer>> getMostDonatedTypesOfBlood() throws SQLException
    {
        //query string to check the ammout of donnations by blood type
        String most_donated_types_of_blood_query="select tipo_sangue,count(tipo_sangue) from sangue group by tipo_sangue";

        //create a prepared statement with the query
        PreparedStatement statement = database_connection.prepareStatement(most_donated_types_of_blood_query);

        //execute the query and save the results in a ResultSet
        ResultSet query_result = statement.executeQuery();

        //create the pair list to fill
        List<Pair<String,Integer>>list_of_donations_by_blood_type= new ArrayList<>();

        //iterate through the resultSet to fill the list to return
        while(true)
        {
            query_result.next();
            if(query_result.isAfterLast())
                break;
            list_of_donations_by_blood_type.add(new Pair<>(query_result.getString(1),query_result.getInt(2)));
        }

        return list_of_donations_by_blood_type;

    }

    /**
     * This fun will query the database for the number of blood transactions occurred each year and will return a pair list of year-number of transactions
     * @return a pair list of year-number of transactions
     * @throws SQLException Throws an exception of the database cannot process the request, letting the user know
     */
    public List<Pair<Integer,Integer>> getNumberOfBloodTransactionsByYear() throws SQLException
    {
        //query string to check the ammout of transactions by year
        String number_of_transactions_by_year_query="select year(data),count(id_transfusao) from transfusao group by year(data)";

        //create a prepared statement with the query
        PreparedStatement statement = database_connection.prepareStatement(number_of_transactions_by_year_query);

        //execute the query and save the results in a ResultSet
        ResultSet query_result = statement.executeQuery();

        //create the pair list to fill
        List<Pair<Integer,Integer>>list_number_of_transactions_by_year= new ArrayList<>();

        //iterate through the resultSet to fill the list to return
        while(true)
        {
            query_result.next();
            if(query_result.isAfterLast())
                break;
            list_number_of_transactions_by_year.add(new Pair<>(query_result.getInt(1),query_result.getInt(2)));
        }

        return list_number_of_transactions_by_year;
    }

    /**
     * This fun will query the database for the number of blood donations occurred each year and will return a pair list of year-number of donations
     * @return a pair list of year-number of donations
     * @throws SQLException Throws an exception of the database cannot process the request, letting the user know
     */
    public List<Pair<Integer,Integer>> getBloodDonationsByYear() throws SQLException
    {
        //query string to check the ammout of transactions by year
        String number_of_donations_by_year_query="select year(data),count(id_doacao) from doacao group by year(data)";

        //create a prepared statement with the query
        PreparedStatement statement = database_connection.prepareStatement(number_of_donations_by_year_query);

        //execute the query and save the results in a ResultSet
        ResultSet query_result = statement.executeQuery();

        //create the pair list to fill
        List<Pair<Integer,Integer>>list_number_of_donations_by_year= new ArrayList<>();

        //iterate through the resultSet to fill the list to return
        while(true)
        {
            query_result.next();
            if(query_result.isAfterLast())
                break;
            list_number_of_donations_by_year.add(new Pair<>(query_result.getInt(1),query_result.getInt(2)));
        }

        return list_number_of_donations_by_year;
    }


    /**
     * @return a List in which each place corresponds to a model.Pair and the first member of the pair is an age and the
     * second member of the pair is the amount of blood donations made by persons of that age. Take the following
     * return value as an example [ [15, 127], [16, 200], ...]
     * @throws SQLException in case a problem occurs with the database server.
     */
    public List<Pair<Integer, Integer>> getBloodDonationsByAge() throws SQLException{
        //construct the query that is to execute which will simply group the donors by age
        //and for each age it will sum the amount of donations made by someone of that age
        String query_to_execute = "select doador.idade, count(*) from doacao, doador where doacao.id_doador = doador.id_doador group by doador.idade;";

        //execute the query
        ResultSet query_results = database_connection.createStatement().executeQuery(query_to_execute);

        //return the blood donations by age that we got on the ResultSet
        return extractBloodDonationsByAgeFromResultSet(query_results);
    }

    /**
     * @return a List in which each element is a list in which the first place represents the male gender
     * and the second represents the female gender and in that place the data placed there is the exact same
     * and the one returned by getBloodDonationsByAge.
     * @throws SQLException in case a problem occurs with the database server.
     */
    public List<List<Pair<Integer, Integer>>> getBloodDonationsByAgeAndGender() throws SQLException{
        //construct and execute the query to fetch the blood donations by age made by males
        String query_to_execute_to_get_male_donations = "select doador.idade, count(*) from doacao, doador where doacao.id_doador = doador.id_doador and doador.sexo = 'M' group by doador.idade;";
        ResultSet male_query_results = database_connection.createStatement().executeQuery(query_to_execute_to_get_male_donations);


        //construct and execute the query to fetch the blood donations by age made by females
        String query_to_execute_to_get_female_donations = "select doador.idade, count(*) from doacao, doador where doacao.id_doador = doador.id_doador and doador.sexo = 'F' group by doador.idade;";
        ResultSet female_query_results = database_connection.createStatement().executeQuery(query_to_execute_to_get_female_donations);

        //return the extracted information
        return List.of(extractBloodDonationsByAgeFromResultSet(male_query_results), extractBloodDonationsByAgeFromResultSet(female_query_results));
    }

    /**
     * @param result_set a ResultSet that contains two columns, one with the age and the other with the amount
     * of donations made by that age.
     * @return List in which each place corresponds to a model.Pair and the first member of the pair is an age and the
     * second member of the pair is the amount of blood donations made by persons of that age. Take the following
     * return value as an example [ [15, 127], [16, 200], ...]
     * @throws SQLException in case a problem occurs with the database server.
     */
    private List<Pair<Integer, Integer>> extractBloodDonationsByAgeFromResultSet(ResultSet result_set) throws SQLException {
        //construct the list that is to be returned
        ArrayList<Pair<Integer, Integer>> blood_donations_by_age = new ArrayList<>();

        //while we have results
        while(true) {
            //advance the iterator in the ResultSet and get out of the loop if we are past the last
            //element
            result_set.next();
            if (result_set.isAfterLast()) {
                break;
            }

            //add the [age, amount_of_donations] to our list
            blood_donations_by_age.add(new Pair<>(result_set.getInt(1), result_set.getInt(2)));
        }

        //now return the constructed List
        return blood_donations_by_age;
    }


    public void executeUpdate(String update) throws SQLException {
        database_connection.createStatement().executeUpdate(update);
    }
}
