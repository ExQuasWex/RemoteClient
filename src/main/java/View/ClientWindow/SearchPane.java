package View.ClientWindow;

import View.ClientWindow.Listeners.SearchPaneListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Didoy on 2/14/2016.
 */
public class SearchPane extends HBox  {

    private TextField textField=  new TextField();
    private Button searchBtn = new Button("Search");


    private SearchPaneListener searchPaneListener;


    public SearchPane() {
        getStylesheets().add("/CSS/SearchPaneCSS.css");
        getStyleClass().add("hBox-pane");

        init();

    }

    private void init(){
        searchBtn.setPrefWidth(150);

        textField.setPrefWidth(400);
        textField.setPromptText("Search Family");
        textField.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        setSpacing(5);
        setPadding(new Insets(5));
        getChildren().addAll(textField, searchBtn);

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchPaneListener.searchFamily(textField.getText());
            }
        });

    }

    public void addSearchPaneListener(SearchPaneListener SearchPaneListener){
        searchPaneListener = SearchPaneListener;
        System.out.println("searchPaneListener added");

    }

}
