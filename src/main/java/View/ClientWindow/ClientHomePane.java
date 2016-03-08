package View.ClientWindow;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by reiner on 3/8/2016.
 */
public class ClientHomePane extends BorderPane {

    public ClientHomePane(String name) {
        Text contentText = new Text("Hi," + name+ " Lets make the job done by clicking some menu from the left :)");
        contentText.setFont(Font.font(30));
        contentText.setSmooth(true);

        setCenter(contentText);

    }
}
