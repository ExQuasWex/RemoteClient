package View.AdminGUI.Management;

import AdminModel.ResponseModel.ActiveAccounts;
import Controller.Controller;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by Didoy on 2/5/2016.
 */
public class ManagementTable extends TableView {

    TableColumn id = new TableColumn("ID");
    TableColumn username = new TableColumn("Username");
    TableColumn name = new TableColumn("Name");
    TableColumn status = new TableColumn("Status");
    TableColumn requestStatus = new TableColumn("Account Status");
    private  ContextMenu manageContextMenu;

    private MenuItem enable = new MenuItem("Enable");
    private MenuItem disable = new MenuItem("Disable");
    private MenuItem delete = new MenuItem("Delete");

    ObservableList data;

    public ManagementTable(BorderPane root) {

        buildColumns();
        buildContextMenus();
        addMenuItemListeners();
        addTableListeners(root);

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setPadding(new Insets(60));
        getColumns().addAll(id, name, username, requestStatus, status);

    }

    private void  buildColumns(){

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


        requestStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ActiveAccounts, String>, ObservableValue<ActiveAccounts>>() {
            @Override
            public ObservableValue<ActiveAccounts> call(TableColumn.CellDataFeatures<ActiveAccounts, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getRequestStatus());
            }
        });

        status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ActiveAccounts, String>, ObservableValue<ActiveAccounts>>() {
            @Override
            public ObservableValue<ActiveAccounts> call(TableColumn.CellDataFeatures<ActiveAccounts, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getStatus());
            }
        });

    }


    public  void setData(ArrayList list){
         data = FXCollections.observableArrayList(list);
        setItems(data);
        refresh();
    }


    private void addMenuItemListeners(){

        enable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateAccountStatus(AccountStatus.APPROVED);
            }
        });

        disable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateAccountStatus(AccountStatus.DISABLE);
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateAccountStatus(AccountStatus.DELETE);
            }
        });

    }

    private void addTableListeners(BorderPane root){

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    double x = event.getScreenX();
                    double y = event.getScreenY();
                    showOption(root, x, y, manageContextMenu);
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeOptions(manageContextMenu);
            }
        });

    }

    private void buildContextMenus(){
        // manage table
        manageContextMenu = new ContextMenu();
        manageContextMenu.getItems().addAll(enable, disable, delete);

    }
    private void  showOption(Node root, double x, double y, ContextMenu contextMenu){
        contextMenu.show(root,x,y);
    }

    private void closeOptions(ContextMenu contextMenu){
        contextMenu.hide();
    }

    private void updateAccountStatus(AccountStatus accountStatus){
        System.out.println(accountStatus.toString());
    }


}
