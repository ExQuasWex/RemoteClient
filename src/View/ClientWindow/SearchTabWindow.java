package View.ClientWindow;

import Family.Family;
import View.Login.CustomStage;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * Created by Didoy on 1/6/2016.
 */
public class SearchTabWindow extends CustomStage {

    private TabPane tabPane;
    private BorderPane root;


    public SearchTabWindow(){


        tabPane = new TabPane();
        root = new BorderPane();

        root.setCenter(tabPane);

        Scene scene = new Scene(root);

        setScene(scene);

    }

    public void addTab(String tabName, Family fam){

        Tab tab = new Tab(tabName);
        FamilyForm familyForm = new FamilyForm();

        familyForm.setDate(fam.getFamilyinfo().getInputDate());

    }


}
