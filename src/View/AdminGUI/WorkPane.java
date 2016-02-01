package View.AdminGUI;

import AdminModel.Params;
import AdminModel.ResponseModel.BarangayFamily;
import Controller.Controller;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

    private TableView tabableView = new TableView() ;

    private Button SelectAll = new Button("Select All");
    private Button Deselect  = new Button("Deselect All");
    private Button save  = new Button("Save Changes");

    private ComboBox<String> barangay = new ComboBox<String>();
    private ComboBox<String> month = new ComboBox<String>();
    private ComboBox<String> year = new ComboBox<String>();

    private Controller ctr = Controller.getInstance();
    private Params params;

    public WorkPane() {

        generateHeader();
        generateTools();


        View.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                params = new Params();
                int yearInt = Integer.parseInt(year.getSelectionModel().getSelectedItem());
                int monthInt = Utility.convertStringMonth(month.getSelectionModel().getSelectedItem());

                params.setBarangay1(barangay.getSelectionModel().getSelectedItem());
                params.setYear(yearInt);
                params.setMonth(monthInt);

                ArrayList list = ctr.getFamilyBarangay(params);

                generateTable(list);
            }
        });

        SelectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("SelectAll");

            }
        });
        Deselect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Deselect");

            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("save");

            }
        });

    }

    private void generateTable(ArrayList list){

        ObservableList<BarangayFamily> ItemList = FXCollections.observableArrayList(list);

        tabableView.setItems(ItemList);

        TableColumn check = new TableColumn();
        TableColumn ID = new TableColumn();
        TableColumn name = new TableColumn();
        TableColumn spouseName = new TableColumn();
        TableColumn Date = new TableColumn();
        TableColumn Action = new TableColumn();

        check.setText("Selection");
        ID.setText("ID");
        name.setText("Name");
        spouseName.setText("SpouseName");
        Date.setText("Date");
        Action.setText("Action");


        Action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures param) {
                return  new ReadOnlyObjectWrapper(param.getValue());
            }
        });
        Action.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableActionCell();
            }
        });

        ID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayFamily, Integer>, ObservableValue<BarangayFamily>>() {
            @Override
            public ObservableValue<BarangayFamily> call(TableColumn.CellDataFeatures<BarangayFamily, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getID());
            }
        });

        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayFamily, String>, ObservableValue<BarangayFamily>>() {
            @Override
            public ObservableValue<BarangayFamily> call(TableColumn.CellDataFeatures<BarangayFamily, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getName());
            }
        });

        spouseName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayFamily, String>, ObservableValue<BarangayFamily>>() {
            @Override
            public ObservableValue<BarangayFamily> call(TableColumn.CellDataFeatures<BarangayFamily, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getSpouseName());
            }
        });

        Date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayFamily, String>, ObservableValue<BarangayFamily>>() {
            @Override
            public ObservableValue<BarangayFamily> call(TableColumn.CellDataFeatures<BarangayFamily, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getDate());
            }
        });

        check.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new BooleanCell();
            }
        });



        tabableView.getColumns().addAll(check, ID, name, spouseName, Date, Action);

        tabableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setCenter(tabableView);


    }

    private void generateTools(){


        toolBox.setAlignment(Pos.CENTER);
        toolBox.setPadding(new Insets(50));
        toolBox.setSpacing(7);


        toolBox.getChildren().addAll(SelectAll, Deselect, save);

        setBottom(toolBox);

    }

    private void generateHeader(){

        View.setPrefWidth(80);

        barangay.setItems(getBarangayCb());
        month.setItems(getMonths());
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

    private ObservableList<String> getBarangayCb(){
        ObservableList<String> baranagayList = FXCollections.observableArrayList();
        baranagayList.add("Atlo Bola");
        baranagayList.add("Bical");
        baranagayList.add("Bundangul");
        baranagayList.add("Cacutud");
        baranagayList.add("Calumpang");
        baranagayList.add("Camchilles");
        baranagayList.add("Dapdap");
        baranagayList.add("Dau");
        baranagayList.add("Dolores");
        baranagayList.add("Duquit");
        baranagayList.add("Lakandula");
        baranagayList.add("Mabiga");
        baranagayList.add("Macapagal village");
        baranagayList.add("Mamatitang");
        baranagayList.add("Mangalit");
        baranagayList.add("Marcos village");
        baranagayList.add("Mawaque");
        baranagayList.add("Paralayunan");
        baranagayList.add("Publasyon");
        baranagayList.add("San Francisco");
        baranagayList.add("San Joaquin");
        baranagayList.add("Sta. Ines");
        baranagayList.add("Sta. Maria");
        baranagayList.add("Sto. Rosario");
        baranagayList.add("Sapang Balen");
        baranagayList.add("Sapang Biabas");
        baranagayList.add("Tabun");

        return  baranagayList;
    }

    private ObservableList<String> getMonths(){
        ObservableList<String> monthList = FXCollections.observableArrayList();

        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");

        return  monthList;
    }

    private ObservableList<String> getYears(){
        ObservableList<String> yearList = FXCollections.observableArrayList(ctr.getYears());

        return  yearList;
    }


}
