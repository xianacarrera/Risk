package risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/* Dudas:
    - En la clase jugador, convendría dividir cambiarEjercito en 3 métodos distintos (añadirEjercito, quitarEjercito,
        setEjercito)?
    - Class Color?

 */


public class Risk {

    private int numJug;
    private Dados dados;
    private Jugador jugador1, jugador2, jugador3, jugador4, jugador5, jugador6;
    private ArrayList<Casilla> mapa;

    // TODO: Convendría guardar los jugadores en una lista - preguntar si se puede usar ArrayList
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();


    public static void main(String[] args) {
        // Para poder lanzar los métodos hace falta una instancia de la clase Main, en este caso, risk
        Menu menu = new Menu();

        menu.crearMapa();
        menu.verMapa();
    }

    public void setNumJug(int numJug) {
        this.numJug = numJug;
    }

    public int getNumJug() {
        return this.numJug;
    }

    /*
    public void crearJugadores(){
        // Simplificar switch? Cambiar por ifs?
        // Ya sé que este switch es una chapuza. Que conste que sin usar ArrayList era bastante elegante -_-
        switch(numJug){
            case 3:
                jugador1 = new Jugador(nombre1, color1);
                jugadores.add(jugador1);
                jugador2 = new Jugador(nombre2, color2);
                jugadores.add(jugador2);
                jugador3 = new Jugador(nombre3, color3);
                jugadores.add(jugador3);
                break;
            case 4:
                jugador1 = new Jugador(nombre1, color1);
                jugadores.add(jugador1);
                jugador2 = new Jugador(nombre2, color2);
                jugadores.add(jugador2);
                jugador3 = new Jugador(nombre3, color3);
                jugadores.add(jugador3);
                jugador4 = new Jugador(nombre4, color4);
                jugadores.add(jugador4);
                break;
            case 5:
                jugador1 = new Jugador(nombre1, color1);
                jugadores.add(jugador1);
                jugador2 = new Jugador(nombre2, color2);
                jugadores.add(jugador2);
                jugador3 = new Jugador(nombre3, color3);
                jugadores.add(jugador3);
                jugador4 = new Jugador(nombre4, color4);
                jugadores.add(jugador4);
                jugador5 = new Jugador(nombre5, color5);
                jugadores.add(jugador5);
                break;
            case 6:
                jugador1 = new Jugador(nombre1, color1);
                jugadores.add(jugador1);
                jugador2 = new Jugador(nombre2, color2);
                jugadores.add(jugador2);
                jugador3 = new Jugador(nombre3, color3);
                jugadores.add(jugador3);
                jugador4 = new Jugador(nombre4, color4);
                jugadores.add(jugador4);
                jugador5 = new Jugador(nombre5, color5);
                jugadores.add(jugador5);
                jugador6 = new Jugador(nombre6, color6);
                jugadores.add(jugador6);
                break;
            default:
                System.out.println("Número de jugadores incorrecto");
                break;
        }
    }
     */

    public void crearDados() {
        dados = new Dados();
    }


