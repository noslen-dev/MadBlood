package gui.controllers;

import gui.ToastMessage;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.BloodBankDatabase;
import model.DATA_ELEMENT;
import model.ERROR_MESSAGES;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AdministrativeController implements Initializable {
    BloodBankDatabase model;

    @FXML
    public Button idBtnDonationData;
    @FXML
    public Button idBtnTransfusionData;
    @FXML
    public Button idBtnHospitalStockData;
    @FXML
    public Button idBtnOrderData;
    @FXML
    public Button idBtnBloodInStockData;


    /**
     * Content stack pane is the Stack pane where the data members below shall be placed.
     */
    @FXML
    public StackPane content_stack_pane;

    /**
     * The content that shall be placed on the above stack pane. This content contains the tables with
     * checkbox the tables to fill and the table to fill dropdown.
     */
    public ArrayList<VBox> contents;


    public AdministrativeController(BloodBankDatabase model) {
        this.model = model;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize our contents
        contents = new ArrayList<>();

        //load the fxml files of all the data elements
        for(DATA_ELEMENT data_element : DATA_ELEMENT.values()) {
            FXMLLoader fxml_loader = new FXMLLoader();
            fxml_loader.setLocation(getClass().getResource(data_element.toString()));
            fxml_loader.setController(new Object());
            try {
                contents.add(data_element.ordinal(), fxml_loader.load());
                contents.get(contents.size() - 1).setVisible(false);
            }catch (Exception e){e.printStackTrace();}
        }

        content_stack_pane.getChildren().add(contents.get(0));
        getStackPaneFromContent(contents.get(0)).getChildren().addAll(
                getTableViewWithColumns("name", "age", "sex", "contact", "address"),
                getTableViewWithColumns("blood_type", "price"),
                getTableViewWithColumns("address")
        );
        getStackPaneFromContent(contents.get(0)).getChildren().get(0).setVisible(true);

        content_stack_pane.getChildren().add(contents.get(1));
        getStackPaneFromContent(contents.get(1)).getChildren().addAll(
                getTableViewWithColumns("name", "age", "sex", "blood_type"),
                getTableViewWithColumns("blood_type", "price"),
                getTableViewWithColumns("name", "address", "contact"),
                getTableViewWithColumns("address"),
                getTableViewWithColumns("name", "age", "sex", "contact", "address")
        );
        getStackPaneFromContent(contents.get(1)).getChildren().get(0).setVisible(true);


        content_stack_pane.getChildren().add(contents.get(2));
        getStackPaneFromContent(contents.get(2)).getChildren().addAll(
                getTableViewWithColumns("blood_type", "price"),
                getTableViewWithColumns("name", "address","contact"),
                getTableViewWithColumns("address")
        );
        getStackPaneFromContent(contents.get(2)).getChildren().get(0).setVisible(true);


        content_stack_pane.getChildren().add(contents.get(3));
        getStackPaneFromContent(contents.get(3)).getChildren().addAll(
                getTableViewWithColumns("blood_type", "price"),
                getTableViewWithColumns("name", "address", "contact"),
                getTableViewWithColumns("address")
        );
        getStackPaneFromContent(contents.get(3)).getChildren().get(0).setVisible(true);


        content_stack_pane.getChildren().add(contents.get(4));
        getStackPaneFromContent(contents.get(4)).getChildren().addAll(
                getTableViewWithColumns("blood_type", "price"),
                getTableViewWithColumns("address")
        );
        getStackPaneFromContent(contents.get(4)).getChildren().get(0).setVisible(true);


        //set our content to the first data element
        content_stack_pane.getChildren().get(0).setVisible(true);

        //Set the listeners to the dropdowns to change the tables to the selected values
        getComboBoxFromContent(contents.get(0)).setValue("Donor");
        getComboBoxFromContent(contents.get(0)).setOnAction(event->{
            switch(getComboBoxFromContent(contents.get(0)).getValue()){
                case "Donor" -> {
                    getStackPaneFromContent(contents.get(0)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(0)).getChildren().get(0).setVisible(true);
                }
                case "Blood"-> {
                    getStackPaneFromContent(contents.get(0)).getChildren().forEach((e) -> {
                        e.setVisible(false);
                    });
                    getStackPaneFromContent(contents.get(0)).getChildren().get(1).setVisible(true);
                }
                case "Stock" ->{
                    getStackPaneFromContent(contents.get(0)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(0)).getChildren().get(2).setVisible(true);
                }
            }

            }

        );

        getComboBoxFromContent(contents.get(1)).setValue("Receptor");
        getComboBoxFromContent(contents.get(1)).setOnAction(event->{
            switch(getComboBoxFromContent(contents.get(1)).getValue()){
                case "Receptor" -> {
                    getStackPaneFromContent(contents.get(1)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(1)).getChildren().get(0).setVisible(true);
                }
                case "Blood" ->{
                    getStackPaneFromContent(contents.get(1)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(1)).getChildren().get(1).setVisible(true);
                }
                case "Hospital"->{
                    getStackPaneFromContent(contents.get(1)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(1)).getChildren().get(2).setVisible(true);
                }
                case "Stock"->{
                    getStackPaneFromContent(contents.get(1)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(1)).getChildren().get(3).setVisible(true);
                }
                case "Donor"->{
                    getStackPaneFromContent(contents.get(1)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(1)).getChildren().get(4).setVisible(true);
                }
            }
        });

        getComboBoxFromContent(contents.get(2)).setValue("Blood");
        getComboBoxFromContent(contents.get(2)).setOnAction(event->{
            switch(getComboBoxFromContent(contents.get(2)).getValue()){

                        case "Blood"-> {
                            getStackPaneFromContent(contents.get(2)).getChildren().forEach((e) -> {
                                e.setVisible(false);
                            });
                            getStackPaneFromContent(contents.get(2)).getChildren().get(0).setVisible(true);
                        }
                        case "Hospital" -> {
                            getStackPaneFromContent(contents.get(2)).getChildren().forEach((e)->{e.setVisible(false);});
                            getStackPaneFromContent(contents.get(2)).getChildren().get(1).setVisible(true);
                        }
                        case "Stock" ->{
                            getStackPaneFromContent(contents.get(2)).getChildren().forEach((e)->{e.setVisible(false);});
                            getStackPaneFromContent(contents.get(2)).getChildren().get(2).setVisible(true);
                        }

                    }

                }

        );

        getComboBoxFromContent(contents.get(3)).setValue("Donor");
        getComboBoxFromContent(contents.get(3)).setOnAction(event->{
            switch(getComboBoxFromContent(contents.get(3)).getValue()){
                        case "Donor" -> {
                            getStackPaneFromContent(contents.get(3)).getChildren().forEach((e)->{e.setVisible(false);});
                            getStackPaneFromContent(contents.get(3)).getChildren().get(0).setVisible(true);
                        }
                        case "Hospital"-> {
                            getStackPaneFromContent(contents.get(3)).getChildren().forEach((e) -> {
                                e.setVisible(false);
                            });
                            getStackPaneFromContent(contents.get(3)).getChildren().get(1).setVisible(true);
                        }
                        case "Stock" ->{
                            getStackPaneFromContent(contents.get(3)).getChildren().forEach((e)->{e.setVisible(false);});
                            getStackPaneFromContent(contents.get(3)).getChildren().get(2).setVisible(true);
                        }
                    }

                }
        );

        getComboBoxFromContent(contents.get(4)).setValue("Blood");
        getComboBoxFromContent(contents.get(4)).setOnAction(event->{
            switch(getComboBoxFromContent(contents.get(4)).getValue()){
                case "Blood" -> {
                    getStackPaneFromContent(contents.get(4)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(4)).getChildren().get(0).setVisible(true);
                }
                case "Stock" ->{
                    getStackPaneFromContent(contents.get(4)).getChildren().forEach((e)->{e.setVisible(false);});
                    getStackPaneFromContent(contents.get(4)).getChildren().get(1).setVisible(true);
                }
            }
        });

        idBtnDonationData.setOnAction(
                (event)->{
                    content_stack_pane.getChildren().forEach(child -> child.setVisible(false));
                    content_stack_pane.getChildren().get(0).setVisible(true);
                }
        );
        idBtnTransfusionData.setOnAction(
                (event)->{
                    content_stack_pane.getChildren().forEach(child -> child.setVisible(false));
                    content_stack_pane.getChildren().get(1).setVisible(true);
                }
        );
        idBtnHospitalStockData.setOnAction(
                (event)->{
                    content_stack_pane.getChildren().forEach(child -> child.setVisible(false));
                    content_stack_pane.getChildren().get(2).setVisible(true);
                }
        );
        idBtnOrderData.setOnAction(
                (event)->{
                    content_stack_pane.getChildren().forEach(child -> child.setVisible(false));
                    content_stack_pane.getChildren().get(3).setVisible(true);
                }
        );
        idBtnBloodInStockData.setOnAction(
                (event)->{
                    content_stack_pane.getChildren().forEach(child -> child.setVisible(false));
                    content_stack_pane.getChildren().get(4).setVisible(true);
                }
        );

        //donations run button
        getRunButtonListenerFromContent(contents.get(0)).setOnMouseClicked(
                (event)->{
                    try {
                        getQueryFromDonationDataElement();
                    }catch (Exception e){
                        ToastMessage.show(contents.get(0).getScene().getWindow(), e.getMessage());
                    }
                }
        );
        getRunButtonListenerFromContent(contents.get(1)).setOnMouseClicked(
                (event)->{
                    try {
                        getQueryFromTransfusionDataElement();
                    }catch (Exception e){
                        ToastMessage.show(contents.get(0).getScene().getWindow(), e.getMessage());
                    }
                }
        );
        getRunButtonListenerFromContent(contents.get(2)).setOnMouseClicked(
                (event)->{
                    try {
                        getQueryFromHospitalStocksDataElement();
                    }catch (Exception e){
                        ToastMessage.show(contents.get(0).getScene().getWindow(), e.getMessage());
                    }
                }
        );
        getRunButtonListenerFromContent(contents.get(3)).setOnMouseClicked(
                (event)->{
                    try {
                        getQueryFromOrdersDataElement();
                    }catch (Exception e){
                        ToastMessage.show(contents.get(0).getScene().getWindow(), e.getMessage());
                    }
                }
        );
        getRunButtonListenerFromContent(contents.get(4)).setOnMouseClicked(
                (event)->{
                    try {
                        getQueryFromBloodInStocksDataElement();
                    }catch (Exception e){
                        ToastMessage.show(contents.get(0).getScene().getWindow(), e.getMessage());
                    }
                }
        );
    }

    public StackPane getStackPaneFromContent(VBox content){
        return ((StackPane) content.getChildren().get(2));
    }

    public Node getRunButtonListenerFromContent(VBox content){
        return ((HBox)content.getChildren().get(1)).getChildren().get(1);
    }

    public String getQueryFromDonationDataElement(){
        //see if at least one checkbox from the list of checkboxes is selected
        ArrayList<String> selected_checkboxes_names = getCheckBoxListFromContent(contents.get(0));

        //if nothing is selected show a dialog with an error
        if(selected_checkboxes_names.isEmpty()){
            ToastMessage.show(contents.get(0).getScene().getWindow(), ERROR_MESSAGES.NO_CHECKBOXES_SELECTED);
            return "";
        }

        //at least one checkbox is selected, so we can proceed to check the validity of the filters


        //construct the query that is to return
        StringBuilder query = new StringBuilder("select ");
        for(int i = 0 ; i != selected_checkboxes_names.size(); ++i){
            query.append(selected_checkboxes_names.get(i));
            if(i != selected_checkboxes_names.size() - 1){
                query.append(",");
            }
        }

        //construct the from with all the joins
        query.append(" from doador inner join sangue on (doador.id_doador=sangue.id_doador) inner join armazenamento on (sangue.id_sangue= armazenamento.id_sangue)" +
                "inner join stock on (stock.id_stock = armazenamento.id_stock)  ");


        //get all the rows of the donations table
        ObservableList<ArrayList<String>> donor_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(0)).getChildren().get(0)
        ).getItems();

        ObservableList<ArrayList<String>> blood_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(0)).getChildren().get(1)
        ).getItems();

        ObservableList<ArrayList<String>> stock_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(0)).getChildren().get(2)
        ).getItems();


        //validate each field of this row [name, age, sex, contact, address] => donor
        for (int i = 0; i != donor_rows.size(); ++i) {
                //validate the name
                int conditions = 0;
                boolean where_placed = false;
                if (i > 0)
                    where_placed = true;
                boolean or_placed = false;
                if (!donor_rows.get(i).get(0).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append(" or( ");
                        or_placed = true;
                    }
                    try {
                        validateText(donor_rows.get(i).get(0));
                    }catch (Exception e){
                        throw new RuntimeException("In table Donor row: " + (i + 1) + " at column: name\n" + e.getMessage());
                    }
                    conditions++;
                    where_placed = true;
                    if (i == 0)
                        query.append(" where (");
                    query.append("  " + composeLikeStatements(donor_rows.get(i).get(0), "doador.nome") + " ");
                }


                if (!donor_rows.get(i).get(1).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append(" or( ");
                        or_placed = true;
                    }
                    conditions++;
                    validateNumber(donor_rows.get(i).get(1));
                    where_placed = true;
                    query.append(String.format("%s  %s", !where_placed ? " where(" : " And ", decomposeString(donor_rows.get(i).get(1), "doador.idade")));
                }

                if (!donor_rows.get(i).get(2).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append(" or (");
                        or_placed = true;
                    }
                    conditions++;
                    validateSex(donor_rows.get(i).get(2));
                    where_placed = true;
                    query.append(String.format("%s %s", !where_placed ? " where (" : " and ", decomposeString(donor_rows.get(i).get(2), "doador.sexo")));
                }


                if (!donor_rows.get(i).get(3).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append(" or( ");
                        or_placed = true;
                    }
                    conditions++;
                    validateText(donor_rows.get(i).get(3));
                    query.append(String.format("%s   %s", !where_placed ? " where(" : " and ", decomposeString(donor_rows.get(i).get(3), "doador.contacto")));
                    where_placed = true;
                }
                if (!donor_rows.get(i).get(4).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append(" or( ");
                        or_placed = true;
                    }
                    conditions++;
                    validateText(donor_rows.get(i).get(4));
                    query.append(String.format("%s  %s ", !where_placed ? " where(" : " and ", composeLikeStatements(donor_rows.get(i).get(4), "doador.morada")));
                    where_placed = true;
                }
                if (!blood_rows.get(i).get(0).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append("or(");
                        or_placed = true;
                    }
                    validateText(blood_rows.get(i).get(0));
                    conditions++;
                    where_placed = true;
                    query.append(String.format("%s  %s", !where_placed ? " where(" : " And ", decomposeString(blood_rows.get(i).get(0), "sangue.tipo_sangue")));

                }

                if (!blood_rows.get(i).get(1).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append(" or( ");
                        or_placed = true;
                    }
                    validateNumber(blood_rows.get(i).get(1));
                    conditions++;
                    where_placed = true;
                    query.append(String.format("%s  %s", !where_placed ? " where(" : " And ", decomposeString(blood_rows.get(i).get(1), "sangue.preco ")));
                }

                if (!stock_rows.get(i).get(0).isEmpty()) {
                    if (i > 0 && !or_placed) {
                        query.append("or(");
                        or_placed = true;
                    }
                    validateText(stock_rows.get(i).get(0));
                    conditions++;
                    where_placed = true;
                    query.append(String.format("%s  %s ", !where_placed ? " where(" : " And ", composeLikeStatements(stock_rows.get(i).get(0), "stock.morada")));
                }

                if (conditions == 0) {
                    break;
                } else
                    query.append(")");
            }


        System.out.println(query);
        return null;
    }
    public String getQueryFromTransfusionDataElement(){
        //see if at least one checkbox from the list of checkboxes is selected
        ArrayList<String> selected_checkboxes_names = getCheckBoxListFromContent(contents.get(1));

        //if nothing is selected show a dialog with an error
        if(selected_checkboxes_names.isEmpty()){
            ToastMessage.show(contents.get(1).getScene().getWindow(), ERROR_MESSAGES.NO_CHECKBOXES_SELECTED);
            return "";
        }

        //at least one checkbox is selected, so we can proceed to check the validity of the filters


        //construct the query that is to return
        StringBuilder query = new StringBuilder("select ");
        for(int i = 0 ; i != selected_checkboxes_names.size(); ++i){
            query.append(selected_checkboxes_names.get(i));
            if(i != selected_checkboxes_names.size() - 1){
                query.append(",");
            }
        }

        //construct the from with all the joins
        query.append(" from receptor inner join transfusao on (transfusao.id_receptor=receptor.id_receptor) inner join sangue on (sangue.id_sangue= transfusao.id_sangue)" +
                "inner join hospital on (hospital.id_hospital = transfusao.id_transfusao) inner join stock on (stock.id_stock = transfusao.id_stock) inner join doador on (" +
                "doador.id_doador= sangue.id_doador)  ");


        //get all the rows of the donations table
        ObservableList<ArrayList<String>> receptor_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(1)).getChildren().get(0)
        ).getItems();

        ObservableList<ArrayList<String>> blood_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(1)).getChildren().get(1)
        ).getItems();
        ObservableList<ArrayList<String>> hospital_rows=((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(1)).getChildren().get(2)
        ).getItems();

        ObservableList<ArrayList<String>> stock_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(1)).getChildren().get(3)
        ).getItems();
        ObservableList<ArrayList<String>>donor_rows=((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(1)).getChildren().get(4)
        ).getItems();



        //validate each field of this row [name, age, sex, contact, address] => donor
        for(int i=0 ; i != donor_rows.size(); ++i){
            //validate the name
            int conditions=0;
            boolean where_placed = false;
            if(i>0)
                where_placed=true;
            boolean or_placed=false;
            if(!receptor_rows.get(i).get(0).isEmpty()) {
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(receptor_rows.get(i).get(0));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append("  " + composeLikeStatements(receptor_rows.get(i).get(0), "receptor.nome") + " ");
            }


            if(!receptor_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;}
                conditions++;
                validateNumber(receptor_rows.get(i).get(1));
                where_placed = true;
                query.append(String.format("%s  %s",!where_placed?" where(":" And ",decomposeString(receptor_rows.get(i).get(1),"receptor.idade")));
            }

            if (!receptor_rows.get(i).get(2).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append(" or (");
                    or_placed=true;}
                conditions++;
                validateSex(receptor_rows.get(i).get(2));
                where_placed = true;
                query.append(String.format("%s receptor.sexo = '%s'", !where_placed ? " where (" : " and ", receptor_rows.get(i).get(2)));
            }


            if(!receptor_rows.get(i).get(3).isEmpty()) {
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;}
                conditions++;
                validateText(receptor_rows.get(i).get(3));
                query.append(String.format("%s  %s", !where_placed ? " where(" : " and ", decomposeString(receptor_rows.get(i).get(3),"receptor.tipo_sangue")));
                where_placed = true;
            }

            if(!blood_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;}
                validateText(blood_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s",!where_placed?" where(":" And ", decomposeString(blood_rows.get(i).get(0),"sangue.tipo_sangue")));

            }

            if(!blood_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateNumber(blood_rows.get(i).get(1));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" sangue.preco  " + blood_rows.get(i).get(1) );
            }
            if(!hospital_rows.get(i).get(0).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(0));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" %s " + composeLikeStatements(hospital_rows.get(i).get(0), "hospital.nome") );
            }
            if(!hospital_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(1));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" %s " + composeLikeStatements(hospital_rows.get(i).get(1), "hospital.morada" ));
            }
            if(!hospital_rows.get(i).get(2).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(2));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" hospital.contacto = " + hospital_rows.get(i).get(2) );
            }


            if(!stock_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;
                }
                validateText(stock_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s ",!where_placed?" where(":" And ",composeLikeStatements(stock_rows.get(i).get(0), "stock.morada")));
            }
            if(!donor_rows.get(i).get(0).isEmpty()) {
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(donor_rows.get(i).get(0));
                conditions++;
                where_placed = true;
                query.append(String.format("%s  %s ", !where_placed ? " where(" : " and ", composeLikeStatements(donor_rows.get(i).get(0), "doador.nome")));
            }


            if(!donor_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;}
                conditions++;
                validateNumber(donor_rows.get(i).get(1));
                where_placed = true;
                query.append(String.format("%s %s",!where_placed?" where(":" And ",decomposeString(donor_rows.get(i).get(1),"doador.idade")));
            }


            if (!donor_rows.get(i).get(2).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append(" or (");
                    or_placed=true;}
                conditions++;
                validateSex(donor_rows.get(i).get(2));
                where_placed = true;
                query.append(String.format("%s doador.sexo = '%s'", !where_placed ? " where (" : " and ", donor_rows.get(i).get(2)));
            }


            if(!donor_rows.get(i).get(3).isEmpty()) {
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;}
                conditions++;
                validateText(donor_rows.get(i).get(3));
                query.append(String.format("%s doador.contacto = %s", !where_placed ? " where(" : " and ", donor_rows.get(i).get(3)));
                where_placed = true;
            }
            if(!donor_rows.get(i).get(4).isEmpty()) {
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;}
                conditions++;
                validateText(donor_rows.get(i).get(4));
                query.append(String.format("%s  %s ", !where_placed ? " where(" : " and ", composeLikeStatements(donor_rows.get(i).get(4), "doador.morada")));
                where_placed = true;
            }

            if(conditions==0){
                break;
            }
            else
                query.append(")");
        }

        System.out.println(query);
        return null;
    }
    public String getQueryFromHospitalStocksDataElement(){
        //see if at least one checkbox from the list of checkboxes is selected
        ArrayList<String> selected_checkboxes_names = getCheckBoxListFromContent(contents.get(2));

        //if nothing is selected show a dialog with an error
        if(selected_checkboxes_names.isEmpty()){
            ToastMessage.show(contents.get(2).getScene().getWindow(), ERROR_MESSAGES.NO_CHECKBOXES_SELECTED);
            return "";
        }

        //at least one checkbox is selected, so we can proceed to check the validity of the filters


        //construct the query that is to return
        StringBuilder query = new StringBuilder("select ");
        for(int i = 0 ; i != selected_checkboxes_names.size(); ++i){
            query.append(selected_checkboxes_names.get(i));
            if(i != selected_checkboxes_names.size() - 1){
                query.append(",");
            }
        }

        //construct the from with all the joins
        query.append(" from  hospital  inner join stock on (stock.id_hospital = hospital.id_hospital) inner join armazenamento on (" +
                "stock.id_stock= armazenamento.id_stock) inner join sangue on (sangue.id_sangue= armazenamento.id_sangue  ");


        //get all the rows of the donations table


        ObservableList<ArrayList<String>> blood_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(2)).getChildren().get(0)
        ).getItems();
        ObservableList<ArrayList<String>> hospital_rows=((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(2)).getChildren().get(1)
        ).getItems();

        ObservableList<ArrayList<String>> stock_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(2)).getChildren().get(2)
        ).getItems();




        //validate each field of this row [name, age, sex, contact, address] => donor
        for(int i=0 ; i != blood_rows.size(); ++i){
            //validate the name
            int conditions=0;
            boolean where_placed = false;
            if(i>0)
                where_placed=true;
            boolean or_placed=false;

            if(!blood_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;}
                validateText(blood_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s",!where_placed?" where(":" And ", decomposeString(blood_rows.get(i).get(0),"sangue.tipo_sangue")));

            }

            if(!blood_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateNumber(blood_rows.get(i).get(1));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" sangue.preco  " + blood_rows.get(i).get(1) );
            }
            if(!hospital_rows.get(i).get(0).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(0));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" %s " + composeLikeStatements(hospital_rows.get(i).get(0), "hospital.nome") );
            }
            if(!hospital_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(1));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" %s " + composeLikeStatements(hospital_rows.get(i).get(1), "hospital.morada" ));
            }
            if(!hospital_rows.get(i).get(2).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(2));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" hospital.contacto = " + hospital_rows.get(i).get(2) );
            }


            if(!stock_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;
                }
                validateText(stock_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s ",!where_placed?" where(":" And ",composeLikeStatements(stock_rows.get(i).get(0), "stock.morada")));
            }


            if(conditions==0){
                break;
            }
            else
                query.append(")");
        }

        System.out.println(query);
        return null;
    }
    public String getQueryFromOrdersDataElement(){
        //see if at least one checkbox from the list of checkboxes is selected
        ArrayList<String> selected_checkboxes_names = getCheckBoxListFromContent(contents.get(3));

        //if nothing is selected show a dialog with an error
        if(selected_checkboxes_names.isEmpty()){
            ToastMessage.show(contents.get(3).getScene().getWindow(), ERROR_MESSAGES.NO_CHECKBOXES_SELECTED);
            return "";
        }

        //at least one checkbox is selected, so we can proceed to check the validity of the filters


        //construct the query that is to return
        StringBuilder query = new StringBuilder("select ");
        for(int i = 0 ; i != selected_checkboxes_names.size(); ++i){
            query.append(selected_checkboxes_names.get(i));
            if(i != selected_checkboxes_names.size() - 1){
                query.append(",");
            }
        }

        //construct the from with all the joins
        query.append(" from  hospital  inner join stock on (stock.id_hospital = hospital.id_hospital) inner join armazenamento on (" +
                "stock.id_stock= armazenamento.id_stock) inner join sangue on (sangue.id_sangue= armazenamento.id_sangue  ");


        //get all the rows of the donations table


        ObservableList<ArrayList<String>> blood_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(3)).getChildren().get(0)
        ).getItems();
        ObservableList<ArrayList<String>> hospital_rows=((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(3)).getChildren().get(1)
        ).getItems();

        ObservableList<ArrayList<String>> stock_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(3)).getChildren().get(2)
        ).getItems();




        //validate each field of this row [name, age, sex, contact, address] => donor
        for(int i=0 ; i != blood_rows.size(); ++i){
            //validate the name
            int conditions=0;
            boolean where_placed = false;
            if(i>0)
                where_placed=true;
            boolean or_placed=false;

            if(!blood_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;}
                validateText(blood_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s",!where_placed?" where(":" And ", decomposeString(blood_rows.get(i).get(0),"sangue.tipo_sangue")));

            }

            if(!blood_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateNumber(blood_rows.get(i).get(1));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" sangue.preco  " + blood_rows.get(i).get(1) );
            }
            if(!hospital_rows.get(i).get(0).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(0));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" %s " + composeLikeStatements(hospital_rows.get(i).get(0), "hospital.nome") );
            }
            if(!hospital_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(1));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" %s " + composeLikeStatements(hospital_rows.get(i).get(1), "hospital.morada" ));
            }
            if(!hospital_rows.get(i).get(2).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateText(hospital_rows.get(i).get(2));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" hospital.contacto = " + hospital_rows.get(i).get(2) );
            }


            if(!stock_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;
                }
                validateText(stock_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s ",!where_placed?" where(":" And ",composeLikeStatements(stock_rows.get(i).get(0), "stock.morada")));
            }


            if(conditions==0){
                break;
            }
            else
                query.append(")");
        }

        System.out.println(query);
        return null;
    }
    public String getQueryFromBloodInStocksDataElement(){
        //see if at least one checkbox from the list of checkboxes is selected
        ArrayList<String> selected_checkboxes_names = getCheckBoxListFromContent(contents.get(4));

        //if nothing is selected show a dialog with an error
        if(selected_checkboxes_names.isEmpty()){
            ToastMessage.show(contents.get(4).getScene().getWindow(), ERROR_MESSAGES.NO_CHECKBOXES_SELECTED);
            return "";
        }

        //at least one checkbox is selected, so we can proceed to check the validity of the filters


        //construct the query that is to return
        StringBuilder query = new StringBuilder("select ");
        for(int i = 0 ; i != selected_checkboxes_names.size(); ++i){
            query.append(selected_checkboxes_names.get(i));
            if(i != selected_checkboxes_names.size() - 1){
                query.append(",");
            }
        }

        //construct the from with all the joins
        query.append(" from   stock  inner join armazenamento on (" +
                "stock.id_stock= armazenamento.id_stock) inner join sangue on (sangue.id_sangue= armazenamento.id_sangue  ");


        //get all the rows of the donations table


        ObservableList<ArrayList<String>> blood_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(4)).getChildren().get(0)
        ).getItems();

        ObservableList<ArrayList<String>> stock_rows = ((TableView<ArrayList<String>>) getStackPaneFromContent(
                contents.get(4)).getChildren().get(1)
        ).getItems();




        //validate each field of this row [name, age, sex, contact, address] => donor
        for(int i=0 ; i != blood_rows.size(); ++i){
            //validate the name
            int conditions=0;
            boolean where_placed = false;
            if(i>0)
                where_placed=true;
            boolean or_placed=false;

            if(!blood_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;}
                validateText(blood_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s",!where_placed?" where(":" And ", decomposeString(blood_rows.get(i).get(0),"sangue.tipo_sangue")));

            }

            if(!blood_rows.get(i).get(1).isEmpty()){
                if(i>0&&!or_placed){
                    query.append(" or( ");
                    or_placed=true;
                }
                validateNumber(blood_rows.get(i).get(1));
                conditions++;
                where_placed = true;
                if(i==0)
                    query.append(" where (");
                query.append(" sangue.preco  " + blood_rows.get(i).get(1) );
            }
            if(!stock_rows.get(i).get(0).isEmpty())
            {
                if(i>0&&!or_placed){
                    query.append("or(");
                    or_placed=true;
                }
                validateText(stock_rows.get(i).get(0));
                conditions++;
                where_placed=true;
                query.append(String.format("%s %s ",!where_placed?" where(":" And ",composeLikeStatements(stock_rows.get(i).get(0), "stock.morada")));
            }


            if(conditions==0){
                break;
            }
            else
                query.append(")");
        }

        System.out.println(query);
        return null;
    }

    public static void validateText(String name){
        //trim the whitespaces of the string, at the beginning and at the end
        name = name.trim();

        //see if the first character is a quote and if it is then validate with
        //quotes, else is is always a valid name, no matter what the user does
        if(name.charAt(0) != '"') {
            throw new RuntimeException("No quotes in text");
        }
        else{
            validateWithQuotes(name);
        }
    }

    public static void validateWithQuotes(String name){
        //see if the user inserted quotes, it it did then we know we have a sequence of
        //"text enclosed in quotes" logical operators ...
        int current_character_index = 0;

        while (current_character_index < name.length()) {
            //find the first '"'
            current_character_index = name.indexOf('"', current_character_index);

            if(current_character_index == -1){
                throw new RuntimeException("Text without quotes");
            }
            //find the second '"'
            current_character_index = name.indexOf('"', current_character_index + 1);

            //there is no enclosing quote
            if (current_character_index == -1) {
                throw new RuntimeException("the quotes were not closed");
            }

            //there is an enclosing quote so lets see if after it we have an 'or' or an 'and'
            int index_of_or = name.indexOf("or", current_character_index);
            int index_of_and = name.indexOf("and", current_character_index);

            //advance the current character index
            ++current_character_index;

            //if there are no operators
            if(index_of_or == -1 && index_of_and == -1){
                if(current_character_index == name.length()){
                    return;
                }
                else{
                    throw new RuntimeException("No quotes on string");
                }
            }

            //now note that both operators might have been found so we must get the position
            //of the operator that is before
            if(index_of_or == -1){
                current_character_index = index_of_and + 3;
            }
            else if(index_of_and == -1){
                current_character_index = index_of_or + 2;
            }
            //if both exist then we must find who is before the other
            else{
                current_character_index = index_of_or < index_of_and ? index_of_or + 2 : index_of_and + 2;
            }

            if(current_character_index == name.length()){
                throw new RuntimeException("operator without operand!");
            }
        }
    }

    private static String composeLikeStatements(String statement_to_compose, String field){
        statement_to_compose = statement_to_compose.trim();
        //like "this" or like "that"
        // 1º " ->Like %
        // "Maria" or "Jose" and "Pedro" => Like %Maria% or Like "%Jose%" and Like "%Pedro%"
        StringBuilder string_to_return = new StringBuilder();

        //see if the user inserted quotes, it it did then we know we have a sequence of
        //"text enclosed in quotes" logical operators ...
        int current_character_index = 0;
        boolean and_present = false;
        boolean or_present = false;
        boolean first_time = true;

        while (current_character_index < statement_to_compose.length()) {
            //find the first '"'
            int first_quote = statement_to_compose.indexOf('"', current_character_index);

            if(first_quote == -1){
                throw new RuntimeException("Text without quotes");
            }

            //find the second '"'
            current_character_index = statement_to_compose.indexOf('"', first_quote + 1);

            //there is no enclosing quote
            if (current_character_index == -1) {
                throw new RuntimeException("the quotes were not closed");
            }

            //we got two quotes so there must be something between this quotes which is the text that must be
            //in the like
            String text_between_quotes = statement_to_compose.substring(first_quote + 1, current_character_index);

            //advance the current character index past this quote
            ++current_character_index;


            if(first_time){
                string_to_return.append(field + " LIKE '%" + text_between_quotes + "%'");
                first_time = false;
            }
            else if(or_present){
                string_to_return.append(" or " + field + " LIKE '%" + text_between_quotes + "%'");
            }
            else if(and_present){
                string_to_return.append(" and " + field + " LIKE '%" + text_between_quotes + "%'");
            }

            //there is an enclosing quote so lets see if after it we have an 'or' or an 'and'
            int index_of_or = statement_to_compose.indexOf("or", current_character_index);
            int index_of_and = statement_to_compose.indexOf("and", current_character_index);
            if (index_of_or == -1 && index_of_and == -1) {
                and_present = false;
                or_present = false;
                continue;
            }

            //now note that both operators might have been found so we must get the position
            //of the operator that is before
            if(index_of_or == -1){
                current_character_index = index_of_and + 3;
                or_present = false;
                and_present = true;
            }
            else if(index_of_and == -1){
                current_character_index = index_of_or + 2;
                and_present = false;
                or_present = true;
            }
            //if both exist then we must find who is before the other
            else{
                if(index_of_and < index_of_or){
                    current_character_index = index_of_and + 3;
                    and_present = true;
                    or_present = false;
                }
                else{
                    current_character_index = index_of_or + 2;
                    or_present = true;
                    and_present = false;
                }
            }
        }

        if(and_present || or_present){
            throw new RuntimeException("Operator without operand!");
        }
        else{
            return string_to_return.toString();
        }
    }

    private static String decomposeString(String string_to_decompose, String field_name){
        string_to_decompose = string_to_decompose.trim();
        return string_to_decompose.charAt(0) == '"' ?
                decomposeBloodString(string_to_decompose, field_name) : decomposeNumberString(string_to_decompose, field_name);
    }

    private static String decomposeBloodString(String string_to_decompose, String field){
        StringBuilder string_to_return = new StringBuilder();

        //see if the user inserted quotes, it it did then we know we have a sequence of
        //"text enclosed in quotes" logical operators ...
        int current_character_index = 0;
        boolean and_present = false;
        boolean or_present = false;
        boolean first_time = true;

        while (current_character_index < string_to_decompose.length()) {
            //find the first '"'
            int first_quote = string_to_decompose.indexOf('"', current_character_index);

            if(first_quote == -1){
                throw new RuntimeException("Text without quotes");
            }

            //find the second '"'
            current_character_index = string_to_decompose.indexOf('"', first_quote + 1);

            //there is no enclosing quote
            if (current_character_index == -1) {
                throw new RuntimeException("the quotes were not closed");
            }

            //we got two quotes so there must be something between this quotes which is the text that must be
            //in the like
            String text_between_quotes = string_to_decompose.substring(first_quote + 1, current_character_index);

            //advance the current character index past this quote
            ++current_character_index;


            if(first_time){
                string_to_return.append(field + " = '" + text_between_quotes + "'");
                first_time = false;
            }
            else if(or_present){
                string_to_return.append(" or " + field + " = '" + text_between_quotes + "'");
            }
            else if(and_present){
                string_to_return.append(" and " + field + " = '" + text_between_quotes + "'");
            }

            //there is an enclosing quote so lets see if after it we have an 'or' or an 'and'
            int index_of_or = string_to_decompose.indexOf("or", current_character_index);
            int index_of_and = string_to_decompose.indexOf("and", current_character_index);
            if (index_of_or == -1 && index_of_and == -1) {
                and_present = false;
                or_present = false;
                continue;
            }

            //now note that both operators might have been found so we must get the position
            //of the operator that is before
            if(index_of_or == -1){
                current_character_index = index_of_and + 3;
                or_present = false;
                and_present = true;
            }
            else if(index_of_and == -1){
                current_character_index = index_of_or + 2;
                and_present = false;
                or_present = true;
            }
            //if both exist then we must find who is before the other
            else{
                if(index_of_and < index_of_or){
                    current_character_index = index_of_and + 3;
                    and_present = true;
                    or_present = false;
                }
                else{
                    current_character_index = index_of_or + 2;
                    or_present = true;
                    and_present = false;
                }
            }
        }

        if(and_present || or_present){
            throw new RuntimeException("Operator without operand!");
        }
        else{
            return string_to_return.toString();
        }
    }

    private static String decomposeNumberString(String string_to_decompose, String field){
        StringBuilder string_to_return = new StringBuilder();

        //>12 and <78 => [<, >, <=, >=, =]


        int current_character_index = 0;
        boolean and_present = false;
        boolean or_present = false;
        boolean first_time = true;

        while (current_character_index < string_to_decompose.length()) {
            //the range of the number an operator is [current_character_index, end]
            int end = string_to_decompose.indexOf(' ', current_character_index + 1);


            //we got two quotes so there must be something between this quotes which is the text that must be
            //in the like
            String operator_and_number = string_to_decompose.substring(
                    current_character_index, end == -1 ? string_to_decompose.length() : end
            ).trim();

            //extract the operator from it
            int operator_end=0;
            while(!Character.isDigit(operator_and_number.charAt(operator_end))){
                ++operator_end;
            }
            String operator = operator_and_number.substring(0, operator_end);
            String number = operator_and_number.substring(operator_end,  end == -1 || end > operator_and_number.length() ? operator_and_number.length() : end );


            //advance the current character index past this number
            current_character_index += operator_and_number.length();

            if(first_time){
                string_to_return.append(field + " " + operator + " " + number + " ");
                first_time = false;
            }
            else if(or_present){
                string_to_return.append(" or " + field + " " + operator + " " + number + " " );
            }
            else if(and_present){
                string_to_return.append(" and " + field + " " + operator + " " + number + " " );
            }

            //there is an enclosing quote so lets see if after it we have an 'or' or an 'and'
            int index_of_or = string_to_decompose.indexOf("or", current_character_index);
            int index_of_and = string_to_decompose.indexOf("and", current_character_index);
            if (index_of_or == -1 && index_of_and == -1) {
                and_present = false;
                or_present = false;
                continue;
            }

            //now note that both operators might have been found so we must get the position
            //of the operator that is before
            if(index_of_or == -1){
                current_character_index = index_of_and + 3;
                or_present = false;
                and_present = true;
            }
            else if(index_of_and == -1){
                current_character_index = index_of_or + 2;
                and_present = false;
                or_present = true;
            }
            //if both exist then we must find who is before the other
            else{
                if(index_of_and < index_of_or){
                    current_character_index = index_of_and + 3;
                    and_present = true;
                    or_present = false;
                }
                else{
                    current_character_index = index_of_or + 2;
                    or_present = true;
                    and_present = false;
                }
            }
        }

        if(and_present || or_present){
            throw new RuntimeException("Operator without operand!");
        }
        else{
            return string_to_return.toString();
        }
    }



    /**
     * Function to validate filters that require input of number filters
     * @param number filter introduced by the user
     */
    static public void validateNumber(String number) {
        //System.out.println(number);
        if (number.charAt(0) != '<' && number.charAt(0) != '>' && number.charAt(0) != '=' || number.length() < 2)
            throw new RuntimeException("Inexistent operator \"<>=\"");
            //parsing the first operator the user introduced to filter the query
        String operator = number.toUpperCase().substring(0, 1);
        //position of condition OR in the filter, -1 if non existent
        int condition_or = number.toUpperCase().indexOf("OR");
        //position of condition AND in the filter, -1 if non existent
        int condition_and = number.toUpperCase().indexOf("AND");
        //checking if the first operator contans equal statement in addition
        if ((operator.equals("<") || operator.equals(">")) && number.toUpperCase().substring(1, 2).equals("=")) {
            operator += "=";

        }
        _validate_number_with_composed_operator(number, condition_or, condition_and, operator);


    }

    /**
     *
     * @param number expression introduced by the user, called recursively if more then one operation
     * @param condition_or index of OR statement, -1 if non existent
     * @param condition_and index of AND statement, -1 if non existent
     * @param operator current operator chosen by the user
     * @throws RuntimeException exceltion thrown if the syntax is invalid, with information to be shown to the user with information of what when wrong
     */
    static void _validate_number_with_composed_operator(String number, int condition_or, int condition_and, String operator) throws RuntimeException {
        //System.out.println(number);
        //offset between conditions to recursively call the function with next part of the filter after And or OR
        int condition_offset=condition_or>0?3:condition_and>0?4:0;
        //if there is an And or OR condition, this segment will be executed
        if (condition_or != -1 || condition_and != -1) {
            try {
                String first_condition = number.substring(operator.length(), Math.max(condition_or, condition_and) - 1);
                //number to be used in the current filter
                int filter_number = Integer.parseInt(first_condition);
                //checking if the user used a space between conditions, else it will cause an exception
                if(number.charAt(Math.max(condition_or, condition_and)-1)!=' ')
                    throw new RuntimeException("invalid syntax, a space must separate the operator and the number");
                //construction a new partial statement to call this function recursivly to parte the rest of the query
                String recursive_number=number.substring(Math.max(condition_or, condition_and)+condition_offset) ;
                //recursive call to the function to parte the rest of the query
                validateNumber(recursive_number);
            }
            catch (NumberFormatException numberFormatException) {
                throw new RuntimeException("invalid number inserted");
            }
        }
        //if there is no and or Or statements this will be executed instead

        else {

            try { //= 12
                //number to be used in the current filter
                int filter_number = Integer.parseInt(number.substring(operator.length()));
            } catch (NumberFormatException numberFormatException) {
                throw new RuntimeException("Invalid number inserted");
            }
        }

    }


    public static void validateSex(String sex){
        //trim the whitespaces of the string, at the beginning and at the end
        sex = sex.trim();

        //we must only have one character wich must be eitheir an 'F' or an 'M'
        if(!sex.equals("F") && !sex.equals("M")){
            throw new RuntimeException("Sex must be either M or F");
        }
    }



    /**
     * @param column_names the names of the columns that will appear in the TableView
     * @return a TableView with the specified columns.
     */
    TableView<ArrayList<String>> getTableViewWithColumns(String ...column_names){
        //create the TableView to configure
        TableView<ArrayList<String>> to_configure = new TableView<>();

        //create a column for each column name that was supplied
        int column_number = 0;
        for(String column_name : column_names) {
            TableColumn<ArrayList<String>, String> table_column = new TableColumn<>(column_name);
            table_column.setCellFactory(TextFieldTableCell.forTableColumn());
            table_column.setEditable(true);

            int final_column_number = column_number;
            table_column.setOnEditCommit((event)->{
                event.getTableView().getItems().get(event.getTablePosition().getRow()).set(
                        final_column_number, event.getNewValue()
                );
            });

            table_column.setCellValueFactory(
                    line -> new ReadOnlyObjectWrapper<>(line.getValue().get(final_column_number))
            );
            to_configure.getColumns().add(table_column);
            ++column_number;
        }
        //set the TableView as editable
        to_configure.setEditable(true);


        //create the row to add to the table
        for(int i=0 ; i != 10; ++i) {
            ArrayList<String> row = new ArrayList<>(column_names.length);
            int size = column_names.length;
            while(size-- != 0) {
                row.add("");
            }
            to_configure.getItems().add(row);
        }
        to_configure.setVisible(false);
        return to_configure;
    }

    ComboBox<String> getComboBoxFromContent(VBox content){
        return (ComboBox<String>) ((HBox)content.getChildren().get(1)).getChildren().get(0);
    }

    ArrayList<String> getCheckBoxListFromContent(VBox content){
        ArrayList<String> selected_checkboxes = new ArrayList<>();

        //get the checkbox list
        HBox checkbox_list = (HBox) content.getChildren().get(0);

        for(Node vbox : checkbox_list.getChildren()){
            VBox check_boxes = (VBox) ((ScrollPane)((VBox)vbox).getChildren().get(1)).getContent();
            for(Node check_box : check_boxes.getChildren()){
                if(((CheckBox)check_box).isSelected() ){
                    selected_checkboxes.add(
                            translateTableName(((Label)((VBox)vbox).getChildren().get(0)).getText()) + "." + translateField(((CheckBox)check_box).getText())
                    );
                }
            }
        }
        //return the selected checkboxes names
        return selected_checkboxes;
    }

    /**
     * Translates the name of a table from english to portuguese which is the idiom of the database tables.
     * @param table_name the name of the table to be translated.
     * @return the translated table name.
     */
    String translateTableName(String table_name){
        return switch (table_name) {
            case "Donor" -> "doador";
            case "Blood" -> "sangue";
            case "Transfusion" -> "transfusao";
            default -> table_name;
        };
    }

    /**
     * Translates a field name to its name to the database name of such field.
     * @param field_name the name of the field to be translated.
     * @return the translated field name.
     */
    String translateField(String field_name){
        return switch (field_name){
            case "name"->"nome";
            case "age"->"idade";
            case "sex"->"sexo";
            case "blood_type"-> "tipo_sangue";
            case "address" -> "morada";
            case "contact"->"contacto";
            case "price" -> "preco";
            default -> throw new RuntimeException("field " + field_name + " cannot be translated!");
        };
    }

    /**
     * Validates a date expression. A date expression is something of the form("[]" for grouping)
     * [sign][date][logical operator]...
     * @param date the date expression to be validated.
     * @throws RuntimeException in case an error occurs with the date.
     */
    public static void validateDate(String date){
        //we have something like this [signal][date] [operator] [signal][date]...

        //construct a Scanner that reads dates or operators
        Scanner parser = new Scanner(date);
        boolean operator_is_present = false;
        while(parser.hasNext()){
            //read a signal
            boolean is_sign_present = parser.hasNext("(=|<=|>=|=|<|>).*");
            if(is_sign_present){
                //if the sign is present we must read it all
                String unprocessed_string = parser.nextLine();
                int index = 0;
                while(!Character.isDigit(unprocessed_string.charAt(index))){
                    ++index;
                }

                //now that the sign is off, put it back on the Scanner
                parser = new Scanner(unprocessed_string.substring(index));
            }

            //read the date
            boolean is_date_present = parser.hasNext("(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[012])\\/\\d{4}");
            if(is_date_present){
                parser.next("(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[012])\\/\\d{4}");
            }
            if(is_sign_present && !is_date_present){
                throw new RuntimeException("signal without date");
            }
            if(!is_sign_present && !is_date_present){
                throw new RuntimeException("neitheir a sign nor a date are present");
            }
            if(!is_sign_present){
                throw new RuntimeException("date without sign");
            }
            //read an operator
            operator_is_present = parser.hasNext("(or|and).*");
            if(operator_is_present){
                //if the operator is present then read it
                String unprocessed_string = parser.nextLine();
                int index = 0;
                while(Character.isAlphabetic(unprocessed_string.charAt(index)) || Character.isWhitespace(unprocessed_string.charAt(index)) ){
                    ++index;
                }

                //now that the sign is off, put it back on the Scanner
                parser = new Scanner(unprocessed_string.substring(index));
            }
        }
    }


}


