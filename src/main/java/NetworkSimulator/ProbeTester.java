package NetworkSimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The probetester is a PacketListener implementation that can test if a package has been
 * along a certain flow (series of devices). A very simple setup for EndDevice would be to monitor if a package is send from
 * the starting EndDevice and received on the destination endDevice.
 */
public class ProbeTester implements PacketListener {

    private final Map<DataPacket, List<Probe>> dataPacketFlowMap;

    public ProbeTester() {
        dataPacketFlowMap = new HashMap<>();
    }

    /**
     * Add a new monitoring probe for a device. This probe will listen on the traffic of
     * this specific device for the specific packet
     *
     * @param device Device to listen to
     * @param packet Packet to monitor
     */
    public void addProbe(Device device, DataPacket packet) {
        if (!dataPacketFlowMap.containsKey(packet)) {
            dataPacketFlowMap.put(packet, new ArrayList<>());
        }

        dataPacketFlowMap.get(packet).add(new Probe(device));
    }

    /**
     * Check if all the added probes for a certain packet have received the packet
     *
     * @param dataPacket Packet to check
     * @return true if all probes have received the packet
     */
    public boolean checkPackageProbes(DataPacket dataPacket) {
        if (!dataPacketFlowMap.containsKey(dataPacket)) {
            return true;
        }

        for (Probe probe : dataPacketFlowMap.get(dataPacket)) {
            if (!probe.isReceived()) {
                return false;
            }
        }

        return true;
    }

    public List<DataPacket> getPacketsRegisteredFor(int source, int destination, int port) {
        List<DataPacket> dataPackets = new ArrayList<>();
        for (DataPacket dataPacket : dataPacketFlowMap.keySet()) {
            if (dataPacket.frame.header.getSourceAddress() == source &&
                    dataPacket.frame.header.getDestinationAddress() == destination
                    && dataPacket.header.getPort() == port) {

                dataPackets.add(dataPacket);
            }
        }
        return dataPackets;
    }


    @Override
    public void receivedPacket(Device device, DataPacket packet, int inPort) {
        if (dataPacketFlowMap.containsKey(packet)) {
            List<Probe> probes = dataPacketFlowMap.get(packet);

            for (Probe probe : probes) {
                if (probe.device.equals(device)) {
                    probe.setReceived(true);
                }
            }
        }
    }

    @Override
    public void sendPacket(Device device, DataPacket packet, int outPort) {

    }

    /**
     * A Probe holds combination if a certain device has received the "to be checked" packet
     */
    public static class Probe {

        private final Device device;
        private boolean received;

        public Probe(Device device) {
            this.device = device;
        }

        public boolean isReceived() {
            return received;
        }

        public void setReceived(boolean received) {
            this.received = received;
        }
    }


}
