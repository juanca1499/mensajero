package gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeAudio;
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
    private JLabel lblImagenLocal;
    private JLabel lblImagenExterna;
    private JButton btnColgarVllamada;
    //private JButton btnIniciarVllamada;
    private Webcam webcam;
    private CapturadorVideo capturadorVideo;
    private EnviadorMensaje enviador;
    private String usuario;

    public VideollamadaGUI(String usuario, EnviadorMensaje enviador) {
        super("Mensajero Fenix");
        this.usuario = usuario;
        this.enviador = enviador;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
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

        panelVideo = new JPanel(new FlowLayout());
        lblImagenLocal = new JLabel();
        lblImagenExterna = new JLabel();
        panelVideo.add(lblImagenLocal);
        panelVideo.add(lblImagenExterna);
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

    private void enviarFrame(ImageIcon frame) {
        MensajeVideo mensajeVideo = new MensajeVideo(frame);
        enviador.enviarVideo(mensajeVideo);
    }

    private class CapturadorVideo extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Image frame = webcam.getImage();
                    ImageIcon icono = new ImageIcon(frame);
                    enviarFrame(icono);
                    lblImagenLocal.setIcon(icono);
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
            // Se imprime el frame recibido del otro cliente.
            lblImagenExterna.setIcon(((MensajeVideo) mensaje).getFrame());
        } else if(mensaje instanceof MensajeAudio) {
            // Code
        }
    }
}
