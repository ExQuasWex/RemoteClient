package View.AdminGUI.Management;

import AdminModel.ResponseModel.ActiveAccounts;
import Controller.Controller;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by Didoy on 2/5/2016.
 */
public class ManagementTable extends TableView {

    TableColumn id = new TableColumn("ID");
    TableColumn username = new TableColumn("Username");
    TableColumn name = new TableColumn("Name");
    ScrollPane scrollPane = new ScrollPane(this);


    ObservableList data;

    public ManagementTable() {

        getColumns().addAll(id, username, name);
        columInit();
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        scrollPane.setFitToWidth(true);

    }

    private void columInit(){


        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ActiveAccounts, Integer>, ObservableValue<ActiveAccounts>>() {
            @Override
            public ObservableValue<ActiveAccounts> call(TableColumn.CellDataFeatures<ActiveAccounts, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getId());
            }
        });

        username.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ActiveAccounts, String>, ObservableValue<ActiveAccounts>>() {
            @Override
            public ObservableValue<ActiveAccounts> call(TableColumn.CellDataFeatures<ActiveAccounts, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getUsername());
            }
        });

        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ActiveAccounts, String>, ObservableValue<ActiveAccounts>>() {
            @Override
            public ObservableValue<ActiveAccounts> call(TableColumn.CellDataFeatures<ActiveAccounts, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getName());
            }
        });



    }

    public  void setData(ArrayList list){
         data = FXCollections.observableArrayList(list);
        setItems(data);
        refresh();
    }

    public ScrollPane getScrollPane()
        {
            return  scrollPane;
        }

}
