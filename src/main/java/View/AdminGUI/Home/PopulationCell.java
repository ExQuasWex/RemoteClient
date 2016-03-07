package View.AdminGUI.Home;

import View.AdminGUI.Home.Listeners.ViewCellListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

/**
 * Created by reiner on 3/7/2016.
 */
public class PopulationCell extends TableCell{

    Label label = new Label();

    public PopulationCell(ViewCellListener listener){
        setAlignment(Pos.CENTER);
        label.setPrefWidth(100);
        label.setAlignment(Pos.CENTER);

    }

    @Override
    protected void updateItem(Object item, boolean empty) {
        if (!empty){
            label.setText(String.valueOf(item));
            setGraphic(label);

        }
    }

}
