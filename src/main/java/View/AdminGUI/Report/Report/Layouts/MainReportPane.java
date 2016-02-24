package View.AdminGUI.Report.Report.Layouts;

import AdminModel.Params;
import AdminModel.Report.Children.Model.ResponseCompareOverview;
import AdminModel.Report.Parent.Model.ResponseOverviewReport;
import Controller.Controller;
import View.AdminGUI.Report.Enums.ReportCategoryMethod;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

    // sub headers which shows the paremeters for report
    private ReportOverViewPane overViewPane;
    private ReportComparePane reportComparePane;
    private ReportCompareSpecificPane reportCompareSpecificPane;
    private ReportSpecificOverviewPane reportSpecificOverviewPane;
    private ReportSpecificPane reportSpecificPane;

    private VBox InitialPane;

    private Controller controller = Controller.getInstance();

    private MainReportPane(){
        overViewPane = new ReportOverViewPane();
        reportComparePane = new ReportComparePane();
        reportCompareSpecificPane = new ReportCompareSpecificPane();
        reportSpecificOverviewPane = new ReportSpecificOverviewPane();
        reportSpecificPane = new ReportSpecificPane();

        InitialPane = getInitialPane();


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

        getStylesheets().add("/CSS/AdminReportCSS.css");
        setSpacing(15);
        setVgrow(InitialPane, Priority.ALWAYS);
        getChildren().addAll(createHeader(), overViewPane, InitialPane);

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
        ObservableList<ReportCategoryMethod> categories = FXCollections.observableArrayList(ReportCategoryMethod.values());

        category.setItems(getCategories());
        Type.setItems(getTypes());

        category.getSelectionModel().select(0);
        Type.getSelectionModel().select(0);

        addCategoryListener();

        header.getChildren().addAll(categoryLabel, category, Type, columnView, gridView);

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
        ViewReportColumn columnview = new ViewReportColumn();
        ResponseOverviewReport  overviewReport = null;

        if (method == ReportCategoryMethod.OVERVIEW){

            overviewReport = controller.getOverViewData(param, type);
            columnview.showOverViewReport(overviewReport);
            getChildren().remove(2);
            getChildren().add(columnview);

        }else if (method == ReportCategoryMethod.COMPARE_OVERVIEW){
            ResponseCompareOverview compareOverview  = controller.getCompareOverViewData(param, type);

            columnview.showCompareOverviewReport(compareOverview, param);
            getChildren().remove(2);
            getChildren().add(columnview);

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

    public static MainReportPane getInstance(){

            if (mainReportPane == null){
                mainReportPane = new MainReportPane();
                return mainReportPane;
            }else {
                return mainReportPane;
            }
    }

}
