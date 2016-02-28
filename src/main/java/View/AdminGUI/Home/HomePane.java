package View.AdminGUI.Home;

import AdminModel.Report.Children.Model.ResponsePovertyRate;
import BarangayData.BarangayData;
import PriorityModels.PriorityLevel;
import Remote.Method.FamilyModel.Family;
import View.AdminGUI.Report.Charts.ChartFactory;
import View.AdminGUI.Work.PriorityProgressBarCell;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by reiner on 2/29/2016.
 */
public class HomePane extends VBox{

    private TableView<BarangayData> tableView  = new TableView<BarangayData>();
    private ScrollPane scrollPane = new ScrollPane();
    private ChartFactory chartFactory = new ChartFactory();

    private PieChart pieChart =  chartFactory.createPieChart();

    public HomePane() {
        createTable();

        setSpacing(10);
        setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, pieChart);

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);

    }

    private void createTable(){

        createColumns();
    }

    public void setData(ArrayList<BarangayData> list){
        ObservableList data = FXCollections.observableArrayList(list);

        tableView.getItems().clear();
        tableView.setItems(data);

        setPieChartData(data);
    }

    private void createColumns(){

        TableColumn barangayCol = new TableColumn("Barangay Name");
        TableColumn priorityLevel = new TableColumn("Priority Level");
        TableColumn priorityType = new TableColumn();
        TableColumn unresolvePopulation = new TableColumn("Un-resolve Population");
        TableColumn resolpopulation = new TableColumn("Resolve Population");
        TableColumn view = new TableColumn("View");

        priorityType.setVisible(false);

        priorityLevel.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new PriorityProgressBarCell();
            }
        });

        view.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new ViewCell();
            }
        });

        priorityLevel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, PriorityLevel>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, PriorityLevel> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getPriorityLevel());
            }
        });

        barangayCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, String>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getBarangayName());
            }
        });

        priorityType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, String>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getPriorityType());
            }
        });

        unresolvePopulation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, String>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getUnresolvePopulation());
            }
        });
        resolpopulation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, String>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getResolvePopulation());
            }
        });

        tableView.getColumns().addAll(barangayCol, priorityLevel, priorityType, unresolvePopulation, resolpopulation, view);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private void setPieChartData (ObservableList<BarangayData> barangayDataList){

        int x = 0;

        ObservableList<PieChart.Data>  pieData = FXCollections.observableArrayList();

        while (x <= barangayDataList.size() - 1){
            BarangayData bd =  barangayDataList.get(x);
            pieData.add(new PieChart.Data(bd.getBarangayName(),bd.getUnresolvePopulation()));
            x++;
        }

        pieChart.setData(pieData);

    }
}
