package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import utility.Utility;

import java.io.File;
import java.io.IOException;

/**
 * Created by reiner on 3/5/2016.
 */
public class ExportDatabasePane extends BorderPane {

    DirectoryChooser directoryChooser = new DirectoryChooser();
    private String dbPath = "C:\\Users\\reiner\\DummyDB";
    private Text titleText = new Text("Choose Directory to Export Database");
    private Button directoryBtn = new Button("Select Directory");
    private Button export = new Button("Export Database");

    private TextField diretoryField = new TextField();
    private String path = "";

    public ExportDatabasePane(Stage stage) {

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setFillWidth(false);
        vBox.setSpacing(20);

        diretoryField.setDisable(true);
        diretoryField.setPrefWidth(300);

        directoryBtn.setPrefWidth(150);
        export.setPrefWidth(150);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(directoryBtn, export);

        vBox.getChildren().addAll(titleText,diretoryField,hBox);

        directoryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = directoryChooser.showDialog(stage);
                if (file.exists() && file.isDirectory()){
                    path =    file.getPath();
                    diretoryField.setText(path);

                }else {
                    Utility.showMessageBox("Directory Path not found", Alert.AlertType.ERROR);
                    path = "";
                    diretoryField.setText("");
                }
            }
        });

        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExportDatabase();
            }
        });

        setCenter(vBox);

    }

    private void ExportDatabase(){
            if (path.equals("")){
                Utility.showMessageBox("Please Select Directory path", Alert.AlertType.ERROR);
            }else {
             boolean confirmed =    Utility.showConfirmationMessage("Continuing this operation required to shutdown the server, would you still like to continue?", Alert.AlertType.CONFIRMATION);

                    if (confirmed){

                        //Controller.getInstance().shutDownServer();

                        File source = new File(dbPath);
                        File dest = new File(path);
                        try {
                            FileUtils.copyDirectory(source, dest);
                            FileUtils.copyDirectoryToDirectory(source, dest);
                            Utility.showMessageBox("Successfully copied Database, you can now Turn On the Server", Alert.AlertType.INFORMATION);

                        } catch (IOException e) {
                            Utility.showMessageBox("Unable to backup Database", Alert.AlertType.ERROR);
                            e.printStackTrace();
                        }

                    }
            }
    }
}
