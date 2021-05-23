package utilidades;

public class RutaUtilidades {
    public static String formatearRuta(String ruta) {
        String nuevaRuta = "";
        for(int index = 0; index < ruta.length(); index++) {
            char token = ruta.charAt(index);
            if(token == '\\') {
                nuevaRuta += token + "\\";
            } else {
                nuevaRuta += token;
            }
        }
        return nuevaRuta;
    }
}
