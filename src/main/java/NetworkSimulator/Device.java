package NetworkSimulator;

/**
 * A Generic representation of a Device
 */
public interface Device {

    /**
     * Add or connect a link to this Device. As a general rule, only the sending device has
     * the ownership of the PhysicalLink.
     * @param link
     */
    void connect(PhysicalLink link);

    /**
     * Send a datapacket over a specific port. If the device doesnt have ports
     * this field is ignored.
     *
     * @param packet
     * @param outPort
     */
    void send(DataPacket packet, int outPort);

    /**
     * Receive a packet from a port.
     * @param packet
     * @param inPort
     */
    void receive(DataPacket packet, int inPort);

    /**
     * Add a PacketListener that can monitor the behaviour of the device.
     * The specific behaviour that can be monitored should be at least in {@link Device#send(DataPacket, int)} and
     * {@link Device#receive(DataPacket, int)}
     *
     * @param packetListener
     */
    void addPacketListener(PacketListener packetListener);
}
