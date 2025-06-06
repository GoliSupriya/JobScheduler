import java.time.DayOfWeek;
import java.time.LocalTime;

public class Job {
    public enum Type {
        HOURLY, DAILY, WEEKLY
    }

    private String name;
    private Type type;
    private int minuteOfHour; // for HOURLY
    private LocalTime timeOfDay; // for DAILY and WEEKLY
    private DayOfWeek dayOfWeek; // for WEEKLY

    public Job(String name, Type type, int minuteOfHour, LocalTime timeOfDay, DayOfWeek dayOfWeek) {
        this.name = name;
        this.type = type;
        this.minuteOfHour = minuteOfHour;
        this.timeOfDay = timeOfDay;
        this.dayOfWeek = dayOfWeek;
    }

    public boolean shouldRun(java.time.LocalDateTime now) {
        switch (type) {
            case HOURLY:
                return now.getMinute() == minuteOfHour;
            case DAILY:
                return now.toLocalTime().withSecond(0).withNano(0).equals(timeOfDay);
            case WEEKLY:
                return now.getDayOfWeek() == dayOfWeek &&
                        now.toLocalTime().withSecond(0).withNano(0).equals(timeOfDay);
            default:
                return false;
        }
    }

    public void run() {
        System.out.println("[" + java.time.LocalDateTime.now() + "] " + name + ": Hello World");
    }

    public String getName() {
        return name;
    }
}
