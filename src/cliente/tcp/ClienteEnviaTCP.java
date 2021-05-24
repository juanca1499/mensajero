package cliente.tcp;
import cliente.mensajes.MensajeArchivo;
import conexion.ConexionServidor;

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
    private ObjectOutputStream objOut;
    
    public ClienteEnviaTCP(ConexionServidor conexionServidor) throws Exception{
        PUERTO_SERVER = conexionServidor.getPuertoTCP();
        IP_SERVIDOR = conexionServidor.getIp();

        // Instanciamos un socket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación.
        socket = new Socket(IP_SERVIDOR,PUERTO_SERVER);
    }
    
    public void enviar(MensajeArchivo mensajeArchivo) {

        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            System.out.println("\n\nENVIANDO MENSAJE DESDE CLIENTE " + mensajeArchivo.getOrigen() +
            " CON DESTINO AL USUARIO " + mensajeArchivo.getDestino());
            // get the output stream from the socket.
            OutputStream outputStream = socket.getOutputStream();
            // create an object output stream from the output stream so we can send an object through it
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mensajeArchivo);
            objectOutputStream.flush();
        }
        // utilizamos el catch para capturar los errores que puedan surgir
        catch (Exception e) {
            // si existen errores los mostrará en la consola y después saldrá del
            // programa
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
