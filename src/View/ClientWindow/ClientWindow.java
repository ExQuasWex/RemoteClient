package View.ClientWindow;

import Controller.Controller;
import View.Login.LoginWindow;
import clientModel.StaffInfo;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.Optional;

/**
 * Created by Didoy on 9/30/2015.
 */
public class ClientWindow extends Stage{

    private static ClientWindow mainframe = new ClientWindow();
    private BorderPane root;

    private ClientWindow(){

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        SlidePane sp = new SlidePane(500);

        root = new BorderPane();
        root.setCenter(new Label("Center"));
        root.setLeft(sp);

        Scene scene = new Scene(root);

        setWidth(screen.getWidth());
        setHeight(screen.getHeight());

        setScene(scene);
        centerOnScreen();
        show();

    }

    public static ClientWindow getInstance(){
        return mainframe;
    }
    public void showAccount(){
        StaffInfo staffInfo = Controller.getInstance().getStaffInfo();
        GridPane gp = new GridPane();

        // Labels
        Label nameL = new Label("Name");
        Label usernameL = new Label("Username");
        Label addressL = new Label("Address");
        Label contactL = new Label("Contact");
        Label passwordL = new Label("Password");
        Label passwordConfirmL = new Label("Retype Password");
        Label entriesL = new Label("Total Entries");

        //TextField
        TextField nameField = new TextField(staffInfo.getName());
        TextField usernameField = new TextField(staffInfo.getUsername());
        TextField addresField = new TextField(staffInfo.getAddress());
        TextField contactField = new TextField(staffInfo.getContact());
        TextField passwordField = new TextField();
        TextField passwordConfirmField = new TextField();
        TextField entriesField = new TextField();
        entriesField.setEditable(false);

        String entries = String.valueOf(staffInfo.getEntries());
        entriesField.setText(entries);


        // first column
        gp.setConstraints(nameL,    0,0,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(usernameL,0,1,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(addressL, 0,2,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(contactL, 0,3,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(passwordL,0,4,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(passwordConfirmL,0,5,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(entriesL, 0,6,1,1, HPos.CENTER, VPos.CENTER);

        // second column
            gp.setConstraints(nameField,1,0,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(usernameField,1,1,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(addresField,  1,2,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(contactField, 1,3,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(passwordField,1,4,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(passwordConfirmField,1,5,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(entriesField, 1,6,1,1, HPos.CENTER,VPos.CENTER);


        gp.setVgap(10);
        gp.setHgap(5);
        gp.setAlignment(Pos.CENTER);
        gp.getChildren().addAll(nameL, usernameL,addressL,contactL, passwordL, passwordConfirmL, entriesL,
                nameField,usernameField,addresField,contactField, passwordField, passwordConfirmField,entriesField );

        root.setCenter(null);
        root.setCenter(gp);


    }
    public void showFamilyForm(){
        FamilyForm fm = new FamilyForm();
        root.setCenter(null);
        root.setCenter(fm);
    }

    public void Logout(){
        Alert alertBox = new Alert(Alert.AlertType.CONFIRMATION);
        alertBox.setContentText("Are you sure you want to Logout?");
        alertBox.setHeaderText(null);

        Optional<ButtonType> result = alertBox.showAndWait();
        if (result.get() == ButtonType.OK){
            closeClientWindow();

        }else if (result.get() == ButtonType.CANCEL){
            alertBox.close();
        }

    }

    public void closeClientWindow(){
        Controller.getInstance().Logout();
        LoginWindow.getInstantance().showLoginWindow(true);
        System.out.println("log out fnish");
        close();

    }
    public void showClientWindow(){
        show();

    }


}
