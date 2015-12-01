package View.ClientWindow;

import Controller.Controller;
import View.Login.LoginWindow;
import Family.Family;
import clientModel.StaffInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.Optional;

/**
 * Created by Didoy on 9/30/2015.
 */
public class ClientWindow extends Stage{

    private static ClientWindow mainframe = new ClientWindow();
    private BorderPane root;
    private FamilyForm fm;
    private Button updateButton;
    private    StaffInfo staffInfo;


    private ClientWindow(){

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();


        SlidePane sp = new SlidePane(500);

        root = new BorderPane();
        root.setCenter(new Label("Center"));
        root.setLeft(sp);

        updateButton = new Button();
        updateButton.setText("Update");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/ClientWindowCSS.css");

        setWidth(screen.getWidth());
        setHeight(screen.getHeight());

        // Listener of familyForm
        fm = new FamilyForm();
        fm.addFamilyFormListener(new FamilyFormListener() {
            @Override
            public void handle(Family family) {
                addFamily(family);
            }
        });

        setScene(scene);
        centerOnScreen();
        show();
    }

    public void addFamily(Family family){

        System.out.println(family.getFamilyinfo().getName());
       // return Controller.getInstance().addFamilyInfo(Family);
    }

    public static ClientWindow getInstance(){
        return mainframe;
    }

    public void showAccount(){
         staffInfo = Controller.getInstance().getStaffInfo();
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
        TextField oldPasswordField = new TextField();

        entriesField.setEditable(false);

        String entries = String.valueOf(staffInfo.getEntries());
        entriesField.setText(entries);

            updateButton.setPrefWidth(230);

        // first column
        gp.setConstraints(nameL,    0,0,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(usernameL,0,1,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(addressL, 0,2,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(contactL, 0,3,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(passwordL,0,4,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(passwordConfirmL,0,5,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(entriesL, 0,6,1,1, HPos.CENTER, VPos.CENTER);
        gp.setConstraints(new Label("Old Password"), 0,7,1,1, HPos.CENTER, VPos.CENTER);



        // second column
            gp.setConstraints(nameField,1,0,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(usernameField,1,1,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(addresField,  1,2,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(contactField, 1,3,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(passwordField,1,4,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(passwordConfirmField,1,5,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(entriesField, 1,6,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(oldPasswordField, 1,7,1,1, HPos.CENTER,VPos.CENTER);
        gp.setConstraints(updateButton, 0, 8, 2, 1, HPos.CENTER, VPos.CENTER);


        gp.setVgap(10);
        gp.setHgap(5);
        gp.setAlignment(Pos.CENTER);

        gp.getChildren().addAll(nameL, usernameL,addressL,contactL, passwordL, passwordConfirmL, entriesL,
                nameField,usernameField,addresField,contactField, passwordField, passwordConfirmField,
                entriesField, oldPasswordField, updateButton );

        root.setCenter(null);
        root.setCenter(gp);


        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    String name = nameField.getText();
                    String username = usernameField.getText();
                    String address = addresField.getText();
                    String newPassword = passwordField.getText();
                    String oldPassword = oldPasswordField.getText();
                    String Contact = contactField.getText();



                    if (newPassword != passwordConfirmField.getText()){
                        showValidation("Password Don't match");
                    }else if (oldPassword != staffInfo.getPassword()){
                        showValidation("Old password don't match");
                    }else {

                        staffInfo.setName(name);
                        staffInfo.setUsername(username);
                        staffInfo.setAddress(address);
                        staffInfo.setPassword(newPassword);
                        staffInfo.setContact(Contact);

                        Controller.getInstance().updateStaffInfo(staffInfo);
                    }

            }
        });

    }

    private void showValidation(String msgError){
        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setTitle("Error Information");
        alertBox.setContentText(msgError);
        alertBox.setHeaderText(null);

    }

    public void showFamilyForm(){
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
