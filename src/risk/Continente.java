package risk;

import java.util.*;
import risk.enums.AbrevContinentes;

public class Continente {

    private final String nombre;
    private final String abreviatura;
    private final String color;
    private final int rearme;

    private int numPaisesFrontera;

    private HashMap<String, Pais> paises;

    private static ConsolaNormal consolaNormal;


    public Continente(String abreviatura) throws Exception {

        if (AbrevContinentes.contiene(abreviatura)){        // AbrevContinentes es un enum con las abreviaturas válidas
            this.abreviatura = abreviatura;
        } else {            // La abreviatura leída es incorrecta
            this.abreviatura = null;
            // Inicializar la abreviatura aquí es necesario para que el programa compile (aunque no tenga efecto)
            consolaNormal.imprimir("No es un continente válido\n");
            System.exit(1);
        }

        switch (abreviatura){
            case "África":
                nombre = abreviatura;
                color = "VERDE";
                rearme = 3;
                numPaisesFrontera = 2;
                break;
            case "Europa":
                nombre = abreviatura;
                color = "AMARILLO";
                rearme = 5;
                numPaisesFrontera = 6;
                break;
            case "Oceanía":
                nombre = abreviatura;
                color = "AZUL";
                rearme = 2;
                numPaisesFrontera = 1;
                break;
            case "Asia":
                nombre = abreviatura;
                color = "CYAN";
                rearme = 7;
                numPaisesFrontera = 7;
                break;
            case "AméricaNorte":
                nombre = "América del Norte";
                color = "VIOLETA";
                rearme = 5;
                numPaisesFrontera = 3;
                break;
            case "AméricaSur":
                nombre = "América del Sur";
                color = "ROJO";
                rearme = 2;
                numPaisesFrontera = 2;
                break;
            default:
                nombre = null;      // No se va a ejecutar, pero es necesario para que el programa compile (final)
                color = null;
                rearme = 0;
        }

        paises = new HashMap<>();
        if (consolaNormal == null) consolaNormal = ConsolaNormal.getInstancia();
    }

    public Continente(String abreviatura, String color) throws Exception {
        this.abreviatura = abreviatura;
        this.nombre = abreviatura;
        this.color = color;
        rearme = 0;

        paises = new HashMap<>();
        if (consolaNormal == null) consolaNormal = ConsolaNormal.getInstancia();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){           // La referencia de los objetos es la misma
            return true;
        }

        if (obj == null){           // El otro objeto es nulo
            return false;
        }
        // Es necesario descartar el caso obj == null antes de los siguientes (darían error)

        if (getClass() != obj.getClass()){          // Las clases de los objetos no coinciden
            return false;
        }

        // El otro objeto es un continente, por lo que se puede castear:
        final Continente otro = (Continente) obj;

        // Dos continentes son iguales si coinciden en nombre, abreviatura y color
        if (!this.nombre.equals(otro.getNombre())){
            return false;
        }

        if (!this.abreviatura.equals(otro.getAbreviatura())){
            return false;
        }

