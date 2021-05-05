package ex6.TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCP {
    /* Servidor TCP que genera un número perquè ClientTcpAdivina.java jugui a encertar-lo
     * i on la comunicació dels diferents jugador passa per el Thread : ThreadServidorAdivina.java
     * */

    int port;
    Llista ns;

    public ServidorTCP(int port ) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
                ThreadServidor FilServidor = new ThreadServidor(clientSocket, ns);
                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
		/*if (args.length != 1) {
            System.err.println("Usage: java SrvTcpAdivina <port number>");
            System.exit(1);
        }*/


        //int port = Integer.parseInt(args[0]);
        ServidorTCP srv = new ServidorTCP(5558);
        srv.listen();

    }
}
