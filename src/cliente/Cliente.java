package cliente;

import gui.MensajeroClienteGUI;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.ClienteEnviaUDP;
import cliente.udp.ClienteEscuchaUDP;
import conexion.ConexionCliente;
import conexion.ConexionServidor;


public class Cliente implements EnviadorMensaje, ReceptorMensaje {

    private String nombreUsuario;
    private ImpresoraChat impresora;
    private ConexionServidor servidor;
    private ConexionCliente conexion;
    private ClienteEnviaUDP clienteEnviaUDP;
    private ClienteEscuchaUDP clienteEscuchaUDP;
    private ClienteEnviaTCP clienteEnviaTCP;

    private MensajeroClienteGUI clienteGUI;

    public Cliente(ConexionServidor conServidor, String nombreUsuario) throws Exception {
        this.nombreUsuario = nombreUsuario;
        this.servidor = conServidor;
        conexion = new ConexionCliente(conServidor.getIp(),conServidor.getPuertoUDP(),
                conServidor.getPuertoTCP(),nombreUsuario);
        clienteEnviaUDP = new ClienteEnviaUDP(conexion.getSocket(),conServidor.getIp(),conServidor.getPuertoUDP());
        clienteEscuchaUDP = new ClienteEscuchaUDP(conexion.getSocket(),this);
        //clienteEnviaTCP = new ClienteEnviaTCP(servidor);
        clienteGUI = new MensajeroClienteGUI(nombreUsuario,this);
        clienteGUI.setVisible(true);
        impresora = clienteGUI;
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        mensaje.setOrigen(nombreUsuario);
        clienteEnviaUDP.enviar(mensaje);
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {
        clienteEnviaTCP.enviar(archivo);
    }

    @Override
    public void recibirMensaje(MensajeTexto mensaje) {
        impresora.imprimirMensaje(mensaje);
    }

    @Override
    public void recibirArchivo(MensajeArchivo archivo) {
        impresora.imprimirMensaje(archivo);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public ConexionCliente getConexion() {
        return conexion;
    }

    public void setServidor(ConexionServidor conexionServidor) {
        servidor = conexionServidor;
    }
}
