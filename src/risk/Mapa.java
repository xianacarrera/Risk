package risk;

import java.util.ArrayList;
import java.util.HashMap;

import risk.excepciones.ExcepcionGeo;

public class Mapa {

    private static ArrayList<ArrayList<Casilla>> casillas;           // Matriz que representa el mapa
    private static HashMap<String, Continente> continentes;          // Abreviatura del continente, continente
    private static HashMap<String, Pais> paises;                     // Abreviatura del país, país

    private static Color color;
    private static ConsolaNormal consolaNormal;

    public Mapa() throws Exception {
        // Inicialización de atributos
        continentes = new HashMap<>();
        paises = new HashMap<>();
        casillas = new ArrayList<>();

        color = Color.getInstancia();
        consolaNormal = ConsolaNormal.getInstancia();

        // Se completa el mapa
        generarContinentes();
        generarCasillas();
    }

    public Mapa(ArrayList<String> continen, ArrayList<String> colores) throws Exception {
        continentes = new HashMap<>();
        paises = new HashMap<>();
        casillas = new ArrayList<>();

        color = Color.getInstancia();
        consolaNormal = ConsolaNormal.getInstancia();

        generarContinentes(continen, colores);
        generarCasillas();
    }


    @Override
    public String toString() {
        Pais tempPais;              // Variable temporal para almacenar un país
        int tempEj;                 // Variable temporal para almacenar un número de ejércitos
        String mensaje;             // Texto del mapa

        // Línea inicial |-----|----|----|...
        mensaje = "|";
        for (int i = 0; i < 11; i++) {
            mensaje += "===========|";
        }
        mensaje += "\n";            // Siguiente línea

        // 8x11 casillas
        for (ArrayList<Casilla> fila : casillas) {            // Cada una de las columnas del mapa
            mensaje += "|";         // Primer carácter

            for (Casilla casilla : fila) {            // Cada una de las casillas dentro de una fila
                switch (casilla.getTipoCasilla()) {
                    case 0:                             // Océano
                        mensaje += "           ";
                        break;
                    case 1:                             // Línea roja horizontal
                        mensaje += color.obtenerFuente("ROJO") + "-----------" + color.reset();
                        break;
                    case 2:                             // Línea roja vertical
                        mensaje += color.obtenerFuente("ROJO") + "     |     " + color.reset();
                        break;
                    default:                            // Un país
                        tempPais = casilla.getPais();       // El país de la casilla

                        // Se imprime un espacio y el nombre del país con su color como fondo
                        mensaje += " " + color.obtenerBackground(tempPais.getContinente().getColor()) +
                                tempPais.getAbreviatura();

                        for (int i = 0; i < (9 - tempPais.getAbreviatura().length()); i++) {
                            mensaje += " ";
                            // Se imprimen los espacios que falten para llegar a 9 manteniendo el color
                        }
                        mensaje += color.reset() + " ";       // Espacio final (sin color de fondo)
                }

                if ((casillas.indexOf(fila) == 4 && fila.indexOf(casilla) == 3)
                        || (casillas.indexOf(fila) == 5 && fila.indexOf(casilla) == 3)) {
                    // En estas dos casillas, la barra final es de color rojo
                    mensaje += color.obtenerFuente("ROJO");
                }
                mensaje += "|" + color.reset();      // Barra entre casillas
            }

            mensaje += "\n|";       // Nueva línea y carácter inicial

            for (Casilla casilla : fila) {             // Para cada una de las casillas de la fila
                switch (casilla.getTipoCasilla()) {
                    case 0:                         // Océano
                    case 1:                         // Océano con conexión horizontal
                        mensaje += "           ";
                        break;
                    case 2:                         // Océano con conexión vertical
                        mensaje += color.obtenerFuente("ROJO") + "     |     ";     // Barra vertical roja
                        break;
                    default:                        // Un país
                        tempPais = casilla.getPais();       // El país de la casilla

                        tempEj = tempPais.getNumEjercitos();    // Su número de ejércitos

                        if (tempEj > 0) {
                            mensaje += " " + color.obtenerFuente(tempPais.getJugador().getColor()) + tempEj;
                            // Se imprime el número de ejércitos con la fuente del color que tenga el jugador

                            for (int i = 0; i < (10 - String.valueOf(tempEj).length()); i++) {
                                mensaje += " ";             // Espacios que falten
                            }
                        } else {
                            mensaje += " -         ";       // Línea
                        }
                }

                if (casillas.indexOf(fila) == 4 && fila.indexOf(casilla) == 3) {
                    // En esta casilla en específico, la barra final es roja
                    mensaje += color.obtenerFuente("ROJO") + "|";      // Barra roja
                } else {
                    mensaje += color.reset() + "|";      // Barra sin color
                }

            }

            // Separación horizontal
            mensaje += "\n|";           // Nueva línea y primer carácter

            for (int j = 0; j < 11; j++) {            // Para las 11 casillas de una fila
                mensaje += "===========";
                if (casillas.indexOf(fila) == 4 && j == 3) {
                    // En esta casilla hay una barra en rojo
                    mensaje += color.obtenerFuente("ROJO");
                }
                mensaje += "|" + color.reset();     // Barra y reseteo del color
            }
            mensaje += "\n";            // Nueva línea del bloque de la fila
        }

        return mensaje + "\n";
    }

