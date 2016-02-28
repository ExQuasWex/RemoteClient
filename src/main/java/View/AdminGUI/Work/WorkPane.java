package View.AdminGUI.Work;

import AdminModel.Params;
import AdminModel.ResponseModel.BarangayFamily;
import Controller.Controller;
import Remote.Method.FamilyModel.Family;
import ListModels.UiModels;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by Didoy on 2/1/2016.
 */
public class WorkPane extends BorderPane {

    private HBox toolBox = new HBox();
    private HBox HBOX = new HBox();
    private Button View = new Button("View");

    private TableView tableView = new TableView() ;

    private Button SelectAll = new Button("Select All");
    private Button Deselect  = new Button("Deselect All");
    private Button save  = new Button("Save Changes");

    private ComboBox<String> barangay = new ComboBox<String>();
    private ComboBox<String> month = new ComboBox<String>();
    private ComboBox<String> year = new ComboBox<String>();

    private BooleanCell booleanCell = new BooleanCell();
    private TableActionCell tableActionCell = new TableActionCell();

    private Controller ctr = Controller.getInstance();
    private Params params;

    private     TableColumn check;
    public WorkPane() {

        generateHeader();
        generateTools();

        establishTable();

        View.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String Month = (String) month.getSelectionModel().getSelectedItem();
                String Year = ( (String)year.getSelectionModel().getSelectedItem());
                String Barangay  = (String) barangay.getSelectionModel().getSelectedItem();

                String date = Utility.concatinateYearAndMonth(Year, Month);

                Params params = new Params(date , Barangay);
                ArrayList list = ctr.getFamilyBarangay(params);

                showTable(list);

            }
        });

        SelectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkAll();

            }
        });
        Deselect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deselectAll();

            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("save");

            }
        });

    }

    private void establishTable(){

        check = new TableColumn();
        TableColumn ID = new TableColumn();
        TableColumn name = new TableColumn();
        TableColumn spouseName = new TableColumn();
        TableColumn surveyedDate = new TableColumn();
        TableColumn inputedDate = new TableColumn();
        TableColumn Action = new TableColumn();

        check.setText("Selection");
        ID.setText("ID");
        name.setText("Name");
        spouseName.setText("SpouseName");
        inputedDate.setText("Encode Date");
        surveyedDate.setText("Survey Date");
        Action.setText("Action");


        Action.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableActionCell();
            }
        });


        ID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().familyId());
            }
        });


        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getName());
            }
        });

        spouseName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getSpouseName());
            }
        });


        inputedDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getInputDate());
            }
        });

        surveyedDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getSurveyedYr());
            }
        });


        // response from node cells in Tableview

        booleanCell.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean uncCheck, Boolean iscCheck) {

              ObservableList x  =  tableView.getSelectionModel().getSelectedIndices();
                for (Object index : x){
                    System.out.println(index);
                }
            }
        });


        tableView.getColumns().addAll(ID, name, spouseName, inputedDate, surveyedDate, Action);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    private void showTable(ArrayList list){

        ObservableList<BarangayFamily> ItemList = FXCollections.observableArrayList(list);

        tableView.setItems(ItemList);

        setCenter(tableView);
    }

    private void generateTools(){

        toolBox.setAlignment(Pos.CENTER);
        toolBox.setPadding(new Insets(50));
        toolBox.setSpacing(7);

        toolBox.getChildren().addAll(SelectAll, Deselect, save);

        setBottom(toolBox);

    }

    private void generateHeader(){

        ObservableList barangayList = UiModels.getBarangayListModel();
        ObservableList monthList = UiModels.getMonthListModel();

        View.setPrefWidth(80);

        barangay.setItems(barangayList);
        month.setItems(monthList);
        year.setItems(getYears());

        barangay.setPromptText("Barangay");
        month.setPromptText("Month");
        year.setPromptText("Year");

        HBOX.setAlignment(Pos.CENTER);
        HBOX.setPadding(new Insets(50));
        HBOX.setSpacing(7);

        HBOX.getChildren().addAll(barangay, month, year, View);

        setTop(HBOX);

    }


    private ObservableList<String> getYears(){
        ObservableList<String> yearList = FXCollections.observableArrayList(ctr.getYears());

        return  yearList;
    }


    private void  checkAll(){
        tableView.getSelectionModel().selectAll();

    }

    private void  deselectAll(){
        tableView.getSelectionModel().clearSelection();

    }



}
