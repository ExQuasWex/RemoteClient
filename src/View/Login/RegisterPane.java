package View.Login;

import Controller.Controller;
import View.Login.Listeners.RegisterPaneListeners;
import clientModel.SecretQuestion;
import clientModel.StaffRegister;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.Utility;

import java.util.regex.Pattern;

/**
 * Created by Didoy on 1/29/2016.
 */
public class RegisterPane extends VBox {

    private  Controller ctr = Controller.getInstance();

    private TextField  contactField, nameField, homeAdress, userName,  secretAns;
    private PasswordField password,  confirmPass;
    private RadioButton tbMale, tbFemale;
    private Label statusLabel;
    private MessageWindow messageBox;

    private  Button save;
    private  ComboBox secretQ;
    private  ToggleGroup tg;

    private RegisterPaneListeners registerPaneListeners;

    private SecretQuestion secretQuestion;
    public RegisterPane(){

        messageBox = MessageWindow.getInstance();
        contactField = new TextField();

        secretQ = new ComboBox();

        setUpRegister();

        // handle Save Button
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Register();

            }
        });

    }
    public void  setUpRegister(){

        // this is for register layout
        setAlignment(Pos.CENTER);
        setFillWidth(false);
        setSpacing(5);

         HBox hBox = new HBox();
         tg = new ToggleGroup();

         statusLabel = new Label();
         nameField = new TextField();
         contactField = new TextField();
         homeAdress = new TextField();
         userName = new TextField();
         password = new PasswordField();
         confirmPass = new PasswordField();
         tbMale = new RadioButton("Male");
         tbFemale = new RadioButton("FeMale");
         save = new Button("Save");
         ComboBox secretQ = new ComboBox();
         secretQ.setPromptText("Secret Question");
         secretAns = new TextField();

        tbMale.setToggleGroup(tg);
        tbFemale.setToggleGroup(tg);
        tg.selectToggle(tbMale);

        // Sizes
        save.setPrefWidth(90);
        nameField.setPrefWidth(240);
        userName.setPrefWidth(240);
        contactField.setPrefWidth(240);
        homeAdress.setPrefWidth(240);
        password.setPrefWidth(240);
        confirmPass.setPrefWidth(240);
        statusLabel.setPrefWidth(240);
        secretQ.setPrefWidth(240);
        secretAns.setPrefWidth(240);

        // adding elements to combo Box
        secretQ.getItems().setAll(SecretQuestion.values());

        // Text Orientation
        statusLabel.setAlignment(Pos.CENTER);
        nameField.setAlignment(Pos.CENTER);
        userName.setAlignment(Pos.CENTER);
        contactField.setAlignment(Pos.CENTER);
        homeAdress.setAlignment(Pos.CENTER);
        password.setAlignment(Pos.CENTER);
        confirmPass.setAlignment(Pos.CENTER);
        secretAns.setAlignment(Pos.CENTER);

        // set prompt
        nameField.setPromptText("Enter Full Name");
        userName.setPromptText("Username for Log In");
        contactField.setPromptText("Mobile Number");
        homeAdress.setPromptText("Current Home Address");
        password.setPromptText("Password");
        confirmPass.setPromptText("Confirm Password");
        secretAns.setPromptText("Your secret answer");

        // set The radioButton
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(2));
        hBox.getChildren().addAll(tbMale,tbFemale);

       setPadding(new Insets(5, 5, 0, 5));

       getChildren().addAll(nameField,userName, contactField, homeAdress,secretQ, secretAns,
                password,confirmPass,hBox, save);


        secretQ.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secretQuestion = (SecretQuestion) secretQ.getSelectionModel().getSelectedItem();
                System.out.println(secretQuestion );
            }
        });

    }

    private void Register(){

        if (!ctr.isServerConnected()){

            registerPaneListeners.setUpLoginWindowDisconnected();

        }else{

            String name = nameField.getText().trim();
            String username = userName.getText().trim();
            String contact = contactField.getText().trim();
            String address = homeAdress.getText().trim();
            String pass  = password.getText().trim();
            String cpass = confirmPass.getText().trim();
            String secretAnswer = secretAns.getText().trim();
            String gender = null;

            if (tg.getSelectedToggle() == tbFemale){
                gender = "Female";
            }else {
                gender = "Male";
            }

            // format contact
            contact = contact.replaceFirst("(\\d{4})(\\d{3})(\\d{4})","$1-$2-$3");
            contactField.setText(contact);

            int secretID = 0;


            if (secretQuestion.equals(SecretQuestion.PET)){
                secretID = 1;
                secretQuestion = SecretQuestion.PET;
                System.out.println("Pet");
            }else if (secretQuestion.equals(SecretQuestion.TVSHOW)){
                secretID = 2;
                secretQuestion = SecretQuestion.TVSHOW;
            }else if (secretQuestion.equals(SecretQuestion.BOOK)){
                secretID = 3;
                secretQuestion = SecretQuestion.BOOK;
            }

            StaffRegister staffRegister = new StaffRegister(name,username,contact,address,secretID,secretQuestion,
                    secretAnswer,gender,pass,cpass);

            if (validate(staffRegister)){
                boolean isRegistered;
                System.out.println("all registration is now validated");

                isRegistered =  registerPaneListeners.Register(staffRegister);

                //

                if (isRegistered){

                    Utility.showMessageBox("Your account is succesfully Created", Alert.AlertType.INFORMATION );

                    nameField.setText("");
                    userName.setText("");
                    homeAdress.setText("");
                    password.setText("");
                    contactField.setText("");
                    confirmPass.setText("");
                    homeAdress.setText("");
                    secretAns.setText("");

                }else{
                    Utility.showMessageBox("Internal server Error", Alert.AlertType.ERROR);
                }
            }
        }

    }


    public  boolean validate(StaffRegister staffReg){

        boolean isValidated = false;

        String name = staffReg.getName();
        String username = staffReg.getUsername();
        String contact = staffReg.getContact();
        String address = staffReg.getAddress();
        String pass  = staffReg.getPassword();
        String cpass = staffReg.getComfirmpass();
        SecretQuestion securityQ = staffReg.getSq();
        String secretAnswer = staffReg.getSecretAnswer();

        //// NAME VALIDATION
        if (name.equals(null) || name.equals("")) {
            Utility.showMessageBox("The name you entered is empty", Alert.AlertType.INFORMATION);
            isValidated = false;
        }else if (name.length()<4 || name.length()>70){
            Utility.showMessageBox("The name must must contain atleast 4 to 30 characters", Alert.AlertType.INFORMATION);

        }
        else if (!Pattern.matches("^[a-zA-Z\\s]+[a-zA-Z\\s]", name)){
            Utility.showMessageBox("The name should not contain any digits or Special characters.", Alert.AlertType.INFORMATION);
            isValidated = false;
        }
        // USERNAME VALIDATION
        else if (username.equals("")|| username.equals(null)){
            Utility.showMessageBox("The username you entered is empty", Alert.AlertType.INFORMATION);
            isValidated = false;
        }

        else  if (username.length() <6 || username.length()> 10){
            Utility.showMessageBox("Username must contain atleast 6 to 10 characters ", Alert.AlertType.INFORMATION);
            isValidated = false;
        }

        else  if (!Pattern.matches("^[a-zA-Z0-9_-]{6,12}$+", username)){
            Utility.showMessageBox("Username must not contain any special character", Alert.AlertType.INFORMATION);
            isValidated = false;
        }
        else if (ctr.getUsername(username)){
            Utility.showMessageBox("Username is already taken ", Alert.AlertType.INFORMATION);
            isValidated = false;
        }

        // CONTACT VALIDATION
        else if (contact.equals("")|| contact.equals(null)){
            Utility.showMessageBox("The Contact you entered is empty", Alert.AlertType.INFORMATION);
            isValidated = false;
        }
        else if (!Pattern.matches("^[\\d\\-]{13}$+",contact)) {
            Utility.showMessageBox("Invalid Contact Number", Alert.AlertType.INFORMATION);
            isValidated = false;
        }

        // ADDRESS VALIDATION
        else if(address.equals("") || address.equals(null)){
            Utility.showMessageBox("You have empty Address", Alert.AlertType.INFORMATION);
            isValidated = false;
        }
        else if (!Pattern.matches("^[a-zA-Z0-9\\.\\-\\s]+", address)) {
            Utility.showMessageBox("Please don't add any special symbols except(. or -)", Alert.AlertType.INFORMATION);
            isValidated = false;

            // SECURITY QUESTION VALIDATION
        }else if (securityQ == null){
            Utility.showMessageBox("Please add Secret Question", Alert.AlertType.INFORMATION);
            isValidated = false;
        }
        // SECRET ANSWER VALIDATION
        else if (secretAnswer.equals("")){
            Utility.showMessageBox("Please add Secret Answer for your secret question", Alert.AlertType.INFORMATION);
            isValidated = false;
        }
        else if (!Pattern.matches("^[a-zA-Z0-9\\s_.-]+", secretAnswer)){
            Utility.showMessageBox("Please remove any special characters in your Security Answer", Alert.AlertType.INFORMATION);
            isValidated = false;
        }
        // PASSWORD VALIDATION
        else if (pass.equals("") || cpass.equals("")){
            Utility.showMessageBox("Please add your desired password", Alert.AlertType.INFORMATION);
            isValidated = false;
        }

        else if (!pass.equals(cpass)) {
            Utility.showMessageBox("Password don't match", Alert.AlertType.INFORMATION);
            isValidated = false;
        }

        else if (!Pattern.matches("^[a-zA-Z0-9]{6,15}$+", pass)){
            Utility.showMessageBox("The password you entered is Invalid", Alert.AlertType.INFORMATION);
            isValidated = false;
        }

        else {
            isValidated = true;
        }

        return isValidated;
    }

    public void addRegisterPaneListener(RegisterPaneListeners registerPaneListeners){
        this.registerPaneListeners = registerPaneListeners;
    }

}
