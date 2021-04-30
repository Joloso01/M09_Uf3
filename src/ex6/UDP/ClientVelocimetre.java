package ex6.UDP;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClientVelocimetre {
    private int portDesti;
    private int result;
    private String Nom, ipSrv;
    private int intents;
    private InetAddress adrecaDesti;
    List<Integer> velocitats = new ArrayList<>();

    private MulticastSocket multisocket;
    private InetAddress multicastIP;
    boolean continueRunning = true;

    InetSocketAddress groupMulticast;
    NetworkInterface netIf;

    public ClientVelocimetre(String ip, int port) {
        this.portDesti = port;
        result = -1;
        intents = 0;
        ipSrv = ip;
        try {
            multisocket = new MulticastSocket(5557);
            multicastIP = InetAddress.getByName("224.0.0.7");
            groupMulticast = new InetSocketAddress(multicastIP,5557);
            netIf = NetworkInterface.getByName("wlp0s20f3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            adrecaDesti = InetAddress.getByName(ipSrv);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[4];
        //Bucle
        while(true) {
            multisocket.joinGroup(groupMulticast,netIf);
            while (continueRunning) {
                DatagramPacket mpacket = new DatagramPacket(receivedData, 4);
                multisocket.receive(mpacket);
                String received = new String(mpacket.getData(), 0, mpacket.getLength());
                System.out.println(received);
//                velocitats.add(Integer.parseInt(data));
//                System.out.println("Velocitat: "+data);
//                if (velocitats.size() %5 == 0){
//                    int total = 0;
//                    for (Integer integer:velocitats){
//                        total = total+integer;
//                    }
//                    int media = total/velocitats.size();
//                    System.out.println("la mitja es: "+media);
//                }
            }
            multisocket.leaveGroup(groupMulticast,netIf);
            multisocket.close();
        }
    }

    public static void main(String[] args) {
        ClientVelocimetre cAdivina = new ClientVelocimetre("224.0.0.7", 5556);
        try {
            cAdivina.runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
