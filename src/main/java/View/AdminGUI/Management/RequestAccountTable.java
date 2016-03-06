package View.AdminGUI.Management;

import AdminModel.Enum.AccountApproveStatus;
import AdminModel.Enum.AccountStatus;
import AdminModel.RequestAccounts;
import Controller.Controller;
import View.AdminGUI.Listeners.TableAccountListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by reiner on 3/6/2016.
 */
public class RequestAccountTable extends TableView{

    private MenuItem approveItem = new MenuItem("Approve");
    private MenuItem AdminItem = new MenuItem("Approve as Admin");
    private MenuItem reject = new MenuItem("Reject");

    private   TableColumn nameCol  = new TableColumn("Name");
    private TableColumn IdCol = new TableColumn("ID");

    private  ContextMenu contextMenu;
    private TableAccountListener tableAccountListener;

    public RequestAccountTable(BorderPane root) {

        buildColumns();
        buildContextMenus();
        addItemMenuListener();
        addTableListener(root);

        getColumns().addAll(IdCol, nameCol);

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setPadding(new Insets(60));


    }

    private void addItemMenuListener(){

        approveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RequestAccounts ra = (RequestAccounts)  getSelectionModel().getSelectedItem();
                boolean isActivated = tableAccountListener.approveAccount(ra.getId(), AccountApproveStatus.APPROVED);
                if (isActivated){
                    Utility.showMessageBox("Account Activated as Encoder", Alert.AlertType.INFORMATION);
                    refreshTable(getRequestAccounts());
                }else{
                    Utility.showMessageBox("Cannot grant your request right now please try again later", Alert.AlertType.ERROR);
                    refreshTable(getRequestAccounts());
                }

            }
        });


        AdminItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RequestAccounts  ra = (RequestAccounts)  getSelectionModel().getSelectedItem();
                boolean isActivated = tableAccountListener.approveAccount(ra.getId(), AccountApproveStatus.ADMIN);

                if (isActivated){
                    Utility.showMessageBox("Account Activated as Admin", Alert.AlertType.INFORMATION);
                    refreshTable(getRequestAccounts());
                }else{
                    Utility.showMessageBox("Cannot grant your request right now please try again later", Alert.AlertType.ERROR);
                    refreshTable(getRequestAccounts());
                }
            }
        });

        reject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RequestAccounts  ra = (RequestAccounts) getSelectionModel().getSelectedItem();

                boolean isRejected =  tableAccountListener.approveAccount(ra.getId(), AccountApproveStatus.REJECT);

                if (isRejected){
                    Utility.showMessageBox("Account is Now Rejected", Alert.AlertType.INFORMATION);
                    refreshTable(getRequestAccounts());
                }else{
                    Utility.showMessageBox("Cannot grant your request right now please try again later", Alert.AlertType.ERROR);
                    refreshTable(getRequestAccounts());
                }
            }
        });

    }

    private void buildColumns(){
        nameCol.setPrefWidth(400);

        IdCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RequestAccounts, String>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<RequestAccounts, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getId());
            }
        });

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RequestAccounts,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RequestAccounts,String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getName());
            }
        });

    }
    private void buildContextMenus(){
        // request table
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(approveItem, AdminItem, reject);
        contextMenu.setAutoHide(true);

    }
    private void addTableListener(BorderPane root) {

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    double x = event.getScreenX();
                    double y = event.getScreenY();
                    showOption(root, x, y, contextMenu);
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeOptions(contextMenu);
            }
        });



    }

    private ArrayList  getRequestAccounts(){
        ArrayList requestList = Controller.getInstance().getRequestAccounts();
        return requestList;
    }


    private void refreshTable(ArrayList data){
         getItems().clear();
        ObservableList Data = FXCollections.observableArrayList(data);
        setItems(Data);

         refresh();
    }

    private void  showOption(Node root, double x, double y, ContextMenu contextMenu){
        contextMenu.show(root,x,y);
    }


    private void closeOptions(ContextMenu contextMenu){
        contextMenu.hide();
    }

    public void addAccountTableListener(TableAccountListener tableAccountListener){
        this.tableAccountListener = tableAccountListener;
    }


}
