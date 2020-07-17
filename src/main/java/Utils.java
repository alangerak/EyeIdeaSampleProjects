import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Utils {

    /**
     * Change the order of two numbers in the collection.
     *
     * @param indexA Index of the first number
     * @param indexB Index of the second number
     * @param trains Train collection to apply the reorder to
     */
    public static void reorder(int indexA, int indexB, List<Train> trains) {
        Train temp = trains.get(indexA);
        trains.set(indexA, trains.get(indexB));

        //This is the fix for task 1
        //change switch to indexB
        trains.set(indexA, temp);
    }


}
