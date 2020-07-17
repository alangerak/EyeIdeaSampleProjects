import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This simple debug task consist out of a train interval scheduling problem.
 * The goal is to get familiar with the eye tracking tool before proceeding
 * with the second task.
 */
public class SimpleDebugTask {

    public static void main(String[] args) {
        List<Train> trains = new ArrayList<>(
                Arrays.asList(
                        new Train(15,16),
                        new Train(0, 15),
                        new Train(9, 15),
                        new Train(11, 14),
                        new Train(6, 10),
                        new Train(1, 8),
                        new Train(2, 5)
                ));

        Scheduler scheduler = new Scheduler(trains);
        System.out.println("Scheduling the following trains: \n");
        for (Train train : trains) {
            System.out.println(train);
        }

        List<Train> scheduledTrains = scheduler.maximize();

        System.out.println("\nCreated the following schedule: \n");
        for (Train train : scheduledTrains) {
            System.out.println(train);
        }

        System.out.println("\n----- CHECKING RESULT ----");
        if (scheduledTrains.size() != 4) {
            System.out.println("\n[Failed] Expected 4 trains but got " + scheduledTrains.size());
        } else {
            System.out.println("[Success]");
        }


    }

}
