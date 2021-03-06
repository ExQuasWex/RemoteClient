package View.Login;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


/**
 * Created by Didoy on 8/31/2015.
 * this class is intended for Animations of
 * showing and closing the stage.
 *
 * The given stage must setX to 0
 * and setY to the max height of the screen
 */
public class CustomStage extends Stage {

    private double centerX;
    private double centerY;
    private   Rectangle2D screenBound;

    private double finalWidth;
    private double finalHeight;

    private Timeline t1;
    private KeyValue kv;
    private KeyFrame kf1;
    private boolean isPlaying;
    private boolean isClosing;

    public CustomStage(double startingWidth, double startingHeight ,double FinalWidth, double FinalHeigt) {

        this.finalWidth = FinalWidth;
        this.finalHeight = FinalHeigt;
        this.initStyle(StageStyle.UNDECORATED);

        setWidth(startingWidth);
        setHeight(startingHeight);

        screenBound = Screen.getPrimary().getVisualBounds();

        centerX = screenBound.getWidth()/2 - FinalWidth/2;

        centerY = (screenBound.getHeight()/2 - FinalHeigt/2) -100;

        t1 = new Timeline();

    }

    public CustomStage(){

    }

    public void fadeShowAnimation(){
        setOpacity(1.0);
        if (isPlaying){

            if (isClosing){
                close();
                show();
                t1.stop();
                t1.getKeyFrames().removeAll(kf1);
                kv = new KeyValue(this.opacityProperty(),1.0);
                kf1 = new KeyFrame(Duration.millis(3000), kv );
                t1.getKeyFrames().addAll(kf1);
                t1.play();

            }else {
                close();
                show();
                t1.stop();
                t1.playFromStart();
            }

        }else{
            isPlaying = true;
            isClosing = false;
            t1.getKeyFrames().removeAll(kf1);
            show();
            kv = new KeyValue(this.opacityProperty(),1.0);
            kf1 = new KeyFrame(Duration.millis(3000), kv );
            t1.getKeyFrames().add(kf1);
            t1.play();
        }

        t1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    fadeCloseAnimation();
                }
            });

    }
    private void fadeCloseAnimation(){
        isPlaying = true;
        isClosing = true;
        t1.stop();
        t1.getKeyFrames().removeAll(kf1);
        kv = new KeyValue(this.opacityProperty(),0);
        kf1 = new KeyFrame(Duration.millis(1000), kv );
        t1.getKeyFrames().add(kf1);
        t1.play();



        t1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isPlaying = false;
                isClosing = false;
                close();

            }
        });
    }

    public void clseWithAnimation(){
        show();

        Timeline t1 = new Timeline();

        // add Amimation on position of the Stage
        Interpolator tp = Interpolator.EASE_BOTH;
        KeyValue kv = new KeyValue(getXProperty(),0,tp);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);

        Interpolator tp1 = Interpolator.EASE_BOTH;
        KeyValue kv1 = new KeyValue(getYProperty(),getScreenMaxHeight(),tp1);
        KeyFrame kf1 = new KeyFrame(Duration.millis(500), kv1);

        // shrink the stage
        Interpolator tp2 = Interpolator.EASE_BOTH;
        KeyValue kv2 = new KeyValue(getScaleXProperty(), 30,tp2);
        KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);

        Interpolator tp3 = Interpolator.EASE_BOTH;
        KeyValue kv3 = new KeyValue(getScaleYProperty(),30,tp3);
        KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);

        t1.getKeyFrames().addAll(kf, kf1, kf2,kf3);
        t1.play();

        t1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    public void showWithAnimation(){

        show();

        Timeline t1 = new Timeline();

        // add Amimation on position of the Stage
        Interpolator tp = Interpolator.EASE_BOTH;
        KeyValue kv = new KeyValue(getXProperty(),centerX,tp);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);

        Interpolator tp1 = Interpolator.EASE_BOTH;
        KeyValue kv1 = new KeyValue(getYProperty(),centerY,tp1);
        KeyFrame kf1 = new KeyFrame(Duration.millis(500), kv1);

        // add Animation on scaling of Stage
        Interpolator tp2 = Interpolator.EASE_BOTH;
        KeyValue kv2 = new KeyValue(getScaleXProperty(),finalWidth,tp2);
        KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);

        Interpolator tp3 = Interpolator.EASE_BOTH;
        KeyValue kv3 = new KeyValue(getScaleYProperty(),finalHeight,tp3);
        KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);

        t1.getKeyFrames().addAll(kf, kf1, kf2,kf3);
        t1.play();


    }

    public double getScreenMaxHeight(){
        return screenBound.getHeight();
    }

    public SimpleDoubleProperty getXProperty(){
        return xlocationproperty;
    }
    public SimpleDoubleProperty getYProperty(){
        return ylocationproperty;
    }
    public SimpleDoubleProperty getScaleXProperty(){
        return scalexproperty;
    }
    public SimpleDoubleProperty getScaleYProperty(){
        return scaleyproperty;
    }


    SimpleDoubleProperty scaleyproperty = new SimpleDoubleProperty(){
        @Override
        public void set(double newValue) {
            setHeight(newValue);
        }

        @Override
        public double get() {
            return getHeight();
        }
    };




    SimpleDoubleProperty scalexproperty = new SimpleDoubleProperty(){
        @Override
        public void set(double newValue) {
            setWidth(newValue);
        }

        @Override
        public double get() {
            return getWidth();
        }
    };



    SimpleDoubleProperty xlocationproperty = new SimpleDoubleProperty(){
        @Override
        public void set(double newValue) {
            setX(newValue);
        }

        @Override
        public double get() {
            return getX();
        }
    };

    SimpleDoubleProperty ylocationproperty = new SimpleDoubleProperty(){
        @Override
        public void set(double newValue) {
            setY(newValue);
        }

        @Override
        public double get() {
            return getY();
        }
    };


    public void ShowConnectingWindow(BorderPane root){

        GridPane grid = new GridPane();

        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(190);

        Label connectingL = new Label("Connecting to Server. . .");

        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        t.setAutoReverse(true);
        KeyValue kv = new KeyValue(connectingL.opacityProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);

        KeyValue kv2 = new KeyValue(connectingL.opacityProperty(), 1.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(2000), kv2);
        t.getKeyFrames().addAll(kf, kf2);
        t.play();

        grid.setConstraints(connectingL, 0, 0, 1,1, HPos.CENTER, VPos.CENTER);
        grid.setConstraints(pb, 0, 1, 1,1, HPos.CENTER, VPos.CENTER);

        grid.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(connectingL,pb);

        root.setDisable(true);
        root.setCenter(grid);

    }

}
