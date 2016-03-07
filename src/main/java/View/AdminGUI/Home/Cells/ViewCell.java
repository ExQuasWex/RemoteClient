package View.AdminGUI.Home.Cells;

import View.AdminGUI.Home.Listeners.ViewCellListener;
import View.AdminGUI.Home.Listeners.ViewPopleCellListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import utility.Utility;

/**
 * Created by reiner on 2/29/2016.
 */
public class ViewCell extends TableCell {

private Button view = new Button("View People");

    private ViewCellListener listener;

    public ViewCell(ViewPopleCellListener listener) {
        setAlignment(Pos.CENTER);


        this.setGraphic(view);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);

        view.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TableRow row = getTableRow();
                int index =  row.getIndex();

               TableView tableView =  getTableView();
               TableColumn tableColumn = (TableColumn) tableView.getColumns().get(0);

                String barangayName = (String) tableColumn.getCellData(index);
                String date = Utility.getCurrentYear();
                listener.viewAllPeople(barangayName, date);
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
