package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.awt.*;

import static javafx.scene.layout.GridPane.*;

public class Main extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        window.setTitle("Kingsman's Newspaper");

        //Icon
        ImageView iv = new ImageView("https://previews.123rf.com/images/putracetol/putracetol1706/putracetol170603332/80692864-letter-k-icon-logo-design-element.jpg");
        iv.setFitWidth(70);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);

        //Name label
        Label appLabel = new Label("KINGSMAN'S DAILY NEWS");
        appLabel.setFont(new Font("Apple Chancery", 24));

        appLabel.setAlignment(Pos.TOP_CENTER);

        //Searching tab
        TextField textField = new TextField();

        //Page button
        Button page1 = new Button("1");
        Button page2 = new Button("2");
        Button page3 = new Button("3");
        Button page4 = new Button("4");
        Button page5 = new Button("5");
        Button prev = new Button("Prev");
        Button next = new Button("Next");


        //Categories
        Button category1 = new Button("HOT NEW");
        Button category2 = new Button("COVID19");
        Button category3 = new Button("POLITICS");
        Button category4 = new Button("BUSINESS");
        Button category5 = new Button("TECHNOLOGY");
        Button category6 = new Button("HEALTH");
        Button category7 = new Button("SPORTS");
        Button category8 = new Button("ENTERTAINMENT");
        Button category9 = new Button("WORLD");
        Button category10 = new Button("OTHERS");


        //Border
        BorderPane borderPane = new BorderPane();
        BorderPane.setAlignment(appLabel,Pos.TOP_CENTER);
        borderPane.setCenter((appLabel));
        borderPane.setLeft(iv);
        borderPane.setRight(textField);

        //Categories
        HBox hBox = new HBox(category1,category2,category3,category4,category5,category6,category7
                            ,category8,category9,category10);

        //Pages button
        HBox hBox1 = new HBox(prev,page1,page2,page3,page4,page5,next);
        hBox1.setAlignment(Pos.BOTTOM_CENTER);

        //        GridPane
        GridPane gridPane = new GridPane();
        gridPane.add(borderPane,0,0);
        gridPane.add(hBox,0,1);

        BorderPane borderPane1 = new BorderPane();
        borderPane1.setTop(gridPane);
        borderPane1.setBottom(hBox1);
        borderPane1.setPadding(new Insets(10,10,10,10));

        //Scene
        Scene scene = new Scene(borderPane1,800,800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
