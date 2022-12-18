package gui;

import gui.resources.ImageManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import model.ERROR_MESSAGES;

import java.util.Timer;
import java.util.TimerTask;

public class ToastMessage {
    private ToastMessage() {}

    /**
     * Creates and shows a dialog with the icons/cancel.png image
     * and the error message that was passed through the message parameter
     * @param owner
     * @param message
     */
    public static void show(Window owner, ERROR_MESSAGES message) {
        final Dialog popup = new Dialog();
        //popup.setAutoHide(true);
        //popup.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_BOTTOM_LEFT);
        popup.setTitle("Error message");
        double x = owner.getX();
        double y = owner.getY();
        double w = owner.getWidth();
        double h = owner.getHeight();
        Image icon = ImageManager.getImage("cancel.png");
        Label lbMessage = new Label(message.toString());
        DialogPane dialogPane = popup.getDialogPane();

        ImageView imageView = new ImageView(icon);


        dialogPane.setContent(new VBox(8, lbMessage));
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialogPane.setGraphic(imageView);
        popup.show();
        //popup.show(owner,x+w/2-message.length()/2.0*lbMessage.getFont().getSize(),y+0.80*h);
    }


    public static void show(Window owner, String message) {
        final Dialog popup = new Dialog();
        //popup.setAutoHide(true);
        //popup.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_BOTTOM_LEFT);
        popup.setTitle("Error message");
        double x = owner.getX();
        double y = owner.getY();
        double w = owner.getWidth();
        double h = owner.getHeight();
        Image icon = ImageManager.getImage("cancel.png");
        Label lbMessage = new Label(message);
        DialogPane dialogPane = popup.getDialogPane();

        ImageView imageView = new ImageView(icon);


        dialogPane.setContent(new VBox(8, lbMessage));
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialogPane.setGraphic(imageView);
        popup.show();
        //popup.show(owner,x+w/2-message.length()/2.0*lbMessage.getFont().getSize(),y+0.80*h);
    }


}
