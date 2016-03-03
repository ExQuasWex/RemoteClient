package View.AdminGUI.Report.Report.Layouts.Listener;

import AdminModel.Enum.ReportCategoryMethod;
import AdminModel.Params;

/**
 * Created by reiner on 3/2/2016.
 */
public interface MainReportPaneListener {
    public void getReportChartParameters(Params params, ReportCategoryMethod method);
}
