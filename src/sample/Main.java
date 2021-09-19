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

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/homepage.fxml")));
        primaryStage.setTitle("Kingsman Daily News");
        primaryStage.setScene(new Scene(root, 700, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
