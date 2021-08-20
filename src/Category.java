import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.LinkedList;

public class Category {
    public Category (String type) throws IOException {
       // check(type);
    }

    private LinkedList<Article> New = new LinkedList<Article>();

    private LinkedList<Article> Covid = new LinkedList<Article>();

    private LinkedList<Article> Pol = new LinkedList<Article>();

    private LinkedList<Article> Buss = new LinkedList<Article>();

    private LinkedList<Article> Heal = new LinkedList<Article>();

    private LinkedList<Article> Spo = new LinkedList<Article>();

    private LinkedList<Article> Entertain = new LinkedList<Article>();

    private LinkedList<Article> World = new LinkedList<Article>();

    private LinkedList<Article> Other = new LinkedList<Article>();

    private void check (String type) {

    }

    /*
    public LinkedList<String> getList() {
        return out;
    }*/
//
    public void getCovid () throws IOException {
        int count = 0;

        //VNExpress


        //selectFirst("section.section_main.section_topstory").
        for ( Element e : Jsoup.connect("https://vnexpress.net/suc-khoe/vaccine").get().select(".item-news.full-thumb") ) {
            Covid.add(new Article(e.selectFirst("a").attr("href")) );
        }
        System.out.println(Covid.size());



        //-------------------------------------------------------------------------//

        //Zing
        count = 0;

        for (Element e: Jsoup.connect("https://zingnews.vn/sang-kien-chong-dich.html").get()
                .getElementsByAttributeValueContaining("class", "article-item") ) {
            Covid.add(new Article("https://zingnews.vn/" + e.selectFirst("a").attr("href")) );

            count++;

            if (count == 10) {
                break;
            }
        }
        System.out.println(Covid.size());

        //------------------------------------------------------------------//

        //Tuoi tre

        count = 0;

        for (Element e : Jsoup.connect("https://tuoitre.vn/dich-covid-19-e576.htm?fbclid=IwAR32Ce6hadrKVsRt7UTy1cHZ2uulOwGvCfTzcBFoN7I2HdS7GKBC0YsC2hM").get()
                .select("li.news-item") ) {
            Covid.add(new Article("https://tuoitre.vn/" + e.selectFirst("a").attr("href") ) );

            count++;

            if (count == 10) {
                break;
            }
        }
        System.out.println(Covid.size());

        System.out.println(Covid);

        //------------------------------------------------------------------//

        //Thanh Nien
        /*
        count = 0;

        for ( Element e : Jsoup.connect("https://thanhnien.vn/e-magazine/toan-canh-covid-19-tin-tuc-so-lieu-phan-tich-1265104.html").get()
                .select("article.article") ) {
            System.out.println(e.selectFirst("a").attr("href") );

            count++;

            if ( count == 10) {
                break;
            }
        }
        System.out.println(Covid.size());*/

    }
}
