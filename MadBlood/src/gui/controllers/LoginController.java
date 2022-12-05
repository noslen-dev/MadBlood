package gui.controllers;

import gui.ToastMessage;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.BloodBankDatabase;
import model.ERROR_MESSAGES;


public class LoginController implements Initializable {

    BloodBankDatabase model;

    @FXML
    public Pane root;
    @FXML
    public PasswordField idPasswordField;
    @FXML
    public TextField idUsernameField;
    @FXML
    public Button idBtnLogin;

    public LoginController(BloodBankDatabase model){
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        registerHandlers();
    }

    /**
     * Registers the handlers necessary for the UI to work
     * binding the idBtnLogin to the idUsernameField and idPasswordField
     * so they need to be filled with text before the button becomes available.
     * Also sets a onClick action on the button so it calls the model.login(username,password) function
     * on the model and checks to see if any errors occurred
     */
    public void registerHandlers(){
        idBtnLogin.disableProperty().bind(Bindings.createBooleanBinding( () ->
                        idUsernameField.getText().trim().isEmpty(), idUsernameField.textProperty()
                )
                // If you want to check more text field, use it as by removing comments
                .or(Bindings.createBooleanBinding( () ->
                        idPasswordField.getText().trim().isEmpty(), idPasswordField.textProperty()
                )
                )
        );

        idBtnLogin.setOnMouseClicked(mouseEvent -> {
            ERROR_MESSAGES returnMessage = model.login(idUsernameField.getText(),idPasswordField.getText());
            if(returnMessage != ERROR_MESSAGES.USER_IS_DATA_ANALYST && returnMessage != ERROR_MESSAGES.USER_IS_ADMINISTRATIVE)
                ToastMessage.show(root.getScene().getWindow(),returnMessage);

            if(returnMessage == ERROR_MESSAGES.USER_IS_DATA_ANALYST){
                try {
                    changeToDataAnalyst();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else{
                changeToAdministrative();
            }

        });

    }

    /**
     * Loads the data analyst view from the XML file and sets its controller
     */
    public void changeToDataAnalyst() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/fxml/data_analyst_view.fxml"));
        DataAnalystViewController dataAnalystViewController = new DataAnalystViewController(model);
        loader.setController(dataAnalystViewController);
        Parent root = loader.load();

        Stage window = (Stage) idBtnLogin.getScene().getWindow();
        window.setScene(new Scene(root));

        window.show();
    }

    public void changeToAdministrative(){

    }
}
