package gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MensajeroServidorGUI extends MensajeroGUI {

    private JTextArea txtAreaLogs;
    private EnviadorMensaje enviador;

    public MensajeroServidorGUI(EnviadorMensaje enviador) throws Exception {
        this.enviador = enviador;
        this.cargarComponentes();
        this.agregarEventos();
    }

    protected void cargarComponentes() {
        super.cargarComponentes();
        lblUsuario = new JLabel("Servidor");
        lblUsuario.setFont(fuente);
        panelSuperior.add(lblUsuario,BorderLayout.LINE_START);
        panelGeneral.add(panelSuperior,BorderLayout.PAGE_START);

        panelCentral = new JPanel(new GridLayout(0,1));
        txtAreaLogs = new JTextArea("[SERVIDOR INICIALIZADO]\n");
        txtAreaLogs.setFont(fuente);
        txtAreaLogs.setEditable(false);
        txtAreaLogs.setBorder(bordePanel);
        panelCentral.add(txtAreaLogs);
        panelGeneral.add(panelCentral);
    }

    protected void agregarEventos() {
        super.agregarEventos();
        btnEnviarMensaje.addActionListener(new EnviarMensaje());
    }

    private class EnviarMensaje implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!archivoAdjunto) {
                String texto = txtAreaMensaje.getText();
                MensajeTexto mensaje = new MensajeTexto(texto);
                enviador.enviarMensaje(mensaje);
                txtAreaMensaje.setText("");
                txtAreaLogs.append("\n\n<<[SERVIDOR]>>");
                txtAreaLogs.append("\n" + mensaje);
            } else {
                // Missing code
                archivoAdjunto = false;
                txtAreaMensaje.setEnabled(true);
                lblStatus.setText("Listo");
            }
        }
    }

    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        if (mensaje instanceof MensajeTexto) {
            MensajeTexto msjTexto = (MensajeTexto) mensaje;
            txtAreaLogs.append("\n\n[" + msjTexto.getOrigen() + "]" + " ---> " + "[" + msjTexto.getDestino() + "]");
            txtAreaLogs.append("\n" + msjTexto.getFecha().toString());
            txtAreaLogs.append("\n" + msjTexto);
        } else if (mensaje instanceof MensajeArchivo) {
            MensajeArchivo msjArchivo = (MensajeArchivo) mensaje;
            txtAreaLogs.append("\n\n[" + msjArchivo.getOrigen() + "]" + " ---> " + "[" + msjArchivo.getDestino() + "]");
            txtAreaLogs.append("\n"+ msjArchivo.getFecha().toString());
            txtAreaLogs.append("\n"+ msjArchivo.getArchivo().toString());
        }
    }
}
