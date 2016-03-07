package View.AdminGUI.Report.Report.Layouts;

import AdminModel.Enum.FactorCategoryParameter;
import AdminModel.Params;
import AdminModel.Report.Parent.ResponseCompareOverview;
import AdminModel.Report.Parent.ResponseOverviewReport;
import AdminModel.Report.Parent.ResponseSpecific;
import AdminModel.Report.Parent.ResponseSpecificOverView;
import Controller.Controller;
import AdminModel.Enum.ReportCategoryMethod;
import ToolKit.Printer;
import View.AdminGUI.Report.Report.Layouts.Listener.MainReportPaneListener;
import View.AdminGUI.Report.Report.Layouts.Listener.ViewReportLayoutListener;
import View.AdminGUI.Report.SubHeader.*;
import View.AdminGUI.Report.interfaces.ReportButtonListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utility.Logger;
import utility.Utility;

/**
 * Created by Didoy on 1/12/2016.
 */
public class MainReportPane extends VBox {


    // header components
    private HBox header ;
    private ComboBox category;
    private ComboBox Type;
    private  ToggleButton columnView;
    private  ToggleButton gridView;

    // sub headers which shows the paremeters for report
    private ReportOverViewPane overViewPane;
    private ReportComparePane reportComparePane;
    private ReportCompareSpecificPane reportCompareSpecificPane;
    private ReportSpecificOverviewPane reportSpecificOverviewPane;
    private ReportSpecificPane reportSpecificPane;

    private VBox InitialPane;
    private ViewReportColumn reportColumnPane = new ViewReportColumn();
    private Controller controller = Controller.getInstance();
    private Button print = new Button("Print");

    private MainReportPaneListener mainReportPaneListener;

