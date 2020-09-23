package TrainScheduling;

import java.util.List;

/**
 * This sorting class is responsible for sorting a list of trains based on their end times
 */
public class Sorting {

    private List<Train> trains;

    /**
     * Add the data that needs to be sorted
     *
     * @param trains Trains that needs to be sorted
     */
    public void addDataToBeSorted(List<Train> trains) {
        this.trains = trains;
    }

    /**
     * Sort the containing list of numbers from low to high
     */
    public List<Train> sort() {
        //This is the fix for task 2
        for (int i = trains.size() - 1; i > 0; i--        //Change i from 1 to 0
        ) {
            for (int j = 0; j < i; j++) {
                if (trains.get(j).getEndTime() > trains.get(j + 1).getEndTime())
                    Utils.reorder(j, j + 1, trains);
            }
        }
        return trains;
    }

}
