package View;

import ClientModel.StaffRegister;
import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;
import utility.Utility;

import java.awt.*;
import java.util.regex.Pattern;

/**
 * Created by Didoy on 8/24/2015.
 */
public class LoginWindow  {

    private static CustomStage loginStage;
    private static  LoginWindow  instance = new LoginWindow();

    private  GridPane gridPane;
    private  GridPane discGridPane;
    private  TextField userText;
    private  Label statusLabel;
    private  PasswordField userPass;
    private  Scene scene;
    private  Button loginButton, cancelButton;

    private  ToggleButton loginToggle,registerToggle, forgotToggle;
    private  ToggleGroup toggleGroup;
    private  BorderPane root;
    private  HBox hBox;
    private  VBox registerVbox;
    private MessageWindow messageBox;

    private boolean isConnected;
    private  Controller ctr;

    private  LoginWindow(){

        ctr = new Controller();
        isConnected = ctr.isServerConnected();

        root = new BorderPane();
        gridPane = new GridPane();
        discGridPane = new GridPane();
        hBox = new HBox();

        // this is for register layout
        registerVbox = new VBox(5);
        registerVbox.setAlignment(Pos.CENTER);
        registerVbox.setFillWidth(false);

        toggleGroup = new ToggleGroup();
        loginToggle = new ToggleButton("Login");
        registerToggle = new ToggleButton("Register");
        forgotToggle = new ToggleButton("Forgot");

        loginToggle.setToggleGroup(toggleGroup);
        registerToggle.setToggleGroup(toggleGroup);
        forgotToggle.setToggleGroup(toggleGroup);
        loginToggle.setSelected(true);


        // top bordderPane
        hBox.setPadding(new Insets(1,20,0,20));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(360);
        hBox.setHgrow(forgotToggle, Priority.ALWAYS);
        hBox.setHgrow(loginToggle, Priority.ALWAYS);
        hBox.setHgrow(registerToggle, Priority.ALWAYS);

        loginToggle.setPrefWidth(hBox.getPrefWidth()/5 );
        registerToggle.setPrefWidth(hBox.getPrefWidth()/5 );
        forgotToggle.setPrefWidth(hBox.getPrefWidth()/5 );

        hBox.getChildren().addAll(loginToggle, registerToggle, forgotToggle);
        root.setTop(hBox);


        // nodes for Login
        userText = new TextField();
        userPass = new PasswordField();
        loginButton = new Button("Log In");
        cancelButton = new Button("Cancel");
        userText.setPromptText("Username");
        userPass.setPromptText("Password");
        userText.setAlignment(Pos.CENTER);
        userPass.setAlignment(Pos.CENTER);


        // for the parent Layout
        setUpGridPane(300,400);
        loginStage = new CustomStage(30,30,gridPane.getPrefWidth(), gridPane.getPrefHeight());
        scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.setX(0);
        loginStage.setY(loginStage.getScreenMaxHeight());
        loginStage.setResizable(false);
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setTitle("Log In");


        loginToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(null);
                root.setCenter(loginSetUp());
                loginStage.setHeight(gridPane.getPrefHeight());
            }
        });
        registerToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(null);
                root.setCenter(setUpRegister());
                loginStage.setHeight(registerVbox.getPrefHeight());


            }
        });


        // set the loginsetup to center if connected to server
        // if not set loginDisconnected to server

        if (isConnected){
            root.setCenter(loginSetUp());
        }else if (!isConnected){
            root.setCenter(setUpDisconnectedLogin());
        }
        loginStage.showWithAnimation();

    }


    private void setUpGridPane(double prefWidth, double prefHeight){


        // connected login GridPane

        gridPane.setPrefWidth(prefWidth);
        gridPane.setPrefHeight(prefHeight);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(8);
        gridPane.setPadding(new Insets(15,0,30,0));

        gridPane.add(userText,0,0);
        gridPane.add(userPass,0,1);
        gridPane.setHalignment(loginButton, HPos.RIGHT);
        gridPane.setHalignment(cancelButton, HPos.CENTER);
        gridPane.add(loginButton,0,2);
        gridPane.add(cancelButton,0,2);


        // Disconnected login gridPane;

        discGridPane.setPrefWidth(prefWidth);
        discGridPane.setPrefHeight(prefHeight);

        Image img = new Image(getClass().getResourceAsStream("/images/30.gif"));

        Label label = new Label();
        label.setGraphic(new ImageView(img));

        discGridPane.setAlignment(Pos.CENTER);
        discGridPane.setVgap(8);
        discGridPane.setPadding(new Insets(15,0,30,0));

        discGridPane.add(label,0,0);
        discGridPane.add(userText,0,1);
        discGridPane.add(userPass,0,2);
        discGridPane.setHalignment(loginButton, HPos.RIGHT);
        discGridPane.setHalignment(cancelButton, HPos.CENTER);
        discGridPane.add(loginButton,0,3);
        discGridPane.add(cancelButton,0,3);

    }



    private GridPane setUpDisconnectedLogin(){
        root.setCenter(null);

        loginToggle.setDisable(true);
        registerToggle.setDisable(true);
        forgotToggle.setDisable(true);

        userPass.setDisable(true);
        userText.setDisable(true);
        loginButton.setDisable(true);
        cancelButton.setDisable(true);

        return discGridPane;
    }
    private GridPane loginSetUp(){
        gridPane = new GridPane();
        root.setCenter(null);

        loginToggle.setDisable(false);
        registerToggle.setDisable(false);
        forgotToggle.setDisable(false);

        userPass.setDisable(false);
        userText.setDisable(false);
        loginButton.setDisable(false);
        cancelButton.setDisable(false);

        // Handle Login Button
        loginButton.setOnAction(e -> {
            System.out.println("login button was clicke");
            // if true this stage must close
        });

        // Handle Cancel Button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Utility.confirmExit()){
                    loginStage.clseWithAnimation();
                }
            }
        });

        return gridPane;
    }


    private VBox setUpRegister(){
        HBox hBox = new HBox();
        ToggleGroup tg = new ToggleGroup();

        Button save = new Button("Save");
        save.setPrefWidth(90);
        statusLabel = new Label();
        TextField nameField = new TextField();
        TextField contactField = new TextField();
        TextField homeAdress = new TextField();
        TextField userName = new TextField();
        PasswordField password = new PasswordField();
        PasswordField confirmPass = new PasswordField();
        PasswordField adminPass = new PasswordField();
        RadioButton tbMale = new RadioButton("Male");
        RadioButton tbFemale = new RadioButton("FeMale");

        tbMale.setToggleGroup(tg);
        tbFemale.setToggleGroup(tg);

        // Sizes
        nameField.setPrefWidth(190);
        userName.setPrefWidth(190);
        contactField.setPrefWidth(190);
        homeAdress.setPrefWidth(190);
        password.setPrefWidth(190);
        confirmPass.setPrefWidth(190);
        adminPass.setPrefWidth(190);
        statusLabel.setPrefWidth(190);


        // Text Orientation
        statusLabel.setAlignment(Pos.CENTER);
        nameField.setAlignment(Pos.CENTER);
        userName.setAlignment(Pos.CENTER);
        contactField.setAlignment(Pos.CENTER);
        homeAdress.setAlignment(Pos.CENTER);
        password.setAlignment(Pos.CENTER);
        confirmPass.setAlignment(Pos.CENTER);
        adminPass.setAlignment(Pos.CENTER);


        // set prompt
        nameField.setPromptText("Enter Full Name");
        userName.setPromptText("Username for Log In");
        contactField.setPromptText("Mobile Number");
        homeAdress.setPromptText("Current Home Address");
        password.setPromptText("Password");
        confirmPass.setPromptText("Confirm Password");
        adminPass.setPromptText("Admin Key Code");

        // set The radioButton
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(2));
        hBox.getChildren().addAll(tbMale,tbFemale);

        registerVbox.setPadding(new Insets(5, 5, 0, 5));
        registerVbox.getChildren().addAll(nameField,userName, contactField, homeAdress,
               password,confirmPass,adminPass,hBox, save);
        registerVbox.setPrefHeight(300);



        // handle Save Button
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameField.getText();
                String username = userName.getText();
                String contact = contactField.getText();
                String address = homeAdress.getText();
                String pass  = password.getText();
                String cpass = confirmPass.getText();
                String gender = null;

                        if (tg.getSelectedToggle() == tbFemale){
                            gender = "Female";
                        }else {
                            gender = "Male";
                        }

                StaffRegister  staffRegister= new StaffRegister(name,username,contact,address,gender,pass,cpass);

                if (!validate(staffRegister)){
                    // let the validate method to handle this
                }else{
                    // save to database
                }
            }
        });

        return  registerVbox;
    }


    public  boolean validate(StaffRegister staffReg){
         messageBox = new MessageWindow();

        boolean isValidated = false;
        double width = loginStage.getWidth();
        double x = (loginStage.getX() + loginStage.getWidth()/2);
        double y = (loginStage.getY() + loginStage.getHeight()/2 ) - 60;

        String invalidName = "Invalid Name";
        String invalidUsername = "invalid username";
        String invalidContact = "invalid contact";
        String invalidAddress = "invalid address";
        String invalidPassword = "Invalid password";

                if (staffReg.getName().equals(null) || staffReg.getName().equals("")){
                         messageBox.showValidationInfo("Please enter your name",x,y,width - 20);
                         isValidated = false;

                } else if (!Pattern.matches("[a-zA-Z ]{3,25}", staffReg.getName())){
                         messageBox.showValidationInfo(invalidName,x,y, width - 20);
                         isValidated = false;
                }
                else if (!Pattern.matches("[a-zA-Z]{5,12}", staffReg.getUsername())){
                    messageBox.showValidationInfo(invalidUsername,x,y, width - 20);
                    isValidated = false;

                }else if (!Pattern.matches("[0-9]{11}", staffReg.getContact())){
                    messageBox.showValidationInfo(invalidContact,x,y, width - 20);
                    isValidated = false;
                }else if (!Pattern.matches("[a-zA-z0-9 ]",staffReg.getAddress())){
                    messageBox.showValidationInfo(invalidAddress,x,y, width- 20);
                }else if (!staffReg.getPassword().equals(staffReg.getComfirmpass())){
                    messageBox.showValidationInfo(invalidPassword,x,y, width- 20);
                }


        return isValidated;
    }



    public  void close(){
        loginStage.clseWithAnimation();
    }


    public static  LoginWindow getInstantance (){
       return instance;
    }

    public void showLoginWindow(boolean isConnected){
        this.isConnected = isConnected;
    }

}
