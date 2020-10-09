package risk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/* Hacer cabecera */

/**
 *
 * @author Manuel Lama
 */
public class Menu {
    // En esta clase se deberían de definir los atributos a los que será
    // necesario acceder durante la ejecución del programa como, por ejemplo,
    // el mapa o los jugadores

    private ArrayList<Casilla> mapa;
    private ArrayList<Jugador> jugadores;
    private HashMap<String, Pais> paises;
    private HashMap<String,Continente> continentes;



    /**
     *
     */
    public Menu() {
        // Inicialización de algunos atributos

        /*
        // Iniciar juego
        String orden= null;
        BufferedReader bufferLector= null;
        try {
            File fichero=  new File("comandos.csv");
            FileReader lector= new FileReader(fichero);
            bufferLector= new BufferedReader(lector);
            while((orden= bufferLector.readLine())!=null) {
                System.out.println("$> " + orden);
                String[] partes=orden.split(" ");
                String comando= partes[0];
                // COMANDOS INICIALES PARA EMPEZAR A JUGAR
                //    crear mapa
                //    crear jugadores <nombre_fichero>
                //    crear <nombre_jugador> <nombre_color>
                //    asignar misiones
                //    asignar paises <nombre_fichero>
                //    asignar <nombre_pais> <nombre_jugador>

                // COMANDOS DISPONIBLES DURANTE EL JUEGO
                //    acabar
                //    atacar <nombre_pais> <nombre_pais>
                //    describir continente <nombre_continente>
                //    describir frontera <nombre_pais>
                //    describir frontera <nombre_continente>
                //    describir jugador <nombre_jugador>
                //    describir pais <nombre_pais>
                //    jugador
                //    repartir ejercitos
                //    ver mapa
                //    ver pais <nombre_pais>
                switch(comando) {
                    case "crear":
                        if(partes.length==2) {
                            if(partes[1].equals("mapa")) {
                                // crearMapa es un método de la clase Menú desde el que se puede invocar
                                // a otros métodos de las clases que contienen los atributos y los métodos
                                // necesarios para realizar esa invocación
                                crearMapa();
                            } else {
                                System.out.println("\nComando incorrecto.");
                            }
                        } if(partes.length==3) {
                            if(partes[1].equals("jugadores")) {
                                crearJugador(new File(partes[2]));
                            } else {
                                crearJugador(partes[1], partes[2]);
                            }
                        } else {
                            System.out.println("\nComando incorrecto.");
                        }
                        break;
                    case "asignar":
                        if(partes.length!=3) {
                            System.out.println("\nComando incorrecto.");
                        } else if(partes[1].equals("paises")) {
                            // asignarPaises es un método de la clase Menu que recibe como entrada el fichero
                            // en el que se encuentra la asignación de países a jugadores. Dentro de este
                            // método se invocará a otros métodos de las clases que contienen los atributos
                            // y los métodos necesarios para realizar esa invocación
                            asignarPaises(new File(partes[2]));
                        } else {
                            asignarPaises(partes[1], partes[2]);
                        }
                        break;
                    default:
                        System.out.println("\nComando incorrecto.");
                }
            }
        } catch(Exception excepcion) {
            excepcion.printStackTrace();
        }
        */
    }

    /**
     *
     * @param file
     */
    public void asignarPaises(File file) {
        // Código necesario para asignar países
    }

    /**
     *
     * @param nombrePais
     * @param nombreJugador
     */
    public void asignarPaises(String nombrePais, String nombreJugador) {
        // Código necesario para asignar un país a un jugador
    }

