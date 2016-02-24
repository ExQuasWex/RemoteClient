package View.AdminGUI.Report.Charts;

import AdminModel.Report.Children.Model.ResponsePovertyFactor;
import AdminModel.Report.Children.Model.ResponsePovertyRate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

/**
 * Created by Didoy on 2/24/2016.
 */
public class SeriesFactory {


    public XYChart.Series createSeries(String seriesName, String xValue, int yValue  ){

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);

        series.getData().add(new XYChart.Data<String, Number>(xValue, yValue));

        return series;

    }

    public XYChart.Series createPovertyFactorSeries(String seriesName, ArrayList povertyFactorList  ){

        ArrayList factorList =   povertyFactorList;

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);

        int x = 0;

        while (x <= factorList.size() -1 ){
            ResponsePovertyFactor factors = (ResponsePovertyFactor)  factorList.get(x);

            series.getData().add(new XYChart.Data<String, Integer>("Unemployed", factors.getUnemployed()));
            series.getData().add(new XYChart.Data<String, Integer>("Below City Threshold", factors.getBelowMinimun()));
            series.getData().add(new XYChart.Data<String, Integer>("No Extra Income", factors.getNoOtherIncome()));
            series.getData().add(new XYChart.Data<String, Integer>("Under Employed", factors.getUnderemployed()));
            series.getData().add(new XYChart.Data<String, Integer>("Illegal Settlers", factors.getNoShelter()));

            x++;
        }
        return series;

    }

    public XYChart.Series createPovertyPopulationSeries(String seriesName, ArrayList povertyFactorList  ){

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);

        int x = 0;

        ArrayList povertyList =   povertyFactorList;

        while (x <= povertyList.size() - 1){

            ResponsePovertyRate povertyRate = (ResponsePovertyRate) povertyList.get(x);
            series.getData().add(new XYChart.Data(povertyRate.getBarangayName(), povertyRate.getUnresolvePopulation()));
            x++;
        }

        return series;

    }

}
