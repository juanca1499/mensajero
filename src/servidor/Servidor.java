package servidor;

import conexion.ConexionCliente;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import conexion.ConexionServidor;
import gui.MensajeroServidorGUI;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEnviaUDP;
import servidor.udp.ServidorEscuchaUDP;

import java.util.ArrayList;
import java.util.List;

public class Servidor implements EnviadorMensaje, ReceptorMensaje {

    private ImpresoraChat impresora;
    private List<ConexionCliente> listaClientes;
    private ConexionServidor conexion;
    private ServidorEscuchaUDP servidorEscuchaUDP;
    private ServidorEscuchaTCP servidorEscuchaTCP;
    private ServidorEnviaUDP servidorEnviaUDP;


    private MensajeroServidorGUI servidorGUI;

    public Servidor() throws Exception {
        this.listaClientes = new ArrayList<>();
        conexion = new ConexionServidor("192.168.0.15",50000,60000);
        servidorEscuchaUDP = new ServidorEscuchaUDP(conexion.getPuertoUDP(),this);
        servidorEscuchaTCP = new ServidorEscuchaTCP(conexion.getPuertoTCP(),this);
        inicializarEscuchadores();
        servidorGUI = new MensajeroServidorGUI(this);
        servidorGUI.setVisible(true);
        impresora = servidorGUI;
    }

    private synchronized void inicializarEscuchadores() {
        servidorEscuchaUDP.start();
        //servidorEscuchaTCP.start();
    }
    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        try {
            ConexionCliente cliente = buscarCliente(mensaje.getDestino());
            //servidorEnviaUDP = new ServidorEnviaUDP(cliente);
            //servidorEnviaUDP.enviar(mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {

    }

    @Override
    public void recibirMensaje(MensajeTexto mensaje) {
        impresora.imprimirMensaje(mensaje);
        enviarMensaje(mensaje);
    }

    @Override
    public void recibirArchivo(MensajeArchivo archivo) {
        impresora.imprimirMensaje(archivo);
        enviarArchivo(archivo);
    }

    public ConexionServidor agregarCliente(ConexionCliente cliente) {
        ConexionServidor conexionServidor = new ConexionServidor(conexion.getIp());
        int[] puertos = getPuertosDisponibles();
        conexionServidor.setPuertoUDP(puertos[0]);
        conexionServidor.setPuertoTCP(puertos[1]);
        listaClientes.add(cliente);
        return conexionServidor;
    }

    public ConexionCliente buscarCliente(String usuario) {
        for(ConexionCliente cliente : listaClientes) {
            if(cliente.getUsuario().equals(usuario)) {
                return cliente;
            }
        }
        return null;
    }

    private int[] getPuertosDisponibles() {
        int[] puertos = new int[2];
        if(!listaClientes.isEmpty()) {
            ConexionCliente ultimaConexion = listaClientes.get(listaClientes.size() - 1);
            int ultimoPuertoUDP = ultimaConexion.getPuertoUDP();
            int ultimoPuertoTCP = ultimaConexion.getPuertoTCP();
            puertos[0] = ultimoPuertoUDP + 1;
            puertos[1] = ultimoPuertoTCP + 1;
        } else {
            puertos[0] = conexion.getPuertoUDP();
            puertos[1] = conexion.getPuertoTCP();
        }
        return puertos;
    }
}