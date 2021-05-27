package servidor.tcp;

import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeVideo;
import conexion.ConexionServidor;

import java.net.*;

import java.io.*;

public class ServidorEscuchaTCP extends Thread {
    protected ServerSocket socket;
    protected Socket socketCliente;
    private ObjectInputStream objIn;
    protected final int PUERTO_SERVER;
    private ReceptorMensaje receptorMensaje;
    
    public ServidorEscuchaTCP(ConexionServidor conexionServidor, ReceptorMensaje receptorMensaje) throws Exception {
        PUERTO_SERVER = conexionServidor.getPuertoTCP();
        this.receptorMensaje = receptorMensaje;
        // Instanciamos un ServerSocket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación
        socket = new ServerSocket(PUERTO_SERVER);
        System.out.println("SERVIDOR ESCUCHANDO EN EL PUERTO (TCP): " + socket.getLocalPort());
    }
    // método principal main de la clase
    @Override
    public void run() {
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            while (true) {
                // Creamos un socket_cli al que le pasamos el contenido del objeto socket después
                // de ejecutar la función accept que nos permitirá aceptar conexiones de clientes
                socketCliente = socket.accept();
                // Declaramos e instanciamos el objeto DataInputStream
                // que nos valdrá para recibir datos del cliente
                objIn = new ObjectInputStream(socketCliente.getInputStream());
                Mensaje mensaje = (Mensaje) objIn.readObject();
                System.out.println("\n\nArchivo recibido de la direccion " + socketCliente.getInetAddress() + ":" + socketCliente.getPort());
                reedireccionarMensaje(mensaje);
                objIn.close();
            }
        }
        // utilizamos el catch para capturar los errores que puedan surgir
        catch (Exception e) {

            // si existen errores los mostrará en la consola y después saldrá del
            // programa
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void reedireccionarMensaje(Mensaje mensaje) {
        if(mensaje instanceof MensajeArchivo) {
            receptorMensaje.recibirArchivo((MensajeArchivo) mensaje);
        } else if(mensaje instanceof MensajeVideo) {
            receptorMensaje.recibirVideo((MensajeVideo) mensaje);
        }
    }
}
