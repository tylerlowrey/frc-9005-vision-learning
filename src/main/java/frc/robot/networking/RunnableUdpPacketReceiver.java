package frc.robot.networking;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RunnableUdpPacketReceiver implements Runnable {

    private int udpPort = 5808;

    public RunnableUdpPacketReceiver(int port) {
        this.udpPort = port;
    }

    @Override
    public void run() {
        byte[] udpData = new byte[2];
        DatagramPacket packet = new DatagramPacket(udpData, udpData.length);
        try (DatagramSocket socket = new DatagramSocket(udpPort)) {
            while (true) {
                socket.receive(packet);
                byte apriltagId = udpData[0];
                boolean isAprilTagDetected = udpData[1] != 0;
                Robot.aprilTagId.set(apriltagId);
                Robot.isAprilTagDetected.set(isAprilTagDetected);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
