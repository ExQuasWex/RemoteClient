package View.AdminGUI.Work;

import PriorityModels.PriorityLevel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by reiner on 2/28/2016.
 */
public class PriorityProgressBarCell extends TableCell{

    ProgressBar progressBar = new ProgressBar();

    private static final String RED_BAR    = "red-bar";
    private static final String YELLOW_BAR = "yellow-bar";
    private static final String ORANGE_BAR = "orange-bar";
    private static final String GREEN_BAR  = "green-bar";
    private static final String[] barColorStyleClasses = { RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };


    public PriorityProgressBarCell(){

        this.setGraphic(progressBar);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);

        setAlignment(Pos.CENTER);
        setTextAlignment(TextAlignment.CENTER);

      getStylesheets().add("/CSS/ProgressBarPriority.css");


    }
    //Display button if the row is not empty

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty){
            String prio =  "";
            PriorityLevel priorityLevel = (PriorityLevel) item;
            if (priorityLevel == PriorityLevel.Low){
                setProgressBar(progressBar, "green-bar", .2);

            }else if(priorityLevel == PriorityLevel.Moderate){
                setProgressBar(progressBar, "yellow-bar", .5);

            }else if(priorityLevel == PriorityLevel.High){
                setProgressBar(progressBar, "orange-bar", .8);

            }else if(priorityLevel == PriorityLevel.Critical){
                setProgressBar(progressBar, "red-bar", 1);
            }else {
                setProgressBar(progressBar, "orange-bar", .5);
            }


            prio = priorityLevel.toString();
            setGraphic(createGrpahics(prio));

        } else {
            setGraphic(null);
        }
    }

    private void setProgressBar( ProgressBar bar, String style, double progress ){
        bar.getStyleClass().removeAll(barColorStyleClasses);
        bar.getStyleClass().add(style);
        bar.setProgress(progress);

    }

    private HBox createGrpahics(String Str){
        Text text = new Text(Str);
        HBox hBox = new HBox();

        hBox.setPadding(new Insets(3));
        hBox.setSpacing(5);
        hBox.getChildren().addAll(progressBar, text);

        return hBox;
    }


}
