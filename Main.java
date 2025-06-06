import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JobScheduler scheduler = new JobScheduler();
        Scanner sc = new Scanner(System.in);

        System.out.print("How many jobs do you want to schedule? ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Job " + (i + 1) + " ---");

            System.out.print("Enter job name: ");
            String name = sc.nextLine();

            System.out.print("Enter job type (HOURLY / DAILY / WEEKLY): ");
            String typeStr = sc.nextLine().toUpperCase();

            Job.Type type = Job.Type.valueOf(typeStr);
            int minute = -1;
            LocalTime time = null;
            DayOfWeek day = null;

            if (type == Job.Type.HOURLY) {
                System.out.print("Enter minute of the hour to run the job (0-59): ");
                minute = sc.nextInt();
                sc.nextLine(); // consume newline
            } else {
                System.out.print("Enter time to run the job (HH:mm): ");
                String timeStr = sc.nextLine();
                time = LocalTime.parse(timeStr);

                if (type == Job.Type.WEEKLY) {
                    System.out.print("Enter day of week (e.g., MONDAY): ");
                    String dayStr = sc.nextLine().toUpperCase();
                    day = DayOfWeek.valueOf(dayStr);
                }
            }

            Job job = new Job(name, type, minute, time, day);
            scheduler.addJob(job);
        }

        scheduler.start();
        sc.close();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down scheduler...");
            scheduler.stop();
        }));
    }
}
