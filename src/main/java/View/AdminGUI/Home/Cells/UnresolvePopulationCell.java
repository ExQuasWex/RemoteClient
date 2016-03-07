package View.AdminGUI.Home.Cells;

import View.AdminGUI.Home.Listeners.StatusCellListener;
import View.AdminGUI.Home.Listeners.ViewCellListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import utility.Utility;

/**
 * Created by reiner on 3/7/2016.
 */
public class UnresolvePopulationCell extends TableCell {

    Button view = new Button("view");

    public UnresolvePopulationCell(ViewCellListener listener){

        setAlignment(Pos.CENTER);
        view.setPrefWidth(100);

        view.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TableRow row = getTableRow();
                int index =  row.getIndex();

                TableView tableView =  getTableView();
                TableColumn tableColumn = (TableColumn) tableView.getColumns().get(0);

                String barangayName = (String) tableColumn.getCellData(index);

                String year = Utility.getCurrentYear();
                listener.viewData(barangayName, year);

            }
        });

    }

    @Override
    protected void updateItem(Object item, boolean empty) {
        if (!empty){
            view.setText(String.valueOf(item));
            setGraphic(view);
            setGraphic(view);
        }
    }
}
