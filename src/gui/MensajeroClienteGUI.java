package gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.mensajes.*;
import utilidades.RutaUtilidades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MensajeroClienteGUI extends MensajeroGUI {

    private VideollamadaGUI videollamadaGUI;
    private JButton btnVideollamada;
    private JTextArea txtAreaMensajesPropios;
    private JTextArea txtAreaMensajesExternos;

    private String usuario;

    private EnviadorMensaje enviador;

    public MensajeroClienteGUI(String usuario, EnviadorMensaje enviador)  {
        this.usuario = usuario;
        this.enviador = enviador;
        this.cargarComponentes();
        this.agregarEventos();
    }

    protected void cargarComponentes() {
        super.cargarComponentes();
        panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(bordePanel);
        lblUsuario = new JLabel("Cliente:" + usuario);
        lblUsuario.setFont(fuente);
        panelSuperior.add(lblUsuario,BorderLayout.LINE_START);
        btnVideollamada = new JButton(iconVideollamada);
        btnVideollamada.setFont(fuente);
        panelSuperior.add(btnVideollamada,BorderLayout.LINE_END);
        panelGeneral.add(panelSuperior,BorderLayout.PAGE_START);

        panelCentral = new JPanel(new GridLayout(0,2));
        txtAreaMensajesExternos = new JTextArea("Mensajes recibidos...");
        txtAreaMensajesExternos.setFont(fuente);
        txtAreaMensajesExternos.setEditable(false);
        txtAreaMensajesExternos.setBorder(bordePanel);
        panelCentral.add(txtAreaMensajesExternos);
        txtAreaMensajesPropios = new JTextArea("Mensajes enviados...");
        txtAreaMensajesPropios.setFont(fuente);
        txtAreaMensajesPropios.setEditable(false);
        txtAreaMensajesPropios.setBorder(bordePanel);
        panelCentral.add(txtAreaMensajesPropios);
        panelGeneral.add(panelCentral,BorderLayout.CENTER);
    }

    protected void agregarEventos() {
        super.agregarEventos();
        btnEnviarMensaje.addActionListener(new EnviarMensaje());
        btnVideollamada.addActionListener(new IniciarVieollamada());
    }

    private class EnviarMensaje implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!archivoAdjunto) {
                // Se enviará un mensaje de texto.
                String texto = txtAreaMensaje.getText();
                if(!texto.equals("")) {
                    MensajeTexto mensaje = new MensajeTexto(texto);
                    enviador.enviarMensaje(mensaje);
                    txtAreaMensaje.setText("");
                    txtAreaMensajesPropios.append("\n\n<<" + mensaje.getFecha().toString() + ">>");
                    txtAreaMensajesPropios.append("\n" + mensaje);
                }
            } else {
                // Se enviará un archivo.
                // Se convierte la ruta del archivo a un formato que acepta Java.
                String ruta = RutaUtilidades.formatearRuta(rutaArchivo);
                MensajeArchivo mensajeArchivo = new MensajeArchivo(new File(ruta));
                enviador.enviarArchivo(mensajeArchivo);
                archivoAdjunto = false;
                txtAreaMensaje.setEnabled(true);
                txtAreaMensajesPropios.append("\n\n<<" + mensajeArchivo.getFecha().toString() + ">>");
                txtAreaMensajesPropios.append("\nARCHIVO ENVIADO:");
                txtAreaMensajesPropios.append("\n" + mensajeArchivo);
                lblStatus.setText("Listo");
            }
        }
    }

    private class IniciarVieollamada implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            videollamadaGUI = new VideollamadaGUI(usuario, enviador);
            videollamadaGUI.setVisible(true);
        }
    }

    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        if (mensaje instanceof MensajeTexto) {
            MensajeTexto msjTexto = (MensajeTexto) mensaje;
            txtAreaMensajesExternos.append("\n\n[" + mensaje.getOrigen() + "]");
            txtAreaMensajesExternos.append("\n<<" + mensaje.getFecha().toString() + ">>");
            txtAreaMensajesExternos.append("\n" + msjTexto);
        } else if (mensaje instanceof MensajeArchivo) {
            MensajeArchivo msjArchivo = (MensajeArchivo) mensaje;
            txtAreaMensajesExternos.append("\n\n[" + mensaje.getOrigen() + "]");
            txtAreaMensajesExternos.append("\n<<" + mensaje.getFecha().toString() + ">>");
            txtAreaMensajesExternos.append("\nSe recibió un archivo: \n" + msjArchivo);
        } else if(mensaje instanceof MensajeVideo) {
            if(videollamadaGUI == null) {
                videollamadaGUI = new VideollamadaGUI(usuario, enviador);
                videollamadaGUI.setVisible(true);
                videollamadaGUI.imprimirMensaje(mensaje);
            } else {
                videollamadaGUI.imprimirMensaje(mensaje);
            }
        } else if(mensaje instanceof MensajeAudio) {
            videollamadaGUI.imprimirMensaje(mensaje);
        }
    }
}