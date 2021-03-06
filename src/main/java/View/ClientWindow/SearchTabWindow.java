package View.ClientWindow;

import Remote.Method.FamilyModel.Family;
import Remote.Method.FamilyModel.FamilyHistory;
import Remote.Method.FamilyModel.FamilyInfo;
import Remote.Method.FamilyModel.FamilyPoverty;
import View.ClientWindow.Listeners.EditableListener;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Didoy on 1/6/2016.
 */
public class SearchTabWindow extends CustomStage {

    private TabPane tabPane;
    private BorderPane root;
    private VBox vBox = new VBox();
    private Button editButton = new Button("Edit");

    private EditableListener editableListener;

    private static SearchTabWindow searchTabWindow = null;

    private SearchTabWindow(){

        setTitle("Search Tab");
        tabPane = new TabPane();
        root = new BorderPane();

        vBox.setPadding(new Insets(50));
        vBox.setAlignment(Pos.TOP_CENTER);

        editButton.setPrefHeight(15);
        editButton.setPrefWidth(70);
        //vBox.getChildren().add(editButton);

        root.setCenter(tabPane);
        root.setAlignment(vBox, Pos.CENTER);
        root.setBottom(vBox);

        Scene scene = new Scene(root, 400, 500);

        setScene(scene);

         setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    tabPane.getTabs().clear();
                }
            });

    }

    public static SearchTabWindow getInstance(){

        if (searchTabWindow == null){
            searchTabWindow = new SearchTabWindow();
        }
        return  searchTabWindow;
    }

    public void addTab( ObservableList familyList){
        int x = 0;

        while (x <= familyList.size() - 1){
            Map map  = new LinkedHashMap<>();

            Family fam = (Family) familyList.get(x);

            FamilyInfo familyInfo = fam.getFamilyinfo();
            FamilyPoverty familyPoverty = fam.getFamilypoverty();
            FamilyHistory familyHistory = fam.getFamilyHistory();

            String name =  familyInfo.getName();
            Tab tab = new Tab(name);

            String numofChildren = String.valueOf(familyInfo.getNumofChildren());
            String surveyedYr = String.valueOf(familyInfo.getSurveyedYr());
            String residenceYR = String.valueOf(familyInfo.getResidencyYr());

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

            map.put("","");

            map.put("Occupancy",familyPoverty.getOccupancy());
            map.put("UnderEmployed",familyPoverty.getIsunderEmployed());
            map.put("Ownership",familyPoverty.getOwnership());
            map.put("Other Income",familyPoverty.getHasotherIncome());
            map.put("Threshold",familyPoverty.getIsbelow8k());
            map.put("Children in School",familyPoverty.getChildreninSchool());


                if (familyHistory != null){
                    map.put("ID",familyHistory.getId());
                    map.put("Action",familyHistory.getAction());
                    map.put("Date",familyHistory.getDate());
                    map.put("Admin",familyHistory.getAdminName());
                    map.put("Revoke",familyHistory.isRevoke());
                    map.put("Revoke Description",familyHistory.getRevokeDescription());
                }



            ObservableList data = FXCollections.observableArrayList(map.entrySet());

            TableView tableView = new TableView();
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn Category = new TableColumn("Category");
            TableColumn Value = new TableColumn("Value");

            tableView.setItems(data);

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


            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    editableListener.Edit(fam);
                }
            });


            tab.setContent(tableView);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);

            x ++;
        }

        requestFocus();

    }

    public void addEditableListener(EditableListener editableListener){
            this.editableListener = editableListener;
    }

}
