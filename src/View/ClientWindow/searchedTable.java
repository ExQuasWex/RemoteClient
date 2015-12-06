package View.ClientWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * Created by Didoy on 12/6/2015.
 */
public class searchedTable  {

    private ObservableList data;
    private TableView table;
    private VBox vbox;

    public searchedTable(){

        table = new TableView();

        TableColumn idCol = new TableColumn("ID");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn spouseCol = new TableColumn("Spouse Name");

        table.getColumns().addAll(idCol,nameCol,spouseCol);

        vbox = new VBox();

        vbox.getChildren().add(table);


    }

    public void setData(ObservableList data){
        table.setItems(data);
    }
}
