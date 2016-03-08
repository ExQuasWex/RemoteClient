package View.AdminGUI.Home;

import Remote.Method.FamilyModel.FamilyHistory;
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
 * Created by reiner on 3/8/2016.
 */
public class HistoryTable extends BorderPane {

    TableView tableView = new TableView();

    TableColumn Date = new TableColumn("Date");
    TableColumn Name = new TableColumn("Name");
    TableColumn ID = new TableColumn("ID");
    TableColumn num = new TableColumn("#");
    TableColumn admin = new TableColumn("Admin");
    TableColumn changed = new TableColumn("IsChange");
    TableColumn Action = new TableColumn("Action");
    TableColumn Descrip = new TableColumn("Description");

    public HistoryTable() {
        tableView.setColumnResizePolicy(TableBarangayView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPadding(new Insets(20));

        establishColumns();
        tableView.getColumns().addAll(num, ID, Name, Date, changed, Action, admin, Descrip );

        setCenter(tableView);
    }

    private void establishColumns(){

        admin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getAdminName());
            }
        });


        changed.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().isRevoke());
            }
        });


        Action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getAction());
            }
        });

        Descrip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getRevokeDescription());
            }
        });


        ID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getId());
            }
        });


        Date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getDate());
            }
        });


        num.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(param.getValue()));
            }
        });

        Name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FamilyHistory, String>, ObservableValue<FamilyHistory>>() {
            @Override
            public ObservableValue<FamilyHistory> call(TableColumn.CellDataFeatures<FamilyHistory, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyName());
            }
        });

    }

    public void setData(ArrayList list){
        ObservableList data = FXCollections.observableArrayList(list);
        tableView.setItems(data);
    }
}
