package View.Login;

import View.Login.Listeners.TopPaneListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by Didoy on 1/29/2016.
 */
public class LoginHeaderMenu extends HBox {

    private ToggleButton loginToggle,registerToggle, forgotToggle;
    private ToggleGroup toggleGroup;

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

        // top borderPane
        setPadding(new Insets(1, 20, 0, 20));
        setAlignment(Pos.CENTER);
        setPrefWidth(360);
        getStyleClass().add("hboxToggleGroup");
        setHgrow(forgotToggle, Priority.ALWAYS);
        setHgrow(loginToggle, Priority.ALWAYS);
        setHgrow(registerToggle, Priority.ALWAYS);

        loginToggle.setPrefWidth(getPrefWidth()/5 );
        registerToggle.setPrefWidth(getPrefWidth()/5 );
        forgotToggle.setPrefWidth(getPrefWidth()/5 );

        getChildren().addAll(loginToggle, registerToggle, forgotToggle);


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

    }

    public void addTopPaneListener(TopPaneListener topPaneListener){
        this.topPaneListener = topPaneListener;
    }

}
