package View.AdminGUI;

import Controller.Controller;
import View.AdminGUI.Listeners.AdminSlidePaneListner;
import View.ToolKit.Screen;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;

/**
 * Created by Didoy on 10/1/2015.
 */
public class AdminSlidePane extends HBox{

    private VBox leftVbox;
    private boolean isOFF;
    private final double prefWidth;
    private Label Management = new Label("Management");
    private Label home = new Label( "Home");
    private Label work = new Label("Work");
    private Label reports = new Label("Reports");
    private Label help = new Label("Help");
    private Label logout = new Label("Logout");

    private StackPane sp = new StackPane();
    private String totalRequest = "";
    private    boolean  isNotificationOut;
    private AdminSlidePaneListner adminSlidePaneListner;

    private Dimension windowScreen = Screen.screen;

    public AdminSlidePane( ){

        prefWidth = windowScreen.getWidth()/5;
        getStylesheets().add("/CSS/AdminSlidepane.css");

        setPrefWidth(windowScreen.getWidth()/5 );

        // sizing width
        home.setPrefWidth(prefWidth-2);
        work.setPrefWidth(prefWidth-2);
        reports.setPrefWidth(prefWidth-2);
        help.setPrefWidth(prefWidth-2);
        logout.setPrefWidth(prefWidth-2);

        // sizing height
        home.setPrefHeight(30);
        work.setPrefHeight(30);
        reports.setPrefHeight(30);
        help.setPrefHeight(30);
        logout.setPrefHeight(30);

        Management.setPrefWidth(prefWidth - 2);
        Management.setPrefHeight(30);
        Management.setAlignment(Pos.CENTER);

        Image arrow = new Image(getClass().getResourceAsStream("/images/leftArrow.png"),40,70,false,true);
        Label slideArrow = new Label();

        slideArrow.getStyleClass().add("label-arrow");
        slideArrow.setGraphic(new ImageView(arrow));

        VBox rightVbox = new VBox();
        rightVbox.getChildren().add(slideArrow);
        rightVbox.setAlignment(Pos.CENTER);

        putNotification(Management);

        leftVbox = new VBox();
        leftVbox.getStyleClass().add("vBox-list");
        leftVbox.setFillWidth(true);
        leftVbox.setAlignment(Pos.TOP_CENTER);
        leftVbox.setPrefWidth(prefWidth);
        leftVbox.getChildren().addAll(home,work,sp,reports, logout);

        addMouseListenerToSlideButton(slideArrow);

         addComponentListener();

        //splitpane
        setPrefWidth(prefWidth * 1.2);
        getChildren().addAll(leftVbox, rightVbox);

    }

    private void addComponentListener(){
        isNotificationOut = false;

        Management.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (isNotificationOut){
                    adminSlidePaneListner.showManageMent();
                    isNotificationOut = true;

                }else{
                    isNotificationOut = true;
                    sp.getChildren().remove(1,3);
                    adminSlidePaneListner.showManageMent();
                }
            }
        });

        home.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                adminSlidePaneListner.showInitialReport();
            }
        });


        logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                adminSlidePaneListner.Logout();
            }
        });

        work.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                adminSlidePaneListner.ShowWorkPane();

            }
        });

        reports.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                adminSlidePaneListner.showReport();
            }
        });


    }

    public void addAdminSlidePaneListener(AdminSlidePaneListner adminSlidePaneListner){
        this.adminSlidePaneListner = adminSlidePaneListner;
    }

    private void addMouseListenerToSlideButton(Label slideArrow){

        slideArrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isOFF) {
                    showMenu();
                    isOFF = false;
                } else {
                    closeMenu();
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


    }

    private void putNotification(Label lbl){

        int numberOfRequest = getRequestNumber();

            if (numberOfRequest != 0){
                totalRequest = String.valueOf(numberOfRequest);
                addNotification(lbl);
                isNotificationOut = true;
            }else {
                sp.getChildren().add(Management);
            }
    }

    private void addNotification(Label lbl){

        final StackPane sp = new StackPane();
        final Rectangle redRect = new Rectangle();
        final Text text  = new Text();

        text.setText(totalRequest);
        text.setFill(Color.WHITE);

        redRect.setFill(Color.web("#ff4d4d"));
        redRect.setArcWidth(6);
        redRect.setArcHeight(6);

        redRect.setWidth(text.getLayoutBounds().getWidth()+5);
        redRect.setHeight(15);

        sp.setAlignment(Pos.CENTER);

        sp.getChildren().addAll(lbl,redRect,text);
        sp.setMargin(redRect, new Insets(0, 0, 0, 100));
        sp.setMargin(text, new Insets   (0, 0, 0, 100));

        this.sp = sp;
    }

    private int getRequestNumber(){
        int pendingAccounts = Controller.getInstance().getPendingAccounts();
        return pendingAccounts;
    }

    public  void showMenu(){
        leftVbox.setPrefWidth(0);
        getChildren().add(0, leftVbox);

        KeyValue kv1 = new KeyValue(leftVbox.prefWidthProperty(),prefWidth, Interpolator.EASE_BOTH);
        KeyFrame kf1 = new KeyFrame(Duration.millis(200), kv1);

        KeyValue kv2 = new KeyValue(prefWidthProperty(),prefWidth);
        KeyFrame kf2 = new KeyFrame(Duration.millis(200), kv2);

        Timeline t = new Timeline();

        t.getKeyFrames().addAll(kf1,kf2);

        t.play();
    }
    public  void closeMenu(){

        KeyValue kv1 = new KeyValue(leftVbox.prefWidthProperty(),0,Interpolator.EASE_BOTH);
        KeyFrame kf1 = new KeyFrame(Duration.millis(200), kv1);

        KeyValue kv2 = new KeyValue(prefWidthProperty(),prefWidth/10);
        KeyFrame kf2 = new KeyFrame(Duration.millis(200), kv2);

        Timeline t = new Timeline();

        t.getKeyFrames().addAll(kf1, kf2);

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
