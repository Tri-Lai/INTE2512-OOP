package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;

public class Controller {

//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//
//    public void switchScene(ActionEvent event) throws Exception{
//        root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();
//    }


    @FXML
    private BorderPane mainScreen;

    @FXML
    private ProgressIndicator myProgressIndicator;

    @FXML
    private Button test;

    private TilePane tilePane;

    public void setTilePane() {
        myProgressIndicator = new ProgressIndicator();
        test = new Button("Increase");
        test.setOnAction(events);

        tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER);
        tilePane.getChildren().addAll(myProgressIndicator,test);
    }

    @FXML
    private StackPane myStackPane;

    //Blurscreen and pop up the progress indicator when the button is cliked then move on a new screen
    @FXML
    protected void isClicked(){
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainScreen.setEffect(gaussianBlur);
        setTilePane();
        myStackPane.getChildren().add(tilePane);
    }

    //Progress Indicator function
    @FXML
    EventHandler<ActionEvent> events = new EventHandler<ActionEvent>() {
        double ii = 0;
        public void handle(ActionEvent e)
        {
            // set progress to different level of progress indicator
            ii += 0.1;
            myProgressIndicator.setProgress(ii);
            if(ii > 1.1 ) {
                mainScreen.setEffect(null);
                myStackPane.getChildren().removeAll(tilePane);
                ii = 0;
//                switchScene();
            }
        }
    };

}
