package View.Login;

import Controller.Controller;
import View.AdminGUI.AdminWindow;
import View.ClientWindow.ClientWindow;
import clientModel.SecretQuestion;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import utility.Utility;

import java.util.regex.Pattern;

/**
 * Created by Didoy on 8/24/2015.
 * if the server is down this class will show disconnected login from setUpDisconnectedLogin()
 * and will try to reconnect by connectToServer() method
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

    private double dragX;
    private double dragY;

    private  LoginWindow(){

        ctr = Controller.getInstance();
        isConnected = isServerConnected();

        root = new BorderPane();
        gridPane = new GridPane();
        hBox = new HBox();

        utility = new Utility();

        toggleGroup = new ToggleGroup();
        loginToggle = new ToggleButton("Login");
        registerToggle = new ToggleButton("Register");
        forgotToggle = new         ToggleButton("Forgot");

        loginToggle.setToggleGroup(toggleGroup);
        registerToggle.setToggleGroup(toggleGroup);
        forgotToggle.setToggleGroup(toggleGroup);
        loginToggle.setSelected(true);

        messageBox = MessageWindow.getInstance();


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


        // top borderPane
        hBox.setPadding(new Insets(1,20,0,20));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(360);
        hBox.getStyleClass().add("hboxToggleGroup");
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
        scene.getStylesheets().add("/CSS/Login.css");
        loginStage.setScene(scene);
        loginStage.setX(0);
        loginStage.setY(loginStage.getScreenMaxHeight());
        loginStage.setResizable(false);
        loginStage.setTitle("Log In");

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragX = loginStage.getX() - event.getScreenX() ;
                dragY = loginStage.getY() - event.getScreenY() ;

            }
        });

        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragX = event.getScreenX() + dragX;
                dragY = event.getScreenY() + dragY;

                loginStage.setX(dragX);
                loginStage.setY(dragY);

            }
        });

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
            String username = userText.getText();
            String password = userPass.getText();

            Login(username, password);

        });

        // handle cancel button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (utility.showConfirmationMessage("Are you sure you want to close the application? ", Alert.AlertType.CONFIRMATION)){
                    loginStage.clseWithAnimation();
                }
            }
        });

        return gridPane;
    }

    private void Login(String username, String password ){

        if (isServerConnected()){
            // check whether the account is existing
            if (ctr.Login(username, password,"")){
                userText.setText("");
                userPass.setText("");

                StaffInfo staffInfo = ctr.getStaffInfo();

                // check if the account is already online on other terminal
                if (staffInfo.getStatus().equals("Online")){
                    Utility.showMessageBox("The account is being used in other terminal", Alert.AlertType.ERROR);

                }else {
                    // decide whether admin or client window to be shown here
                    if (staffInfo.getRole().equals("Admin")){
                        showAdminWindow();
                    }else {
                        showClientWindow();
                    }
                    loginStage.close();
                }

                // Invalid account
            }else {
                Utility.showMessageBox("Invalid username or password", Alert.AlertType.ERROR);
            }
        }
        else{
            userText.setText("");
            userPass.setText("");
            setLoginStageToDisconnected();
        }

    }

    private GridPane setUpDisconnectedLogin(){

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

        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(190);

        gridPane.setConstraints(logoLabel, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(connectingL,0,1,2,1,HPos.CENTER,VPos.CENTER);
        gridPane.setConstraints(pb, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER);

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

        gridPane.getChildren().addAll(logoLabel,pb,connectingL,userText,userPass,loginButton,cancelButton);

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
                if (utility.showConfirmationMessage("Are you sure you want to close the application? ", Alert.AlertType.CONFIRMATION)){
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
        secretAns.setAlignment(Pos.CENTER);

        // set prompt
        nameField.setPromptText("Enter Full Name");
        userName.setPromptText("Username for Log In");
        contactField.setPromptText("Mobile Number");
        homeAdress.setPromptText("Current Home Address");
        password.setPromptText("Password");
        confirmPass.setPromptText("Confirm Password");
        secretAns.setPromptText("Your secret answer");

        // set The radioButton
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(2));
        hBox.getChildren().addAll(tbMale,tbFemale);

        registerVbox.setPadding(new Insets(5, 5, 0, 5));
        registerVbox.getChildren().addAll(nameField,userName, contactField, homeAdress,secretQ, secretAns,
                password,confirmPass,hBox, save);

        // setting this height will also set the height of the window
        registerVbox.setPrefHeight(380);



        // handle Save Button
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isServerConnected()){

                    setLoginStageToDisconnected();
                }else{
                    double width = loginStage.getWidth();
                    double x = (loginStage.getX() + loginStage.getWidth()/2);
                    double y = (loginStage.getY() + loginStage.getHeight()/2 ) - 60;


                    String name = nameField.getText().trim();
                    String username = userName.getText().trim();
                    String contact = contactField.getText().trim();
                    String address = homeAdress.getText().trim();
                    String pass  = password.getText().trim();
                    String cpass = confirmPass.getText().trim();
                    String secretAnswer = secretAns.getText().trim();
                    String gender = null;

                    if (tg.getSelectedToggle() == tbFemale){
                        gender = "Female";
                    }else {
                        gender = "Male";
                    }

                    // format contact
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
                        sq = null;
                    }

                    StaffRegister staffRegister = new StaffRegister(name,username,contact,address,secretID,sq,secretAnswer,gender,pass,cpass);

                    if (!validate(staffRegister,x,y,width,messageBox)){
                        // let the validate method to handle this
                    }
                    else{
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
                            secretAns.setText("");

                        }else{

                        }
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

                    while (!isConnected){
                        Thread.sleep(1500);
                        if (isServerConnected()){
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


    // Validate Register
    public  boolean validate(StaffRegister staffReg, double x, double y, double width, MessageWindow messageBox){

        boolean isValidated = false;

        String name = staffReg.getName();
        String username = staffReg.getUsername();
        String contact = staffReg.getContact();
        String address = staffReg.getAddress();
        String pass  = staffReg.getPassword();
        String cpass = staffReg.getComfirmpass();
        SecretQuestion securityQ = staffReg.getSq();
        String secretAnswer = staffReg.getSecretAnswer();

                                         //// NAME VALIDATION
                if (name.equals(null) || name.equals("")) {
                    messageBox.showValidationInfo("The name you entered is empty from login", x, y, width - 20);
                    isValidated = false;
                }else if (name.length()<4 || name.length()>70){
                    messageBox.showValidationInfo("The name must only contain 4 to 30 characters", x, y, width - 20);

                }
                    else if (!Pattern.matches("^[a-zA-Z\\s]+[a-zA-Z\\s]", name)){
                    messageBox.showValidationInfo("The name should not contain \n" +
                            "any digits or Special characters.", x, y, width - 20);
                    isValidated = false;
                    }
                                        // USERNAME VALIDATION
                else if (username.equals("")|| username.equals(null)){
                    messageBox.showValidationInfo("The username you entered is empty", x, y, width - 20);
                    isValidated = false;
                }

                else  if (username.length() <6 || username.length()> 10){
                    messageBox.showValidationInfo("Username must contain of 6 to 10 characters \n", x, y, width - 20);
                    isValidated = false;
                }

                else  if (!Pattern.matches("^[a-zA-Z0-9_-]{6,12}$+",username)){
                    messageBox.showValidationInfo("Username must not contain any special character \n", x, y, width - 20);
                    isValidated = false;
                     }
                else if (ctr.getUsername(username)){
                    messageBox.showValidationInfo("Username is already taken \n", x, y, width - 20);
                    isValidated = false;
                }

                                            // CONTACT VALIDATION
                else if (contact.equals("")|| contact.equals(null)){
                    messageBox.showValidationInfo("The Contact you entered is empty", x, y, width - 20);
                    isValidated = false;
                }
                     else if (!Pattern.matches("^[\\d\\-]{13}$+",contact)) {
                    messageBox.showValidationInfo("Your contact number must be 11 digits", x, y, width - 20);
                    isValidated = false;
                     }

                                            // ADDRESS VALIDATION
                else if (!Pattern.matches("^[a-zA-Z0-9\\.\\-\\s]+", address)) {
                    messageBox.showValidationInfo("Your Address must consist of 5 digits and\n" +
                            "not contain any special character", x, y, width - 20);
                    isValidated = false;

                                            // SECURITY QUESTION VALIDATION
                }else if (securityQ == null){
                    messageBox.showValidationInfo("Please add Secret Question", x, y, width - 20);
                    isValidated = false;
                }
                                        // SECRET ANSWER VALIDATION
                else if (secretAnswer.equals("")){
                    messageBox.showValidationInfo("Please add Secret Answer \n for your secret question", x, y, width - 20);
                    isValidated = false;
                }
                else if (!Pattern.matches("^[a-zA-Z0-9\\s_.-]+", secretAnswer)){
                    messageBox.showValidationInfo("Please remove any special characters \n in your Security Answer", x, y, width - 20);
                    isValidated = false;
                }
                                        // PASSWORD VALIDATION
                else if (pass.equals("") || cpass.equals("")){
                    messageBox.showValidationInfo("Please add your desired password", x, y, width - 20);
                    isValidated = false;
                }

                else if (!pass.equals(cpass)) {
                    messageBox.showValidationInfo("Password don't match", x, y, width - 20);
                    isValidated = false;
                }

                else if (!Pattern.matches("^[a-zA-Z0-9]{6,15}$+", pass)){
                    messageBox.showValidationInfo("The password you entered is Invalid", x, y, width - 20);
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
        loginStage.show();
        }

    // this event occur when client side cant reach the server
    // this is being called from another class example by the time the
    // it process information and suddenly we lose connection to server
    public void setLoginStageToDisconnected(){
        alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setHeaderText(null);
        alertBox.setTitle("Error information");
        alertBox.setContentText("We cant connect to server as of the moment");
        alertBox.show();

        root.setCenter(null);
        root.setCenter(setUpDisconnectedLogin());
        toggleGroup.selectToggle(loginToggle);
        loginStage.setHeight(290);
    }

    public void showClientWindow(){

        // show Client Window
        ClientWindow cw = new ClientWindow();
        cw.show();

        // list to ClientWindow when to close
        cw.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                cw.closeClientWindow();
            }
        });

    }

    private void showAdminWindow(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AdminWindow.getInstance().show();
            }
        });
    }

}
