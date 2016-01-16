package View.AdminGUI.Report.Report.Layouts;

import AdminModel.BarangayData;
import AdminModel.OverViewReportObject;
import Remote.Method.FamilyModel.FamilyPoverty;
import View.AdminGUI.Report.Enums.ReportType;
import View.AdminGUI.Report.interfaces.ReportMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created by Didoy on 1/12/2016.
 */
public class ViewReportColumn extends VBox implements ReportMethods {

    public ViewReportColumn() {
        setAlignment(Pos.CENTER);
        setSpacing(10);

    }

    public void setData(OverViewReportObject reportObject){
        OverViewReportObject reportObject1 = (OverViewReportObject) reportObject;
        ArrayList factorList =  reportObject1.getFactorList();
        ArrayList<BarangayData> povertyList =reportObject1.getPovertyList();


        int x = 0;

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        while (x <= povertyList.size() - 1){

            System.out.println(povertyList.size());

            BarangayData bd = (BarangayData) povertyList.get(x);
            pieData.add(new PieChart.Data(bd.getBarangayName(),bd.getUnresolvePopulation()));
            x++;
        }

        PieChart pc = new PieChart(pieData);
        pc.setTitle("Year Reports");
        getChildren().addAll(pc);

    }

    @Override
    public void GenerateOverViewReport(int Year) {

    }

    @Override
    public void GenerateComparingReport(int Year, String barangay, ReportType type) {

    }

    @Override
    public void GenerateSpecificReport(int Year, int month, String barangay, ReportType type) {

    }
}
