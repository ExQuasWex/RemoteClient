package View.Login.Menu;

import View.Login.ForgotPassword.IpConfiguration;
import View.Login.Listeners.TopPaneListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by Didoy on 1/29/2016.
 */
public class LoginHeaderMenu extends GridPane {

    private ToggleButton loginToggle,registerToggle, forgotToggle;
    private ToggleGroup toggleGroup;
    private   Label ipSettingLabel;

    private IpConfiguration ipConfiguration;

    private TopPaneListener topPaneListener;

    public LoginHeaderMenu(){

        toggleGroup = new ToggleGroup();
        loginToggle = new ToggleButton("Login");
        registerToggle = new ToggleButton("Register");
        forgotToggle = new         ToggleButton("Forgot");

        loginToggle.setToggleGroup(toggleGroup);
        registerToggle.setToggleGroup(toggleGroup);
        forgotToggle.setToggleGroup(toggleGroup);
        loginToggle.setSelected(true);

        ipConfiguration = new IpConfiguration();

        ipSettingLabel = new Label();
        Image image = new Image(getClass().getResourceAsStream("/images/settingIcon2.png"), 32, 32, true, true );
        ipSettingLabel.setGraphic(new ImageView(image));


        // top borderPane
        setAlignment(Pos.CENTER);
        setHgap(10);
        setPrefWidth(360);
        getStyleClass().add("hboxToggleGroup");
        setHgrow(forgotToggle, Priority.ALWAYS);
        setHgrow(loginToggle, Priority.ALWAYS);
        setHgrow(registerToggle, Priority.ALWAYS);

        loginToggle.setPrefWidth(100);
        registerToggle.setPrefWidth(100 );
        forgotToggle.setPrefWidth(100 );

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(0, 170, 0, 170));

        hBox.getChildren().addAll(loginToggle, registerToggle, forgotToggle);

        setConstraints(hBox,           1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        setConstraints(ipSettingLabel, 2, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

        setMargin(hBox, new Insets(0, -335, 0, 0));
        setMargin(ipSettingLabel, new Insets(0, 0, 0, 300));

        getChildren().addAll(hBox, ipSettingLabel);


        loginToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                topPaneListener.setLogin();
            }
        });
        registerToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                topPaneListener.setRegister();

            }
        });

        forgotToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                topPaneListener.setForgot();
            }
        });

        ipSettingLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ipConfiguration.showWindow();
            }
        });

    }

    public void addTopPaneListener(TopPaneListener topPaneListener){
        this.topPaneListener = topPaneListener;
    }


    public void Disable(boolean isDisable){
            loginToggle.setDisable(isDisable);
            registerToggle.setDisable(isDisable);
            forgotToggle.setDisable(isDisable);
            ipSettingLabel.setDisable(false);
    }

}
