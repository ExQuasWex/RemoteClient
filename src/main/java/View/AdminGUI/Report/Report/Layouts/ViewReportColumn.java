package View.AdminGUI.Report.Report.Layouts;

import AdminModel.Params;
import AdminModel.Report.Children.Model.ResponsePovertyFactor;
import AdminModel.Report.Children.Model.ResponsePovertyRate;
import AdminModel.Report.Parent.ResponseCompareOverview;
import AdminModel.Report.Parent.ResponseOverviewReport;
import AdminModel.Report.Parent.ResponseSpecific;
import AdminModel.Report.Parent.ResponseSpecificOverView;
import View.AdminGUI.Report.Charts.ChartFactory;
import View.AdminGUI.Report.Charts.SeriesFactory;
import View.AdminGUI.Report.Charts.SeriesListener;
import AdminModel.Enum.ReportCategoryMethod;
import View.AdminGUI.Report.Report.Layouts.Listener.ViewReportLayoutListener;
import View.AdminGUI.Report.interfaces.Reports;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by Didoy on 1/12/2016.
 */
public class ViewReportColumn extends VBox implements Reports {

    private ScrollPane scrollPane = new ScrollPane();
    private VBox vBox = new VBox();
    private String currentYear =  Utility.getCurrentYear();

    private ChartFactory chartFactory = new ChartFactory();
    private SeriesFactory seriesFactory = new SeriesFactory();
    private ViewReportLayoutListener viewReportLayoutListener;
    private ReportCategoryMethod reportCategoryMethod;

