package View.ClientWindow;

import AdminModel.RequestAccounts;
import Family.Family;
import Family.FamilyInfo;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by Didoy on 12/6/2015.
 */
public class SearchTable extends VBox {


    private TableView<Family> table;

    private  TableColumn idCol;
    private  TableColumn nameCol;
    private  TableColumn spouseCol;

    public SearchTable(){

         table = new TableView();

         idCol = new TableColumn("ID");
         nameCol = new TableColumn("Name");
         spouseCol = new TableColumn("Spouse Name");

        table.getColumns().addAll(idCol,nameCol,spouseCol);


        getChildren().add(table);


    }

    public void setData(ArrayList data){

        ObservableList<Family> Data = FXCollections.observableArrayList(data);
        table.setItems(Data);

        idCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family,Integer>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family,Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().familyId());
            }
        });

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family,String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family,String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getName());
            }
        });

        spouseCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Family,String>, ObservableValue<Family>>() {
            @Override
            public ObservableValue<Family> call(TableColumn.CellDataFeatures<Family,String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getFamilyinfo().getSpouseName());
            }
        });


    }
}
