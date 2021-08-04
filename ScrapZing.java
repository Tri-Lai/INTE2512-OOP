import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;
import java.util.List;

import java.io.IOException;

public class ScrapZing {
    public static void main (String[] args) throws IOException{
        StopWatch clock = new StopWatch();

        Scanner scan = new Scanner(System.in);

        //System.out.print("Enter Zing's news url: ");
        String url = "https://zingnews.vn/pho-thu-tuong-lay-nhiem-qua-he-thong-phan-phoi-rat-dang-bao-dong-post1246882.html";

        clock.start();

        Document doc = Jsoup.connect(url).get();

        //title
        Element head = doc.select("header.the-article-header").first();

        System.out.println("Category is: " + head.select("p.the-article-category").text() + "\n" );

        System.out.println("tile is: " + head.select("h1.the-article-title").text() + "\n");


        System.out.println("Publish time is: " + head.select("ul.the-article-meta").first().select("li.the-article-publish").text() + "\n");
        //Summary
        Elements sum = doc.select("p.the-article-summary");
        System.out.printf("Summary is: %n%s%n%n", sum.text());

        //Content
        System.out.println("Content is: ");

        Element try4 = doc.select("div.the-article-body").first();

        List<Element> str2 = try4.children();

        for (Element e1 : str2) {
            String tag = e1.tagName();

            //Normal paragraph
            if (tag == "p") {
                System.out.println(e1.text() + "\n");
            }

            //Heading
            else if (tag == "h3"){
                System.out.println("\t" + e1.text() + "\n");
            }

            //Picture
            else if (tag == "table") {
                if ( e1.className().equals("picture") ){
                    Elements rows = e1.select("tbody").first().select("tr");

                        for (Element row : rows) {
                            Element cell = row.select("td").first();

                            switch (cell.className() ) {
                                case "pic":
                                    Element img = row.getElementsByTag("img").first();
                                    System.out.println("Picture link: " + img.attr("data-src"));
                                    break;
                                case "pCaption caption":
                                    Element capt = row.getElementsByTag("p").first();
                                    System.out.println("Caption: " + capt.text() + "\n");
                            }
                        }

                }
            }

            //Widget
            else if ( tag == "div"){
                if ( e1.className().equals("z-widget-corona") ) {
                    Element widget = e1.select("div.z-widget-corona").first().select("div.z-corona-header").first().select(" a").first();
                    System.out.println("Link to widget: " + widget.attr("href"));
                }
            }
        }

        clock.stop();
        System.out.print("\n"+ "Time consume: " + clock.getElapsedTime() + " ms" + "\n");
    }
}
