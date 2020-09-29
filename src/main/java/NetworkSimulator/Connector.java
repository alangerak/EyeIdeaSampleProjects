package NetworkSimulator;

/**
 * The connector creates connections between two devices.
 */
public class Connector {

    /**
     * Create a connection that can run both ways (i.e. both devices can send/receive on the same port)
     * If there was already a connection, it will be overriden with the new one.
     *
     * @param deviceA
     * @param portA port of Device A
     * @param deviceB
     * @param portB port of Device B
     */
    public void createTwoWayConnection(Device deviceA, int portA, Device deviceB, int portB){
        createOneWayConnection(deviceA, portA, deviceB, portB);
        createOneWayConnection(deviceB, portB, deviceA, portA);
    }

    /**
     * Create a single connection that only runs from one device to another.
     * If there was already a connection, it will be overriden with the new one.
     *
     * @param from Device that can send
     * @param portFrom port of the sending device
     * @param to Device that can receive
     * @param portTo port of the receiving device
     */
    public void createOneWayConnection(Device from, int portFrom, Device to, int portTo){
        from.connect(new PhysicalLink(from, portFrom, to, portTo));
    }

    /**
     * Create a connection between 2 switches in which a firewall is placed between them.
     * The connections use a two way Connection, (i.e. switches can send/receive on the same port)
     *
     * Note: If the firewall was already connection, these connections will be overridden.
     *
     * @param switch1
     * @param port1 port of switch 1
     * @param switch2
     * @param port2 port of switch 2
     * @param firewall
     */
    public void createFirewallConnection(Switch switch1, int port1, Switch switch2, int port2, Firewall firewall){
        createOneWayConnection(switch1, port1, firewall, 0);
        createOneWayConnection(switch2, port2, firewall, 1);
    }

}
