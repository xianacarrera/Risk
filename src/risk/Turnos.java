package risk;

/*
 * Esta clase se utiliza para gestionar el orden de los turnos de los jugadores, así como la introducción de comandos
 * siguiendo las guías establecidas. Esto es, comprueba condiciones relacionadas con la ejecución de ciertas funciones
 * necesarias para poder utilizar otras.
 */


/*
 * Valores de faseInicial:
 *  0 -> No se ha creado el mapa.
 *  1 -> Se ha creado el mapa. No se han creado jugadores.
 *  2 -> Se están creando jugadores (puede que aún no se haya acabado).
 *  3 -> Se ha empezado a asignar misiones. Ya no se permite crear más jugadores.
 *  4 -> Se ha terminado de asignar misiones. Se va a asignar países por primera vez. Se aprovecha para asignar
 *       los ejércitos iniciales a cada jugador.
 *  5 -> Se están asignando países.
 *  6 -> Se han asignado todos los países, pero aún no se ha repartido ningún ejército (fase de transición).
 *  7 -> Se están repartiendo ejércitos.
 *  -1 -> Ha empezado el juego.
 */

/*
 * Valores de ordenTurno:
 *  0 -> El turno acaba de empezar. No se ha introducido ningún comando.
 *  1 -> Se han cambiado cartas.
 *  2 -> Se están repartiendo ejércitos.
 *  3 -> Se ha atacado.
 */

public class Turnos {

    private static int faseInicial;                         // Gestión de la introducción de comandos
    private static int ordenTurno;                          // Gestión de la introducción de comandos
    private static boolean cartaAsignada;                   // Gestión de la asignación de cartas
    private static boolean sinEjercitosDisponibles;         // Gestión del reparto de ejércitos disponibles
    private static boolean conquistado;                     // Gestión de la conquista de países
    private static boolean rearmado;                        // Gestión del rearme de ejércitos
    private static int actual;                              // Jugador que tiene el turno

    // Getters
    public int getFaseInicial(){ return faseInicial; }
    public int getActual(){ return actual; }


    // Setters
    public void setActual(int actual){ Turnos.actual = actual; }


    // Actualización de atributos (a efectos prácticos, son setters)
    public void cambiarFaseInicial(int fase){ faseInicial = fase; }

    public void empezarJuego(){
        faseInicial = -1;
        ordenTurno = 0;
        actual = -1;
    }

    public void actualizarCartasCambiadas(){ ordenTurno = 1; }
    public void actualizarEjercitosTurnoRepartidos(){ ordenTurno = 2; }
    public void actualizarAtacado(){ ordenTurno = 3; }
    public void actualizarCartaAsignada(){ cartaAsignada = true; }
    public void actualizarConquistado(){ conquistado = true; }
    public void actualizarRearmado(){ rearmado = true; }
    public void actualizarEjercitosDisponibles(){ sinEjercitosDisponibles = true; }

    public void nuevoTurno(){
        ordenTurno = 0;
        conquistado = false;
        cartaAsignada = false;
        sinEjercitosDisponibles = false;
        rearmado = false;
    }

    // Comprobaciones
    public boolean mapaNoCreado(){ return faseInicial == 0; }
    public boolean todosPaisesAsignados(){
        /*
         * faseInicial == 6 --> Se han asignado todos los países, pero aún no se han repartido ejércitos.
         * faseInicial == 7 --> La fase de repartir ejércitos ya ha empezado (se ha usado el comando al menos una vez).
         */
        return faseInicial >= 6;
    }
    public boolean juegoEmpezado(){ return faseInicial == -1; }
    public boolean sePuedeCambiarCartas(){ return faseInicial == -1 && ordenTurno == 0; }
    public boolean sePuedeRepartirEjercitosTurno(){
        return faseInicial == -1 && (ordenTurno == 0 || ordenTurno == 1 || ordenTurno == 2);
    }

    public boolean sePuedeAtacar(){
        return faseInicial == -1 && sinEjercitosDisponibles && !rearmado;
        /*
         * No hay que comprobar ordenTurno >= 2 porque que no haya ejércitos disponibles ya implica lo primero (pues
         * siempre se asignan al menos 3 a cada jugador al inicio de su turno)
         */
    }

    public boolean sePuedeAsignarCarta(){
        return faseInicial == -1 && ordenTurno == 3 && !cartaAsignada && conquistado;
        /*
         * No hay que comprobar sinEjercitosDisponibles == true porque haber atacado (es decir, ordenTurno == 3) ya
         * implica que esta condición se cumple.
         */
    }

    public boolean sePuedeRearmar(){
        return faseInicial == -1 && ordenTurno == 3 && !rearmado;
        /*
         * No hay que comprobar sinEjercitosDisponibles == true porque haber atacado (es decir, ordenTurno == 3) ya
         * implica que esta condición se cumple.
         */
    }

    public boolean sePuedeAcabarTurno(){
        return faseInicial == 6 || (faseInicial == -1 && sinEjercitosDisponibles);
        /*
         * Solo se puede acabar turno si se está en la fase inicial de repartir ejércitos, o si ya ha empezado el juego,
         * el jugador ha repartido todos sus ejércitos disponibles.
         *
         * No hay que comprobar ordenTurno >= 2 porque que no haya ejércitos disponibles ya implica lo primero.
         */
    }
}
