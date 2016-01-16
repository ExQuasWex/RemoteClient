package utility;

import Controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by Didoy on 9/1/2015.
 */
public class Utility {

     Controller controller;
     private boolean isConnected;
    public Utility(){
        controller = Controller.getInstance();
    }

    public boolean confirmExit(){
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

    public static void ClearComponents(Pane mainpane){
        for (Node node: mainpane.getChildren()){

                        if (node.getClass().equals(GridPane.class) || node.getClass().equals(VBox.class) ||
                                node.getClass().equals(HBox.class) || node.getClass().equals(BorderPane.class)){

                                Pane childrenPane = (Pane) node;

                                    for (Node childrenNode : childrenPane.getChildren()){

                                        if (childrenNode.getClass().equals(TextField.class)){
                                            ((TextField) childrenNode).setText("");
                                        }
                                    }

                        }else if (node.getClass().equals(ScrollPane.class)){
                                    ScrollPane scrollPane =  ((ScrollPane) node);
                                    Pane pane1 =  (Pane)scrollPane.getContent();
                                    ClearComponents(pane1);
                        }
                        else{
                                for (Node childrenNode : mainpane.getChildren()){

                                    if (childrenNode.getClass().equals(TextField.class)){
                                        ((TextField) childrenNode).setText("");
                                    }else if (childrenNode.getClass().equals(ComboBox.class)){
                                        ((ComboBox) childrenNode).getSelectionModel().clearSelection();

                                    }

                                }

                        }


        }

    }



}
