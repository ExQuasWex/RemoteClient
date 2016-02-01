package View.Login;

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

    private Preferences pref;
    private      String ip;
    private final String KEY = "serverIP";

    private TextInputDialog dialog;

    public IpConfiguration() {

         pref =  Preferences.userRoot().node(this.getClass().getName());

         ip = pref.get(KEY, ip);

        dialog = new TextInputDialog(ip);

        dialog.setHeaderText(null);
        dialog.setTitle("Server Ip addres Configuration");
        dialog.setContentText("Please add Server Ip address: ");

    }

    public void showWindow(){
        Optional<String> result =  dialog.showAndWait();

        if (result.isPresent()) {
            ip = result.get();
            pref.put(KEY, ip);
        }else {
                if (ip.equals("") || ip.equals(null) ){
                       showWindow();
                }
        }
    }


}
