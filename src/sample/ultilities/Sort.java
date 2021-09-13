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

package sample.ultilities;

import sample.model.Article;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        String result;

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

    /**
     * This method will sorts the article list in descending order.
     * Acknowledgement: The source code is inspired by Geeksforgeeks.
     *
     * @param articleList: the compared Article list in ascending order
     */
    public static void sortNewest(ArrayList<Article> articleList) {
        Collections.sort(articleList, new Comparator<Article>() {
            // Set the date format to compare
            final DateFormat datePattern = new SimpleDateFormat("dd/MM/yyyy, hh:mm");

            @Override
            // The method only works if dateTime is not null at the time of comparison
            // so it's necessary to handle null as well to avoid NullPointerException.
            public int compare(Article d1, Article d2) {
                try {
                    if (d1.getPubDay() == null || d2.getPubDay() == null)
                        return 0;
                    else
                        // Return the compared value of the Article object in ascending order.
                        return datePattern.parse(d1.getPubDay()).compareTo(datePattern.parse(d2.getPubDay()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    /**
     * This method will sorts the article list in descending order.
     * Acknowledgement: The source code is inspired by Geeksforgeeks.
     *
     * @param articleList: the compared Article list in descending order
     */
    public static void sortOldest(ArrayList<Article> articleList) {
        Collections.sort(articleList, (d1, d2) -> {
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
    }
}
