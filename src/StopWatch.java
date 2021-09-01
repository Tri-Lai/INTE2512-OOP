import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class StopWatch {
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
