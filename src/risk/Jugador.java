package risk;

import java.util.ArrayList;
import java.util.HashMap;
import risk.cartas.Carta;

/*
 * Clase que representa un jugador. Contiene funciones y datos relacionados con su "inventario"
 */

public class Jugador {

    private final String nombre;
    private final String color;

    private String mision;
    private int numPaisesConquistados;
    private int numPaisesConquistados2Ejs;

    private int ejercitosDisponibles;
    private int ejercitosTotales;

    private final HashMap<String, Pais> paises;             // Países controlados (las claves son abreviaturas)
    private final HashMap<String, Carta> cartas;            // Cartas de equipamiento que posee el jugador
    private final HashMap<String, Continente> continentes;  // Continentes controlados (las claves son abreviaturas)

    private final ConsolaNormal consolaNormal;


    public Jugador(String nombre, String color) throws Exception {
        // Si se llama a este constructor, ya se ha comprobado que tanto el nombre como el color son válidos
        this.nombre = nombre;
        this.color = color;

        paises = new HashMap<>();
        continentes = new HashMap<>();
        consolaNormal = ConsolaNormal.getInstancia();
        cartas = new HashMap<>();
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

        // El otro objeto es un jugador, por lo que se puede castear:
        final Jugador otro = (Jugador) obj;

        // Dos jugadores son iguales si coinciden en nombre y color
        if (!this.nombre.equals(otro.getNombre())){
            return false;
        }

        return this.color.equals(otro.getColor());
    }

    @Override
    public String toString() {
        ArrayList<String> nomPais=new ArrayList<>();

        ejercitosTotales = 0;
        for (Pais pais : paises.values()){
            ejercitosTotales += pais.getNumEjercitos();
            nomPais.add(pais.getNombre());
        }

        return ("{" + consolaNormal.formatoSimple("nombre", nombre) + ","
                + consolaNormal.formatoSimple("color", color) + ","
                + consolaNormal.formatoSimple("mision", mision) + ","
                + consolaNormal.formatoSimple("numeroEjercitos", ejercitosTotales) + ","
                + consolaNormal.formatoConjuntoStrings("paises", nomPais) + ","
                + consolaNormal.formatoConjuntoStrings("continentes", continentes.keySet()) + ","
                + consolaNormal.formatoConjuntoStrings("cartas", cartas.keySet()) + ","
                + consolaNormal.formatoSimple("numeroEjercitosRearmar", calcularRearme())
                + "\n}\n");
    }


    public String toString(boolean noMision) {
        // Para diferenciar esta función del toString normal, sobrecargamos el método. En este caso, no se imprime
        // la misión
        ejercitosTotales = 0;
        ArrayList<String> nomPais=new ArrayList<>();
        for (Pais pais : paises.values()){
            ejercitosTotales += pais.getNumEjercitos();
            nomPais.add(pais.getNombre());
        }

        return ("{" + consolaNormal.formatoSimple("nombre", nombre) + ","
                + consolaNormal.formatoSimple("color", color) + ","
                + consolaNormal.formatoSimple("numeroEjercitos", ejercitosTotales) + ","
                + consolaNormal.formatoConjuntoStrings("paises", nomPais) + ","
                + consolaNormal.formatoConjuntoStrings("continentes", continentes.keySet()) + ","
                + consolaNormal.formatoConjuntoStrings("cartas", cartas.keySet()) + ","
                + consolaNormal.formatoSimple("numeroEjercitosRearmar", calcularRearme())
                + "\n}\n");
    }

    // Getters
    public String getNombre() {
        return nombre;
    }
    public String getColor() {
        return color;
    }
    public String getMision(){ return mision; }
    public int getEjercitosDisponibles(){
        return ejercitosDisponibles;
    }
    public HashMap<String, Continente> getContinentes(){
        return continentes;
    }
    public HashMap<String, Pais> getPaises(){
        return paises;
    }
    public int getNumPaisesConquistados() { return numPaisesConquistados; }
    public int getNumPaisesConquistados2Ejs() { return numPaisesConquistados2Ejs; }

    public HashMap<String, Carta> getCartas(){
        //Función para ver el mazo de cartas del jugador
        return cartas;
    }

    public Carta getCarta(String idCarta){
        return cartas.get(idCarta);
    }


    // Setters
    public void setMision(String mision){
        this.mision = mision;
    }
    public void setNumPaisesConquistados(int n) { numPaisesConquistados = n; }
    public void setNumPaisesConquistados2Ejs(int n) { numPaisesConquistados2Ejs = n; }


    // Otras funciones

    public void aumentarEjercito(int numTropas) {
        ejercitosDisponibles += numTropas;
    }

    public void reducirEjercito(int numTropas){
        ejercitosDisponibles -= numTropas;
    }

    public void asignarPais(Pais pais){
        if (!paises.containsValue(pais)) {
            // Aunque por la construcción del código nunca se intentará introducir un país repetido, no está
            // de más asegurarse de que la medida se cumple.
            paises.put(pais.getAbreviatura(), pais);
        }
    }

    //Función para eliminar un pais de la lista de un jugador
    public void perderPais(Pais pais){
        paises.remove(pais.getAbreviatura());
        // Si el país no está presente en paises, el HashMap no cambia
    }

    //Función para anhadir una nueva carta al mazo del jugador
    public void nuevaCarta(Carta carta){
        cartas.put(carta.getIdCarta(), carta);
    }

    //Función que quitar del mazo del jugador una carta
    public void quitarCarta(Carta carta){
        cartas.remove(carta.getIdCarta(), carta);
    }

    public void controla(Continente continente){
        if (!continentes.containsValue(continente)){
            // Aunque por la construcción del código nunca se intentará introducir un continente repetido, no está
            // de más asegurarse de que la medida se cumple.
            continentes.put(continente.getAbreviatura(), continente);
        }
    }

    public void noControla(Continente continente) {
        continentes.remove(continente.getAbreviatura());
        // Si el continente no está presente en continentes, el HashMap no cambia
    }

    public int calcularRearme(){
        int numEjer = Math.max(paises.size() / 3, 3);      // División entera
        // El mínimo de ejércitos recibido por los países es 3

        // Si hay un bonus por el continente, se añade
        for (Continente cont : continentes.values()){
            numEjer += cont.getRearme();
        }

        return numEjer;
    }

}
