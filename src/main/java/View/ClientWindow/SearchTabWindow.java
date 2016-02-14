package View.ClientWindow;

import Remote.Method.FamilyModel.Family;
import Remote.Method.FamilyModel.FamilyInfo;
import View.Login.CustomStage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Didoy on 1/6/2016.
 */
public class SearchTabWindow extends CustomStage {

    private TabPane tabPane;
    private BorderPane root;
    private VBox vBox = new VBox();

    private static SearchTabWindow searchTabWindow = null;

    private SearchTabWindow(){

        setTitle("Search Tab");
        tabPane = new TabPane();
        root = new BorderPane();

        vBox.setPadding(new Insets(50));
        vBox.setAlignment(Pos.TOP_CENTER);

        root.setCenter(tabPane);
        root.setAlignment(vBox, Pos.CENTER);
        root.setBottom(vBox);

        Scene scene = new Scene(root, 400, 500);

        setScene(scene);
        show();



    }

    public static SearchTabWindow getInstance(){

        if (searchTabWindow == null){
            searchTabWindow = new SearchTabWindow();
        }else {
            searchTabWindow.show();
        }
        return  searchTabWindow;
    }

    public void addTab(String tabName, Family fam){
        Tab tab = new Tab(tabName);

        FamilyInfo familyInfo = fam.getFamilyinfo();

        String numofChildren = String.valueOf(familyInfo.getNumofChildren());
        String surveyedYr = String.valueOf(familyInfo.getSurveyedYr());
        String residenceYR = String.valueOf(familyInfo.getResidencyYr());

        Map map  = new LinkedHashMap<>();
        map.put("Name",familyInfo.getName());
        map.put("MaritalStatus",familyInfo.getMaritalStatus());
        map.put("Spouse",familyInfo.getSpouseName());
        map.put("Number of Childred",numofChildren);
        map.put("Entry Date",familyInfo.getInputDate());
        map.put("Year Issued",surveyedYr);
        map.put("Year of Residency",residenceYR);
        map.put("Barangay",familyInfo.getBarangay());
        map.put("Address",familyInfo.getAddress());
        map.put("Age",familyInfo.getAge());


        ObservableList data = FXCollections.observableArrayList(map.entrySet());

        TableView tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(true);

        TableColumn Category = new TableColumn("Category");
        TableColumn Value = new TableColumn("Value");

        tableView.setItems(data);


        Value.setCellFactory(TextFieldTableCell.forTableColumn());


        Category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getKey());
            }
        });


        Value.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue());
            }
        });


        Value.setSortType(TableColumn.SortType.DESCENDING);
        Category.setSortType(TableColumn.SortType.DESCENDING);

        tableView.getColumns().addAll(Category, Value);

        Button editButton = new Button("Edit");
        editButton.setPrefHeight(15);
        editButton.setPrefWidth(70);
        vBox.getChildren().add(editButton);


        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


//             EditWindow editWindow =   EditWindow.getInstance();
//                editWindow.LoadFXML();

            }
        });


        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        tabPane.getTabs().add(tab);

    }


}
