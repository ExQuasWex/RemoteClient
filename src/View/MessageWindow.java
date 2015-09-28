package View;

import Controller.Controller;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Created by Didoy on 8/25/2015.
 * this class is for validation purposes,
 * it popup and validate the data entered
 *
 */
public class MessageWindow {

    private CustomStage stage;
    private Label content, imageLabel;
    private Button yesBtn, noBtn;
    private Scene scene;
    private ProgressIndicator pi;

    private GridPane gridPane;
    private BorderPane borderPane;

    private boolean bol = false;
    private  int num = 0;

    private  Controller controller;

    public MessageWindow() {

        gridPane = new GridPane();
        borderPane = new BorderPane();
        content = new Label("The server is Down would you like to Reconnect? \n " +
                "                        this will take few Minutes");
        imageLabel = new Label();
        pi = new ProgressIndicator();

        yesBtn = new Button("Yes");
        noBtn = new Button("No");
        stage = new CustomStage(30,30,350,150);

        controller = new Controller();

    }


    public  void showValidationInfo(String message, double x, double y, double width) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5));
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label(message);
        label.setPadding(new Insets(2));
        label.setAlignment(Pos.CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);


        label.prefWidthProperty().bind(vBox.widthProperty());
        label.prefHeightProperty().bind(vBox.heightProperty());
        label.setStyle("-fx-background-color: brown");
        label.setTextFill(Color.AZURE);


        Group g = new Group();
        g.getChildren().add(label);
        vBox.getChildren().addAll(g);
        vBox.setVgrow(label,Priority.ALWAYS);

        scene = new Scene(vBox,width,70);
        stage.setWidth(scene.getWidth());
        stage.setHeight(scene.getHeight());
        stage.setScene(scene);
        stage.setX(x - (label.getPrefWidth()/2));
        stage.setY(y);
        stage.fadeShowAnimation();
    }



}




