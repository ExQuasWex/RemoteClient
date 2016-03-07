package View.AdminGUI.Work;

import PriorityModels.PriorityType;
import Remote.Method.FamilyModel.Family;
import Remote.Method.FamilyModel.FamilyHistory;
import ToolKit.MessageBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

/**
 * Created by Didoy on 2/1/2016.
 */
public class TableActionCell extends TableCell {

    private ComboBox resolutionBox = new ComboBox();
    private String originalVal = "";
    private ObservableList orignalList = FXCollections.observableArrayList();
    private String provokeDescription = null;
    public RevokeHistory revokeHistory = null;
    private int x = 0;
    int def = 0;
    public TableActionCell(TableBarangayView tableBarangayView) {
        revokeHistory = new RevokeHistory(false, "", "");

        resolutionBox.setItems(getItems());

        resolutionBox.valueProperty().addListener(new ChangeListener() {
              @Override
                      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                            x = tableItemChange();
                            System.out.println("X: " + x);

                            if (newValue != originalVal  && x >= 1){

                                provokeDescription = MessageBox.showHistoryDialog("What is your reason for changing this?","Information Box");
                                originalVal = (String) newValue;
                                addRevokeHistory(provokeDescription);
                                x = 0;
                        }
                      }
                  }
        );

        this.setGraphic(resolutionBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);

    }

    //Display button if the row is not empty

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty){

            if (item == PriorityType.JOB){
                resolutionBox.getSelectionModel().select("JOB");
            }else if (item == PriorityType.HOME){
                resolutionBox.getSelectionModel().select("HOME");
            }else if (item == PriorityType.FREE){
                resolutionBox.getSelectionModel().clearSelection();
            }

            if (def == 0 && item != PriorityType.FREE){
                originalVal = (String) resolutionBox.getSelectionModel().getSelectedItem();
                def++;
            }else {
                resolutionBox.getSelectionModel().select(originalVal);
            }

            setGraphic(resolutionBox);

        } else {
            setGraphic(null);
        }
    }

    private ObservableList getItems(){
        ObservableList<String> itemList  =  FXCollections.observableArrayList();
        itemList.add("JOB");
        itemList.add("HOME");

    return itemList;
    }

    private void addRevokeHistory(String revokeDesc){

        if (revokeDesc != null) {

            String action = (String) resolutionBox.getSelectionModel().getSelectedItem();
            revokeHistory.setRevoke(true);
            revokeHistory.setSolution(action);
            revokeHistory.setRevokeDesck(revokeDesc);

            TableRow tableRow = getTableRow();

            Family family = (Family) tableRow.getItem();

            FamilyHistory familyHistory = family.getFamilyHistory();
            if (familyHistory != null){
                familyHistory.setAction(action);
                familyHistory.setRevokeDescription(revokeDesc);
                familyHistory.setRevoke(true);
            }else {
                familyHistory = new FamilyHistory();
                familyHistory.setAction(action);
                familyHistory.setRevokeDescription(revokeDesc);
                familyHistory.setRevoke(true);
            }

            family.setFamilyHistory(familyHistory);

        }else{
            resolutionBox.getSelectionModel().select(originalVal);
        }
    }


    public RevokeHistory getSelectedItem(){
        return revokeHistory;
    }

    private int tableItemChange(){
       ObservableList list =  getItems();
        int size = getItems().size();
            if (list != orignalList ){
                x = 0;
                orignalList = list;
                System.out.println("not equal to list");
            }else if (list == orignalList){
                x  = 1;
                System.out.println("equal to list");
            }else {
                System.out.println("else");

            }
        return  x;
    }
}