    /**
     *
     */
    public void inicializarContinentes(){
        continentes = new HashMap<String, Continente>();

        continentes.put("asia", new Continente("Asia"));
        continentes.put("africa", new Continente("África"));
        continentes.put("europa", new Continente("Europa"));
        continentes.put("americaDelNorte", new Continente("América del Norte"));
        continentes.put("americaDelSur", new Continente("América del Sur"));
        continentes.put("australia", new Continente("Australia"));
        continentes.put("océanos", new Continente("Océanos"));
    }
    public void inicializarPaises(){
        paises = new HashMap<String, Pais>();

        paises.put("alaska", continentes.get("americaDelNorte").getPaisAlf(0));
        paises.put("tNoroeste" ,continentes.get("americaDelNorte").getPaisAlf(5));
        paises.put("groenlan",continentes.get("americaDelNorte").getPaisAlf(3));
        paises.put("oceano1", continentes.get("océanos").getPaisAlf(1));
        paises.put("islandia", continentes.get("europa").getPaisAlf(5));
        paises.put("escandina", continentes.get("europa").getPaisAlf(0));
        paises.put("siberia",continentes.get("asia").getPaisAlf(9));
        paises.put("yakustsk", continentes.get("asia").getPaisAlf(11));
        paises.put("kamchatka", continentes.get("asia").getPaisAlf(5));

        paises.put("alberta", continentes.get("americaDelNorte").getPaisAlf(1));
        paises.put("ontario", continentes.get("americaDelNorte").getPaisAlf(4));
        paises.put("quebec", continentes.get("americaDelNorte").getPaisAlf(5));
        paises.put("oceano", continentes.get("océanos").getPaisAlf(0));
        paises.put("gBretaña", continentes.get("europa").getPaisAlf(4));
        paises.put("eurNorte", continentes.get("europa").getPaisAlf(1));
        paises.put("rusia", continentes.get("europa").getPaisAlf(6));
        paises.put("irkutsk", continentes.get("asia").getPaisAlf(3));

        paises.put("usaOeste", continentes.get("americaDelNorte").getPaisAlf(8));
        paises.put("usaEste", continentes.get("americaDelNorte").getPaisAlf(7));
        paises.put("eurOcc", continentes.get("europa").getPaisAlf(3));
        paises.put("eurSur", continentes.get("europa").getPaisAlf(2));
        paises.put("urales", continentes.get("asia").getPaisAlf(10));
        paises.put("mongolia", continentes.get("asia").getPaisAlf(6));
        paises.put("japon", continentes.get("asia").getPaisAlf(4));

        paises.put("ameCentra", continentes.get("americaDelNorte").getPaisAlf(2));
        paises.put("oceano2", continentes.get("océanos").getPaisAlf(2));
        paises.put("afgan", continentes.get("asia").getPaisAlf(0));
        paises.put("china", continentes.get("asia").getPaisAlf(1));

        paises.put("venezuela", continentes.get("americaDelSur").getPaisAlf(3));
        paises.put("aNorte", continentes.get("africa").getPaisAlf(0));
        paises.put("egipto", continentes.get("africa").getPaisAlf(3));
        paises.put("oMedio", continentes.get("asia").getPaisAlf(7));
        paises.put("india", continentes.get("asia").getPaisAlf(2));
        paises.put("sAsiático", continentes.get("asia").getPaisAlf(8));

        paises.put("perú", continentes.get("americaDelSur").getPaisAlf(2));
        paises.put("brasil", continentes.get("americaDelSur").getPaisAlf(1));
        paises.put("congo", continentes.get("africa").getPaisAlf(2));
        paises.put("aOriental", continentes.get("africa").getPaisAlf(1));

        paises.put("argentina", continentes.get("americaDelSur").getPaisAlf(0));
        paises.put("sudáfrica", continentes.get("africa").getPaisAlf(5));
        paises.put("madagasca", continentes.get("africa").getPaisAlf(4));
        paises.put("indonesia", continentes.get("australia").getPaisAlf(2));
        paises.put("nGuinea",continentes.get("australia").getPaisAlf(3));

        paises.put("ausOccid", continentes.get("australia").getPaisAlf(0));
        paises.put("ausOrient ",continentes.get("australia").getPaisAlf(1));
    }