    /**
     *
     */
    public void crearMapa() {
        // Código necesario para crear el mapa
        int i, j;
        mapa = new ArrayList<>();

        Continente asia = new Continente("Asia");
        Continente africa = new Continente("África");
        Continente europa = new Continente("Europa");
        Continente americaDelNorte = new Continente("América del Norte");
        Continente americaDelSur = new Continente("América del Sur");
        Continente australia = new Continente("Australia");
        Continente oceanos = new Continente("Océanos");

        mapa.add(new Casilla(americaDelNorte.getPais(0), 1, 1,
                americaDelNorte));
        mapa.add(new Casilla(americaDelNorte.getPais(5), 1, 2,
                americaDelNorte));
        mapa.add(new Casilla(americaDelNorte.getPais(3), 1, 3,
                americaDelNorte));
        mapa.add(new Casilla(oceanos.getPais(0), 1, 4, oceanos));
        mapa.add(new Casilla(europa.getPais(5), 1, 5, europa));
        mapa.add(new Casilla(europa.getPais(0), 1, 6, europa));
        mapa.add(new Casilla(asia.getPais(9), 1, 7, asia));
        mapa.add(new Casilla(asia.getPais(11), 1, 8, asia));
        mapa.add(new Casilla(asia.getPais(5), 1, 9, asia));
        // Nota: distinguir los océanos con conexiones verticales, horizontales... ?
        mapa.add(new Casilla(oceanos.getPais(0), 1, 10, oceanos));
        mapa.add(new Casilla(oceanos.getPais(0), 1, 11, oceanos));

        mapa.add(new Casilla(americaDelNorte.getPais(1), 2, 1,
                americaDelNorte));
        mapa.add(new Casilla(americaDelNorte.getPais(4), 2, 2,
                americaDelNorte));
        mapa.add(new Casilla(americaDelNorte.getPais(5), 2, 3,
                americaDelNorte));
        mapa.add(new Casilla(oceanos.getPais(0), 2, 4, oceanos));
        mapa.add(new Casilla(oceanos.getPais(0), 2, 5, oceanos));
        mapa.add(new Casilla(europa.getPais(4), 2, 6, europa));

    }

    public void verMapa() {
        int i, j, k;
        String temp;

        System.out.print("|");
        for (j = 0; j < 11; j++) {
            System.out.print("===========|");
        }
        System.out.print("\n");
        for (i = 0; i < 2; i++) {
            System.out.print("|");
            for (j = 0; j < 11; j++) {
                System.out.print("===========|");
            }
            System.out.print("\n|");
            for (j = 0; j < 11; j++) {
                System.out.print(" " +
                        mapa.get(i * 11 + j).getLugar().getColor() +
                        mapa.get(i * 11 + j).toString());
                System.out.print(" |");
            }
            System.out.print("\n|");
            for (j = 0; j < 11; j++) {
                // valueOf convierte int a String
                temp = String.valueOf(mapa.get(i * 11 + j).getNumEjercitos());
                System.out.print(" " + temp);
                for (k = 0; k < (10 - temp.length()); k++) {
                    System.out.print(" ");
                }
                System.out.print("|");

            }
        }
    }


    public void repartirEjercitos() {
        // Número de ejércitos a añadir en función del número de jugadores:
        // 6 -> 20 = 5 * 4 = 5 * (10 - 6)
        // 5 -> 25 = 5 * 5 = 5 * (10 - 5)
        // 4 -> 30 = 5 * 6 = 5 * (10 - 4)
        // 3 -> 35 = 5 * 7 = 5 * (10 - 3)

        if (jugadores.size() >= 3 && jugadores.size() <= 6) {
            for (Jugador jugador : jugadores) {
                jugador.cambiarEjercito(5 * (10 - jugadores.size()));
            }
        }
    }

    public void asignarCartasMision() {

    }

    public void asignarPaises() {

    }

    public void repartirCartasEjercito() {

    }

    public void jugarPartida() {

    }
}

// Esta clase representa los dados del juego
class Dados {

    // Devuelve el resultado total de una tirada de dados, cuyo número viene dado como parámetro
    public int tirarDados(int numDados) {
        int sum = 0;
        Random rand = new Random();

        if (numDados > 0 && numDados < 4) {
            for (int i = 0; i < numDados; i++) {
                sum += rand.nextInt(6) + 1;
                   /* nextInt genera un entero aleatorio, en este caso, entre 0 (incluido) y 6 (no incluido). Para
                    que el rango pase a estar entre 1 y 6, le sumo 1 al resultado: pasa a un rango del 1 (incluido)
                    al 7 (no incluido) - 1,2,3,4,5,6
                    */
            }
            return sum;
        } else {
            // Número de dados incorrecto
            return -1;
        }
    }
}

