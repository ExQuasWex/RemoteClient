package View.AdminGUI.Work;

import AdminModel.Params;
import Controller.Controller;
import DecisionSupport.Prioritizer;
import Remote.Method.FamilyModel.Family;
import ListModels.UiModels;
import Remote.Method.FamilyModel.FamilyHistory;
import Remote.Method.FamilyModel.FamilyInfo;
import Remote.Method.FamilyModel.FamilyPoverty;
import ToolKit.Printer;
import View.AdminGUI.Work.Listener.WorkPaneListener;
import View.ClientWindow.SearchTabWindow;
import ToolKit.MessageBox;
import clientModel.StaffInfo;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by Didoy on 2/1/2016.b
 */
public class WorkPane extends BorderPane {

    public static boolean isRevoke = false;
    private HBox toolBox = new HBox();
    private HBox HBOX = new HBox();
    private Button View = new Button("View");

    private TableView tableView = new TableView() ;

    private Button SelectAll = new Button("Select All");
    private Button Deselect  = new Button("Deselect All");
    private Button save  = new Button("Save Changes");
    private Button print  = new Button("Print");

    private ComboBox<String> barangay = new ComboBox<String>();
    private ComboBox<String> month = new ComboBox<String>();
    private ComboBox<String> year = new ComboBox<String>();

    private BooleanCell booleanCell = new BooleanCell();

    private Controller ctr = Controller.getInstance();

    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem viewMenuItem = new MenuItem("View");

    private     TableColumn check;
    private WorkPaneListener workPaneListener;

    public WorkPane() {

        generateHeader();
        generateTools();

        establishTable();

        setContextMenu();

        View.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String Month = (String) month.getSelectionModel().getSelectedItem();
                String Year = ( (String)year.getSelectionModel().getSelectedItem());
                String Barangay  = (String) barangay.getSelectionModel().getSelectedItem();

                String date = Utility.concatinateYearAndMonth(Year, Month);

                Params params = new Params(date , Barangay);
                ArrayList list = ctr.getFamilyBarangay(params,  null);

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
                 Save();
            }
        });

        print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Printer.Print( tableView, null);
            }
        });

    }

    private void setContextMenu(){
        contextMenu.getItems().add(viewMenuItem);

        viewMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             ObservableList<Family>  familyList =  tableView.getSelectionModel().getSelectedItems();

                SearchTabWindow searchTabWindow = SearchTabWindow.getInstance();

                searchTabWindow.addTab(familyList);
                searchTabWindow.show();
            }
        });

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.SECONDARY)){
                    double x  = event.getScreenX();
                    double y = event.getScreenY();
                    showContextMenu(HBOX, x, y);
                }
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
        TableColumn priorityCol = new TableColumn();
        TableColumn Action = new TableColumn();

        check.setText("Selection");
        ID.setText("ID");
        name.setText("Name");
        spouseName.setText("SpouseName");
        inputedDate.setText("Encode Date");
        surveyedDate.setText("Survey Date");
        Action.setText("Action");
        priorityCol.setText("Priority Level");

        Action.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableActionCell(tableView);
            }
        });

        Action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilypoverty().getPriorityType());
            }
        });

        priorityCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new PriorityProgressBarCell();
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

        priorityCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family, Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilypoverty().getPriorityLevel());
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

                ObservableList x = tableView.getSelectionModel().getSelectedIndices();
                for (Object index : x) {
                    System.out.println(index);
                }
            }
        });

        tableView.getColumns().addAll(ID, name, spouseName, inputedDate, surveyedDate, priorityCol, Action);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setPadding(new Insets(20));

    }

    public void showTable(ArrayList<Family> list){

        for (Object family :  list){
            Family fam = (Family) family;

            FamilyPoverty familyPoverty = fam.getFamilypoverty();
            FamilyInfo familyInfo = fam.getFamilyinfo();

            Prioritizer.addPriorityLevel(familyPoverty, familyInfo.getNumofChildren());

        }

        ObservableList<Family> ItemList = FXCollections.observableArrayList(list);

        tableView.setItems(ItemList);

        setCenter(tableView);
        workPaneListener.showWorkPane();

    }

    private void generateTools(){

        toolBox.setAlignment(Pos.CENTER);
        toolBox.setPadding(new Insets(50));
        toolBox.setSpacing(7);

        toolBox.getChildren().addAll(SelectAll, Deselect, print, save);

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

    public void addWorkPaneListener(WorkPaneListener workPaneListener){
        this.workPaneListener = workPaneListener;
    }

    private void showContextMenu(HBox root, double x, double y){
        contextMenu.show(root, x, y);
    }

    private void saveChanges(){

        ObservableList familyList =  tableView.getSelectionModel().getSelectedItems();

        int x = 0;

        while (x<= familyList.size() - 1){

            Family family = (Family) familyList.get(x);
            String action = family.getFamilypoverty().getPriorityType().toString();
            FamilyHistory familyHistory = family.getFamilyHistory();

            if (familyHistory != null) {
                if (!familyHistory.isRevoke()){
                    familyHistory.setRevoke(false);
                    familyHistory.setRevokeDescription("");
                    familyHistory.setAction(action);
                }
            }else {
                familyHistory = new FamilyHistory();
                familyHistory.setRevoke(false);
                familyHistory.setRevokeDescription("");
                familyHistory.setAction(action);
            }
            family.setFamilyHistory(familyHistory);

            family.setFamilyHistory(familyHistory);
            x++;
        }
        workPaneListener.saveChanges(familyList);
    }

    private void Save(){
        if (tableView.getItems().isEmpty()) {
            Utility.showMessageBox("There are no data available", Alert.AlertType.ERROR);

        }else {
            if (tableView.getSelectionModel().getSelectedItems().isEmpty()){
                Utility.showMessageBox("Please select row in the table", Alert.AlertType.ERROR);
            }
            else {
                boolean isConfirm = Utility.showConfirmationMessage("Are you sure you want to apply these actions?", Alert.AlertType.CONFIRMATION);

                if (isConfirm){
                    StaffInfo staffInfo = ctr.getStaffInfo();
                    String password = staffInfo.getPassword();
                    boolean x = MessageBox.confirmMessageWithPassword("Enter your password", "Password Confirmation",password);
                    if (x){
                        saveChanges();
                    }else {
                        Utility.showMessageBox("Incorrect Password", Alert.AlertType.ERROR);
                    }
                }
            }
        }

    }

}


