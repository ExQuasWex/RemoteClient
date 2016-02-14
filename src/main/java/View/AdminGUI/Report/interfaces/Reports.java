package View.AdminGUI.Report.interfaces;

import AdminModel.Params;
import AdminModel.Report.Children.Model.ResponseCompareOverview;
import AdminModel.Report.Parent.Model.ResponseOverviewReport;

import java.util.ArrayList;

/**
 * Created by Didoy on 1/25/2016.
 */
public interface Reports {

    public void showOverViewReport(ResponseOverviewReport reportObject);
    public void showCompareOverviewReport(ResponseCompareOverview responseCompareOverview, Params params);
    public void showCompareSpecificReport();
    public void showSpecificOverViewReport();
    public void showSpecificReport();

}
