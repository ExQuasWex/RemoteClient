package View.AdminGUI;

import AdminModel.RequestAccounts;
import Controller.Controller;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;


/**
 * Created by Didoy on 12/24/2015.
 */
public class ManagementPane extends  BorderPane {

    private ToggleGroup toggleGroup;
    private  ContextMenu contextMenu;
    private  TableView table;

    private GridPane grid;
    private boolean isNoTificationOut;

    private TableItemListener tableListener;

    private String totalRequest;

    private   TableColumn nameCol;

    public ManagementPane(BorderPane root){

        totalRequest = getRequestNumber();

        contextMenu = new ContextMenu();

        MenuItem approveItem = new MenuItem("Approve");
        MenuItem AdminItem = new MenuItem("Approve as Admin");
        MenuItem reject = new MenuItem("Reject");

        approveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RequestAccounts  ra = (RequestAccounts) table.getSelectionModel().getSelectedItem();
                boolean isActivated = tableListener.Approve(ra);
                        if (isActivated){
                            Controller.showMessageBox("Account Activated as Encoder", Alert.AlertType.INFORMATION);
                            refreshTable();
                        }else{
                            Controller.showMessageBox("Cannot grant your request right now please try again later", Alert.AlertType.ERROR);
                            refreshTable();
                        }

            }
        });


        AdminItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RequestAccounts  ra = (RequestAccounts) table.getSelectionModel().getSelectedItem();
                 boolean isActivated =  tableListener.ApproveAdmin(ra);

                        if (isActivated){
                            Controller.showMessageBox("Account Activated as Admin", Alert.AlertType.INFORMATION);
                            refreshTable();
                        }else{
                            Controller.showMessageBox("Cannot grant your request right now please try again later", Alert.AlertType.ERROR);
                            refreshTable();
                        }
            }
        });

        reject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RequestAccounts  ra = (RequestAccounts) table.getSelectionModel().getSelectedItem();

                boolean isRejected =  tableListener.Reject(ra);

                        if (isRejected){
                            Controller.showMessageBox("Account is Now Rejected", Alert.AlertType.INFORMATION);
                            refreshTable();
                        }else{
                            Controller.showMessageBox("Cannot grant your request right now please try again later", Alert.AlertType.ERROR);
                            refreshTable();
                        }
            }
        });



        contextMenu.getItems().addAll(approveItem,AdminItem,reject);


        // Initialize
        grid = initialize(root, totalRequest);

        setTop(grid);

    }

    private GridPane initialize(BorderPane root, String totalRequest){
        final StackPane sp = new StackPane();
        final Rectangle redRect = new Rectangle();
        final Text text  = new Text();

        ToggleButton requestToggle = new ToggleButton("Request");
        ToggleButton manageToggle = new ToggleButton("Manage");

        text.setText(totalRequest);
        text.setFill(Color.WHITE);

        redRect.setFill(Color.web("#ff4d4d"));
        redRect.setArcWidth(6);
        redRect.setArcHeight(6);

        redRect.setWidth(text.getLayoutBounds().getWidth()+5);
        redRect.setHeight(15);

        sp.getChildren().addAll(requestToggle,redRect,text);
        sp.setMargin(redRect, new Insets(0, 0, 0, 65));
        sp.setMargin(text, new Insets   (0, 0, 0, 65 ));

        sp.setAlignment(Pos.CENTER);

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10));

        toggleGroup = new ToggleGroup();

        requestToggle.setToggleGroup(toggleGroup);
        manageToggle.setToggleGroup(toggleGroup);

        requestToggle.setPrefWidth(150);
        manageToggle.setPrefWidth(150);

        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(5);

        gp.setConstraints(sp,0,0,1,1, HPos.CENTER, VPos.TOP);
        gp.setConstraints(manageToggle, 1,0,1,1, HPos.CENTER, VPos.TOP);
        gp.getChildren().addAll(sp, manageToggle);

        isNoTificationOut = false;
        requestToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (isNoTificationOut) {
                    getRequestTable(root);
                } else{
                    isNoTificationOut = true;
                    sp.getChildren().removeAll(redRect,text);
                    getRequestTable(root);
                }
            }
        });

        return gp;

    }

    private void getRequestTable(BorderPane root){

        table = new TableView();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList Data = FXCollections.observableArrayList(getRequestAccounts());

        table.setItems(Data);


        // create columns
         nameCol = new TableColumn("Name");
        TableColumn IdCol = new TableColumn("ID");


        nameCol.setPrefWidth(400);

        table.getColumns().addAll(IdCol, nameCol);


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



        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.SECONDARY)){
                      double x  = event.getScreenX();
                      double y = event.getScreenY();
                      showOption(root,x,y);
                    }
            }
        });

        table.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    closeOptions();
            }
        });



        setCenter(table);
        setMargin(table,new Insets(10,40,20,20));

    }

    private void  showOption(BorderPane root, double x, double y){

        contextMenu.show(root,x,y);

    }

    private void closeOptions(){
        contextMenu.hide();
    }

    public void addTableListener(TableItemListener tableItemListener){
        tableListener = tableItemListener;
    }

    private String getRequestNumber(){
        int pendingAccounts = Controller.getInstance().getPendingAccounts();
        String pendingStr = String.valueOf(pendingAccounts);

        return pendingStr;
    }

    private ArrayList  getRequestAccounts(){
        ArrayList requestList = Controller.getInstance().getRequestAccounts();
        return requestList;
    }

    private void refreshTable(){

        table.getItems().clear();
        ObservableList Data = FXCollections.observableArrayList(getRequestAccounts());
        table.setItems(Data);

        table.refresh();

    }

}
