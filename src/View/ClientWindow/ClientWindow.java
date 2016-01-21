package View.ClientWindow;

import Controller.Controller;
import Remote.Method.FamilyModel.Family;
import View.Login.CustomStage;
import View.Login.LoginWindow;
import clientModel.StaffInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import utility.Utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Didoy on 9/30/2015.
 */
public class ClientWindow extends CustomStage implements Runnable{

    private static ClientWindow mainframe = new ClientWindow();
    private BorderPane root;
    private FamilyForm fm;
    private Button updateButton;
    private StaffInfo staffInfo;

    private boolean isNotified = false;
    private ClientWindow(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        System.out.println(screen.getWidth());
        System.out.println(screen.getWidth());

        SlidePane sp = new SlidePane(screen.getWidth()/4);

        root = new BorderPane();
        root.setCenter(new Label("Center"));
        root.setLeft(sp);


        updateButton = new Button();
        updateButton.setText("Update");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/ClientWindowCSS.css");

        setWidth(screen.getWidth());
        setHeight(screen.getHeight());

        // Listener for familyForm
        fm = new FamilyForm();

                fm.addFamilyFormListener(new FamilyFormListener() {
                    @Override
                    public void handle(Family family) {
                        addFamily(family);
                        System.out.println("SAve family");
                    }
                });

                fm.addSearchListener(new SearchListener() {
                    @Override
                    public void SearchEvent(String searchName) {
                        search(searchName);
                    }
                });


        setScene(scene);
        centerOnScreen();

    }

    private void search(String Searchedname){
        if (Controller.getInstance().isServerConnected()){

                ArrayList list = Controller.getInstance().searchedList(Searchedname);

                if (Searchedname.equals("")){
                    Controller.showMessageBox("Search Box is empty", Alert.AlertType.ERROR);
                }else if (list.isEmpty()){
                    Controller.showMessageBox("No records found", Alert.AlertType.ERROR);
                }else {
                    System.out.println("show records");
                   // Controller.getInstance().showSearchedList(list);
                    showSearchedTable(list);
                }
        }else{
                ShowConnectingWindow(root);
                connect();
        }

    }

    private void connect(){

                Thread thread = new Thread(new Runnable() {
                    @Override
                        public void run() {

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            while(!Controller.getInstance().isServerConnected()){
                                }
                                if (Controller.getInstance().isServerConnected()){

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            root.setDisable(false);
                                            root.setCenter(null);
                                            root.setCenter(fm);
                                            System.out.println("CONNECTED");
                                        }
                                    });

                                }
                        }
                });

            thread.start();

    }

    public boolean addFamily(Family family){

    boolean success = false;

      if (Controller.getInstance().isServerConnected()){

          success = Controller.getInstance().addFamilyInfo(family);

                if (success){
                    Controller.showMessageBox("Successfully Save Family Information", Alert.AlertType.INFORMATION);

                }else {

                    if (Controller.getInstance().getMethodIdenifier().equals("NOTIFY")){

                    }else {
                        Controller.showMessageBox("There was problem occur, please try again after few seconds", Alert.AlertType.ERROR);
                    }

                }
      }else {
                 ShowConnectingWindow(root);
                 // Add connection functionality here
      }
       return success;
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
        Label OldPassword = new Label("Old Password");

        //TextField
        TextField nameField = new TextField(staffInfo.getName());
        TextField usernameField = new TextField(staffInfo.getUsername());
        TextField addresField = new TextField(staffInfo.getAddress());
        TextField contactField = new TextField(staffInfo.getContact());
        PasswordField passwordField = new PasswordField();
        PasswordField passwordConfirmField = new PasswordField();
        TextField entriesField = new TextField();
        PasswordField oldPasswordField = new PasswordField();

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
        gp.setConstraints(OldPassword, 0,7,1,1, HPos.CENTER, VPos.CENTER);



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
                entriesField, OldPassword, oldPasswordField, updateButton );

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



                    if (!newPassword.equals(passwordConfirmField.getText())){
                        Utility.showMessageBox("Password Don't match", Alert.AlertType.ERROR);
                    }else if (!oldPassword.equals(staffInfo.getPassword())){
                        Utility.showMessageBox("Old password don't match", Alert.AlertType.ERROR);
                    }else {

                            staffInfo.setName(name);
                            staffInfo.setUsername(username);
                            staffInfo.setAddress(address);
                            staffInfo.setPassword(newPassword);
                            staffInfo.setContact(Contact);

                            if (Controller.getInstance().updateStaffInfo(staffInfo))  {
                                Utility.showMessageBox("You successfully updated your personal information", Alert.AlertType.INFORMATION);
                            }else {
                                Utility.showMessageBox("failed to Update your personal information, please try again lateer", Alert.AlertType.ERROR);
                            }

                    }

            }
        });

    }


    public  boolean showConfirmationMessage(String message,Alert.AlertType alertType, ArrayList list){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(alertType);
                alert.setTitle("Information Message");
                alert.setHeaderText(message);
                alert.setContentText(null);


                System.out.println("shoowww");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    showSearchedTable(list);
                } else {
                    isNotified = false;
                }
            }
        });

        return  isNotified;
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
        // logout from database
        Controller.getInstance().Logout();
        // logout UI
        root.setRight(null);
        Utility.ClearComponents(fm);
        SearchTabWindow.getInstance().close();
        LoginWindow.getInstantance().showLoginWindow(true);
        System.out.println("log out fnish from clientWindow");
        close();

    }

    public  void showSearchedTable(ArrayList<Family> data){
        SearchTable table = new SearchTable(root);
        table.setData(data);

        root.setRight(table);

    }

    public  void  removeSearchTable(){
        root.setRight(null);
    }
    public void showClientWindow(){
        show();
    }


    @Override
    public void run() {

    }
}
