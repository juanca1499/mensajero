package cliente.udp;

import javax.swing.*;
import java.net.*;
import java.io.*;
 
//declaramos la clase udp
public class ClienteUDP{
    protected final int PUERTO_SERVER;
    protected final String SERVER;

    public ClienteUDP(String servidor, int puertoS){
        PUERTO_SERVER=puertoS;
        SERVER=servidor;
    }
    
    public void inicia()throws Exception{
        //DatagramSocket socket=new DatagramSocket();
        //ClienteEscuchaUDP clienteEscuchaUDP=new ClienteEscuchaUDP(socket);
        //ClienteEnviaUDP clienteEnviaUDP=new ClienteEnviaUDP(socket, SERVER, PUERTO_SERVER);
        //clienteEscuchaUDP.start();
        //clienteEnviaUDP.start();
    }
}
