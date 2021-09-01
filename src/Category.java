
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Category {
    public Category() throws IOException {
    }

    private ArrayList<Article> New = new ArrayList<>();

    private ArrayList<Article> Covid = new ArrayList<>();

    private ArrayList<Article> Pol = new ArrayList<>();

    private ArrayList<Article> Busi = new ArrayList<Article>();

    private ArrayList<Article> Tech = new ArrayList<Article>();

    private ArrayList<Article> Health = new ArrayList<Article>();

    private ArrayList<Article> Sport = new ArrayList<Article>();

    private ArrayList<Article> Entertain = new ArrayList<Article>();

    private ArrayList<Article> World = new ArrayList<Article>();

    private ArrayList<Article> Other = new ArrayList<Article>();

    private static String[] source = {"zingnews",
            "thanhnien",
            "tuoitre",
            "nhandan",
            "vnexpress"};
    private static String[] link = {"https://zingnews.vn/",
            "https://thanhnien.vn/",
            "https://tuoitre.vn/",
            "https://nhandan.vn/",
            "https://vnexpress.net/"};

    public ArrayList<Article> getList(String name) {
        ArrayList<Article> out = new ArrayList<Article>();

        switch ( name ) {
            case "covid":
                out = Covid;
                break;

            case "pol":
                out = Pol;
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

            case "Entertain":
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
        System.out.println("Covid: " + Covid.size() + " Politic: " + Pol.size()
                + " Business: " + Busi.size() + " Tech: " + Tech.size() + " Health: " + Health.size()
                + " Sport: " + Sport.size() + " Entertain: " + Entertain.size() + " World: " + World.size());
    }

    public void setCate(String cate) throws IOException {
        StopWatch clock = new StopWatch();
        clock.start();

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        es.execute( () -> {
            try {
                getFrom(10, cate, source[0], link[0]);
            } catch (IOException e) {}
            System.out.println("Zing: " + clock.getElapsedTime());
        });

        es.execute( () -> {
            try {
                getFrom(10, cate, source[1], link[1]);
            } catch (IOException e) {}
            System.out.println("Thanh Nien: " + clock.getElapsedTime());
        });

        es.execute( () -> {
            try {
                getFrom(10, cate, source[3], link[3]);
            } catch (IOException e) {}
            System.out.println("Nhan Dan: " + clock.getElapsedTime());
        });

        es.execute( () -> {
            try {
                getFrom(5, cate, source[4], link[4]);
            } catch (IOException e) {}
            System.out.println("VNE: " + clock.getElapsedTime());
        });

        es.execute( () -> {
            try {
                getFrom(5, cate, source[4], link[4]);
            } catch (IOException e) {}
            System.out.println("VNE: " + clock.getElapsedTime());
        });

        es.shutdown();
        while(!es.isTerminated()) {};

        System.out.print("\n" + "Time consume: " + clock.getElapsedTime() + " ms" + "\n");
    }

    private void getFrom(int number, String cate, String src, String hpl) throws IOException {
        File hP = new File("Downloads/" + src + ".html");

        Document doc = Jsoup.parse(hP, "UTF-8", hpl);

        int count = 1;

        //Check and get URl of category
        String urlcate = getUrlCate(doc, cate, hpl);

        doc = Jsoup.connect(urlcate).get();

        //Get each Article
        for (Element e : doc.select("article")) {
            String avt = "";

            //Check and get Avatar Image Link
            if (!(e.select("img").isEmpty())) {
                avt = e.selectFirst("img").attr("src");

                //Check image link
                if (!(avt.contains("https:")) && !(avt.contains(".jpeg"))) {
                    avt = e.selectFirst("img").attr("data-src");
                }
            }

            //Get Article Link
            String url = e.selectFirst("a").attr("href");

            //Check to complete url
            if (!url.contains(hpl)) {
                url = new StringBuffer(hpl).deleteCharAt(hpl.length() - 1) + url;
            }

            //Create Article from link
            Article a = new Article(url, avt);

            //Check if it belongs to any other Cate and add
            check2Add(a.getKWs().toLowerCase(), a);

            //System.out.printf("%d: %s%n%s%n%n", Tech.size(), url, a.getKWs());

            //Stop when scraped 10 Article
            if (count == number) {
                break;
            }

            count++;
        }
    }

    private String getUrlCate(Document doc, String cate, String hpl) throws IOException {
        String out = "";

        //Get category hyperlink
        switch (cate) {
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
                if (!(doc.select("a[href*=kinh-doanh]").isEmpty())) {
                    out = doc.selectFirst("a[href*=kinh-doanh]").attr("href");
                } else if (!doc.select("a[href*=kinhdoanh]").isEmpty()) {
                    out = doc.selectFirst("a[href*=kinhdoanh]").attr("href");
                }

                break;
            }

            //Category Technology
            case "tech": {
                if (!(doc.select("a[href*=cong-nghe]").isEmpty())) {
                    out = doc.selectFirst("a[href*=cong-nghe]").attr("href");
                } else if (!doc.select("a[href*=congnghe]").isEmpty()) {
                    out = doc.selectFirst("a[href*=congnghe]").attr("href");
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
                if (!(doc.select("a[href*=the-thao]").isEmpty())) {
                    out = doc.selectFirst("a[href*=the-thao]").attr("href");
                } else if (!doc.select("a[href*=thethao]").isEmpty()) {
                    out = doc.selectFirst("a[href*=thethao]").attr("href");
                }

                break;
            }

            //Entertainment
            case "entertain": {
                if (!(doc.select("a[href*=giai-tri]").isEmpty())) {
                    out = doc.selectFirst("a[href*=giai-tri]").attr("href");
                } else if (!doc.select("a[href*=giaitri]").isEmpty()) {
                    out = doc.selectFirst("a[href*=giaitri]").attr("href");
                }

                if (out.isEmpty()) {
                    if (!(doc.select("a[href*=van-hoa]").isEmpty())) {
                        out = doc.selectFirst("a[href*=van-hoa]").attr("href");
                    } else if (!doc.select("a[href*=vanhoa]").isEmpty()) {
                        out = doc.selectFirst("a[href*=vanhoa]").attr("href");
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
            }
        }

        //Check to complete url
        if (!out.contains(hpl)) {
            out = new StringBuffer(hpl).deleteCharAt(hpl.length() - 1) + out;
        }

        return out;
    }

    private void check2Add(String kw, Article article) {
        byte sig = 0;

        //Check if Covid
        if (kw.contains("covid-19") || kw.contains("chống dịch") || kw.contains("vaccine") || kw.contains("f0")
                || kw.contains("dịch bệnh") || kw.contains("ncov")) {
            if (Covid.size() < 50) {
                Covid.add(article);
            }
            sig = 1;
        }

        //Check if Politic
        if (kw.contains("chính trị") || kw.contains("đảng") || kw.contains("chính phủ") || kw.contains("quốc hội")
                || kw.contains("thủ tướng") || kw.contains("chủ tịch") || kw.contains("bộ y tế")
                || kw.contains("bộ công an")) {
            if (Pol.size() < 50) {
                Pol.add(article);
            }
            sig = 1;
        }

        //Check if Business
        if (kw.contains("kinh doanh")) {
            if (Busi.size() < 50) {
                Busi.add(article);
            }
            sig = 1;
        }

        //Check if Technology
        if (kw.contains("công nghệ") || kw.contains("internet") || kw.contains("số hóa")) {
            if (Tech.size() < 50) {
                Tech.add(article);
            }
            sig = 1;
        }

        //Check if Health
        if (kw.contains("sức khỏe")) {
            if (Health.size() < 10) {
                Health.add(article);
            }
            sig = 1;
        }

        //Check if Sport
        if (kw.contains("thể thao")) {
            if (Sport.size() < 10) {
                Sport.add(article);
            }
            sig = 1;
        }

        //Check if Entertainment
        if (kw.contains("giải trí") || kw.contains("âm nhạc") || kw.contains("phim ảnh")) {
            if (Entertain.size() < 10) {
                Entertain.add(article);
            }
            sig = 1;
        }

        //Check if World
        if (kw.contains("thế giới")) {
            if (World.size() < 10) {
                World.add(article);
            }
        }

        //Check if article hasn't belong to any Cate
        else if (sig == 0 && (Other.size() < 10)) {
            Other.add(article);
        }
    }

}
