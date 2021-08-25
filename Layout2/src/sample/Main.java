package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.awt.*;
import java.io.FileInputStream;

import static javafx.scene.layout.GridPane.*;

public class Main extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        window.setTitle("Kingsman's Newspaper");

        String video = new String();

        //Test image in the content
        String testImage = new String("https://znews-photo.zadn.vn/w1920/Uploaded/ovhpaob/2021_08_15/btr_long_11_16286781526312039640150.jpeg");
        ImageView imageView = new ImageView(new Image(testImage));
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);

        String test = new String("TP HCM bắt đầu tiêm vaccine Sinopharm\n" +
                "Toàn bộ vaccine được phân bổ đã dùng hết, TP HCM tiếp tục sử dụng nguồn một triệu liều Vero Cell đã qua Bộ Y tế kiểm định để tiêm cho người dân.\n" +
                "\n" +
                "Thông tin được Phó chủ tịch UBND TP HCM Dương Anh Đức nói tại cuộc họp báo cung cấp thông tin công tác phòng, chống Covid-19 trên địa bàn trưa 13/8. Cuộc họp diễn ra trong bối cảnh thành phố đã ghi nhận hơn 137.000 ca nhiễm trong đợt dịch thứ tư, bước qua ngày thứ 36 giãn cách xã hội theo Chỉ thị 16 và 16 tăng cường.\n" +
                "\n" +
                "Theo ông Đức, thời gian qua thành phố đã tập trung đẩy nhanh tốc độ tiêm vaccine cho người dân với ngày cao nhất tiêm được hơn 318.000 liều. Đến hôm qua, hầu như lượng vaccine được Bộ Y tế cấp với khoảng 4,3 triệu liều đã được tiêm hết.\n" +
                "\n" +
                " Phó chủ tịch UBND TP HCM Dương Anh Đức tại buổi họp báo trưa 13/8. Ảnh: Hữu Công\n" +
                "Phó chủ tịch UBND TP HCM Dương Anh Đức tại buổi họp báo trưa 13/8. Ảnh: Hữu Công\n" +
                "\"Bắt đầu từ hôm nay, thành phố đẩy mạnh tiếp cận các nguồn vaccine khác, đề nghị Bộ Y tế hỗ trợ, bổ sung và khai thác nguồn vaccine mà thành phố đã có là một triệu liều Sinopharm\", ông Đức nói và cho biết Sở Y tế đang đưa số vaccine này đến các địa phương để tiêm.\n" +
                "\n" +
                "Trước đó, trong ngày 12/8, Trung tâm Kiểm soát bệnh tật TP HCM (HCDC) có văn bản về cấp vaccine Vero Cell cho các quận, huyện. Trong đó, các quận 1, 3, 4 mỗi nơi được cấp 1.000 liều; các quận 6, 8, 10; 12, Gò Vấp, Tân Bình, Bình Thạnh, huyện Nhà Bè mỗi nơi 2.000 liều; mỗi nơi 3.000 liều cho các quận 7, Bình Tân, huyện Bình Chánh; 4.000 liều cho huyện Củ Chi; 5.000 liều cho huyện Hóc Môn và 7.000 liều cho TP Thủ Đức.\n" +
                "\n" +
                "HCDC yêu cầu các đơn vị cử nhân sự nhận vaccine, vật tư trước ngày 14/8 và chuẩn bị đầy đủ trang thiết bị để bảo quản, theo dõi nhiệt độ trong quá trình tiếp nhận, vận chuyển vaccine về đơn vị.\n" +
                "\n" +
                "Theo lãnh đạo UBND TP HCM, với sự tham vấn của các chuyên gia, thành phố thấy bằng mọi giá phải đạt độ phủ vaccine lớn càng sớm càng tốt. Ngoài một triệu liều Sinopharm đã nhận, thành phố tiếp tục tìm kiếm cơ hội để có thêm nguồn vaccine, trong đó cơ 5 triệu liều Moderna đang đàm phán.\n" +
                "\n" +
                "\"Nhu cầu vaccine trên thế giới đang rất lớn, đặc biệt là vacicne Moderna. Do đó để đem về 2 triệu liều Moderna trong tháng 10 sẽ rất khó khăn, cần phải nỗ lực rất lớn và thành phố xem đây là việc trọng tâm\", ông Đức nói và cho biết thành phố cũng thông qua hợp tác quốc tế với các quốc gia, vùng lãnh thổ có quan hệ tốt với TP HCM, Việt Nam với số vaccine cam kết khoảng 750.000 liều.\n" +
                "\n" +
                "Khẳng định việc tiêm vaccine tốt cho mọi người và góp phần chống dịch, ông Đức nói thêm một trong những tiêu chí với vaccine mà lãnh đạo thành phố nhất quán từ trước đến nay, cũng như khẳng định của Thủ tướng, là \"vaccine tốt nhất là vaccine đến trước\".\n" +
                "\n" +
                "\"Cái đang có là cái tốt nhất. Thành phố chỉ sử dụng vaccine với 2 điều kiện - được Tổ chức Y tế thế giới thẩm định cấp phép sử dụng và Bộ Y tế thẩm định, cấp phép\", ông nói Đức nói và mong người dân ý thức tầm quan trọng cũng như sẵn sàng tiếp nhận vaccine.\n" +
                "\n" +
                "Tính từ tháng 3 đến nay, thành phố đã tiêm cho hơn 4,3 triệu người, trên 100.000 người đã tiêm đủ hai mũi. Gần 456.400 người trên 65 tuổi được tiêm.\n" +
                "\n" +
                " Bác sĩ Nguyễn Văn Vĩnh Châu, Giám đốc Bệnh viện bệnh nhiệt đới TP HCM tại buổi họp báo. Ảnh: Hữu Công\n" +
                "Bác sĩ Nguyễn Văn Vĩnh Châu, Giám đốc Bệnh viện bệnh nhiệt đới TP HCM tại buổi họp báo. Ảnh: Hữu Công\n" +
                "Tại cuộc họp báo, bác sĩ Nguyễn Văn Vĩnh Châu, Giám đốc Bệnh viện bệnh nhiệt đới TP HCM, chia sẻ việc giữa tháng 6 năm nay, 69 nhân viên hậu cần - hành chính của bệnh viện bị nhiễm Covid-19 (do nhiễm ở bên ngoài vào).\n" +
                "\n" +
                "Do các nhân viên được tiêm hai mũi vaccine nên 50% người nhiễm bệnh không có triệu chứng, vừa cách ly, điều trị vừa làm việc từ xa. 50% còn lại sốt, ho, mệt mỏi, hoặc mất vị giác. Chỉ có một trường hợp cần thở oxy một thời gian ngắn, sau đó tự hồi phục.\n" +
                "\n" +
                "\"Đây là các trường hợp cụ thể tại bệnh viện. Ngoài ra còn rất nhiều tài liệu khác cho thấy khi được tiêm vaccine, trong người sẽ có kháng thể làm diễn tiến bệnh rất nhẹ\", ông Châu nói và cho biết việc bao phủ tỷ lệ tiêm vaccine sẽ giúp giảm ca mắc mới và ca bệnh nặng, góp phần giảm tử vong.\n" +
                "\n" +
                "Tuy nhiên, người đã tiêm hai mũi vẫn phải thực hiện nghiêm quy định 5K, cách ly, tránh lây cho người khác nếu bị mắc Covid-19 vì vaccine giúp giảm bệnh nặng chứ không có khả năng ngăn virus lây nhiễm.");

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

        //Content of the newspaper
        Text text = new Text();
        text.setText(test);

        //Pane for the content inside the newspaper
        GridPane gridPane2 = new GridPane();
        gridPane2.add(text,1,1);
        gridPane2.add(imageView,1,2);
        gridPane2.setVgap(30);


        //Scroll Pane for the newspaper content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(500,600);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(gridPane2);

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
        gridPane.add(scrollPane,0,2);
        gridPane.setVgap(20);

        BorderPane borderPane1 = new BorderPane();
        borderPane1.setTop(gridPane);
        borderPane1.setBottom(hBox1);
        borderPane1.setPadding(new Insets(10,10,10,10));

        //Scene
        Scene scene = new Scene(borderPane1,800,800);

        //Make text fit with the screen
        text.wrappingWidthProperty().bind(scene.widthProperty().subtract(50));

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
