package NetworkSimulator;

/**
 * An EndDevice is a representation of a device that can create a new {@link DataPacket} and send it to
 * another EndDevice.
 *
 * These can be connected directly with each other or by utilizing various routing devices to create complex networks.
 *
 */
public class EndDevice implements Device{

    private final int address;
    private PhysicalLink link;
    private PacketListener packetListener;

    public EndDevice(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public void connect(PhysicalLink link) {
        this.link = link;
    }

    @Override
    public void send(DataPacket packet, int port) {
        if(link != null){
            link.send(packet);
        }

        if(packetListener != null){
            packetListener.sendPacket(this, packet, port);
        }
    }

    @Override
    public void receive(DataPacket packet, int port) {
        if(packetListener != null){
            packetListener.receivedPacket(this, packet, port);
        }
    }

    @Override
    public void addPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

}
