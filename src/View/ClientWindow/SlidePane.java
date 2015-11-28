package View.ClientWindow;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Created by Didoy on 10/1/2015.
 */
public class SlidePane extends HBox{

    private VBox leftVbox;
    private boolean isOFF;
    private final double prefWidth;

    public SlidePane(double prefW){
        getStylesheets().add("/CSS/Slidepane.css");
        prefWidth = prefW/2;
        setPrefWidth(prefWidth * 1.2);

        Label accountinfo = new Label( "Account");
        Label form = new Label("Form");
        Label help = new Label("Help");
        Label logout = new Label("Logout");

        // set the width of textfield
        accountinfo.setPrefWidth(prefWidth-4);
        form.setPrefWidth(prefWidth-4);
        help.setPrefWidth(prefWidth-4);
        logout.setPrefWidth(prefWidth-4);

        // set the heights of texfields
        accountinfo.setPrefHeight(30);
        form.setPrefHeight(30);
        help.setPrefHeight(30);
        logout.setPrefHeight(30);

        // set the images of textfields
        accountinfo.getStyleClass().add("AccountTextField");


        leftVbox = new VBox();
        leftVbox.getStyleClass().add("vBox-list");
        leftVbox.setFillWidth(true);
        leftVbox.setAlignment(Pos.TOP_CENTER);
        leftVbox.setPrefWidth(prefWidth);
        leftVbox.getChildren().addAll(accountinfo,form,help,logout);

        Image arrow = new Image(getClass().getResourceAsStream("/images/leftArrow.png"),60,80,false,true);
        Label slideB = new Label();
        slideB.setGraphic(new ImageView(arrow));

        VBox rightVbox = new VBox();
        rightVbox.getChildren().add(slideB);
        rightVbox.setAlignment(Pos.CENTER);


            slideB.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (isOFF){
                        animateOn();
                        isOFF = false;
                    }else {
                        animateOff();
                        isOFF = true;
                    }
                }
            });

            accountinfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ClientWindow.getInstance().showAccount();
                }
            });

            form.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ClientWindow.getInstance().showFamilyForm();
                }
            });

            logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ClientWindow.getInstance().Logout();
                }
            });



        //splitpane
        getChildren().addAll(leftVbox, rightVbox);
        setStyle("-fx-border-color: #585858");

    }


    public  void animateOn(){
        getChildren().add(0,leftVbox);
        KeyValue kv = new KeyValue(leftVbox.prefWidthProperty(), prefWidth);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);

        KeyValue kv2 = new KeyValue(prefWidthProperty(),prefWidth * 1.2);
        KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);

        Timeline t = new Timeline();

        t.getKeyFrames().addAll(kf,kf2);
        t.getKeyFrames().add(kf);

        t.play();
    }
    public  void animateOff(){
        KeyValue kv = new KeyValue(leftVbox.prefWidthProperty(),0);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        Timeline t = new Timeline();

        KeyValue kv2 = new KeyValue(prefWidthProperty(),prefWidth/10);
        KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);

        t.getKeyFrames().addAll(kf, kf2);

        t.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getChildren().remove(leftVbox);
            }
        });

        t.play();
    }

    public HBox getInstance(){
        return  this;
    }



}
