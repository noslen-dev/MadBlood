package gui.controllers;

import gui.ToastMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.BloodBankDatabase;
import model.ERROR_MESSAGES;
import model.Pair;


import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


public class DataAnalystViewController implements Initializable {

    @FXML
    TabPane root;
    @FXML
    public Tab idBloodDonatedByAgeTab;
    @FXML
    public AnchorPane idAnchorBloodDonatedByAge;
    @FXML
    public TextArea idTextBloodDonatedByAge;
    @FXML
    public Tab idBloodDonatedByAgeAndGenderTab;
    @FXML
    public AnchorPane idAnchorBloodDonatedByAgeAndGender;
    @FXML
    public TextArea idTextBloodDonatedByAgeAndGender;
    @FXML
    public Tab idTrendOfBloodDonationsTab;
    @FXML
    public AnchorPane idAnchorTrendBloodDonations;
    @FXML
    public TextArea idTrendBloodDonations;
    @FXML
    public Tab idNumberDonationsThroughoutTheYearsTab;
    @FXML
    public AnchorPane idAnchorDonationsThroughoutTheYear;
    @FXML
    public TextArea idNumberDonationsThroughoutTheYears;
    @FXML
    public Tab idMostDonatedTypesBloodTab;
    @FXML
    public AnchorPane idAnchorMostDonatedTypesOfBlood;
    @FXML
    public TextArea idMostDonatedTypesOfBlood;

    BloodBankDatabase model;

    public DataAnalystViewController(BloodBankDatabase model){
        this.model = model;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        registerHandlers();
    }

    /**
     * Registers all the handlers needed to handle the tab changes and calls
     * the respective functions to fill and organize the GUI
     */
    public void registerHandlers() {
        try{
            //model.executeUpdate("delete from transfusao");
            //model.executeUpdate("delete from receptor;");
            //model.executeUpdate("delete from armazenamento;");
            //model.executeUpdate("delete from tipo_sangue_encomenda");
            //model.executeUpdate("delete from encomenda_sangue;");
            //model.executeUpdate("delete from encomenda;");
            //model.executeUpdate("delete from doacao;");
            //model.executeUpdate("delete from stock;");
            //model.executeUpdate("delete from hospital");
            //model.executeUpdate("delete from sangue;");
            //model.executeUpdate("delete from doador;");

        }catch (Exception e){}


        //new RandomDatabaseFiller(model);
        try {
            bloodDonatedByAge();
        } catch (SQLException e) {
            ToastMessage.show(root.getScene().getWindow(), ERROR_MESSAGES.SQL_ERROR);
        }
        idBloodDonatedByAgeTab.setOnSelectionChanged(event -> {
            try {
                bloodDonatedByAge();
            } catch (SQLException e) {
                ToastMessage.show(root.getScene().getWindow(), ERROR_MESSAGES.SQL_ERROR);
            }
        });
        idBloodDonatedByAgeAndGenderTab.setOnSelectionChanged(event -> {
            try {
                bloodDonatedByAgeAndGender();
            } catch (SQLException e) {
                e.printStackTrace();
                ToastMessage.show(root.getScene().getWindow(), ERROR_MESSAGES.SQL_ERROR);
            }
        });
        //fillTrendOfBloodDonationsReport(model.getBloodDonationsByAge());
        idTrendOfBloodDonationsTab.setOnSelectionChanged(event -> {
            try {
                trendOfBloodDonations();
            } catch (SQLException e) {
                e.printStackTrace();
                ToastMessage.show(root.getScene().getWindow(), ERROR_MESSAGES.SQL_ERROR);
            }
        });
        idNumberDonationsThroughoutTheYearsTab.setOnSelectionChanged(event -> {
            try {
                numberOfTransfusionsThroughoutTheYears();
            } catch (SQLException e) {
                ToastMessage.show(root.getScene().getWindow(), ERROR_MESSAGES.SQL_ERROR);
            }
        });
        idMostDonatedTypesBloodTab.setOnSelectionChanged(event -> {
            try {
                mostDonatedTypesOfBlood();
            } catch (SQLException e) {
                ToastMessage.show(root.getScene().getWindow(), ERROR_MESSAGES.SQL_ERROR);
            }
        });

    }


