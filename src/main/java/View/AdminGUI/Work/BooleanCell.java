package View.AdminGUI.Work;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

/**
 * Created by Didoy on 2/1/2016.
 */
public class BooleanCell<S, T> extends TableCell {

    private CheckBox checkBox = new CheckBox();
    private   ObservableValue<T> ov;
    public BooleanCell( ) {
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean uncheck, Boolean check) {

                    if (check){
                        System.out.println("check");
                    }else {
                        System.out.println("uncheck");

                    }
            }
        });
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);


    }


    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if(!empty){
            setGraphic(checkBox);
            if (ov instanceof BooleanProperty) {
                checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
            }
            ov = getTableColumn().getCellObservableValue(getIndex());
            if (ov instanceof BooleanProperty) {
                checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
            }
        } else {
            setGraphic(null);

        }
    }

    public void  setChecked(boolean isCheck){
        checkBox.setSelected(isCheck);

    }
}
