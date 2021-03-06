package View.ClientWindow;

import Remote.Method.FamilyModel.Family;
import View.ClientWindow.Listeners.EditableListener;
import View.ClientWindow.Listeners.SearchTableListener;
import View.ClientWindow.Listeners.ShowEditFormListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
    private MenuItem editItem;

    private BorderPane root;
    private VBox innerVBox;

    private SearchTableListener searchTableListener;
    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private Double HEIGHT = 0.0;

    private EditableListener editableListener;
    public SearchTable(BorderPane root){

        HEIGHT = screen.getHeight()-100;

        this.root = root;

        contextMenu = new ContextMenu();
        viewItem = new MenuItem("View");
        editItem = new MenuItem("Edit");

        contextMenu.getItems().addAll(viewItem, editItem);

        innerVBox = new VBox();

        table = new TableView();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

        /*
         Trigger to create new window
         and view the Information
         of the searched
         person
          */
        viewItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList familyList = table.getSelectionModel().getSelectedItems();
                SearchTabWindow searchTabWindow = SearchTabWindow.getInstance();

                searchTabWindow.addTab(familyList);
                searchTabWindow.show();
            }
        });

        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               Family family =  table.getSelectionModel().getSelectedItem();
                editableListener.Edit(family);
            }
        });


        table.setPrefHeight(HEIGHT);
        innerVBox.setPrefWidth(screen.getWidth()/5);
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
    private void resetRollUp(){
        table.setPrefHeight(HEIGHT);
    }

    private void rollUp(){

        KeyValue kv = new KeyValue(table.prefHeightProperty() , 0);
        KeyFrame kf = new KeyFrame(Duration.millis(500),kv);

        Timeline t = new Timeline();
                t.getKeyFrames().add(kf);

                t.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        searchTableListener.rollUp();
                        resetRollUp();
                    }
                });

        t.play();
    }

    public void addSearchTableListener(SearchTableListener searchTableListener){
        this.searchTableListener = searchTableListener;
    }

    public void addEditableListener(EditableListener editableListener ){
        this.editableListener = editableListener;
    }


}
