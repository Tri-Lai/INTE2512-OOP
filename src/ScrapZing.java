import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScrapZing {
    public static void main(String[] args) throws IOException {
        StopWatch clock = new StopWatch();

        clock.start();

        String url = "https://zingnews.vn/pho-thu-tuong-lay-nhiem-qua-he-thong-phan-phoi-rat-dang-bao-dong-post1246882.html";

        Document doc = Jsoup.connect(url).get();

        //title
        String header = "header.the-article-header";

        Element head = doc.select(header).first();

        System.out.println("Category is: " + head.select("p.the-article-category").text() + "\n");

        System.out.println("tile is: " + head.select("h1.the-article-title").text() + "\n");

        System.out.println("Author is: " + doc.select("div.the-article-credit p").text() + "\n");

        System.out.println("Publish time is: " + head.select("ul.the-article-meta").first().select("li.the-article-publish").text() + "\n");

        //Summary
        Elements sum = doc.select("p.the-article-summary");
        System.out.println("Summary is: \n" + sum.text() + "\n");

        //Content
        System.out.println("Content is: ");

        Elements body = doc.select("div.the-article-body").first().children();


        for (Element e1 : body) {
            String tag = e1.tagName();

            //Normal paragraph
            if (tag.equals("p") ) {
                System.out.println(e1.text() + "\n");
            }

            //Heading
            else if (tag == "h3") {
                System.out.println("\t" + e1.text() + "\n");
            }

            //Table
            else if (tag == "table") {
                if (e1.className().equals("picture")) {
                    Elements rows = e1.select("tbody > tr");

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

                } else if (e1.className().equals("article")) {
                    System.out.println("Related articles: ");

                    Element row = e1.select("tbody > tr > td > div.inner-article").first();

                    System.out.println(row.cssSelector() + "\n" + row);
                }
            }

            //Widget
            else if (tag == "div") {
                if (e1.className().equals("z-widget-corona")) {
                    Element widget = e1.select("div.z-widget-corona").first().select("div.z-corona-header").first().select(" a").first();
                    System.out.println("Link to widget: " + widget.attr("href"));
                }
            }
        }/*
        Elements rela = doc.getElementsByClass("the-article-body");

        for (Element e : rela) {
            System.out.println(e.cssSelector() + "\n" + e);
        }
        */
        clock.stop();
        System.out.print("\n" + "Time consume: " + clock.getElapsedTime() + " ms" + "\n");
    }
}
