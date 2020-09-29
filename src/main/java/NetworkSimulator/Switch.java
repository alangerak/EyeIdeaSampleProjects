package NetworkSimulator;

import java.util.HashMap;
import java.util.Map;

/**
 * A Switch is a device that connects other devices together to create complex networks.
 * It has a finite amount of ports that can be used to connect to other devices.
 * <p>
 * It reads the destination of a datapacket and sends over its appropriate ports.
 */
public class Switch implements Device {

    private Map<Integer, Integer> addressPortTable;
    private Map<Integer, PhysicalLink> portLinksTable;
    private PacketListener packetListener;

    public Switch(int numOfPorts) {
        addressPortTable = new HashMap<>();
        portLinksTable = new HashMap<>();

        for (int i = 0; i < numOfPorts; i++) {
            portLinksTable.put(i, new PhysicalLink());
        }
    }

    @Override
    public void connect(PhysicalLink link) {
        if (portLinksTable.containsKey(link.getPortSource())) {
            portLinksTable.put(link.getPortSource(), link);
        } else {
            System.out.println("Port does not exist!");
        }
    }

    @Override
    public void send(DataPacket packet, int outPort) {
        portLinksTable.get(outPort).send(packet);

        if (packetListener != null) {
            packetListener.sendPacket(this, packet, outPort);
        }
    }

    @Override
    public void receive(DataPacket packet, int inPort) {

        if (packetListener != null) {
            packetListener.receivedPacket(this, packet, inPort);
        }

        int source = packet.frame.header.getSourceAddress();

        if (!addressPortTable.containsKey(source)) {
            addressPortTable.put(source, inPort);
        }

        int destination = packet.frame.header.getDestinationAddress();

        if (addressPortTable.containsKey(destination)) {
            send(packet, addressPortTable.get(destination));
        } else {
            for (Integer outPort : portLinksTable.keySet()) {
                send(packet, outPort);
            }
        }
    }

    @Override
    public void addPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }
}
