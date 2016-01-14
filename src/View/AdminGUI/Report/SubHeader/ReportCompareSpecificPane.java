package View.AdminGUI.Report.SubHeader;

import AdminModel.Params;
import View.AdminGUI.Report.interfaces.ReportButtonListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

/**
 * Created by Didoy on 1/13/2016.
 */
public class ReportCompareSpecificPane extends HBox {

    private ComboBox barangay1 ;
    private ComboBox barangay2 ;
    private ComboBox Month ;
    private ComboBox Year ;
    private Button generateReportBtn;
    private ReportButtonListener reportButtonListener;

    public ReportCompareSpecificPane() {
        setAlignment(Pos.CENTER);
        setSpacing(5);
        generateReportBtn = new Button("Generate Report");

        barangay1 = new ComboBox();
        barangay2 = new ComboBox();
        Month = new ComboBox();
        Year = new ComboBox();

        barangay1.setPromptText("Barangay 1");
        barangay2.setPromptText("Barangay 2");
        Month.setPromptText("Month");
        Year.setPromptText("Year");


        barangay1.setItems(getBarangay());
        barangay2.setItems(getBarangay());
        Month.setItems(getMonths());
        Year.setItems(getYears());

        setMargin(generateReportBtn, new Insets(0,0,0,10));

        getChildren().addAll(barangay1, barangay2, Month, Year, generateReportBtn);

        generateReportBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Params params = new Params(2012,1,0,"Dau", null);
                reportButtonListener.generateReport(params);
            }
        });

    }
    private ObservableList getMonths(){
        // fetch years here
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        months.add("All Month");


        return months;
    }

    private ObservableList getBarangay(){

        ObservableList<String> baranagayList = FXCollections.observableArrayList();
        baranagayList.add("Atlo Bola");
        baranagayList.add("Bical");
        baranagayList.add("Bundangul");
        baranagayList.add("Cacutud");
        baranagayList.add("Calumpang");
        baranagayList.add("Camchilles");
        baranagayList.add("Dapdap");
        baranagayList.add("Dau");
        baranagayList.add("Dolores");
        baranagayList.add("Duquit");
        baranagayList.add("Lakandula");
        baranagayList.add("Mabiga");
        baranagayList.add("Macapagal village");
        baranagayList.add("Mamatitang");
        baranagayList.add("Mangalit");
        baranagayList.add("Marcos village");
        baranagayList.add("Mawaque");
        baranagayList.add("Paralayunan");
        baranagayList.add("Publasyon");
        baranagayList.add("San Francisco");
        baranagayList.add("San Joaquin");
        baranagayList.add("Sta. Ines");
        baranagayList.add("Sta. Maria");
        baranagayList.add("Sto. Rosario");
        baranagayList.add("Sapang Balen");
        baranagayList.add("Sapang Biabas");
        baranagayList.add("Tabun");

        return  baranagayList;
    }
    private ObservableList getYears(){
        // fetch years here
        ObservableList<String> initialYearLis = FXCollections.observableArrayList();
        initialYearLis.add("2011");
        initialYearLis.add("2013");
        initialYearLis.add("All year");

        return initialYearLis;
    }

    public void addReportButtonListener(ReportButtonListener reportButtonListener){
        this.reportButtonListener = reportButtonListener;
    }




}
