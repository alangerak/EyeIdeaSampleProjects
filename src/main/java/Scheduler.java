import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Scheduler {

    private List<Train> trains;

    public Scheduler(List<Train> trains) {
        this.trains = trains;
    }

    /**
     * Schedule the maximum amounts of trains possible.
     * @return trains that can be scheduled
     */
    public List<Train> maximize(){
        Sorting sort = new Sorting();
        sort.addDataToBeSorted(trains);

        List<Train> scheduledTrains = new ArrayList<>();
        ListIterator<Train> trainListIterator = sort.sort().listIterator();

        Train scheduledTrain = trainListIterator.next();
        scheduledTrains.add(scheduledTrain);

        for(Train train = trainListIterator.next();trainListIterator.hasNext(); train = trainListIterator.next()){
            if(!train.overlapWithTrain(scheduledTrain)){
                scheduledTrain = train;
                scheduledTrains.add(scheduledTrain);
            }
            //This is the fix for task 3
            //if(!trainListIterator.hasNext())break;
        }

        return scheduledTrains;
    }
}
