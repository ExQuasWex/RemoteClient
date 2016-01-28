package View.Login;

import Controller.Controller;
import View.AdminGUI.AdminWindow;
import View.ClientWindow.ClientWindow;
import View.Login.Listeners.LoginPaneListener;
import View.Login.Listeners.RegisterPaneListeners;
import View.Login.Listeners.TopPaneListener;
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
public class LoginWindow extends CustomStage {


    private  BorderPane root;
    private  Scene scene;

    private boolean isConnected = true;
    private  Controller ctr;

    private double dragX;
    private double dragY;

    private LoginPane loginPane;
    private LoginHeaderMenu topPane;
    private RegisterPane registerPane;

    public   LoginWindow(){
        super(30,30,350,280);

        ctr = Controller.getInstance();
        isConnected = ctr.isServerConnected();

        root = new BorderPane();

        loginPane = new LoginPane();
        topPane = new LoginHeaderMenu();
        registerPane = new RegisterPane();

        root.setTop(topPane);


        AddComponentListeners();

        if (isConnected){
            System.out.println("setLoginStageConnected");
                setLoginStageConnected();
        }else if (!isConnected){
                  System.out.println("setLoginStageDisconnected");
                setLoginStageDisconnected();
        }

        scene = new Scene(root);
        scene.getStylesheets().add("/CSS/Login.css");
        setScene(scene);
        setX(0);
        setY(getScreenMaxHeight());
        setResizable(false);
        setTitle("Log In");

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragX = getX() - event.getScreenX() ;
                dragY = getY() - event.getScreenY() ;

            }
        });

        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragX = event.getScreenX() + dragX;
                dragY = event.getScreenY() + dragY;

              setX(dragX);
              setY(dragY);

            }
        });

        showWithAnimation();

    }

    private void AddComponentListeners(){
        loginPane.addLoginPaneListener(new LoginPaneListener() {
            @Override
            public void showAdminWindow() {
                ShowAdminWindow();
            }

            @Override
            public void showClientWindow() {
                ShowClientWindow();
            }

            @Override
            public void closeLoginStage() {
                   close();
            }

            @Override
            public void exitApplication() {
                System.exit(1);
            }
        });
        registerPane.addRegisterPaneListener(new RegisterPaneListeners() {
            @Override
            public void setUpLoginWindowDisconnected() {
                setLoginStageConnected();
            }

            @Override
            public boolean Register(StaffRegister staffRegister) {
                return RegisterClient(staffRegister);
            }
        });

        topPane.addTopPaneListener(new TopPaneListener() {
            @Override
            public void setLogin() {
                setCenter(loginPane.loginSetUp());
            }

            @Override
            public void setRegister() {
                setCenter(registerPane.setUpRegister());
            }

            @Override
            public void setForgot() {

            }
        });

    }

    private boolean RegisterClient(StaffRegister staffRegister){
       boolean isRegistered =  ctr.register(staffRegister);
        return isRegistered;
    }


    public void showLoginWindow(boolean isConnected){
        show();
        }

    // this event occur when client side cant reach the server
    // this is being called from another class example by the time the
    // it process information and suddenly we lose connection to server

    private void setLoginStageDisconnected(){
        root.setCenter(null);
        loginPane.setLoginStageToDisconnected();
        root.setCenter(loginPane);

        loginPane.connectToServer();
    }
    private void setLoginStageConnected(){
        root.setCenter(null);
        root.setCenter(loginPane.loginSetUp());

    }

    public void ShowClientWindow(){
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

    private void ShowAdminWindow(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AdminWindow.getInstance().show();
            }
        });
    }

   private void setCenter(Pane pane){
        root.setCenter(null);
        root.setCenter(pane);
   }

}
