package View.Login.ForgotPassword;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import utility.Utility;

import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by Didoy on 1/31/2016.
 */
public class IpConfiguration  {

    private      String ip;
    private TextInputDialog dialog;

    public IpConfiguration() {

        createNewInstanceWithIP();

    }

    public void showWindow(){

        createNewInstanceWithIP();

        Optional<String> result =  dialog.showAndWait();

        if (result.isPresent()) {
            ip = result.get();
             Utility.SavePreference(ip);
        }else {
                if (ip.equals("") || ip.equals(null) ){
                       showWindow();
                }
        }
    }

    public void createNewInstanceWithIP(){
        ip = Utility.getPreference();

        dialog = new TextInputDialog(ip);
        dialog.setHeaderText(null);
        dialog.setTitle("Server Ip addres Configuration");
        dialog.setContentText("Please add Server Ip address: ");


    }


}
