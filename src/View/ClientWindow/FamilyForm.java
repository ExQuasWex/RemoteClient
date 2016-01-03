package View.ClientWindow;

import Controller.Controller;
import Family.Family;
import Family.FamilyInfo;
import Family.FamilyPoverty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * Created by Didoy on 10/15/2015.
 */
public class FamilyForm extends GridPane{

    private GridPane topPane;
    private GridPane bottomPane;
    private Button save;
    private  Button cancel;

    private TextField searchBox;
    private Button searchButton;

    private ScrollPane scrollPane;

    private GridPane subGrid;

    //top pane
    private TextField dateField;
    private TextField surveyedDateField;
    private TextField fnameField;
    private TextField lnameField;
    private TextField spousefnameField;
    private TextField spouselnameField;
    private TextField agefield;
    private TextField addressF;
    private TextField yrResidency;
    private TextField numofChildrenF;

    private ComboBox maritalCBox;
    private ComboBox barangayCb;
    private ComboBox genderCB;

    // bottom
    private ComboBox underEmployedCBox;
    private ComboBox otherIncomeCbox;
    private ComboBox ownershipCbox;
    private ComboBox below8kCbox;
    private ComboBox occupancyCBox;
    private ComboBox childrenSchlCBox;
    private int clientID;

    private FamilyFormListener familyFormListener;
    private Family family;

    private ObservableList<Node> errorNodeList;

    private SearchListener searchlistener;

