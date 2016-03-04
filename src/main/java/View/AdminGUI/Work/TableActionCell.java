package View.AdminGUI.Work;

import PriorityModels.PriorityType;
import View.ToolKit.MessageBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

/**
 * Created by Didoy on 2/1/2016.
 */
public class TableActionCell extends TableCell {

    private ComboBox resolutionBox = new ComboBox();
    private boolean isPrvoke;
    private String provokeDescription = null;
    private RevokeHistory revokeHistory = null;

    public TableActionCell() {

        resolutionBox.setItems(getItems());

        resolutionBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String item = (String) resolutionBox.getSelectionModel().getSelectedItem();
                    if (item != oldValue){
                            provokeDescription = MessageBox.showHistoryDialog("What is your reason for changing this?","Information Box");

                        if (provokeDescription != null) {
                            isPrvoke = true;
                            revokeHistory = new RevokeHistory(isPrvoke, item, provokeDescription);
                        }
                    }

            }
        });

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
                resolutionBox.getSelectionModel().select("Job Opportunity");
            }else if (item == PriorityType.HOME){
                resolutionBox.getSelectionModel().select("Home");
            }else if (item == PriorityType.FREE){
                resolutionBox.getSelectionModel().clearSelection();
            }
            setGraphic(resolutionBox);
        } else {
            setGraphic(null);
        }
    }

    private ObservableList getItems(){
        ObservableList<String> itemList  =  FXCollections.observableArrayList();
        itemList.add("Job Opportunity");
        itemList.add("Home");

    return itemList;
    }

    public RevokeHistory getSelectedItem(){
       return revokeHistory;
    }
}
