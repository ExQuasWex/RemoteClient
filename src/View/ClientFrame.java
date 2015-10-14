package View;

import Controller.Controller;
import clientModel.StaffInfo;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

import javax.tools.Tool;
import java.awt.*;

/**
 * Created by Didoy on 9/30/2015.
 */
public class ClientFrame {

    private static CustomStage clientStage ;
    private static ClientFrame mainframe = new ClientFrame();
    private BorderPane root;

    private ClientFrame(){

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        clientStage =  new CustomStage(30,30,screen.getWidth(),screen.getHeight());
        clientStage.initStyle(StageStyle.DECORATED);
        clientStage.setMaxWidth(screen.getWidth());
        clientStage.setMaxHeight(screen.getHeight());

        clientStage.setHeight(clientStage.getMaxHeight());
        clientStage.setWidth(clientStage.getMaxWidth());

        SlidePane sp = new SlidePane(500);

        root = new BorderPane();
        root.setCenter(new Label("Center"));
        root.setLeft(sp);

        Scene scene = new Scene(root);

        clientStage.setScene(scene);
        clientStage.centerOnScreen();
        clientStage.show();
    }

    public static ClientFrame getInstance(){
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
    public void showClient(){
        clientStage.showWithAnimation();
    }


}
