package View.AdminGUI.Home;

import BarangayData.BarangayData;
import PriorityModels.PriorityLevel;
import View.AdminGUI.Home.Cells.*;
import View.AdminGUI.Home.Listeners.*;
import View.AdminGUI.Report.Charts.ChartFactory;
import View.AdminGUI.Work.PriorityProgressBarCell;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import sun.dc.pr.PRError;

import java.util.ArrayList;

/**
 * Created by reiner on 2/29/2016.
 */
public class HomePane extends VBox{

    private TableView<BarangayData> tableView  = new TableView<BarangayData>();
    private ScrollPane scrollPane = new ScrollPane();
    private ChartFactory chartFactory = new ChartFactory();

    TableColumn barangayCol = new TableColumn("Barangay");
    TableColumn priorityLevel = new TableColumn("Priority Level");
    TableColumn totalPopu = new TableColumn("Population");
    TableColumn priorityType = new TableColumn();
    TableColumn histories = new TableColumn("Help Given");
    TableColumn unresolvePopulation = new TableColumn("Un-resolve Population");
    TableColumn resolpopulation = new TableColumn("Resolve Population");
    TableColumn view = new TableColumn("View");
    private TableColumn.SortType sortType = null;

    private PieChart pieChart =  chartFactory.createPieChart();
    String year;

    private HomePaneListener homePaneListener;

    public HomePane() {

        setPadding(new Insets(20));
        setSpacing(10);
        setAlignment(Pos.CENTER);

        priorityLevel.setSortable(true);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, pieChart);

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);


    }

    public void setData(ArrayList<BarangayData> list, String year){
        this.year = year;
        ObservableList data = FXCollections.observableArrayList(list);

        priorityLevel.setSortType(TableColumn.SortType.ASCENDING);

        tableView.getItems().clear();
        tableView.setItems(data);

        createColumns();
        tableView.getSortOrder().add(priorityLevel);

        setPieChartData(data);
    }

    private void createColumns(){

        priorityType.setVisible(false);
        tableView.getColumns().clear();

        ViewPopleCellListener viewPopleCellListener = new ViewPopleCellListener() {
            @Override
            public void viewAllPeople(String barangayName, String date) {
                    homePaneListener.viewAllPeople(barangayName, date);
            }
        };

        HistoryCellListener historyCellListener = new HistoryCellListener() {
            @Override
            public void showFamilyHistories(String barangayName, String date) {
                homePaneListener.showFamilyHistories(barangayName, date);
            }
        };

        ViewCellListener viewCellListener = new ViewCellListener() {
            @Override
            public void viewData(String barangayName, String type) {
                homePaneListener.viewPeople(barangayName, year, type);
            }
        };

        StatusCellListener statusCellListener = new StatusCellListener() {
            @Override
            public void viewDataByStatus(String barangayName, String date, String status) {
                homePaneListener.viewDataByStatus(barangayName, date, status);
            }
        };


        //====================== CELL RENDERER==========================///

        totalPopu.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override




            public TableCell call(TableColumn param) {
                return new PopulationCell();
            }
        });

        histories.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new HistoryCell( historyCellListener);
            }
        });


        priorityLevel.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new PriorityProgressBarCell();
            }
        });

        view.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new ViewCell(viewPopleCellListener);
            }
        });

        unresolvePopulation.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new UnresolvePopulationCell(viewCellListener);
            }
        });

        resolpopulation.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new ResolvePopulationCell(statusCellListener);
            }
        });

        // =================== CELL VALUE FACTORY ================================//

        totalPopu.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, PriorityLevel>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, PriorityLevel> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getPopulation());
            }
        });


        unresolvePopulation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, PriorityLevel>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, PriorityLevel> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getUnresolvePopulation());
            }
        });

        histories.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, PriorityLevel>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, PriorityLevel> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getHistories());
            }
        });

        resolpopulation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BarangayData, PriorityLevel>, ObservableValue<BarangayData>>() {
            @Override
            public ObservableValue<BarangayData> call(TableColumn.CellDataFeatures<BarangayData, PriorityLevel> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getResolvePopulation());
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

        tableView.getColumns().addAll(barangayCol, priorityLevel, totalPopu, priorityType, unresolvePopulation, resolpopulation,histories, view);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }


    private void setPieChartData (ObservableList<BarangayData> barangayDataList){

        int x = 0;

        ObservableList<PieChart.Data>  pieData = FXCollections.observableArrayList();

        while (x <= barangayDataList.size() - 1){
            BarangayData bd =  barangayDataList.get(x);
            if (bd.getUnresolvePopulation() >= 1){
                pieData.add(new PieChart.Data(bd.getBarangayName(),bd.getUnresolvePopulation()));
            }
            x++;
        }

        pieChart.setData(pieData);

    }

    public void addHomePaneListeners(HomePaneListener listener){
        homePaneListener = listener;
    }
}