class Continente {
    //Atributos
    private String nombre;
    private int numeroEjercitos;
    private String color;
    private ArrayList<Pais> paises;

    Continente(String nombre) {
        setNombre(nombre);
        asignarColorContinente();
        crearPaises();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void crearPaises() {
        paises = new ArrayList<>();
        switch (this.nombre) {
            case "Asia":
                paises.add(new Pais("Afganistan"));
                paises.add(new Pais("China"));
                paises.add(new Pais("India"));
                paises.add(new Pais("Irkutsk"));
                paises.add(new Pais("Japón"));
                paises.add(new Pais("Kamchatka"));
                paises.add(new Pais("Mongolia"));
                paises.add(new Pais("Oriente Medio"));
                paises.add(new Pais("Siberia"));
                paises.add(new Pais("Sudeste Asiático"));
                paises.add(new Pais("Urales"));
                paises.add(new Pais("Yakutsk"));
                break;
            case "África":
                paises.add(new Pais("África del Norte"));
                paises.add(new Pais("África Oriental"));
                paises.add(new Pais("Congo"));
                paises.add(new Pais("Egipto"));
                paises.add(new Pais("Madagascar"));
                paises.add(new Pais("Sudáfrica"));
            case "Europa":
                paises.add(new Pais("Escandinavia"));
                paises.add(new Pais("Europa del Norte"));
                paises.add(new Pais("Europa Occidental"));
                paises.add(new Pais("Europa del Sur"));
                paises.add(new Pais("Gran Bretaña"));
                paises.add(new Pais("Islandia"));
                paises.add(new Pais("Rusia"));
                break;
            case "América del Norte":
                paises.add(new Pais("Alaska"));
                paises.add(new Pais("Alberta"));
                paises.add(new Pais("América Central"));
                paises.add(new Pais("Groenlandia"));
                paises.add(new Pais("Ontario"));
                paises.add(new Pais("Quebec"));
                paises.add(new Pais("Territorios del Noroeste"));
                paises.add(new Pais("Estados Unidos del Este"));
                paises.add(new Pais("Estados Unidos del Oeste"));
                break;
            case "América del Sur":
                paises.add(new Pais("Venezuela"));
                paises.add(new Pais("Perú"));
                paises.add(new Pais("Brasil"));
                paises.add(new Pais("Argentina"));
                break;
            case "Australia":
                paises.add(new Pais("Indonesia"));
                paises.add(new Pais("Nueva Guinea"));
                paises.add(new Pais("Australia Occidental"));
                paises.add(new Pais("Australia Oriental"));
                break;
            case "Oceanos":
                break;

        }
    }

    public ArrayList<Pais> getPaises() {
        return paises;
    }

    public Pais getPais(int i) {
        return this.paises.get(i);
    }


    public void setNombre(String nombreContinente) {
        if (nombre.equals("África")
                || nombre.equals("Europa")
                || nombre.equals("América del Norte")
                || nombre.equals("América del Sur")
                || nombre.equals("Australia")
                || nombre.equals("Asia")
                || nombre.equals("Oceanos")) {
            this.nombre = nombreContinente;
        } else {
            System.out.println("No es un continente");
        }
    }


    public String getColor() {
        return this.color;
    }

    public void asignarColorContinente() {
        switch (nombre) {
            case "Asia":
                this.color = "\033[4;36m";        // CYAN
                break;
            case "África":
                this.color = "\033[4;32m";        // VERDE
                break;
            case "Europa":
                this.color = "\033[4;33m";        // AMARILLO
                break;
            case "América del Norte":
                this.color = "\033[4;35m";        // VIOLETA
                break;
            case "América del Sur":
                this.color = "\033[4;31m";        // ROJO
                break;
            case "Australia":
                this.color = "\033[4;34m";        // AZUL
                break;
            case "Océanos":
                this.color = "\033[4;37m";       // BLANCO
                break;
            //No hay default porque esto ya se comprueba en setNombre

        }
    }


}

class Pais {

