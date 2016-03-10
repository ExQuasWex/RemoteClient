package View.AdminGUI.Management;

import AdminModel.Enum.AccountApproveStatus;
import AdminModel.Enum.AccountStatus;
import Controller.Controller;
import View.AdminGUI.Listeners.TableAccountListener;
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

import java.util.ArrayList;


/**
 * Created by Didoy on 12/24/2015.
 */
public class ManagementPane extends  BorderPane {

    private ToggleGroup toggleGroup;

    private GridPane grid;
    private boolean isNoTificationOut;

    private TableAccountListener tableListener;

    ManagementTable managementTable = new ManagementTable(this);
    RequestAccountTable requestAccountTable = new RequestAccountTable(this);

    ToggleButton requestToggle = new ToggleButton("Request Accounts");
    ToggleButton manageToggle = new ToggleButton("Manage Accounts");

    Controller controller = Controller.getInstance();

    public ManagementPane(BorderPane root){

        int accounts = getRequestNumber();

        // Initialize
        grid = initialize(root, accounts);
        toggleGroup.selectToggle(requestToggle);

        addAccountTableListeners();
        setRequestTableCenter();
        setTop(grid);
    }
    private GridPane initialize(BorderPane root, int totalRequest){
        final StackPane sp = new StackPane();
        final Rectangle redRect = new Rectangle();
        final Text text  = new Text();

        if (totalRequest >= 1){

            text.setText(String.valueOf(totalRequest));
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

            isNoTificationOut = false;

        }
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

        if (totalRequest >= 1){
            gp.getChildren().addAll(sp, manageToggle);
        }else {
            gp.getChildren().addAll(requestToggle, manageToggle);
        }

        requestToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isNoTificationOut){
                    isNoTificationOut = true;
                    sp.getChildren().removeAll(redRect, text);
                }
                setRequestTableCenter();
            }
        });
        manageToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setAccountTableCenter();
            }
        });

        return gp;

    }

    // Optimize here
    public void addTableListener(TableAccountListener tableAccountListener){
        tableListener = tableAccountListener;
    }

    private int getRequestNumber(){
        int pendingAccounts = Controller.getInstance().getPendingAccounts();

        return pendingAccounts;
    }

    private void  setAccountTableCenter(){
        ArrayList list = controller.getActiveAccounts();
        managementTable.setData(list);
        setCenter(managementTable);

    }
    private void setRequestTableCenter(){
        ArrayList list = controller.getRequestAccounts();
        ObservableList Data = FXCollections.observableArrayList(list);
        requestAccountTable.setItems(Data);
        setCenter(null);
        setCenter(requestAccountTable);

    }

    private void addAccountTableListeners(){
        managementTable.addAccountTableListener(new TableAccountListener() {
            @Override
            public boolean updateAccountStatus(int id, AccountStatus status) {
              return tableListener.updateAccountStatus(id, status);
            }

            @Override
            public boolean approveAccount(int id, AccountApproveStatus status) {
                return false;
            }
        });

        requestAccountTable.addAccountTableListener(new TableAccountListener() {
            @Override
            public boolean updateAccountStatus(int id, AccountStatus status) {
                return false;
            }

            @Override
            public boolean approveAccount(int id, AccountApproveStatus status) {
                return tableListener.approveAccount(id, status);
            }
        });
    }

}
