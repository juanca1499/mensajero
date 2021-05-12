package servidor.gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.ImpresoraChatEscritorio;
import cliente.mensajes.MensajeTexto;
import cliente.mensajes.ReceptorMensajeServidor;
import gui.MensajeroGUI;
import servidor.ConexionServidor;
import servidor.Servidor;
import servidor.mensajes.EnviadorMensajeServidor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MensajeroServidorGUI extends MensajeroGUI {

    private JTextArea txtAreaMensajes;

    private ImpresoraChat impresora;
    private Servidor servidor;

    public MensajeroServidorGUI() throws Exception {
        this.cargarComponentes();
        this.agregarEventos();
        this.inicializarServicios();
        impresora = new ImpresoraChatEscritorio(txtAreaMensajes);
        servidor = new Servidor(impresora);
    }

    protected void cargarComponentes() {
        super.cargarComponentes();
        lblUsuario = new JLabel("Servidor");
        lblUsuario.setFont(fuente);
        panelSuperior.add(lblUsuario,BorderLayout.LINE_START);
        panelGeneral.add(panelSuperior,BorderLayout.PAGE_START);

        panelCentral = new JPanel(new GridLayout(0,1));
        txtAreaMensajes = new JTextArea("Logs...");
        //txtAreaMensajes = new JTextArea("Mensajes del servidor...");
        txtAreaMensajes.setFont(fuente);
        txtAreaMensajes.setEditable(false);
        txtAreaMensajes.setBorder(bordePanel);
        panelCentral.add(txtAreaMensajes);
        panelGeneral.add(panelCentral);
    }

    protected void agregarEventos() {
        super.agregarEventos();
        btnEnviarMensaje.addActionListener(new EnviarMensaje());
    }

    protected void inicializarServicios() {
    }

    private class EnviarMensaje implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!archivoAdjunto) {
                String texto = txtAreaMensaje.getText();
                MensajeTexto mensaje = new MensajeTexto("Cliente1","Cliente2",texto);
                servidor.enviarMensaje(mensaje);
            } else {
                // Missing code
                archivoAdjunto = false;
                txtAreaMensaje.setEnabled(true);
                lblStatus.setText("Listo");
            }
        }
    }
}
