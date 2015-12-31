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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

    private GridPane grid;
    private boolean isNoTificationOut;
    public ManagementPane(String totalRequest){

        grid = initialize(totalRequest);

        setTop(grid);

    }

    private GridPane initialize(String totalRequest){
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
                    getRequestTable();
                } else{
                    isNoTificationOut = true;
                    sp.getChildren().removeAll(redRect,text);
                    getRequestTable();
                }
            }
        });

        return gp;

    }

    private void getRequestTable(){

        ArrayList requestList = Controller.getInstance().getRequestAccounts();
        ObservableList Data = FXCollections.observableArrayList(requestList);

        TableView table = new TableView();

        table.setItems(Data);

        TableColumn nameCol = new TableColumn("Name");

        table.getColumns().add(nameCol);

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RequestAccounts,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RequestAccounts,String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getName());
            }
        });

        setCenter(table);
        setMargin(table,new Insets(10,40,20,20));

    }



}
