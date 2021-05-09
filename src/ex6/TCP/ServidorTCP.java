package ex6.TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCP {

    int port;

    public ServidorTCP(int port ) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket;
        Socket clientSocket;

        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                clientSocket = serverSocket.accept();
                ThreadServidor FilServidor = new ThreadServidor(clientSocket);
                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ServidorTCP srv = new ServidorTCP(5558);
        srv.listen();

    }
}