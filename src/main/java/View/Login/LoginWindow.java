package View.Login;

import Controller.Controller;
import View.AdminGUI.AdminWindow;
import View.ClientWindow.ClientWindow;
import View.Login.ForgotPassword.ForgotPasswordPane;
import View.Login.Listeners.LoginPaneListener;
import View.Login.Listeners.RegisterPaneListeners;
import View.Login.Listeners.TopPaneListener;
import View.Login.Menu.LoginHeaderMenu;
import View.Login.Register.RegisterPane;
import clientModel.StaffRegister;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import utility.Utility;


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
    private ForgotPasswordPane forgotPasswordPane;

    private ClientWindow clientWindow;
    private AdminWindow adminWindow;

    public   LoginWindow(){
        super(30,30,600,400);

        ctr = Controller.getInstance();
        root = new BorderPane();

        loginPane = new LoginPane();
        topPane = new LoginHeaderMenu();
        registerPane = new RegisterPane();

        root.setTop(topPane);

        AddComponentListeners();
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


        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(1);
            }
        });

        initStyle(StageStyle.DECORATED);

        loginWindowInitialize();

    }

    public void loginWindowInitialize(){
        isConnected = ctr.isServerConnected( );

        if (isConnected){
            System.out.println("setLoginStageConnected");
            setLoginWindoweConnected();
        }else if (!isConnected){
            System.out.println("setLoginStageDisconnected");
            setLoginWindowDisconnected();

        }

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

            @Override
            public void setLoginDisconnected() {
                setLoginWindowDisconnected();
            }

            @Override
            public void setLoginConnected() {
                setLoginWindoweConnected();
            }
        });
        registerPane.addRegisterPaneListener(new RegisterPaneListeners() {
            @Override
            public void setUpLoginWindowDisconnected() {
                setLoginWindowDisconnected();
            }

            @Override
            public boolean Register(StaffRegister staffRegister) {
                return RegisterClient(staffRegister);
            }
        });

        topPane.addTopPaneListener(new TopPaneListener() {
            @Override
            public void setLogin() {
                setCenter(loginPane);
            }

            @Override
            public void setRegister() {
                setCenter(registerPane);
            }

            @Override
            public void setForgot() {
                forgotPasswordPane = new ForgotPasswordPane();
                setCenter(forgotPasswordPane);
            }
        });

    }

    private boolean RegisterClient(StaffRegister staffRegister){
        return  ctr.register(staffRegister);
    }


    public void showLoginWindow(boolean isConnected){
        show();
        }

    // this event occur when client side cant reach the server
    // this is being called from another class example by the time the
    // it process information and suddenly we lose connection to server

    private void setLoginWindowDisconnected(){
        root.setCenter(null);
        loginPane.setUpDisconnectedLogin();
        loginPane.setDisable(true);
        root.setCenter(loginPane);

        topPane.Disable(true);

        loginPane.connectToServer();

        Utility.showMessageBox("We cant connect to server as of the moment", Alert.AlertType.ERROR);

    }
    private void setLoginWindoweConnected(){
        loginPane.setLoginPaneConnected();
        topPane.Disable(false);
        root.setCenter(loginPane);

    }


    public void ShowClientWindow(){
        // show Client Window
        clientWindow = new ClientWindow();
        clientWindow.show();

        // list to ClientWindow when to close
        clientWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                boolean x = Utility.showConfirmationMessage("Are you sure you want to Logout?", Alert.AlertType.CONFIRMATION);
                    if (x){
                        clientWindow.closeClientWindow();
                    }else {
                        event.consume();
                    }
            }
        });


    }

    private void ShowAdminWindow(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                adminWindow = new AdminWindow();
                adminWindow.show();

                adminWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {

                        boolean x = Utility.showConfirmationMessage("Are you sure you want to Logout?", Alert.AlertType.CONFIRMATION);
                        if (x){
                            adminWindow.AdminLogout();
                        }
                        else {
                            event.consume();
                        }

                    }
                });

            }
        });
    }

   private void setCenter(Pane pane){
        root.setCenter(null);
        root.setCenter(pane);
   }

}
