package cliente.tcp;

import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeArchivo;
import conexion.ConexionCliente;

import java.net.*;

import java.io.*;

public class ClienteEscuchaTCP extends Thread {
    // declaramos un objeto ServerSocket para realizar la comunicación
    protected ServerSocket socket;
    protected Socket socketServidor;
    private ObjectInputStream objIn;
    protected final int PUERTO_CLIENTE;
    private ReceptorMensaje receptorMensaje;

    public ClienteEscuchaTCP(ConexionCliente conexionCliente, ReceptorMensaje receptorMensaje) throws Exception {
        PUERTO_CLIENTE = conexionCliente.getPuertoTCP();
        this.receptorMensaje = receptorMensaje;
        // Instanciamos un ServerSocket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación
        socket = new ServerSocket(PUERTO_CLIENTE);
        System.out.println("CLIENTE ESCUCHANDO EN EL PUERTO (TCP): " + socket.getLocalPort());
    }
    // método principal main de la clase
    @Override
    public void run() {
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            while (true) {
                // Creamos un socket_cli al que le pasamos el contenido del objeto socket después
                // de ejecutar la función accept que nos permitirá aceptar conexiones de clientes
                socketServidor = socket.accept();

                // Declaramos e instanciamos el objeto DataInputStream
                // que nos valdrá para recibir datos del cliente
                objIn = new ObjectInputStream(socketServidor.getInputStream());
                MensajeArchivo mensajeArchivo = (MensajeArchivo) objIn.readObject();
                receptorMensaje.recibirArchivo(mensajeArchivo);
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
}