package sample.controller;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import sample.model.*;
import sample.Main;
import sample.ultilities.Sort;
import sample.ultilities.StopWatch;

public class HomepageController implements Initializable {

    public ImageView logo;
    public VBox mainBox;
    public Button hotnewBt;
    public Button covidBt;
    public Button businessBt;
    public Button politicBt;
    public Button healthBt;
    public Button technologyBt;
    public Button sportBt;
    public Button entertainmentBt;
    public Button worldBt;
    public Button otherBt;
    public BorderPane articleContainer;
    public Hyperlink title1;
    public ImageView outlet1;
    public Hyperlink title2;
    public ImageView outlet2;
    public Hyperlink title3;
    public ImageView outlet3;
    public AnchorPane imagePane1;
    public Text pubDate1 = new Text("hello");
    public HBox pubdateBox;


    private int default_page = 0;
    private final int[] pages = new int[5];

    private Stage stage;

    @FXML
    private TilePane tilePane;

    @FXML
    private Pane myPane;

    @FXML
    private GridPane myGridPane;

    @FXML
    private BorderPane mainScreen;

    @FXML
    private StackPane myStackPane;

    @FXML
    private SplitPane splitpane1;

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

    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView image9;
    @FXML
    private ImageView image10;

    @FXML
    VBox box1;

    @FXML
    private ImageView[] imageList = {image1, image2, image3, image4, image5,
                                    image6, image7, image8, image9, image10};

    //add ScrollPane to store the newspaper content when finish loading
    private ScrollPane myScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StopWatch clock1 = new StopWatch();
        clock1.start();

        Category c1 = null;
        try {
            c1 = new Category();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            c1.setCate("business");
        } catch (IOException e) {}

        int count = 1;

        for ( Article a : c1.getList("business") ) {
            System.out.println(a.getUrl() );

            System.out.println(count + ": " + a.getTitle() + "\n" );//+ a.getKWs());
            //System.out.println(count + ": " + a.getAvt() + "\n");
            //a.getSum();
            //a.getBody();

            count++;
        }
        System.out.print("\n" + "Time consume: " + clock1.getElapsedTime() + " ms" + "\n");
        c1.getNum();
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

    //Blurred and pop up the progress indicator when the button is clicked then move on a new screen
    @FXML
    protected void articleClicked(){
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainBox.setEffect(gaussianBlur);
        setTilePane("Link");
        myStackPane.getChildren().add(tilePane);
    }

    @FXML
    protected void pageClicked(){
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainBox.setEffect(gaussianBlur);
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
                Parent article_page = null;
                try {
                    article_page = FXMLLoader.load(getClass().getResource("article_layout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(article_page));
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
                mainBox.setEffect(null);
                myStackPane.getChildren().removeAll(tilePane);
                ii = 0;
            }
        }
    };


}
