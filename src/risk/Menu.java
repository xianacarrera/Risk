package risk;

import java.io.*;

import risk.excepciones.*;

/**
 * Menu representa una antesala del verdadero programa.
 *
 * En él se realiza la lectura de los comandos introducidos por el usuario y las gestiones necesarias para poder
 * enviar la información que estas proporcionan de forma limpia a los diferentes métodos que después las ejecutan,
 * desde la clase Juego.
 *
 * Se describen a continuación las diferentes posibilidades para la inserción de comandos:
 *
 * COMANDOS INICIALES PARA EMPEZAR A JUGAR
 * crear mapa
 * crear <nombre_jugador> <nombre_color>
 * crear jugadores <nombre_fichero>
 * asignar <nombre_jugador> <identificador_misión>
 * asignar misiones <nombre_fichero>
 * asignar <nombre_jugador> <abreviatura_país>
 * asignar paises <nombre_fichero>
 * repartir ejercitos <número> <nombre_país>
 * repartir ejercitos
 *
 *
 * COMANDOS DISPONIBLES DURANTE EL JUEGO
 * obtener frontera <abreviatura_país>
 * obtener continente <abreviatura_país>
 * obtener color <abreviatura_país>
 * obtener países <abreviatura_continente>
 * cambiar cartas <id_carta1> <id_carta2> <id_carta3>
 * cambiar cartas todas
 * acabar turno
 * jugador
 * describir jugador <nombre_jugador>
 * describir país <abreviatura_país>
 * describir continente <abreviatura_continente>
 * ver mapa
 * atacar <abreviatura_pais1> <abreviatura_pais2>
 * atacar <abreviatura_pais1> <dadosAtaque> <abreviatura_pais2> <dadosDefensa>
 * rearmar <abreviatura_pais1> <número_ejércitos> <abreviatura_pais2>
 * asignar carta <id_carta>
 */


public class Menu {

    // Atributos correspondientes a otras clases del proyecto
    private ConsolaNormal consolaNormal;        // No puede ser final porque se inicializa dentro de un try-catch
    private Juego juego;                        // No puede ser final porque se inicializa dentro de un try-catch
    private final Turnos turnos;

    /********************************************************************************************************/

    public Menu() {

        // En primer lugar, se inicializan los atributos de la clase
        try {
            // El constructor de ConsolaNormal puede lanzar una excepción que hay que tratar
            // Como Juego inicializa ConsolaNormal en su constructor, puede lanzar el mismo error

            consolaNormal = ConsolaNormal.getInstancia();
            // getInstancia() es un método estático que devuelve un objeto de ConsolaNormal
            juego = new Juego();      // En Juego se desarrollan las instrucciones de los comandos
        } catch (Exception excepcion) {
            System.exit(1);     // No se ha podido crear la consola para leer e imprimir mensajes
        }

        turnos = new Turnos();   // Mantenimiento del orden correcto en la introducción de comandos

        // Lectura de los comandos introducidos por el usuario
        String orden;                        // Almacenará cada línea leída
        try {
            //File fichero = new File("comandos.txt");
            //FileReader lector = new FileReader(fichero);
            //BufferedReader bufferLector = new BufferedReader(lector);


            //while ((orden = bufferLector.readLine()) != null) {
                //gestionComandos(orden);
            //}

            //consolaNormal.escribirFinComandos();         // Se ha terminado de leer comandos

        } catch (Exception excepcion) {
            // No se ha encontrado un fichero o ha habido un error en la apertura del mismo. Introducción manual
            while (!((orden = consolaNormal.leer()).equals("fin"))) {
                gestionComandos(orden);
            }

            consolaNormal.escribirFinComandos();        // Se ha terminado de leer comandos
        }
    }