    /**
     * Draws a linechart graph where the X represents the age >= 18 && <= 65
     * and the Y represents the amount of times a person donated
     * @throws SQLException
     */
    public void bloodDonatedByAge() throws SQLException {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Age");
        yAxis.setLabel("Amount of times donated");

        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(5);
        xAxis.setLowerBound(18);
        xAxis.setUpperBound(65);

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        final List<Pair<Integer, Integer>> dataToPopulateChart = model.getBloodDonationsByAge();

        lineChart.setTitle("Blood donated by age");
        //defining a series
        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setName("Blood donated by age");
        //populating the series with data
        for(Pair<Integer, Integer> pair : dataToPopulateChart){
            series.getData().add(new XYChart.Data<>(pair.first, pair.second));
        }

        lineChart.getData().add(series);
        idAnchorBloodDonatedByAge.getChildren().add(lineChart);

        //function to produce the report by age
        bloodDonatedByAgeReport(dataToPopulateChart);
    }
    /**
     * Fills the report text area about the amount of blood donated
     * by age and also specifies the age group with the most donations
     */
    public void bloodDonatedByAgeReport(List<Pair<Integer,Integer>> data) {
        String ageGroups = "";
        final Pair<Integer,Integer> age_with_most_donated_blood = data.stream().max(Comparator.comparingInt(x -> x.second)).get();

        if(age_with_most_donated_blood.first >= 18 && age_with_most_donated_blood.first <= 39)
            ageGroups = "Young adult";
        else if(age_with_most_donated_blood.first >= 40 && age_with_most_donated_blood.first <= 59)
            ageGroups = "Middle age adult";
        else if(age_with_most_donated_blood.first >= 60 && age_with_most_donated_blood.first <= 65)
            ageGroups = "Old people";
        else
            ageGroups = "Every age group donated the same amount";

        idTextBloodDonatedByAge.setText("\nThe age with most number of donations is: "+ age_with_most_donated_blood.first +
                ".\nDonating the following amount: " + age_with_most_donated_blood.second +
                ".\nThe age group they belong is: " + ageGroups);
    }
    /**
     * Draws a linechart graph where the X represents the age >= 18 && <= 65
     * and the Y represents the amount of times a person donated
     * There are two lines the orange representing the male gender
     * and the yellow representing the female gender
     * @throws SQLException
     */
    public void bloodDonatedByAgeAndGender() throws SQLException {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Age");
        yAxis.setLabel("Amount donated");

        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(5);
        xAxis.setLowerBound(18);
        xAxis.setUpperBound(65);

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        final List<Pair<Integer, Integer>> dataToPopulateChart = model.getBloodDonationsByAge();

        lineChart.setTitle("Blood donated by age and gender");
        //defining a series

        final List<List<Pair<Integer,Integer>>> ageAndGenderData = model.getBloodDonationsByAgeAndGender();

        final List<Pair<Integer,Integer>> maleData = ageAndGenderData.get(0);
        final List<Pair<Integer,Integer>> femaleData = ageAndGenderData.get(1);

        //serie for the males
        XYChart.Series<Number,Number> seriesMales = new XYChart.Series<>();
        seriesMales.setName("Male");

        //populating the series with data
        for(Pair<Integer, Integer> pair : maleData){
            XYChart.Data<Number,Number> data = new XYChart.Data<>(pair.first, pair.second);
            seriesMales.getData().add(data);
        }

        //serie for the females
        XYChart.Series<Number,Number> seriesFemales = new XYChart.Series<>();
        seriesFemales.setName("Female");

        //populating the series with data
        for(Pair<Integer, Integer> pair : femaleData){
            XYChart.Data<Number,Number> data = new XYChart.Data<>(pair.first, pair.second);
            seriesFemales.getData().add(data);

        }



        lineChart.getData().add(seriesMales);
        lineChart.getData().add(seriesFemales);
        //seriesFemales.getNode().setStyle("-fx-stroke: red;");
        //seriesMales.getNode().setStyle("-fx-stroke: blue;");


        idAnchorBloodDonatedByAgeAndGender.getChildren().add(lineChart);
        fillbloodDonatedByAgeAndGenderReport(ageAndGenderData);
    }
    /**
     * Fills the report text area about the amount of blood donated
     * by age and also specifies the age group with the most donations
     * additionally it specifies which gender donated the most blood
     */
    public void fillbloodDonatedByAgeAndGenderReport(List<List<Pair<Integer,Integer>>> data){

        final List<Pair<Integer,Integer>> maleData = data.get(0);
        final List<Pair<Integer,Integer>> femaleData = data.get(1);

        final int total_blood_by_male = maleData.stream().mapToInt(pair -> pair.second).sum();
        final int total_blood_by_female = femaleData.stream().mapToInt(pair -> pair.second).sum();

        if(total_blood_by_male > total_blood_by_female){
            idTextBloodDonatedByAgeAndGender.setText("The male gender donated more blood \nthan the female gender\n" +
                    "Donating a total of " + total_blood_by_male + " liters of blood\n when compared to" +
                    "the " +total_blood_by_female +" liters donated by the female gender");
        } else{
            idTextBloodDonatedByAgeAndGender.setText("The female gender donated more blood \nthan the male gender\n" +
                    "Donating a total of " + total_blood_by_female + " liters of blood\n when compared to" +
                    "the " +total_blood_by_male +" liters donated by the male gender");
        }

        final Pair<Integer,Integer> age_with_most_donated_blood = (data.stream().flatMap(List::stream).toList()).stream().max(Comparator.comparingInt(x -> x.second)).get();

        final int total_blood_by_young_adult = maleData.stream().filter(pair-> pair.first>= 18 && pair.first <=39).mapToInt(pair-> pair.second).sum() + femaleData.stream().filter(pair-> pair.first>= 18 && pair.first <=39).mapToInt(pair-> pair.second).sum();
        final int total_blood_by_middle_age_adult = maleData.stream().filter(pair-> pair.first>= 40 && pair.first <=59).mapToInt(pair-> pair.second).sum() + femaleData.stream().filter(pair-> pair.first>= 18 && pair.first <=39).mapToInt(pair-> pair.second).sum();
        final int total_blood_by_old_people = maleData.stream().filter(pair-> pair.first>= 60 && pair.first <=65).mapToInt(pair-> pair.second).sum() + femaleData.stream().filter(pair-> pair.first>= 18 && pair.first <=39).mapToInt(pair-> pair.second).sum();

        String ageGroups = " ";

        System.out.println(total_blood_by_young_adult);
        System.out.println(total_blood_by_middle_age_adult);
        System.out.println(total_blood_by_old_people);



        if(age_with_most_donated_blood.first >= 18 && age_with_most_donated_blood.first <= 39)
            ageGroups = "Young adult";
        else if(age_with_most_donated_blood.first >= 40 && age_with_most_donated_blood.first <= 59)
            ageGroups = "Middle age adult";
        else if(age_with_most_donated_blood.first >= 60 && age_with_most_donated_blood.first <= 65)
            ageGroups = "Old people";
        else
            ageGroups = "Every age group donated the same amount";

        idTextBloodDonatedByAgeAndGender.appendText("\nThe age with most number of donations is: "+ age_with_most_donated_blood.first +
                ".\nDonating the following amount: " + age_with_most_donated_blood.second +
                ".\nThe age group they belong is: " + ageGroups);

    }

