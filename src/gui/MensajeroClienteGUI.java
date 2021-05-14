package gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.mensajes.*;
import gui.MensajeroGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MensajeroClienteGUI extends MensajeroGUI {

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
    }

    private class EnviarMensaje implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!archivoAdjunto) {
                String texto = txtAreaMensaje.getText();
                MensajeTexto mensaje = new MensajeTexto(texto);
                enviador.enviarMensaje(mensaje);
                txtAreaMensaje.setText("");
                txtAreaMensajesPropios.append("\n" + mensaje);
            } else {
//                String ruta = lblStatus.getText();
//                String nuevaRuta = "";
//                for(int index = 0; index < ruta.length(); index++) {
//                    char token = ruta.charAt(index);
//                    if(token == '\\') {
//                        nuevaRuta += "\\";
//                    } else {
//                        nuevaRuta += token;
//                    }
//                }
                String nuevaRuta = "C:\\Users\\karlo\\Desktop\\Hola.txt";
                MensajeArchivo mensajeArchivo = new MensajeArchivo(new File(nuevaRuta));
                enviador.enviarArchivo(mensajeArchivo);
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
            txtAreaMensajesExternos.append("\n\n[" + mensaje.getOrigen() + "]");
            txtAreaMensajesExternos.append("\n<<" + mensaje.getFecha().toString() + ">>");
            txtAreaMensajesExternos.append("\n" + msjTexto);
        } else if (mensaje instanceof MensajeArchivo) {
            MensajeArchivo msjArchivo = (MensajeArchivo) mensaje;
            txtAreaMensajesExternos.append("\n\n[" + mensaje.getOrigen() + "]");
            txtAreaMensajesExternos.append("\n<<" + mensaje.getFecha().toString() + ">>");
            txtAreaMensajesExternos.append("\n" + "Se recibió un archivo: " + msjArchivo.getArchivo().getAbsolutePath());
        }
    }
}