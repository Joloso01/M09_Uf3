package ex6.TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadServidor implements Runnable {
    /* Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient ClientTcpAdivina.java */

    Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Llista llistaEntrant, llistaSortint;
    boolean acabat;
    Llista llista;

    public ThreadServidor(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
//        llista = ns;
        acabat = false;
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());

    }

    @Override
    public void run() {
        try {
            while(!acabat) {
                llistaEntrant = (Llista) ois.readObject();
                System.out.println(llistaEntrant.getNom());
                for(Integer i:llistaEntrant.getNumberList()){
                    System.out.println(i);
                }
                llistaEntrant = generaResposta(llistaEntrant);
                oos.writeObject(llistaEntrant);
                oos.flush();


            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getLocalizedMessage());
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Llista generaResposta(Llista llista) {
        if (llista != null){
            List<Integer> numerosDesordanados = llista.getNumberList();
            List<Integer> numerosOrdenados = numerosDesordanados.stream().sorted().distinct().collect(Collectors.toList());
            llista.setNumberList(numerosOrdenados);
            return llista;
        }else return null;

    }

}