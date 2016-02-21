package View.ClientWindow;

import Controller.Controller;
import Remote.Method.FamilyModel.Family;
import Remote.Method.FamilyModel.FamilyInfo;
import Remote.Method.FamilyModel.FamilyPoverty;
import View.ClientWindow.Listeners.FamilyFormListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import utility.FamilyNodes;
import utility.Utility;

import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.regex.Pattern;


/**
 * Created by Didoy on 10/15/2015.
 */
public class FamilyForm extends GridPane{

    private GridPane topPane;
    private GridPane bottomPane;
    private Button save;
    private  Button cancel;

    private ScrollPane scrollPane;

    private GridPane subGrid;

    //top pane
    private TextField dateField;
    private DatePicker datePicker;
    private TextField Name;
    private TextField SpouseName;
    private TextField agefield;
    private TextField addressF;
    private TextField yrResidency;
    private TextField numofChildrenF;
    private String surveyedyr;

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


    private Text Title = new Text("Survey Form");

    private Label bottomTitle = new Label("Poverty Assesment");
    private Label topTitleL = new Label("Family Data Entry");


    private boolean Validate(){

        FamilyNodes familyNodes = new FamilyNodes(dateField, datePicker, Name, SpouseName, agefield,
                addressF, yrResidency, numofChildrenF, surveyedyr, maritalCBox, barangayCb, genderCB, underEmployedCBox,
                otherIncomeCbox, ownershipCbox, below8kCbox, occupancyCBox,childrenSchlCBox);

        return Utility.Validate(familyNodes);
    }

