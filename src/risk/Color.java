package risk;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Color es una clase de apoyo que contiene información y métodos de apoyo al uso de colores a lo largo del proyecto.
 *
 * Durante la ejecución completa del programa, solo se crea una instancia de Color. Todos sus atributos son estáticos y
 * finales (constantes), por lo que su uso es similar al que se podría hacer de una clase abstracta. Utilizando una
 * sola instancia, evitamos tener varias copias del mismo objeto donde todos tienen los mismos atributos y métodos.
 *
 * El motivo por el que se ha elegido no hacer esta clase abstracta es principalmente porque no forma parte de ninguna
 * jerarquía de herencia, por lo que su uso se vería limitado (no se podría llamar a su constructor).
 */


public class Color {

    private static final ArrayList<String> coloresPermitidos = new ArrayList<>();
    private static final String reset = "\033[0m";                              // Forma de resetear todos los colores
    private static final HashMap<String, String> fuente = new HashMap<>();      // Forma de imprimir cada color
    private static final HashMap<String, String> background = new HashMap<>();  // Forma de imprimir fondos con color

    private static Color color = null;

    public Color(){
        // Se guarda en cada atributo la información pertinente

        coloresPermitidos.add("AMARILLO");
        coloresPermitidos.add("AZUL");
        coloresPermitidos.add("CYAN");
        coloresPermitidos.add("ROJO");
        coloresPermitidos.add("VERDE");
        coloresPermitidos.add("VIOLETA");

        fuente.put("AMARILLO", "\033[0;33m");
        fuente.put("AZUL", "\033[0;34m");
        fuente.put("CYAN", "\033[0;36m");
        fuente.put("ROJO", "\033[0;31m");
        fuente.put("VERDE", "\033[0;32m");
        fuente.put("VIOLETA", "\033[0;35m");

        background.put("AMARILLO", "\033[43m");
        background.put("AZUL", "\033[44m");
        background.put("CYAN", "\033[46m");
        background.put("ROJO", "\033[41m");
        background.put("VERDE", "\033[42m");
        background.put("VIOLETA", "\033[45m");
    }

    private static void crearInstancia(){
        color = new Color();
    }

    public static Color getInstancia(){
        if (color == null) crearInstancia();        // Creación de la primera instancia de la clase
        return color;
    }

    public boolean esPermitido(String color){
        return coloresPermitidos.contains(color);
    }

    public String reset(){
        return reset;
    }

    public String obtenerFuente(String color) {
        return fuente.get(color);           // Devuelve null si el color no está en el HashMap
    }

    public String obtenerBackground(String color){
        return background.get(color);        // Devuelve null si el color no está en el HashMap
    }
}
