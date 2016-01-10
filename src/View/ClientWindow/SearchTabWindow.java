package View.ClientWindow;

import Remote.Method.FamilyModel.Family;
import Remote.Method.FamilyModel.FamilyInfo;
import Remote.Method.FamilyModel.FamilyPoverty;
import View.Login.CustomStage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Didoy on 1/6/2016.
 */
public class SearchTabWindow extends CustomStage {

    private TabPane tabPane;
    private BorderPane root;

    private static SearchTabWindow searchTabWindow = null;

    private SearchTabWindow(){

        setTitle("Search Tab");
        tabPane = new TabPane();
        root = new BorderPane();

        root.setCenter(tabPane);

        Scene scene = new Scene(root, 600, 800);

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

        Map map  = new LinkedHashMap<>();
        map.put("Name",familyInfo.getName());
        map.put("MaritalStatus",familyInfo.getMaritalStatus());
        map.put("Spouse",familyInfo.getSpouseName());
        map.put("Number of Childred",familyInfo.getNumofChildren());
        map.put("Entry Date",familyInfo.getInputDate());
        map.put("Year Issued",familyInfo.getSurveyedYr());
        map.put("Year of Residency",familyInfo.getResidencyYr());
        map.put("Barangay",familyInfo.getBarangay());
        map.put("Address",familyInfo.getAddress());
        map.put("Age",familyInfo.getAge());


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

        tab.setContent(tableView);
        tabPane.getTabs().add(tab);

    }


}
