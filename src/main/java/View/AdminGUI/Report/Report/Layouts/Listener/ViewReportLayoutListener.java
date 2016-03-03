package View.AdminGUI.Report.Report.Layouts.Listener;

import AdminModel.Enum.ReportCategoryMethod;

/**
 * Created by reiner on 3/2/2016.
 */
public interface ViewReportLayoutListener {
    public void getReportChartParameters(ReportCategoryMethod reportCategoryMethod, String xValue, String date, String barangayName);
}
