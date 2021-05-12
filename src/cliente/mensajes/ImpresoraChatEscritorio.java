package cliente.mensajes;

import cliente.interfaces.ImpresoraChat;

import javax.swing.JTextArea;

public class ImpresoraChatEscritorio implements ImpresoraChat {

    private JTextArea chat;

    public ImpresoraChatEscritorio(JTextArea chat) {
        this.chat = chat;
    }

    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        if (mensaje instanceof MensajeTexto) {
            MensajeTexto msjTexto = (MensajeTexto) mensaje;
            chat.append("\n[" + msjTexto.getOrigen() + "]" );
            chat.append("\n" + msjTexto.toString());
        } else if (mensaje instanceof MensajeArchivo) {
            // Code
        }
    }
}
