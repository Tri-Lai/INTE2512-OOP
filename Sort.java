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

package ultilities;

import java.time.ZonedDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import article.Article;

public class Sort {

    /**
     * Get the duration of the article since it was posted
     *
     * @param DateInput: the date with format "dd/MM/yyyy"
     * @param hourInput: the time with format "hh:mm"
     */
    public String getPostTime(String DateInput, String hourInput) {
        return getPostDay(DateInput, hourInput);
    }

    /**
     * Get elapsed days of the article since it was posted
     *
     * @param dayPublish:  the date with format "dd/MM/yyyy"
     * @param hourPublish: the time with format "hh:mm"
     */
    protected String getPostDay(String dayPublish, String hourPublish) {
        // Set the format for date
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String current_time = timeFormat.format(now);
        System.out.println(current_time);

        // Convert the local date to int elements
        String[] split_element = current_time.split("/");
        int[] time_element = new int[3];

        for (int i = 0; i < 3; i++)
            time_element[i] = Integer.parseInt(split_element[i]);

        // Convert the published date to int element
        String[] split_timeIn = dayPublish.split("/");
        int[] timeIn_element = new int[3];

        for (int i = 0; i < 3; i++)
            timeIn_element[i] = Integer.parseInt(split_timeIn[i]);

        // Calculate elapsed day, month or year
        int posted_time;
        String result = null;

        /*
         * If published year equals with current year then move to month check
         * and if published month equals with the current month then delve into
         * the day. If the published day is today so the elapsed hours should be considered.
         */
        if (timeIn_element[2] == time_element[2]) {
            if (timeIn_element[1] == time_element[1]) {
                if (timeIn_element[0] == time_element[0])
                    result = getPostHours(hourPublish);
                else {
                    posted_time = time_element[0] - timeIn_element[0];
                    result = "This article was posted: " + posted_time + ((posted_time > 1) ? " days ago." : " day ago.");
                }
            } else {
                posted_time = time_element[1] - timeIn_element[1];
                result = "This article was posted: " + posted_time + ((posted_time > 1) ? " months ago." : " month ago.");
            }
        } else {
            posted_time = time_element[2] - timeIn_element[2];
            result = "This article was posted: " + posted_time + ((posted_time > 1) ? " years ago." : " year ago.");
        }
        return result;
    }

    /**
     * Get elapsed hours of the article le since it was posted
     *
     * @param hourPublish: the time with format "hh:mm"
     */
    protected String getPostHours(String hourPublish) {
        // Set the format for hour
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime lTime = LocalTime.now();
        String time = timeFormat.format(lTime);

        // Extract hours format
        String[] split_element = time.split(":");
        int[] local_hour = new int[2];

        for (int i = 0; i < 2; i++)
            local_hour[i] = Integer.parseInt(split_element[i]);

        // Convert the published hour to int element
        String[] split_hourInput = hourPublish.split(":");
        int[] post_hour = new int[2];

        for (int i = 0; i < 2; i++)
            post_hour[i] = Integer.parseInt(split_hourInput[i]);

        /*
         * If the published hour equals with current hour so move to check the elapsed minutes
         */
        int posted_hours;
        String result = null;

        if (post_hour[0] == local_hour[0]) {
            if (post_hour[1] != local_hour[1]) {
                posted_hours = local_hour[1] - post_hour[1];
                result = "This article was posted: " + posted_hours + ((posted_hours > 1) ? " minutes ago." : " minute ago.");
            } else {
                result = "Now";
            }
        } else {
            posted_hours = local_hour[0] - post_hour[0];
            result = "This article was posted: " + posted_hours + ((posted_hours > 1) ? " hours ago." : " hour ago.");
        }
        return result;
    }

    public ArrayList<Article> sortNewest(ArrayList<Article> article_list) {

    }
}
