package utilidades;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import java.io.File;

public class Alerta {

    public static File pedirUbicacion(Component componente, String nombreArchivo) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Indica la ruta para guardar el archivo:");
        selector.setSelectedFile(new File(nombreArchivo));
        int eleccion = selector.showSaveDialog(componente);
        if(eleccion == JFileChooser.APPROVE_OPTION) {
            return selector.getSelectedFile();
        } else {
            return null;
        }
    }

    public static File pedirArchivo(Component componente) {
        JFileChooser selector = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        selector.setDialogTitle("Escoge el archivo:");
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int valorRetorno = selector.showOpenDialog(componente);
        if(valorRetorno == JFileChooser.APPROVE_OPTION) {
            return selector.getSelectedFile();
        } else {
            return null;
        }
    }
}
