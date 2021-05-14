package servidor.udp;

import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeTexto;

import java.net.*;
import java.io.*;

public class ServidorEscuchaUDP extends Thread {
    protected DatagramSocket socket;
    protected final int PUERTO_SERVER;
    protected int puertoCliente=0;
    
    protected InetAddress addressCliente;
    protected byte[] mensaje2_bytes;
    protected final int MAX_BUFFER=256;
    protected DatagramPacket paquete;
    protected byte[] mensaje_bytes;
    protected DatagramPacket envPaquete;
    private ReceptorMensaje receptorMensaje;
    
    public ServidorEscuchaUDP(int puertoS, ReceptorMensaje receptorMensaje) throws Exception{
        //Creamos el socket
        PUERTO_SERVER=puertoS;
        socket = new DatagramSocket(puertoS);
        this.receptorMensaje = receptorMensaje;
    }

    public void run() {
        try {
            String mensajeComp ="";
                       
            //Iniciamos el bucle
            do {
                // Recibimos el paquete
                mensaje_bytes=new byte[MAX_BUFFER];
                paquete = new DatagramPacket(mensaje_bytes,MAX_BUFFER);
                socket.receive(paquete);
                ByteArrayInputStream byteStream = new ByteArrayInputStream(mensaje_bytes);
                ObjectInputStream objectStream = new ObjectInputStream(
                        new BufferedInputStream(byteStream));
                Object mensajeTexto = objectStream.readObject();
                // Lo mostramos por pantalla
                receptorMensaje.recibirMensaje((MensajeTexto) mensajeTexto);
                puertoCliente = paquete.getPort();
                addressCliente = paquete.getAddress();
                System.out.println("IP A SER ENVIADO: " + addressCliente);
                System.out.println("PUERTO A SER ENVIADO: " + puertoCliente);
                enviaMensaje("Ola");
                objectStream.close();

                //Obtenemos IP Y PUERTO

//                if (mensaje.startsWith("fin")) {
//                    mensajeComp="Transmisión con el servidor finalizada...";
//                    enviaMensaje(mensajeComp);
//                }
//                else if (mensaje.startsWith("hola")) {
//                    mensajeComp="¿Cómo estas?";
//
//                    //formateamos el mensaje de salida
//                    enviaMensaje(mensajeComp);
//                }
//                else if (mensaje.startsWith("bien y tú")) {
//                    mensajeComp="También estoy bien, gracias";
//
//                    //formateamos el mensaje de salida
//                    enviaMensaje(mensajeComp);
//                }
//                else{
//                    mensajeComp="...";
//                }

            } while (true);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    private void enviaMensaje(String mensajeComp) throws Exception{
        //mensaje2_bytes = new byte[mensajeComp.length()];
        //mensaje2_bytes = mensajeComp.getBytes();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteArray);
        objectStream.writeObject(new MensajeTexto(mensajeComp));
        mensaje2_bytes = byteArray.toByteArray();
    
        //Preparamos el paquete que queremos enviar
        envPaquete = new DatagramPacket(mensaje2_bytes,mensaje2_bytes.length,addressCliente,puertoCliente);

        // realizamos el envio
        socket.send(envPaquete);
//        System.out.println("Mensaje saliente del servidor \""+
//                (new String(envPaquete.getData(),0,envPaquete.getLength()))+
//                "\" al cliente " + addressCliente + ": "+puertoCliente);
    }
}
