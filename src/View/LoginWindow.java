package View;

import clientModel.StaffRegister;
import Controller.Controller;
import clientModel.SecretQuestion;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utility.Utility;

import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

/**
 * Created by Didoy on 8/24/2015.
 * if the server is down the this class will show disconnected login from setUpDisconnectedLogin()
 * and will try to reconnect by connectToServer()
 * if not it will show login window
 *
 * Height = 240 connected
 * Height = 290 disconnected;
 */
public class LoginWindow  {

    private static CustomStage loginStage;
    private static  LoginWindow  instance = new LoginWindow();

    private  GridPane gridPane;
    private  TextField userText, contactField;
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
    private Utility utility;

    private boolean isConnected = true;
    private  Controller ctr;

    private Alert alertBox;

    private  LoginWindow(){

        ctr = new Controller();
        isConnected = ctr.isServerConnected();

        root = new BorderPane();
        gridPane = new GridPane();
        hBox = new HBox();

        utility = new Utility();

        toggleGroup = new ToggleGroup();
        loginToggle = new ToggleButton("Login");
        registerToggle = new ToggleButton("Register");
        forgotToggle = new ToggleButton("Forgot");

        loginToggle.setToggleGroup(toggleGroup);
        registerToggle.setToggleGroup(toggleGroup);
        forgotToggle.setToggleGroup(toggleGroup);
        loginToggle.setSelected(true);


        // nodes for Login
        userText = new TextField();
        userPass = new PasswordField();
        userText.setMaxWidth(200);
        userPass.setMaxWidth(200);
        loginButton = new Button("Log In");
        cancelButton = new Button("Cancel");
        userText.setPromptText("Username");
        userPass.setPromptText("Password");
        userText.setAlignment(Pos.CENTER);
        userPass.setAlignment(Pos.CENTER);

        // nodes that need to declared final and global from registerSetup()
        contactField = new TextField();


        // top bordderPane
        hBox.setPadding(new Insets(1,20,0,20));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(360);
        hBox.setStyle("-fx-background-color: #6495ED  ");
        hBox.setHgrow(forgotToggle, Priority.ALWAYS);
        hBox.setHgrow(loginToggle, Priority.ALWAYS);
        hBox.setHgrow(registerToggle, Priority.ALWAYS);

        loginToggle.setPrefWidth(hBox.getPrefWidth()/5 );
        registerToggle.setPrefWidth(hBox.getPrefWidth()/5 );
        forgotToggle.setPrefWidth(hBox.getPrefWidth()/5 );

        hBox.getChildren().addAll(loginToggle, registerToggle, forgotToggle);
        root.setTop(hBox);

        loginToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(null);
                loginStage.setHeight(loginSetUp().getPrefHeight());
                root.setCenter(loginSetUp());
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
            System.out.println("center loginsetup");
            root.setCenter(loginSetUp());

            loginStage =  new CustomStage(30,30,350,245);
        }else if (!isConnected){
            root.setCenter(setUpDisconnectedLogin());
            loginStage =  new CustomStage(30,30,350,290);
        }

        scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.setX(0);
        loginStage.setY(loginStage.getScreenMaxHeight());
        loginStage.setResizable(false);
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setTitle("Log In");

