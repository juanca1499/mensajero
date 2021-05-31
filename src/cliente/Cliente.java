package cliente;

import cliente.mensajes.*;
import cliente.tcp.ClienteEscuchaTCP;
import gui.MensajeroClienteGUI;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.ClienteEnviaUDP;
import cliente.udp.ClienteEscuchaUDP;
import conexion.ConexionCliente;
import conexion.ConexionServidor;
import utilidades.Alerta;
import utilidades.Progreso;
import utilidades.Serializador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.DatagramSocket;


public class Cliente implements EnviadorMensaje, ReceptorMensaje {

    private String nombreUsuario;
    private ImpresoraChat impresora;
    private ConexionServidor conexionServidor;
    private ConexionCliente conexionCliente;
    private ClienteEnviaUDP clienteEnviaUDP;
    private ClienteEscuchaUDP clienteEscuchaUDP;
    private ClienteEnviaTCP clienteEnviaTCP;
    private ClienteEscuchaTCP clienteEscuchaTCP;
    private MensajeroClienteGUI clienteGUI;
    private DatagramSocket socketUDP;

    private Progreso progresoTransferencia;
    private boolean transferenciaEnProgreso;
    private MensajeLatencia mensajeLatencia;

    public Cliente(ConexionCliente conexionCliente) throws Exception {
        this.conexionCliente = conexionCliente;
        this.nombreUsuario = conexionCliente.getUsuario();
        this.socketUDP = new DatagramSocket(conexionCliente.getPuertoUDP());
        transferenciaEnProgreso = false;
        mensajeLatencia = null;
        clienteGUI = new MensajeroClienteGUI(nombreUsuario,this);
        clienteGUI.setVisible(true);
        impresora = clienteGUI;
    }

    private void inicializarServicios() throws Exception {
        clienteEnviaUDP = new ClienteEnviaUDP(socketUDP,conexionServidor);
        clienteEscuchaUDP = new ClienteEscuchaUDP(socketUDP,this);
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
            // Se cálcula la latencia hacia el cliente para estimar el tiempo de envío
            transferenciaEnProgreso = true;
            progresoTransferencia = new Progreso();
            calcularLatencia(archivo.getOrigen(),archivo.getDestino());
            new ContadorTiempo().start();
            FileInputStream fileInput = new FileInputStream(archivo.getArchivo());
            byte[] bytesArchivo = new byte[fileInput.available()];
            calcularLatenciaArchivo(bytesArchivo.length);
            fileInput.read(bytesArchivo);
            archivo.setBytes(bytesArchivo);
            new ClienteEnviaTCP(conexionServidor,archivo).start();
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
                fileOutput.flush();
                fileOutput.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void enviarVideo(MensajeVideo frame) {
        frame.setOrigen(nombreUsuario);
        if(frame.getOrigen().equals("Juca")) {
            frame.setDestino("Juanito");
        } else {
            frame.setDestino("Juca");
        }
        new ClienteEnviaTCP(conexionServidor,frame).start();
    }

    @Override
    public void recibirVideo(MensajeVideo frame) {
        impresora.imprimirMensaje(frame);
    }

    @Override
    public void enviarAudio(MensajeAudio sample) {
        sample.setOrigen(nombreUsuario);
        if(sample.getOrigen().equals("Juca")) {
            sample.setDestino("Juanito");
        } else {
            sample.setDestino("Juca");
        }
        clienteEnviaUDP.enviar(sample);
    }

    @Override
    public void recibirAudio(MensajeAudio sample) {
        impresora.imprimirMensaje(sample);
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

    private void calcularLatencia(String origen, String destino) {
        MensajeLatencia mensajeLatencia = new MensajeLatencia(origen,destino);
        mensajeLatencia.setTiempoInicial(System.currentTimeMillis());
        long cantBytes = Serializador.serializar(mensajeLatencia).length;
        mensajeLatencia.setCantBytes(cantBytes);
        new ClienteEnviaTCP(conexionServidor,mensajeLatencia).start();
    }

    private void calcularLatenciaArchivo(long tamanoArchivo) {
        // En base a la latencia de prueba obtenida, se calcula el tiempo estimado
        // para el archivo elegido.
        while (mensajeLatencia == null) {
            progresoTransferencia.setLatencia(0);
        }
        long bytesPorSeg = mensajeLatencia.getCantBytes() / (mensajeLatencia.getLatencia() / 1000);
        // Se pone la latencia en segundos.
        progresoTransferencia.setLatencia((tamanoArchivo / bytesPorSeg)) ;
    }

    @Override
    public void recibirLatencia(MensajeLatencia mensajeLatencia) {
        if(mensajeLatencia.isPong()) {
            // El mensaje de latencia llegó de regresó al cliente que lo solicitó.
            this.mensajeLatencia = mensajeLatencia;
            impresora.imprimirProgreso(progresoTransferencia);
        } else {
            // El mensaje de latencia llegó al detino que necesitaba alcanzar y ahora
            // tiene que regresar al que lo solicitó.
            System.out.println("Me llegó un mensaje para solicitar la latencia!!! :)");
            mensajeLatencia.setTiempoFinal(System.currentTimeMillis());
            mensajeLatencia.setPong(true);
            String nuevoDestino = mensajeLatencia.getOrigen();
            String nuevoOrigen = mensajeLatencia.getDestino();
            mensajeLatencia.setOrigen(nuevoOrigen);
            mensajeLatencia.setDestino(nuevoDestino);
            new ClienteEnviaTCP(conexionServidor,mensajeLatencia).start();
        }
    }

    private class ContadorTiempo extends Thread {
        private long tiempoInicio;
        private long tiempoActual;

        public ContadorTiempo() {
            tiempoInicio = System.currentTimeMillis();
        }

        @Override
        public void run() {
            while (transferenciaEnProgreso) {
                try {
                    tiempoActual = System.currentTimeMillis();
                    tiempoActual = (tiempoActual - tiempoInicio) / 1000;
                    progresoTransferencia.setTiempoTranscurrido(tiempoActual);
                    impresora.imprimirProgreso(progresoTransferencia);
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            this.interrupt();
        }
    }
}
