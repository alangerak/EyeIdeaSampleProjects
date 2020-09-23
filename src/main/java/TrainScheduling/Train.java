package TrainScheduling;

/**
 * Representation of a Train with start and end times for a certain station
 */
public class Train {

    private int startTime;
    private int endTime;

    /**
     * Create a new Train
     * @param startTime starting time of a train
     * @param endTime finish time of a train
     */
    public Train(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    /**
     * Check whether a Train would be in the same station as another train
     * @param other other train
     * @return true if the trains would be in the same station
     */
    public boolean overlapWithTrain(Train other){
        return this.getStartTime() <= other.getEndTime() && other.getStartTime() <= this.getEndTime();
    }

    @Override
    public String toString() {
        return "test.Train{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
