package View;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        setPrefWidth(prefWidth * 1.5);

        Label accountinfo = new Label( "Account");
        Label form = new Label("Form");


        // sizing
        accountinfo.setPrefWidth(prefWidth-2);
        form.setPrefWidth(prefWidth-2);
        accountinfo.setPrefHeight(30);
        form.setPrefHeight(30);


        leftVbox = new VBox();
        leftVbox.getStyleClass().add("vBox-list");
        leftVbox.setFillWidth(true);
        leftVbox.setAlignment(Pos.TOP_CENTER);
        leftVbox.setPrefWidth(prefWidth);
        leftVbox.getChildren().addAll(accountinfo,form);

        Image arrow = new Image(getClass().getResourceAsStream("/images/leftArrow.png"),40,70,false,true);
        Label slideArrow = new Label();
        slideArrow.getStyleClass().add("label-arrow");
        slideArrow.setGraphic(new ImageView(arrow));

        VBox rightVbox = new VBox();
        rightVbox.getChildren().add(slideArrow);
        rightVbox.setAlignment(Pos.CENTER);


            slideArrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (isOFF) {
                        animateOn();
                        isOFF = false;
                    } else {
                        animateOff();

                        isOFF = true;
                    }
                }
            });

        slideArrow.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("enteer");
            }
        });

        slideArrow.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("exit");
            }
        });

        accountinfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("account is clicked");
                    ClientFrame.getInstance().setAccountForm();

            }
        });



        //splitpane
        getChildren().addAll(leftVbox, rightVbox);

    }

    public  void animateOn(){
        getChildren().add(0,leftVbox);
        KeyValue kv = new KeyValue(leftVbox.prefWidthProperty(), prefWidth);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);

        KeyValue kv2 = new KeyValue(prefWidthProperty(),prefWidth * 1.5);
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

        KeyValue kv2 = new KeyValue(prefWidthProperty(),prefWidth/2);
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
