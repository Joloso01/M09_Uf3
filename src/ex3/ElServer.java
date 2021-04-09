package ex3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ElServer {
    DatagramSocket socket;
    private int elNumero = (int) (Math.random()*100);
    private int conexionNumero = 0;
    private String nombre = "usuario";

    public void init(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("Iniciando servidor...");
    }

    public void runServer() throws IOException {
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

//el servidor atén el port indefinidament
        while(true){

//creació del paquet per rebre les dades
            DatagramPacket packet = new DatagramPacket(receivingData, 1024);
//espera de les dades
            socket.receive(packet);
//processament de les dades rebudes i obtenció de la resposta
            sendingData = processData(packet.getData(), packet.getLength());
//obtenció de l'adreça del client
            clientIP = packet.getAddress();
//obtenció del port del client
            clientPort = packet.getPort();
//creació del paquet per enviar la resposta
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort);
//enviament de la resposta
            socket.send(packet);

        }
    }

    private byte[] processData(byte[] data, int length) {
        String mensaje;
        String resp = null;
        mensaje = new String(data, 0,length);

        if (conexionNumero == 0){
            nombre = mensaje;
            conexionNumero++;
        }

        resp = "<"+nombre+"<"+mensaje+">"+">";

        System.out.println(mensaje);

        byte[] respuesta = resp.getBytes(StandardCharsets.UTF_8);

    return respuesta;
    }

}
