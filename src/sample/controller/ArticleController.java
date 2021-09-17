package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sample.model.Article;
import sample.model.Category;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ArticleController implements Initializable{
    public ArticleController(Article input) {
        a1 = input;
    }

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private ScrollPane mainScreen;

    //TilePane to store the progress bar
    private TilePane tilePane;

    @FXML
    private ProgressIndicator myProgressIndicator;

    @FXML
    private StackPane myStackPane;

    @FXML
    private Button test;

    @FXML
    private Button backButton;

    @FXML
    private VBox articleContainer;

    private Article a1;

    //Set video
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle) {
        loadContent();
    }

    private void loadContent() {
        //Title
        addTitle( setTitle( a1.getTitle() ), articleContainer);

        //Publish time
        addDateTime( setDate( a1.getPubDay() ),articleContainer);

        //Summary
        String[] SumLine = a1.getSum().split("\n");

        for (int id = 0; id < SumLine.length; id++) {
            //Image
            if (SumLine[id].contains("Link image: ")) {
                if (SumLine[id + 1].contains("Caption:")) {
                    id++;

                    addPictureAndCaption( setImgAndCap( SumLine[id-1].replace("Link image: ", "")
                            , SumLine[id].replace("Caption: ", "") ), articleContainer);
                }
                else {
                    addPicture(setImage( SumLine[id].replace("Link image: ", "") ),articleContainer);
                }
            }
            else if ( !SumLine[id].isEmpty() ){
                addSummary( setSummary( SumLine[id]),articleContainer);
            }
        }

        //Body
        String[] lines = a1.getBody().split("\n");

        for (int id = 0; id < lines.length; id++) {
            //Image
            if (lines[id].contains("Link image: ")) {
                if (lines[id + 1].contains("Caption:")) {
                    id++;

                    addPictureAndCaption( setImgAndCap( lines[id-1].replace("Link image: ", "")
                            , lines[id].replace("Caption: ", "") ), articleContainer);
                }
                else {
                    addPicture(setImage( lines[id].replace("Link image: ", "") ),articleContainer);
                }
            }

            //Video have background
            else if (lines[id].contains("Background image: ") ) {
                if (lines[id+2].contains("Caption:") ) {
                    addVidAndCap(setVidAndCap( lines[id+1].replace("Link video: ", "")
                            , lines[id].replace("Background image: ", "")
                            , lines[id+2].replace("Caption: ", "") ),articleContainer);
                    id++;
                }
                id++;

            }

            //only video
            else if (lines[id].contains("Link video: ") ) {
                if (lines[id+1].contains("Caption:") ) {

                    addVidAndCap(setVidAndCap( lines[id].replace("Link video: ", "")
                            , a1.getAvt()
                            , lines[id+1].replace("Caption: ", "") ),articleContainer);

                    id++;
                }
            }
            else if ( ! lines[id].isEmpty()  ){
                addContent(setContentNewspaper( lines[id] ),articleContainer);
            }
        }

        addAuthor(setAuthor(a1.getAuthor()),articleContainer);
    }

    /*
     * Create a tile pane to store a loading pie
     */
    public void setTilePane(String name) {
        myProgressIndicator = new ProgressIndicator(0);


        Button test = new Button("Increase");
        if(name.equals("Link"))
            test.setOnAction(linkClick);

        tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER);
        tilePane.getChildren().addAll(myProgressIndicator, test);
    }

    @FXML
    EventHandler<ActionEvent> backClick = new EventHandler<ActionEvent>() {
        double ii = 0;
        public void handle(ActionEvent event) {
            ii += 0.1;
            myProgressIndicator.setProgress(ii);

            if(ii > 1.1){
                //Change to homepage scene
                Parent root = null;
                try {
                    HomepageController c2 = new HomepageController();
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/view/homepage.fxml")));
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
        }
    };


    //Blurscreen and pop up the progress indicator when the button is clicked then move on a new screen
    @FXML
    protected void articleClicked(){
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.5);
        mainScreen.setEffect(gaussianBlur);
        setTilePane("Link");
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
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/view/homepage.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    };


    //-------------------------------------------------------------------------------------------------------------
    //Function to add content to the newspaper (Vbox)
    public void addTitle(Text Title, VBox articleContainer){
        VBox.setVgrow(Title, Priority.ALWAYS);
        VBox.setMargin(Title, new Insets(30,0,0,0));
        articleContainer.getChildren().add(Title);
    }

    public void addDateTime(Text Date, VBox articleContainer){
        VBox.setMargin(Date, new Insets(0,0,0,20));
        articleContainer.getChildren().add(Date);
    }

    public void addSummary(Text Summary, VBox articleContainer){
        VBox.setVgrow(Summary, Priority.ALWAYS);
        VBox.setMargin(Summary, new Insets(5,20,10,20));
        articleContainer.getChildren().add(Summary);
    }

    public void addContent(Text Content, VBox articleContainer){
        VBox.setVgrow(Content, Priority.ALWAYS);
        VBox.setMargin(Content, new Insets(20,0,20,0));
        articleContainer.getChildren().add(Content);
    }

    public void addPicture(ImageView image, VBox articleContainer) {
        VBox.setVgrow(image, Priority.ALWAYS);
        articleContainer.getChildren().add(image);
    }

    public void addPictureAndCaption(VBox imgAndCap, VBox articleContainer) {
        VBox.setVgrow(imgAndCap, Priority.ALWAYS);
        articleContainer.getChildren().add(imgAndCap);
    }

    public void addVid(ImageView video, VBox articleContainer){
        VBox.setVgrow(video, Priority.ALWAYS);
        articleContainer.getChildren().add(video);
    }

    public void addVidAndCap(VBox vidAndCap, VBox articleContainer){
        VBox.setVgrow(vidAndCap, Priority.ALWAYS);
        articleContainer.getChildren().add(vidAndCap);
    }

    public void addAuthor(Text Author, VBox articleContainer){
        VBox.setMargin(Author, new Insets(0,20,0,0));
        articleContainer.getChildren().add(Author);
    }

    //----------------------------------------------------------------------------------------------------------------
    //Funtions to set the content
    public Text setTitle(String content){
        Text title = new Text();
        title.setText(content);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 41));
        title.setWrappingWidth(1300);
        title.setTextAlignment(TextAlignment.CENTER);
        return title;
    }

    public Text setDate(String dateTime){
        Text Date = new Text();
        Date.setText(dateTime);
        Date.setFont(Font.font("Arial", 16));
        Date.setWrappingWidth(1350);
        Date.setTextAlignment(TextAlignment.LEFT);
        return Date;
    }

    public Text setContentNewspaper(String content){
        Text ContentNewspaper = new Text();
        ContentNewspaper.setText(content);
        ContentNewspaper.setFont(Font.font("Arial", 20));
        ContentNewspaper.setWrappingWidth(1300);
        ContentNewspaper.setTextAlignment(TextAlignment.JUSTIFY);
        return ContentNewspaper;
    }

    public WebView setVideo1(String URL){
        //video
        WebView video = new WebView();
        WebEngine webEngine = video.getEngine();
        webEngine.load(URL);
        video.setPrefSize(1350,500);
        video.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        video.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        return video;
    }

    public StackPane setVideo(String URL, String Background){
        StackPane stackPane = new StackPane();

        //Play button
        Button play = new Button("Play");
        play.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        play.setOnAction(event -> {
            stackPane.getChildren().clear();

            WebView video = setVideo1(URL);

            //Prevent the screen auto scroll up
            articleContainer.requestFocus();

            stackPane.getChildren().add(video);
        });

        if ( !Background.isEmpty() ) {
            stackPane.getChildren().addAll(setImage(Background),play);
        } else {
            Image i = new Image((new File("src/sample/view/image/backgroundvideo.jpeg")).toURI().toString());
            ImageView image = new ImageView(i);
            image.setFitHeight(550);
            image.setFitWidth(800);
            image.setPreserveRatio(true);
            image.setSmooth(true);

            stackPane.getChildren().addAll(image,play);
        }

        return stackPane;
    }

    public VBox setVidAndCap(String URL, String Background, String cap){
        StackPane video = setVideo(URL, Background);

        ImageView backgroundImg;

        if ( !Background.isEmpty() ) {
            backgroundImg = setImage(Background);
        }else {
            Image i = new Image((new File("src/sample/view/image/backgroundvideo.jpeg")).toURI().toString());
            ImageView image = new ImageView(i);
            image.setFitHeight(550);
            image.setFitWidth(800);
            image.setPreserveRatio(true);
            image.setSmooth(true);

            backgroundImg = image;
        }

        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setSpacing(5);
        v.setFillWidth(true);
        v.setPrefSize(backgroundImg.getFitWidth(),backgroundImg.getFitHeight());

        Text caption = new Text();
        caption.setText(cap);
        caption.setFont(Font.font("Arial", FontPosture.ITALIC, 16));
        caption.setWrappingWidth(backgroundImg.getFitWidth());
        caption.setOpacity(0.55);

        v.getChildren().addAll(video,caption);;

        return v;
    }

    public Text setSummary(String content){
        Text summary = new Text();
        summary.setText(content);
        summary.setFont(Font.font("Arial", FontPosture.ITALIC, 20));
        summary.setWrappingWidth(1300);
        summary.setTextAlignment(TextAlignment.JUSTIFY);
        return summary;
    }

    public ImageView setImage(String URL){
        Image i = new Image(URL);
        ImageView image = new ImageView(i);
        image.setFitHeight(550);
        image.setFitWidth(800);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        return image;
    }

    public VBox setImgAndCap(String URL, String cap){
        ImageView image = setImage(URL);

        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setSpacing(5);
        v.setFillWidth(true);
        v.setPrefSize(image.getFitWidth(),image.getFitHeight());

        Text caption = new Text();
        caption.setText(cap);
        caption.setFont(Font.font("Arial", FontPosture.ITALIC, 16));
        caption.setWrappingWidth(image.getFitWidth());
        caption.setOpacity(0.55);

        v.getChildren().addAll(image,caption);;

        return v;
    }

    public Text setAuthor(String authorName){
        Text Author = new Text();
        Author.setText(authorName);
        Author.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 33));
        Author.setWrappingWidth(1350);
        Author.setTextAlignment(TextAlignment.RIGHT);
        return Author;
    }
}