package NetworkSimulator;

/**
 * A Datapacket is a container for a {@link DataFrame} to include a port field.
 */
public class DataPacket {

    DataFrame frame;
    Header header;

    public DataPacket(int port, DataFrame frame) {
        header = new Header(port);
        this.frame = frame;
    }

    /**
     * The Header of the DataPacket includes only the port field as additional field
     */
    public static class Header {

        private final int port;

        public Header(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "port=" + port +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DataPacket{" +
                "frame=" + frame +
                ", header=" + header +
                '}';
    }
}
