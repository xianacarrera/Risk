package risk;

import risk.excepciones.*;

public interface Comando {

    /** Sobre la eliminación de crearJugadores, asignarMisiones y asignarPaises de la interfaz:
     *
     * Cuando se está ejecutando un comando a partir de las instrucciones contenidas en un fichero, se desea no
     * interrumpir por completo el proceso cuando ocurre un error. Es decir, que si una única línea del fichero
     * da error, se debe notificar al usuario del mismo y continuar con la lectura de las siguientes líneas.
     *
     * Esto implica que se deben tratar las excepciones en la propia función que utiliza el fichero (si se relegaran
     * al menú, se perdería el punto de referencia de la línea hasta la que se había leído). Como en la especificación
     * del proyecto se establece que los try-catch solo pueden estar en el menú, es imposible implementar
     * crearJugadores, asignarMisiones y asignarPaises en la clase Juego manteniendo el comportamiento descrito.
     *
     * Por consiguiente, estos 3 métodos han sido borrados de la interfaz y trasladados al menú.
     *
     **/

    /**
     *
     * @throws Exception
     */
    void crearMapa() throws Exception;

    /**
     *
     * @param abrevPais Abreviatura del país cuya frontera se quiere obtener
     * @throws ExcepcionGeo
     */
    void obtenerFrontera(String abrevPais) throws ExcepcionGeo;

    /**
     *
     * @param abrevPais Abreviatura del país cuyo continente se quiere obtener
     * @throws ExcepcionGeo
     */
    void obtenerContinente(String abrevPais) throws ExcepcionGeo;

    /**
     *
     * @param abrevPais Abreviatura del país cuyo color se quiere obtener
     * @throws ExcepcionGeo
     */
    void obtenerColor(String abrevPais) throws ExcepcionGeo;

    /**
     *
     * @param abrevCont Abreviatura del continente del que se quiere obtener sus países
     * @throws ExcepcionGeo
     */
    void obtenerPaises(String abrevCont) throws ExcepcionGeo;

    /**
     *
     * @param nombre Nombre del jugador
     * @param color Color que le será asignado
     * @throws Exception
     */
    void crearJugador(String nombre, String color) throws Exception;

    /**
     *
     * @param nombreJugador Nombre del jugador
     * @param idMision id de la misión que le será asiganda
     * @throws ExcepcionMision
     * @throws ExcepcionJugador
     */
    void asignarMision(String nombreJugador, String idMision) throws ExcepcionMision, ExcepcionJugador;

    /**
     *
     * @param nombreJugador Nombre del jugador
     * @param abrevPais Abreviatura del país que le será asignado
     * @throws ExcepcionJugador
     * @throws ExcepcionGeo
     */
    void asignarPais(String nombreJugador, String abrevPais) throws ExcepcionJugador, ExcepcionGeo;

    /**
     *
     * @param jugador Jugador cuyos ejércitos se van a repartir
     * @param numero Número de ejércitos a repartir
     * @param abrevPais Abreviatura del país donde se colocarán las tropas
     * @param imprimir Booleano (imprimir/no imprimir resultado)
     * @throws ExcepcionJugador
     * @throws ExcepcionGeo
     */
    void repartirEjercitos(Jugador jugador, int numero, String abrevPais, boolean imprimir)
            throws ExcepcionJugador, ExcepcionGeo;

    /**
     *
     * @throws ExcepcionJugador
     * @throws ExcepcionGeo
     */
    void repartirEjercitosAut() throws ExcepcionJugador, ExcepcionGeo;

    /**
     *
     * @param idCarta1 id de la primera carta a cambiar
     * @param idCarta2 id de la segunda carta a cambiar
     * @param idCarta3 id de la tercera carta a cambiar
     * @throws ExcepcionCarta
     */
    void cambiarCartas(String idCarta1, String idCarta2, String idCarta3) throws ExcepcionCarta;

    /**
     *
     * @throws ExcepcionCarta
     */
    void cambiarCartasTodas() throws ExcepcionCarta;

    /**
     *
     */
    void acabarTurno();

    /**
     *
     */
    void jugador();

    /**
     *
     * @param nombreJugador Nombre del jugador a describir
     * @throws ExcepcionJugador
     */
    void describirJugador(String nombreJugador) throws ExcepcionJugador;

    /**
     *
     * @param abrevPais Abreviatura del país a describir
     * @throws ExcepcionGeo
     */
    void describirPais(String abrevPais) throws ExcepcionGeo;

    /**
     *
     * @param abrevCont Abreviatura del continente a describir
     * @throws ExcepcionGeo
     */
    void describirContinente(String abrevCont) throws ExcepcionGeo;

    /**
     *
     */
    void verMapa();

    /**
     *
     * @param abrevAtacante Abreviatura del país desde el que se ataca
     * @param abrevDefensor Abreviatura del país al que se ataca
     * @throws ExcepcionJugador
     * @throws ExcepcionGeo
     */
    void atacar(String abrevAtacante, String abrevDefensor)
            throws ExcepcionJugador, ExcepcionGeo;

    /**
     *
     * @param abrevAtacante Abreviatura del país desde el que se ataca
     * @param rawDadosAtaque Representación del resultado de los dados de ataque
     * @param abrevDefensor Abreviatura del país al que se ataca
     * @param rawDadosDefensa Representación del resultado de los dados de defensa
     * @throws ExcepcionJugador
     * @throws ExcepcionGeo
     */
    void atacarMan(String abrevAtacante, String rawDadosAtaque, String abrevDefensor, String rawDadosDefensa)
            throws ExcepcionJugador, ExcepcionGeo;

    /**
     *
     * @param abrevPais1 Abreviatura del país desde donde parten las tropas
     * @param numEjercitos Número de tropas a mover
     * @param abrevPais2 Abreviatura del país destino de las tropas
     * @throws ExcepcionJugador
     * @throws ExcepcionGeo
     */
    void rearmar(String abrevPais1, int numEjercitos, String abrevPais2)
            throws ExcepcionJugador, ExcepcionGeo;

    /**
     *
     * @param id_Carta id de la carta a asignar, con formato Clase&País
     * @throws ExcepcionCarta
     */
    void asignarCarta(String id_Carta) throws ExcepcionCarta;


}