    public void gestionComandos(String orden) {
        try {
            // Se lee una línea y se guarda en orden. Si no es null, aún no se ha acabado de leer el fichero.

            consolaNormal.imprimir("$> " + orden);     // Se imprime por pantalla y se guarda
            String[] partes = orden.split(" ");
            String comando = partes[0];

            switch (comando) {
                case "crear":
                    switch (partes.length) {
                        case 2:
                            if (partes[1].equals("mapa")) {                   // crear mapa
                                if (turnos.mapaNoCreado()) {
                                    juego.crearMapa();                        // Se crea el mapa
                                    juego.verMapa();                          // Se imprime por pantalla
                                    turnos.cambiarFaseInicial(1);             // Siguiente fase
                                } else {
                                    // Mapa ya creado
                                    throw new ExcepcionGeo(consolaNormal.mensajeError(107));
                                }
                            } else {
                                // Comando incorrecto
                                throw new ExcepcionComando(consolaNormal.mensajeError(101));
                            }
                            break;

                        case 3:
                            switch (turnos.getFaseInicial()) {
                                case 0:
                                    // Mapa no creado
                                    throw new ExcepcionGeo(consolaNormal.mensajeError(106));
                                case 1:                                              // Mapa ya creado
                                case 2:                                              // Fase de jugadores
                                    if (partes[1].equals("jugadores")) {             // crear jugadores
                                        crearJugadores(new File(partes[2]));
                                    } else {
                                        juego.crearJugador(partes[1], partes[2]);    // crear jugador
                                    }
                                    break;
                                default:
                                    // Comando no permitido
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                            }
                            break;

                        default:
                            throw new ExcepcionComando(consolaNormal.mensajeError(101));
                            // Comando incorrecto
                    }
                    break;


                case "asignar":
                    switch (partes.length) {
                        case 3:
                            switch (partes[1]) {
                                case "paises":                                      // asignar paises
                                    switch (turnos.getFaseInicial()) {
                                        case 0:                 // Mapa no creado
                                        case 1:                 // Mapa ya creado
                                        case 2:                 // Jugadores ya creados
                                        case 3:                 // Fase de asignar misiones (sin finalizar)
                                            // Misiones no asignadas
                                            throw new ExcepcionMision(consolaNormal.mensajeError(118));
                                        case 4:                 // Fase de preparación para asignar países
                                            juego.asignarEjercitosIniciales();
                                            /*
                                             * Para poder asignar un país se necesita que el correspondiente
                                             * jugador tenga ya disponibles los ejércitos iniciales
                                             */
                                            turnos.cambiarFaseInicial(5);
                                        case 5:                 // Fase de asignar países
                                        case 6:                 // Aún no se ha empezado a repartir ejércitos
                                            asignarPaises(new File(partes[2]));
                                            break;
                                        default:
                                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                    }
                                    break;

                                case "misiones":                                    // asignar misiones
                                    switch (turnos.getFaseInicial()) {
                                        case 0:             // Mapa no creado
                                        case 1:             // Mapa ya creado
                                            // Jugadores no creados
                                            throw new ExcepcionJugador(consolaNormal.mensajeError(105));
                                        case 2:             // Creación de jugadores
                                            /*
                                             * Si la introducción de los comandos es correcta, en este momento
                                             * ya se han creado todos los jugadores. Se trata, por consiguiente,
                                             * del instante adecuado para comprobar que el número de jugadores
                                             * esté dentro de los límites definidos: [3, 6]. Si esto es cierto,
                                             * se pasará a la fase de asignar misiones. En caso contrario,
                                             * se cierra el programa (no hay un error definido).
                                             */
                                            juego.comprobarJugadores();
                                        case 3:             // Fase de asignar misiones
                                        case 4:             // Fase de transición entre asignar misiones y países
                                            asignarMisiones(new File(partes[2]));
                                            break;
                                        default:
                                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                    }
                                    break;

                                case "carta":                                       // asignar carta
                                    if (turnos.sePuedeAsignarCarta()) {
                                        juego.asignarCarta(partes[2]);
                                    } else {
                                        throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                    }
                                    break;

                                default:
                                    throw new ExcepcionComando(consolaNormal.mensajeError(101));
                            }
                            break;

                        case 4:
                            switch (partes[1]) {
                                case "mision":                              // asignar mision
                                    switch (turnos.getFaseInicial()) {
                                        case 0:                // Mapa no creado
                                        case 1:                // Mapa ya creado
                                            throw new ExcepcionJugador(consolaNormal.mensajeError(105));
                                        case 2:                // Fase de creación de jugadores
                                            /*
                                             * Si la introducción de los comandos es correcta, en este momento
                                             * ya se han creado todos los jugadores. Se trata, por consiguiente,
                                             * del instante adecuado para comprobar que el número de jugadores
                                             * esté dentro de los límites definidos: [3, 6]. Si esto es cierto,
                                             * se pasará a la fase de asignar misiones. En caso contrario,
                                             * se cierra el programa (no hay un error definido).
                                             */
                                            juego.comprobarJugadores();
                                        case 3:                 // Fase de asignar misiones
                                        case 4:                 // Fase de transición entre asignar misiones y países
                                            juego.asignarMision(partes[2], partes[3]);
                                            break;
                                        default:
                                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                    }
                                    break;

                                case "pais":                                         // asignar pais
                                    switch (turnos.getFaseInicial()) {
                                        case 0:            // Mapa no creado
                                        case 1:            // Mapa ya creado
                                        case 2:            // Creación de jugadores
                                        case 3:            // Asignación de misiones (sin completar)
                                            // Misiones no asignadas
                                            throw new ExcepcionMision(consolaNormal.mensajeError(118));
                                        case 4:             // Asignación de misiones completada - fase previa a 5
                                            juego.asignarEjercitosIniciales();
                                            /*
                                             * Para poder asignar un país se necesita que el correspondiente
                                             * jugador tenga ya disponibles los ejércitos iniciales
                                             */
                                            turnos.cambiarFaseInicial(5);
                                        case 5:             // Fase de asignación de países
                                        case 6:             // Aún no se han repartido ejércitos
                                            juego.asignarPais(partes[2], partes[3]);
                                            break;
                                        default:
                                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                    }
                                    break;          // case "pais"

                                default:
                                    throw new ExcepcionComando(consolaNormal.mensajeError(101));
                            }
                            break;          // case donde partes.length == 4

                        default:
                            throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    }
                    break;          // case "asignar"


                case "repartir":                                        // repartir ejercitos
                    if (partes.length == 2 && partes[1].equals("ejercitos")) {
                        if (turnos.todosPaisesAsignados()) {
                            juego.repartirEjercitosAut();
                            turnos.empezarJuego();
                            /*
                             * Tras ejecutarse repartirEjercitosAut(), todos los jugadores tendrán todos sus
                             * ejércitos iniciales repartidos. Por tanto, puede empezar el juego en sí.
                             */
                            juego.asignarEjercitos(juego.jugadorActual());
                            break;
                        } else {
                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }

                    } else if (partes.length == 4) {
                        /*
                         * En este caso, se deben comprobar dos casos: si la ejecución se encuentra en el reparto
                         * de ejércitos del proceso inicial, o si ya ha empezado el juego y se llama desde el turno
                         * de un jugador dado. Esto no ocurría para el comando "repartir ejércitos", que solo se
                         * puede utilizar en el primer caso.
                         */
                        switch (turnos.getFaseInicial()) {
                            case 6:         // Se han asignado todos los países => fase de repartir ejércitos
                                turnos.cambiarFaseInicial(7);
                            case 7:         // Fase de repartir ejércitos
                                juego.repartirEjercitos(juego.jugadorActual(),
                                        Integer.parseInt(partes[2]), partes[3], true);
                                break;

                            case -1:        // Ya ha empezado el juego
                                if (turnos.sePuedeRepartirEjercitosTurno()) {
                                    juego.repartirEjercitos(juego.jugadorActual(),
                                            Integer.parseInt(partes[2]), partes[3], true);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;

                            default:
                                throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }
                        break;

                    } else {
                        throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    }

                case "cambiar":
                    if (partes.length == 3 && partes[1].equals("cartas") && partes[2].equals("todas")) {
                        // cambiar cartas todas
                        if (turnos.sePuedeCambiarCartas()) {
                            juego.cambiarCartasTodas();
                        } else {
                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }
                    } else if (partes.length == 5) {        // cambiar cartas
                        if (turnos.sePuedeCambiarCartas()) {
                            juego.cambiarCartas(partes[2], partes[3], partes[4]);
                        } else {
                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }
                    } else {
                        throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    }
                    break;


                case "acabar":
                    if (partes.length == 2 && partes[1].equals("turno")) {          // acabar turno
                        if (turnos.sePuedeAcabarTurno()) {
                            juego.acabarTurno();
                        } else {
                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }
                    } else {
                        throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    }
                    break;


                case "jugador":                         // jugador
                    if (turnos.juegoEmpezado()) {
                        // La condición para llamar al comando es únicamente que haya empezado el juego
                        juego.jugador();
                    } else {
                        throw new ExcepcionComando(consolaNormal.mensajeError(99));
                    }
                    break;


                case "describir":
                    if (partes.length == 3) {
                        switch (partes[1]) {
                            case "jugador":             // describir jugador
                                if (turnos.juegoEmpezado()) {
                                    juego.describirJugador(partes[2]);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;

                            case "pais":                // describir pais
                                if (turnos.juegoEmpezado()) {
                                    juego.describirPais(partes[2]);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;

                            case "continente":          // describir continente
                                if (turnos.juegoEmpezado()) {
                                    juego.describirContinente(partes[2]);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;

                            default:
                                throw new ExcepcionComando(consolaNormal.mensajeError(101));
                        }
                    } else {
                        throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    }
                    break;


                case "ver":
                    if (partes.length == 2 && partes[1].equals("mapa")) {       // ver mapa
                        if (turnos.juegoEmpezado()) {
                            juego.verMapa();
                        } else {
                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }
                    } else {
                        throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    }
                    break;


                case "atacar":                              // atacar
                    switch (partes.length) {
                        case 3:
                            if (turnos.sePuedeAtacar()) {
                                juego.atacar(partes[1], partes[2]);
                            } else {
                                throw new ExcepcionComando(consolaNormal.mensajeError(99));
                            }
                            break;

                        case 5:
                            if (turnos.sePuedeAtacar()) {
                                juego.atacarMan(partes[1], partes[2], partes[3], partes[4]);
                            } else {
                                throw new ExcepcionComando(consolaNormal.mensajeError(99));
                            }
                            break;

                        default:
                            throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    }
                    break;


                case "rearmar":
                    if (partes.length != 4) {
                        throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    } else {                                // rearmar
                        if (turnos.sePuedeRearmar()) {
                            juego.rearmar(partes[1], Integer.parseInt(partes[2]), partes[3]);
                        } else {
                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }
                    }
                    break;


                case "obtener":
                    if (partes.length != 3) {
                        throw new ExcepcionComando(consolaNormal.mensajeError(101));
                    } else {
                        switch (partes[1]) {
                            case "frontera":                    // obtener frontera
                                if (turnos.juegoEmpezado()) {
                                    juego.obtenerFrontera(partes[2]);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;
                            case "continente":                  // obtener continente
                                if (turnos.juegoEmpezado()) {
                                    juego.obtenerContinente(partes[2]);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;
                            case "color":                       // obtener color
                                if (turnos.juegoEmpezado()) {
                                    juego.obtenerColor(partes[2]);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;
                            case "paises":                      // obtener paises
                                if (turnos.juegoEmpezado()) {
                                    juego.obtenerPaises(partes[2]);
                                } else {
                                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                                }
                                break;
                            default:
                                throw new ExcepcionComando(consolaNormal.mensajeError(101));
                        }
                    }
                    break;

                default:                // default de switch (comando)
                    throw new ExcepcionComando(consolaNormal.mensajeError(101));
            }
        } catch (ExcepcionGeo | ExcepcionCarta | ExcepcionJugador | ExcepcionMision | ExcepcionComando excepcionGeo) {
            consolaNormal.imprimir(excepcionGeo.getMessage());
        } catch (Exception exception) {
            // Se recogen también errores debidos a otras causas, como la lectura de ficheros
            consolaNormal.imprimir("Error no contemplado\n");
        }
    }

    public void crearJugadores(File file) {
        String linea;
        try {
            FileReader lector = new FileReader(file);
            BufferedReader bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null) {
                String[] infoJug = linea.split(";");

                // Para poder seguir leyendo el fichero aunque ocurra un error, se introduce un try-catch en el bucle
                try {
                    if (infoJug.length == 2) juego.crearJugador(infoJug[0], infoJug[1]);
                    // En cada línea se indican nombre y color
                } catch (Exception exception) {
                    /*
                     * Como los únicos tipos de excepciones que da crearJugador son excepcionGeo y excepcionJugador,
                     * se pueden recoger englobándolas en su clase base, Exception, pues la forma de gestionar cada
                     * uno es la misma.
                     */
                    consolaNormal.imprimir(exception.getMessage());
                }
            }

            bufferLector.close();

        } catch (Exception exception) {     // El error no se debe a crearJugador, sino al propio fichero
            consolaNormal.imprimir("Error en la apertura del fichero de jugadores\n");
        }
    }

    public void asignarMisiones(File file) {
        String linea;
        try {
            FileReader lector = new FileReader(file);
            BufferedReader bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null) {
                String[] infoMision = linea.split(";");

                // Para poder seguir leyendo el fichero aunque ocurra un error, se introduce un try-catch en el bucle
                try {
                    if (infoMision.length == 2) {
                        juego.asignarMision(infoMision[0], infoMision[1]);
                    }
                } catch (Exception exception) {
                    /*
                     * Como las únicas excepciones que da asignarMision son excepcionMision y excepcionJugador,
                     * se pueden recoger englobándolas en su clase base, Exception, pues la forma de gestionar cada
                     * uno es la misma.
                     */
                    consolaNormal.imprimir(exception.getMessage());
                }
            }

            bufferLector.close();

        } catch (Exception exception) {         // El error no se debe a asignarMisiones, sino al propio fichero
            consolaNormal.imprimir("Error en la apertura del fichero de misiones\n");
        }
    }

    public void asignarPaises(File file) {
        String linea;
        try {
            FileReader lector = new FileReader(file);
            BufferedReader bufferLector = new BufferedReader(lector);

            while ((linea = bufferLector.readLine()) != null) {
                String[] info = linea.split(";");

                // Para poder seguir leyendo el fichero aunque ocurra un error, se introduce un try-catch en el bucle
                try {
                    if (info.length == 2) {
                        // La llamada a asignar no necesita objeto. Java implícitamente asume que el objeto es this
                        juego.asignarPais(info[0], info[1]);
                    }
                } catch (Exception exception) {
                    /*
                     * Como las únicas excepciones que da asignarPais son excepcionGeo y excepcionJugador,
                     * se pueden recoger englobándolas en su clase base, Exception, pues la forma de gestionar cada
                     * uno es la misma.
                     */
                    consolaNormal.imprimir(exception.getMessage());
                }
            }

            bufferLector.close();

        } catch (Exception exception) {         // El error no se debe a asignarPais, sino al propio fichero
            consolaNormal.imprimir("Error en la apertura del fichero de países\n");
        }
    }
}
