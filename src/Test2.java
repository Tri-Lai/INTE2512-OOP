import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;

public class Test2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        StopWatch clock1 = new StopWatch();
        clock1.start();
        //ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        String zing = "https://zingnews.vn/",
                tn = "https://thanhnien.vn/",
                tt = "https://tuoitre.vn/",
                nd = "https://nhandan.vn/",
                vne = "https://vnexpress.net/";

        Category c1 = new Category();

        //Thread t1 = new Thread( () -> {
            try {
                //c1.setOther();
                c1.setCate("politic");
            } catch (IOException e) {}

        //});
        //t1.start();

        //t1.join();

        //Thread t2 = new Thread( () -> {

            c1.sort("politic");
            int count = 1;

            for ( Article a : c1.getList("politic") ) {

                //System.out.println(a.getUrl()  +  "\n" + a.getAvt());

                System.out.println(count + ": " + a.getTitle() );

                System.out.println(a.getPubDay() + "\n");

                //a.getSum();

                //a.getBody();

                if (count == 50) {
                    break;
                }
                count++;
            }
        c1.getNum();
            System.out.print("\n" + "Time consume: " + clock1.getElapsedTime() + " ms" + "\n");
        //});
       //t2.start();

        //c1.getPol();
        //System.out.println(doc);
        //Document doc = Jsoup.connect("https://tuoitre.vn/phap-luat.htm").get();
        //int count = 1;




        //Document doc = Jsoup.connect(src).get();

    }
}
