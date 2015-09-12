package utility;

import View.LoginWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by Didoy on 9/1/2015.
 */
public class Utility {

    public static boolean confirmExit(){
        boolean bol = false;

        Alert alertBox = new Alert(Alert.AlertType.CONFIRMATION);
        alertBox.setTitle("Confirmation Window");
        alertBox.setHeaderText("Are you sure you would like to exit?");
        alertBox.setContentText(null);

        Optional<ButtonType> result = alertBox.showAndWait();

        if (result.get() == ButtonType.OK){
            bol = true;
        }else{
            bol = false;
            alertBox.close();
        }
        return bol;
    }

    public boolean validateName (String Name){
        boolean boo;
        if (Pattern.matches("[a-zA-z]{3,25}",Name)){
            boo = true;
        }else {
            boo = false;
        }
        return boo;
    }

}