        return this.color.equals(otro.getColor());
    }

    @Override
    public String toString() {
        HashMap<String,Integer> infoJugEjers = new HashMap<>();
        // Para cada jugador con países en el continente, se guarda su nombre y el número de ejércitos que tiene en él
        int numeroTotal = 0;

        for (Pais pais : paises.values()){
            if (pais.getJugador() == null){
                return "";
            }
            if (!infoJugEjers.containsKey(pais.getJugador().getNombre())) {
                // Se añade el nombre del jugador al HashMap junto con los ejércitos del país de la iteración actual
                infoJugEjers.put(pais.getJugador().getNombre(), pais.getNumEjercitos());
            } else {        // Se actualiza la información para el jugador que ocupa el país
                // Es necesario separar los casos porque al añadir un jugador por primera vez, no tiene nada asociado
                infoJugEjers.put(pais.getJugador().getNombre(),
                        infoJugEjers.get(pais.getJugador().getNombre()) + pais.getNumEjercitos());
            }

            numeroTotal += pais.getNumEjercitos();
        }

        return ("{" + consolaNormal.formatoSimple("nombre", nombre) + ","
                + consolaNormal.formatoSimple("abreviatura", abreviatura) + ","
                + consolaNormal.formatoDosConjuntos("jugadores", infoJugEjers.keySet(), infoJugEjers.values())
                + "," + consolaNormal.formatoSimple("numeroEjercitos", numeroTotal) + ","
                + consolaNormal.formatoSimple("rearmeContinente", rearme)
                + "\n}\n");
    }

    public String getAbreviatura(){
        return abreviatura;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public int getRearme(){
        return rearme;
    }

    public int getNumPaisesFrontera(){
        return numPaisesFrontera;
    }

    public ArrayList<Pais> getListaPaises() {
        return new ArrayList<>(paises.values());
    }

    // Devuelve un ArrayList<String> con el nombre de todos los países con un jugador asignado
    public ArrayList<String> obtenerListaPaisesOcupados(){
        ArrayList<String> paisesOcupados = new ArrayList<>();
        for (Pais pais : paises.values()){
            if (pais != null && pais.getJugador() != null){
                paisesOcupados.add(pais.getNombre());
            }
        }
        return paisesOcupados;
    }

    // Devuelve un ArrayList<String> con el nombre de todos los países ocupados por un jugador concreto
    public ArrayList<String> obtenerListaPaisesOcupados(Jugador jugador){

        ArrayList<String> paisesOcupados = new ArrayList<>();
        for (Pais pais : paises.values()){
            if (pais != null && jugador.equals(pais.getJugador())){
                paisesOcupados.add(pais.getNombre());
            }
        }
        return paisesOcupados;
    }

    // Devuelve un ArrayList<Integer> con el número de tropas de un jugador en cada uno de sus países del continente
    public ArrayList<Integer> obtenerDatosOcupacion(Jugador jugador){
        ArrayList<Integer> datosOcupacion = new ArrayList<>();
        ArrayList<String> paisesOcupadosJugador = obtenerListaPaisesOcupados(jugador);
        for (String nombrePais : paisesOcupadosJugador){
            datosOcupacion.add(paises.get(nombrePais).getNumEjercitos());
        }
        return datosOcupacion;
    }

    public void guardarPais(Pais pais){
        paises.put(pais.getNombre(), pais);
        /*
         * Se ha escogido el nombre como clave y no la abreviatura por una cuestión de conveniencia respecto de las
         * funciones obtenerListaPaisesOcupados y obtenerDatosOcupacion
         */
    }

    public void actualizarOcupacion(){
        /*
         * Desde este método se va a comprobar si el continente está ocupado por completo por un mismo jugador.
         * En caso afirmativo, se guardará esta información dentro del propio jugador, y no del continente, por
         * comodidad en el manejo de los datos (en todos los casos en los que se necesita consultar si esto es cierto,
         * la consulta se realiza desde el punto de vista del jugador y no del continente).
         */

        Iterator<Pais> itPaises = paises.values().iterator();

        Pais pais = itPaises.next();        // Al menos hay un país
        Jugador jugador = pais.getJugador();
        if (jugador == null){
            // Un país no tiene asignado jugador
            return;         // No todos los países son controlados por el mismo jugador
        }

        while (itPaises.hasNext()){
            pais = itPaises.next();
            if (pais.getJugador() != null && !pais.getJugador().equals(jugador)){
                /*
                 * Antes de llamar a equals se debe comprobar que a ese país ya se le haya asignado un jugador. En caso
                 * contrario, pais.getJugador() devolverá null y habría un error durante la ejecución.
                 */

                // No todos los países pertenecen al mismo jugador
                jugador.noControla(this);
                // El jugador no controla el continente. Si lo estaba haciendo anteriormente, debe actualizarse el dato
                return;
            }
        }

        //Si se llega al final del bucle, no se ha entrado en el if en ninguna iteración. Todos los jugadores coinciden
        jugador.controla(this);
    }
}