        loginStage.showWithAnimation();
    }

    private GridPane loginSetUp(){
        gridPane = new GridPane();

        gridPane.setPrefHeight(240);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(8);
        gridPane.setPadding(new Insets(5,0,20,0));

        Label logoLabel = new Label();
        Image imgLogo = new Image(getClass().getResourceAsStream("/images/mblctLogo.png"),100,100,false,true);
        logoLabel.setGraphic(new ImageView(imgLogo));
        gridPane.setConstraints(logoLabel, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER);

        userText.setPrefColumnCount(15);
        userPass.setPrefColumnCount(15);
        loginButton.setPrefWidth(60);
        cancelButton.setPrefWidth(60);
        gridPane.setMargin(loginButton, new Insets(0, 0, 0, 80));
        gridPane.setMargin(cancelButton, new Insets(0,55,0,0));
        gridPane.setConstraints(userText, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(userPass, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(loginButton, 0, 3, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(cancelButton, 0, 3, 2, 1, HPos.CENTER, VPos.CENTER);

        gridPane.getChildren().addAll(logoLabel, userText, userPass, loginButton, cancelButton);

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


            System.out.println("isConnected is: "+  isConnected);
            if (!isServerConnected()){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        changeLogIncCenter(setUpDisconnectedLogin());
                    }
                });
            }else if (isServerConnected()){
                String username = userText.getText();
                String pass = userPass.getText();
                try {
                    ctr.Login(username, pass);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                System.out.println("coonected");
            }

        });

        // Handle Cancel Button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (utility.confirmExit()){
                    loginStage.clseWithAnimation();
                }
            }
        });

        return gridPane;
    }

    private GridPane setUpDisconnectedLogin(){

        Image img = new Image(getClass().getResourceAsStream("/images/30.gif"));
        Image imgLogo = new Image(getClass().getResourceAsStream("/images/mblctLogo.png"),100,100,false,true);

        Label connectingL = new Label("Connecting to Server. . .");

        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        t.setAutoReverse(true);
        KeyValue kv = new KeyValue(connectingL.opacityProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);

        KeyValue kv2 = new KeyValue(connectingL.opacityProperty(), 1.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(2000), kv2);
        t.getKeyFrames().addAll(kf, kf2);
        t.play();

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(5, 0, 10, 0));

        Label logoLabel = new Label();
        gridPane.setHalignment(logoLabel, HPos.CENTER);
        logoLabel.setGraphic(new ImageView(imgLogo));

        Label label = new Label();
        label.setGraphic(new ImageView(img));

        gridPane.setConstraints(logoLabel, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(label, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(connectingL,0,2,2,1,HPos.CENTER,VPos.CENTER);

        userText.setPrefColumnCount(15);
        userPass.setPrefColumnCount(15);
        loginButton.setPrefWidth(60);
        cancelButton.setPrefWidth(60);
        gridPane.setMargin(loginButton, new Insets(0,0,0,80));
        gridPane.setMargin(cancelButton, new Insets(0,55,0,0));
        gridPane.setConstraints(userText, 0, 3, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(userPass, 0, 4, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(loginButton, 0, 5, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(cancelButton, 0, 5, 2, 1, HPos.CENTER, VPos.CENTER);

        gridPane.getChildren().addAll(logoLabel,label,connectingL,userText,userPass,loginButton,cancelButton);

        loginToggle.setDisable(true);
        registerToggle.setDisable(true);
        forgotToggle.setDisable(true);

        userPass.setDisable(true);
        userText.setDisable(true);
        loginButton.setDisable(true);
        cancelButton.setDisable(false);

        // handle cancel button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (utility.confirmExit()){
                    loginStage.clseWithAnimation();
                }
            }
        });

      connectToServer();

        return gridPane;
    }

    private VBox setUpRegister(){

        // this is for register layout
        registerVbox = new VBox(5);
        registerVbox.setAlignment(Pos.CENTER);
        registerVbox.setFillWidth(false);

        HBox hBox = new HBox();
        ToggleGroup tg = new ToggleGroup();



        statusLabel = new Label();
        TextField nameField = new TextField();
        contactField = new TextField();
        TextField homeAdress = new TextField();
        TextField userName = new TextField();
        PasswordField password = new PasswordField();
        PasswordField confirmPass = new PasswordField();
        PasswordField adminPass = new PasswordField();
        RadioButton tbMale = new RadioButton("Male");
        RadioButton tbFemale = new RadioButton("FeMale");
        Button save = new Button("Save");
        ComboBox secretQ = new ComboBox();
        secretQ.setPromptText("Secret Question");
        TextField secretAns = new TextField();


        tbMale.setToggleGroup(tg);
        tbFemale.setToggleGroup(tg);
        tg.selectToggle(tbMale);

        // Sizes
        save.setPrefWidth(90);
        nameField.setPrefWidth(240);
        userName.setPrefWidth(240);
        contactField.setPrefWidth(240);
        homeAdress.setPrefWidth(240);
        password.setPrefWidth(240);
        confirmPass.setPrefWidth(240);
        adminPass.setPrefWidth(240);
        statusLabel.setPrefWidth(240);
        secretQ.setPrefWidth(240);
        secretAns.setPrefWidth(240);

        // adding elements to combo Box
        secretQ.getItems().setAll(SecretQuestion.values());

        // Text Orientation
        statusLabel.setAlignment(Pos.CENTER);
        nameField.setAlignment(Pos.CENTER);
        userName.setAlignment(Pos.CENTER);
        contactField.setAlignment(Pos.CENTER);
        homeAdress.setAlignment(Pos.CENTER);
        password.setAlignment(Pos.CENTER);
        confirmPass.setAlignment(Pos.CENTER);
        adminPass.setAlignment(Pos.CENTER);
        secretAns.setAlignment(Pos.CENTER);

        // set prompt
        nameField.setPromptText("Enter Full Name");
        userName.setPromptText("Username for Log In");
        contactField.setPromptText("Mobile Number");
        homeAdress.setPromptText("Current Home Address");
        password.setPromptText("Password");
        confirmPass.setPromptText("Confirm Password");
        adminPass.setPromptText("Admin Key Code");
        secretAns.setPromptText("Your secret answer");

        // set The radioButton
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(2));
        hBox.getChildren().addAll(tbMale,tbFemale);

        registerVbox.setPadding(new Insets(5, 5, 0, 5));
        registerVbox.getChildren().addAll(nameField,userName, contactField, homeAdress,secretQ, secretAns,
                password,confirmPass,adminPass,hBox, save);

        // setting this height will also set the height of the window
        registerVbox.setPrefHeight(380);



        // handle Save Button
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double width = loginStage.getWidth();
                double x = (loginStage.getX() + loginStage.getWidth()/2);
                double y = (loginStage.getY() + loginStage.getHeight()/2 ) - 60;

                messageBox = new MessageWindow();

                String name = nameField.getText().trim();
                String username = userName.getText().trim();
                String contact = contactField.getText().trim();
                String address = homeAdress.getText().trim();
                String pass  = password.getText().trim();
                String cpass = confirmPass.getText().trim();
                String secretAnswer = secretAns.getText().trim();
                String keyCode = adminPass.getText().trim();
                String gender = null;

                if (tg.getSelectedToggle() == tbFemale){
                    gender = "Female";
                }else {
                    gender = "Male";
                }

                // format contact string
                contact = contact.replaceFirst("(\\d{4})(\\d{3})(\\d{4})","$1-$2-$3");
                contactField.setText(contact);

                int secretID = 0;
                SecretQuestion sq = null;
                if (SecretQuestion.PET == secretQ.getSelectionModel().getSelectedItem()){
                    secretID = 1;
                    sq = SecretQuestion.PET;
                }else if (SecretQuestion.TVSHOW == secretQ.getSelectionModel().getSelectedItem()){
                    secretID = 2;
                    sq = SecretQuestion.TVSHOW;
                }else if (SecretQuestion.BOOK == secretQ.getSelectionModel().getSelectedItem()){
                    secretID = 3;
                    sq = SecretQuestion.BOOK;
                }else {
                    messageBox.showValidationInfo("Please add secret Question",x,y,width - 20);
                }

                StaffRegister  staffRegister = new StaffRegister(name,username,contact,address,secretID,sq,secretAnswer,gender,pass,cpass);

                if (!validate(staffRegister,x,y,width,messageBox,keyCode)){
                    // let the validate method to handle this
                }else{
                    boolean isRegistered;
                    System.out.println("all registration is now validated");
                    isRegistered =  ctr.register(staffRegister);

                    if (isRegistered){
                        alertBox = new Alert(Alert.AlertType.INFORMATION);
                        alertBox.setHeaderText(null);
                        alertBox.setTitle("Register");
                        alertBox.setContentText("Your account is succesfully Created");
                        alertBox.show();

                        nameField.setText("");
                        userName.setText("");
                        homeAdress.setText("");
                        password.setText("");
                        contactField.setText("");
                        confirmPass.setText("");
                        homeAdress.setText("");
                        adminPass.setText("");
                        secretAns.setText("");

                    }else{

                    }
                }
            }
        });

        return  registerVbox;
    }


    private void connectToServer(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1300);
                    isConnected = isServerConnected();
                    while (!isConnected){
                        isConnected = isServerConnected();
                        System.out.println("connecting to server");
                        if (isConnected){
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                System.out.println("we are now connected to server");
                                                changeLogIncCenter(loginSetUp());
                                                loginStage.setHeight(240);
                                            }
                                        });
                                        break;
                                    }
                            }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
    private  boolean isServerConnected(){
        return ctr.isServerConnected();
    }

    private void changeLogIncCenter(Pane pane){
                root.setCenter(null);
                root.setCenter(pane);
    }

    public  boolean validate(StaffRegister staffReg, double x, double y, double width,MessageWindow messageBox, String keyCode){

        boolean isValidated = false;

                if (staffReg.getName().equals(null) || staffReg.getName().equals("")) {
                    messageBox.showValidationInfo("The name you entered is empty", x, y, width - 20);
                    isValidated = false;
                }
                    else if (!Pattern.matches("^\\W?[a-zA-Z\\s]{4,30}$+", staffReg.getName())){
                    messageBox.showValidationInfo("     The name should not contain \n" +
                            "any digits or Symbol,or must not exceed to 30 characters", x, y, width - 20);
                    isValidated = false;
                    }

                else if (staffReg.getUsername().equals("")|| staffReg.getUsername().equals(null)){
                    messageBox.showValidationInfo("The username you entered is empty", x, y, width - 20);
                    isValidated = false;
                }   else  if (!Pattern.matches("^[a-zA-Z0-9_-]{6,10}$+",staffReg.getUsername())){
                    messageBox.showValidationInfo("Username must not contain any special character \n" +
                            "rather than - or _ , Character size: 6-10", x, y, width - 20);
                    isValidated = false;
                     }

                else if (staffReg.getContact().equals("")|| staffReg.getContact().equals(null)){
                    messageBox.showValidationInfo("The Contact you entered is empty", x, y, width - 20);
                    isValidated = false;
                }
                     else if (!Pattern.matches("^[\\d\\-]{13}$+",staffReg.getContact())) {
                    messageBox.showValidationInfo("Your contact number must be 11 digits and have no \n" +
                            "any special character", x, y, width - 20);
                    isValidated = false;
                     }

                else if (!Pattern.matches("^[\\d]+\\s?[a-zA-Z\\.\\-\\s]+",staffReg.getAddress())){
                    messageBox.showValidationInfo("Your Address must consist of 5 digits\n" +
                            "any special character", x, y, width - 20);
                    isValidated = false;
                }else if (staffReg.getSq() == null){
                    messageBox.showValidationInfo("Please add Secret Question", x, y, width - 20);
                    isValidated = false;
                }

                else if (staffReg.getSecretAnswer().equals("")){
                    messageBox.showValidationInfo("Please add Secret Answer \n for your secret question", x, y, width - 20);
                    isValidated = false;
                }
                else if (!Pattern.matches("^[a-zA-Z0-9\\s_.-]+",staffReg.getSecretAnswer())){
                    messageBox.showValidationInfo("please avoid using uneccessary symbols", x, y, width - 20);
                    isValidated = false;
                }
                else if (staffReg.getPassword().equals("")|| staffReg.getComfirmpass().equals("")){
                    messageBox.showValidationInfo("Please add your desired password", x, y, width - 20);
                    isValidated = false;
                }

                else if (!staffReg.getPassword().equals(staffReg.getComfirmpass())){
                    messageBox.showValidationInfo("Password don't match", x, y, width - 20);
                    isValidated = false;
                }

                else if (!Pattern.matches("^[a-zA-Z0-9]{6,15}$+",staffReg.getPassword())){
                    messageBox.showValidationInfo("Please change your password \n and avoid using special characters", x, y, width - 20);
                    isValidated = false;
                }else if (staffReg.getGender().equals("")){
                    messageBox.showValidationInfo("Please select your biological gender", x, y, width - 20);
                    isValidated = false;
                }
                // match the inut keycode from database admin keycode
                else if (keyCode.equals("")){
                    messageBox.showValidationInfo("The keyCode you entered is Empty", x, y, width - 20);
                    isValidated = false;
                }
                else if (!ctr.getAdminKeyCode(keyCode)){
                    messageBox.showValidationInfo("The keyCode you entered is invalid", x, y, width - 20);
                    isValidated = false;
                }
                else {
                    isValidated = true;
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

    // this event occur when client side cant reach the server
    // this is being called from another class example by the time the
    // it process information and suddenly we lose connection to server
    public void setLoginStageToDisconnected(){
        root.setCenter(null);
        root.setCenter(setUpDisconnectedLogin());
        toggleGroup.selectToggle(loginToggle);
        loginStage.setHeight(290);
    }

}
