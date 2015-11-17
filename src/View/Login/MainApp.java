package View.Login;

import View.Login.LoginWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.rmi.RemoteException;

/**
 * Created by Didoy on 8/24/2015.
 */


public class MainApp extends Application {

    public static void main(String[] arg) throws RemoteException {

     Thread.setDefaultUncaughtExceptionHandler(ueh);

        Application.launch(arg);


    }

    // handler listener
    private static Thread.UncaughtExceptionHandler ueh = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            System.out.println("handlesdddssdasdasd");
        }
    };


    @Override
    public void start(Stage primaryStage) throws Exception {
                    startLogwinWindow();
    }

    public void startLogwinWindow(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                   LoginWindow login = LoginWindow.getInstantance();
            }
        });
    }




}
