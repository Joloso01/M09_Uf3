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
//    Llista llista;
//    List<Integer> numeros = new ArrayList<>();

    public ServidorTCP(int port ) {
        this.port = port;
//        for (int i = 0; i < 10; i++) {
//            numeros.add((int) (Math.random()*100));
//        }
//        llista = new Llista("llista: 0",numeros);
    }

    public void listen() {
        ServerSocket serverSocket;
        Socket clientSocket;

        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
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