    public FamilyForm(){

          setStyle("-fx-border-color: brown");
         getStylesheets().add("/CSS/familyFormCss.css");

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

         save = new Button("Save");
         cancel = new Button("Cancel");

         scrollPane = new ScrollPane(getSubGrid());

         save.setPrefWidth(80);
         cancel.setPrefWidth(80);
         searchButton.setPrefWidth(100);

         clientID = Controller.getInstance().getStaffInfo().getAccountID();

         errorNodeList = FXCollections.observableArrayList();


        /////////////////////////////////// MAIN PANE ///////////////////////////////////
        double gap = (screen.getWidth()/4)/2;

        setMargin(scrollPane, new Insets(0,0,0,gap));
        setConstraints(scrollPane, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);

        getChildren().addAll(scrollPane);


        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (isValidated()){
                    System.out.println("All Family information are validated");
                    //Save();
                }else {
                    // undecided
                }
            }
        });
    }

    private GridPane getSubGrid(){
        subGrid = new GridPane();

        topPane = getTopPane();
        bottomPane = getBottomPane();

        // Css styling
        topPane.getStyleClass().add("TopPaneBorder");
        bottomPane.getStyleClass().add("TopPaneBorder");

        Label bottomTitle = new Label("Poverty Assesment");
        Label topTitleL = new Label("Family Data Entry");

        topTitleL.getStyleClass().add("topPaneTitle");
        bottomTitle.getStyleClass().add("topPaneTitle");

        bottomPane.setHgap(10);
        bottomPane.setVgap(5);
        bottomPane.setAlignment(Pos.CENTER_LEFT);

        topPane.setVgap(10);
        topPane.setHgap(10);
        topPane.setAlignment(Pos.CENTER);

        subGrid.setAlignment(Pos.CENTER);
        subGrid.setPadding(new Insets(10));
        subGrid.setMargin(topPane, new Insets(10));
        subGrid.setMargin(bottomPane, new Insets(10));


        subGrid.setConstraints(topTitleL, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        subGrid.setConstraints(topPane, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        subGrid.setConstraints(bottomTitle, 0, 3, 1, 1, HPos.CENTER, VPos.CENTER);
        subGrid.setConstraints(bottomPane, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER);

        subGrid.getChildren().addAll(topTitleL,topPane,bottomTitle,bottomPane);


        return subGrid;
    }

    private GridPane getBottomPane(){
        ////////////////////////////////// BOTTOM PANE /////////////////////////////////////////////////

        bottomPane = new GridPane();

        //Labels
        Label underEmployedL = new Label("Under Employed");
        Label otherIncomeL = new Label("Other Income Resource");
        Label typeofOwnershipL = new Label("Ownership");
        Label below8kL = new Label("Below 8,000php");
        Label occupancyL = new Label("Occupancy");
        Label schoolage = new Label("Children in School");

        // Combobox

        underEmployedCBox  = new ComboBox(getYesNo());
        otherIncomeCbox    = new ComboBox(getYesNo());
        ownershipCbox      = new ComboBox(getOwnerShip());
        below8kCbox        = new ComboBox(getYesNo());
        occupancyCBox      = new ComboBox(getOccupancy());
        childrenSchlCBox   = new ComboBox(getChildrenSchool());

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

        indexY++;
        bottomPane.setConstraints(save,0,indexY,2,1,HPos.CENTER,VPos.CENTER);
        bottomPane.setConstraints(cancel,1,indexY,2,1,HPos.CENTER,VPos.CENTER);

        bottomPane.setMargin(save, new Insets(5,65,5,0));
        bottomPane.setMargin(cancel, new Insets(5,50,5,0));



        occupancyCBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String item = (String)occupancyCBox.getSelectionModel().getSelectedItem();
                if (item.equals("Employed") || item.equals("Self-Employed")){
                    underEmployedCBox.setDisable(false);
                }else {
                    underEmployedCBox.getSelectionModel().clearSelection();
                    underEmployedCBox.setDisable(true);
                }
            }
        });


        bottomPane.getChildren().addAll(
                underEmployedL,underEmployedCBox,otherIncomeL,otherIncomeCbox,
                typeofOwnershipL,ownershipCbox,below8kL,below8kCbox,
                occupancyL,occupancyCBox,schoolage,childrenSchlCBox,save,cancel);



        ///////// return to normal node if this nodes are disable spousefnameField, spouselnameField, underEmployedCBox

        spousefnameField.disabledProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean notDisable, Boolean isDisable) {
                if (isDisable){
                    System.out.println("Disable");
                    spousefnameField.getStyleClass().remove("text-field-error");
                    errorNodeList.remove(spousefnameField);
                }else{
                    System.out.println("not disable");
                }


            }
        });

        spouselnameField.disabledProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean notDisable, Boolean isDisable) {
                if (isDisable){
                    System.out.println("Disable");
                    spouselnameField.getStyleClass().remove("text-field-error");
                    errorNodeList.remove(spouselnameField);

                }

            }
        });

        underEmployedCBox.disabledProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean notDisable, Boolean isDisable) {
                if (isDisable){
                    System.out.println("Disable");
                    underEmployedCBox.getStyleClass().remove("combo-box-error");
                    errorNodeList.remove(underEmployedCBox);

                }

            }
        });
        return bottomPane;
    }

    private GridPane getTopPane(){
        topPane = new GridPane();

        // Top pane
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

        // search nodes declaration

        searchBox = new TextField();
        searchButton = new Button("Search");

        searchButton.setPrefHeight(27);
        searchBox.setPromptText("Search");
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPrefWidth(20);

        // topPane nodes declaration
        dateField  = new TextField(dateFormat.format(date));
        surveyedDateField  = new TextField();
        fnameField = new TextField();
        lnameField= new TextField();
        spousefnameField = new TextField();
        spouselnameField= new TextField();
        agefield= new TextField();
        addressF = new TextField();
        yrResidency= new TextField();
        numofChildrenF = new TextField();

        //nodes initialization
        dateField.setAlignment(Pos.CENTER);
        dateField.setDisable(true);
        numofChildrenF.setText("0");

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

        maritalCBox = new ComboBox(getMaritalStatus());
        barangayCb = new ComboBox(getBarangayCb());
        genderCB    = new ComboBox(getGender());


        maritalCBox.setPrefWidth(140);
        maritalCBox.setPromptText("Marital Status");
        maritalCBox.setEditable(false);

        genderCB.setPromptText("Gender");
        genderCB.setPrefWidth(140);

        barangayCb.setPromptText("Barangay");
        barangayCb.setEditable(false);

        /////////////// LAYOUTING //////////////

        //1st row
        int indexYTop =0;
        topPane.setMargin(searchBox, new Insets(0, 75, 25, 150));
        topPane.setMargin(searchButton, new Insets(0, -28, 25, 0));
        topPane.setConstraints(searchBox, 0, indexYTop, 5, 1, HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(searchButton, 0, indexYTop, 5, 1, HPos.RIGHT, VPos.CENTER);

        //2nd row
        indexYTop++;
        topPane.setConstraints(DateL,    0,indexYTop,1,1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(dateField,1,indexYTop,1,1, HPos.CENTER, VPos.CENTER);

        //3rd row
        indexYTop++;
        topPane.setConstraints(surveyDateL,      0, indexYTop, 1, 1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(surveyedDateField,1, indexYTop,1,1, HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(barangayCb,5,indexYTop,1,1, HPos.CENTER, VPos.CENTER);

        //4t row
        indexYTop++;
        bottomPane.setConstraints(yearofResidencyL,0,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(yrResidency,1,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(maritalCBox,5,indexYTop,1,1,HPos.CENTER,VPos.CENTER);

        //5th row
        indexYTop++;
        bottomPane.setConstraints(numofChildrenL,0,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(numofChildrenF,1,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(genderCB,5,indexYTop,1,1,HPos.CENTER,VPos.CENTER);


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
        dateField.setPrefColumnCount(6);
        lnameField.setPrefColumnCount(11);
        agefield.setPrefColumnCount(6);


        // add Change listener to validate number of children field
        numofChildrenF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean outFocused, Boolean focused) {

                if (focused){

                }else if (outFocused){

                    if (numofChildrenF.getText().matches("\\d+")){
                        int children = Integer.parseInt(numofChildrenF.getText());
                        if (children > 0 ){
                            numofChildrenF.setText(String.valueOf(children));
                        }else {
                            numofChildrenF.setText("0");
                        }
                    }else {
                        numofChildrenF.setText("0");
                    }

                }

            }
        });

        // add Change listener to validate number of children field
        agefield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean outFocus, Boolean focus) {

                if (outFocus){
                    if (agefield.getText().matches("\\d+")){
                        int age = Integer.parseInt(agefield.getText());
                        agefield.setText(String.valueOf(age));
                    }else {
                        agefield.setText("");
                    }
                }


            }
        });


            // adding nodes to the top gridpane
        topPane.getChildren().addAll(searchBox, searchButton ,DateL,dateField,surveyDateL,surveyedDateField,
                barangayCb,yearofResidencyL,yrResidency, maritalCBox,numofChildrenL,numofChildrenF,genderCB,
                NameL,lnameField,fnameField, ageL,agefield,spouseNameL,spouselnameField,spousefnameField,addressL,addressF);



        maritalCBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String item = (String) maritalCBox.getSelectionModel().getSelectedItem();

                if (item.equals("Married") || item.equals("Live-in")){
                    spouselnameField.setDisable(false);
                    spousefnameField.setDisable(false);
                }else {
                    spouselnameField.setText("");
                    spousefnameField.setText("");
                    spouselnameField.setDisable(true);
                    spousefnameField.setDisable(true);
                }
            }
        });



        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String Searchedname = searchBox.getText();

                searchlistener.SearchEvent(Searchedname);

            }
        });

        return  topPane;
    }


    public void addSearchListener(SearchListener listener){
        searchlistener = listener;
    }


    private ObservableList getBarangayCb(){
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

    private ObservableList getGender(){
        ObservableList<String> maritalStatus = FXCollections.observableArrayList();
        maritalStatus.add("Male");
        maritalStatus.add("Female");

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
        owenerShipList.add("Home Owner");
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

    private void Save(){

        if (underEmployedCBox.getSelectionModel().isEmpty()){

            String inputedDate = dateField.getText();
            int surveyedYr = Integer.parseInt(surveyedDateField.getText());
            int residencyYr = Integer.parseInt(yrResidency.getText());
            int numofchildren = Integer.parseInt(numofChildrenF.getText());
            String name = fnameField.getText() + " " + lnameField.getText();
            String spousename = spousefnameField.getText() + " " + spouselnameField.getText();
            String age = agefield.getText();
            String maritalStatus =  maritalCBox.getSelectionModel().getSelectedItem().toString();
            String barangay = barangayCb.getSelectionModel().getSelectedItem().toString();
            String gender =  genderCB.getSelectionModel().getSelectedItem().toString();
            String address =  addressF.getText();


            //poverty factors
            String hasOtherIncome = otherIncomeCbox.getSelectionModel().getSelectedItem().toString();
            String isBelow8k = below8kCbox.getSelectionModel().getSelectedItem().toString();
            String ownership = ownershipCbox.getSelectionModel().getSelectedItem().toString();
            String occupancy = occupancyCBox.getSelectionModel().getSelectedItem().toString();
            String isunderEmployed = "";
            String childrenScl = childrenSchlCBox.getSelectionModel().getSelectedItem().toString();


            FamilyInfo familyInfo = new FamilyInfo(clientID,inputedDate,surveyedYr,residencyYr,
                    numofchildren,name,spousename,age,maritalStatus,barangay,gender,address );

            FamilyPoverty familyPoverty  = new FamilyPoverty(hasOtherIncome,isBelow8k,ownership,
                    occupancy,isunderEmployed,childrenScl);

            family = new Family(familyInfo,familyPoverty);
            System.out.println("no error");


        }else{

            String inputedDate = dateField.getText();
            int surveyedYr = Integer.parseInt(surveyedDateField.getText());
            int residencyYr = Integer.parseInt(yrResidency.getText());
            int numofchildren = Integer.parseInt(numofChildrenF.getText());
            String name = fnameField.getText() + " " + lnameField.getText();
            String spousename = spousefnameField.getText() + " " + spouselnameField.getText();
            String age = agefield.getText();
            String maritalStatus =  maritalCBox.getSelectionModel().getSelectedItem().toString();
            String barangay = barangayCb.getSelectionModel().getSelectedItem().toString();
            String gender =  genderCB.getSelectionModel().getSelectedItem().toString();
            String address =  addressF.getText();


            //poverty factors
            String hasOtherIncome = otherIncomeCbox.getSelectionModel().getSelectedItem().toString();
            String isBelow8k = below8kCbox.getSelectionModel().getSelectedItem().toString();
            String ownership = ownershipCbox.getSelectionModel().getSelectedItem().toString();
            String occupancy = occupancyCBox.getSelectionModel().getSelectedItem().toString();
            String isunderEmployed = underEmployedCBox.getSelectionModel().getSelectedItem().toString();
            String childrenScl = childrenSchlCBox.getSelectionModel().getSelectedItem().toString();

            FamilyInfo familyInfo = new FamilyInfo(clientID,inputedDate,surveyedYr,residencyYr,
                    numofchildren,name,spousename,age,maritalStatus,barangay,gender,address );

            FamilyPoverty familyPoverty  = new FamilyPoverty(hasOtherIncome,isBelow8k,ownership,
                    occupancy,isunderEmployed,childrenScl);

            family = new Family(familyInfo,familyPoverty);
            System.out.println("no error");


        }

    }

    private boolean isValidated(){

        boolean isvalidate = false;
        String surveyedyr = surveyedDateField.getText();
        int  yrNow = Calendar.getInstance().get(Calendar.YEAR);
        String residencyYr = yrResidency.getText();
        String name = fnameField.getText() + " " + lnameField.getText();
        String spousename = spousefnameField.getText() + " " + spouselnameField.getText();
        String age = agefield.getText();
        String address =  addressF.getText();
        String maritalStatus = null;
                                ////////---- Year of residency ----////////

            if (surveyedyr.equals("")){

                isvalidate = false;
                showErrorMessage("Please add surveyed year in year field", "Error Information",surveyedDateField);

            }
            else if (!Pattern.matches("^[\\d]{4}+",surveyedyr)){

                showErrorMessage("Invalid Surveyed Year", "Error Information",surveyedDateField);
                System.out.println(yrNow);
                isvalidate = false;

            }
            else if (Integer.parseInt(surveyedyr) > yrNow){
                System.out.println(yrNow);
                showErrorMessage("Invalid surveyed year, surveyed year is later than the current year", "Error Information", surveyedDateField);
                isvalidate = false;

            }
                                ////////---- Year of residency ----////////
            else if (residencyYr.equals("")){
                showErrorMessage("Please add year of residency in residency field", "Error Information", yrResidency);
                isvalidate = false;
            }
            else if (!Pattern.matches("^[\\d]{4}+",residencyYr)){
                showErrorMessage("Invalid year of residency", "Error Information", yrResidency);
                isvalidate = false;
            }
            else if (Integer.parseInt(residencyYr) > yrNow){
                isvalidate = false;
                showErrorMessage("Invalid year of residency, year of residency is later than the current year", "Error Information",yrResidency);
            }
                                ////////---- Marital Status ----////////
            else if (maritalCBox.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Please select marital status", "Error Information",maritalCBox);
            }
                                ////////---- Barangay ----////////
            else if (barangayCb.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Please select barangay", "Error Information",barangayCb);
            }
                                 ////////---- Gender ----////////
            else if (genderCB.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Please select Gender", "Error Information",genderCB);
            }                    ////////---- Age ----////////
            else if (age.equals("")){
                isvalidate = false;
                showErrorMessage("Please add Age in age field", "Error Information",agefield);
            }
                                          ////////---- Name ----////////
            else if (fnameField.getText().equals("") || lnameField.getText().equals("")){
                isvalidate = false;
                errorNodeList.add(lnameField);
                showErrorMessage("Please add name in name fields", "Error Information",fnameField);
            }
            else if (!Pattern.matches("^[A-Za-z\\s]+",name)){
                isvalidate = false;
                errorNodeList.add(lnameField);
                showErrorMessage("Please remove any digit or any special character in name fields", "Error Information",fnameField);
            }
                                              ////////---- Spouse Name ----////////
            else if ((spousefnameField.getText().equals("") || spouselnameField.getText().equals("")) && (maritalCBox.getSelectionModel().getSelectedItem().toString().equals("Married")||
                    maritalCBox.getSelectionModel().getSelectedItem().toString().equals("Live-in"))){

                        isvalidate = false;
                        errorNodeList.add(spousefnameField);
                        showErrorMessage("Please add spouse name in spouse name fields", "Error Information", spouselnameField);

            }
                                              ////////---- Address ----////////
            else if (address.equals("")){
                isvalidate = false;
                showErrorMessage("Please add address in address field","Error Information",addressF);
            }
            else if (!Pattern.matches("^[\\d]{5}+\\s+[a-zA-Z-_.\\s]+",address)){
                isvalidate = false;
                showErrorMessage("Invalid Address in address field","Error Information",addressF);
            }else if (otherIncomeCbox.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Other income resource option is empty","Error Information",otherIncomeCbox);
            }else if (below8kCbox.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Below eight thousand option is empty","Error Information",below8kCbox);

            }else if (ownershipCbox.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Ownership option is empty","Error Information",ownershipCbox);

            }
            else if (occupancyCBox.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Occupancy option is empty","Error Information",occupancyCBox);

            }
            else if (underEmployedCBox.getSelectionModel().isEmpty() && occupancyCBox.getSelectionModel().getSelectedItem().equals("Employed")){
                isvalidate = false;
                showErrorMessage("UnderEmployed option is empty","Error Information",underEmployedCBox);

            }
            else if (childrenSchlCBox.getSelectionModel().isEmpty()){
                isvalidate = false;
                showErrorMessage("Children in school option is empty","Error Information",childrenSchlCBox);

            }
            else {
                isvalidate = true;
            }

        return isvalidate;
    }

    private void showErrorMessage(String msg,String title,Node node) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);

        errorNodeList.add(node);

        for (Node nodes : errorNodeList) {
            if (nodes.equals(maritalCBox) || nodes.equals(genderCB) || nodes.equals(barangayCb)
                    || nodes.equals(otherIncomeCbox)  || nodes.equals(below8kCbox)
                    || nodes.equals(ownershipCbox)  || nodes.equals(occupancyCBox)
                    || nodes.equals(underEmployedCBox) || nodes.equals(childrenSchlCBox)  ){

                nodes.getStyleClass().add("combo-box-error");

            }else{
                nodes.getStyleClass().add("text-field-error");
            }

        }

        for (Node nodee : errorNodeList){
            nodee.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (nodee.equals(maritalCBox) || nodee.equals(genderCB) || nodee.equals(barangayCb)
                            || nodee.equals(otherIncomeCbox)  || nodee.equals(below8kCbox)
                            || nodee.equals(ownershipCbox)  || nodee.equals(occupancyCBox)
                            || nodee.equals(underEmployedCBox) || nodee.equals(childrenSchlCBox)  ){

                        nodee.getStyleClass().remove("combo-box-error");
                        errorNodeList.remove(nodee);

                    }else {
                        nodee.getStyleClass().remove("text-field-error");
                        errorNodeList.remove(nodee);

                    }
                }
            });

                        // return to normal color after being focused
            nodee.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean outFocused, Boolean Focused) {
                    if (Focused){
                        if (nodee.equals(maritalCBox) || nodee.equals(genderCB) || nodee.equals(barangayCb)
                                || nodee.equals(otherIncomeCbox)  || nodee.equals(below8kCbox)
                                || nodee.equals(ownershipCbox)  || nodee.equals(occupancyCBox)
                                || nodee.equals(underEmployedCBox) || nodee.equals(childrenSchlCBox)  ){

                            nodee.getStyleClass().remove("combo-box-error");
                            errorNodeList.remove(nodee);

                        }else {
                            nodee.getStyleClass().remove("text-field-error");
                            errorNodeList.remove(nodee);

                        }
                    }
                }
            });
        }

        alert.show();
    }


    public void addFamilyFormListener (FamilyFormListener familyFormListener){
        this.familyFormListener = familyFormListener;

    }







}
