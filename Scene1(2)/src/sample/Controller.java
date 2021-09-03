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
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Pane myPane;

    @FXML
    private GridPane myGridPane;

    @FXML
    private VBox mainScreen;

    @FXML
    private BorderPane articleContainer;

    @FXML
    private ProgressIndicator myProgressIndicator;

    @FXML
    private Button test;

    @FXML
    private Button page1;

    @FXML
    private Button page2;

    @FXML
    private Button page3;

    @FXML
    private Button page4;

    @FXML
    private Button page5;

    @FXML
    private Button next;

    @FXML
    private Button prev;

    //add ScrollPane to store the newspaper content when finish loading
    private ScrollPane myScrollPane;

    public Controller() {
    }

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
    protected void otherClicked() {

    }

    @FXML
    protected void prevClicked() {

    }

    @FXML
    protected void firstPageClicked() {

    }

    @FXML
    protected void secondPageClicked() {

    }

    @FXML
    protected void thirdPageClicked() {


    }

    @FXML
    protected void fourthPageClicked() {


    }

    @FXML
    protected void fifthPageClicked() {


    }

    @FXML
    protected void nextClicked() {

    }

    @FXML
    private SplitPane splitPane;

    @FXML
    private ImageView image1;

    //Set image fit with the pane
    public void initialize(URL url, ResourceBundle resourceBundle){
        image1.fitWidthProperty().bind(splitPane.widthProperty());
        image1.fitHeightProperty().bind(splitPane.heightProperty().subtract(80));
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
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Layout2.fxml")));
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
