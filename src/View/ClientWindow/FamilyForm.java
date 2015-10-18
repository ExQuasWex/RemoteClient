package View.ClientWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Didoy on 10/15/2015.
 */
public class FamilyForm extends GridPane{

    private GridPane topPane;
    private GridPane bottomPane;

    public FamilyForm(){

         topPane = new GridPane();
         bottomPane = new GridPane();


        // Top pane
        Label topTitleL = new Label("Family Data Entry");
        Label DateL = new Label("Date");
        Label surveyDateL = new Label("Surveyed Year");
        Label NameL = new Label("Name");
        Label spouseNameL = new Label("Spouse Name");
        Label addressL = new Label("Address");
        Label ageL = new Label("Age");
        Label yearofResidencyL = new Label("Year of Residency");
        Label numofChildrenL = new Label("Number of Children");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");  // yyyy/MM/dd HH:mm:ss
        Date date = new Date();

        TextField DateField =  new TextField(dateFormat.format(date));
        TextField surveyedDateField = new TextField();
        TextField fnameField = new TextField();
        TextField lnameField = new TextField();
        TextField spousefnameField = new TextField();
        TextField spouselnameField = new TextField();
        TextField agefield = new TextField();
        TextField addressF = new TextField();
        TextField yrResidency = new TextField();
        TextField numofChildrenF = new TextField();

        DateField.setAlignment(Pos.CENTER);
        DateField.setDisable(true);

        fnameField.setPromptText("First Name");
        lnameField.setPromptText("Last Name");
        spousefnameField.setPromptText("First Name");
        spouselnameField.setPromptText("Last Name");
        surveyedDateField.setPromptText("e.g 2014");
        agefield.setPromptText("35");
        addressF.setPromptText("e.g 12345 Manga st. Mabalacat");
        yrResidency.setPromptText("e.g 2012");
        numofChildrenF.setPromptText("e.g 4");

        spousefnameField.setDisable(true);
        spouselnameField.setDisable(true);

        ComboBox maritalCBox = new ComboBox(getMaritalStatus());
        ComboBox barangay = new ComboBox(getBarangay());

        maritalCBox.setPrefWidth(140);
        maritalCBox.setPromptText("Marital Status");
        maritalCBox.setEditable(false);
        barangay.setPromptText("Barangay");
        barangay.setEditable(false);


        //1st row
        int indexYTop =0;
        topTitleL.setAlignment(Pos.CENTER);
        topPane.setConstraints(topTitleL,0,indexYTop,6,1, HPos.CENTER, VPos.TOP);
        //2nd row
        indexYTop++;
        topPane.setConstraints(DateL,    0,indexYTop,1,1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(DateField,1,indexYTop,1,1, HPos.CENTER, VPos.CENTER);

        //3rd row
        indexYTop++;
        topPane.setConstraints(surveyDateL,      0, indexYTop, 1, 1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(surveyedDateField,1, indexYTop,1,1, HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(barangay,5,indexYTop,1,1, HPos.CENTER, VPos.CENTER);

        //4t row
        indexYTop++;
        bottomPane.setConstraints(yearofResidencyL,0,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(yrResidency,1,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(maritalCBox,5,indexYTop,1,1,HPos.CENTER,VPos.CENTER);

        //5th row
        indexYTop++;
        bottomPane.setConstraints(numofChildrenL,0,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(numofChildrenF,1,indexYTop,1,1,HPos.LEFT,VPos.CENTER);

        //5th row
        indexYTop++;
        topPane.setConstraints(NameL,     0,indexYTop,1,1,  HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(lnameField,1,indexYTop,1,1,  HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(fnameField,2,indexYTop, 1, 1, HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(ageL,      4,indexYTop, 1, 1, HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(agefield,  5,indexYTop, 1, 1, HPos.CENTER, VPos.CENTER);


        //6t row
        indexYTop++;
        topPane.setConstraints(spouseNameL,     0,indexYTop,1,1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(spouselnameField,1,indexYTop,1,1,  HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(spousefnameField,2,indexYTop, 1, 1, HPos.CENTER, VPos.CENTER);

        //6t row
        indexYTop++;
        topPane.setConstraints(addressL,0,indexYTop,1,1,  HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(addressF,1,indexYTop,2,1,  HPos.CENTER, VPos.CENTER);

        surveyedDateField.setPrefColumnCount(6);
        DateField.setPrefColumnCount(6);
        lnameField.setPrefColumnCount(11);
        agefield.setPrefColumnCount(6);

        topPane.getChildren().addAll(DateL,DateField,surveyDateL,surveyedDateField,barangay,yearofResidencyL,yrResidency,
                maritalCBox,numofChildrenL,numofChildrenF,NameL,lnameField,fnameField,
                ageL,agefield,spouseNameL,spouselnameField,spousefnameField,addressL,addressF);



        maritalCBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String item = (String) maritalCBox.getSelectionModel().getSelectedItem();

                    if (item.equals("Married") || item.equals("Live-in")){
                        spouselnameField.setDisable(false);
                        spousefnameField.setDisable(false);
                    }else {
                        spouselnameField.setDisable(true);
                        spousefnameField.setDisable(true);
                    }
            }
        });

        ////////////////////////////////// BOTTOM PANE /////////////////////////////////////////////////



        //Labels
        Label bottomTitle = new Label("Poverty Assesment");
        Label underEmployedL = new Label("Under Employed");
        Label otherIncomeL = new Label("Other Income Resource");
        Label typeofOwnershipL = new Label("Ownership");
        Label below8kL = new Label("Below 8,000php");
        Label occupancyL = new Label("Occupancy");
        Label schoolage = new Label("Children in School");

        // Combobox

        ComboBox underEmployedCBox = new ComboBox(getYesNo());
        ComboBox otherIncomeCbox = new ComboBox(getYesNo());
        ComboBox ownershipCbox = new ComboBox(getOwnerShip());
        ComboBox below8kCbox = new ComboBox(getYesNo());
        ComboBox occupancyCBox = new ComboBox(getOccupancy());
        ComboBox childrenSchlCBox = new ComboBox(getChildrenSchool());

        ownershipCbox.setPrefWidth(115);
        childrenSchlCBox.setPrefWidth(115);
        occupancyCBox.setPrefWidth(115);

        underEmployedCBox.setDisable(true);


        int indexY = 0;


        indexY++;
        bottomPane.setConstraints(otherIncomeL,0,indexY,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(otherIncomeCbox,1,indexY,1,1,HPos.LEFT,VPos.CENTER);

        indexY++;
        bottomPane.setConstraints(below8kL,0,indexY,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(below8kCbox,1,indexY,1,1,HPos.LEFT,VPos.CENTER);

        indexY++;
        bottomPane.setConstraints(typeofOwnershipL,0,indexY,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(ownershipCbox,1,indexY,1,1,HPos.LEFT,VPos.CENTER);

        indexY++;
        bottomPane.setConstraints(occupancyL,0,indexY,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(occupancyCBox,1,indexY,1,1,HPos.LEFT,VPos.CENTER);

        indexY++;
        bottomPane.setConstraints(underEmployedL,0,indexY,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(underEmployedCBox,1,indexY,1,1,HPos.LEFT,VPos.CENTER);

        indexY++;
        bottomPane.setConstraints(schoolage,0,indexY,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(childrenSchlCBox,1,indexY,1,1,HPos.LEFT,VPos.CENTER);


        occupancyCBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String item = (String)occupancyCBox.getSelectionModel().getSelectedItem();
                if (item.equals("Employed") || item.equals("Self-Employed")){
                    underEmployedCBox.setDisable(false);
                }else {
                    underEmployedCBox.setDisable(true);
                }
            }
        });


        bottomPane.getChildren().addAll(bottomTitle,
               underEmployedL,underEmployedCBox,otherIncomeL,otherIncomeCbox,
               typeofOwnershipL,ownershipCbox,below8kL,below8kCbox,
                occupancyL,occupancyCBox,schoolage,childrenSchlCBox);


        bottomPane.setHgap(10);
        bottomPane.setVgap(5);
        bottomPane.setAlignment(Pos.CENTER_LEFT);



        topPane.setVgap(5);
        topPane.setHgap(10);
        topPane.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(40));
        setMargin(topPane, new Insets(20));
        setMargin(bottomPane, new Insets(20));

        setConstraints(topTitleL,0,0,1,1,HPos.CENTER,VPos.CENTER);
        setConstraints(topPane,  0,1,1,1,HPos.CENTER,VPos.CENTER);
        setConstraints(bottomTitle,0,2,1,1,HPos.CENTER,VPos.CENTER);
        setConstraints(bottomPane,0,3,1,1,HPos.LEFT,VPos.CENTER);


        getChildren().addAll(topTitleL,topPane,bottomTitle,bottomPane);
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

    private ObservableList getMaritalStatus(){
        ObservableList<String> maritalStatus = FXCollections.observableArrayList();
        maritalStatus.add("Married");
        maritalStatus.add("Live-in");
        maritalStatus.add("Single");
        maritalStatus.add("Seperated");
        maritalStatus.add("Widowed");

        return  maritalStatus;
    }

    private ObservableList getYesNo(){
        ObservableList<String> YesNoList = FXCollections.observableArrayList();
        YesNoList.add("Yes");
        YesNoList.add("No");

        return  YesNoList;
    }
    private ObservableList getOwnerShip(){
        ObservableList<String> owenerShipList = FXCollections.observableArrayList();
        owenerShipList.add("Rental");
        owenerShipList.add("HomeOwner");
        owenerShipList.add("Sharer");
        owenerShipList.add("Informal Settler");


        return  owenerShipList;
    }

    private ObservableList getOccupancy(){
        ObservableList<String> owenerShipList = FXCollections.observableArrayList();
        owenerShipList.add("Employed");
        owenerShipList.add("Unemployed");
        owenerShipList.add("Self-Employed");
        owenerShipList.add("Retired");


        return  owenerShipList;
    }
    private ObservableList getChildrenSchool(){
        ObservableList<String> owenerShipList = FXCollections.observableArrayList();
        owenerShipList.add("Graduated");
        owenerShipList.add("Vocational");
        owenerShipList.add("In-School");
        owenerShipList.add("Some");
        owenerShipList.add("None");
        return  owenerShipList;
    }





}
