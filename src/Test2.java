import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Test2 {
    public static void main (String[] args) throws IOException{
        Document doc = Jsoup.connect("https://zingnews.vn/chu-tich-tp-phan-rang-thap-cham-xin-loi-nguoi-dan-phuong-tan-tai-post1241263.html").get();

        Elements title = doc.getElementsByClass("the-article-title");
        System.out.printf("tile is:  %s%n", title.text());

        Elements sum = doc.getElementsByClass("the-article-summary");

        String a = sum.text();
        System.out.printf("Summary is: %n%s%n", a);

        Elements content = doc.getElementsByClass("the-article-body");
        String b = content.text();

        System.out.println("Content is: ");

        for (String i : b.split("\\. ") ) {
            System.out.println(i);
        }
    }
}
