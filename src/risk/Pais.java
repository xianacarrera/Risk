package risk;

import java.util.ArrayList;
import risk.ejercitos.*;
import risk.enums.Abreviaturas;

public class Pais {

    // Atributos que definen un país. Son finales porque no deberían cambiar nunca para un objeto dado
    private final String nombre;
    private final String abreviatura;
    private final Continente continente;

    private Jugador jugador;     // Jugador que ocupa actualmente el país (sí puede cambiar a lo largo de la partida)
    private Ejercito ejercito;            // Representación de las tropas que se encuentran en el país
    private static ConsolaNormal consolaNormal;           // Clase necesaria para imprimir y guardar por fichero

    private final ArrayList<Pais> fronterasPais;    // Las fronteras están determinadas para cada país y no cambian
    private int numVecesOcupado;                    // Se debe registrar el número de veces que ha sido ocupado el país

    /************************************************************************************************************/

    public Pais(String abreviatura, Continente continente) throws Exception {
        /* Los países se generan desde un fichero destinado al respecto. Está asegurado que cada abreviatura se envía
         * junto al continente correcto para ella.
         *
         * Es cierto que se podría crear un país a partir únicamente de su abreviatura, y determinar su continente
         * en función de la misma. No obstante, se ha optado por la solución que aquí se presenta para aprovechar
         * la rapidez y mayor claridad en el código que ofrece la lectura directa del dato.
         */

        if (Abreviaturas.contiene(abreviatura)){            // La abreviatura leída es válida
            this.abreviatura = abreviatura;
        } else {
            this.abreviatura = null;
            // Inicializar la abreviatura aquí es necesario para la compilación (aunque no tenga efecto)
            System.exit(1);             // Los datos del fichero deben ser corregidos
        }

        switch (this.abreviatura) {                 // La abreviatura determina el nombre del país
            case "Afgan":
                this.nombre = "Afganistán";
                break;
            case "China":
                this.nombre = "China";
                break;
            case "India":
                this.nombre = "India";
                break;
            case "Irkutsk":
                this.nombre = "Irkutsk";
                break;
            case "Japón":
                this.nombre = "Japón";
                break;
            case "Kamchatka":
                this.nombre = "Kamchatka";
                break;
            case "Mongolia":
                this.nombre = "Mongolia";
                break;
            case "OMedio":
                this.nombre = "Oriente Medio";
                break;
            case "Siberia":
                this.nombre = "Siberia";
                break;
            case "SAsiático":
                this.nombre = "Sureste Asiático";
                break;
            case "Urales":
                this.nombre = "Urales";
                break;
            case "Yakustsk":
                this.nombre = "Yakustsk";
                break;
            case "ANorte":
                this.nombre = "África del Norte";
                break;
            case "AOriental":
                this.nombre = "África Oriental";
                break;
            case "Congo":
                this.nombre = "Congo";
                break;
            case "Egipto":
                this.nombre = "Egipto";
                break;
            case "Madagasca":
                this.nombre = "Madagascar";
                break;
            case "Sudáfrica":
                this.nombre = "Sudáfrica";
                break;
            case "Escandina":
                this.nombre = "Escandinavia";
                break;
            case "EurNorte":
                this.nombre = "Europa del Norte";
                break;
            case "EurOcc":
                this.nombre = "Europa Occidental";
                break;
            case "EurSur":
                this.nombre = "Europa del Sur";
                break;
            case "GBretaña":
                this.nombre = "Gran Bretaña";
                break;
            case "Islandia":
                this.nombre = "Islandia";
                break;
            case "Rusia":
                this.nombre = "Rusia";
                break;
            case "Alaska":
                this.nombre = "Alaska";
                break;
            case "Alberta":
                this.nombre = "Alberta";
                break;
            case "AmeCentra":
                this.nombre = "América Central";
                break;
            case "Groenlan":
                this.nombre = "Groenlandia";
                break;
            case "Ontario":
                this.nombre = "Ontario";
                break;
            case "Quebec":
                this.nombre = "Quebec";
                break;
            case "TNoroeste":
                this.nombre = "Territorios del Noroeste";
                break;
            case "USAEste":
                this.nombre = "Estados Unidos del Este";
                break;
            case "USAOeste":
                this.nombre = "Estados Unidos del Oeste";
                break;
            case "Venezuela":
                this.nombre = "Venezuela";
                break;
            case "Perú":
                this.nombre = "Perú";
                break;
            case "Brasil":
                this.nombre = "Brasil";
                break;
            case "Argentina":
                this.nombre = "Argentina";
                break;
            case "Indonesia":
                this.nombre = "Indonesia";
                break;
            case "NGuinea":
                this.nombre = "Nueva Guinea";
                break;
            case "AusOccid":
                this.nombre = "Australia Occidental";
                break;
            case "AusOrient":
                this.nombre = "Australia Oriental";
                break;
            default:
                this.nombre = null;
                // Aunque esta línea no se ejecutará nunca, es necesaria para que la compilación del programa
        }

        this.continente = continente;       // La propia clase Continente controla que este esté bien creado

        numVecesOcupado = -1;   // Empieza a -1 para que se ponga a 0 después de que se asignen todos los países
        if (consolaNormal == null) consolaNormal = ConsolaNormal.getInstancia();
        // Formateo es estático. Solo es necesario obtener una instancia si este es el primer país en crearse
        fronterasPais = new ArrayList<>();
    }

