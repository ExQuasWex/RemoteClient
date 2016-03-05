package View.ToolKit;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import utility.Utility;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Created by reiner on 3/3/2016.
 */
public class MessageBox {

private static  boolean isConfirmed = false;
private static String option = "";
    public static boolean  confirmMessageWithPassword(String text, String title, String officialPassword){

        DialogPane dialogPane = new DialogPane();
        Dialog dialog = new Dialog();

        Text textHeader = new Text(text);
        PasswordField passwordField  = new PasswordField();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(textHeader, passwordField);
        vBox.setSpacing(10);

        dialogPane.setContent(vBox);

        dialog.setDialogPane(dialogPane);
        dialog.setTitle(title);
        dialog.setContentText(text);


        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(new Callback() {
            @Override
            public Object call(Object param) {

                ButtonType type = (ButtonType) param;
                    if (type == okButtonType){
                        String password = passwordField.getText();
                        return password;
                    }
                return null;
            }
        });

       Optional response =  dialog.showAndWait();
        passwordField.requestFocus();

        if (response.isPresent()){

            if (response.get().equals(officialPassword)){
                isConfirmed = true;
            }
        }

        return  isConfirmed;

    }

    public static String showHistoryDialog(String text, String title){
        ObservableList opList = FXCollections.observableArrayList();
        opList.add("A");
        opList.add("B");
        opList.add("C");

        DialogPane dialogPane = new DialogPane();
        dialogPane.setPrefSize(200, 300);
        Dialog dialog = new Dialog();

        Text textHeader = new Text(text);
        ComboBox comboBox = new ComboBox();
        comboBox.setPromptText("Options");

        comboBox.setItems(opList);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(textHeader, comboBox);
        vBox.setSpacing(10);

        dialogPane.setContent(vBox);

        dialog.setDialogPane(dialogPane);
        dialog.setTitle(title);
        dialog.setContentText(text);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(new Callback() {
            @Override
            public Object call(Object param) {

                ButtonType type = (ButtonType) param;
                if (type == okButtonType){
                    return okButtonType;

                }
                return null;
            }
        });
                                Optional<ButtonType> response =  dialog.showAndWait();

                                if (response.isPresent()){
                                    if (response.get() == okButtonType){
                                        option = (String) comboBox.getSelectionModel().getSelectedItem();
                                       if (option == null){
                                           Utility.showMessageBox("PLease select description in optionlist", Alert.AlertType.INFORMATION);
                                           option = null;
                                       }
                                    }else {
                                        option = null;
                                    }
                                }

        return  option;
    }


}
