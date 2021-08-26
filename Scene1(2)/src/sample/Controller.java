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
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Controller {

    @FXML
    private Pane myPane;

    @FXML
    private GridPane myGridPane;

    @FXML
    private BorderPane mainScreen;

    @FXML
    private ProgressIndicator myProgressIndicator;

    @FXML
    private Button test;

    @FXML
    private Text text;

    //add ScrollPane to store the newspaper content when finish loading
    private ScrollPane myScrollPane;

    public void setScrollPane(){

        //Set text for the scrolPane
        text = new Text();
        text.setText("Thủ tướng Phạm Minh Chính đến kiểm tra một số điểm an sinh xã hội ở TP HCM - vùng dịch lớn nhất nước trên cương vị Trưởng ban phòng chống Covid-19 quốc gia, sáng 26/8.\n" +
                "\n" +
                "Chuyến đi thực tế, kiểm tra công tác chống dịch lần này của Thủ tướng diễn ra trong bối cảnh TP HCM ghi nhận hơn 190.800 ca nhiễm ở đợt dịch thứ tư và bước qua ngày thứ tư siết chặt giãn cách theo nguyên tắc \"ai ở đâu yên đó\".\n" +
                "\n" +
                "Đại tá Ngô Minh Thuấn (ngoài cùng bên phải), Tổng giám đốc Tổng công ty Tân cảng Sài Gòn báo cáo Thủ tướng về công tác phòng chống dịch, duy trì sản xuất của Tổng công ty, sáng 26/8. Ảnh: Công Hoan\n" +
                "Điểm an sinh xã hội khẩn cấp ở phường Cát Lái, TP Thủ Đức, là nơi đầu tiên Thủ tướng đến khảo sát. Địa phương này có hơn 400 người phục vụ an sinh cho 6.000 hộ, tức mỗi người phục vụ trung bình 15 hộ.\n" +
                "\n" +
                "Tại đây, Thủ tướng yêu cầu phải sử dụng mọi nguồn lực có thể để giúp đỡ người dân. Quá trình làm có thể sáng tạo nhưng phải đảm bảo giãn cách tuyệt đối nhằm \"tranh thủ thời gian vàng\". Ông cũng lưu ý người dân phải được tiếp cận y tế sớm nhất khi cần thông qua trạm y tế lưu động và trạm y tế phường.\n" +
                "\n" +
                "Người đứng đầu Chính phủ yêu cầu địa phương phải sớm phát hiện ra F0, tiếp cận ngay để phân loại, điều trị phù hợp, trong đó, phải xét nghiệm, ưu tiên những người nguy cơ cao. \"Chiến thắng Covid-19 là chiến thắng của dân. Chiến thắng dịch hay không do dân quyết định nên dân phải tham gia\", ông nói và nhấn mạnh thông điệp \"vaccine tốt nhất là vaccine được tiêm sớm nhất\".\n" +
                "\n" +
                "Trò chuyện với cán bộ, nhân viên làm nhiệm vụ tại phường, Thủ tướng nói rằng chống dịch không phải ngày một ngày hai, vì vậy cần vừa làm, vừa động viên người dân muốn đẩy lùi Covid-19 nhanh phải chấp hành nghiêm. \"Chịu khổ 5, 10, 20 ngày còn hơn chịu khổ cả năm. Chịu khó, chịu khổ lúc này để nhanh bình thường trở lại\", ông nói.\n" +
                "\n" +
                "Trong buổi sáng, Thủ tướng cùng đoàn công tác kiểm tra công tác phòng chống dịch tại cảng Cát Lái và thăm hỏi, động viên người dân sinh sống trong một khu nhà trọ trên đường Nguyễn Thị Định, phường Thạnh Mỹ Lợi, TP Thủ Đức.\n" +
                "\n" +
                "Trưa 26/8, Thủ tướng cùng đoàn công tác đến thăm hỏi, động viên người lao động làm việc tại Trung tâm thương mại - phân phối hàng hóa Co.opXtra, ở phường Hiệp Bình Chánh, TP Thủ Đức, thuộc Liên hiệp Hợp tác xã thương mại TP HCM (Saigon Co.op).\n" +
                "\n" +
                "Đánh giá cao nỗ lực của Saigon Co.op trong duy trì ổn định các kênh phân phối hàng hóa, lương thực, thực phẩm trong dịch, Thủ tướng yêu cầu TP HCM phải tiếp tục đảm bảo cung ứng đầy đủ, thường xuyên, liên tục hàng hóa, nhu yếu phẩm cho người dân trên địa bàn, không để đứt gãy chuỗi cung ứng.\n" +
                "\n" +
                "Rời siêu thị Co.opXtra, Thủ tướng đến thăm Cơ sở tư vấn và cai nghiện Bình Triệu (TP.Thủ Đức), Bệnh viện dã chiến điều trị Covid-19 Quân dân y Miền Đông đang thu dung, chữa trị hơn 1.300 bệnh nhân.\n" +
                "\n" +
                "Đây là lần thứ tư người đứng đầu Chính phủ trực tiếp kiểm tra công tác chống dịch tại thành phố từ khi dịch bùng phát hồi cuối tháng 5.");

        text.setFont(Font.font("Arial", 16));
        text.setLineSpacing(5);



        myScrollPane = new ScrollPane();
        myScrollPane.fitToHeightProperty().set(true);
        myScrollPane.fitToWidthProperty().set(true);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        myScrollPane.setContent(text);
    }

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
                myGridPane.getChildren().removeAll(myPane);
                setScrollPane();
                myGridPane.add(myScrollPane,0,1);

                ii = 0;
            }
        }
    };

}
