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
  Acknowledgement: Canvas lecture slides, W3schools,
*/

package sample.ultilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StopWatch {
    private DateTimeFormatter out = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    LocalDateTime now = LocalDateTime.now();


    private String startTime, endTime;
    private long s, e;

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public StopWatch() {
        startTime = out.format(now);
    }

    public void start() {
        startTime = out.format(now);
        s = System.currentTimeMillis();
    }

    public void stop() {
        endTime = out.format(now);
        e = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return  System.currentTimeMillis() - s;
    }
}