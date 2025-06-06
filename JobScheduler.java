import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class JobScheduler {
    private final List<Job> jobList = new ArrayList<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public void addJob(Job job) {
        jobList.add(job);
    }

    public void start() {
        executor.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
            for (Job job : jobList) {
                if (job.shouldRun(now)) {
                    job.run();
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void stop() {
        executor.shutdown();
    }
}
