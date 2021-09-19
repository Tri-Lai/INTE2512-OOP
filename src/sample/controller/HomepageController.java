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

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.controller.ArticleController;
import sample.model.Article;
import sample.model.Category;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomepageController implements Initializable {

    //--------------------------------------------Variable declarations------------------------------------------------
    public Article article;
    public String linkArticle = "";
    private ArrayList<Article> articles;
    private int index = 0;
    private int article_index;
    private Category temp_cate;
    // Create a Category object
    Category cate = new Category();

    //--------------------------------------------FXML components------------------------------------------------

    private Scene scene;
    private Parent root;
    private Stage stage;
    ProgressIndicator myProgressIndicator = new ProgressIndicator(0);

    @FXML
    ProgressIndicator loadingPie = new ProgressIndicator(0);

    //--Pane initialisation--
    @FXML
    private Pane myPane;

    //TilePane to store the proogress bar
    private VBox tilePane;

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

    //--------------------------------------------Main functions------------------------------------------------

    /*
     * This initialize function will run firstly at the time the app open.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadArticles("new"); // Newest is a default category
    }

    /**
     * Setting the category instance and access the news outlets to scrape the articles of desired category. Since the
     * total articles of each category is 50 so the number of articles per outlets need to be scraped is 10 articles.
     *
     * @param category: category user clicked
     */
    private void loadArticles(String category) {

        try {
            cate.setCate(category); // Set the category
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the scraped articles in ascending order based upon their published time
        cate.sort(category);

        // Copy the sorted category for further using shuffle in "Others" category
        temp_cate = cate;

        cate.getNum();
        // Get the list of sorted articles
        articles = cate.getList(category);

        //Resize the first image
        image1.fitWidthProperty().bind(splitPane.widthProperty());
        image1.fitHeightProperty().bind(splitPane.heightProperty().subtract(80));

        // Loading first page articles as default at the time the application open or after switching category
        defaultPage();

        // Referencing action when click page number
        page1.setOnAction((ActionEvent) -> firstPageClicked());
        page2.setOnAction((ActionEvent) -> secondPageClicked());
        page3.setOnAction((ActionEvent) -> thirdPageClicked());
        page4.setOnAction((ActionEvent) -> fourthPageClicked());
        page5.setOnAction((ActionEvent) -> fifthPageClicked());
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
     */
    @FXML
    protected void defaultPage() {
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
                String avtLink = articles.get(i).getAvt();
                setIMG(Images[i], (avtLink.equals("") ? "sample/view/image/unavailable_Avatar.jpg" : avtLink));
            }
        });

        es.execute(() -> {
            //Place title
            for (int i =0; i< Title.length; i++){
                setTitle(Title[i], articles.get(i).getTitle());
            }
        });

        es.execute(() -> {
            //Set the source outlet image for each article
            for (int i =0; i< Source.length; i++){
                if (articles.get(i).getSource().equals("vnexpress"))
                    setIMG(Source[i], "sample/view/image/vnexpress_logo.png");
                else if (articles.get(i).getSource().equals("tuoitre"))
                    setIMG(Source[i], "sample/view/image/tuoitre_logo.png");
                else if (articles.get(i).getSource().equals("nhandan"))
                    setIMG(Source[i], "sample/view/image/nhandan_logo.png");
                else if (articles.get(i).getSource().equals("zingnews"))
                    setIMG(Source[i], "sample/view/image/zingnews_logo.png");
                else if (articles.get(i).getSource().equals("thanhnien"))
                    setIMG(Source[i], "sample/view/image/thanhnien_logo.png");
            }
        });

        es.execute(() -> {
            //Loop for date time
            for (int i = 0; i < DateTime.length; i++){
                setDateTime(DateTime[i], articles.get(i).getPubDay());
            }
        });

        //Stop getting task
        es.shutdown();
    }

    //--Page number handler--

    /**
     * Loading the first 10 articles in the category list
     */
    @FXML
    protected void firstPageClicked() {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        es.execute(() -> {
            index = 0;
            loadArticles(index);
        });

        //Stop getting task
        es.shutdown();
    }

    /**
     * Loading the next article 10th to 19th (10 articles) in the category list
     */
    @FXML
    protected void secondPageClicked() {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        es.execute(() -> {
            index = 10;
            loadArticles(index);
        });

        //Stop getting task
        es.shutdown();
    }

    /**
     * Loading the next article 20th to 29th (10 articles) in the category list
     */
    @FXML
    protected void thirdPageClicked() {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        es.execute(() -> {
            index = 20;
            loadArticles(index);
        });

        //Stop getting task
        es.shutdown();
    }

    /**
     * Loading the next article 30th to 39th (10 articles) in the category list
     */
    @FXML
    protected void fourthPageClicked() {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        es.execute(() -> {
            index = 30;
            loadArticles(index);
        });

        //Stop getting task
        es.shutdown();
    }

    /**
     * Loading the next article 40th to 49th (10 articles) in the category list
     */
    @FXML
    protected void fifthPageClicked() {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        es.execute(() -> {
            index = 40;
            loadArticles(index);
        });

        //Stop getting task
        es.shutdown();
    }

    //--category handler--

    /**
     * Displaying articles in the "Newest" category
     */
    @FXML
    protected void hotnewClicked() {
        loadArticles("new");
    }

    /**
     * Displaying articles in the "COVID-19" category
     */
    @FXML
    protected void covidClick() {
        loadArticles("covid");
    }

    /**
     * Displaying articles in the "Politic" category
     */
    @FXML
    protected void politicClicked() {
        loadArticles("politic");
    }

    /**
     * Displaying articles in the "Business" category
     */
    @FXML
    protected void businessClicked() {
        loadArticles("business");
    }

    /**
     * Displaying articles in the "Technology" category
     */
    @FXML
    protected void techClicked() {
        loadArticles("tech");
    }

    /**
     * Displaying articles in the "Health" category
     */
    @FXML
    protected void healthClicked() {
        loadArticles("health");
    }

    /**
     * Displaying articles in the "Sports" category
     */
    @FXML
    protected void sportsClicked() {
        loadArticles("sport");
    }

    /**
     * Displaying articles in the "Entertainment" category
     */
    @FXML
    protected void entertainmentClicked() {
        loadArticles("entertain");
    }

    /**
     * Displaying articles in the "World" category
     */
    @FXML
    protected void worldClicked() {
        loadArticles("world");
    }

    /**
     * Displaying articles in the "Others" category
     */
    @FXML
    protected void otherClicked() {
        loadArticles("other");
    }

    /**
     * Create a tile pane to store a loading pie
     * @param name:
     */
    public void setTilePane(String name) {

//
        loadingPie = new ProgressIndicator();

        Button changeSceneButton = new Button("Click to continue");
        changeSceneButton.setOnAction(linkClick);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(loadingPie.progressProperty(), 0)),
                new KeyFrame(Duration.minutes(0.1), e-> {
                    // do anything you need here on completion...
                    tilePane.getChildren().addAll(changeSceneButton);
                }, new KeyValue(loadingPie.progressProperty(), 1))
        );

        timeline.play();

        System.out.println(123);

        tilePane = new VBox();
        tilePane.setSpacing(20);
        tilePane.setAlignment(Pos.CENTER);
        tilePane.getChildren().addAll( loadingPie);
    }

    @FXML
    protected void articleClicked(ActionEvent event) {

        // Check which article has been clicked and get their index in "articles" list
        if (event.getTarget().toString().contains("title1")) {
            article_index = index;
        } else if (event.getTarget().toString().contains("title2")) {
            article_index = index + 1;
        } else if (event.getTarget().toString().contains("title3")) {
            article_index = index + 2;
        } else if (event.getTarget().toString().contains("title4")) {
            article_index = index + 3;
        } else if (event.getTarget().toString().contains("title5")) {
            article_index = index + 4;
        } else if (event.getTarget().toString().contains("title6")) {
            article_index = index + 5;
        } else if (event.getTarget().toString().contains("title7")) {
            article_index = index + 6;
        } else if (event.getTarget().toString().contains("title8")) {
            article_index = index + 7;
        } else if (event.getTarget().toString().contains("title9")) {
            article_index = index + 8;
        } else if (event.getTarget().toString().contains("title10")) {
            article_index = index + 9;
        }

        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainScreen.setEffect(gaussianBlur);
        setTilePane("Link");


        myStackPane.getChildren().add(tilePane);
    }

    @FXML
    protected void pageClicked(){
        // Create blur screen
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainScreen.setEffect(gaussianBlur);
        setTilePane("Page");
        myStackPane.getChildren().add(tilePane);
    }


    @FXML
    EventHandler<ActionEvent> linkClick = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            //Change to article scene
            Parent root = null;
            try {
                ArticleController c2 = new ArticleController(articles.get(article_index));
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/view/article.fxml")));
                loader.setController(c2);
                root = loader.load();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //Progress Indicator function
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();

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

    private void categoryClicked() {
        hotnew.setOnAction((ActionEvent) -> hotnewClicked());
        business.setOnAction((ActionEvent) -> businessClicked());
        covid.setOnAction((ActionEvent) -> covidClick());
        world.setOnAction((ActionEvent) -> worldClicked());
        technology.setOnAction((ActionEvent) -> techClicked());
        health.setOnAction((ActionEvent) -> healthClicked());
        others.setOnAction((ActionEvent) -> otherClicked());
        politic.setOnAction((ActionEvent) -> politicClicked());
        sports.setOnAction((ActionEvent) -> sportsClicked());
        entertainment.setOnAction((ActionEvent) -> entertainmentClicked());
    }

    //--------------------------------------------Utilities functions------------------------------------------------

    /**
     * Functions set Image avatar of articles in the Homepage layout except the first article.
     *
     * @param image: Avatar ImageView element
     * @param URL: Path link of article thumbnail image
     * @return: ImageView instance
     */
    protected ImageView setIMG(ImageView image, String URL){
        Image img = new Image(URL);
        // Allow the JavaFX system to run the code on the JavaFX application thread
        Platform.runLater(new Runnable() {
            @Override public void run() {
                image.setImage(img);
            }
        });
        return image;
    }

    /**
     * Functions set title of articles in the Homepage layout.
     *
     * @param title: HyperLink instance
     * @param titleName: Name of the article
     * @return: Title of the article as the path link
     */
    protected Hyperlink setTitle(Hyperlink title, String titleName){
        // Allow the JavaFX system to run the code on the JavaFX application thread
        Platform.runLater(new Runnable() {
            @Override public void run() {
                title.setText(titleName);
            }
        });
        return title;
    }

    /**
     * Functions set published date of articles in the Homepage layout.
     *
     * @param dateTime: Text instance
     * @param URL: Published date of the article
     * @return: Published date as Text instance
     */
    protected Text setDateTime(Text dateTime, String URL){
        // Allow the JavaFX system to run the code on the JavaFX application thread
        Platform.runLater(new Runnable() {
            @Override public void run() {
                dateTime.setText(URL);
            }
        });
        return dateTime;
    }

    /**
     * Functions set Image avatar of the first article in the Homepage layout.
     *
     * @param image: Avatar ImageView element
     * @param URL: Path link of article thumbnail image
     * @return: ImageView instance
     */
    protected ImageView set1stIMG(ImageView image, String URL){
        // Allow the JavaFX system to run the code on the JavaFX application thread
        Image img = new Image(URL);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                image.setImage(img);
                image.fitWidthProperty().bind(splitPane.widthProperty());
                image.fitHeightProperty().bind(splitPane.heightProperty().subtract(80));
            }
        });
        return image;
    }
}

