package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class Controller2 implements Initializable{

    private Stage stage;
    private Parent root;
    private Scene scene;


    @FXML
    private Text text;

    @FXML
    private VBox mainScreen;

    @FXML
    private BorderPane articleContainer;

    @FXML
    private ProgressIndicator myProgressIndicator;

    @FXML
    private Button test;

    //add ScrollPane to store the newspaper content when finish loading
    private ScrollPane myScrollPane;

    @FXML
    protected void hotnewClicked() {

    }

    @FXML
    protected void covidClick() {

    }

    @FXML
    protected void politicClicked() {

    }

    @FXML
    protected void businessClicked() {

    }

    @FXML
    protected void techClicked() {

    }

    @FXML
    protected void healthClicked() {

    }

    @FXML
    protected void sportsClicked() {

    }

    @FXML
    protected void entertainmentClicked() {

    }

    @FXML
    protected void worldClicked() {

    }

    @FXML
    private VBox vBox;

    @FXML
    private WebView webView;

    //Set video
    public void initialize(URL url, ResourceBundle resourceBundle){
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://znews-mcloud-bf-s2.zadn.vn/VJsUNap_P-o/7ea4b57414aafef4a7bb/aef9fd1f58d2b28cebc3/720/58044cd8748e9dd0c49f.mp4?authen=exp=1630743859~acl=/VJsUNap_P-o/*~hmac=ad11d4e1e668c78c86f55023dfbc0211");
    }

    //TilePane to store the proogress bar
    private TilePane tilePane;

    public void setTilePane(String name) {
        myProgressIndicator = new ProgressIndicator();
        test = new Button("Increase");

        if(name.equals("Link"))
            test.setOnAction(linkClick);
        else
            test.setOnAction(pageClick);

        tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER);
        tilePane.getChildren().addAll(myProgressIndicator, test);
    }

    @FXML
    private StackPane myStackPane;

    //Blurscreen and pop up the progress indicator when the button is cliked then move on a new screen
    @FXML
    protected void articleClicked(){
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainScreen.setEffect(gaussianBlur);
        setTilePane("Link");
        myStackPane.getChildren().add(tilePane);
    }

    @FXML
    protected void pageClicked(){
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainScreen.setEffect(gaussianBlur);
        setTilePane("Page");
        myStackPane.getChildren().add(tilePane);
    }

    //Progress Indicator function
    @FXML
    EventHandler<ActionEvent> linkClick = new EventHandler<ActionEvent>() {
        double ii = 0;
        public void handle(ActionEvent event) {
            ii += 0.1;
            myProgressIndicator.setProgress(ii);

            if(ii > 1.1){
                //Change scene function
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    };

    @FXML
    EventHandler<ActionEvent> pageClick = new EventHandler<ActionEvent>() {
        double ii = 0;

        public void handle(ActionEvent e) {
            // set progress to different level of progress indicator
            ii += 0.1;
            myProgressIndicator.setProgress(ii);

            if (ii > 1.1) {
                mainScreen.setEffect(null);
                myStackPane.getChildren().removeAll(tilePane);
                ii = 0;
            }
        }
    };

}
