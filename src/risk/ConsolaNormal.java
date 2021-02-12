package risk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import risk.gui.Ventana;

public class ConsolaNormal implements Consola{

    // Salida por fichero
    private static ConsolaNormal consolaNormal = null;
    private static PrintWriter ficheroSalida = null;
    private static final Color colores = Color.getInstancia();

    public ConsolaNormal() throws Exception {
        BufferedWriter bufferWriter = null;
        for (int i = 0; i < 2; i++){
            bufferWriter = new BufferedWriter(new FileWriter("resultados.txt"));
            ficheroSalida = new PrintWriter(bufferWriter);
        }
    }

    public String leer(){
        Scanner myObj = new Scanner(System.in);
        return myObj.nextLine();
    }


    private static void crearInstancia() throws Exception {
        consolaNormal = new ConsolaNormal();
    }

    public static ConsolaNormal getInstancia() throws Exception {
        if(consolaNormal==null) crearInstancia();
        return consolaNormal;
    }

    public void imprimir(String out) {
        System.out.println(out);
        imprimirPorFicheroSinColores(out);
        Ventana.mostrarTexto(out);
    }

    public void imprimirPorFicheroSinColores(String out){
        // Todas las secuencias especiales de colores (fuente, background y reset) se eliminan antes de imprimir

        out = out.replace(colores.reset(), "");
        out = out.replace(colores.obtenerFuente("AMARILLO"), "");
        out = out.replace(colores.obtenerFuente("AZUL"), "");
        out = out.replace(colores.obtenerFuente("CYAN"), "");
        out = out.replace(colores.obtenerFuente("ROJO"), "");
        out = out.replace(colores.obtenerFuente("VERDE"), "");
        out = out.replace(colores.obtenerFuente("VIOLETA"), "");
        out = out.replace(colores.obtenerBackground("AMARILLO"),"");
        out = out.replace(colores.obtenerBackground("AZUL"),"");
        out = out.replace(colores.obtenerBackground("CYAN"),"");
        out = out.replace(colores.obtenerBackground("ROJO"),"");
        out = out.replace(colores.obtenerBackground("VERDE"),"");
        out = out.replace(colores.obtenerBackground("VIOLETA"),"");
        ficheroSalida.println(out);
    }

    public void escribirFinComandos(){
        ficheroSalida.println("EOF");
        ficheroSalida.close();
    }


    public String formatoSimple(String atributo, String valor) {
        return "\n " + atributo + ": \"" + valor + "\"";
    }

    public String formatoSimple(String atributo, int valor) {
        return "\n " + atributo + ": " + valor;
    }

    public String formatoConjuntoStrings(String atributo, ArrayList<String> valores) {
        String resultado = "\n " + atributo + ": [";
        for (int i = 0; i < valores.size(); i++) {
            if (i == valores.size() - 1) {
                resultado += " \"" + valores.get(i) + "\"";
            } else {
                resultado += " \"" + valores.get(i) + "\",";
            }
        }
        resultado += " ]";
        return resultado;
    }

    public String formatoConjuntoStrings(String atributo, Set<String> valores) {
        String resultado = "\n " + atributo + ": [";
        Iterator<String> itValores = valores.iterator();

        int i = 0;
        while (itValores.hasNext()) {
            if (i == valores.size() - 1) {
                resultado += " \"" + itValores.next() + "\"";
            } else {
                resultado += " \"" + itValores.next() + "\",";
            }
            i++;
        }
        resultado += " ]";
        return resultado;
    }

    public String formatoDosConjuntos(String atributo, Collection<String> valores1, Collection<Integer> valores2) {
        if (valores1.size() != valores2.size()) {
            return "\nError en el tamaño de los conjuntos";
        }

        String resultado = "\n " + atributo + ": [";

        Iterator<String> itValores1 = valores1.iterator();
        Iterator<Integer> itValores2 = valores2.iterator();
        int i = 0;
        while (itValores1.hasNext() && itValores2.hasNext()) {
            if (i == valores1.size() - 1) {             //Último elemento
                resultado += " { \"" + itValores1.next() + "\", " + itValores2.next() + " }";
            } else {                                    //No es el último elemento
                resultado += " { \"" + itValores1.next() + "\", " + itValores2.next() + " },";
            }
            i++;
        }
        resultado += " ]";
        return resultado;
    }

    public String formatoConjuntoCaracter(String atributo, char tipoConjuntoInicio, ArrayList<Integer> valores) {
        String resultado = "\n " + atributo + ": " + tipoConjuntoInicio + " ";
        for (int i = 0; i < valores.size() - 1; i++) {
            resultado += valores.get(i) + ", ";
        }
        resultado += valores.get(valores.size() - 1) + " " + (char) (tipoConjuntoInicio + 2);
        // Se pueden sumar chars e ints (se suma el valor ascii y se devuelve un int)
        // Sumando dos al carácter de apertura se obtiene el mismo carácter, pero de cierre
        return resultado;
    }


    public String mensajeError(int codigo){
        switch (codigo){
            case 99:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Comando no permitido en este momento\""
                        + "\n}\n");
            case 100:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Color no permitido\""
                        + "\n}\n");
            case 101:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Comando incorrecto\""
                        + "\n}\n");
            case 102:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El continente no existe\""
                        + "\n}\n");
            case 103:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El jugador no existe\""
                        + "\n}\n");
            case 104:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El jugador ya existe\""
                        + "\n}\n");
            case 105:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Los jugadores no están creados\""
                        + "\n}\n");
            case 106:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El mapa no está creado\""
                        + "\n}\n");
            case 107:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El mapa ya ha sido creado\""
                        + "\n}\n");
            case 109:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El país no existe\""
                        + "\n}\n");
            case 110:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El país no pertenece al jugador\""
                        + "\n}\n");
            case 111:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El país pertenece al jugador\""
                        + "\n}\n");
            case 112:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Los países no son frontera\""
                        + "\n}\n");
            case 113:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El país ya está asignado\""
                        + "\n}\n");
            case 114:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El color ya está asignado\""
                        + "\n}\n");
            case 115:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"La misión ya está asignada\""
                        + "\n}\n");
            case 116:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"La misión no existe\""
                        + "\n}\n");
            case 117:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El jugador ya tiene asignada una misión\""
                        + "\n}\n");
            case 118:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Las misiones no están asignadas\""
                        + "\n}\n");
            case 119:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Ejércitos no disponibles\""
                        + "\n}\n");
            case 120:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"No hay cartas suficientes\""
                        + "\n}\n");
            case 121:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"No hay configuración de cambio\""
                        + "\n}\n");
            case 122:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Algunas cartas no pertenecen al jugador\""
                        + "\n}\n");
            case 123:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Algunas cartas no existen\""
                        + "\n}\n");
            case 124:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"No hay ejércitos suficientes\""
                        + "\n}\n");
            case 125:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"El identificador no sigue el formato correcto\""
                        + "\n}\n");
            case 126:
                return("{" + formatoSimple("código de error", codigo) + ", "
                        + "\n descripción: \"Carta de equipamiento ya asignada\""
                        + "\n}\n");
            default:
                return "";
        }
    }
}
