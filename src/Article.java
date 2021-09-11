import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;

public class Article {
    public Article(String url, String avtUrl) throws Exception {
        //Create connection
        Connection connect = Jsoup.connect(url);

        //Change the timeout to avoid socket timeout exception
        connect.timeout(10000);

        //get Document
        doc = connect.get();

        id = checkWeb(url);
        iurl = url;

        pubD = setPubDay();

        img = avtUrl;


    }

    private String img;
    private final String iurl;

    public String getUrl() {
        return (iurl);
    }

    private final Document doc;

    private final int id;

    private final String pubD;

    private static final String[] category = {"p[class*=cate]"    //Zing
            , "ul.breadcrumb > li:nth-child(1)"     //VNExpress
            , "div.breadcrumbs"     //Thanh Nien
            , "div.bread-crumbs.fl > ul > li.fl:nth-child(1)"        //Tuoi Tre
            , "ul.uk-breadcrumb > li.bc-item:nth-child(1)"};        //Nhan Dan

    private static final String[] title = {"h1[class*=-title]"     //Zing
            , "h1.title-detail"     //VNExpress
            , "h1.details__headline"        //Thanh Nien
            , "h1.article-title"        //Tuoi Tre
            , "h1[class*=box-title-detail]"};       //Nhan Dan

    private static final String[] author = {"div.the-article-credit p"    //Zing
            , "p.author_mail"       //VNExpress
            , "div.details__author__meta "      //Thanh Nien
            , "div.author"      //Tuoi Tre
            , "div.box-author.uk-text-right.uk-clearfix"};      //Nhan Dan

    private static final String[] pubDay = {"li.the-article-publish"      //Zing
            , "span.date"       //VNExpress
            , "time"        //Thanh Nien
            , "div.date-time"       //Tuoi Tre
            , "div[class=box-date pull-left]"};        //Nhan Dan

    private static final String[] sum = {"p.the-article-summary"      //Zing
            , "p.description"       //VNExpress
            , "div.sapo"        //Thanh Nien
            , "h2.sapo"     //Tuoi Tre
            , "div.box-des-detail.this-one"};       //Nhan Dan

    private static final String[] body = {"div.the-article-body"      //Zing
            , "article.fck_detail"      //VNExpress
            , "div#abody"       //Thanh Nien
            , "div.content.fck"     //Tuoi Tre
            , "div.detail-content-body "};      //Nhan Dan

    //Check Website through url
    public Integer checkWeb(String url) {
        int id;

        //Zing
        if (url.contains("zingnews.vn")) {
            id = 0;
        }

        //VNExpress
        else if (url.contains("vnexpress.net")) {
            id = 1;
        }

        //Thanh Nien
        else if (url.contains("thanhnien.vn")) {
            id = 2;
        }

        //Tuoi Tre
        else if (url.contains("tuoitre.vn")) {
            id = 3;
        }

        //Nhan Dan
        else {
            id = 4;
        }

        return id;
    }

    public String getAvt() {
        if (img.isEmpty()) {
            //Waitingggggggggg
        }
        return img;
    }

    //Get Category
    public String getKWs() {
        //Get Keywords
        StringBuilder kw = new StringBuilder(doc.select("meta[name=keywords]").attr("content").toLowerCase());

        //Get Description
        kw.append(doc.select("meta[name=description]").attr("content").toLowerCase());

        //Get tags
        for (Element e : doc.select("meta[property=article:tag]")) {
            kw.append(e.attr("content").toLowerCase());
        }

        //Get Inside Category
        kw.append(doc.select(category[id]).text().toLowerCase());

        return kw.toString();
    }

    //Get Title
    public String getTitle() {
        String out;

        if (id == 1 && doc.select(title[id]).isEmpty()) {
            out = Objects.requireNonNull(doc.selectFirst("title")).text();
        } else if (id == 2 && doc.select(title[id]).isEmpty()) {
            out = Objects.requireNonNull(Objects.requireNonNull(doc.selectFirst("div.container")).selectFirst("img")).attr("src");
        } else if (id == 3 && doc.select(title[id]).isEmpty()) {
            out = Objects.requireNonNull(doc.selectFirst("div.sp-cover")).selectFirst("img").attr("src");
        } else {
            out = doc.selectFirst(title[id]).text();
        }


        //Check if there is label text
        if (id == 2 && !doc.select(title[id]).isEmpty()) {
            if (!doc.selectFirst(title[id]).select("label").isEmpty()) {
                out = out.replace(doc.selectFirst(title[id]).select("label").text() + " ", "");
            }
        }
        return out;
    }

