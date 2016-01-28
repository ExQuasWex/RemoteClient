package View.AdminGUI.Report.Report.Layouts;

import AdminModel.Params;
import AdminModel.Report.Children.Model.ResponseCompareOverview;
import AdminModel.Report.Children.Model.ResponsePovertyFactor;
import AdminModel.Report.Children.Model.ResponsePovertyRate;
import AdminModel.Report.Parent.Model.ResponseOverviewReport;
import View.AdminGUI.Report.Enums.ReportType;
import View.AdminGUI.Report.interfaces.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.layout.VBox;
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by Didoy on 1/12/2016.
 */
public class ViewReportColumn extends VBox implements Reports {

    public ViewReportColumn() {
        setAlignment(Pos.CENTER);
        setSpacing(10);

    }

    @Override
    public void showOverViewReport( ResponseOverviewReport reportObject) {
        //Line Chart Configuration
        ArrayList factorList =  reportObject.getFactorList();
        ArrayList<ResponsePovertyRate> povertyList = reportObject.getPovertyList();

        ScrollPane lineCharScrlPane = new ScrollPane();
        lineCharScrlPane.setFitToWidth(true);
        int x = 0;

        final CategoryAxis lineXAxis = new CategoryAxis();
        final NumberAxis lineYAxis = new NumberAxis();

        XYChart.Series lineseries = new XYChart.Series();
        lineseries.setName("Poverty Population");

        while (x <= povertyList.size() - 1){

            ResponsePovertyRate povertyRate =  povertyList.get(x);
            lineseries.getData().add(new XYChart.Data(povertyRate.getBarangayName(), povertyRate.getUnresolvePopulation()));
            x++;
        }

        LineChart lineChart = new LineChart(lineXAxis, lineYAxis);
        lineChart.getData().add(lineseries);
        lineChart.setTitle(Utility.getCurrentYear() + " Poverty Rate");

        lineCharScrlPane.setContent(lineChart);

        //-------------------- Barchart configuration -------------------//
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);

        bc.setTitle("Poverty Factor of the year 2016");
        xAxis.setLabel("Factors");
        yAxis.setLabel("Value");


        x = 0;

        while (x <= factorList.size() -1 ){
            XYChart.Series series1 = new XYChart.Series<String, Integer>();
            ResponsePovertyFactor factors = (ResponsePovertyFactor)  factorList.get(x);
            XYChart.Series series2 = new XYChart.Series<String, Integer>();
            XYChart.Series series3 = new XYChart.Series<String, Integer>();


            // add reflectionj to get all data fields in factor object

            series1.getData().add(new XYChart.Data<String, Integer>("Unemployed", factors.getUnemployed()));
            series2.getData().add(new XYChart.Data<String, Integer>("Below City Threshold", factors.getBelowMinimun()));
            series3.getData().add(new XYChart.Data<String, Integer>("No Extra Income", factors.getNoOtherIncome()));
            series1.getData().add(new XYChart.Data<String, Integer>("Under Employed", factors.getUnderemployed()));
            series2.getData().add(new XYChart.Data<String, Integer>("Illegal Settlers", factors.getNoShelter()));

            bc.getData().addAll(series1, series2, series3);
            x++;
        }


        VBox vBox = new VBox();
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lineCharScrlPane,bc);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);

        getChildren().addAll(scrollPane);
    }

    @Override
    public void showCompareOverviewReport(ResponseCompareOverview responseCompareOverview, Params params) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final BarChart<String,Number> barChart =
                new BarChart<String,Number>(xAxis,yAxis);

        barChart.setTitle("Poverty Rate " + params.getYear() +" " + params.getMaxYear());

        String initialyear = String.valueOf(params.getYear());
        String maxYear = String.valueOf(params.getMaxYear());


        XYChart.Series series1 = new XYChart.Series();
        series1.setName(initialyear);

        XYChart.Series series2 = new XYChart.Series();
        series2.setName(maxYear);

        series1.getData().add(new XYChart.Data(initialyear, responseCompareOverview.getUnresolvePopulationYearOne()));
        series2.getData().add(new XYChart.Data(maxYear,  responseCompareOverview.getUnresolvePopulationYeartwo()));

        barChart.getData().addAll(series1, series2);
        barChart.setBarGap(10);
        barChart.setAnimated(true);

        ScrollPane scrollPane = new ScrollPane(barChart);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);

    }


    @Override
    public void showCompareSpecificReport() {

    }

    @Override
    public void showSpecificOverViewReport() {

    }

    @Override
    public void showSpecificReport() {

    }
}
