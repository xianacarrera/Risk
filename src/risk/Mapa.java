package risk;

import java.util.ArrayList;
import java.util.HashMap;

public class Mapa {

    private ArrayList<ArrayList<Casilla>> casillas;
    private HashMap<String, Continente> continentes;
    private HashMap<String, Pais> paises;

    public Mapa(){
        continentes = new HashMap<>();
        paises = new HashMap<>();
        casillas = new ArrayList<>();

        generarContinentes();
        generarCasillas();
    }

    public void generarContinentes(){
        continentes.put("Asia", new Continente("Asia"));
        continentes.put("África", new Continente("África"));
        continentes.put("Europa", new Continente("Europa"));
        continentes.put("AméricaNorte", new Continente("América del Norte"));
        continentes.put("AméricaSur", new Continente("América del Sur"));
        continentes.put("Oceanía", new Continente("Oceanía"));
    }

    public void generarCasillas(){
        for (int i = 1; i <= 8; i++){
            casillas.add(new ArrayList<>());
            for (int j = 1; j <= 11; j++){
                casillas.get(i - 1).add(new Casilla(i, j));
            }
        }
    }

    public void generarPais(String abrevPais, String abrevContinente, int fila, int columna){
        Casilla casilla = casillas.get(fila - 1).get(columna - 1);
        switch (abrevPais){
            case "Océano":
                casilla.setTipoCasilla(0);
                break;
            case "Océano1":
                casilla.setTipoCasilla(1);
                break;
            case "Océano2":
                casilla.setTipoCasilla(2);
                break;
            default:
                casilla.setTipoCasilla(-1);

                paises.put(abrevPais, new Pais(abrevPais, continentes.get(abrevContinente)));
                continentes.get(abrevContinente).guardarPais(paises.get(abrevPais));
                casilla.setPais(paises.get(abrevPais));
        }
    }

    @Override
    public String toString() {
        Pais tempPais;              // Variable temporal para almacenar un país
        String tempEj, mensaje;     // Variable temporal para almacenar un número de ejércitos y texto del mapa

        // Línea inicial |-----|----|----| ...
        mensaje = "|";
        for(int i = 0; i < 11; i++){
            mensaje += "===========|";
        }
        mensaje += "\n";

        // 8x11 casillas
        for(ArrayList<Casilla> fila : casillas){
            mensaje += "|";   // Primer carácter
            for(Casilla casilla : fila){

                switch (casilla.getTipoCasilla()){
                    case 0:
                        mensaje += "           ";
                        break;
                    case 1:     // Línea roja horizontal
                        mensaje += "\033[0;31m-----------\033[0m";
                        break;
                    case 2:   // Línea roja vertical
                        mensaje += "\033[0;31m     |     \033[0m";
                        break;
                    default:
                        tempPais = casilla.getPais();

                        mensaje += " " + tempPais.getContinente().getColor() + tempPais.getAbreviatura();
                        // Imprime un espacio y el nombre del país con su color como fondo
                        for (int i = 0; i < (9 - tempPais.getAbreviatura().length()); i++) {
                            mensaje += " ";          // Imprime los espacios que falten para llegar a 9 con color
                        }
                        mensaje += "\033[0m ";       // Espacio final (sin color de fondo)
                }

                if ((casillas.indexOf(fila) == 4 && fila.indexOf(casilla) == 3)
                        || (casillas.indexOf(fila) == 5 && fila.indexOf(casilla) == 3)){
                    mensaje += "\033[0;31m";      // Pone la barra roja
                }
                mensaje += "|\033[0m";      // Barra entre casillas
            }
            mensaje += "\n|";
            for(Casilla casilla: fila){

                // TODO: preguntar si hace falta overrride equals()
                switch (casilla.getTipoCasilla()){
                    case 0:
                    case 1:
                        mensaje += "           ";
                        break;
                    case 2:
                        mensaje += "\033[0;31m     |     ";
                        break;
                    default:
                        tempPais = casilla.getPais();
                        // valueOf convierte int a String
                        tempEj = String.valueOf(tempPais.getNumEjercitos());        // Número de ejércitos

                        // + tempEj.getJugador().getColor()
                        mensaje += " " + tempEj;                     // Imprime el número de ejércitos
                        for (int i = 0; i < (10 - tempEj.length()); i++) {
                            mensaje += " ";                      // Espacios que falten
                        }
                }

                if (casillas.indexOf(fila) == 4 && fila.indexOf(casilla) == 3){
                    mensaje += "\033[0;31m|";      // Barra roja
                } else {
                    mensaje += "\033[0m|";      // Barra
                }

            }

            // Separación horizontal
            mensaje += "\n|";
            for(int j = 0; j < 11; j++){
                mensaje += "===========";
                if (casillas.indexOf(fila) == 4 && j == 3){
                    mensaje += "\033[0;31m";         // Hay una línea con un símbolo rojo
                }
                mensaje += "|\033[0m";
            }
            mensaje += "\n";
        }

        return mensaje;
    }

    public Pais getPais(String abrevPais) {
        if (paises.containsKey(abrevPais)) {
            return paises.get(abrevPais);
        }

        System.out.println("El país deseado no existe");
        return null;
    }

    public String preguntarContinentePais(String abrevPais){
        return paises.get(abrevPais).getContinente().getNombre();
    }

    public String preguntarColorPais(String abrevPais){
        return paises.get(abrevPais).getContinente().getColor();
    }

    public ArrayList<Pais> preguntarListaPaises(String abrevCont){
        return continentes.get(abrevCont).getListaPaises();
    }

}