    //Set Published Day
    public String setPubDay() throws Exception {
        String out;

        if (id == 0 && doc.select(pubDay[id]).isEmpty()) {
            out = doc.selectFirst("span.publish").text();
        } else if (id == 4 && doc.select(pubDay[id]).isEmpty()) {
            out = doc.select("div[class=box-date uk-text-center]").text();
        } else {
            out = doc.selectFirst(pubDay[id]).text();

        }
        out = out.replace(" (GMT+7)", "").replace(" GMT+7", "")
                .replaceAll("[^\\d/\\s:-]", "")
                .replaceAll("\\s{2}", "");

        if (id == 2) {
            String[] part = out.split("\\s");
            if (part.length > 2) {
                out = part[2] + " " + part[0];
            } else {
                out = out.replaceAll("[\\s-]", "");
            }

        } else if (id == 4) {
            out = out.replaceAll("-", "/");
        }
        return out;
    }

    public String getPubDay() {
        return pubD;
    }

    //Get Author Name
    public String getAuthor() {
        String name = "";

        //VNExpress
        if (id == 1) {
            if (doc.select(author[id]).text().isEmpty()) {
                name = doc.getElementsByAttributeValueMatching("style", "text-align:right;").text();

            } else {
                name = doc.select(author[id]).text();
            }
        }

        //ThanhNien if more than 1 author
        else if (doc.select("div.details__author__meta ").size() > 1) {
            for (int i = 0; i < doc.select("div.details__author__meta ").size(); i++) {
                name += " & " + doc.select(author[id]).get(i).selectFirst("h4 > a").text();
            }
        } else {
            name = doc.selectFirst(author[id]).text();
        }

        return name;
    }

    //Get Summary
    public String getSum() {
        String out = null;

        if (id == 4) {
            out += "Link image : " + doc.select("div.box-detail-thumb.uk-text-center > img").attr("src") + "\n";

            if (doc.selectFirst("div.box-detail-thumb.uk-text-center").childrenSize() > 1) {
                out += "Caption: " + doc.select("div.box-detail-thumb.uk-text-center > em").text() + "\n";
            }
        }
        out += doc.select(sum[id]).text() + "\n";

        if (id == 2 && !(doc.selectFirst("div[id*=contentAvatar]").select("img").isEmpty())) {
            out += "Link image: " + doc.selectFirst("div[id*=contentAvatar]").selectFirst("img").attr("src") + "\n";

            if (!(doc.selectFirst("div[id*=contentAvatar]").select("div[class*=caption]").isEmpty())) {
                Element cap = doc.selectFirst("div[id*=contentAvatar]").selectFirst("div[class*=caption]");
                out += "Caption: "
                        + cap.text().replace(cap.selectFirst("div.source").text(), "") + "\n"
                        + "Source: " + cap.selectFirst("div.source").text() + "\n";
            }
        }
        return out;
    }

    private String bodyContent = "";

    //Check and run body methods
    public String getBody() {
        String out = "";

        Element b;

        switch (id) {
            //Zing
            case 0:
                b = doc.selectFirst(body[id]);
                bodyZing(b);
                out = bodyContent;
                break;

            //VNExpress
            case 1:
                if (doc.select(author[id]).text().isEmpty()) {
                    //remove Author name
                    doc.getElementsByAttributeValueMatching("style", "text-align:right;").remove();

                    b = doc.selectFirst(body[id]);

                } else {
                    b = doc.selectFirst(body[id]);
                }
                bodyVNExpress(b);
                out = bodyContent;
                break;

            //Thanh Nien
            case 2:
                b = doc.selectFirst(body[id]);
                bodyTN(b);
                out = bodyContent;
                break;

            //Tuoi Tre
            case 3:
                b = doc.selectFirst(body[id]);
                bodyTT(b);
                out = bodyContent;
                break;

            //Nhan Dan
            case 4:
                b = doc.selectFirst(body[id]);
                bodyND(b);
                out = bodyContent;
                break;
        }
        return out;
    }

