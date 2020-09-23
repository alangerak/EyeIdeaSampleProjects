package NetworkSimulator;

/**
 * A PacketListener monitors the network where a datapacket is currently located in the network.
 */
public interface PacketListener {

    /**
     * A Packet is received on a device
     * @param device Device that received the packet
     * @param packet The Packet that is received
     * @param inPort The port of the device that received the packet
     */
    void receivedPacket(Device device, DataPacket packet, int inPort);

    /**
     * A Packet is send from a device
     * @param device Device that send the packet
     * @param packet The Packet that is send
     * @param outPort The port of the device that send the packet
     */
    void sendPacket(Device device, DataPacket packet, int outPort);
}