    private String nombre;
    private String abreviatura;
    private boolean esFrontera;

    public Pais(String nombre) {
        setNombre(nombre);
        setAbreviatura();
    }

    public void setNombre(String nombre) {
        if (nombre.equals("Afganistan")
                || nombre.equals("China")
                || nombre.equals("India")
                || nombre.equals("Irkutsk")
                || nombre.equals("Japón")
                || nombre.equals("Kamchatka")
                || nombre.equals("Mongolia")
                || nombre.equals("Oriente Medio")
                || nombre.equals("Siberia")
                || nombre.equals("Sudeste Asiático")
                || nombre.equals("Urales")
                || nombre.equals("Yakutsk")
                || nombre.equals("África del Norte")
                || nombre.equals("África Oriental")
                || nombre.equals("Congo")
                || nombre.equals("Egipto")
                || nombre.equals("Madagascar")
                || nombre.equals("Sudáfrica")
                || nombre.equals("Escandinavia")
                || nombre.equals("Europa del Norte")
                || nombre.equals("Europa Occidental")
                || nombre.equals("Europa del Sur")
                || nombre.equals("Gran Bretaña")
                || nombre.equals("Islandia")
                || nombre.equals("Rusia")
                || nombre.equals("Alaska")
                || nombre.equals("Alberta")
                || nombre.equals("América Central")
                || nombre.equals("Groenlandia")
                || nombre.equals("Ontario")
                || nombre.equals("Quebec")
                || nombre.equals("Territorios del Noroeste")
                || nombre.equals("Estados Unidos del Este")
                || nombre.equals("Estados Unidos del Oeste")
                || nombre.equals("Venezuela")
                || nombre.equals("Perú")
                || nombre.equals("Brasil")
                || nombre.equals("Argentina")
                || nombre.equals("Indonesia")
                || nombre.equals("Nueva Guinea")
                || nombre.equals("Australia Occidental")
                || nombre.equals("Australia Oriental")) {
            this.nombre = nombre;
        } else {
            System.out.println("No es un país");
        }

    }

    public String getNombre() {
        return nombre;
    }

    public void setAbreviatura() {
        switch (this.nombre) {
            case "Afganistan":
                this.abreviatura = "Afgan";
                break;

            case "China":
                this.abreviatura = "China";
                break;
            case "India":
                this.abreviatura = "India";
                break;
            case "Irkutsk":
                this.abreviatura = "Irkutsk";
                break;
            case "Japón":
                this.abreviatura = "Japón";
                break;
            case "Kamchatka":
                this.abreviatura = "Kamchatka";
                break;
            case "Mongolia":
                this.abreviatura = "Mongolia";
                break;
            case "Oriente Medio":
                this.abreviatura = "OMedio";
                break;
            case "Siberia":
                this.abreviatura = "Siberia";
                break;
            case "Sudeste Asiático":
                this.abreviatura = "SAsiático";
                break;
            case "Urales":
                this.abreviatura = "Urales";
                break;
            case "Yakutsk":
                this.abreviatura = "Yakutsk";
                break;
            case "África del Norte":
                this.abreviatura = "ANorte";
                break;
            case "África Oriental":
                this.abreviatura = "AOriental";
                break;
            case "Congo":
                this.abreviatura = "Congo";
                break;
            case "Egipto":
                this.abreviatura = "Egipto";
                break;
            case "Madagascar":
                this.abreviatura = "Madagasca";
                break;
            case "Sudáfrica":
                this.abreviatura = "Sudáfrica";
                break;
            case "Escandinavia":
                this.abreviatura = "Escandina";
                break;
            case "Europa del Norte":
                this.abreviatura = "EurNorte";
                break;
            case "Europa Occidental":
                this.abreviatura = "EurOcc";
                break;
            case "Europa del Sur":
                this.abreviatura = "EurSur";
                break;
            case "Gran Bretaña":
                this.abreviatura = "GBretaña";
                break;
            case "Islandia":
                this.abreviatura = "Islandia";
                break;
            case "Rusia":
                this.abreviatura = "Rusia";
                break;
            case "Alaska":
                this.abreviatura = "Alaska";
                break;
            case "Alberta":
                this.abreviatura = "Alberta";
                break;
            case "América Central":
                this.abreviatura = "AmeCentral";
                break;
            case "Groenlandia":
                this.abreviatura = "Groenlan";
                break;
            case "Ontario":
                this.abreviatura = "Ontario";
                break;
            case "Quebec":
                this.abreviatura = "Quebec";
                break;
            case "Territorios del Noroeste":
                this.abreviatura = "TNoroeste";
                break;
            case "Estados Unidos del Este":
                this.abreviatura = "USAEste";
                break;
            case "Estados Unidos del Oeste":
                this.abreviatura = "USAOeste";
                break;
            case "Venezuela":
                this.abreviatura = "Venezuela";
                break;
            case "Perú":
                this.abreviatura = "Perú";
                break;
            case "Brasil":
                this.abreviatura = "Brasil";
                break;
            case "Argentina":
                this.abreviatura = "Argentina";
                break;
            case "Indonesia":
                this.abreviatura = "Indonesia";
                break;
            case "Nueva Guinea":
                this.abreviatura = "NGuinea";
                break;
            case "Australia Occidental":
                this.abreviatura = "AusOccid";
                break;
            case "Australia Oriental":
                this.abreviatura = "AusOrient";
                break;
        }

    }


