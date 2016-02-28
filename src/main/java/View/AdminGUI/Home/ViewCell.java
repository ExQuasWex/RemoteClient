package View.AdminGUI.Home;

import PriorityModels.PriorityType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

/**
 * Created by reiner on 2/29/2016.
 */
public class ViewCell extends TableCell {

private Button view = new Button("View People");

    public ViewCell() {

        this.setGraphic(view);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);

        view.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

    //Display button if the row is not empty

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty){
            setGraphic(view);
        } else {
            setGraphic(null);
        }
    }

}
