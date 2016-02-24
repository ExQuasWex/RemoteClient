package View.AdminGUI.Report.Charts;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

/**
 * Created by Didoy on 2/24/2016.
 */
public class ChartFactory {

    public BarChart createBarChart(String YLabel, String Xlabel,String Title ){

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        yAxis.setLabel(YLabel);
        xAxis.setLabel(Xlabel);

        BarChart<String,Number> barChart =
                new BarChart<String,Number>(xAxis,yAxis);

        barChart.setTitle(Title);
        barChart.setBarGap(10);
        barChart.setAnimated(true);



        return barChart;
    }
    public LineChart createLineChart(String xTitle, String yLabel, String Title){
        final CategoryAxis lineXAxis = new CategoryAxis();
        final NumberAxis lineYAxis = new NumberAxis();

        lineXAxis.setLabel(xTitle);
        lineYAxis.setLabel(yLabel);

        LineChart lineChart = new LineChart(lineXAxis, lineYAxis);

        return lineChart;
    }
}
