package NetworkSimulator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NetworkSimulatorApp {


    /**
     * Use a probeTester to check if a package can be send from a device to a specific port in the receiving device
     *
     * @param fromDevice      Device to send a package from
     * @param toDevice        Device to send a package to
     * @param destinationPort Receiving port of the toDecive
     * @param probeTester     Probetester that will check the path of the package
     * @return true if package is received at the toDevice
     */
    public static boolean testPackageSend(EndDevice fromDevice, EndDevice toDevice, int destinationPort, ProbeTester probeTester) {

        DataPacket dataPacket = new DataPacket(
                destinationPort,
                new DataFrame
                        (new DataFrame.Header(fromDevice.getAddress(), toDevice.getAddress()),
                                new DataFrame.Data("TEST")));

        probeTester.addProbe(toDevice, dataPacket);
        fromDevice.send(dataPacket, 0);

        return probeTester.checkPackageProbes(dataPacket);
    }

    /**
     * Use a probeTester to check if a package can be send from a device to a specific port in the receiving device
     *
     * @param fromDevice      Device to send a package from
     * @param toDevice        Device to send a package to
     * @param destinationPort Receiving port of the toDecive
     * @param intermediateDevices Devices that lie on the path of fromDevice to toDevice
     * @param probeTester     Probetester that will check the path of the package
     * @return true if package is received at the toDevice
     */
    public static boolean testPackageSend(EndDevice fromDevice, EndDevice toDevice, int destinationPort, List<Device> intermediateDevices, ProbeTester probeTester) {

        DataPacket dataPacket = new DataPacket(
                destinationPort,
                new DataFrame
                        (new DataFrame.Header(fromDevice.getAddress(), toDevice.getAddress()),
                                new DataFrame.Data("TEST")));

        for (Device device: intermediateDevices){
            probeTester.addProbe(device, dataPacket);
        }
        probeTester.addProbe(toDevice, dataPacket);

        fromDevice.send(dataPacket, 0);

        return probeTester.checkPackageProbes(dataPacket);
    }

    public static void main(String[] args) {
        Connector connector = new Connector();
        ProbeTester probeTester = new ProbeTester();

        Firewall firewall = new Firewall();
        firewall.addPacketListener(probeTester);

        EndDevice device4 = new EndDevice(4);
        device4.addPacketListener(probeTester);
        EndDevice device5 = new EndDevice(5);
        device5.addPacketListener(probeTester);
        EndDevice device6 = new EndDevice(6);
        device6.addPacketListener(probeTester);

        Switch switch1 = new Switch(4);
        switch1.addPacketListener(probeTester);
        Switch switch2 = new Switch(10);
        switch2.addPacketListener(probeTester);

        firewall.addRule(5, 6, 0, Firewall.Action.DENY);
        firewall.addRule(5, 6, 80, Firewall.Action.ALLOW);
        firewall.addRule(5, 6, 99, Firewall.Action.ALLOW);

        firewall.addRule(4, 5, 7, Firewall.Action.DENY);
        firewall.addRule(4, 6, 99, Firewall.Action.ALLOW);

        firewall.addRule(6, 5, 1, Firewall.Action.ALLOW);

        connector.createTwoWayConnection(switch1, 1, device5, 5);
        connector.createTwoWayConnection(switch1, 3, device4, 9);
        connector.createTwoWayConnection(switch2, 2, device6, 1);

        connector.createFirewallConnection(switch1, 2, switch2, 1, firewall);


        /*
            Run Tests
         */
        if (!testPackageSend(device5, device6, 80, probeTester)) {
            System.out.println("Could not send package from device5 to device6 over port 80");
        }

        if (!testPackageSend(device4, device5, 7, probeTester)) {
            System.out.println("Could not send package from device4 to device5 over port 7");
        }

        if (!testPackageSend(device4, device5, 8, probeTester)) {
            System.out.println("Package blocked! From device4 to device5 over port 8");
        }

        if (!testPackageSend(device4, device6, 99, probeTester)) {
            System.out.println("Could not send package from device4 to device6 over port 99");
        }

        if (!testPackageSend(device6, device5, 1, probeTester)) {
            System.out.println("Could not send package from device6 to device5 over port 1");
        }

        if (!testPackageSend(device5, device6, 99, Collections.singletonList(firewall), probeTester)) {
            System.out.println("Could not send package from device5 to device6 over port 99");
        }

        if (testPackageSend(device5, device6, 0, probeTester)) {
            System.out.println("Package not Blocked!, could send package from device5 to device6 over port 0");
        }
    }

}
