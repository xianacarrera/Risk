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

    private ArrayList<Pais> mapa;
    private ArrayList<Jugador> jugadores;
    private HashMap<String, Pais> paises;
    private HashMap<String,Continente> continentes;


    // TODO: preguntar por toString de AmeCentral

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




    public void crearMapa() {
        // Código necesario para crear el mapa

        paises = new HashMap<>();
        mapa = new ArrayList<>();

        String linea = null;
        BufferedReader bufferLector = null;

        // TODO: preguntar si hacer mapa una Clase con un HashMap y un ArrayList dentro

        try{
            File fichero = new File("Mapa.txt");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null){
                String[] infoPais = linea.split(" ");
                if (infoPais.length == 4) {
                    paises.put(infoPais[0], new Pais(infoPais[0], continentes.get(infoPais[1]),
                            Integer.parseInt(infoPais[2]), Integer.parseInt(infoPais[3])));
                    continentes.get(infoPais[1]).guardarPaises(paises.get(infoPais[0]));
                    mapa.add(paises.get(infoPais[0]));
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
        int i, j, k;        // Índices de recorrido
        Pais tempPais;      // Variable temporal para almacenar un país
        String tempEj;      // Variable temporal para almacenar un número de ejércitos

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
                tempPais = mapa.get(i * 11 + j);     // Almacena el país de la casilla i x j

                if (tempPais.getNombre().equals("Océano1")){            // Línea roja horizontal
                    System.out.print("\033[0;31m-----------\033[0m");
                } else if (tempPais.getNombre().equals("Océano2")) {    // Línea roja vertical
                    System.out.print("\033[0;31m     |     \033[0m");
                } else {
                    System.out.print(" " +
                            tempPais.getContinente().getColor() + tempPais.toString());
                    // Imprime un espacio y el nombre del país con su color como fondo
                    for (k = 0; k < (9 - tempPais.toString().length()); k++) {
                        System.out.print(" ");          // Imprime los espacios que falten para llegar a 9 con color
                    }
                    System.out.print("\033[0m ");       // Espacio final (sin color de fondo)
                }

                if ((i == 4 && j == 3) || (i == 5 && j == 3)){
                    System.out.print("\033[0;31m");      // Pone la barra roja
                }
                System.out.print("|");      // Barra entre casillas
            }
            System.out.print("\n|");
            for(j = 0; j < 11; j++){
                // valueOf convierte int a String
                tempPais = mapa.get(i * 11 + j);
                tempEj = String.valueOf(tempPais.getNumEjercitos());        // Número de ejércitos

                // TODO: preguntar si hace falta overrride equals()
                if (tempPais.getNombre().equals("Océano1")){
                    System.out.print("           ");
                } else if (tempPais.getNombre().equals("Océano2")){
                    System.out.print("\033[0;31m     |     ");
                } else {
                    // + tempEj.getJugador().getColor()
                    System.out.print(" " + tempEj);                     // Imprime el número de ejércitos
                    for (k = 0; k < (10 - tempEj.length()); k++) {
                        System.out.print(" ");                      // Espacios que falten
                    }
                }

                if (i == 4 && j == 3){
                    System.out.print("\033[0;31m|");      // Barra roja
                } else {
                    System.out.print("\033[0m|");      // Barra
                }

            }

            // Separación horizontal
            System.out.print("\n|");
            for(j = 0; j < 11; j++){
                System.out.print("===========");
                if (i == 4 && j == 3){
                    System.out.print("\033[0;31m");         // Hay una línea con un símbolo rojo
                }
                System.out.print("|\033[0m");
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