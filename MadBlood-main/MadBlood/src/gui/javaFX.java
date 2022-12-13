package gui;

import gui.controllers.DataAnalystViewController;
import gui.controllers.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.BloodBankDatabase;

import java.io.IOException;

public class javaFX extends Application {
    BloodBankDatabase model;

    @Override
    public void init() throws Exception {
        super.init();
        model = new BloodBankDatabase();
    }

    @Override
    public void start(Stage stage) throws Exception {
        configureStage(stage);
        stage.setOnCloseRequest(evt ->{  //when we close one of the screens, it will automatically close the others
            Platform.exit();
        });
    }

    /**
     * Loads the login view from the XML file and sets its controller
     */
    private void configureStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resources/fxml/login.fxml"));
        LoginController loginController = new LoginController(model);
        loader.setController(loginController);
        Parent root = loader.load();
        //root.setUserData(model);
        Scene scene = new Scene(root);
        stage.setTitle("MadBlood");
        stage.setScene(scene);
        stage.show();
    }
}
