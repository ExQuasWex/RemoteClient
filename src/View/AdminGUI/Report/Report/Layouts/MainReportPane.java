package View.AdminGUI.Report.Report.Layouts;

import AdminModel.OverViewReportObject;
import AdminModel.Params;
import Controller.Controller;
import View.AdminGUI.Report.Enums.ReportCategoryMethod;
import View.AdminGUI.Report.SubHeader.*;
import View.AdminGUI.Report.interfaces.ReportButtonListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.util.ArrayList;

/**
 * Created by Didoy on 1/12/2016.
 */
public class MainReportPane extends VBox {

    private static MainReportPane mainReportPane = null;
    // header components
    private HBox header ;
    private ComboBox category;
    private ComboBox Type;
    private  ToggleButton columnView;
    private  ToggleButton gridView;

    // sub header
    private ReportOverViewPane overViewPane;
    private ReportComparePane reportComparePane;
    private ReportCompareSpecificPane reportCompareSpecificPane;
    private ReportSpecificOverviewPane reportSpecificOverviewPane;
    private ReportSpecificPane reportSpecificPane;


    private Controller controller = Controller.getInstance();

    private MainReportPane(){
        overViewPane = new ReportOverViewPane();
        reportComparePane = new ReportComparePane();
        reportCompareSpecificPane = new ReportCompareSpecificPane();
        reportSpecificOverviewPane = new ReportSpecificOverviewPane();
        reportSpecificPane = new ReportSpecificPane();

        createHeader();

                overViewPane.addReportButtonListener(new ReportButtonListener() {
                    @Override
                    public void generateReport(Params params) {
                        ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                        String type = (String) Type.getSelectionModel().getSelectedItem();
                        generateReports(method, type, params);

                    }
                });
                reportComparePane.addReportButtonListener(new ReportButtonListener() {
                    @Override
                    public void generateReport(Params params) {
                        ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                        String type = (String) Type.getSelectionModel().getSelectedItem();
                        generateReports(method, type, params);

                    }
                });

                reportCompareSpecificPane.addReportButtonListener(new ReportButtonListener() {
                    @Override
                    public void generateReport(Params params) {
                        ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                        String type = (String) Type.getSelectionModel().getSelectedItem();
                        generateReports(method, type, params);

                    }
                });
                 reportSpecificOverviewPane.addReportButtonListener(new ReportButtonListener() {
                    @Override
                    public void generateReport(Params params) {
                        ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                        String type = (String) Type.getSelectionModel().getSelectedItem();
                        generateReports(method, type, params);

                    }
                });
                reportSpecificPane.addReportButtonListener(new ReportButtonListener() {
                    @Override
                    public void generateReport(Params params) {
                        ReportCategoryMethod method = (ReportCategoryMethod) category.getSelectionModel().getSelectedItem();
                        String type = (String) Type.getSelectionModel().getSelectedItem();
                        generateReports(method, type, params);

                    }
                });



        getChildren().addAll(createHeader(), overViewPane);

    }


    private HBox createHeader(){
        header = new HBox();
        header.setPrefHeight(80);
        header.setAlignment(Pos.CENTER);
        header.setSpacing(5);

        ToggleGroup toggleGroup = new ToggleGroup();

        Label categoryLabel = new Label("View Report by:");

         category = new ComboBox();
         Type = new ComboBox();

        // Button viewReport = new Button("View Report");

        columnView = new ToggleButton("Column ");
        gridView = new ToggleButton("Grid");
        columnView.setToggleGroup(toggleGroup);
        gridView.setToggleGroup(toggleGroup);

        //category.setPrefWidth(50);
        //viewReport.setPrefWidth(50);
        columnView.setPrefWidth(50);
        gridView.setPrefWidth(50);

        columnView.setSelected(true);

        // set categories
        ObservableList<ReportCategoryMethod> categories = FXCollections.observableArrayList(ReportCategoryMethod.values());

        category.setItems(getCategories());
        Type.setItems(getTypes());

        category.getSelectionModel().select(0);
        Type.getSelectionModel().select(0);

                category.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        generateSubheader((ReportCategoryMethod) category.getSelectionModel().getSelectedItem());
                    }
                });

        header.getChildren().addAll(categoryLabel, category, Type, columnView, gridView);

        return header;
    }

    private ObservableList getTypes(){
        ObservableList<String> typelist = FXCollections.observableArrayList();
        typelist.add("Poverty Factors");
        typelist.add("Poverty Rate");

        return typelist;
    }

    private void generateReports(ReportCategoryMethod method,  String type,  Params param){
        OverViewReportObject data = null;

        if (method == ReportCategoryMethod.OVERVIEW){
            data = controller.getOverViewData(param, type);
            System.out.println(data.getFactorList().get(0));
            System.out.println("OVERVIEW");
        }else if (method == ReportCategoryMethod.COMPARE_OVERVIEW){
           // data = controller.getCompareOverViewData(param, type);
            System.out.println("COMPARE_OVERVIEW");
        }else if (method == ReportCategoryMethod.COMPARE_SPECIFIC){
          //  data = controller.getCompareSpecificData(param, type);
            System.out.println("COMPARE_SPECIFIC");
        }else if (method == ReportCategoryMethod.SPECIFIC_OVERVIEW){
           // data = controller.getSpecificOverViewData(param, type);
            System.out.println("SPECIFIC_OVERVIEW");
        }else if (method == ReportCategoryMethod.SPECIFIC){
           // data = controller.getSpecific(param, type);
            System.out.println("SPECIFIC");

        }

        setDataReport(data);
    }

    private void setDataReport(Object data){
        OverViewReportObject reportObject = (OverViewReportObject) data;
        if (columnView.isSelected()){
            ViewReportColumn columnview = new ViewReportColumn();
            columnview.setData(reportObject);

            getChildren().add(columnview);
        }else {
            ViewReportGrid columnview = new ViewReportGrid();
            //columnview.setData(data);
        }
    }

    private void generateSubheader(ReportCategoryMethod cat){
        if (cat.equals(ReportCategoryMethod.OVERVIEW)){
                      getChildren().remove(1);
                      getChildren().add( overViewPane);
        }else if (cat.equals(ReportCategoryMethod.COMPARE_OVERVIEW)){
                      getChildren().remove(1);
                      getChildren().add(reportComparePane);
        }else if (cat.equals(ReportCategoryMethod.COMPARE_SPECIFIC)){
                      getChildren().remove(1);
                      getChildren().add(reportCompareSpecificPane);
        }else if (cat.equals(ReportCategoryMethod.SPECIFIC_OVERVIEW)){
                      getChildren().remove(1);
                      getChildren().add(reportSpecificOverviewPane);
        }else if (cat.equals(ReportCategoryMethod.SPECIFIC)){
                      getChildren().remove(1);
                      getChildren().add(reportSpecificPane);
        }else {

        }
    }

    private ObservableList getCategories(){
        ObservableList<ReportCategoryMethod> categoryList = FXCollections.observableArrayList(ReportCategoryMethod.values());
//        categoryList.add("Overview");
//        categoryList.add("Compare OverView");
//        categoryList.add("Compare Specific");
//        categoryList.add("Specific Overview");
//        categoryList.add("Specific");

        return  categoryList;
    }

    public static MainReportPane getInstance(){

            if (mainReportPane == null){
                mainReportPane = new MainReportPane();
                return mainReportPane;
            }else {
                return mainReportPane;
            }
    }

}