    public ViewReportColumn() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20, 30 ,10 , 0));

        vBox.setAlignment(Pos.CENTER);

        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(0, 30, 0, 30));
        getChildren().addAll(scrollPane);

        seriesFactory.addSeriesFactoryListener(new SeriesListener() {
            @Override
            public void provideData(String xDataValue, String date, String barangay) {
                viewReportLayoutListener.getReportChartParameters( reportCategoryMethod, xDataValue, date, barangay);
            }
        });

    }

    @Override
    public void showOverViewReport(  ResponseOverviewReport reportObject, Params params) {
        reportCategoryMethod = ReportCategoryMethod.OVERVIEW;
        String year = Utility.getCurrentYear();

        //Line Chart Configuration
        ArrayList factorList =  reportObject.getFactorList();
        ArrayList<ResponsePovertyRate> povertyList = reportObject.getPovertyList();

        ScrollPane lineScrollPane = new ScrollPane();
        lineScrollPane.setFitToWidth(true);

        XYChart.Series lineseries = seriesFactory.createPovertyPopulationSeries("Poverty Population", povertyList);
        lineseries.setName("Poverty Population");

        LineChart lineChart = chartFactory.createLineChart("Barangay", "Population", currentYear );
        lineChart.getData().add(lineseries);
        lineChart.setTitle(Utility.getCurrentYear() + " Poverty Rate");

        lineScrollPane.setContent(lineChart);

        seriesFactory.addHoverToSeries(lineseries, currentYear, "");

        //-------------------- Barchart configuration -------------------//
        final BarChart<String,Number> bc = chartFactory.createBarChart("Population", "Factors", "Poverty Factor of the year 2016" );
        XYChart.Series series1 = seriesFactory.createPovertyFactorSeriesByList("Poverty Factors", factorList);

        bc.getData().addAll(series1);
        chartFactory.addDiffrentColor(bc);

        chartFactory.setBarChartSizeFlexible(bc, 7);

        seriesFactory.addHoverToSeries(series1, currentYear, "");

        RemoveAllChildren();
        vBox.getChildren().addAll(lineScrollPane, bc);
        scrollPane.setContent(null);
        scrollPane.setContent(vBox);

    }

    @Override
    public void showCompareOverviewReport(ResponseCompareOverview responseCompareOverview, Params params) {
        reportCategoryMethod = ReportCategoryMethod.COMPARE_OVERVIEW;

        // ==================== Poverty Poppulation ======================//
        String yr1 = String.valueOf(params.getYear());
        String yr2 = String.valueOf(params.getMaxYear());

        BarChart barChart = chartFactory.createBarChart("Population", "Year", " Poverty Population of " + yr1 + " vs " + yr2  );

        XYChart.Series series1 = seriesFactory.createSeries(yr1, yr1, responseCompareOverview.getUnresolvePopulationYearOne());
        XYChart.Series series2 = seriesFactory.createSeries(yr2, yr2, responseCompareOverview.getUnresolvePopulationYeartwo());

        barChart.getData().addAll(series1, series2);

        seriesFactory.addHoverToSeries(series1, yr1, "");
        seriesFactory.addHoverToSeries(series2, yr2, "");

        chartFactory.addDiffrentColor(barChart);
        chartFactory.setBarChartSizeFlexible(barChart, 50);

        // ==================== Factors   ======================//
        ArrayList  povertyFactorOneList = responseCompareOverview.getPovertyFactorOneList();
        ArrayList  povertyFactorOneList2 = responseCompareOverview.getPovertyFactorTwoList();

        XYChart.Series lineseries1 = seriesFactory.createPovertyFactorSeriesByList(yr1, povertyFactorOneList );
        XYChart.Series lineseries2 = seriesFactory.createPovertyFactorSeriesByList(yr2, povertyFactorOneList2 );

        LineChart lineChart = chartFactory.createLineChart("Factors", " Population"," Poverty Factors of " + yr1 + " vs " + yr2 );
        lineChart.getData().addAll(lineseries1, lineseries2);

        seriesFactory.addHoverToSeries(lineseries1, yr1, "");
        seriesFactory.addHoverToSeries(lineseries2, yr2, "");

        RemoveAllChildren();
        vBox.getChildren().addAll(barChart, lineChart);
        scrollPane.setContent(null);
        scrollPane.setContent(vBox);

    }

    @Override
    public void showCompareSpecificReport(ResponseCompareOverview responseCompareOverview, Params params) {
        reportCategoryMethod = ReportCategoryMethod.COMPARE_SPECIFIC;


        // ==================== Poverty Barchart  ======================//
        String yr1 = String.valueOf(params.getYear());
        String yr2 = String.valueOf(params.getMaxYear());

        String barangayOne = params.getBarangay1();
        String barangayTwo = params.getBarangay2();

        BarChart barChart = chartFactory.createBarChart("Population", "Year", " Poverty Population of " + yr1 + " vs " + yr2  );

        XYChart.Series series1 = seriesFactory.createSeries(barangayOne, yr1, responseCompareOverview.getUnresolvePopulationYearOne());
        XYChart.Series series2 = seriesFactory.createSeries(barangayTwo, yr2, responseCompareOverview.getUnresolvePopulationYeartwo());

        barChart.getData().addAll(series1, series2);
        seriesFactory.addHoverToSeries(series1, yr1, barangayOne );
        seriesFactory.addHoverToSeries(series2, yr2, barangayTwo );

        chartFactory.addDiffrentColor(barChart);

        chartFactory.setBarChartSizeFlexible(barChart, 50);

        // ==================== Factors  ======================//
        ArrayList  povertyFactorOneList = responseCompareOverview.getPovertyFactorOneList();
        ArrayList  povertyFactorOneList2 = responseCompareOverview.getPovertyFactorTwoList();

        XYChart.Series lineseries1 = seriesFactory.createPovertyFactorSeriesByList(barangayOne, povertyFactorOneList);
        XYChart.Series lineseries2 = seriesFactory.createPovertyFactorSeriesByList(barangayTwo, povertyFactorOneList2);

        LineChart lineChart = chartFactory.createLineChart("Population", "Poverty Factors"," Poverty Factors of " + yr1 + " vs " + yr2 );
        lineChart.getData().addAll(lineseries1, lineseries2);

        seriesFactory.addHoverToSeries(lineseries1, yr1, barangayOne);
        seriesFactory.addHoverToSeries(lineseries2, yr2, barangayTwo);

        RemoveAllChildren();
        vBox.getChildren().addAll(barChart, lineChart);
        scrollPane.setContent(null);
        scrollPane.setContent(vBox);

    }

    @Override
    public void showSpecificOverViewReport( ResponseSpecificOverView responseSpecificOverView, Params params) {
        reportCategoryMethod = ReportCategoryMethod.SPECIFIC_OVERVIEW;
        String year = String.valueOf(params.getYear());
        String barangayName = params.getBarangay1();

        ArrayList povertyPopulation = responseSpecificOverView.getMonthLyPopulationList();
        ArrayList povertyFactorList = responseSpecificOverView.getMonthLyPovertyFactorList();

        RemoveAllChildren();

        // --- poverty populations ---- //
        LineChart lineChart = chartFactory.createLineChart("Month", "Population", "Monthly Poverty Rate of " + barangayName );
        XYChart.Series series = seriesFactory.createPovertySeriesSpecificOverView("Monthly Poverty Population", povertyPopulation );

        lineChart.getData().add(series);

        seriesFactory.addHoverToSeries(series, year, barangayName);

        vBox.getChildren().addAll(lineChart);

        // POVERTY FACTORS //
        int x = 0;
        while ( x <= povertyFactorList.size() -1 ){
            ResponsePovertyFactor povertyFactor = (ResponsePovertyFactor) povertyFactorList.get(x);
            String month  = povertyFactor.getMonth();

            BarChart barChart = chartFactory.createBarChart("Population", "Factors", month + " Poverty Factors");

            XYChart.Series factorSeries = seriesFactory.createPovertyFactorSeriesByData("Factors", povertyFactor );

            barChart.getData().add(factorSeries);
            chartFactory.setBarChartSizeFlexible(barChart, 7);

            String date = Utility.concatinateYearAndMonth(year, month);
            seriesFactory.addHoverToSeries(factorSeries, date, barangayName);

            chartFactory.addDiffrentColor(barChart);

            vBox.getChildren().addAll(barChart);

            x++;
        }

        scrollPane.setContent(null);
        scrollPane.setContent(vBox);

    }

    @Override
    public void showSpecificReport(ResponseSpecific responseSpecific, Params params) {
        reportCategoryMethod = ReportCategoryMethod.SPECIFIC;

        String date = params.getDate();

        String barangayName = params.getBarangay1();

        ResponsePovertyFactor povertyFactor = responseSpecific.getPovertyFactor();

        // === poverty population === //
        BarChart barChart = chartFactory.createBarChart("Population", "", "Poverty population  " + barangayName  +" "   );

        int population = responseSpecific.getBarangayPovertyPopulation();
        XYChart.Series  series = seriesFactory.createSeries("Poverty population", "Total poverty Population", population );

        barChart.getData().addAll(series);

        seriesFactory.addHoverToSeries(series, date, barangayName);

        chartFactory.setBarChartSizeFlexible(barChart, 50);

        chartFactory.addDiffrentColor(barChart);

        // === poverty factors === //

        LineChart lineChart = chartFactory.createLineChart("Factors", "Population", "Poverty factors of" + barangayName  +" " );
        XYChart.Series lineSeries = seriesFactory.createPovertyFactorSeriesByData("Factors", povertyFactor);

        lineChart.getData().addAll(lineSeries);

        seriesFactory.addHoverToSeries(lineSeries, date, barangayName);

        RemoveAllChildren();
        vBox.getChildren().addAll(barChart, lineChart);
        scrollPane.setContent(null);
        scrollPane.setContent(vBox);

    }

    private void RemoveAllChildren(){
        vBox.getChildren().remove(0, vBox.getChildren().size());
    }

    public void addViewReportLayoutListener(ViewReportLayoutListener viewReportLayoutListener){
        this.viewReportLayoutListener = viewReportLayoutListener;
    }

}