    public void generarContinentes() throws Exception {
        // Se crea cada uno de los 6 continentes, y se guardan en un HashMap
        continentes.put("Asia", new Continente("Asia"));
        continentes.put("África", new Continente("África"));
        continentes.put("Europa", new Continente("Europa"));
        continentes.put("AméricaNorte", new Continente("AméricaNorte"));
        continentes.put("AméricaSur", new Continente("AméricaSur"));
        continentes.put("Oceanía", new Continente("Oceanía"));
    }

    public void generarContinentes(ArrayList<String> abreviaturas, ArrayList<String> colores) throws Exception {
        continentes.put(abreviaturas.get(0), new Continente(abreviaturas.get(0), colores.get(0)));
        continentes.put(abreviaturas.get(1), new Continente(abreviaturas.get(1), colores.get(1)));
        continentes.put(abreviaturas.get(2), new Continente(abreviaturas.get(2), colores.get(2)));
        continentes.put(abreviaturas.get(3), new Continente(abreviaturas.get(3), colores.get(3)));
        continentes.put(abreviaturas.get(4), new Continente(abreviaturas.get(4), colores.get(4)));
        continentes.put(abreviaturas.get(5), new Continente(abreviaturas.get(5), colores.get(5)));
    }

    public void generarCasillas() {
        for (int i = 1; i <= 8; i++) {
            casillas.add(new ArrayList<>());        // 8 ArrayLists <=> 8 filas
            for (int j = 1; j <= 11; j++) {
                casillas.get(i - 1).add(new Casilla());         // 11 casillas por fila <=> 11 columnas
            }
        }
    }

    public void generarPais(String abrevPais, String abrevContinente, int fila, int columna) throws Exception {
        // Fila y columna van del 1 al 8 y del 1 al 11 (ambos inclusive), respectivamente
        Casilla casilla = casillas.get(fila - 1).get(columna - 1);
        switch (abrevPais) {
            case "Océano":                  // Casilla sin país
                casilla.setTipoCasilla(0);
                break;
            case "Océano1":                 // Casilla sin país que en el mapa tiene una conexión horizontal
                casilla.setTipoCasilla(1);
                break;
            case "Océano2":                 // Casilla sin país que en el mapa tiene una conexión vertical
                casilla.setTipoCasilla(2);
                break;
            default:                        // Casilla con país
                casilla.setTipoCasilla(-1);

                // Se genera el país y se guarda en el HashMap de Mapa
                paises.put(abrevPais, new Pais(abrevPais, continentes.get(abrevContinente)));
                // Se almacena también en su respectivo continente
                continentes.get(abrevContinente).guardarPais(paises.get(abrevPais));
                casilla.setPais(paises.get(abrevPais));       // Se establece una relación de la casilla al país
        }
    }

    public void generarPais(String abrevPais, String nombrePais,
                            String abrevContinente, int fila, int columna) throws Exception {
        // Fila y columna van ahora del 0 al 7 y del 0 al 10
        Casilla casilla = casillas.get(fila).get(columna);
        casilla.setTipoCasilla(-1);

        // Se genera el país y se guarda en el HashMap de Mapa
        paises.put(abrevPais, new Pais(abrevPais, nombrePais, continentes.get(abrevContinente)));
        // Se almacena también en su respectivo continente
        continentes.get(abrevContinente).guardarPais(paises.get(abrevPais));
        casilla.setPais(paises.get(abrevPais));       // Se establece una relación de la casilla al país

    }

