package View.AdminGUI.Report.interfaces;

import AdminModel.Params;
import AdminModel.Report.Parent.ResponseCompareOverview;
import AdminModel.Report.Parent.ResponseOverviewReport;
import AdminModel.Report.Parent.ResponseSpecific;
import AdminModel.Report.Parent.ResponseSpecificOverView;

import java.util.ArrayList;

/**
 * Created by Didoy on 1/25/2016.
 */
public interface Reports {

    public void showOverViewReport(ResponseOverviewReport reportObject, Params params);
    public void showCompareOverviewReport(ResponseCompareOverview responseCompareOverview, Params params);
    public void showCompareSpecificReport(ResponseCompareOverview responseCompareOverview, Params params);
    public void showSpecificOverViewReport(ResponseSpecificOverView responseSpecificOverView, Params params);
    public void showSpecificReport(ResponseSpecific responseSpecific, Params params);

}
