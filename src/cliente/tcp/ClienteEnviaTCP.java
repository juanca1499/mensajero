package cliente.tcp;
import cliente.mensajes.MensajeArchivo;
import conexion.ConexionServidor;

import java.net.*;
// importar la libreria java.net
import java.io.*;
// importar la libreria java.io
 
// declararamos la clase clientetcp
public class ClienteEnviaTCP {
    protected BufferedReader in;
    // declaramos un objeto socket para realizar la comunicación
    protected Socket socket;
    protected final int PUERTO_SERVER;
    protected final String SERVER;
    protected DataOutputStream out;
    
    public ClienteEnviaTCP(ConexionServidor conexionServidor)throws Exception{
        PUERTO_SERVER=conexionServidor.getPuertoTCP();
        SERVER=conexionServidor.getIp();

        // Instanciamos un socket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación
        socket = new Socket(SERVER,PUERTO_SERVER);
        
        // Declaramos e instanciamos el objeto DataOutputStream
        // que nos valdrá para enviar datos al servidor destino
        out = new DataOutputStream(socket.getOutputStream());
    }
    
    public void enviar (MensajeArchivo mensajeArchivo) {

        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            // Creamos un bucle do while en el que enviamos al servidor el mensaje
            // los datos que hemos obtenido despues de ejecutar la función
            // "readLine" en la instancia "in"
            File archivo = mensajeArchivo.getArchivo();
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(archivo);
            OutputStream out = socket.getOutputStream();
            int contador;
            while ((contador = in.read(bytes)) > 0) {
                out.write(bytes,0,contador);
            }
            out.close();
            in.close();
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