    public void generarFronteras() {
        for (ArrayList<Casilla> fila : casillas) {            // Cada una de las filas
            for (Casilla casilla : fila) {                   // Cada una de las columnas

                if (casilla.getTipoCasilla() == -1) {         // La casilla debe ser un país para añadir frontera
                    Pais pais = casilla.getPais();

                    // Casilla en la fila anterior, misma columna (i-1, j)
                    if (casillas.indexOf(fila) > 0) {          // No puede ser la primera fila
                        Casilla temp = casillas.get(casillas.indexOf(fila) - 1).get(fila.indexOf(casilla));
                        // Casilla justo encima de la actual

                        if (temp.getTipoCasilla() == -1) {              // La casilla es un país
                            pais.addFronteraPais(temp.getPais());      // Se añade como frontera
                        } else {
                            // Estas dos conexiones indirectas tienen como condición necesaria que la casilla anterior
                            // sea de océano
                            switch (pais.getAbreviatura()) {
                                case "ANorte":
                                    pais.addFronteraPais(paises.get("EurOcc"));
                                    break;
                                case "Egipto":
                                    pais.addFronteraPais(paises.get("EurSur"));
                                    break;
                                case "Indonesia":
                                    pais.addFronteraPais(paises.get("SAsiático"));
                                    break;
                            }
                        }
                    }

                    // Casilla en la fila posterior, misma columna (i+1, j)
                    if (casillas.indexOf(fila) < 7) {                  // No puede ser el extremo final
                        Casilla temp = casillas.get(casillas.indexOf(fila) + 1).get(fila.indexOf(casilla));
                        // Casilla justo debajo de la actual

                        if (temp.getTipoCasilla() == -1) {          // La casilla siguiente es un país
                            pais.addFronteraPais(temp.getPais());
                        } else {                                    // Conexiones indirectas
                            switch (pais.getAbreviatura()) {
                                case "EurOcc":
                                    pais.addFronteraPais(paises.get("ANorte"));
                                    break;
                                case "EurSur":
                                    pais.addFronteraPais(paises.get("Egipto"));
                                    break;
                                case "SAsiático":
                                    pais.addFronteraPais(paises.get("Indonesia"));
                                    break;
                            }
                        }
                    }

                    //Casilla en la misma fila, columna anterior (i, j-1)
                    if (fila.indexOf(casilla) > 0) {             // No puede estar en la primera fila
                        Casilla temp = casillas.get(casillas.indexOf(fila)).get(fila.indexOf(casilla) - 1);
                        // Casilla justo a la izquierda

                        if (temp.getTipoCasilla() == -1) {           // Tiene que ser un país
                            pais.addFronteraPais(temp.getPais());
                        } else {                                     // Conexiones indirectas
                            switch (pais.getAbreviatura()) {
                                case "Islandia":
                                    pais.addFronteraPais(paises.get("Groenlan"));
                                    break;
                                case "ANorte":
                                    pais.addFronteraPais(paises.get("Brasil"));
                                    break;
                            }
                        }
                    } else if (fila.indexOf(casilla) == 0 && casillas.indexOf(fila) == 0) {     // Columna 0, fila 0
                        // Es necesario separar este caso porque la columna no cumple la condición > 0
                        pais.addFronteraPais(paises.get("Kamchatka"));   // Se añade Kamchatka a la frontera de Alaska
                    }

                    //Casilla en la misma fila, columna posterior (i, j+1)
                    if (fila.indexOf(casilla) < 10) {        // No puede estar en la última fila
                        Casilla temp = casillas.get(casillas.indexOf(fila)).get(fila.indexOf(casilla) + 1);
                        // Casilla justo a la derecha

                        if (temp.getTipoCasilla() == -1) {      // La casilla debe ser un país
                            pais.addFronteraPais(temp.getPais());
                        } else {
                            switch (pais.getAbreviatura()) {
                                case "Groenlan":
                                    pais.addFronteraPais(paises.get("Islandia"));
                                    break;
                                case "Brasil":
                                    pais.addFronteraPais(paises.get("ANorte"));
                                    break;
                                case "Kamchatka":
                                    pais.addFronteraPais(paises.get("Alaska"));
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void generarFronterasEditadas() {
        for (ArrayList<Casilla> fila : casillas) {            // Cada una de las filas
            for (Casilla casilla : fila) {                   // Cada una de las columnas

                if (casilla.getTipoCasilla() == -1) {         // La casilla debe ser un país para añadir frontera
                    Pais pais = casilla.getPais();

                    // Casilla en la fila anterior, misma columna (i-1, j)
                    if (casillas.indexOf(fila) > 0) {          // No puede ser la primera fila
                        Casilla temp = casillas.get(casillas.indexOf(fila) - 1).get(fila.indexOf(casilla));
                        // Casilla justo encima de la actual

                        if (temp.getTipoCasilla() == -1) {              // La casilla es un país
                            pais.addFronteraPais(temp.getPais());      // Se añade como frontera
                        }
                    }

                    // Casilla en la fila posterior, misma columna (i+1, j)
                    if (casillas.indexOf(fila) < 7) {                  // No puede ser el extremo final
                        Casilla temp = casillas.get(casillas.indexOf(fila) + 1).get(fila.indexOf(casilla));
                        // Casilla justo debajo de la actual

                        if (temp.getTipoCasilla() == -1) {          // La casilla siguiente es un país
                            pais.addFronteraPais(temp.getPais());
                        }
                    }

                    //Casilla en la misma fila, columna anterior (i, j-1)
                    if (fila.indexOf(casilla) > 0) {             // No puede estar en la primera fila
                        Casilla temp = casillas.get(casillas.indexOf(fila)).get(fila.indexOf(casilla) - 1);
                        // Casilla justo a la izquierda

                        if (temp.getTipoCasilla() == -1) {           // Tiene que ser un país
                            pais.addFronteraPais(temp.getPais());
                        }
                    }

                    //Casilla en la misma fila, columna posterior (i, j+1)
                    if (fila.indexOf(casilla) < 10) {        // No puede estar en la última fila
                        Casilla temp = casillas.get(casillas.indexOf(fila)).get(fila.indexOf(casilla) + 1);
                        // Casilla justo a la derecha

                        if (temp.getTipoCasilla() == -1) {      // La casilla debe ser un país
                            pais.addFronteraPais(temp.getPais());
                        }
                    }
                }
            }
        }
    }

    public void crearFronterasIndirectasEditadas(ArrayList<String> fronteras) {
        for (String frontera : fronteras) {
            // Cada frontera se compone de: fila1 + columna1 + fila2 + columna2, referido a casillas 1 y 2
            // Está asegurado que ninguna de las casillas corresponde a un océano
            Casilla casilla = casillas.get(Character.getNumericValue(frontera.charAt(0))).
                    get(Character.getNumericValue(frontera.charAt(1)));
            Casilla casilla2 = casillas.get(Character.getNumericValue(frontera.charAt(2))).
                    get(Character.getNumericValue(frontera.charAt(3)));

            if (casilla.getTipoCasilla() == -1 && casilla2.getTipoCasilla() == -1) {
                // Relación simétrica. Se añaden ambas fronteras
                casilla.getPais().addFronteraPais(casilla2.getPais());
                casilla2.getPais().addFronteraPais(casilla.getPais());
            }
        }

    }

    // Getters
    public Continente getContinente(String abrevContinente) {
        return continentes.get(abrevContinente);
    }

    public HashMap<String, Pais> getPaises() {
        return paises;
    }

    public HashMap<String, Continente> getContinentes() {
        return continentes;
    }

    public Pais obtenerPais(String abrevPais) throws ExcepcionGeo {
        // No se toma del HashMap directamente para poder imprimir un error si el país pedido no es correcto
        if (paises.containsKey(abrevPais)) {
            return paises.get(abrevPais);
        }

        // El país no existe
        throw new ExcepcionGeo(consolaNormal.mensajeError(109));
    }

    public ArrayList<ArrayList<Casilla>> getCasillas() {
        return casillas;
    }
}
