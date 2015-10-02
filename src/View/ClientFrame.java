package View;

import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

import javax.tools.Tool;
import java.awt.*;

/**
 * Created by Didoy on 9/30/2015.
 */
public class ClientFrame {

    private static CustomStage clientStage ;
    private static ClientFrame mainframe = new ClientFrame();

    private ClientFrame(){

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        clientStage =  new CustomStage(30,30,screen.getWidth(),screen.getHeight());
        clientStage.initStyle(StageStyle.DECORATED);
        clientStage.setMaxWidth(screen.getWidth());
        clientStage.setMaxHeight(screen.getHeight());

        clientStage.setHeight(clientStage.getMaxHeight());
        clientStage.setWidth(clientStage.getMaxWidth());

        SlidePane sp = new SlidePane(500);

        BorderPane root = new BorderPane();
        root.setCenter(new Label("Center"));
        root.setLeft(sp);

        Scene scene = new Scene(root);
        clientStage.setScene(scene);
        clientStage.centerOnScreen();
        clientStage.show();
    }

    public static ClientFrame getInstance(){
        return mainframe;
    }
    public void showClient(){
        clientStage.showWithAnimation();
    }


}
