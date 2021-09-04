import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Category {
    public Category() {
    }

    private ArrayList<Article> New = new ArrayList<>();

    private ArrayList<Article> Covid = new ArrayList<>();

    private ArrayList<Article> Pol = new ArrayList<>();

    private ArrayList<Article> Busi = new ArrayList<>();

    private ArrayList<Article> Tech = new ArrayList<>();

    private ArrayList<Article> Health = new ArrayList<>();

    private ArrayList<Article> Sport = new ArrayList<>();

    private ArrayList<Article> Entertain = new ArrayList<>();

    private ArrayList<Article> World = new ArrayList<>();

    private ArrayList<Article> Other = new ArrayList<>();

    private static final String[] source = {"zingnews",
            "thanhnien",
            "tuoitre",
            "nhandan",
            "vnexpress"};
    private static final String[] link = {"https://zingnews.vn/",
            "https://thanhnien.vn/",
            "https://tuoitre.vn/",
            "https://nhandan.vn/",
            "https://vnexpress.net/"};

    //Get the specified Category list
    public ArrayList<Article> getList(String name) {
        ArrayList<Article> out = new ArrayList<>();

        switch ( name ) {
            case "new":
                out = New;
                break;

            case "covid":
                out = Covid;
                break;

            case "politic":
                out = Pol;
                break;

            case "business":
                out = Busi;
                break;

            case "tech":
                out = Tech;
            break;

            case "health":
                out =  Health;
            break;

            case "sport":
                out =  Sport;
            break;

            case "entertain":
                out =  Entertain;
            break;

            case "world":
                out = World;
                break;

            case "other":
                out = Other;
                break;
        }

        return out;
    }

    public void getNum () {
        System.out.println("New: " + New.size() + " Covid: " + Covid.size() + " Politic: " + Pol.size()
                + " Business: " + Busi.size() + " Tech: " + Tech.size() + " Health: " + Health.size()
                + " Sport: " + Sport.size() + " Entertain: " + Entertain.size() + " World: " + World.size()
                + " Other: " + Other.size());
    }

    //Scrap articles for specified Category
    public void setCate(String cate) throws IOException {
        StopWatch clock = new StopWatch();
        clock.start();

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int need = 0;

        switch (cate) {
            case "new":
                need = 40;  break;

            case "covid":
                need = 40;
                break;

            case "politic":
                if (Pol.size() < 50) {
                    if ( ((50 - Pol.size() ) %5) > 0 ) {
                        need = ((50 - Pol.size() ) /5) + 1;
                    }
                    else {
                        need = (50 - Pol.size() ) /5;
                    }
                }
                break;

            case "business":
                if (Busi.size() < 50 && ( ((50 - Busi.size() ) %5) > 0 )) {
                        need = ((50 - Busi.size() ) /5) + 1;
                }
                else {
                    need = (50 - Busi.size() ) /5;
                }
                break;

            case "tech":
                if (Tech.size() < 50 && ( ((50 - Tech.size() ) %5) > 0 )) {
                    need = ((50 - Tech.size() ) /5) + 1;
                }
                else {
                    need = (50 - Tech.size() ) /5;
                }
                break;

            case "health":
                if (Health.size() < 50 && ( ((50 - Health.size() ) %5) > 0 )) {
                    need = ((50 - Health.size() ) /5) + 1;
                }
                else {
                    need = (50 - Health.size() ) /5;
                }
                break;

            case "sport":
                if (Sport.size() < 50 && ( ((50 - Sport.size() ) %5) > 0 )) {
                    need = ((50 - Sport.size() ) /5) + 1;
                }
                else {
                    need = (50 - Sport.size() ) /5;
                }
                break;

            case "entertain":
                if (Entertain.size() < 50 && ( ((50 - Entertain.size() ) %5) > 0 )) {
                    need = ((50 - Entertain.size() ) /5) + 1;
                }
                else {
                    need = (50 - Entertain.size() ) /5;
                }
                break;

            case "world":
                if (World.size() < 50 && ( ((50 - World.size() ) %5) > 0 )) {
                    need = ((50 - World.size() ) /5) + 1;
                }
                else {
                    need = (50 - World.size() ) /5;
                }
                break;

            case "other":
                need = 40;
                break;
        }
        for (int id = 0; id <= 4; id++) {
            int finalId = id;

            int finalNeed = need;
            es.execute( () -> {
                try {
                    getFrom(finalNeed, cate, source[finalId], link[finalId]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(source[finalId] + ": " + clock.getElapsedTime());
            });
        }
        //Stop getting task
        es.shutdown();

        //wait to all task completed
        while ( !es.isTerminated() ) {}

        System.out.print("\n" + "Time setCate() consume: " + clock.getElapsedTime() + " ms" + "\n");
    }

    //Get the Number of article of Cate from Src which have homepage link is hpl
    private void getFrom(int number, String cate, String src, String hpl) throws IOException {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        File hP = new File("Downloads/" + src + ".html");

        Document doc = Jsoup.parse(hP, "UTF-8", hpl);

        //Check and get URl of category
        String urlcate = getUrlCate(doc, cate, hpl);

        int limit = number;

        //if urlcate is empty then scrap from home page
        if ( urlcate.isEmpty()) {
            urlcate = hpl;
            limit = 40;
        }
        if (cate.equals("new") || cate.equals("other") ) {
            limit = 40;
        }

        System.out.println(urlcate);

        //Get Category page
        doc = Jsoup.connect(urlcate).get();

        List<Element> list;
        boolean isTuoiTre;

        //Vne, Zing, TN, ND
        if ( !doc.select("article").isEmpty() ) {
             list = doc.select("article");
             isTuoiTre = false;
        }

        //Tuoi Tre
        else {
            if ( !doc.select("div.box-tournament.box-worldcup-2018").isEmpty() ) {
                doc.select("div.box-tournament.box-worldcup-2018").remove();
            }
            list = Objects.requireNonNull(doc.selectFirst("section[id*=content]")).select("a[href*=/][title~=[a-z]]");
            isTuoiTre = true;
        }
        System.out.println(list.size());

        for (int id = 0; id < list.size(); id++) {
            Element e = list.get(id);

            if ( !isTuoiTre ) {
                //avoid the blank
                if (e.child(0).tagName().equals("ins")) {
                    continue;
                }
                else if ( !e.select("a").isEmpty() ) {
                    if ( Objects.requireNonNull(e.selectFirst("a")).attr("href").equals("") ) {
                        continue;
                    }
                }
                else if ( e.child(1).tagName().equals("ins") ) {
                    id++;
                    e = list.get(id);
                }
            }

            //Filter for tuoi tre
            if (e.select("img").isEmpty() && isTuoiTre) {
                continue;
            }

            String avt = "";

            //Check and get Avatar Image Link
            if (!(e.select("img").isEmpty())) {
                    avt = Objects.requireNonNull(e.selectFirst("img").attr("src") );

                    //Check image link
                    if (!(avt.contains("https:")) && !(avt.contains(".jpeg"))) {
                        avt = Objects.requireNonNull(e.selectFirst("img").attr("data-src") );
                    }
                }

            //Get Article Link
            String url = e.selectFirst("a").attr("href");

            //Check to complete url
            if (!url.contains(hpl)) {
                    url = new StringBuffer(hpl).deleteCharAt(hpl.length() - 1) + url;
                }

            //System.out.println(id + ": " + url);

            //dont get the special articles
            if ( url.contains("https://special.nhandan.vn/") ) {
                continue;
            }

            String finalAvt = avt;
            String finalUrl = url;

            es.execute( () -> {
                try {
                    Article a = new Article(finalUrl, finalAvt);
                    //System.out.println(finalUrl + "\n" +  finalAvt);
                    //Check if it belongs to any other Cate and add
                    check2Add(a.getKWs().toLowerCase(), a, cate);
                } catch (Exception ex) {};
            });

            //Stop when scraped 10 Article
            if (id == limit || id == list.size()-1) {
                System.out.println("blue");
                es.shutdown();
                break;
            }
        }
        es.shutdown();

        while ( !es.isTerminated() ) {}
    }

    //Get url of page of specified category
    private String getUrlCate(Document doc, String cate, String hpl) {
        String out = "";

        //Get category hyperlink
        switch (cate) {
            case "new": case "other": {
                for ( Element e : doc.select("a[href*=/]") ) {
                    if (e.text().toLowerCase().contains("tin mới") || e.text().equalsIgnoreCase("mới nhất")) {
                        out = e.attr("href");
                        break;
                    }
                }
                break;
            }
            //category Politic
            case "politic": {
                //Check
                if (!(doc.select("a[href*=chinh-tri]").isEmpty())) {
                    out = doc.selectFirst("a[href*=chinh-tri]").attr("href");
                } else if (!doc.select("a[href*=chinhtri]").isEmpty()) {
                    out = doc.selectFirst("a[href*=chinhtri]").attr("href");
                }

                //Use "Phap Luat" category instead
                if (out.isEmpty()) {
                    if (!(doc.select("a[href*=phap-luat]").isEmpty())) {
                        out = doc.selectFirst("a[href*=phap-luat]").attr("href");
                    } else if (!doc.select("a[href*=phapluat]").isEmpty()) {
                        out = doc.selectFirst("a[href*=phapluat]").attr("href");
                    }
                }

                break;
            }

            //Category Business
            case "business": {
                if (!doc.select("a[href*=kinh-doanh]").isEmpty()) {
                    for (Element e : doc.select("a[href*=kinh-doanh]") ) {
                        if (e.text().equalsIgnoreCase("kinh doanh") || e.text().equalsIgnoreCase("tài chính - kinh doanh")) {
                            out = e.attr("href");

                            if (out.contains(hpl) ) {
                                break;
                            }
                        }
                    }
                }
                else if (!(doc.select("a[href*=kinhdoanh]").isEmpty())) {
                    for (Element e : doc.select("a[href*=kinhdoanh]") ) {
                        if (e.text().equalsIgnoreCase("kinh doanh") || e.text().equalsIgnoreCase("tài chính - kinh doanh")) {
                            out = e.attr("href");

                            if (out.contains(hpl) ) {
                                break;
                            }
                        }
                    }
                }

                if (out.isEmpty()) {
                    if (!(doc.select("a[href*=kinhte]").isEmpty())) {
                        out = doc.selectFirst("a[href*=kinhte]").attr("href");
                    } else if (!doc.select("a[href*=kinh-te]").isEmpty()) {
                        out = doc.selectFirst("a[href*=kinh-te]").attr("href");
                    }
                }

                break;
            }

            //Category Technology
            case "tech": {
                if (!doc.select("a[href*=congnghe]").isEmpty()) {
                    out = doc.selectFirst("a[href*=congnghe]").attr("href");
                }
                else if (!(doc.select("a[href*=cong-nghe]").isEmpty())) {
                    out = doc.selectFirst("a[href*=cong-nghe]").attr("href");
                }
                break;
            }

            //Category Health
            case "health": {
                if (!(doc.select("a[href*=suc-khoe]").isEmpty())) {
                    out = doc.selectFirst("a[href*=suc-khoe]").attr("href");
                } else if (!doc.select("a[href*=suckhoe]").isEmpty()) {
                    out = doc.selectFirst("a[href*=suckhoe]").attr("href");
                }

                break;
            }

            //Category Sport
            case "sport": {
                if (!(doc.select("a[href*=thethao]").isEmpty())) {
                    out = doc.selectFirst("a[href*=thethao]").attr("href");
                } else if (!doc.select("a[href*=the-thao]").isEmpty()) {
                    out = doc.selectFirst("a[href*=the-thao]").attr("href");
                }

                break;
            }

            //Entertainment
            case "entertain": {
                if (!doc.select("a[href*=giaitri]").isEmpty()) {
                    for (Element e : doc.select("a[href*=giaitri]") ) {
                        if (e.text().equalsIgnoreCase("giải trí")) {
                            out = e.attr("href");

                            if (!out.contains("video") ) {
                                break;
                            }
                        }
                    }
                }
                else if (!(doc.select("a[href*=giai-tri]").isEmpty())) {
                    for (Element e : doc.select("a[href*=giai-tri]") ) {
                        if (e.text().equalsIgnoreCase("giải trí")) {
                            out = e.attr("href");

                            if (!out.contains("video") ) {
                                break;
                            }
                        }
                    }
                }

                //Replace by Van Hoa
                if ( out.isEmpty() ) {
                    if (!(doc.select("a[href*=vanhoa]").isEmpty()) ) {
                        for (Element e : doc.select("a[href*=vanhoa]")) {
                            if (e.text().equalsIgnoreCase("văn hóa")) {
                                out = e.attr("href");

                                if (out.contains(hpl)) {
                                    break;
                                }
                            }
                        }
                    }
                    else if (!(doc.select("a[href*=van-hoa]").isEmpty())) {
                        for (Element e : doc.select("a[href*=van-hoa]")) {
                            if (e.text().equalsIgnoreCase("văn hoá")) {
                                out = e.attr("href");

                                if (out.contains(hpl)) {
                                    break;
                                }
                            }
                        }
                    }
                }

                break;
            }

            //World
            case "world": {
                if (out.isEmpty()) {
                    if (!(doc.select("a[href*=the-gioi]").isEmpty())) {
                        out = doc.selectFirst("a[href*=the-gioi]").attr("href");
                    } else if (!doc.select("a[href*=thegioi]").isEmpty()) {
                        out = doc.selectFirst("a[href*=thegioi]").attr("href");
                    }
                }
                break;
            }
        }

        return out;
    }

    //Check Keywords: kw to add to List(s)
    private void check2Add(String kw, Article article, String cate) {
        boolean sig = false;

        if (New.size() < 100 ) {
            New.add(article);
        }

        //Check if Covid
        if (kw.contains("covid-19") || kw.contains("chống dịch") || kw.contains("vaccine") || kw.contains("f0")
                || kw.contains("dịch bệnh") || kw.contains("ncov")  ) { //|| cate.equals("covid")
            if (Covid.size() < 50) {
                Covid.add(article);
            }
            sig = true;
        }

        //Check if Politic
        if (kw.contains("chính trị") || kw.contains("đảng") || kw.contains("chính phủ") || kw.contains("quốc hội")
                || kw.contains("thủ tướng") || kw.contains("chủ tịch") || kw.contains("bộ y tế")
                || kw.contains("bộ công an") || cate.equals("politic")) {
            if (Pol.size() < 50) {
                Pol.add(article);
            }
            sig = true;
        }

        //Check if Business
        if (kw.contains("kinh doanh") || cate.equals("business")) {
            if (Busi.size() < 50) {
                Busi.add(article);
            }
            sig = true;
        }

        //Check if Technology
        if (kw.contains("công nghệ") || kw.contains("internet") || kw.contains("số hóa") || cate.equals("tech")) {
            if (Tech.size() < 50) {
                Tech.add(article);
            }
            sig = true;
        }

        //Check if Health
        if (kw.contains("sức khỏe") || cate.equals("health")) {
            if (Health.size() < 50) {
                Health.add(article);
            }
            sig = true;
        }

        //Check if Sport
        if (kw.contains("thể thao") || kw.contains("tuyển việt nam") || kw.contains("hlv") || kw.contains("cầu thủ")
                || kw.contains("bóng đá") || cate.equals("sport")) {
            if (Sport.size() < 50) {
                Sport.add(article);
            }
            sig = true;
        }

        //Check if Entertainment
        if (kw.contains("giải trí") || kw.contains("âm nhạc") || kw.contains("phim ảnh") || cate.equals("entertain")) {
            if (Entertain.size() < 50) {
                Entertain.add(article);
            }
            sig = true;
        }

        //Check if World
        if (kw.contains("thế giới") || cate.equals("world")) {
            if (World.size() < 50) {
                World.add(article);
            }
        }

        //Check if article hasn't belong to any Cate
        else if (sig == false) {
            if (Other.size() < 50) {
            Other.add(article);
        }
        }
    }

    public void sort () {
        StopWatch clock1 = new StopWatch();
        clock1.start();
        Collections.sort(New, (d1, d2) -> {
            try {
                // Check if published date of one of two article is null so that throw the NullPointerException
                if (d1.getPubDay() == null || d2.getPubDay() == null)
                    throw new Exception();
                // Return the compared value of the Article object in descending order.
                return d2.getPubDay().compareTo(d1.getPubDay());
            } catch (Exception e) {
                throw new NullPointerException("Date Error: Date is empty!");
            }
        });
        System.out.print("\n" + "Time sort() consume: " + clock1.getElapsedTime() + " ms" + "\n");
    }
}
