package NetworkSimulator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A Firewall is device that can monitor incoming {@link DataPacket} and based on
 * certain rules, it can perform an {@link Action}. These rules are based on
 * device that have addresses such as {@link EndDevice}.
 * <p>
 * A Firewall has only 1 input and 1 output.
 */
public class Firewall implements Device {

    private final Map<Integer, TreeRuleNode> rulesMap;

    private final Map<Integer, PhysicalLink> portLinksTable;
    private PacketListener packetListener;


    public Firewall() {
        rulesMap = new HashMap<>();
        portLinksTable = new HashMap<>();

        portLinksTable.put(0, new PhysicalLink());
        portLinksTable.put(1, new PhysicalLink());
    }

    /**
     * Add a new rule to the firewall.
     *
     * @param sourceAddress      Address of the source device
     * @param destinationAddress Address of the destination device
     * @param destinationPort    Port of the destination device
     * @param action             The Action to perform if all conditions are fulfilled.
     */
    public void addRule(int sourceAddress, int destinationAddress, int destinationPort, Action action) {

        if (rulesMap.containsKey(destinationAddress)) {
            TreeRuleNode destNode = rulesMap.get(destinationAddress);
            TreeRuleNode portNode = destNode.addNode(destinationPort);
            TreeRuleNode sourceNode = portNode.addNode(sourceAddress);
            sourceNode.addNode(action);
        } else {
            TreeRuleNode node = new TreeRuleNode();
            node.addNode(destinationPort).addNode(sourceAddress).addNode(action);
            rulesMap.put(destinationAddress, node);
        }
    }

    /**
     * Check if there is a rule that associated with the source, destination address and destination port.
     *
     * @param sourceAddress
     * @param destinationAddress
     * @param destinationPort
     * @return If found, ,the associated Action, otherwise Action.Deny
     */
    public Action checkRule(int sourceAddress, int destinationAddress, int destinationPort) {

        if (rulesMap.containsKey(destinationAddress)) {

            TreeRuleNode destPortMapNode = rulesMap.get(destinationAddress);

            if (destPortMapNode.contains(destinationPort)) {
                TreeRuleNode sourceMapNode = destPortMapNode.get(destinationPort);

                if (sourceMapNode.contains(sourceAddress)) {
                    TreeRuleNode actionMapNode = sourceMapNode.get(sourceAddress);

                    return (Action) actionMapNode.getKeySetIterator().next();
                }
            }

        }

        return Action.DENY;
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
        int port = outPort == 0 ? 1 : 0;
        portLinksTable.get(port).send(packet);

        if (packetListener != null) {
            packetListener.sendPacket(this, packet, outPort);
        }
    }

    @Override
    public void receive(DataPacket packet, int inPort) {
        if (packetListener != null) {
            packetListener.receivedPacket(this, packet, inPort);
        }

        if (checkRule(packet.frame.header.getSourceAddress(), packet.frame.header.getDestinationAddress(), packet.header.getPort()) == Action.ALLOW) {
            send(packet, inPort);
        }
    }

    @Override
    public void addPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }


    /**
     * A TreeRuleNode is a data structure in which the data inside the node is stored as a Map structure.
     * This map stores an association between a field and another TreeRuleNode.
     * <p>
     * This allows to create a Tree in which each node has multiple associations to other nodes by using a value.
     */
    public static class TreeRuleNode {

        private final Map<Object, TreeRuleNode> treeMap;

        public TreeRuleNode() {
            treeMap = new HashMap<>();
        }

        public TreeRuleNode addNode(Object field) {
            TreeRuleNode node = new TreeRuleNode();
            treeMap.put(field, node);
            return node;
        }

        public boolean contains(Object value) {
            return treeMap.containsKey(value);
        }

        public TreeRuleNode get(Object key) {
            return treeMap.get(key);
        }

        public Iterator<Object> getKeySetIterator() {
            return treeMap.keySet().iterator();
        }

    }


    public enum Action {
        ALLOW,
        DENY
    }

}
