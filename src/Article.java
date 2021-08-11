import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.List;

public class Article {
    public Article(String url) throws IOException {
        doc = Jsoup.connect(url).get();

        id = checkWeb(url);
    }

    Document doc;

    int id;

    //0.Zing    1.VNExpress
    private static String[] category = {"p.the-article-category", "ul.breadcrumb > li:nth-child(1)", "div.breadcrumbs"};

    private static String[] title = {"h1.the-article-title", "h1.title-detail", "h1.details__headline"};

    private static String[] author = {"div.the-article-credit p", "p.author_mail", "div.details__author__meta "};

    private static String[] pubDay = {"li.the-article-publish", "span.date", "time"};

    private static String[] sum = {"p.the-article-summary", "p.description", "div.sapo"};

    private static String[] body = {"div.the-article-body", "article.fck_detail"};

    //Check Website through url
    public Integer checkWeb(String url) throws IOException {
        int id = 0;

        //Zing
        if (url.contains("zingnews.vn")) {
            id = 0;
        }

        //VNExpress
        else if (url.contains("vnexpress.net")) {
            id = 1;
        }

        //Thanh Nien
        else if ( url.contains("thanhnien.vn") ) {
            id = 2;
        }

        return id;
    }

    //Get Category
    public String getCate() {
        return doc.select(category[id]).first().text();
    }

    //Get Title
    public  String getTitle() {
        return doc.select(title[id]).text();
    }

    //Get Published Day
    public String getPubDay() {
        // d/m/y
        return doc.select(pubDay[id]).text();
    }

    //Get Author Name
    public String getAuthor() {
        String name = "";

        //Zing
        if (id == 0  || id == 2) {
            name = doc.selectFirst(author[id]).text();
        }

        //VNExpress
        else if (id == 1) {
            if (doc.select(author[id]).text().isEmpty()) {
                name = doc.getElementsByAttributeValueMatching("style", "text-align:right;").text();

            } else {
                name = doc.select(author[id]).text();
            }
        }

        //Thanh Nien if more than 1 author
        if (doc.select("div.details__author__meta ").size() > 1 ) {
            for (int i = 1; i < doc.select("div.details__author__meta ").size(); i++) {
                name += " & " + doc.select(author[id]).get(i).selectFirst("h4 > a").text();
            }
        }

        return name;
    }

    //Get Summary
    public String getSum() {
        return doc.select(sum[id]).text();
    }

    //Check and run body methods
    public void getBody() {
        System.out.println("Content is: ");

        Elements b;

        switch (id) {
            //Zing
            case 0:
                b = doc.selectFirst(body[id]).children();

                bodyZing(b);
                break;

                //VNExpress
            case 1:
                if (doc.select(author[id]).text().isEmpty()) {
                    //remove Author name
                    doc.getElementsByAttributeValueMatching("style", "text-align:right;").remove();

                    b = doc.selectFirst(body[id]).children();

                } else {
                    b = doc.selectFirst(body[id]).children();
                }
                bodyVNExpress(b);
                break;
        }
    }

