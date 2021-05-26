package cliente;

import cliente.tcp.ClienteEscuchaTCP;
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
import utilidades.Alerta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.DatagramSocket;


public class Cliente implements EnviadorMensaje, ReceptorMensaje {

    private String nombreUsuario;
    public final String ubicacionDescargas = System.getProperty("user.home") + "\\DescargasMSJFenix" + "\\";
    private ImpresoraChat impresora;
    private ConexionServidor conexionServidor;
    private ConexionCliente conexionCliente;
    private ClienteEnviaUDP clienteEnviaUDP;
    private ClienteEscuchaUDP clienteEscuchaUDP;
    private ClienteEnviaTCP clienteEnviaTCP;
    private ClienteEscuchaTCP clienteEscuchaTCP;
    private MensajeroClienteGUI clienteGUI;
    private DatagramSocket socketUDP;

    public Cliente(ConexionCliente conexionCliente) throws Exception {
        this.conexionCliente = conexionCliente;
        this.nombreUsuario = conexionCliente.getUsuario();
        this.socketUDP = new DatagramSocket(conexionCliente.getPuertoUDP());
        clienteGUI = new MensajeroClienteGUI(nombreUsuario,this);
        clienteGUI.setVisible(true);
        impresora = clienteGUI;
    }

    private void inicializarServicios() throws Exception {
        clienteEnviaUDP = new ClienteEnviaUDP(socketUDP,conexionServidor);
        clienteEscuchaUDP = new ClienteEscuchaUDP(socketUDP,this);
        clienteEnviaTCP = new ClienteEnviaTCP(conexionServidor);
        clienteEscuchaTCP = new ClienteEscuchaTCP(conexionCliente,this);
        clienteEscuchaUDP.start();
        clienteEscuchaTCP.start();
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
        archivo.setOrigen(nombreUsuario);
        // El siguiente código será removido en caso de alcanzar a implementar la funcionalidad
        // de escoger el cliente a enviar desde la GUI.
        if(archivo.getOrigen().equals("Juca")) {
            archivo.setDestino("Juanito");
        } else {
            archivo.setDestino("Juca");
        }
        try {
            FileInputStream fileInput = new FileInputStream(archivo.getArchivo());
            byte[] bytesArchivo = new byte[fileInput.available()];
            fileInput.read(bytesArchivo);
            archivo.setBytes(bytesArchivo);
            clienteEnviaTCP.enviar(archivo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void recibirMensaje(MensajeTexto mensaje) {
        impresora.imprimirMensaje(mensaje);
    }

    @Override
    public void recibirArchivo(MensajeArchivo archivo) {
        File archivoDestino = Alerta.pedirUbicacion(clienteGUI,
        archivo.getArchivo().getName());
        if(archivoDestino != null) {
            impresora.imprimirMensaje(archivo);
            try {
                FileOutputStream fileOutput = new FileOutputStream(archivoDestino);
                fileOutput.write(archivo.getBytes());
                fileOutput.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
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