    //Get Body Zing
    public void bodyZing(Element body) {
        for (Element e : body.children()) {
            String tag = e.tagName();

            //Normal paragraph
            if (tag.equals("p")) {
                bodyContent += e.text() + "\n";
            }

            //Heading
            else if (tag == "h3") {
                bodyContent += "\t" + e.text() + "\n";
            }

            //Table
            else if (tag == "table") {
                //Picture
                if (e.className().equals("picture")) {
                    for (Element cell : e.select("tbody > tr > td")) {
                        //Link image
                        if (cell.className().equals("pic")) {
                            bodyContent += "Link Image: " + cell.select("img").attr("data-src") + "\n";
                        }

                        //Caption
                        else if (cell.className().equals("pCaption caption")) {
                            bodyContent += "Caption: " + cell.text() + "\n";
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
                    bodyContent += "Link to widget: " + widget.attr("href") + "\n";
                }

                //Live Score - Match Events
                else if (e.attr("id").contains("livestream")) {
                    bodyContent += "Match'events: " + "\n";

                    List<Element> events = e.select("li");

                    //Sort order
                    int sort = 1;

                    for (int i = 0, k = events.size() - 1; i < events.size() && k > -1; i++, k--) {
                        Element event = (sort == 1) ? events.get(i) : events.get(k);

                        //Text
                        bodyContent += event.select("h3").text() + "\n\t" + event.select("p").text() + "\n";

                        //Images
                        for (Element table : event.select("table")) {
                            bodyContent += "Link image: " + table.select("img").first().attr("src") + "\n";
                        }

                        //Videos
                        for (Element figure : event.select("figure")) {
                            //Background
                            bodyContent += "Background image: " + figure.select("div.video-container.formatted").first().attr("style").replace("background-image: url(", "").replace(");", "") + "\n";

                            //VideoLink
                            bodyContent += "Link Video: " + figure.attr("data-video-src");

                            //Cation + hyperlink
                            bodyContent += "Caption: " + figure.select("figcaption > strong").text() + " ( " + "http://zingnews.vn"
                                    + figure.select("a").first().attr("href") + " ) ";

                            //Detail
                            bodyContent += e.select("figcaption").text().replace(e.select("figcaption > strong").text(), "") + "\n";
                        }

                        if (event.className().equals("video")) {
                            bodyContent += "Background Image: " + event.select("video").first().attr("poster") + "\n";

                            bodyContent += "Link video: " + event.select("video").first().attr("src") + "\n";
                        }

                        //Separate parts
                        bodyContent += "------------------------------------------------------------------" + "\n";
                    }
                }
            }

            //Video
            else if (tag == "figure") {
                //Background
                bodyContent += "Background Image: " + e.select("div.video-container.formatted").first().attr("style").replace("background-image: url(", "").replace(");", "") + "\n";

                //VideoLink
                bodyContent += "Link Video: " + e.attr("data-video-src");

                //Cation + hyperlink
                bodyContent += "Caption: " + e.select("figcaption > strong").text() + " ( " + "http://zingnews.vn"
                        + e.select("a").first().attr("href") + " ) " + "\n";

                //Detail
                bodyContent += e.select("figcaption").text().replace(e.select("figcaption > strong").text(), "") + "\n";

            }
        }
    }

    //Get Body VNExpress
    public void bodyVNExpress(Element body) {
        for (Element e : body.children()) {
            //Paragraph
            if (e.tagName().equals("p")) {
                if (e.className().equals("Normal")) {
                    bodyContent += e.text() + "\n";
                }

            }

            //Something
            else if (e.tagName().equals("div")) {
                for (Element e2 : e.children()) {

                    //Paragraph
                    if (e2.tagName().equals("p") && e2.className().equals("Normal")) {
                        bodyContent += e2.select("p.Normal").text() + "\n";
                    }

                    //Image
                    else if (e2.tagName().equals("figure")) {
                        bodyContent += "Link image: " + e2.selectFirst("meta").attr("content") + "\n";

                        bodyContent += "Caption: " + e2.selectFirst("p.Image").text() + "\n";
                    }
                }
            }

            //Image or Video
            else if (e.tagName().equals("figure")) {
                //Image
                if (e.attr("itemprop").equals("associatedMedia image")) {
                    bodyContent += "Link Image: " + e.select("meta").first().attr("content") + "\n";

                    bodyContent += "Caption: " + e.select("p.Image").first().text() + "\n";
                }

                //Video
                else if (e.className().equals("item_slide_show clearfix")) {
                    //out += "Link video: " + e.selectFirst("video").attr("src") + "\n";

                    //out += "Caption: " + e.selectFirst("p.Image").text() + "\n";
                }
            }

            //Data Table
            else if (e.tagName().equals("table")) {
                bodyContent += "\t [ There is a data tabe. Function hasn't dont yet ]" + "\n";
            }

            //List
            else if (e.tagName().equals("ul")) {
                //Each Item
                for (Element item : e.select("li")) {
                    System.out.println("Related Article: ");

                    System.out.println("Link: " + item.select("a").attr("href"));

                    System.out.println("Title: " + item.select("a").attr("title") + "\n");
                }
            }
        }
    }

    //Get Body Thanh Nien
    public void bodyTN(Element body) {
        for (Element e : body.children()) {
            //Header
            if (e.tagName().equals("h2")) {
                bodyContent += "\t" + e.text() + "\n";
            }

            //Video
            else if (e.tagName().equals("table") && e.className().contains("video")) {
                bodyContent += "Link Video: " + e.select("div.clearfix.cms-video").attr("data-video-src") + "\n";
                System.out.println("Link Video: " + e.select("div.clearfix.cms-video").attr("data-video-src") + "\n");

                bodyContent += "Caption: " + e.select("div.imgcaption").text() + "\n";
            }

            //Image
            else if (e.tagName().equals("table") && e.className().contains("imagefull")) {
                bodyContent += "Link image: " + e.child(0).select("img").attr("data-src") + "\n";

                //Has Caption
                if (e.select("td > div").first().childrenSize() > 1) {
                    bodyContent += "Caption: " + e.selectFirst("div.imgcaption > p").text()
                            + ". Source: " + e.select("div.source > p").text() + "\n";
                }
            }

            //Quote??
            else if (e.className().contains("quote")) {
                bodyTN(e.selectFirst("div.quote__content").child(0));
            }

            //Part
            else if (e.tagName().equals("div")) {
                if (e.childrenSize() > 0) {
                    if (e.child(0).tagName().equals("a")) {
                        bodyContent += e.text() + "\n";
                    } else if (!e.tagName().equals("script") && !e.className().equals("details__morenews")) {
                        bodyTN(e);
                    }
                } else if (!e.tagName().equals("script") && !e.className().equals("details__morenews")) {
                    bodyContent += e.text() + "\n";
                }
            }
        }
    }

    //Get Body Tuoi Tre
    public void bodyTT(Element body) {
        for (Element e : body.children()) {

            //Normal Paragraph
            if (e.tagName().equals("p")) {
                if (e.childrenSize() > 0) {
                    for (Element e2 : e.children()) {
                        //Bold Characters
                        if (e2.tagName().equals("b")) {
                            if (e2.text().equals(e.text())) {
                                bodyContent += "[" + e2.text() + "]" + "\n";
                            }
                        } else {
                            bodyContent += e.text() + "\n";
                        }
                    }
                } else {
                    bodyContent += e.text() + "\n";
                }

            }

            //Part
            else if (e.tagName().equals("div")) {

                //Video
                if (e.attr("type").equals("VideoStream")) {
                    bodyContent += "Link Video: " + e.attr("data-src") + "\n";

                    bodyContent += "Caption: " + e.select("div.VideoCMS_Caption").text() + "\n";
                }

                //Image
                else if (e.attr("type").equals("Photo")) {
                    bodyContent += "Link image: " + e.selectFirst("img").attr("src") + "\n";
                    bodyContent += "Caption: " + e.select("div.PhotoCMS_Caption").text() + "\n";
                }

                //Wrap note
                else if (e.attr("type").equals("wrapnote")) {
                    bodyTT(e);
                }
            }
        }
    }

    //Get Body Nhan Dan
    public void bodyND(Element body) {
        for (Element e : body.children()) {
            //Normal Paragraph
            if (e.tagName().equals("p")) {
                bodyContent += e.text() + "\n";
            } else if (e.tagName().equals("div") && e.className().equals("light-img")) {
                bodyContent += "Link image: " + e.select("img").attr("src") + "\n";

                if (e.selectFirst("figure").childrenSize() > 1) {
                    bodyContent += "Caption: " + e.select("figcaption.img-cap").text() + "\n";
                }
            }

        }
    }
}


