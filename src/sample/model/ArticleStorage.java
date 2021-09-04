/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2021B
  Assessment: Final Project
  Created  date: 12/07/2021

  Author: Lai Nghiep Tri - s3799602
          Thieu Tran Tri Thuc - s3870730
          Nguyen Hoang Long - S3878451
          Trinh Pham Hoang Long - s3879366

  Last modified date: dd/mm/yyyy
  Acknowledgement: Canvas lecture slides, W3schools, mkyong
*/

package sample.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sample.ultilities.StopWatch;
import sample.ultilities.*;
import sample.model.Article;


import java.io.IOException;
import java.util.ArrayList;

public class ArticleStorage {
    private static ArrayList<ArrayList<Article>> categories;

    public static ArrayList<ArrayList<Article>> addToCategory () throws IOException {
        StopWatch clock = new StopWatch();
        ArrayList<ArrayList<Article>> categories = new ArrayList<>();

        clock.start();

        // VNExpress
        String[] links = {"https://vnexpress.net/rummenigge-cau-thu-nhu-haaland-chi-xuat-hien-sau-30-40-nam-4342712.html",
                "https://vnexpress.net/bau-duc-khong-tiec-chuc-vo-dich-v-league-4344455.html",
                "https://vnexpress.net/nhung-khan-dai-binh-thuong-tro-lai-o-ngoai-hang-anh-4342499.html",
                "https://vnexpress.net/pique-gay-sot-voi-video-xin-chu-ky-koeman-4341512.html",
                "https://vnexpress.net/hlv-tottenham-son-heung-min-la-sat-thu-4341138.html",
                "https://vnexpress.net/cuoc-dua-kho-luong-o-serie-a-4344055.html",
                "https://vnexpress.net/quang-liem-lam-hlv-giup-toi-nang-cao-trinh-do-4344481.html",
                "https://vnexpress.net/ian-wright-man-utd-that-su-dang-so-voi-varane-4344375.html",
                "https://vnexpress.net/xuan-truong-dong-vien-minh-vuong-4344536.html",
                "https://vnexpress.net/drogba-chelsea-lai-thang-arsenal-theo-kich-ban-cu-4344976.html"};

        // Category
        ArrayList<Article> news = new ArrayList<Article>();
        ArrayList<Article> covid = new ArrayList<Article>();
        ArrayList<Article> politic = new ArrayList<Article>();
        ArrayList<Article> business = new ArrayList<Article>();
        ArrayList<Article> technology = new ArrayList<Article>();
        ArrayList<Article> health = new ArrayList<Article>();
        ArrayList<Article> sports = new ArrayList<Article>();
        ArrayList<Article> entertainment = new ArrayList<Article>();
        ArrayList<Article> world = new ArrayList<Article>();
        ArrayList<Article> others = new ArrayList<Article>();

        categories.add(news);
        categories.add(covid);
        categories.add(politic);
        categories.add(business);
        categories.add(technology);
        categories.add(health);
        categories.add(sports);
        categories.add(entertainment);
        categories.add(world);
        categories.add(others);



//        for (Article a: sports) {
//            a.getAvt();
//        }

        clock.stop();

        try {
            Sort.sortNewest(sports);
            for (Article article: sports) {
                System.out.println("---------------");
                System.out.println(article.getTitle());
                System.out.println(article.getAuthor());
                System.out.println(article.getPubDay());
            }

        } catch (NullPointerException except) {
            System.out.println(except.getMessage());
        } finally {
            System.out.println("Scrape time: " + clock.getElapsedTime());
        }

        return categories;
    }

    public static ArrayList<ArrayList<Article>> getCategoriesList() {
        return categories;
    }
}
