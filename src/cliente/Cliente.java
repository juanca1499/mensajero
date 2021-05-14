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
    private ConexionServidor conexionServidor;
    private ConexionCliente conexionCliente;
    private ClienteEnviaUDP clienteEnviaUDP;
    private ClienteEscuchaUDP clienteEscuchaUDP;
    private ClienteEnviaTCP clienteEnviaTCP;
    private MensajeroClienteGUI clienteGUI;

    public Cliente(ConexionServidor conexionServidor, String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.conexionServidor = conexionServidor;
        clienteGUI = new MensajeroClienteGUI(nombreUsuario,this);
        clienteGUI.setVisible(true);
        impresora = clienteGUI;
    }

    private void inicializarServicios() {
        clienteEnviaUDP = new ClienteEnviaUDP(conexionCliente,conexionServidor);
        clienteEscuchaUDP = new ClienteEscuchaUDP(conexionCliente.getSocket(),this);
        clienteEscuchaUDP.start();
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        mensaje.setOrigen(nombreUsuario);
        if(mensaje.getOrigen().equals("Juca")) {
            mensaje.setDestino("Juanito");
        } else {
            mensaje.setDestino("Juca");
        }
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

    public ConexionCliente getConexionCliente() {
        return conexionCliente;
    }

    public void setServidor(ConexionServidor conexionServidor) throws Exception {
        this.conexionServidor = conexionServidor;
        inicializarServicios();
    }

    public void setConexionCliente(ConexionCliente conexionCliente) throws Exception {
        this.conexionCliente = conexionCliente;
        inicializarServicios();
    }
}