    public void crearMapa() {
        // Código necesario para crear el mapa
        mapa = new ArrayList<>();

        String linea = null;
        BufferedReader bufferLector = null;

        try{
            File fichero = new File("Paises.txt");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null){
                String[] infoPais = linea.split(" ");
                if (infoPais.length == 4) {
                    mapa.add(new Casilla(paises.get(infoPais[0]), continentes.get(infoPais[1]), Integer.parseInt(infoPais[2]),
                            Integer.parseInt(infoPais[3])));
                } else {
                    System.out.println("NO");
                }
            }

            bufferLector.close();

        } catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void verMapa(){
        int i, j, k;
        Casilla tempCas;
        String tempEj;

        // Línea inicial |-----|----|----| ...
        System.out.print("|");
        for(j = 0; j < 11; j++){
            System.out.print("===========|");
        }
        System.out.print("\n");

        // 8x11 casillas
        for(i = 0; i < 8; i++){
            System.out.print("|");   // Primer carácter
            for(j = 0; j < 11; j++){
                tempCas = mapa.get(i * 11 + j);     // Almacena la String de la casilla

                if (tempCas.getPais().equals(paises.get("oceano 1"))){
                    System.out.print("\033[0;31m -----------");
                } else if (tempCas.getPais().equals(paises.get("oceano2"))) {
                    System.out.print("\033[0;31m      |     ");
                } else if (tempCas.getPais().equals(paises.get("oceano"))) {
                    System.out.print("           ");
                } else {
                    System.out.print(" " +
                            tempCas.getLugar().getColor() + tempCas.toString());
                    // Imprime un espacio y el nombre del país con su color (dado por temp.getLugar().getColor() )
                    for (k = 0; k < (9 - tempCas.toString().length()); k++) {
                        System.out.print(" ");          // Imprime los espacios que falten para llegar a 11 entre |
                    }
                }

                if ((i == 4 && j == 3) || (i == 5 && j == 3)){
                    System.out.print("\033[0;31m  |");      // Barra roja
                } else {
                    System.out.print("\033[0m |");      // Resetea el color + barra entre casillas
                }
            }
            System.out.print("\n|");
            for(j = 0; j < 11; j++){
                // valueOf convierte int a String
                tempCas = mapa.get(i * 11 + j);
                tempEj = String.valueOf(tempCas.getNumEjercitos());        // número de ejércitos

                // TODO: preguntar si hace falta overrride equals()
                if (tempCas.getPais().equals(paises.get("oceano1"))){
                    System.out.print("           ");
                } else if (tempCas.getPais().equals(paises.get("oceano2"))){
                    System.out.print("\033[0;31m     |     ");
                } else {
                    System.out.print(" " + tempEj);                     // Imprime el número de ejércitos
                    for (k = 0; k < (10 - tempEj.length()); k++) {
                        System.out.print(" ");                      // Espacios que falten
                    }
                }

                if ((i == 4 && j == 3) || (i == 5 && j == 3)){
                    System.out.print("\033[0;31m  |");      // Barra roja
                } else {
                    System.out.print("\033[0m |");      // Resetea el color + barra entre casillas
                }

            }

            // Separación horizontal
            System.out.print("\n|");
            for(j = 0; j < 11; j++){
                System.out.print("===========|");
            }
            System.out.print("\n");
        }
    }
    private void crearJugador(File file) {
        // Código necesario para crear a los jugadores del RISK

        String linea = null;
        BufferedReader bufferLector = null;

        try{
            File fichero = new File("jugadores.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null){
                String[] infoJug = linea.split(" ");
                if (infoJug.length == 2) {
                    Jugador jugador = new Jugador(infoJug[0], infoJug[1]);
                    // Preguntar??
                    jugadores.add(jugador);
                } else {
                    System.out.println("NO");
                }
            }

            bufferLector.close();

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param file
     */
    private void crearJugador(String nombre, String color) {
        // Código necesario para crear a un jugador a partir de su nombre y color
        Jugador jugador = new Jugador(nombre, color);
        // Preguntar??
        jugadores.add(jugador);
    }
}