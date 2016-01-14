package View.AdminGUI.Report.interfaces;


import View.AdminGUI.Report.Enums.ReportType;

/**
 * Created by Didoy on 1/12/2016.
 */
public interface ReportMethods {

    public void GenerateOverViewReport(int Year);
    public void GenerateComparingReport(int Year, String barangay, ReportType type);
    public void GenerateSpecificReport(int Year, int month, String barangay, ReportType type);


}
