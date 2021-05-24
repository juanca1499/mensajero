package programas;

import conexion.ConexionCliente;
import servidor.Servidor;

public class MensajeroServidorProgram {
    public static void main(String[] args) throws Exception {
        // Se crea el servidor intermediario para redireccionar los mensajes a su destino y
        // proveer futura escalabilidad
        Servidor servidor = new Servidor();
        // Cliente Juca
        servidor.agregarCliente(new ConexionCliente("192.168.0.15","Juca", 40002, 50002));
        // Cliente Juanito
        servidor.agregarCliente(new ConexionCliente("192.168.0.34","Juanito", 40002, 50002));
        // TODO Check why when you first send a message from the server the program fails.
        // TODO Check why I specified two times the username.
    }
}
