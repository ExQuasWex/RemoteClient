package View.ClientWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Didoy on 2/14/2016.
 */
public class SearchPane extends HBox implements Initializable {

    @FXML private TextField searchText;
    @FXML private Button searchBtn;


    public SearchPane() {
        getStylesheets().add("/CSS/SearchPaneCSS.css");
        getStyleClass().add("hBox-pane");

    }

    public void LoadFXML(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ClientWindow/SearchPane.fxml"));

        try {

            HBox hBox = fxmlLoader.load();
            setAlignment(Pos.CENTER);
            getChildren().addAll(hBox);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
