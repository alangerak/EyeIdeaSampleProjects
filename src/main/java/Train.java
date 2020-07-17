public class Train {

    private int startTime;
    private int endTime;

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

    public boolean overlapWithTrain(Train other){
        return this.getStartTime() <= other.getEndTime() && other.getStartTime() <= this.getEndTime();
    }

    @Override
    public String toString() {
        return "Train{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
