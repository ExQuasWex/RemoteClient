package View.Login;

import Controller.Controller;
import clientModel.SecretQuestion;
import global.SecretDetails;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import utility.Utility;

/**
 * Created by Didoy on 1/31/2016.
 */
public class ForgotPasswordPane extends BorderPane{

    private GridPane gridPane;

    private TextField hint;
    private Label securityLabel, Password;
    private Button retrieveButton;
    private final Controller  ctr = Controller.getInstance();

    public ForgotPasswordPane() {

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);

        hint = new TextField();
        hint.setPrefColumnCount(20);
        hint.setPromptText("Username or Mobile Number");
        securityLabel = new Label("Security Question: ");
        Password = new Label("Password");
        retrieveButton = new Button("Retrieve");
        retrieveButton.getStyleClass().add("forgotButton");

        hint.setAlignment(Pos.CENTER);

        int y = 0;
        gridPane.setConstraints(securityLabel,  0 , y,   1, 1, HPos.CENTER, VPos.CENTER );
        y++;
        gridPane.setConstraints(Password,       0 , y, 1, 1, HPos.CENTER, VPos.CENTER );
        y++;
        gridPane.setConstraints(hint,           0 , y, 1, 1, HPos.CENTER, VPos.CENTER );
        y++;
        gridPane.setConstraints(retrieveButton, 0 , y, 1, 1, HPos.CENTER, VPos.CENTER );


        gridPane.getChildren().addAll(securityLabel, Password, hint, retrieveButton);

        setCenter(gridPane);

        retrieveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                validateHint();
            }
        });

    }

    private void validateHint(){
        String hint1 = hint.getText();
            if (hint1.equals("")){
                Utility.showMessageBox("Please Enter your Username", Alert.AlertType.INFORMATION);
            }else {
                SecretDetails secretDetails = ctr.getSecurityQuestion(hint1);
                        if (secretDetails == null){
                            Utility.showMessageBox("No Password is associated with your Username", Alert.AlertType.INFORMATION);
                        }else {
                            showSecurityQuestion(secretDetails);
                        }
            }
    }

    private void showSecurityQuestion(SecretDetails secretDetails){
            SecretQuestion sq = getSecurityQuestion(secretDetails.getSecretID());
            securityLabel.setText(sq.toString());
            Password.setText("Please provide your answer to your security question, \n" +
                    "\t Note: answer must be case Sensitive");

            retrieveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String ans = hint.getText();

                    if (secretDetails.getSecretAnswer().equals(ans)) {
                        Password.setText("Your password: " +  secretDetails.getPassword());
                    } else {
                        Utility.showMessageBox("The answer you provide is incorrect", Alert.AlertType.INFORMATION);
                    }

                }
            });
    }


    private SecretQuestion getSecurityQuestion(int id){
        if (id == 1){
            return SecretQuestion.PET;
        }
        else if (id == 2){
            return SecretQuestion.TVSHOW;
        }else {
            return SecretQuestion.BOOK;
        }
    }

}
