package cliente;

import cliente.gui.MensajeroClienteGUI;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.ClienteEnviaUDP;
import cliente.udp.ClienteEscuchaUDP;
import servidor.ConexionServidor;

import java.net.DatagramSocket;

public class Cliente implements EnviadorMensaje, ReceptorMensaje {

    private String nombreUsuario;
    private ImpresoraChat impresora;
    private ConexionServidor servidor;
    private ConexionCliente conexion;
    private ClienteEnviaUDP clienteEnviaUDP;
    private ClienteEscuchaUDP clienteEscuchaUDP;
    private ClienteEnviaTCP clienteEnviaTCP;
    private MensajeroClienteGUI mensajeroClienteGUI;

    public Cliente(ConexionServidor conServidor, String nombreUsuario, ImpresoraChat impresora) throws Exception {
        this.nombreUsuario = nombreUsuario;
        this.impresora = impresora;
        this.servidor = conServidor;
        conexion = new ConexionCliente(conServidor.getIp(),conServidor.getPuertoUDP(),
                conServidor.getPuertoTCP());
        clienteEnviaUDP = new ClienteEnviaUDP(conexion.getSocket(),conServidor.getIp(),conServidor.getPuertoUDP());
        clienteEscuchaUDP = new ClienteEscuchaUDP(conexion.getSocket(),this);
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        clienteEnviaUDP.enviar(mensaje.toString());
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {

    }

    @Override
    public void recibirMensaje(String mensaje) {
        MensajeTexto mensajeTexto = new MensajeTexto("Cliente","Servidor",mensaje);
        impresora.imprimirMensaje(mensajeTexto);
    }

    @Override
    public void recibirArchivo(byte[] archivo) {

    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public ConexionCliente getConexion() {
        return conexion;
    }
}
