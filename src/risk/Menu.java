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

    private HashMap<String, Jugador> jugadores;

    private Mapa mapa;


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

        jugadores = new HashMap<>();
    }


    public void crearMapa() {
        // Código necesario para crear el mapa
        mapa = new Mapa();

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
                    mapa.generarPais(infoPais[0], infoPais[1], Integer.parseInt(infoPais[2]),
                            Integer.parseInt(infoPais[3]));
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
        System.out.println(mapa);
    }


    public void crearJugador(String nombre, String color) {
        // Código necesario para crear a un jugador a partir de su nombre y color
        Jugador jugador = new Jugador(nombre, color);
        jugadores.put(nombre, jugador);

        System.out.println("{");
        System.out.println("  nombre: \"" + nombre + "\",");
        System.out.println("  color: \"" + color + "\"");
        System.out.println("}");
    }

    /**
     *
     * @param file
     */
    public void crearJugador(String file) {
        // Código necesario para crear a los jugadores del RISK

        String linea = null;
        BufferedReader bufferLector = null;

        try{
            File fichero = new File(file);
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null){
                String[] infoJug = linea.split(";");
                if (infoJug.length == 2) {
                    crearJugador(infoJug[0], infoJug[1]);
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
     * @param nombrePais
     * @param nombreJugador
     */
    public void asignarPaises(String nombrePais, String nombreJugador){
        // Código necesario para asignar un país a un jugador

        Jugador jugador = jugadores.get(nombreJugador);
        Pais pais = mapa.getPais(nombrePais);

        jugador.asignarPais(pais);

        // RESULTADO OK
        System.out.println("{");
        System.out.println("  nombre: \"" + jugador.getNombre() + "\",");
        System.out.println("  pais: \"" + pais.getNombre() + "\",");
        System.out.println("  continente: \"" + pais.getContinente().getNombre() + "\",");
        //TODO: Frontera
        System.out.println("}");
    }

    /**
     *
     * @param file
     */
    public void asignarPaises(String file){
        // Código necesario para asignar países

        String linea = null;
        BufferedReader bufferLector = null;

        try{
            File fichero = new File(file);
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null){
                String[] info = linea.split(";");
                if (info.length == 2) {
                    // La llamada a asignar no necesita objeto porque Java implícitamente asume que el objeto es this
                    asignarPaises(info[1], info[0]);
                } else {
                    System.out.println("NO");
                }
            }

            bufferLector.close();

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void asignarMisiones(String nombreJugador, String idMision){
        Jugador jugador = jugadores.get(nombreJugador);
        jugador.setMision(idMision);

        if (idMision == "M41" || idMision == "M42" || idMision == "M43" || idMision == "M44" || idMision == "M45"
                || idMision == "M46"){
            ArrayList<String> colores = new ArrayList<>();

            for (Jugador jug : jugadores.values()){
                colores.add(jug.getColor());
            }

            switch(idMision){
                case "M41":
                    if (!colores.contains("AMARILLO") || jugador.getColor() == "AMARILLO"){
                        idMision = "M1";
                    }
                    break;
                case "M42":
                    if (!colores.contains("AZUL") || jugador.getColor() == "AZUL"){
                        idMision = "M1";
                    }
                    break;
                case "M43":
                    if (!colores.contains("CYAN") || jugador.getColor() == "CYAN"){
                        idMision = "M1";
                    }
                    break;
                case "M44":
                    if (!colores.contains("ROJO") || jugador.getColor() == "ROJO"){
                        idMision = "M1";
                    }
                    break;
                case "M45":
                    if (!colores.contains("VERDE") || jugador.getColor() == "VERDE"){
                        idMision = "M1";
                    }
                    break;
                case "M46":
                    if (!colores.contains("VIOLETA") || jugador.getColor() == "VIOLETA"){
                        idMision = "M1";
                    }
                    break;
            }
        }




        System.out.println("{");
        System.out.println("  nombre: \"" + nombreJugador + "\",");
        System.out.println("  mision: \"");
        explicarMision(idMision);
        System.out.print("\"");
        System.out.println("}");
    }

    public String explicarMision(String idMision){
        // Recibe el id de la misión e imprime en qué consiste
        String mision;

        switch (idMision){
            case "M1":
                mision = "Conquistar 24 países de la preferencia del jugador";
                break;
            case "M2":
                mision = "Conquistar 18 países de la preferencia del jugador con un mínimo de dos ejércitos";
                break;
            case "M31":
                mision = "Conquistar Asia y América del Sur";
                break;
            case "M32":
                mision = "Conquistar Asia y África";
                break;
            case "M33":
                mision = "Conquistar América del Norte y África";
                break;
            case "M34":
                mision = "Conquistar América del Norte y Oceanía";
                break;
            case "M41":
                mision = "Destruir el ejército AMARILLO";
                break;
            case "M42":
                mision = "Destruir el ejército AZUL";
                break;
            case "M43":
                mision = "Destruir el ejército CYAN";
                break;
            case "M44":
                mision = "Destruir el ejército ROJO";
                break;
            case "M45":
                mision = "Destruir el ejército VERDE";
                break;
            case "M46":
                mision = "Destruir el ejército VIOLETA";
                break;
            default:
                mision = "";
        }
        return mision;
    }

    public void asignarMisiones(String file){

        String linea = null;
        BufferedReader bufferLector = null;

        try{
            File fichero = new File(file);
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null){
                String[] infoMision = linea.split(";");
                if (infoMision.length == 2) {
                    asignarMisiones(infoMision[0], infoMision[1]);
                } else {
                    System.out.println("NO");
                }
            }

            bufferLector.close();

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void repartirEjercitos(Jugador jugador, String numero, String nombrePais){
        int numFinal = Integer.parseInt(numero);

        if (numFinal > jugador.getNumEjercitos()){
            numFinal = jugador.getNumEjercitos();
        }

        mapa.getPais(nombrePais).setNumEjercitos(numFinal);

        System.out.println("{");
        System.out.println("  pais: \"" + nombrePais + "\",");
        System.out.println("  jugador: \"" + jugador.getNombre() + "\",");
        System.out.println("  numeroEjercitosAsignados: " + numFinal + ",");
        System.out.println("  numeroEjercitosTotales: " + mapa.getPais(nombrePais).getNumEjercitos() + ",");

        System.out.println("  paisesOcupadosContinente: [");
        // ?????????????????????

    }

    public void resultadoError(int codigo){
        switch (codigo){
            case 99:
                System.out.println("Comando no permitido en este momento");
                break;
            case 100:
                System.out.println("Color no permitido");
                break;
            case 101:
                System.out.println("Comando incorrecto");
                break;
            case 102:
                System.out.println("El continente no existe");
                break;
            case 103:
                System.out.println("El jugador no existe");
                break;
            case 104:
                System.out.println("El jugador ya existe");
                break;
            case 105:
                System.out.println("Los jugadores no están creados");
                break;
            case 106:
                System.out.println("El mapa no está creado");
                break;
            case 107:
                System.out.println("El mapa ya ha sido creado");
                break;
            case 109:
                System.out.println("El país no existe");
                break;
            case 110:
                System.out.println("El país no pertenece al jugador");
                break;
            case 111:
                System.out.println("El país pertenece al jugador");
                break;
            case 112:
                System.out.println("Los países no son frontera");
                break;
            case 113:
                System.out.println("El país ya está asignado");
                break;
            case 114:
                System.out.println("El color ya está asignado");
                break;
            case 115:
                System.out.println("La misión ya está asignada");
                break;
            case 116:
                System.out.println("La misión no existe");
                break;
            case 117:
                System.out.println("El jugador ya tiene asignada una misión");
                break;
            case 118:
                System.out.println("Las misiones no están asignadas");
                break;
            case 119:
                System.out.println("Ejércitos no disponibles");
                break;
            case 120:
                System.out.println("No hay cartas suficientes");
                break;
            case 121:
                System.out.println("No hay configuración de cambio");
                break;
            case 122:
                System.out.println("Algunas cartas no pertenecen al jugador");
                break;
            case 123:
                System.out.println("Algunas cartas no existen");
                break;
            case 124:
                System.out.println("No hay ejércitos suficientes");
                break;
            case 125:
                System.out.println("El identificador no sigue el formato correcto");
                break;
            case 126:
                System.out.println("Carta de equipamiento ya asignada");
                break;
        }
    }
}