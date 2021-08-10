import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.List;
import java.util.Scanner;

public class Article {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        //System.out.print("Enter Link: ");
        //String url = scan.nextLine();

        String urlimg = "https://vnexpress.net/khong-thu-bat-ky-chi-phi-nao-lien-quan-tiem-vaccine-covid-19-4338154.html";
        String urlimg2 = "https://vnexpress.net/ha-noi-se-dieu-chinh-kiem-tra-giay-di-duong-4338170.html";
        String urlvid = "https://vnexpress.net/hamilton-bi-phat-10-giay-van-thang-grand-prix-anh-4326758.html";

        String urlvid2 = "https://zingnews.vn/son-heung-min-toa-sang-giup-tottenham-danh-bai-arsenal-post1248756.html";

        StopWatch clock = new StopWatch();
        clock.start();

        String url = urlvid2;
        Document doc = Jsoup.connect(url).get();

        int id = checkWeb(url);

        //title

        System.out.println("tile is: " + doc.select(title[id]).text() + "\n");

        //System.out.println("Author is: " + doc.select(author[id]).text() + "\n");

        System.out.println("Publish time is: " + doc.select(pubDay[id]).text() + "\n");

        //Summary
        System.out.println("Summary is: \n" + doc.select(sum[id]).text() + "\n");


        //Content
        System.out.println("Content is: ");

        Elements b = doc.select(body[id]).first().children();

        switch (id) {
            case 0:
                bodyZing(b);
                break;
            case 1:
                bodyVNExpress(b);
                break;
        }

        clock.stop();
        System.out.print("\n" + "Time consume: " + clock.getElapsedTime() + " ms" + "\n");
    }

        //0.Zing    1.VNExpress
        static String[] category = {"p.the-article-category", "ul.breadcrumb > li:nth-child(1)"};

        static String[] title = {"h1.the-article-title", "h1.title-detail"};

        static String[] author = {"div.the-article-credit p"};

        static String[] pubDay = {"li.the-article-publish", "span.date"};

        static String[] sum = {"p.the-article-summary", "p.description"};

        static String[] body = {"div.the-article-body", "article.fck_detail"};

    //Check Website through url
    public static Integer checkWeb(String url) throws IOException {
        int id = 0;

        if (url.contains("zingnews")) {
            id = 0;
        } else if (url.contains("vnexpress")) {
            id = 1;
        }

        return id;
    }

    public static void bodyZing(Elements body) {
        for (Element e : body) {
            String tag = e.tagName();

            //Normal paragraph
            if (tag.equals("p")) {
                System.out.println(e.text() + "\n");
            }

            //Heading
            else if (tag == "h3") {
                System.out.println("\t" + e.text() + "\n");
            }

            //Table
            else if (tag == "table") {
                //Picture
                if (e.className().equals("picture")) {
                    Elements rows = e.select("tbody > tr");

                    for (Element row : rows) {
                        Element cell = row.select("td").first();

                        switch (cell.className()) {
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
                //Related article
                else if (e.className().equals("article")) {
                    System.out.println("Related article: ");

                    System.out.println("\tLink: https://zingnews.vn" + e.select("tbody > tr > td > div.inner-article > a").first().attr("href"));

                    Elements row = e.select("tbody > tr > td > div.inner-article > a").first().children();

                    for (Element child : row) {
                        switch (child.className() ) {
                            case "cover formatted":
                                System.out.println("\tLink Image: " + child.select("p").first().attr("style").replace("background-image:url(", "").replace(");", ""));
                                break;
                            case "summary":
                                System.out.println("\tSummary: " + child.select("p.summary").text());
                                break;
                            case "title":
                            System.out.println("\tTitle is: " + child.select("h2.title").text());
                            break;
                        }
                    }
                    System.out.println("\n");
                }
            }

            //Widget and LiveScore
            else if (tag == "div") {
                //Corona Widget
                if (e.className().equals("z-widget-corona")) {
                    Element widget = e.select("div.z-widget-corona").first().select("div.z-corona-header").first().select(" a").first();
                    System.out.println("Link to widget: " + widget.attr("href"));
                }

                else if (e.className().equals("live")) {
                    System.out.println("Match'events: " + "\n");

                    List<Element> events = e.select("li");

                    int sort = 0;

                    for (int i = 0, k = events.size() - 1; i < events.size() && k > -1; i++, k--) {
                        Element event = (sort == 1) ? events.get(i) : events.get(k);

                        //Text
                        System.out.println(event.select("h3").text() + "\n\t" + event.select("p").text() );

                        //Images
                        for (Element table : event.select("table") ) {
                            System.out.println("Image link: " + table.select("img").first().attr("src"));
                        }

                        //Videos
                        for (Element figure : event.select("figure") ) {
                            //Background
                            System.out.println("Background Image: " + figure.select("div.video-container.formatted").first().attr("style").replace("background-image: url(", "").replace(");", "") );

                            //VideoLink
                            System.out.println("Link Video: " + figure.attr("data-video-src"));

                            //Cation + hyperlink
                            System.out.println("Caption: " + figure.select("figcaption > strong").text() + " ( " + "http://zingnews.vn"
                                    + figure.select("a").first().attr("href")+ " ) ");

                            //Detail
                            System.out.println(e.select("figcaption").text().replace(e.select("figcaption > strong").text(), "") + "\n");
                        }

                        if (event.className().equals("video") ) {
                            //System.out.println(event);

                            System.out.println("Background Image: " + event.select("video").first().attr("poster"));

                            System.out.println("Link video: " + event.select("video").first().attr("src") );
                        }

                        //Separate parts
                        System.out.println("------------------------------------------------------------------");
                    }
                }
            }

            //Video
            else if ( tag == "figure" ) {
                //Background
                System.out.println("Background Image: " +e.select("div.video-container.formatted").first().attr("style").replace("background-image: url(", "").replace(");", "") );

                //VideoLink
                System.out.println("Link Video: " + e.attr("data-video-src"));

                //Cation + hyperlink
                System.out.println("Caption: " + e.select("figcaption > strong").text() + " ( " + "http://zingnews.vn"
                        + e.select("a").first().attr("href")+ " ) ");

                //Detail
                System.out.println(e.select("figcaption").text().replace(e.select("figcaption > strong").text(), "") + "\n");

            }
        }
    }

    public static void bodyVNExpress(Elements body) {

        for (Element e : body) {
            //Paragraph
            if (e.tagName().equals("p")) {
                if (e.className().equals("Normal")) {
                    System.out.println(e.text() + "\n");
                }

            }

            //Something
            else if (e.tagName().equals("div")) {
                for (Element e2 : e.children()) {
                    //Paragraph
                    if (e2.tagName().equals("p") ) {
                        System.out.println(e2.select("p.Normal").text() + "\n");
                    }

                    //Image
                    if (e2.tagName().equals("figure") ) {
                        System.out.println("Link Image: " + e2.select("meta").first().attr("content"));

                        System.out.println("Caption: " + e2.select("p.Image").first().text() + "\n");
                    }
                }
            }

            //Image or Video
            else if (e.tagName().equals("figure") ) {
                //Image
                if ( e.attr("itemprop").equals("associatedMedia image") ) {
                    System.out.println("Link Image: " + e.select("meta").first().attr("content"));

                    System.out.println("Caption: " + e.select("p.Image").first().text() + "\n");
                }

                //Video
                else {
                    System.out.println(e.select("video").first().attr("src"));
                }
            }

        }
    }
}


