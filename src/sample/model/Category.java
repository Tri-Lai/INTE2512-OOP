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

package sample.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import sample.model.Article;
import sample.ultilities.StopWatch;

import java.io.*;

import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Category {
    public Category() {
    }

    //Create Lists for each category
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

    //Name of sources
    private static final String[] source = {"zingnews",
            "thanhnien",
            "tuoitre",
            "nhandan",
            "vnexpress"};

    //Links of HomePage
    private static final String[] link = {"https://zingnews.vn/",
            "https://thanhnien.vn/",
            "https://tuoitre.vn/",
            "https://nhandan.vn/",
            "https://vnexpress.net/"};

    //Get the specified Category list
    public ArrayList<Article> getList(String name) {
        //Create temp List
        ArrayList<Article> out = new ArrayList<>();

        //Check input to get name of category for output
        switch (name) {
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
                out = Health;
                break;

            case "sport":
                out = Sport;
                break;

            case "entertain":
                out = Entertain;
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

    //For checking number of articles in each category
    public void getNum() {
        System.out.println("New: " + New.size() + " Covid: " + Covid.size() + " Politic: " + Pol.size()
                + " Business: " + Busi.size() + " Tech: " + Tech.size() + " Health: " + Health.size()
                + " Sport: " + Sport.size() + " Entertain: " + Entertain.size() + " World: " + World.size()
                + " Other: " + Other.size());
    }

    //Scrap articles for specified Category
    public void setCate(String cate) throws IOException {
        StopWatch clock = new StopWatch();
        clock.start();

        //Using executor service
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int need = 0;

        //Check input string to get name of needed category
        //Set needed number of article for scrapping
        switch (cate) {
            case "new":
            case "covid":
                need = 40;
                break;

            case "politic":
                if (Pol.size() < 50) {
                    if (((50 - Pol.size()) % 5) > 0) {
                        need = ((50 - Pol.size()) / 5) + 1;
                    } else {
                        need = (50 - Pol.size()) / 5;
                    }
                }
                break;

            case "business":
                if (Busi.size() < 50 && (((50 - Busi.size()) % 5) > 0)) {
                    need = ((50 - Busi.size()) / 5) + 1;
                } else {
                    need = (50 - Busi.size()) / 5;
                }
                break;

            case "tech":
                if (Tech.size() < 50 && (((50 - Tech.size()) % 5) > 0)) {
                    need = ((50 - Tech.size()) / 5) + 1;
                } else {
                    need = (50 - Tech.size()) / 5;
                }
                break;

            case "health":
                if (Health.size() < 50 && (((50 - Health.size()) % 5) > 0)) {
                    need = ((50 - Health.size()) / 5) + 1;
                } else {
                    need = (50 - Health.size()) / 5;
                }
                break;

            case "sport":
                if (Sport.size() < 50 && (((50 - Sport.size()) % 5) > 0)) {
                    need = ((50 - Sport.size()) / 5) + 1;
                } else {
                    need = (50 - Sport.size()) / 5;
                }
                break;

            case "entertain":
                if (Entertain.size() < 50 && (((50 - Entertain.size()) % 5) > 0)) {
                    need = ((50 - Entertain.size()) / 5) + 1;
                } else {
                    need = (50 - Entertain.size()) / 5;
                }
                break;

            case "world":
                if (World.size() < 50 && (((50 - World.size()) % 5) > 0)) {
                    need = ((50 - World.size()) / 5) + 1;
                } else {
                    need = (50 - World.size()) / 5;
                }
                break;

            case "other":
                need = 40;
                break;
        }

        //loop through 5 sources
        for (int id = 0; id <= 4; id++) {
            int finalId = id;

            int finalNeed = need;

            //Give tasks for executor service
            es.execute(() -> {
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
        while (!es.isTerminated()) {
        }

        System.out.print("\n" + "Time setCate() consume: " + clock.getElapsedTime() + " ms" + "\n");
    }

    //Get the Number of article of Cate from Src which have homepage link is hpl
    private void getFrom(int number, String cate, String src, String hpl) throws IOException {
        //Create an executor service
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //get homepage file depends on source
        File hP = new File("src/sample/downloads/" + src + ".html");

        //Parsing file to document
        Document doc = Jsoup.parse(hP, "UTF-8", hpl);

        //Check and get URl of category
        String urlcate = getUrlCate(doc, cate, hpl);

        //if urlcate is empty then scrap from home page
        if (urlcate.isEmpty()) {
            urlcate = hpl;
        }

        boolean err = true;

        //keep get Category page
        while (err) {
            try {
                doc = Jsoup.connect(urlcate).get();
                err = false;
            } catch (SocketTimeoutException ste) {
                System.out.println("Socket timeout excetion occurs");

                doc = Jsoup.connect(urlcate).get();
            }
        }

        List<Element> list;
        boolean isTuoiTre;

        //Get list of article for Vne, Zing, TN, ND
        if (!doc.select("article").isEmpty()) {
            //Remove special articles
            if ( !doc.select("div[class*=area area--dark has-margin]").isEmpty() ) {
                doc.select("div[class*=area area--dark has-margin]").remove();
            }

            //Get list of article
            list = doc.select("article");

            isTuoiTre = false;
        }

        //Get list of article for Tuoi Tre
        else {
            if (!doc.select("div.box-tournament.box-worldcup-2018").isEmpty()) {
                doc.select("div.box-tournament.box-worldcup-2018").remove();
            }
            if ( !doc.select("div[class*=box-block-1 w482 fr video]").isEmpty() ) {
                doc.select("div[class*=box-block-1 w482 fr video]").remove();
            }

            list = Objects.requireNonNull(doc.selectFirst("section[id*=content]")).select("a[href*=/][title~=[a-z]]");
            isTuoiTre = true;
        }

        //Loop through each article in the list
        for (int id = 0; id < list.size() - 1; id++) {
            //get one article
            Element e = list.get(id);

            //Filter for special articles
            if (!isTuoiTre) {
                //avoid the blank
                if (e.child(0).tagName().equals("ins")) {
                    continue;
                } else if (!e.select("a").isEmpty()) {
                    if (Objects.requireNonNull(e.selectFirst("a")).attr("href").equals("")) {
                        continue;
                    }
                }
                else if ( !e.select("use[xlink:href*=#Headset]").isEmpty() ) {
                    System.out.println("pop");

                    continue;
                } else if (e.child(1).tagName().equals("ins")) {
                    id++;
                    e = list.get(id);
                }
            }

            //Filter for special articles for tuoi tre
            if (e.select("img").isEmpty() && isTuoiTre) {
                continue;
            }

            String avt = "";

            //Check and get Avatar Image Link
            if (!(e.select("img").isEmpty())) {
                avt = Objects.requireNonNull(Objects.requireNonNull(e.selectFirst("img")).attr("src"));

                //Check image link
                if (!(avt.contains("https:")) && !(avt.contains(".jpeg"))) {
                    avt = Objects.requireNonNull(Objects.requireNonNull(e.selectFirst("img")).attr("data-src"));
                }
            }

            //Get Article Link
            String url = Objects.requireNonNull(e.selectFirst("a")).attr("href");

            //Check to complete url
            if (!url.contains("https://")) {
                url = new StringBuffer(hpl).deleteCharAt(hpl.length() - 1) + url;
            }

            // Don't get the special articles
            if (url.contains("https://special.nhandan.vn/") || url.contains("https://thanhnien.vn/video")
                    || url.contains("https://thanhnien.vn/the-thao/tuong-thuat")
                    || url.contains("https://thoitrangtre.thanhnien.vn/")
                    || url.contains("https://video.vnexpress.net")
                    || url.contains("https://vnexpress.net/diem-tin")
                    || url.contains("https://tuoitre.vn/video")) {
                //id--;
                continue;
            }

            String finalAvt = avt;
            String finalUrl = url;

            //Give task to executor service
            es.execute(() -> {
                try {
                    Article a = new Article(finalUrl, finalAvt);

                    //Check to remove podcast articles
                    if ( !a.getKWs().contains("podcasts") ) {
                        //Check which category it belongs to
                        check2Add(a.getKWs().toLowerCase(), a, cate);
                    }
                }
                //Handle exception
                catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Exception occurs when create and check article: " + finalUrl);
                }
            });

            //Stop when scraped enough Article or run of number of articles
            if (id == number || id == list.size() - 1) {
                es.shutdown();
                break;
            }
        }

        //Stop getting task
        es.shutdown();

        //wait until all task complete
        while (!es.isTerminated()) {
        }
    }

    //Get url of page of specified category
    private String getUrlCate(Document doc, String cate, String hpl) {
        String out = "";

        //Get category hyperlink
        switch (cate) {
            case "new":
            case "other": {
                for (Element e : doc.select("a[href*=/]")) {
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
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=chinh-tri]")).attr("href");
                } else if (!doc.select("a[href*=chinhtri]").isEmpty()) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=chinhtri]")).attr("href");
                }

                //Use "Phap Luat" category instead
                if (out.isEmpty()) {
                    if (!(doc.select("a[href*=phap-luat]").isEmpty())) {
                        out = Objects.requireNonNull(doc.selectFirst("a[href*=phap-luat]")).attr("href");
                    } else if (!doc.select("a[href*=phapluat]").isEmpty()) {
                        out = Objects.requireNonNull(doc.selectFirst("a[href*=phapluat]")).attr("href");
                    }
                }

                break;
            }

            //Category Business
            case "business": {
                if (!doc.select("a[href*=kinh-doanh]").isEmpty()) {
                    for (Element e : doc.select("a[href*=kinh-doanh]")) {
                        if (e.text().equalsIgnoreCase("kinh doanh") || e.text().equalsIgnoreCase("tài chính - kinh doanh")) {
                            out = e.attr("href");

                            if (out.contains(hpl)) {
                                break;
                            }
                        }
                    }
                } else if (!(doc.select("a[href*=kinhdoanh]").isEmpty())) {
                    for (Element e : doc.select("a[href*=kinhdoanh]")) {
                        if (e.text().equalsIgnoreCase("kinh doanh") || e.text().equalsIgnoreCase("tài chính - kinh doanh")) {
                            out = e.attr("href");

                            if (out.contains(hpl)) {
                                break;
                            }
                        }
                    }
                }

                if (out.isEmpty()) {
                    if (!(doc.select("a[href*=kinhte]").isEmpty())) {
                        out = Objects.requireNonNull(doc.selectFirst("a[href*=kinhte]")).attr("href");
                    } else if (!doc.select("a[href*=kinh-te]").isEmpty()) {
                        out = Objects.requireNonNull(doc.selectFirst("a[href*=kinh-te]")).attr("href");
                    }
                }

                break;
            }

            //Category Technology
            case "tech": {
                if (!doc.select("a[href*=congnghe]").isEmpty()) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=congnghe]")).attr("href");
                } else if (!(doc.select("a[href*=cong-nghe]").isEmpty())) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=cong-nghe]")).attr("href");
                }
                break;
            }

            //Category Health
            case "health": {
                if (!(doc.select("a[href*=suc-khoe]").isEmpty())) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=suc-khoe]")).attr("href");
                } else if (!doc.select("a[href*=suckhoe]").isEmpty()) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=suckhoe]")).attr("href");
                }

                break;
            }

            //Category Sport
            case "sport": {
                if (!(doc.select("a[href*=thethao]").isEmpty())) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=thethao]")).attr("href");
                } else if (!doc.select("a[href*=the-thao]").isEmpty()) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=the-thao]")).attr("href");
                }

                break;
            }

            //Entertainment
            case "entertain": {
                if (!doc.select("a[href*=giaitri]").isEmpty()) {
                    for (Element e : doc.select("a[href*=giaitri]")) {
                        if (e.text().equalsIgnoreCase("giải trí")) {
                            out = e.attr("href");

                            if (!out.contains("video")) {
                                break;
                            }
                        }
                    }
                } else if (!(doc.select("a[href*=giai-tri]").isEmpty())) {
                    for (Element e : doc.select("a[href*=giai-tri]")) {
                        if (e.text().equalsIgnoreCase("giải trí")) {
                            out = e.attr("href");

                            if (!out.contains("video")) {
                                break;
                            }
                        }
                    }
                }

                //Replace by Van Hoa
                if (out.isEmpty()) {
                    if (!(doc.select("a[href*=vanhoa]").isEmpty())) {
                        for (Element e : doc.select("a[href*=vanhoa]")) {
                            if (e.text().equalsIgnoreCase("văn hóa")) {
                                out = e.attr("href");

                                if (out.contains(hpl)) {
                                    break;
                                }
                            }
                        }
                    } else if (!(doc.select("a[href*=van-hoa]").isEmpty())) {
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
                if (!(doc.select("a[href*=the-gioi]").isEmpty())) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=the-gioi]")).attr("href");
                } else if (!doc.select("a[href*=thegioi]").isEmpty()) {
                    out = Objects.requireNonNull(doc.selectFirst("a[href*=thegioi]")).attr("href");
                }
                break;
            }
        }

        return out;
    }

    //Check Keywords: kw to add to List(s)
    private void check2Add(String kw, Article article, String cate) {
        //Signal be true if article belongs to any category besides new and other
        boolean sig = false;

        if (New.size() < 50) {
            New.add(article);
        }

        //Check if Covid
        if (kw.contains("covid-19") || kw.contains("chống dịch") || kw.contains("vaccine") || kw.contains("f0")
                || kw.contains("dịch bệnh") || kw.contains("ncov")) { //|| cate.equals("covid")
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

        //Check if article hasn't belonged to any Cate
        else if (!sig) {
            if (Other.size() < 50) {
                Other.add(article);
            }
        }
    }

    //Sort List of article from newest to oldest
    public void sort(String name) {
        //Count time consume
        StopWatch clock1 = new StopWatch();
        clock1.start();

        //Create a temp List to sort
        ArrayList<Article> sortList = new ArrayList<>();

        //Check to get the List and assign to temp list
        switch (name) {
            case "new":
                sortList = New;
                break;

            case "politic":
                sortList = Pol;
                break;

            case "tech":
                sortList = Tech;
                break;

            case "business":
                sortList = Busi;
                break;

            case "health":
                sortList = Health;
                break;

            case "sport":
                sortList = Sport;
                break;

            case "Entertain":
                sortList = Entertain;
                break;

            case "world":
                sortList = World;
                break;

            case "other":
                sortList = Other;
                break;

        }

        //Sorting part
        //Take 1 article from the beginning of the List and then next
        for (int id1 = 0; id1 < sortList.size() - 2; id1++) {
            //Create variable store the id of the newest article
            int newest = id1;

            //take each article which have the id next to the above to compare and find out the newest
            for (int id2 = id1 + 1; id2 < sortList.size(); id2++) {
                boolean done = false;

                //This one for fixed bugs
                //System.out.println(New.get(id1).getUrl() + " vs " +  New.get(id2).getUrl());
                //System.out.println(New.get(id1).getPubDay() + "vs" +  New.get(id2).getPubDay()  + "\n" );

                //Split to 2 strings dd/mm/yyyy and hh/mm
                String[] date1 = sortList.get(newest).getPubDay().split(" "),
                        date2 = sortList.get(id2).getPubDay().split(" ");

                //Split dd/mm/yyyy to 3 strings dd, mm and yyyy
                String[] s1 = date1[0].split("/"),
                        s2 = date2[0].split("/");

                //Take each string from yyyy to dd and compare
                for (int k = s1.length - 1; k >= 0; k--) {
                    //If year or month or day of article 1 is larger than, then article 2 become newest
                    if (Integer.parseInt(s1[k]) != Integer.parseInt(s2[k])) {
                        if (Integer.parseInt(s1[k]) < Integer.parseInt(s2[k])) {
                            newest = id2;
                        }
                        done = true;
                        break;
                    }
                }
                if (done) {
                    continue;
                }

                //If year, month and day is the same, compare hour and minute
                s1 = date1[1].split(":");
                s2 = date2[1].split(":");

                //Take hour and then minutes
                for (int k = 0; k < 2; k++) {

                    //If the hour or minute is larger than, then article 2 become the newest
                    if (Integer.parseInt(s1[k]) != Integer.parseInt(s2[k])) {
                        if (Integer.parseInt(s1[k]) < Integer.parseInt(s2[k])) {
                            newest = id2;
                        }
                        break;
                    }
                }
            }
            Article temp = sortList.get(id1);
            sortList.set(id1, sortList.get(newest));
            sortList.set(newest, temp);
        }

        //Replace by the sorted list
        switch (name) {
            case "new":
                New = sortList;
                break;

            case "politic":
                Pol = sortList;
                break;

            case "tech":
                Tech = sortList;
                break;

            case "business":
                Busi = sortList;
                break;

            case "health":
                Health = sortList;
                break;

            case "sport":
                Sport = sortList;
                break;

            case "Entertain":
                Entertain = sortList;
                break;

            case "world":
                World = sortList;
                break;

            case "other":
                Other = sortList;
                break;

        }

        System.out.print("\n" + "Time sortT() consume: " + clock1.getElapsedTime() + " ms" + "\n");
    }

}