    public String getAbreviatura() {
        return abreviatura;
    }

    /*
    public boolean esPaisFrontera(){
        //Conexiones directas

    }
     */


    @Override
    public String toString() {
        return this.abreviatura;
    }

}


class Casilla {

    private Pais pais;
    private int fila;
    private int col;
    private Continente lugar;       // Si la casilla es de mar, sería un Continente especial que represente el océano
    // private Jugador ocupante;       // Jugador que está ocupando la casilla actualmente
    private int numEjercitos;       // El número de ejércitos que ocupan actualmente la casilla

    Casilla(Pais pais, int fila, int col, Continente lugar) {
        // Es necesario pasar los atributos fila y col teniendo el nombre del pais? O sería mejor un switch aquí
        this.pais = pais;
        this.fila = fila;
        this.col = col;
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        return this.pais.toString();
    }

    public int getFila() {
        return this.fila;
    }

    public int getCol() {
        return this.col;
    }

    public Continente getLugar() {
        return this.lugar;
    }

    public int getNumEjercitos() {
        return this.numEjercitos;
    }

    /*
    public void ocuparCasilla(Jugador jugador){
        this.ocupante = jugador;
    }
    */

}


class Jugador {

    private String nombre;
    private Color color;
    private int numEjercitos;   // No estoy segura de si esto haría falta, lo dejo de momento
    private ArrayList<Pais> paises;     // Países controlados por el jugador

    Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
        paises = new ArrayList<Pais>();
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre + ", color: " + color;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Color getColor() {
        return this.color;
    }

    public int getNumEjercitos() {
        return this.numEjercitos;
    }

    public void cambiarEjercito(int numTropas) {
        /* El nombre es un poco ambiguo (de primeras añadirEjercito o algo así quedaría mejor), pero esta función
            se puede reutilizar para RESTAR tropas. Si un jugador pierde tropas, se le puede pasar un número negativo
            y ya. Haría falta poner un if para que el número de tropas no pueda ser negativo (si llega a 0, creo
            que el jugador pierde y punto).
        */

        numEjercitos += numTropas;
    }


}

class Ejercito {

    private Jugador propietario;
    private Casilla pais;

}

class Carta {


}

/*
class CartaMision implements Carta{

}
*/