    public MainReportPane(){
        overViewPane = new ReportOverViewPane();
        reportComparePane = new ReportComparePane();
        reportSpecificOverviewPane = new ReportSpecificOverviewPane();
        reportCompareSpecificPane = new ReportCompareSpecificPane();
        reportSpecificPane = new ReportSpecificPane();

        InitialPane = getInitialPane();

        addPaneListeners();

        getStylesheets().add("/CSS/AdminReportCSS.css");
        setSpacing(15);
        setVgrow(InitialPane, Priority.ALWAYS);
        getChildren().addAll(createHeader(), overViewPane, InitialPane);

        print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Printer.Print(columnView, null);
            }
        });
    }

    private void addPaneListeners(){

        overViewPane.addReportButtonListener(new ReportButtonListener() {
            @Override
            public void generateReport(Params params) {
                ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                String type = (String) Type.getSelectionModel().getSelectedItem();
                initializeReport(method, type, params);

            }
        });
        reportComparePane.addReportButtonListener(new ReportButtonListener() {
            @Override
            public void generateReport(Params params) {
                ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                String type = (String) Type.getSelectionModel().getSelectedItem();
                initializeReport(method, type, params);

            }
        });

        reportCompareSpecificPane.addReportButtonListener(new ReportButtonListener() {
            @Override
            public void generateReport(Params params) {
                ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                String type = (String) Type.getSelectionModel().getSelectedItem();
                initializeReport(method, type, params);

            }
        });
        reportSpecificOverviewPane.addReportButtonListener(new ReportButtonListener() {
            @Override
            public void generateReport(Params params) {
                ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                String type = (String) Type.getSelectionModel().getSelectedItem();

                initializeReport(method, type, params);

            }
        });
        reportSpecificPane.addReportButtonListener(new ReportButtonListener() {
            @Override
            public void generateReport(Params params) {
                ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                String type = (String) Type.getSelectionModel().getSelectedItem();
                initializeReport(method, type, params);

            }
        });

        reportColumnPane.addViewReportLayoutListener(new ViewReportLayoutListener() {
            @Override
            public void getReportChartParameters(ReportCategoryMethod reportCategoryMethod, String xValue, String date, String barangayName) {

                Params params = getParameters(reportCategoryMethod, xValue, date, barangayName);

                mainReportPaneListener.getReportChartParameters(params, reportCategoryMethod);
            }
        });

    }


    private VBox getInitialPane(){
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        Text contentText = new Text("Hi You can Start Generating Report now :)");
        contentText.setFont(Font.font(30));
        contentText.setSmooth(true);

        vBox.getChildren().add(contentText);

        return vBox;
    }

    private HBox createHeader(){
        header = new HBox();
        header.setPrefHeight(80);
        header.setAlignment(Pos.CENTER);
        header.setSpacing(5);
        header.setPadding(new Insets(10, 0, 10, 0));

        ToggleGroup toggleGroup = new ToggleGroup();

        Label categoryLabel = new Label("View Report by:");

         category = new ComboBox();
         Type = new ComboBox();
         Type.setDisable(true);

        // Button viewReport = new Button("View Report");

        columnView = new ToggleButton("Column ");
        gridView = new ToggleButton("Grid");
        columnView.setToggleGroup(toggleGroup);
        gridView.setToggleGroup(toggleGroup);

        columnView.setPrefWidth(50);
        gridView.setPrefWidth(50);

        columnView.setSelected(true);

        // set categories

        category.setItems(getCategories());
        Type.setItems(getTypes());

        category.getSelectionModel().select(0);
        Type.getSelectionModel().select(0);

        addCategoryListener();

        header.getChildren().addAll(categoryLabel, category, Type, print,  columnView, gridView);

        return header;
    }

    private void addCategoryListener(){

        category.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                generateSubheader((ReportCategoryMethod) category.getSelectionModel().getSelectedItem());
            }
        });

        category.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if (newValue == ReportCategoryMethod.COMPARE_SPECIFIC || newValue == ReportCategoryMethod.SPECIFIC ){
                    Type.setDisable(false);
                }else {
                    Type.setDisable(true);
                }

            }
        });

    }

    private ObservableList getTypes(){
        ObservableList<String> typelist = FXCollections.observableArrayList();
        typelist.add("Poverty Factors");
        typelist.add("Poverty Rate");

        return typelist;
    }

    private void initializeReport(ReportCategoryMethod method, String type, Params param){

        ResponseOverviewReport overviewReport = null;

        if (method == ReportCategoryMethod.OVERVIEW){

            overviewReport = controller.getOverViewData(param, type);
            reportColumnPane.showOverViewReport( overviewReport, param);
            getChildren().remove(2);
            getChildren().add(reportColumnPane);

        }else if (method == ReportCategoryMethod.COMPARE_OVERVIEW){
            ResponseCompareOverview compareOverview  = controller.getCompareOverViewData(param, type);

            reportColumnPane.showCompareOverviewReport(compareOverview, param);
            getChildren().remove(2);
            getChildren().add(reportColumnPane);

        }else if (method == ReportCategoryMethod.COMPARE_SPECIFIC){

            ResponseCompareOverview compareOverview  = controller.getCompareSpecificData(param, type);

            reportColumnPane.showCompareSpecificReport(compareOverview, param);
            getChildren().remove(2);
            getChildren().add(reportColumnPane);

        }else if (method == ReportCategoryMethod.SPECIFIC_OVERVIEW){
            ResponseSpecificOverView responseSpecificOverView = controller.getSpecificOverViewData(param, type);

            reportColumnPane.showSpecificOverViewReport(responseSpecificOverView, param);
            getChildren().remove(2);
            getChildren().add(reportColumnPane);

        }else if (method == ReportCategoryMethod.SPECIFIC){
            ResponseSpecific responseSpecific = controller.getSpecific(param, type);

            reportColumnPane.showSpecificReport(responseSpecific, param);

            getChildren().remove(2);
            getChildren().add(reportColumnPane);

            System.out.println("SPECIFIC");

        }

    }

    private void generateSubheader(ReportCategoryMethod cat){
        if (cat.equals(ReportCategoryMethod.OVERVIEW)){
                      getChildren().remove(1);
                      getChildren().add(1, overViewPane);
        }else if (cat.equals(ReportCategoryMethod.COMPARE_OVERVIEW)){
                      getChildren().remove(1);
                      getChildren().add(1, reportComparePane);
        }else if (cat.equals(ReportCategoryMethod.COMPARE_SPECIFIC)){
                      getChildren().remove(1);
                      getChildren().add(1, reportCompareSpecificPane);
        }else if (cat.equals(ReportCategoryMethod.SPECIFIC_OVERVIEW)){
                      getChildren().remove(1);
                      getChildren().add(1, reportSpecificOverviewPane);
        }else if (cat.equals(ReportCategoryMethod.SPECIFIC)){
                      getChildren().remove(1);
                      getChildren().add(1, reportSpecificPane);
        }else {

        }
    }

    private ObservableList getCategories(){
        ObservableList<ReportCategoryMethod> categoryList = FXCollections.observableArrayList(ReportCategoryMethod.values());
        return  categoryList;
    }

    public void addMainReportPaneListener(MainReportPaneListener mainReportPaneListener){
        this.mainReportPaneListener = mainReportPaneListener;
    }

    private Params getParameters (ReportCategoryMethod reportCategoryMethod,String xValue, String date, String barangayName){
        boolean isFactortType = false;
        Params params = null;

        for(FactorCategoryParameter c : FactorCategoryParameter.values()){
            if (c.toString().equals(xValue)){
                isFactortType = true;
            }
        }

        System.out.println(isFactortType);
        Logger.Log(" Report Type " + reportCategoryMethod.toString() + " xValue: " +xValue+ " date " + date + " Barangay: " + barangayName);

              if (reportCategoryMethod.equals(ReportCategoryMethod.OVERVIEW)){
                  barangayName = xValue;
                  if (isFactortType){

                         params = new Params(date, "", xValue );
                     }else {
                         barangayName = xValue;
                         params = new Params(date, barangayName, "" );
                     }
              }
              else if (reportCategoryMethod.equals(ReportCategoryMethod.COMPARE_OVERVIEW)){
                     if (isFactortType){
                         params = new Params(date, "", xValue );
                     }else {
                         barangayName = xValue;
                         params = new Params(date, barangayName, "" );
                     }
              }
              else if (reportCategoryMethod.equals(ReportCategoryMethod.COMPARE_SPECIFIC)){

                     if (isFactortType){
                         params = new Params(date, barangayName, xValue );
                     }else {
                         params = new Params(date,  barangayName );
                     }

              }
              else if (reportCategoryMethod.equals(ReportCategoryMethod.SPECIFIC_OVERVIEW)){
                    if (isFactortType){
                        params = new Params(date, barangayName, xValue );
                    }else {
                        params = new Params(date, barangayName, xValue );
                    }
              }
              else if (reportCategoryMethod.equals(ReportCategoryMethod.SPECIFIC)){
                    if (isFactortType){
                        params = new Params(date, barangayName, xValue );
                    }else {
                        params = new Params(date, barangayName, xValue );
                    }
              }
              else {
              }
        return params;
    }

}
