package View.ClientWindow;

import Family.Family;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Didoy on 12/6/2015.
 */
public class SearchTable extends VBox {
    private TableView<Family> table;

    private  TableColumn idCol;
    private  TableColumn nameCol;
    private  TableColumn spouseCol;
    private  Button upButton;

    private ContextMenu contextMenu;
    private MenuItem viewItem;

    private BorderPane root;

    private VBox innerVBox;

    private SearchTabWindow searchTabWindow;

    public SearchTable(BorderPane root){

        searchTabWindow = new SearchTabWindow();

        this.root = root;

        contextMenu = new ContextMenu();
        viewItem = new MenuItem("View");
        contextMenu.getItems().add(viewItem);

        innerVBox = new VBox();

        table = new TableView();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idCol = new TableColumn("ID");
        nameCol = new TableColumn("Name");
        spouseCol = new TableColumn("Spouse Name");

        table.getColumns().addAll(idCol,nameCol,spouseCol);

        Image img = new Image("images/upArrow.png",15,15,false,false);

        upButton  = new Button();
             upButton.setPrefWidth(table.getPrefWidth());
             upButton.setGraphic(new ImageView(img));
             upButton.setPrefWidth(250);


        upButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rollUp();
                System.out.println("uo button is pressed");
            }
        });



        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.SECONDARY)){
                    double x  = event.getScreenX();
                    double y = event.getScreenY();
                    showContextMenu(root, x, y);
                }
            }
        });

        viewItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Family family = table.getSelectionModel().getSelectedItem();
                System.out.println(family.getFamilyinfo().getAddress());
            }
        });


        innerVBox.setPrefHeight(330);
        innerVBox.setPrefWidth(300);
        innerVBox.setAlignment(Pos.TOP_CENTER);

        innerVBox.getChildren().addAll(table, upButton);
        getChildren().addAll(innerVBox);


    }


    private void showContextMenu(BorderPane root, double x, double y){
        contextMenu.show(root, x, y);

    }

    public void setData(ArrayList data){

        ObservableList<Family> Data = FXCollections.observableArrayList(data);
        table.setItems(Data);

        idCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family,Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family,Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().familyId());
            }
        });

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family,String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family,String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getName());
            }
        });

        spouseCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family,String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family,String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getSpouseName());
            }
        });


    }

    private void rollUp(){

        KeyValue kv = new KeyValue(innerVBox.prefHeightProperty() , 0);
        KeyFrame kf = new KeyFrame(Duration.millis(500),kv);

        Timeline t = new Timeline();
                t.getKeyFrames().add(kf);

                t.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ClientWindow.getInstance().removeSearchTable();
                    }
                });

        t.play();


    }

}
