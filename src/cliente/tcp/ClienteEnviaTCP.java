package cliente.tcp;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeVideo;
import conexion.ConexionServidor;
import utilidades.Convertidor;

import java.net.*;
// importar la libreria java.net
import java.io.*;
// importar la libreria java.io
 
// declararamos la clase clientetcp
public class ClienteEnviaTCP {
    // declaramos un objeto socket para realizar la comunicación
    protected Socket socket;
    protected final int PUERTO_SERVER;
    protected final String IP_SERVIDOR;
    
    public ClienteEnviaTCP(ConexionServidor conexionServidor) {
        PUERTO_SERVER = conexionServidor.getPuertoTCP();
        IP_SERVIDOR = conexionServidor.getIp();
    }
    
    public void enviar(Mensaje mensaje) {
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            socket = new Socket(IP_SERVIDOR,PUERTO_SERVER);
            System.out.println("\n\nENVIANDO MENSAJE DESDE CLIENTE " + mensaje.getOrigen() +
                    " CON DESTINO AL USUARIO " + mensaje.getDestino());
            System.out.println("\n\nENVIANDO MENSAJE DESDE CLIENTE A LA DIRECCION " + IP_SERVIDOR + ":" + PUERTO_SERVER);
            // get the output stream from the socket.
            OutputStream outputStream = socket.getOutputStream();
            if(!(mensaje instanceof MensajeArchivo)) {
                // create an object output stream from the output stream so we can send an object through it
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(mensaje);
                objectOutputStream.flush();
                outputStream.flush();
                socket.close();
            } else {
                long tiempoInicio = System.currentTimeMillis();
                long tiempoActual;
                System.out.println("Enviando archivo...");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(mensaje);
                tiempoActual = System.currentTimeMillis();
                System.out.println("Tiempo transcurrido (segs): " + (tiempoActual - tiempoInicio) / 1000);
                objectOutputStream.flush();
                outputStream.flush();
                socket.close();
            }
        }
        // utilizamos el catch para capturar los errores que puedan surgir
        catch (Exception e) {
            // si existen errores los mostrará en la consola y después saldrá del
            // programa
            e.printStackTrace();
            System.exit(1);
        }
    }
}