    /**
     * Draws a linechart graph where the X represents the years
     * and the Y represents the amount of donations on that year
     * @throws SQLException
     */
    public void trendOfBloodDonations() throws SQLException {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Years");
        yAxis.setLabel("Donations per year");

        xAxis.setTickUnit(5);

        final List<Pair<Integer,Integer>> bloodDonationsByYear = model.getBloodDonationsByYear();
        final int lower_bound = bloodDonationsByYear.stream().min(Comparator.comparingInt(x->x.first)).get().first;
        final int upper_bound = bloodDonationsByYear.stream().max(Comparator.comparingInt(x->x.first)).get().first;
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(lower_bound);
        xAxis.setUpperBound(upper_bound);

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);


        lineChart.setTitle("Trend of blood donations per year");
        //defining a series



        //serie for the males
        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setName("Trend of blood donations per year");
        //populating the series with data
        for(Pair<Integer, Integer> pair : bloodDonationsByYear){
            series.getData().add(new XYChart.Data<>(pair.first, pair.second));
        }


        lineChart.getData().add(series);


        idAnchorTrendBloodDonations.getChildren().add(lineChart);
        fillTrendOfBloodDonationsReport(bloodDonationsByYear);

    }
    /**
     * Fills a report that specifies whether the graph is on an upwards
     * or downwards trend based on the very scientific method of counting
     * the amount of times the number lowers in a row and checking to see
     * if that amount is superior or equal two half of the dataset then
     * it has an upwards trend otherwise its downwards trend
     */
    public void fillTrendOfBloodDonationsReport(List<Pair<Integer,Integer>> data){
        int numberOfYearsInARow = 0;
        int lastYearDonatedBlood = data.get(0).second;
        int maxNumberOfYearsInARow = 0;
        //Iterate the date and count the number of times the next year has a lower value
        //than the current year
        for(Pair<Integer,Integer> pair : data){
            if(pair.second < lastYearDonatedBlood) {
                numberOfYearsInARow++;
                maxNumberOfYearsInARow = numberOfYearsInARow;
            }
            else
                numberOfYearsInARow = 0;
        }

        if(maxNumberOfYearsInARow >= data.size()/2){ //downward trend
            idTrendBloodDonations.setText("The graph shows a downwards trend of \n" +
                    "the blood donated throughout the years");
        } else{
            idTrendBloodDonations.setText("The graph shows a upwards trend of \n" +
                    "the blood donated throughout the years");
        }

    }
    /**
     * Draws a linechart graph where the X represents the years
     * and the Y represents the amount of transfusions on that year
     * @throws SQLException
     */
    public void numberOfTransfusionsThroughoutTheYears() throws SQLException {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Years");
        yAxis.setLabel("Transfusions per year");

        xAxis.setTickUnit(5);


        final List<Pair<Integer,Integer>> transfusionsByYears = model.getNumberOfBloodTransactionsByYear();

        final int lower_bound = transfusionsByYears.stream().min(Comparator.comparingInt(x->x.first)).get().first;
        final int upper_bound = transfusionsByYears.stream().max(Comparator.comparingInt(x->x.first)).get().first;
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(lower_bound);
        xAxis.setUpperBound(upper_bound);

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);


        lineChart.setTitle("Transfusions throughout the years");
        //defining a series

        //serie for the males
        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setName("Transfusions throughout the years");
        //populating the series with data
        for(Pair<Integer, Integer> pair : transfusionsByYears){
            series.getData().add(new XYChart.Data<>(pair.first, pair.second));
        }

        lineChart.getData().add(series);
        idAnchorDonationsThroughoutTheYear.getChildren().add(lineChart);

        numberOfTransfusionsThroughoutTheYearsReport(transfusionsByYears);
    }

    /**
     * Uses the exact same algorithm specified and explained in the
     * fillTrendOfBloodDonationsReport
     */
    public void numberOfTransfusionsThroughoutTheYearsReport(List<Pair<Integer,Integer>> data){
        int numberOfYearsInARow = 0;
        int lastYearTransfusedBlood = data.get(0).second;
        int maxNumberOfYearsInARow = 0;
        //Iterate the date and count the number of times the next year has a lower value
        //than the current year
        for(Pair<Integer,Integer> pair : data){
            if(pair.second < lastYearTransfusedBlood) {
                numberOfYearsInARow++;
                maxNumberOfYearsInARow = numberOfYearsInARow;
            }
            else
                numberOfYearsInARow = 0;
        }

        if(maxNumberOfYearsInARow >= data.size()/2){ //downward trend
            idNumberDonationsThroughoutTheYears.setText("The graph shows a downwards trend of \n" +
                    "the blood transfused throughout the years");
        } else{
            idNumberDonationsThroughoutTheYears.setText("The graph shows a upwards trend of \n" +
                    "the blood transfused throughout the years");
        }
    }
    /**
     * Draws a piechart graph where containing the bloodtypes donated
     * and the amount each one donated
     * @throws SQLException
     */
    public void mostDonatedTypesOfBlood() throws SQLException {

        //defining a series
        final List<Pair<String,Integer>> mostDonatedTypesOfBlood = model.getMostDonatedTypesOfBlood();

        ObservableList<PieChart.Data> chartMostDonatedTypesOfBlood = FXCollections.observableArrayList();

        for(Pair<String, Integer> pair : mostDonatedTypesOfBlood) {
            chartMostDonatedTypesOfBlood.add(new PieChart.Data(pair.first,pair.second));
        }

        final PieChart chart = new PieChart(chartMostDonatedTypesOfBlood);
        idAnchorMostDonatedTypesOfBlood.getChildren().add(chart);

        mostDonatedTypesOfBloodReport(mostDonatedTypesOfBlood);
    }
    /**
     * Fills a report that specifies which bloodtype donated the most and how much
     * was donated
     */
    public void mostDonatedTypesOfBloodReport(List<Pair<String,Integer>> data){
        final Pair<String,Integer> most_donated_blood_type = data.stream().max(Comparator.comparingInt(x -> x.second)).get();
        idMostDonatedTypesOfBlood.setText("The most donated type of blood is: " + most_donated_blood_type.first
                +"\nwith the following amount: "+ most_donated_blood_type.second);
    }
}