    //Get Body Zing
    public void bodyZing(Elements body) {
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
                    //Elements rows = e.select("tbody > tr > td");
                    for (Element cell : e.select("tbody > tr > td")) {
                        //Link image
                        if (cell.className().equals("pic") ) {
                            System.out.println("Picture link: " + cell.select("img").attr("data-src"));
                        }

                        //Caption
                        else if(cell.className().equals("pCaption caption") ) {
                            System.out.println("Caption: " + cell.text() + "\n");
                        }
                    }
                }
                //Related article
                else if (e.className().equals("article")) {
                    System.out.println("Related article: ");

                    System.out.println("\tLink: https://zingnews.vn" + e.select("tbody > tr > td > div.inner-article > a").first().attr("href"));

                    Elements row = e.select("tbody > tr > td > div.inner-article > a").first().children();

                    for (Element child : row) {
                        switch (child.className()) {
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

                //Live Score - Match Events
                else if (e.attr("id").contains("livestream")) {
                    System.out.println("Match'events: " + "\n");

                    List<Element> events = e.select("li");

                    //Sort order
                    int sort = 1;

                    for (int i = 0, k = events.size() - 1; i < events.size() && k > -1; i++, k--) {
                        Element event = (sort == 1) ? events.get(i) : events.get(k);

                        //Text
                        System.out.println(event.select("h3").text() + "\n\t" + event.select("p").text());

                        //Images
                        for (Element table : event.select("table")) {
                            System.out.println("Image link: " + table.select("img").first().attr("src"));
                        }

                        //Videos
                        for (Element figure : event.select("figure")) {
                            //Background
                            System.out.println("Background Image: " + figure.select("div.video-container.formatted").first().attr("style").replace("background-image: url(", "").replace(");", ""));

                            //VideoLink
                            System.out.println("Link Video: " + figure.attr("data-video-src"));

                            //Cation + hyperlink
                            System.out.println("Caption: " + figure.select("figcaption > strong").text() + " ( " + "http://zingnews.vn"
                                    + figure.select("a").first().attr("href") + " ) ");

                            //Detail
                            System.out.println(e.select("figcaption").text().replace(e.select("figcaption > strong").text(), "") + "\n");
                        }

                        if (event.className().equals("video")) {
                            //System.out.println(event);

                            System.out.println("Background Image: " + event.select("video").first().attr("poster"));

                            System.out.println("Link video: " + event.select("video").first().attr("src"));
                        }

                        //Separate parts
                        System.out.println("------------------------------------------------------------------");
                    }
                }
            }

            //Video
            else if (tag == "figure") {
                //Background
                System.out.println("Background Image: " + e.select("div.video-container.formatted").first().attr("style").replace("background-image: url(", "").replace(");", ""));

                //VideoLink
                System.out.println("Link Video: " + e.attr("data-video-src"));

                //Cation + hyperlink
                System.out.println("Caption: " + e.select("figcaption > strong").text() + " ( " + "http://zingnews.vn"
                        + e.select("a").first().attr("href") + " ) ");

                //Detail
                System.out.println(e.select("figcaption").text().replace(e.select("figcaption > strong").text(), "") + "\n");

            }
        }
    }

    //Get Body VNExpress
    public void bodyVNExpress(Elements body) {
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
                    if (e2.tagName().equals("p") && e2.className().equals("Normal") ) {
                        System.out.println(e2.select("p.Normal").text() + "\n");
                    }

                    //Image
                    else if (e2.tagName().equals("figure")) {
                        System.out.println("Link Image: " + e2.selectFirst("meta").attr("content"));

                        System.out.println("Caption: " + e2.selectFirst("p.Image").text() + "\n");
                    }
                }
            }

            //Image or Video
            else if (e.tagName().equals("figure")) {
                //Image
                if (e.attr("itemprop").equals("associatedMedia image")) {
                    System.out.println("Link Image: " + e.select("meta").first().attr("content"));

                    System.out.println("Caption: " + e.select("p.Image").first().text() + "\n");
                }

                //Video
                else if (e.className().equals("item_slide_show clearfix") ) {
                    System.out.println("Link video: " + e.selectFirst("video").attr("src"));

                    System.out.println("Caption: " + e.selectFirst("p.Image").text() + "\n");
                }
            }

            //Data Table
            else if (e.tagName().equals("table") ) {
                System.out.println("\t [ There is a data tabe. Function hasn't dont yet ]" + "\n");
            }

            //List
            else if ( e.tagName().equals("ul") ) {
                //Each Item
                for (Element item : e.select("li") ) {
                    System.out.println("Related Article: ");

                    System.out.println("Link: " + item.select("a").attr("href") );

                    System.out.println("Title: " + item.select("a").attr("title") + "\n");
                }
            }
        }
    }
}


