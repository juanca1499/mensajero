package servidor.tcp;
import cliente.Cliente;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeArchivo;
import conexion.ConexionCliente;
import conexion.ConexionServidor;

import java.net.*;
// importar la libreria java.net
import java.io.*;
// importar la libreria java.io

// declararamos la clase clientetcp
public class ServidorEnviaTCP {
    // declaramos un objeto socket para realizar la comunicación
    protected Socket socket;
    protected final int PUERTO_CLIENTE;
    protected final String IP_CLIENTE;
    private ObjectOutputStream objOut;

    public ServidorEnviaTCP(ConexionCliente conexionCliente) throws Exception{
        PUERTO_CLIENTE = conexionCliente.getPuertoTCP();
        IP_CLIENTE = conexionCliente.getIp();

        // Instanciamos un socket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación.
        socket = new Socket(IP_CLIENTE,PUERTO_CLIENTE);
    }

    public void enviar(Mensaje mensaje) {
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            System.out.println("ENVIANDO MENSAJE TCP DESDE SERVIDOR A LA DIRECCION:  " + IP_CLIENTE + ":" + PUERTO_CLIENTE);
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objOut.writeObject(mensaje);
            objOut.flush();
            socket.close();
//            File archivo = mensajeArchivo.getArchivo();
//            byte[] bytes = new byte[16 * 1024];
//            InputStream in = new FileInputStream(archivo);
//            OutputStream out = socket.getOutputStream();
//            int contador;
//            while ((contador = in.read(bytes)) > 0) {
//                out.write(bytes,0,contador);
//            }
//            out.close();
//            in.close();
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
