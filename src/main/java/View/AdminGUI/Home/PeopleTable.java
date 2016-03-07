package View.AdminGUI.Home;

import Remote.Method.FamilyModel.Family;
import Remote.Method.FamilyModel.FamilyHistory;
import View.AdminGUI.Work.TableBarangayView;
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
 * Created by reiner on 3/8/2016.
 */
public class PeopleTable extends BorderPane {

    TableView tableView = new TableView();

    TableColumn num = new TableColumn("#");
    TableColumn ID = new TableColumn("ID");
    TableColumn barngay = new TableColumn("Barangay");
    TableColumn Name = new TableColumn("Name");

    TableColumn imputDate = new TableColumn("Input Date");
    TableColumn address = new TableColumn("Address");

    TableColumn rsidency = new TableColumn("Year Residency");
    TableColumn children = new TableColumn("Num of Children");

    public PeopleTable() {
        tableView.setColumnResizePolicy(TableBarangayView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPadding(new Insets(20));

        establishColumns();
        tableView.getColumns().addAll(num, ID, Name, barngay, imputDate, address, rsidency, children );

        setCenter(tableView);
    }

    private void establishColumns(){
        Name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getName());
            }
        });

        num.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(param.getValue()));
            }
        });

        ID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getFamilyId());
            }
        });


        barngay.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getBarangay());
            }
        });

        rsidency.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getResidencyYr());
            }
        });


        address.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getAddress());
            }
        });


        imputDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getInputDate());
            }
        });


        children.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getNumofChildren());
            }
        });


    }

    public void setData(ArrayList list){
        ObservableList data = FXCollections.observableArrayList(list);
        tableView.setItems(data);
    }
}