    public Pais(String abreviatura, String nombre, Continente continente) throws Exception {
        /* No se comprueba la validez de las abreviaturas */
        this.abreviatura = abreviatura;
        this.nombre = nombre;       // El nombre tiene que ser indicado

        this.continente = continente;       // La propia clase Continente controla que este esté bien creado

        numVecesOcupado = -1;   // Empieza a -1 para que se ponga a 0 después de que se asignen todos los países
        if (consolaNormal == null) consolaNormal = ConsolaNormal.getInstancia();
        // Formateo es estático. Solo es necesario obtener una instancia si este es el primer país en crearse
        fronterasPais = new ArrayList<>();
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

        // El otro objeto es un país, por lo que se puede castear:
        final Pais otro = (Pais) obj;

        // Dos países son iguales si y solo si coinciden en nombre, abreviatura y continente:

        if (!this.nombre.equals(otro.getNombre())){
            return false;
        }

        if (!this.abreviatura.equals(otro.getAbreviatura())){
            return false;
        }

        return this.continente.equals(otro.getContinente());
    }

    @Override
    public String toString() {
        if (jugador == null){
            return "";
        }
        ArrayList<String> fronteras = new ArrayList<>();

        for (Pais p : fronterasPais){
            fronteras.add(p.getNombre());
        }

        return("{"
                + consolaNormal.formatoSimple("nombre", nombre) + ","
                + consolaNormal.formatoSimple("abreviatura", abreviatura) + ","
                + consolaNormal.formatoSimple("continente", continente.getNombre()) + ","
                + consolaNormal.formatoConjuntoStrings("frontera", fronteras) + ","
                + consolaNormal.formatoSimple("jugador", jugador.getNombre()) + ","
                + consolaNormal.formatoSimple("numeroEjercitos", ejercito.getNumTropas()) + ","
                + consolaNormal.formatoSimple("numeroVecesOcupado", numVecesOcupado)
                + "\n}\n");
    }

    // Getters
    public String getAbreviatura() {
        return abreviatura;
    }
    public String getNombre() {
        return nombre;
    }
    public Continente getContinente() {
        return this.continente;
    }
    public Jugador getJugador(){
        return jugador;
    }
    public Ejercito getEjercito(){
        return ejercito;
    }
    public ArrayList<Pais> getFronterasPais(){
        return fronterasPais;
    }
    public int getNumEjercitos() {
        if (ejercito != null) return ejercito.getNumTropas();
        return 0;
    }

    // Setters
    public void setNumEjercitos(int numTropas){
        ejercito.setNumTropas(numTropas);
    }

    // Otras funciones
    public void ocuparPais(Jugador jugador, boolean batalla, boolean dosEjercitos){
        // Con el boolean batalla se distingue la asignación de un país en las fases iniciales del Risk de la ocupación
        // por victoria militar
        // Se necesita dosEjercitos por si se debe actualizar la información del jugador si tiene la misión M2

        this.jugador = jugador;             // El país cambia de manos
        jugador.asignarPais(this);          // Esta información se registra también en el objeto del jugador

        if (batalla) {
            // Información necesaria para comprobar las condiciones de victoria (se registra si el jugador tiene una de
            // las misiones que verican estos datos)
            if (jugador.getMision().equals("M1"))
                jugador.setNumPaisesConquistados(jugador.getNumPaisesConquistados() + 1);
            if (dosEjercitos && jugador.getMision().equals("M2"))
                jugador.setNumPaisesConquistados2Ejs(jugador.getNumPaisesConquistados2Ejs() + 1);
        }

        switch (jugador.getColor()){
            case "AMARILLO":
                ejercito = new EjercitoAmarillo();
                break;
            case "AZUL":
                ejercito = new EjercitoAzul();
                break;
            case "CYAN":
                ejercito = new EjercitoCyan();
                break;
            case "ROJO":
                ejercito = new EjercitoRojo();
                break;
            case "VERDE":
                ejercito = new EjercitoVerde();
                break;
            case "VIOLETA":
                ejercito = new EjercitoVioleta();
        }

        // Se suma 1 al atributo que controla el número de veces que se ha ocupado el país
        numVecesOcupado++;

        // Se debe comprobar si el continente del país ha sido ocupado totalmente por el mismo jugador
        continente.actualizarOcupacion();
    }

    public boolean estaOcupadoPor(Jugador jugador){
        return this.jugador.equals(jugador);
    }

    public void addFronteraPais(Pais pais){
        fronterasPais.add(pais);        // Se registra una nueva frontera
    }

    public boolean tieneFronteraCon(Pais pais){
        return fronterasPais.contains(pais);
    }

    // Modificación del número de tropas actuales en el país
    public void anhadirEjercitos(int numTropas){
        ejercito.anhadirTropas(numTropas);
    }
    public void quitarEjercitos(int numTropas){
        ejercito.quitarTropas(numTropas);
    }

}
