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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ArticleController implements Initializable{

    private Parent root;
    private Scene scene;

    @FXML
    private Text text;

    @FXML
    private VBox mainScreen;

    @FXML
    private ProgressIndicator myProgressIndicator;

    @FXML
    private Button test;

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
    private VBox articleContainer;

    @FXML
    private WebView webView;

    //Set video
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Test only
        //Content of the newspaper to check
        String summary = new String("TPO - Bí thư Thành ủy TPHCM Nguyễn Văn Nên cho biết, Tổng Bí thư Nguyễn Phú Trọng đã gọi điện thăm hỏi, động viên Đảng bộ và đồng bào TPHCM đã hơn 2 tháng qua kiên cường ứng phó với đại dịch COVID-19.");
        String imgURL = new String("https://image.thanhnien.vn/2048/uploaded/quocviet/2021_08_10/park/00-parkhang-seoparkchoong-kyun-hoangquan_kmpe.jpeg");
        String contentNews = new String("Quy hoạch nêu rõ, đường bộ là phương thức linh hoạt, hiệu quả với cự ly ngắn và trung bình (dưới 300 km); hỗ trợ gom, giải tỏa hàng hóa, hành khách cho các hình thức vận tải khác. Hệ thống đường bộ quốc gia phải đảm bảo kết nối với đường địa phương; giữa các vùng động lực và vùng khó khăn; đến đầu mối vận tải quốc tế.Các địa phương có thể điều chỉnh tiến độ đầu tư các tuyến đường, nếu nhận thấy nhu cầu cần thiết đầu tư sớm hơn và bố trí được nguồn lực. Địa phương muốn mở rộng quốc lộ đi qua địa bàn có thể phối hợp nguồn vốn theo hướng trung ương đầu tư phần đường theo quy hoạch, địa phương đầu tư phần mở rộng.Để thực hiện được mục tiêu nêu trên, Chính phủ sẽ huy động tối đa mọi nguồn lực; đẩy mạnh đầu tư PPP (đối tác công tư), trong đó vốn ngân sách Nhà nước hỗ trợ, dẫn dắt, kích hoạt để thu hút nguồn lực từ các thành phần kinh tế khác. Chính phủ đặt mục tiêu năm 2030 cả nước có hơn 5.000 km cao tốc đường bộ; đến năm 2050 có 41 tuyến cao tốc với tổng chiều dài hơn 9.000 km.Ngày 1/9, Quy hoạch phát triển mạng lưới đường bộ thời kỳ 2020-2030, tầm nhìn đến 2050 đã được Thủ tướng phê duyệt, nêu rõ đến năm 2050, các tuyến cao tốc gồm: tuyến Bắc Nam phía Đông (Lạng Sơn - Cà Mau) dài hơn 2.000 km, quy mô 4-10 làn xe; tuyến Bắc Nam phía Tây dài hơn 1.200 km, quy mô 4-6 làn xe.Khu vực phía Bắc gồm 14 tuyến, chiều dài 2.300 km; miền Trung - Tây Nguyên có 10 tuyến, chiều dài 1.400 km; phía Nam có 10 tuyến, chiều dài 1.290 km.Vành đai đô thị Hà Nội có 3 tuyến, dài 425 km; vành đai đô thị TP HCM gồm 2 tuyến, dài gần 300 km.Năm 2021, cả nước có hơn 1.160 km cao tốc. Như vậy, đến năm 2030 sẽ có thêm 3.840 km; đến năm 2050 tăng tiếp 7.840 km.Cũng theo Quy hoạch, đến năm 2030, cả nước dự kiến có 172 tuyến quốc lộ, tổng 30.000 km. Đường bộ ven biển chạy qua 20 tỉnh, thành dài 3.000 km. Các dự án được ưu tiên đầu tư dựa trên nguyên tắc đảm bảo tính lan tỏa, là động lực phát triển vùng hoặc liên vùng, đầu tư phát huy hiệu quả ngay.Các tuyến cao tốc cần tập trung hoàn thiện gồm: tuyến Bắc Nam phía Đông; một số tuyến cao tốc khu vực Nam Bộ; miền Trung - Tây Nguyên; miền núi phía Bắc; vành đai đô thị Hà Nội, TP HCM. Mục tiêu đến năm 2030 đưa vào khai thác hơn 5.000 km cao tốc.Chính phủ đặt mục tiêu năm 2030 cả nước có hơn 5.000 km cao tốc đường bộ; đến năm 2050 có 41 tuyến cao tốc với tổng chiều dài hơn 9.000 km.Ngày 1/9, Quy hoạch phát triển mạng lưới đường bộ thời kỳ 2020-2030, tầm nhìn đến 2050 đã được Thủ tướng phê duyệt, nêu rõ đến năm 2050, các tuyến cao tốc gồm: tuyến Bắc Nam phía Đông (Lạng Sơn - Cà Mau) dài hơn 2.000 km, quy mô 4-10 làn xe; tuyến Bắc Nam phía Tây dài hơn 1.200 km, quy mô 4-6 làn xe.Khu vực phía Bắc gồm 14 tuyến, chiều dài 2.300 km; miền Trung - Tây Nguyên có 10 tuyến, chiều dài 1.400 km; phía Nam có 10 tuyến, chiều dài 1.290 km.Vành đai đô thị Hà Nội có 3 tuyến, dài 425 km; vành đai đô thị TP HCM gồm 2 tuyến, dài gần 300 km.Năm 2021, cả nước có hơn 1.160 km cao tốc. Như vậy, đến năm 2030 sẽ có thêm 3.840 km; đến năm 2050 tăng tiếp 7.840 km.Cũng theo Quy hoạch, đến năm 2030, cả nước dự kiến có 172 tuyến quốc lộ, tổng 30.000 km. Đường bộ ven biển chạy qua 20 tỉnh, thành dài 3.000 km. Các dự án được ưu tiên đầu tư dựa trên nguyên tắc đảm bảo tính lan tỏa, là động lực phát triển vùng hoặc liên vùng, đầu tư phát huy hiệu quả ngay.Các tuyến cao tốc cần tập trung hoàn thiện gồm: tuyến Bắc Nam phía Đông; một số tuyến cao tốc khu vực Nam Bộ; miền Trung - Tây Nguyên; miền núi phía Bắc; vành đai đô thị Hà Nội, TP HCM. Mục tiêu đến năm 2030 đưa vào khai thác hơn 5.000 km cao tốc. ");
        String videoURL = new String("https://znews-mcloud-bf-s2.zadn.vn/VJsUNap_P-o/7ea4b57414aafef4a7bb/aef9fd1f58d2b28cebc3/720/58044cd8748e9dd0c49f.mp4?authen=exp=1630959809~acl=/VJsUNap_P-o/*~hmac=3c1d0c6c37aabd748f9defc35a34a742");
        String caption = new String("Kiểm tra giấy đi đường của người dân, ngày 9/8. Ảnh: Giang HuyKiểm tra giấy đi đường của người dân, ngày 9/8. Ảnh: Giang Huy");
        String backgroundIMG = new String("https://i1-vnexpress.vnecdn.net/2021/09/05/san-bay-9618-1630838730.jpg?w=680&h=0&q=100&dpr=2&fit=crop&s=0CoZKIpTENZnfHHPf8-Twg");

        //-------------------------------------------------------------------------------------------------------------

        addTitle(setTitle("Chỉ đạo của Tổng Bí thư về phòng, chống dịch COVID-19 tại TPHCM"), articleContainer);
        addDateTime(setDate("17/08/2021 "),articleContainer);
        addSummary(setSummary(summary),articleContainer);
        addPictureAndCap(setImgAndCap(imgURL,caption),articleContainer);
        addPictureAndCap(setImgAndCap(imgURL,caption),articleContainer);
        addPictureAndCap(setImgAndCap(imgURL,caption),articleContainer);
//        addContent(setContentNewspaper(contentNews),articleContainer);
//        addVideo(setVideo(videoURL),articleContainer);
        addVidAndCap(setVidAndCap(videoURL,backgroundIMG,caption),articleContainer);
        addAuthor(setAuthor("VU ANH"), articleContainer);
    }

    //TilePane to store the progress bar
    private TilePane tilePane;

    //Function to set the tile pane to store the progress bar
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

    //Blur the screen and pop up the progress indicator when the page or the category is clicked then move on a new screen when the
    //progress bar finish loading
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
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    };

    //Progress Indicator function when the page or category is clicked
    @FXML
    EventHandler<ActionEvent> pageClick = new EventHandler<ActionEvent>() {
        double ii = 0;

        public void handle(ActionEvent e) {
            // Increase 10%
            ii += 0.1;
            myProgressIndicator.setProgress(ii);

            //When the progress bar finish loading, remove the blur screen and progress bar and add new 10 articles .
            if (ii > 1.1) {
                mainScreen.setEffect(null);
                myStackPane.getChildren().removeAll(tilePane);
                ii = 0;
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

    //Function to add Date and time to the newspaper
    public void addDateTime(Text Date, VBox articleContainer){
        VBox.setMargin(Date, new Insets(0,0,0,20));
        articleContainer.getChildren().add(Date);
    }

    //Function to add Author's name to the newspaper
    public void addAuthor(Text Author, VBox articleContainer){
        VBox.setMargin(Author, new Insets(0,20,0,0));
        articleContainer.getChildren().add(Author);
    }

    //Function to add Summary to the newspaper
    public void addSummary(Text Summary, VBox articleContainer){
        VBox.setVgrow(Summary, Priority.ALWAYS);
        VBox.setMargin(Summary, new Insets(5,20,10,20));
        articleContainer.getChildren().add(Summary);
    }

    //Function to add Content to the newspaper
    public void addContent(Text Content, VBox articleContainer){
        VBox.setVgrow(Content, Priority.ALWAYS);
        VBox.setMargin(Content, new Insets(20,0,20,0));
        articleContainer.getChildren().add(Content);
    }

    //Function to add Picture and caption to the newspaper
    public void addPictureAndCap(VBox imgAndCap, VBox articleContainer){
        VBox.setVgrow(imgAndCap, Priority.ALWAYS);
        articleContainer.getChildren().add(imgAndCap);
    }

    //Function to add Video without caption to the newspaper
    public void addVideo(StackPane video, VBox articleContainer){
        VBox.setVgrow(video, Priority.ALWAYS);
        articleContainer.getChildren().add(video);
    }

    //Function to add Video with caption to the newspaper
    public void addVidAndCap(VBox vidAndCap, VBox articleContainer){
        VBox.setVgrow(vidAndCap, Priority.ALWAYS);
        articleContainer.getChildren().add(vidAndCap);
    }


    public void addIMG(ImageView IMG, VBox articleContainer){
        VBox.setVgrow(IMG, Priority.ALWAYS);
        articleContainer.getChildren().add(IMG);
    }

    //----------------------------------------------------------------------------------------------------------------
    //Functions to set the Title
    public Text setTitle(String content){
        Text title = new Text();
        title.setText(content);
        //Set Big Font for the Title
        title.setFont(Font.font("Arial", FontWeight.BOLD, 41));
        title.setWrappingWidth(1300);
        title.setTextAlignment(TextAlignment.CENTER);
        return title;
    }

    //Function to set the Date and Time
    public Text setDate(String dateTime){
        Text Date = new Text();
        Date.setText(dateTime);
        //Set small font for the and time
        Date.setFont(Font.font("Arial", 16));
        Date.setWrappingWidth(1350);
        Date.setTextAlignment(TextAlignment.LEFT);
        return Date;
    }

    //Function to set the Content
    public Text setContentNewspaper(String content){
        Text ContentNewspaper = new Text();
        ContentNewspaper.setText(content);
        ContentNewspaper.setFont(Font.font("Arial", 20));
        ContentNewspaper.setWrappingWidth(1300);
        //Set the text equally
        ContentNewspaper.setTextAlignment(TextAlignment.JUSTIFY);
        return ContentNewspaper;
    }

    //Set an Author's name
    public Text setAuthor(String authorName){
        Text Author = new Text();
        Author.setText(authorName);
        //Set big font and italic for the author's name
        Author.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 33));
        Author.setWrappingWidth(1350);
        Author.setTextAlignment(TextAlignment.RIGHT);
        return Author;
    }

    //Function to set the video
    public WebView setVideo1(String URL){
        //video
        WebView video = new WebView();
        WebEngine webEngine = video.getEngine();
        //Load the video with the URL of the newspaper
        webEngine.load(URL);
        video.setPrefSize(1350,500);
        video.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        video.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        return video;
    }

    //Function to set the video with the background image and the play button at the middle of the image
    public StackPane setVideoAndBackground(String URL, String Background){
        //Create stack pane to store the video, background image and button play
        StackPane stackPane = new StackPane();

        //Play button
        Button play = new Button("Play");
        play.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //When the play button is clicked, the image will be disappear and the video will start to play
        play.setOnAction(event -> {
            stackPane.getChildren().removeAll(setImage(Background),play);

            WebView video = setVideo1(URL);

            stackPane.getChildren().add(video);
        });

        stackPane.getChildren().addAll(setImage(Background),play);

        return stackPane;
    }

    //Function to set the summary
    public Text setSummary(String content){
        Text summary = new Text();
        summary.setText(content);
        summary.setFont(Font.font("Arial", FontPosture.ITALIC, 20));
        summary.setWrappingWidth(1300);
        summary.setTextAlignment(TextAlignment.JUSTIFY);
        return summary;
    }

    //Function to set the image
    public ImageView setImage(String URL){
        //Create the image with an URL of the newspaper
        Image i = new Image(URL);
        ImageView image = new ImageView(i);
        image.setFitHeight(530);
        image.setFitWidth(800);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        return image;
    }

    //Function to set the image with caption
    public VBox setImgAndCap(String URL, String cap){
        //Create the image with an URL of the newspaper
        ImageView image = setImage(URL);

        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setSpacing(5);
        v.setFillWidth(true);
        v.setPrefSize(image.getFitWidth(),image.getFitHeight());

        //Caption of the image
        Text caption = new Text();
        caption.setText(cap);
        caption.setFont(Font.font("Arial", FontPosture.ITALIC, 16));
        caption.setWrappingWidth(image.getFitWidth());
        caption.setOpacity(0.55);

        v.getChildren().addAll(image,caption);

        return v;
    }

    //Set video with background image and caption below
    public VBox setVidAndCap(String URL, String Background, String cap){
        StackPane video = setVideoAndBackground(URL, Background);

        //Create background image
        ImageView backgroundImg = setImage(Background);

        //Vbox to store the video and caption below
        VBox v = new VBox();

        v.setAlignment(Pos.CENTER);
        v.setSpacing(5);
        v.setFillWidth(true);
        v.setPrefSize(backgroundImg.getFitWidth(),backgroundImg.getFitHeight());

        //Caption for the video
        Text caption = new Text();
        caption.setText(cap);
        caption.setFont(Font.font("Arial", FontPosture.ITALIC, 16));
        caption.setWrappingWidth(backgroundImg.getFitWidth());
        caption.setOpacity(0.55);

        v.getChildren().addAll(video,caption);

        return v;
    }
}
