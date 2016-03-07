package View.AdminGUI.Home;

import BarangayData.BarangayData;
import PriorityModels.PriorityLevel;
import Remote.Method.FamilyModel.FamilyInfo;
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
 * Created by reiner on 3/7/2016.
 */
public class ResolveTable extends BorderPane{

    TableView tableView = new TableView();

    TableColumn barangayCol = new TableColumn("Barangay");
    TableColumn Name = new TableColumn("Name");
    TableColumn ID = new TableColumn("ID");
    TableColumn num = new TableColumn("#");
    TableColumn helpQuantity = new TableColumn("Recieved Help");

        public ResolveTable(){

            setDataColumn();
            tableView.setColumnResizePolicy(TableBarangayView.CONSTRAINED_RESIZE_POLICY);
            tableView.setPadding(new Insets(20));


            tableView.getColumns().addAll(num, ID, barangayCol, Name, helpQuantity);
            setCenter(tableView);
        }

    private void setDataColumn(){

        num.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyInfo, String>, ObservableValue<FamilyInfo>>() {
            @Override
            public ObservableValue<FamilyInfo> call(TableColumn.CellDataFeatures<FamilyInfo, String> param) {
                return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(param.getValue()));
            }
        });


        ID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyInfo, String>, ObservableValue<FamilyInfo>>() {
            @Override
            public ObservableValue<FamilyInfo> call(TableColumn.CellDataFeatures<FamilyInfo, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyId());
            }
        });


        barangayCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyInfo, String>, ObservableValue<FamilyInfo>>() {
            @Override
            public ObservableValue<FamilyInfo> call(TableColumn.CellDataFeatures<FamilyInfo, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getBarangay());
            }
        });

        Name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyInfo, String>, ObservableValue<FamilyInfo>>() {
            @Override
            public ObservableValue<FamilyInfo> call(TableColumn.CellDataFeatures<FamilyInfo, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getName());
            }
        });

        helpQuantity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyInfo, String>, ObservableValue<FamilyInfo>>() {
            @Override
            public ObservableValue<FamilyInfo> call(TableColumn.CellDataFeatures<FamilyInfo, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getTotalResolution());
            }
        });

    }

    public void setData(ArrayList list){
        ObservableList data = FXCollections.observableArrayList(list);
        tableView.setItems(data);
    }
}
