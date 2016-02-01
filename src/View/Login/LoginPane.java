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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
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
    private    Label connectingL;
    private  ProgressBar pb;


    private LoginPaneListener loginPaneListener;

    private boolean isConnected;

    private  Controller ctr = Controller.getInstance();

    LoginPane(){

        gridPane = new GridPane();

        isConnected = isServerConnected();

        contactField = new TextField();
        userText = new TextField();
        userPass = new PasswordField();
        contactField = new TextField();

        userText.setAlignment(Pos.CENTER);
        userPass.setAlignment(Pos.CENTER);

        userText.setPromptText("Username");
        userPass.setPromptText("Password");

        loginButton = new Button("Login");
        cancelButton = new Button("Cancel");

        statusLabel = new Label();

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


        loginSetUp();

        setCenter(gridPane);

    }

    public void addLoginPaneListener(LoginPaneListener loginPaneListener){
        this.loginPaneListener = loginPaneListener;
    }

    public void setUpDisconnectedLogin(){

         connectingL = new Label("Connecting to Server. . .");

        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        t.setAutoReverse(true);
        KeyValue kv = new KeyValue(connectingL.opacityProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);

        KeyValue kv2 = new KeyValue(connectingL.opacityProperty(), 1.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(2000), kv2);
        t.getKeyFrames().addAll(kf, kf2);
        t.play();

        pb = new ProgressBar();
        pb.setPrefWidth(190);


        gridPane.setConstraints(connectingL,0,4,2,1,HPos.CENTER,VPos.CENTER);
        gridPane.setConstraints(pb, 0, 5, 2, 1, HPos.CENTER, VPos.CENTER);

        // critical here
        gridPane.getChildren().addAll(pb, connectingL);

        userPass.setDisable(true);
        userText.setDisable(true);
        loginButton.setDisable(true);
        cancelButton.setDisable(false);

    }


    public void setLoginPaneConnected(){
        setDisable(false);
        userPass.setDisable(false);
        userText.setDisable(false);
        loginButton.setDisable(false);
        cancelButton.setDisable(false);

        gridPane.getChildren().remove(pb);
        gridPane.getChildren().remove(connectingL);


    }
    public void loginSetUp(){

        HBox hBox = new HBox();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(8);

        Label logoLabel = new Label();
        Image imgLogo = new Image(getClass().getResourceAsStream("/images/mblctLogo.png"),100,100,false,true);
        logoLabel.setGraphic(new ImageView(imgLogo));

        Text Title = new Text("Mabalacat Urban Poor Decision Support System");
        Title.setFontSmoothingType(FontSmoothingType.LCD);
        Title.setFont(Font.font(20));

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(logoLabel, Title);

        gridPane.setConstraints(hBox, 0, 0, 2, 1, HPos.LEFT, VPos.TOP);


        gridPane.setMargin(userText, new Insets(0,80,0,80));
        gridPane.setMargin(userPass, new Insets(0, 80, 0, 80));

        loginButton.setPrefWidth(60);
        cancelButton.setPrefWidth(60);
        gridPane.setMargin(hBox, new Insets(0, 0, 40, 0) );

        gridPane.setMargin(loginButton, new Insets(0, 0, 30, 140));
        gridPane.setMargin(cancelButton, new Insets(0,90,30,0));
        gridPane.setConstraints(userText,     0, 1, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(userPass,     0, 2, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(loginButton,  0, 3, 2, 1, HPos.CENTER, VPos.CENTER);
        gridPane.setConstraints(cancelButton, 0, 3, 2, 1, HPos.CENTER, VPos.CENTER);

        gridPane.getChildren().addAll(hBox, userText, userPass, loginButton, cancelButton);

        userPass.setDisable(false);
        userText.setDisable(false);
        loginButton.setDisable(false);
        cancelButton.setDisable(false);

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
            loginPaneListener.setLoginDisconnected();
        }

    }


    private  boolean isServerConnected(){
        return ctr.isServerConnected();
    }

    /*
        call this method whenver serve is not coonnected wiithin Login Window
     */
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
                                    System.out.println("we are now connected to the server");
                                    loginPaneListener.setLoginConnected();
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

}
