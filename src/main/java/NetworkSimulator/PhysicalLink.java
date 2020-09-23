package NetworkSimulator;

/**
 * A Physical link is a representation of a connection between two different devices.
 * Through this connection, {@link DataPacket} can be send from the source to the destination.
 *
 */
public class PhysicalLink {

    private Device deviceSource;
    private Device deviceDestination;

    private int portSource;
    private int portDestination;

    /**
     * Create an empty connection
     */
    public PhysicalLink() {

    }

    /**
     * Create a connection between two devices
     * @param deviceSource
     * @param portSource
     * @param deviceDestination
     * @param portDestination
     */
    public PhysicalLink(Device deviceSource, int portSource, Device deviceDestination, int portDestination) {
        this.deviceSource = deviceSource;
        this.deviceDestination = deviceDestination;
        this.portSource = portSource;
        this.portDestination = portDestination;
    }

    public Device getDeviceSource() {
        return deviceSource;
    }

    public Device getDeviceDestination() {
        return deviceDestination;
    }

    public int getPortSource() {
        return portSource;
    }

    public int getPortDestination() {
        return portDestination;
    }

    /**
     * Send a device to the destination. If there is no destination, the packet is ignored.
     * @param packet
     */
    public void send(DataPacket packet){
        if(deviceDestination != null){
            deviceDestination.receive(packet, portDestination);
        }
    }

    @Override
    public String toString() {
        return "PhysicalLink{" +
                "deviceSource=" + deviceSource +
                ", deviceDestination=" + deviceDestination +
                ", portSource=" + portSource +
                ", portDestination=" + portDestination +
                '}';
    }
}
