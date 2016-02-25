package View.ClientWindow;

import Remote.Method.FamilyModel.Family;
import clientModel.ClientEntries;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by Didoy on 2/24/2016.
 */
public class FamilyEntry extends BorderPane  {

    private TableView tableView = new TableView();

    TableColumn id = new TableColumn("ID");
    TableColumn Name = new TableColumn("Name");
    TableColumn date = new TableColumn("Date");


    public FamilyEntry()  {

        tableView.getColumns().addAll(id, Name, date);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setPadding(new Insets(50));
        setCenter(tableView);

    }

    public void setData(ObservableList data){

        tableView.setItems(data);


        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientEntries,Integer>, ObservableValue<ClientEntries>>() {
            @Override
            public ObservableValue<ClientEntries> call(TableColumn.CellDataFeatures<ClientEntries,Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getId());
            }
        });

        Name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientEntries,Integer>, ObservableValue<ClientEntries>>() {
            @Override
            public ObservableValue<ClientEntries> call(TableColumn.CellDataFeatures<ClientEntries,Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyName());
            }
        });
        date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientEntries,Integer>, ObservableValue<ClientEntries>>() {
            @Override
            public ObservableValue<ClientEntries> call(TableColumn.CellDataFeatures<ClientEntries,Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getDate());
            }
        });

    }
}
