package risk;

import java.util.ArrayList;

public class Formateo {

    public Formateo(){}

    public String formatoSimple(String atributo, String valor){
        return "  " + atributo + ": \"" + valor + "\",\n";
    }

    public String formatoSimple(String atributo, int valor){
        return "  " + atributo + ": " + valor + ",\n";
    }

    public String formatoSimpleFinal(String atributo, String valor){
        return "  " + atributo + ": \"" + valor + "\"\n";
    }

    public String formatoSimpleFinal(String atributo, int valor){
        return "  " + atributo + ": " + valor + ",\n";
    }

    public String formatoConjuntoStrings(String atributo, ArrayList<String> valores){
        String resultado = "  " + atributo + ": [";
        for (int i = 0; i < valores.size(); i++){
            if (i == 0){
                resultado += " { \"" + valores.get(i) + "\" },\n";
            } else if (i != valores.size() - 1){
                resultado += "\t\t\t{ \"" + valores.get(i) + "\" },\n";
            } else {
                resultado += "\t\t\t{ \"" + valores.get(i) + "\" }\n";
            }
        }
        resultado += "\t\t],\n";

        return resultado;
    }

    public String formatoConjuntoIntegers(String atributo, ArrayList<Integer> valores){
        String resultado = "  " + atributo + ": [";
        for (int i = 0; i < valores.size(); i++){
            if (i == 0){
                resultado += " { " + valores.get(i) + " },\n";
            } else if (i != valores.size() - 1){
                resultado += "\t\t\t{ " + valores.get(i) + " },\n";
            } else {
                resultado += "\t\t\t{ " + valores.get(i) + " }\n";
            }
        }
        resultado += "\t\t],\n";

        return resultado;
    }

    public String formatoConjuntoStringsFinal(String atributo, ArrayList<String> valores){
        String resultado = "  " + atributo + ": [";
        for (int i = 0; i < valores.size(); i++){
            if (i == 0){
                resultado += " { \"" + valores.get(i) + " },\n";
            } else if (i != valores.size() - 1){
                resultado += "\t\t\t{ \"" + valores.get(i) + " },\n";
            } else {
                resultado += "\t\t\t{ \"" + valores.get(i) + " }\n";
            }
        }
        resultado += "\t\t]\n";

        return resultado;
    }

    public String formatoDosConjuntosFinal(String atributo, ArrayList<String> valores1, ArrayList<Integer> valores2) {
        if (valores1.size() != valores2.size()) {
            return "ERROR";
        }

        String resultado = "  " + atributo + ": [";
        for (int i = 0; i < valores1.size(); i++) {
            if (i == 0) {
                resultado += " { \"" + valores1.get(i) + ", " + valores2.get(i) + " },\n";
            } else if (i != valores1.size() - 1) {
                resultado += "\t\t\t{ \"" + valores1.get(i) + ", " + valores2.get(i) + " },\n";
            } else {
                resultado += "\t\t\t{ \"" + valores1.get(i) + ", " + valores2.get(i) + " }\n";
            }
        }
        resultado += "\t\t]\n";

        return resultado;
    }

    public String formatoConjuntoCaracter(String atributo, char tipoConjuntoInicio, ArrayList<Integer> valores){
        String resultado = "  " + atributo + ": " + tipoConjuntoInicio + " ";
        for (int i = 0; i < valores.size() - 1; i++) {
            resultado += valores.get(i) + ", ";
        }
        resultado += valores.get(valores.size()) + " " + (char) (tipoConjuntoInicio + 2) + ",\n";
        // Se pueden sumar chars e ints (se suma el valor ascii y se devuelve un int)

        return resultado;
    }
}
