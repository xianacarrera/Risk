package risk;

import risk.gui.Ventana;
import risk.guiMapa.VentanaEdicionMapa;

/**
 * Autoras: Elena Fernández del Sel, Xiana Carrera Alonso
 *
 * Este programa implementa el juego de mesa Risk en su versión clásica. A través de él se experimenta con diferentes
 * funcionalidades de Java.
 */

/*** IMPORTANTE:
 *  En primera instancia siempre se intentará ejecutar el programa leyendo los comandos desde un fichero de texto:
 *  comandos.txt. Si este no existe o se produce algún tipo de error al abrirlo, se activará la introducción manual
 *  para el usuario.
 */

public class Risk {
    // Clase principal del juego. Se encarga de iniciar el mismo

    public static void main(String[] args) {
        new Menu();     // Se crea una instancia de Menu, donde verdaderamente se desarrolla el programa
        new VentanaEdicionMapa().setVisible(true);
        new Ventana();
    }
}