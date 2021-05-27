package gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeVideo;
import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VideollamadaGUI extends JFrame implements ImpresoraChat {
    private JPanel panelGeneral;
    private JPanel panelVideo;
    private JPanel panelOpciones;
    private JLabel lblImagen;
    private JButton btnColgarVllamada;
    //private JButton btnIniciarVllamada;
    private Webcam webcam;
    private CapturadorVideo capturadorVideo;

    public VideollamadaGUI() {
        super("Mensajero Fenix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        cargarComponentes();
        agregarEventos();
        iniciarVideo();
    }

    private void cargarComponentes() {
        panelGeneral = new JPanel(new BorderLayout());
        add(panelGeneral, BorderLayout.CENTER);

        panelVideo = new JPanel(new BorderLayout());
        lblImagen = new JLabel();
        panelVideo.add(lblImagen, BorderLayout.CENTER);
        panelGeneral.add(panelVideo,BorderLayout.CENTER);

        panelOpciones = new JPanel(new FlowLayout());
        //btnIniciarVllamada = new JButton("Iniciar videollamada");
        btnColgarVllamada = new JButton("Colgar videollamda");
        //panelOpciones.add(btnIniciarVllamada);
        panelOpciones.add(btnColgarVllamada);
        panelGeneral.add(panelOpciones, BorderLayout.PAGE_END);
    }

    private void agregarEventos() {
        btnColgarVllamada.addActionListener(new ColgarVideollamda(this));
    }

    private void iniciarVideo() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320,240));
        webcam.open();
        capturadorVideo = new CapturadorVideo();
        capturadorVideo.start();
    }

    private void detenerVideo() {
        capturadorVideo.interrupt();
        webcam.close();
    }

    private class CapturadorVideo extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Image frame = webcam.getImage();
                    lblImagen.setIcon(new ImageIcon(frame));
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class ColgarVideollamda implements ActionListener {
        private VideollamadaGUI ventana;

        public ColgarVideollamda(VideollamadaGUI ventana) {
            this.ventana = ventana;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ventana.detenerVideo();
            ventana.dispose();
        }
    }

    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        if(mensaje instanceof MensajeVideo) {
            // TODO
        }
    }
}
