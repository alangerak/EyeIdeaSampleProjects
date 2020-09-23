package NetworkSimulator;

import java.util.Objects;

/**
 * A Dataframe is a container to transfer {@link Data} from a device to another device, for example an {@link EndDevice}
 */
public class DataFrame {

    Header header;
    Data data;

    public DataFrame(Header header, Data data) {
        this.header = header;
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataFrame{" +
                "header=" + header +
                ", data=" + data +
                '}';
    }

    /**
     * The Header of a DataFrame contains the source and destination address to indicate the sending and receiving device.
     */
    public static class Header{
        private final int sourceAddress;
        private final int destinationAddress;

        public Header(int sourceAddress, int destinationAddress) {
            this.sourceAddress = sourceAddress;
            this.destinationAddress = destinationAddress;
        }

        public int getSourceAddress() {
            return sourceAddress;
        }

        public int getDestinationAddress() {
            return destinationAddress;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "sourceAddress=" + sourceAddress +
                    ", destinationAddress=" + destinationAddress +
                    '}';
        }
    }

    /**
     * The Data of the dataFrame contains a piece of data to be transferred.
     */
    public static class Data{

        private final String data;

        public Data(String data){
            this.data = data;
        }

        public String getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "data='" + data + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data1 = (Data) o;
            return Objects.equals(data, data1.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

}
