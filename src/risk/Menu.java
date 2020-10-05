package risk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

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

    public void verMapa(){
        int i, j, k;
        String temp;

        System.out.print("|");
        for(j = 0; j < 11; j++){
            System.out.print("===========|");
        }
        System.out.print("\n");
        for(i = 0; i < 2; i++){
            System.out.print("|");
            for(j = 0; j < 11; j++){
                temp = mapa.get(i * 11 + j).toString();
                System.out.print(" " +
                        mapa.get(i * 11 + j).getLugar().getColor() + temp);
                for(k = 0; k < (9 - temp.length()); k++){
                    System.out.print(" ");
                }
                System.out.print("\033[0m |");
            }
            System.out.print("\n|");
            for(j = 0; j < 11; j++){
                // valueOf convierte int a String
                temp = String.valueOf(mapa.get(i * 11 + j).getNumEjercitos());
                System.out.print(" " + temp);
                for(k = 0; k < (10 - temp.length()); k++){
                    System.out.print(" ");
                }
                System.out.print("|");

            }

            System.out.print("\n|");
            for(j = 0; j < 11; j++){
                System.out.print("===========|");
            }
        }
    }

    /**
     *
     * @param file
     */
    private void crearJugador(File file) {
        // Código necesario para crear a los jugadores del RISK

    }

    /**
     *
     * @param file
     */
    private void crearJugador(String nombre, String color) {
        // Código necesario para crear a un jugador a partir de su nombre y color

    }
}