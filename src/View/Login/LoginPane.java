package View.Login;

import Controller.Controller;
import View.Login.Listeners.LoginPaneListener;
import clientModel.StaffInfo;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import utility.Utility;

/**
 * Created by Didoy on 1/28/2016.
 */
public class LoginPane extends BorderPane {

    private  GridPane gridPane;
    private  TextField userText, contactField;
    private  Label statusLabel;
    private PasswordField userPass;
    private  Button loginButton, cancelButton;


    private LoginPaneListener loginPaneListener;

    private boolean isConnected;

    private  Controller ctr = Controller.getInstance();

    LoginPane(){
        isConnected = isServerConnected();

        contactField = new TextField();
        userText = new TextField();
        userPass = new PasswordField();
        contactField = new TextField();

        userText.setAlignment(Pos.CENTER);
        userPass.setAlignment(Pos.CENTER);

        userText.setPromptText("Username");
        userPass.setPromptText("Username");

        loginButton = new Button("Login");
        cancelButton = new Button("Cancel");

        statusLabel = new Label();


    }

    public void addLoginPaneListener(LoginPaneListener loginPaneListener){
        this.loginPaneListener = loginPaneListener;
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

        userPass.setDisable(true);
        userText.setDisable(true);
        loginButton.setDisable(true);
        cancelButton.setDisable(false);

        // handle cancel button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Utility.showConfirmationMessage("Are you sure you want to close the application? ", Alert.AlertType.CONFIRMATION)){
                    loginPaneListener.exitApplication();

                }
            }
        });

        connectToServer();

        return gridPane;
    }

    public void connectToServer(){
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
                                    setHeight(240);
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

    private void changeLogIncCenter(Pane pane){
        setCenter(null);
        setCenter(pane);
    }

    public GridPane loginSetUp(){
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
                if (Utility.showConfirmationMessage("Are you sure you want to close the application? ", Alert.AlertType.CONFIRMATION)){
                    loginPaneListener.exitApplication();
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
                        loginPaneListener.showAdminWindow();
                    }else {
                        loginPaneListener.showClientWindow();
                    }
                         loginPaneListener.closeLoginStage();
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

    public void setLoginStageToDisconnected(){

        Utility.showMessageBox("We cant connect to server as of the moment", Alert.AlertType.ERROR);

        setCenter(null);
        setCenter(setUpDisconnectedLogin());
        setHeight(290);
    }

    private  boolean isServerConnected(){
        return ctr.isServerConnected();
    }
}
