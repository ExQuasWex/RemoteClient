package View.Components;

import Controller.Controller;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Didoy on 2/24/2016.
 */
public class LoadBar {

    private static double percent;
    private static SimpleDoubleProperty valueProperty = new SimpleDoubleProperty();
    private static Text text = new Text();
    private static ProgressBar pb = new ProgressBar(0);
    private static Stage stage = new Stage();
    private static double SIZE = 0.0;


    public static void createProgressbar(int size){
         percent = (100)/size;
         SIZE = size;
         valueProperty.set(0);
         pb.setPrefWidth(300);

         HBox hBox = new HBox(5);
         hBox.setPadding(new Insets(10));
         hBox.setAlignment(Pos.CENTER);
         hBox.getChildren().addAll(pb, text);

         Scene  scene = new Scene(hBox);

         stage.setResizable(false);
         stage.setScene(scene);
         stage.show();
    }

    public static void updateValue() {
        valueProperty.set(valueProperty.get() + percent);
        pb.progressProperty().bind(valueProperty);
        text.setText(String.valueOf(pb.getProgress()));

        if (pb.getProgress() == SIZE){
            stage.close();
            Controller.clientEntryList.clear();
        }
    }

}
