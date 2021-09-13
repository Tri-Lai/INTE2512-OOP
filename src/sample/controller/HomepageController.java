/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2021B
  Assessment: Final Project
  Created  date: 12/07/2021
  Author: Lai Nghiep Tri - s3799602
          Thieu Tran Tri Thuc - s3870730
          Nguyen Hoang Long - S3878451
          Pham Trinh Hoang Long - s3879366
  Last modified date: 18/09/2021
  Acknowledgement: Canvas lecture slides, W3schools, Geeksforgeeks, Oracle Documentation, javatpoint
*/

package sample.controller;

import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import sample.model.Article;
import sample.model.Category;
import sample.ultilities.StopWatch;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomepageController implements Initializable{

    //--Variable declarations--
    public Article article;
    public String linkArticle = "";
    ArrayList<Article> articles;


    //------------FXML Attributes------------
    private Scene scene;
    private Parent root;
    ProgressIndicator myProgressIndicator = new ProgressIndicator(0);


    //--Pane initialisation--
    @FXML
    private Pane myPane;

    //TilePane to store the proogress bar
    private TilePane tilePane;

    @FXML
    private GridPane myGridPane;

    @FXML
    //add ScrollPane to store the newspaper content when finish loading
    private ScrollPane myScrollPane;

    @FXML
    private VBox mainScreen;

    @FXML
    private BorderPane articleContainer;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane myStackPane;

    //--Page number buttons--
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

    //--Category buttons--
    @FXML
    private Button hotnew;

    @FXML
    private Button covid;

    @FXML
    private Button business;

    @FXML
    private Button politic;

    @FXML
    private Button health;

    @FXML
    private Button technology;

    @FXML
    private Button sports;

    @FXML
    private Button entertainment;

    @FXML
    private Button world;

    @FXML
    private Button others;

    //--category handler--
    @FXML
    protected void hotnewClicked() {
        loadArticles("new");
    }

    @FXML
    protected void covidClick() {
        loadArticles("covid");
    }

    @FXML
    protected void politicClicked() {
        loadArticles("politic");
    }

    @FXML
    protected void businessClicked() {
        loadArticles("business");
    }

    @FXML
    protected void techClicked() {
        loadArticles("tech");
    }

    @FXML
    protected void healthClicked() {
        loadArticles("health");
    }

    @FXML
    protected void sportsClicked() {
        loadArticles("sport");
    }

    @FXML
    protected void entertainmentClicked() {
        loadArticles("entertain");
    }

    @FXML
    protected void worldClicked() {
        loadArticles("world");
    }

    @FXML
    protected void otherClicked() {
        loadArticles("other");
    }

    //--Loading article--
    /**
     * Overloading function for loading the next 10 articles when changing page number
     *
     * @param index: page number
     */
    private void loadArticles(int index) {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //Array of image
        ImageView[] Images = {image1, image2, image3, image4, image5, image6, image7, image8, image9, image10};

        //Array of hyperlinks for titles
        Hyperlink[] Title = {title1, title2, title3, title4, title5, title6, title7, title8, title9, title10 };

        //Array of Source
        ImageView[] Source = {src1, src2, src3, src4, src5, src6, src7, src8, src9, src10};

        //Array of Date and Time
        Text[] DateTime = {dateTime1, dateTime2, dateTime3, dateTime4, dateTime5, dateTime6, dateTime7, dateTime8, dateTime9,dateTime10 };

        es.execute(() -> {
            //Loop for avatar article
            for (int i = 0; i< Images.length; i++){
                String avtLink = articles.get(index+i).getAvt();
                setIMG(Images[i], (avtLink.equals("") ? "sample/view/image/unavailable_Avatar.jpg" : avtLink));
            }
        });

        es.execute(() -> {
            //Place title
            for (int i =0; i< Title.length; i++){
                setTitle(Title[i], articles.get(index+i).getTitle());
            }
        });

        es.execute(() -> {
            //Set the source outlet image for each article
            for (int i =0; i< Source.length; i++){
                if (articles.get(index+i).getSource().equals("vnexpress"))
                    setIMG(Source[i], "sample/view/image/vnexpress_logo.png");
                else if (articles.get(index+i).getSource().equals("tuoitre"))
                    setIMG(Source[i], "sample/view/image/tuoitre_logo.png");
                else if (articles.get(index+i).getSource().equals("nhandan"))
                    setIMG(Source[i], "sample/view/image/nhandan_logo.png");
                else if (articles.get(index+i).getSource().equals("zingnews"))
                    setIMG(Source[i], "sample/view/image/zingnews_logo.png");
                else if (articles.get(index+i).getSource().equals("thanhnien"))
                    setIMG(Source[i], "sample/view/image/thanhnien_logo.png");
            }
        });

        es.execute(() -> {
            //Loop for date time
            for (int i = 0; i < DateTime.length; i++){
                setDateTime(DateTime[i], articles.get(index+i).getPubDay());
            }
        });

        //Stop getting task
        es.shutdown();

        //wait to all task completed
        while ( !es.isTerminated() ) {
            setTilePane("Page");
        }
    }

    /**
     * This function will run by default when the user open the application to load the first 10 articles of certain
     * category.
     *
     * @param index: page number
     */
    @FXML
    protected void defaultPage(int index) {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //Array of image
        ImageView[] Images = {image1, image2, image3, image4, image5, image6, image7, image8, image9, image10};

        //Array of hyperlinks for titles
        Hyperlink[] Title = {title1, title2, title3, title4, title5, title6, title7, title8, title9, title10 };

        //Array of Source
        ImageView[] Source = {src1, src2, src3, src4, src5, src6, src7, src8, src9, src10};

        //Array of Date and Time
        Text[] DateTime = {dateTime1, dateTime2, dateTime3, dateTime4, dateTime5, dateTime6, dateTime7, dateTime8, dateTime9,dateTime10 };

        es.execute(() -> {
            //Loop for avatar article
            for (int i = 0; i< Images.length; i++){
                String avtLink = articles.get(index+i).getAvt();
                setIMG(Images[i], (avtLink.equals("") ? "sample/view/image/unavailable_Avatar.jpg" : avtLink));
            }
        });

        es.execute(() -> {
            //Place title
            for (int i =0; i< Title.length; i++){
                setTitle(Title[i], articles.get(index+i).getTitle());
            }
        });

        es.execute(() -> {
            //Set the source outlet image for each article
            for (int i =0; i< Source.length; i++){
                if (articles.get(index+i).getSource().equals("vnexpress"))
                    setIMG(Source[i], "sample/view/image/vnexpress_logo.png");
                else if (articles.get(index+i).getSource().equals("tuoitre"))
                    setIMG(Source[i], "sample/view/image/tuoitre_logo.png");
                else if (articles.get(index+i).getSource().equals("nhandan"))
                    setIMG(Source[i], "sample/view/image/nhandan_logo.png");
                else if (articles.get(index+i).getSource().equals("zingnews"))
                    setIMG(Source[i], "sample/view/image/zingnews_logo.png");
                else if (articles.get(index+i).getSource().equals("thanhnien"))
                    setIMG(Source[i], "sample/view/image/thanhnien_logo.png");
            }
        });

        es.execute(() -> {
            //Loop for date time
            for (int i = 0; i < DateTime.length; i++){
                setDateTime(DateTime[i], articles.get(index+i).getPubDay());
            }
        });

        //Stop getting task
        es.shutdown();
    }

    //--Page number handler--

    /**
     * Loading the first 10 articles in the category list
     *
     * @param index: page number
     */
    @FXML
    protected void firstPageClicked(int index) {
        // Using the Platform.runLater() to avoid FX application thread; currentThread=JavaFX Application Thread error
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadArticles(index);
            }
        });
    }

    /**
     * Loading the next article 10th to 19th (10 articles) in the category list
     *
     * @param index: page number
     */
    @FXML
    protected void secondPageClicked(int index) {
        // Using the Platform.runLater() to avoid FX application thread; currentThread=JavaFX Application Thread error
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadArticles(index);
            }
        });
    }

    /**
     * Loading the next article 20th to 29th (10 articles) in the category list
     *
     * @param index: page number
     */
    @FXML
    protected void thirdPageClicked(int index) {
        // Using the Platform.runLater() to avoid FX application thread; currentThread=JavaFX Application Thread error
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadArticles(index);
            }
        });
    }

    /**
     * Loading the next article 30th to 39th (10 articles) in the category list
     *
     * @param index: page number
     */
    @FXML
    protected void fourthPageClicked(int index) {
        Platform.runLater(() -> loadArticles(index));
    }

    /**
     * Loading the next article 40th to 49th (10 articles) in the category list
     *
     * @param index: page number
     */
    @FXML
    protected void fifthPageClicked(int index) {
        Platform.runLater(() -> loadArticles(index));
    }

    //------------Main Screen components------------
    //--Article avatars--
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

    //--Article Link--
    @FXML
    private Hyperlink title1;

    @FXML
    private Hyperlink title2;

    @FXML
    private Hyperlink title3;

    @FXML
    private Hyperlink title4;

    @FXML
    private Hyperlink title5;

    @FXML
    private Hyperlink title6;

    @FXML
    private Hyperlink title7;

    @FXML
    private Hyperlink title8;

    @FXML
    private Hyperlink title9;

    @FXML
    private Hyperlink title10;

    //--News outlets image--
    @FXML
    private ImageView src1;

    @FXML
    private ImageView src2;

    @FXML
    private ImageView src3;

    @FXML
    private ImageView src4;

    @FXML
    private ImageView src5;

    @FXML
    private ImageView src6;

    @FXML
    private ImageView src7;

    @FXML
    private ImageView src8;

    @FXML
    private ImageView src9;

    @FXML
    private ImageView src10;

    //--Published time of articles--
    @FXML
    private Text dateTime1;

    @FXML
    private Text dateTime2;

    @FXML
    private Text dateTime3;

    @FXML
    private Text dateTime4;

    @FXML
    private Text dateTime5;

    @FXML
    private Text dateTime6;

    @FXML
    private Text dateTime7;

    @FXML
    private Text dateTime8;

    @FXML
    private Text dateTime9;

    @FXML
    private Text dateTime10;

    //------------------------------------------------------------------------------------------------------
    /*
     * This initialize function will run firstly at the time the app open.
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadArticles("new");
    }

    /**
     * Setting the category instance and access the news outlets to scrape the articles of desired category. Since the
     * total articles of each category is 50 so the number of articles per outlets need to be scraped is 10 articles.
     *
     * @param category: category user clicked
     */
    private void loadArticles(String category) {
        // Create Category instance and set the category
        Category tech = new Category();
        try {
            tech.setCate(category);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the scraped articles in ascending order based upon their published time
        tech.sort(category);

        // Get the list of sorted articles
        articles = tech.getList(category);

        //Resize the first image
        image1.fitWidthProperty().bind(splitPane.widthProperty());
        image1.fitHeightProperty().bind(splitPane.heightProperty().subtract(80));

        // Loading first page articles as default at the time the application open
        defaultPage(0);

        // Referencing action when click page number
        page1.setOnAction((ActionEvent) -> firstPageClicked(0));
        page2.setOnAction((ActionEvent) -> secondPageClicked(10));
        page3.setOnAction((ActionEvent) -> thirdPageClicked(20));
        page4.setOnAction((ActionEvent) -> fourthPageClicked(30));
        page5.setOnAction((ActionEvent) -> fifthPageClicked(40));
    }

    /**
     * Create a tile pane to store a loading pie
     *
     * @param name:
     */
    public void setTilePane(String name) {

        if(name.equals("Link")) {
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
                if (!inHierarchy(evt.getPickResult().getIntersectedNode(), title1)) {
                    root.requestFocus();
                }
            });
        } else {

        }

        tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER);
        tilePane.getChildren().addAll(myProgressIndicator);
    }

    /**
     * Blur screen and pop up the progress indicator when the button is clicked then move on a new screen
     */
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

    protected void categoryClicked() {
        hotnew.setOnAction((ActionEvent) -> hotnewClicked());
    }

    //Progress Indicator function
    @FXML
    EventHandler<ActionEvent> linkClick = new EventHandler<ActionEvent>() {
        double ii = 0;
        public void handle(ActionEvent event) {

            // Temp link
            Hyperlink hyperlink = (Hyperlink) event.getTarget();

            Article chosenArticle = null;

            if (hyperlink.equals(title1)) {
                chosenArticle = articles.get(0);
            } else if (hyperlink.getText().equals(articles.get(1).getUrl())) {
                chosenArticle = articles.get(1);
            } else if (hyperlink.getText().equals(articles.get(2).getUrl())) {
                chosenArticle = articles.get(2);
            } else if (hyperlink.getText().equals(articles.get(3).getUrl())) {
                chosenArticle = articles.get(3);
            } else if (hyperlink.getText().equals(articles.get(4).getUrl())) {
                chosenArticle = articles.get(4);
            } else if (hyperlink.getText().equals(articles.get(5).getUrl())) {
                chosenArticle = articles.get(5);
            } else if (hyperlink.getText().equals(articles.get(6).getUrl())) {
                chosenArticle = articles.get(6);
            } else if (hyperlink.getText().equals(articles.get(7).getUrl())) {
                chosenArticle = articles.get(7);
            } else if (hyperlink.getText().equals(articles.get(8).getUrl())) {
                chosenArticle = articles.get(8);
            } else if (hyperlink.getText().equals(articles.get(9).getUrl())) {
                chosenArticle = articles.get(9);
            }

            ii += 0.1;
            myProgressIndicator.setProgress(ii);

            try {
                assert chosenArticle != null;
                article = new Article(chosenArticle.getUrl(), chosenArticle.getAvt());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(ii > 1.1){
                //Change scene function
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/article.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    /**
     *
     *
     * @param node
     * @param potentialHierarchyElement
     * @return
     */
    public static boolean inHierarchy(Node node, Node potentialHierarchyElement) {
        if (potentialHierarchyElement == null) {
            return true;
        }
        while (node != null) {
            if (node == potentialHierarchyElement) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }

    //--------------------------------------------Utilities functions------------------------------------------------

    /**
     * Functions set Image avatar of articles in the Homepage layout except the first article.
     *
     * @param image: Avatar ImageView element
     * @param URL: Path link of article thumbnail image
     * @return: ImageView instance
     */
    public ImageView setIMG(ImageView image, String URL){

        Image img = new Image(URL);
        image.setImage(img);

        return image;
    }

    /**
     * Functions set title of articles in the Homepage layout.
     *
     * @param title: HyperLink instance
     * @param titleName: Name of the article
     * @return: Title of the article as the path link
     */
    public Hyperlink setTitle(Hyperlink title, String titleName){
        title.setText(titleName);
        return title;
    }

    /**
     * Functions set published date of articles in the Homepage layout.
     *
     * @param dateTime: Text instance
     * @param URL: Published date of the article
     * @return: Published date as Text instance
     */
    public Text setDateTime(Text dateTime, String URL){
        dateTime.setText(URL);
        return dateTime;
    }

    /**
     * Functions set Image avatar of the first article in the Homepage layout.
     *
     * @param image: Avatar ImageView element
     * @param URL: Path link of article thumbnail image
     * @return: ImageView instance
     */

    public ImageView set1stIMG(ImageView image, String URL){

        Image img = new Image(URL);
        image.setImage(img);

        image.fitWidthProperty().bind(splitPane.widthProperty());
        image.fitHeightProperty().bind(splitPane.heightProperty().subtract(80));

        return image;
    }
}