    public FamilyForm(){

         getStylesheets().add("/CSS/familyFormCss.css");

         save = new Button("Save");
         cancel = new Button("Cancel");

         Title.setFontSmoothingType(FontSmoothingType.LCD);
         Title.setFont(javafx.scene.text.Font.font(20));


         scrollPane = new ScrollPane(getSubGrid());
         scrollPane.setFitToWidth(true);

         save.setPrefWidth(80);
         cancel.setPrefWidth(80);

         clientID = Controller.getInstance().getStaffInfo().getAccountID();

        /////////////////////////////////// MAIN PANE ///////////////////////////////////

        setConstraints(Title, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        setConstraints(scrollPane, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);

        getChildren().addAll(Title, scrollPane);

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Validate()){
                    Save();
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clear();
            }
        });

        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String x = datePicker.getValue().toString();
                LocalDate date = LocalDate.parse(x);
                System.out.println(date.getMonth());

            }
        });

    }

    private GridPane getSubGrid(){
        subGrid = new GridPane();

        topPane = getTopPane();
        bottomPane = getBottomPane();

        topPane.setPadding(new Insets(50));
        bottomPane.setPadding(new Insets(50));
        subGrid.setPadding(new Insets(0,100,0, 100));

        // Css styling
        topPane.getStyleClass().add("TopPaneBorder");
        bottomPane.getStyleClass().add("TopPaneBorder");

        topTitleL.getStyleClass().add("topPaneTitle");
        bottomTitle.getStyleClass().add("topPaneTitle");

        bottomPane.setHgap(10);
        bottomPane.setVgap(5);
        bottomPane.setAlignment(Pos.CENTER_LEFT);

        topPane.setVgap(10);
        topPane.setHgap(10);
        topPane.setAlignment(Pos.CENTER);

        subGrid.setAlignment(Pos.CENTER);
        subGrid.setMargin(topPane, new Insets(10));
        subGrid.setMargin(bottomPane, new Insets(10));


        subGrid.setConstraints(topPane, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        subGrid.setConstraints(bottomPane, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);

        subGrid.getChildren().addAll(topPane,bottomPane);

        return subGrid;
    }

    private GridPane getBottomPane(){

        //======================================= BOTTOM PANE =======================================//

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
        childrenSchlCBox.setDisable(true);
        occupancyCBox.setPrefWidth(115);

        underEmployedCBox.setDisable(true);

        int indexY = 0;

        bottomPane.setConstraints(bottomTitle,0,indexY,1,1,HPos.LEFT,VPos.CENTER);

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


        addBottomComponentListeners();

        bottomPane.getChildren().addAll(bottomTitle,
                underEmployedL,underEmployedCBox,otherIncomeL,otherIncomeCbox,
                typeofOwnershipL,ownershipCbox,below8kL,below8kCbox,
                occupancyL,occupancyCBox,schoolage,childrenSchlCBox,save,cancel);

        return bottomPane;
    }

    private GridPane getTopPane(){
        topPane = new GridPane();

        // Top pane
        Label DateL = new Label("Date");
        Label surveyDateL = new Label("Surveyed Date");
        Label NameL = new Label("Name");
        Label spouseNameL = new Label("Spouse Name");
        Label addressL = new Label("Address");
        Label ageL = new Label("Age");
        Label yearofResidencyL = new Label("Year of Residency");
        Label numofChildrenL = new Label("Number of Children");

        // search nodes declaration

        // topPane nodes declaration
         dateField  = new TextField(Utility.getCurrentDate());

        datePicker = new DatePicker();
        Name = new TextField();
        SpouseName = new TextField();
        agefield= new TextField();
        addressF = new TextField();
        yrResidency= new TextField();
        numofChildrenF = new TextField();

        //nodes initialization
        dateField.setAlignment(Pos.CENTER);
        dateField.setDisable(true);
        numofChildrenF.setText("0");

        Name.setPromptText("Full Name");
        SpouseName.setPromptText("Spouse Full Name");
        agefield.setPromptText("35");
        addressF.setPromptText("e.g 12345 Manga st. Mabalacat");
        yrResidency.setPromptText("e.g 2012");
        numofChildrenF.setPromptText("e.g 4");

        SpouseName.setDisable(true);

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

        // --------------------- Layouting ----------------------------///

        int indexYTop = 0;

        topPane.setConstraints(topTitleL, 0, indexYTop, 1, 1, HPos.LEFT, VPos.TOP);

        indexYTop++;
        topPane.setConstraints(DateL,      0,  indexYTop,1,1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(dateField,  1,  indexYTop,1,1, HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(barangayCb, 5,  indexYTop,1,1, HPos.CENTER, VPos.CENTER);

        indexYTop++;

        topPane.setConstraints(surveyDateL, 0, indexYTop, 1, 1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(datePicker, 1, indexYTop,1,1, HPos.CENTER, VPos.CENTER);
        bottomPane.setConstraints(maritalCBox,5,indexYTop,1,1,HPos.CENTER,VPos.CENTER);

        indexYTop++;
        bottomPane.setConstraints(yearofResidencyL,0,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(yrResidency,1,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(genderCB,5,indexYTop,1,1,HPos.CENTER,VPos.CENTER);

        indexYTop++;
        bottomPane.setConstraints(numofChildrenL,0,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        bottomPane.setConstraints(numofChildrenF,1,indexYTop,1,1,HPos.LEFT,VPos.CENTER);
        topPane.setConstraints(ageL,      4,indexYTop, 1, 1, HPos.CENTER, VPos.CENTER);
        topPane.setConstraints(agefield,  5,indexYTop, 1, 1, HPos.CENTER, VPos.CENTER);

        indexYTop++;
        topPane.setConstraints(NameL,     0,indexYTop,1,1,  HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(Name,      1,indexYTop, 5, 1, HPos.CENTER, VPos.CENTER);

        indexYTop++;
        topPane.setConstraints(spouseNameL,     0,indexYTop,1,1, HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(SpouseName,1,indexYTop, 5, 1, HPos.CENTER, VPos.CENTER);

        indexYTop++;
        topPane.setConstraints(addressL,0,indexYTop,1,1,  HPos.LEFT, VPos.CENTER);
        topPane.setConstraints(addressF,1,indexYTop,5,1,  HPos.CENTER, VPos.CENTER);

        dateField.setPrefColumnCount(6);
        agefield.setPrefColumnCount(6);

        // adding nodes to the top gridpane
        topPane.getChildren().addAll(topTitleL, DateL,dateField,surveyDateL, datePicker,
                barangayCb,yearofResidencyL,yrResidency, maritalCBox,numofChildrenL,numofChildrenF,genderCB,
                NameL, Name, ageL,agefield,spouseNameL, SpouseName,addressL,addressF);

        addTopComponentListeners();

        return  topPane;
    }


    private void addTopComponentListeners(){

        //  validate number of children field
        numofChildrenF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean outFocused, Boolean focused) {

                if (focused){

                }else if (outFocused){

                    if (numofChildrenF.getText().matches("\\d+")){
                        int children = Integer.parseInt(numofChildrenF.getText());
                        if (children > 1 ){
                            numofChildrenF.setText(String.valueOf(children));
                            if (childrenSchlCBox.getItems().contains("Some")){

                            }else {
                                childrenSchlCBox.getItems().add("Some");
                            }
                            childrenSchlCBox.setDisable(false);
                        }else if (children == 1){
                            numofChildrenF.setText(String.valueOf(children));
                            childrenSchlCBox.setDisable(false);
                            childrenSchlCBox.getItems().remove("Some");
                        }
                        else {
                            numofChildrenF.setText("0");
                            childrenSchlCBox.setDisable(true);
                            childrenSchlCBox.getSelectionModel().clearSelection();
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

                if (outFocus) {
                    if (agefield.getText().matches("\\d+")) {
                        int age = Integer.parseInt(agefield.getText());
                        agefield.setText(String.valueOf(age));
                    } else {
                        agefield.setText("");
                    }
                }

            }
        });

        maritalCBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String item = (String) maritalCBox.getSelectionModel().getSelectedItem();

                if (item.equals("Married") || item.equals("Live-in")){
                    SpouseName.setDisable(false);
                }else {
                    SpouseName.getStyleClass().remove("text-field-error");
                    SpouseName.setText("");
                    SpouseName.setDisable(true);
                    System.out.println("remove spouse name red border");
                }
            }
        });

        barangayCb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("barangayCb called");
            }
        });

    }

    private void addBottomComponentListeners(){

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



        ///////// return to normal node if this nodes are disable underEmployedCBox

        underEmployedCBox.disabledProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean notDisable, Boolean isDisable) {
                if (isDisable){
                    System.out.println(" underEmployedCBox Disable");
                    underEmployedCBox.getStyleClass().remove("combo-box-error");
                }

            }
        });
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

            // Family Information
            String inputedDate = dateField.getText();
            LocalDate dateissued = datePicker.getValue();
            int residencyYr = Integer.parseInt(yrResidency.getText());
            int numofchildren = Integer.parseInt(numofChildrenF.getText());
            String name = Name.getText() + " " ;
            String spousename = SpouseName.getText() + " " ;
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
            String childrenScl;

                    if (childrenSchlCBox.getSelectionModel().isEmpty()){
                        childrenScl = "";
                    }else {
                        childrenScl = childrenSchlCBox.getSelectionModel().getSelectedItem().toString();
                    }

                    if (underEmployedCBox.getSelectionModel().isEmpty()){
                        isunderEmployed = "";
                    }else {
                         isunderEmployed = underEmployedCBox.getSelectionModel().getSelectedItem().toString();
                    }


            FamilyInfo familyInfo = new FamilyInfo(clientID,inputedDate, dateissued,residencyYr,
                    numofchildren,name,spousename,age,maritalStatus,barangay,gender,address );

            FamilyPoverty familyPoverty  = new FamilyPoverty(hasOtherIncome,isBelow8k,ownership,
                    occupancy,isunderEmployed,childrenScl, dateissued, Utility.getCurrentMonth());

            family = new Family(familyInfo,familyPoverty);

             // send data to clientWindow
            familyFormListener.handle(family);

            // Doesn't clear data unless notification is falls
            if (!Controller.isNotified){
                clear();
            }

    }


    public void addFamilyFormListener (FamilyFormListener familyFormListener){
        this.familyFormListener = familyFormListener;

    }

    private void clear(){
        Utility.ClearComponents(topPane);
        Utility.ClearComponents(bottomPane);

        dateField.setText(Utility.getCurrentDate());
        numofChildrenF.setText("0");

    }

}
