package View.AdminGUI.Management;

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
    private String totalRequest;

    private TableAccountListener tableListener;

    ManagementTable managementTable = new ManagementTable(this);
    RequestAccountTable requestAccountTable = new RequestAccountTable(this);

    ToggleButton requestToggle = new ToggleButton("Request Accounts");
    ToggleButton manageToggle = new ToggleButton("Manage Accounts");

    Controller controller = Controller.getInstance();

    public ManagementPane(BorderPane root){

        totalRequest = getRequestNumber();

        // Initialize
        grid = initialize(root, totalRequest);
        toggleGroup.selectToggle(requestToggle);

        setRequestTableCenter();
        setTop(grid);
    }
    private GridPane initialize(BorderPane root, String totalRequest){
        final StackPane sp = new StackPane();
        final Rectangle redRect = new Rectangle();
        final Text text  = new Text();

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
                    setRequestTableCenter();
                } else{
                    isNoTificationOut = true;
                    sp.getChildren().removeAll(redRect, text);
                    setRequestTableCenter();
                }
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

    private String getRequestNumber(){
        int pendingAccounts = Controller.getInstance().getPendingAccounts();
        String pendingStr = String.valueOf(pendingAccounts);

        return pendingStr;
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

}
