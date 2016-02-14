package View.ClientWindow;

import Controller.Controller;
import Remote.Method.FamilyModel.Family;
import View.ClientWindow.Listeners.FamilyFormListener;
import View.ClientWindow.Listeners.SearchListener;
import View.ClientWindow.Listeners.SearchTableListener;
import View.ClientWindow.Listeners.SlidePaneListener;
import View.Login.CustomStage;
import View.Login.LoginWindow;
import clientModel.StaffInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import utility.Utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Didoy on 9/30/2015.
 */
public class ClientWindow extends CustomStage{

    private static BorderPane root;
    private  FamilyForm fm;
    private  SearchPane searchPane = new SearchPane();
    private HBox bottomPane = new HBox();

    private  Button updateButton;

    private  StaffInfo staffInfo;
    private static SearchTable searchtable ;
    private  SlidePane sp;

    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    private static boolean  isNotified = false;

    public ClientWindow(){

        searchPane.LoadFXML();
        bottomPane.setPrefHeight(50);
        bottomPane.setStyle("-fx-background-color: #0082b2");

        sp = new SlidePane(screen.getWidth()/4);
        fm = new FamilyForm();
        fm.setGridLinesVisible(true);

        root = new BorderPane();
        root.setAlignment(searchPane, Pos.TOP_CENTER);
        root.setCenter(new Label("Center"));
        root.setLeft(sp);
        root.setTop(searchPane);
        root.setBottom(bottomPane);

        searchtable = new SearchTable(root);

        updateButton = new Button();
        updateButton.setText("Update");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/ClientWindowCSS.css");

        setWidth(screen.getWidth());
        setHeight(screen.getHeight());

        root.setRight(null);


        implementSlidePaneListener();

                //  adding form listener

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


                searchtable.addSearchTableListener(new SearchTableListener() {
                    @Override
                    public void rollUp() {
                        removeSearchTable();
                    }
                });

        setScene(scene);
        centerOnScreen();

    }


    // Implement SlidePane Listener Interface to slidepane class
    private void implementSlidePaneListener(){

        double smallW = screen.getWidth()/4 ;
        smallW = smallW/2;
        double bigW = screen.getWidth()/4 ;

        final double finalSmallW = smallW;
        sp.addSlidePaneListener(new SlidePaneListener() {
            @Override
            public void showAccount() {
                addAccountForm();
            }

            @Override
            public void showFamilyForm() {
                addFamilyForm();
            }

            @Override
            public void showEditForm() {
                ShowEditForm();
            }

            @Override
            public void Logout() {
                LogoutWindow();
            }

            @Override
            public void offMenu() {
                Node node = root.getCenter();
                root.setMargin(node, new Insets(0));
                root.setMargin(node, new Insets(0, bigW, 0, bigW));
                root.setCenter(null);
                root.setCenter(node);
                System.out.println("Resize");
            }

            @Override
            public void onMenu() {
                Node node = root.getCenter();
                root.setCenter(null);
                root.setMargin(node, new Insets(20, finalSmallW, 20, finalSmallW));
                root.setCenter(node);
                System.out.println("Resize");
            }
        });


    }


    private void ShowEditForm(){
        root.setCenter(null);
        FamilyForm familyForm  = new FamilyForm();
        root.setCenter(familyForm);


    }

    private void search(String Searchedname){
        if (Controller.getInstance().isServerConnected( )){

                ArrayList list = Controller.getInstance().searchedList(Searchedname);

                if (Searchedname.equals("")){
                    Utility.showMessageBox("Search Box is empty", Alert.AlertType.ERROR);
                }else if (list.isEmpty()){
                    Utility.showMessageBox("No records found", Alert.AlertType.ERROR);
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
                            while(!Controller.getInstance().isServerConnected( )){
                                }
                                if (Controller.getInstance().isServerConnected( )){

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

    public void addFamily(Family family){

    boolean isSucceed = false;

      if (Controller.getInstance().isServerConnected( )){

                  if (isNotified){
                        boolean isConfirm = Utility.showConfirmationMessage("Are you sure you want to add this data?", Alert.AlertType.CONFIRMATION);

                            if (isConfirm){
                                    isSucceed = Controller.getInstance().addFamilyInfo(true,family);
                                        if (isSucceed){
                                            isNotified = false;
                                        }
                            }else {
                                            isNotified = false;
                            }
                  }else {
                      isSucceed = Controller.getInstance().addFamilyInfo(false,family);
                  }
                        if (isSucceed){
                             Utility.showMessageBox("Successfully Save Family Information", Alert.AlertType.INFORMATION);

                        }else {

                            if (!Controller.getInstance().getMethodIdenifier().equals("NOTIFY")){
                                Utility.showMessageBox("There was problem occur, please try again after few seconds", Alert.AlertType.ERROR);
                            }
                        }
      }else {
                 ShowConnectingWindow(root);
                 // Add connection functionality here
      }
    }

    public void addAccountForm(){
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

                    if (newPassword.equals("") || newPassword.equals(null)){
                        Utility.showMessageBox("Your new password is empty", Alert.AlertType.ERROR);
                    }
                    else if (!newPassword.equals(passwordConfirmField.getText())){
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


    public  void addFamilyForm(){
        root.setCenter(null);
        root.setCenter(fm);
    }

    public  void LogoutWindow(){

        boolean isLogout = Utility.showConfirmationMessage("Are you sure you want to Logout?", Alert.AlertType.CONFIRMATION);
               if (isLogout){
                   closeClientWindow();
               }
    }

    public void closeClientWindow(){

        // logout from database
        Controller.getInstance().Logout();
        // logout UI
        removeSearchTable();
        close();

        SearchTabWindow.getInstance().close();
        new LoginWindow();
        System.out.println("log out fnish from clientWindow");

    }

    private static void showSearchedTable   (ArrayList<Family> data){

        // get back to FX application if ever we are in RMI TCP Connection(2) thread
                    searchtable.setData(data);
                    root.setRight(searchtable);
    }


    public  void  removeSearchTable(){
        root.setRight(null);
    }

/*
     controller call this method whenever it
     found similar family information in the database
 */
   public static void notifyClient( ArrayList familyList){

       if (!Platform.isFxApplicationThread()) {

               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                        showPromptNotification(familyList);
                   }
               });

       } else {
               showPromptNotification(familyList);
       }

    }
    private static  void showPromptNotification(ArrayList familyList){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information Message");
        alert.setHeaderText("We found Similar record of the person, Would You like to see it before proceeding?");
        alert.setContentText(null);

        Optional result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            isNotified = true;
            showSearchedTable(familyList);
        } else {
            isNotified = false;
        }

    }



}
