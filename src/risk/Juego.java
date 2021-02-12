package risk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import risk.excepciones.*;
import risk.cartas.*;
import risk.enums.Misiones;


public class Juego implements Comando {

    /******************************************** Atributos *****************************************************/

    // Atributos correspondientes a otras clases del proyecto
    private final Color color;
    private final Dados dados;
    private final ConsolaNormal consolaNormal;
    private static Mapa mapa;
    private static Turnos turnos;

    // Gestión de los jugadores
    private static ArrayList<Jugador> jugadores;       // Jugadores de la partida

    // Gestión de misiones
    private static ArrayList<String> misionesDisponibles;          // Misiones aún no asignadas


    /******************************************** Métodos *****************************************************/

    public Juego() throws Exception {
        // Crear una instancia de consolaNormal puede generar una excepción que hay que tratar

        color = Color.getInstancia();
        dados = new Dados();
        consolaNormal = ConsolaNormal.getInstancia();
        turnos = new Turnos();

        jugadores = new ArrayList<>();
        misionesDisponibles = new ArrayList<>();

        // Misiones que aún no han sido asignadas a ningún jugador
        misionesDisponibles.add("M1");
        misionesDisponibles.add("M2");
        misionesDisponibles.add("M31");
        misionesDisponibles.add("M32");
        misionesDisponibles.add("M33");
        misionesDisponibles.add("M34");
        misionesDisponibles.add("M41");
        misionesDisponibles.add("M42");
        misionesDisponibles.add("M43");
        misionesDisponibles.add("M44");
        misionesDisponibles.add("M45");
        misionesDisponibles.add("M46");
    }


    public void crearMapa() throws Exception {
        // Se crea el mapa siguiendo el orden y la información de las casillas pasada por fichero

        if (mapa != null){          // El mapa se ha creado con la gui correspondiente. No se hace nada
            return;
        }
        mapa = new Mapa();

        String linea;
        File fichero = new File("Mapa.txt");
        FileReader lector = new FileReader(fichero);
        BufferedReader bufferLector = new BufferedReader(lector);

        while ((linea = bufferLector.readLine()) != null) {
            String[] infoPais = linea.split(" ");
            if (infoPais.length == 4) {
                // Se lee, en orden: abreviatura del país, abreviatura de su continente, fila y columna
                mapa.generarPais(infoPais[0], infoPais[1], Integer.parseInt(infoPais[2]),
                        Integer.parseInt(infoPais[3]));
            }
        }

        // Una vez se tienen todos los países, se generan todas sus fronteras
        mapa.generarFronteras();

        bufferLector.close();
    }


    public void obtenerFrontera(String abrevPais) throws ExcepcionGeo {
        ArrayList<Pais> fronteras = mapa.obtenerPais(abrevPais).getFronterasPais();
        // Si abrevPais no es una abreviatura correcta, obtenerPais() lanzará el error 109 (el país no existe)

        ArrayList<String> nombresFronteras = new ArrayList<>();

        for (Pais pais : fronteras) {
            nombresFronteras.add(pais.getNombre());         // Se guardan los nombres de todas las fronteras
        }

        consolaNormal.imprimir("{" + consolaNormal.formatoConjuntoStrings("frontera", nombresFronteras) +
                "\n}\n");
    }

    public void obtenerContinente(String abrevPais) throws ExcepcionGeo {
        consolaNormal.imprimir("{" + consolaNormal.formatoSimple("continente",
                mapa.obtenerPais(abrevPais).getContinente().getNombre()) + "\n}\n");
        // Si abrevPais no es una abreviatura correcta, obtenerPais() lanzará el error 109 (el país no existe)
    }

    public void obtenerColor(String abrevPais) throws ExcepcionGeo {
        // obtenerPais() lanza el error 109 si el país es null
        String col = mapa.obtenerPais(abrevPais).getContinente().getColor();

        if (color.esPermitido(col)) {
            consolaNormal.imprimir("{" + consolaNormal.formatoSimple("color",
                    mapa.obtenerPais(abrevPais).getContinente().getColor()) + "\n}\n");
        } else {
            throw new ExcepcionGeo(consolaNormal.mensajeError(100));     // Color no permitido
        }
        // Se comprueba antes el error 109 que el 100. Se mantiene la prioridad en las excepciones
    }

    public void obtenerPaises(String abrevCont) throws ExcepcionGeo {
        if (mapa.getContinentes().containsKey(abrevCont)) {
            ArrayList<Pais> paises = mapa.getContinente(abrevCont).getListaPaises();  // Todos los países del continente
            ArrayList<String> nombresPaises = new ArrayList<>();
            for (Pais pais : paises) {
                nombresPaises.add(pais.getNombre());            // Se almacena el nombre de cada país
            }

            consolaNormal.imprimir("{" + consolaNormal.formatoConjuntoStrings("paises", nombresPaises)
                    + "\n}\n");
        } else {
            throw new ExcepcionGeo(consolaNormal.mensajeError(102));         // El continente no existe
        }
    }

    public void crearJugador(String nombre, String color) throws Exception {
        // Lanza Exception porque el constructor de Jugador llama a ConsolaNormal, que puede lanzar errores

        /******************************** Comprobación de errores ****************************************/

        // Se debe lanzar el de mayor prioridad: 114 > 104 > 100

        if (!this.color.esPermitido(color)) {
            // Se comprueba primero el error 100 porque si se da, nunca ocurrirían ni el 114 ni el 104
            throw new ExcepcionGeo(consolaNormal.mensajeError(100));          // Color no permitido
        }


        // Los errores 114 y 104 se deben validar en bucles diferentes para mantener el orden de las excepciones
        for (Jugador jug : jugadores) {
            if (jug.getColor().equals(color)) {
                throw new ExcepcionJugador(consolaNormal.mensajeError(114));      // El color ya está asignado
            }
        }

        for (Jugador jug : jugadores) {
            if (jug.getNombre().equals(nombre)) {
                throw new ExcepcionJugador(consolaNormal.mensajeError(104));      // El jugador ya existe
            }
        }

        /******************************** Creación del jugador  **********************************************/

        Jugador jugador = new Jugador(nombre, color);       // Jugador determinado por su nombre y su color

        /*
         * El motivo por el que se compara el nombre y el color por separado, antes de crear el objeto jugador, y no
         * a posteriori mediante el equals() de la propia clase es porque este último compara tanto nombre como color,
         * y no permitiría distinguir, en caso de que el jugador ya exista o el color ya haya sido creado, a cuál de
         * las dos razones se debe el fallo.
         */

        jugadores.add(jugador);             // Se añade el jugador al array de jugadores (el orden es relevante)

        consolaNormal.imprimir("{" + consolaNormal.formatoSimple("nombre", nombre) + "," +
                consolaNormal.formatoSimple("color", color) + "\n}\n");

        turnos.cambiarFaseInicial(2);           // Se están creando jugadores
    }

    public void comprobarJugadores() throws ExcepcionComando {
        if (jugadores.size() < 3 || jugadores.size() > 6) {
            throw new ExcepcionComando(consolaNormal.mensajeError(99));           // Comando no permitido
        }
    }

    public String explicarMision(String idMision) {
        // Recibe el id de la misión e devuelve en qué consiste
        String mision;

        switch (idMision) {
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

    public void asignarMision(String nombreJugador, String idMision)
            throws ExcepcionMision, ExcepcionJugador {

        /******************************** Comprobación de errores ****************************************/

        Jugador jugador = null;

        for (Jugador jug : jugadores) {
            if (jug.getNombre().equals(nombreJugador)) {
                // No podemos usar contains porque este utiliza equals, que también compara el color del jugador
                // (y no lo sabemos)
                if (jug.getMision() != null) {
                    throw new ExcepcionMision(consolaNormal.mensajeError(117));
                    // El jugador ya tiene asignada misión
                }
                jugador = jug;
            }
        }

        if (!Misiones.contiene(idMision)) {          // enum con todas las posibles misiones
            throw new ExcepcionMision(consolaNormal.mensajeError(116));             // La misión no existe
        } else if (!misionesDisponibles.contains(idMision)) {
            // La misión ya no se encuentra en misionesDisponibles: ya ha sido asignada
            throw new ExcepcionMision(consolaNormal.mensajeError(115));         // La misión ya está asignada
        } else if (jugador == null) {          // En el bucle no se encontró ningún jugador con el nombre pasado
            throw new ExcepcionJugador(consolaNormal.mensajeError(103));         // El jugador no existe
        }

        /******************************** Asignación de la misión *****************************************/

        ArrayList<String> colores = new ArrayList<>();          // Guardará todos los colores ya usados

        for (Jugador jug : jugadores) {
            colores.add(jug.getColor());
        }

        switch (idMision) {
            // jugador.getColor() nunca dará null exception porque hemos asegurado que se introducen correctamente
            // en el ArrayList
            case "M41":
                if (!colores.contains("AMARILLO") || jugador.getColor().equals("AMARILLO")) {
                    idMision = "M1";
                }
                break;
            case "M42":
                if (!colores.contains("AZUL") || jugador.getColor().equals("AZUL")) {
                    idMision = "M1";
                }
                break;
            case "M43":
                if (!colores.contains("CYAN") || jugador.getColor().equals("CYAN")) {
                    idMision = "M1";
                }
                break;
            case "M44":
                if (!colores.contains("ROJO") || jugador.getColor().equals("ROJO")) {
                    idMision = "M1";
                }
                break;
            case "M45":
                if (!colores.contains("VERDE") || jugador.getColor().equals("VERDE")) {
                    idMision = "M1";
                }
                break;
            case "M46":
                if (!colores.contains("VIOLETA") || jugador.getColor().equals("VIOLETA")) {
                    idMision = "M1";
                }
                break;
            default:
                // No ocurre ningún caso especial. La misión se asigna correctamente: ya no estará disponible
                misionesDisponibles.remove(idMision);
        }

        jugador.setMision(idMision);            // Se le asigna al jugador el id de la misión

        consolaNormal.imprimir("{" + consolaNormal.formatoSimple("nombre", nombreJugador) + ","
                + consolaNormal.formatoSimple("mision", explicarMision(idMision)) + "\n}\n");

        turnos.cambiarFaseInicial(3);
        for (Jugador jug : jugadores) {
            if (jug.getMision() == null) {
                return;            //Algún jugador no tiene misión. Se lee el siguiente comando
            }
        }
        turnos.cambiarFaseInicial(4);
        //No se pasa a la siguiente fase hasta que todos los jugadores tengan asignadas misiones
    }


    public void asignarPais(String nombreJugador, String abrevPais)
            throws ExcepcionJugador, ExcepcionGeo {
        // Hay que indicar también Excepcion119 y Excepcion110 por usar repartirEjercitos(), aunque no vaya a dar error

        /******************************** Comprobación de errores ****************************************/

        Pais pais = mapa.obtenerPais(abrevPais);     // Se encarga de lanzar el error 109 (el país no existe)

        if (pais.getJugador() != null) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(113));          // El país ya está asignado
        }

        Jugador jugador = null;
        for (Jugador jug : jugadores) {
            if (jug.getNombre().equals(nombreJugador)) {
                jugador = jug;
            }
        }

        if (jugador == null) {          // No se ha encontrado ningún jugador con el nombre indicado
            throw new ExcepcionJugador(consolaNormal.mensajeError(103));      // El jugador no existe
        }

        /************************************ Asignación del país ******************************************/

        pais.ocuparPais(jugador, false, false);          // El país tendrá como número de veces ocupado 0
        repartirEjercitos(jugador, 1, abrevPais, false);        // En el país se coloca una tropa

        // Se guardan las fronteras para imprimir el resultado del comando
        ArrayList<Pais> fronteras = pais.getFronterasPais();
        ArrayList<String> nombresFronteras = new ArrayList<>();
        for (Pais frontera : fronteras) {
            nombresFronteras.add(frontera.getNombre());
        }

        consolaNormal.imprimir("{"
                + consolaNormal.formatoSimple("nombre", jugador.getNombre()) + ","
                + consolaNormal.formatoSimple("pais", pais.getNombre()) + ","
                + consolaNormal.formatoSimple("continente", pais.getContinente().getNombre()) + ","
                + consolaNormal.formatoConjuntoStrings("frontera", nombresFronteras)
                + "\n}\n");

        for (Pais p : mapa.getPaises().values()) {
            if (p.getJugador() == null) {
                return;
            }
        }
        turnos.cambiarFaseInicial(6);       // Se están asignando países
        // No se puede empezar a repartir ejércitos hasta haber asignado todos los países
    }

    public void asignarEjercitosIniciales() {
        // Número de ejércitos a añadir en función del número de jugadores:
        // 6 -> 20 = 5 * 4 = 5 * (10 - 6)
        // 5 -> 25 = 5 * 5 = 5 * (10 - 5)
        // 4 -> 30 = 5 * 6 = 5 * (10 - 4)
        // 3 -> 35 = 5 * 7 = 5 * (10 - 3)

        for (Jugador jugador : jugadores) {
            // Inicialmente cada jugador tiene 0 ejércitos
            jugador.aumentarEjercito(5 * (10 - jugadores.size()));
        }
    }

    public void asignarEjercitos(Jugador jugador) {
        // Nótese que el jugador pudo haber cambiado cartas antes de haberse llamado a esta función
        jugador.aumentarEjercito(jugador.calcularRearme());
    }


    public void repartirEjercitos(Jugador jugador, int numero, String abrevPais, boolean imprimir)
            throws ExcepcionJugador, ExcepcionGeo {

        /******************************** Comprobación de errores ****************************************/

        if (jugador.getEjercitosDisponibles() == 0) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(119));    // Ejércitos no disponibles
        }

        Pais pais = mapa.obtenerPais(abrevPais);       // Lanza el error 109 (el país no existe) si es necesario

        if (!pais.estaOcupadoPor(jugador)) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(110));   // El país no pertenece al jugador
        }

        /********************************** Reparto de ejércitos ******************************************/

        // No se pueden asignar más ejércitos que los disponibles para el jugador
        numero = Math.min(numero, jugador.getEjercitosDisponibles());

        pais.anhadirEjercitos(numero);

        jugador.reducirEjercito(numero);

        if (imprimir) {         // Puede ocurrir que se quiera llamar a esta función desde otro comando, sin imprimir
            consolaNormal.imprimir("{" + consolaNormal.formatoSimple("pais", pais.getNombre()) + ","
                    + consolaNormal.formatoSimple("jugador", jugador.getNombre()) + ","
                    + consolaNormal.formatoSimple("numeroEjercitosAsignados", numero) + ","
                    + consolaNormal.formatoSimple("numeroEjercitosTotales", pais.getNumEjercitos()) + ","
                    + consolaNormal.formatoDosConjuntos("paisesOcupadosContinente",
                    pais.getContinente().obtenerListaPaisesOcupados(jugador),
                    pais.getContinente().obtenerDatosOcupacion(jugador))
                    + "\n}\n");
        }

        turnos.actualizarEjercitosTurnoRepartidos();        // No influye en caso de que la partida ya haya empezado
        if (jugador.getEjercitosDisponibles() == 0) {
            turnos.actualizarEjercitosDisponibles();
        }
    }

    public void repartirEjercitosAut() throws ExcepcionJugador, ExcepcionGeo {
        /*
         * En realidad, este método solo puede lanzar el error 99. No obstante, como se utiliza repartirEjercitos(),
         * es necesario indicar que este puede ocasionar Excepcion119, Excepcion110 y Excepcion109
         */

        for (Jugador jugador : jugadores) {
            repartirEjercitosJugador(jugador);
        }
        verMapa();
    }

    public void repartirEjercitosJugador(Jugador jugador) throws ExcepcionJugador, ExcepcionGeo {

        HashMap<String, Continente> continentes = mapa.getContinentes();    // Abreviatura del continente, continente
        HashMap<String, Float> datosOcupacion = new HashMap<>();
        // Para cada continente (la String es su abreviatura) se guarda el número de países que el jugador ocupa en él

        ArrayList<Continente> cumplenRegla1 = new ArrayList<>();
        ArrayList<Continente> cumplenRegla4 = new ArrayList<>();
        int testRegla7 = 0;   // Se guarda el número de continentes donde el jugador ocupa menos del 25% de los países

        int numEjs = 0;
        Continente contTemp;

        boolean flag12 = false;
        boolean flag45 = false;
        boolean flag7 = false;

        for (Continente continente : continentes.values()) {
            datosOcupacion.put(continente.getAbreviatura(), 0.F);           // Se inicializa el ArrayList a 0
        }

        for (String abrevContinente : datosOcupacion.keySet()) {
            ArrayList<Pais> paisesContinente = continentes.get(abrevContinente).getListaPaises();
            for (Pais paisOcupado : paisesContinente) {
                if (paisOcupado.estaOcupadoPor(jugador)) {
                    datosOcupacion.put(abrevContinente, datosOcupacion.get(abrevContinente) + 1);
                }
            }
            // Después del bucle, datosOcupacion contiene el número de países que el jugador ocupa en el continente

            int numeroPaises = paisesContinente.size();

            datosOcupacion.put(abrevContinente, datosOcupacion.get(abrevContinente) / numeroPaises);
            // No es división entera porque el HashMap interno guarda floats

            // En realidad, el else if no haría falta hacerlo en todos los casos. Sin embargo, ahorra iteraciones
            // si hay que llegar a saltar a la regla 4
            if (datosOcupacion.get(abrevContinente) >= 0.5) {           // Ocupa el 50% de los países o más
                cumplenRegla1.add(continentes.get(abrevContinente));
            } else if (datosOcupacion.get(abrevContinente) >= 0.25) {   // Entre 25% y 50%: [25, 50)
                cumplenRegla4.add(continentes.get(abrevContinente));
            } else {        // Menos del 25% de los países
                testRegla7++;
            }
        }

        if (cumplenRegla1.size() > 1) {         // La regla 1 se cumple para más de 1 continente
            // flag12 se activa en el siguiente if
            contTemp = buscarPorReglas25(cumplenRegla1);            // Continente en el que se aplicará la regla
            cumplenRegla1.clear();                                  // Se quitan todos los elementos del ArrayList
            cumplenRegla1.add(contTemp);
        }

        if (cumplenRegla1.size() == 1) {
            flag12 = true;          // Se ha aplicado la regla 1 o la 2
            contTemp = cumplenRegla1.get(0);            // Hay un único elemento en cumplenRegla1

            switch (contTemp.getAbreviatura()) {        // La regla 1 varía en función del continente
                case "Oceanía":
                case "AméricaSur":
                    numEjs = (int) Math.round(jugador.getEjercitosDisponibles() /
                            (1.5 * datosOcupacion.get(contTemp.getAbreviatura()) * contTemp.getListaPaises().size()));
                    /*
                     * La división no es entera: en datosOcupacion hay floats.
                     * datosOcupación ahora guarda la proporción de países ocupados por continente para cada jugador.
                     * En la fórmula se neceista el número de países ocupados, por lo que se multiplica por el número
                     * de países de cada continente para obtenerlo.
                     * Math.round() devuelve long, porque el argumento es un double
                     */
                    break;
                default:
                    numEjs = Math.round(jugador.getEjercitosDisponibles() /
                            (datosOcupacion.get(contTemp.getAbreviatura()) * contTemp.getListaPaises().size()));
                    // La división no es entera: en datosOcupacion hay floats
                    // Math.round() devuelve int y no long, porque el argumento es un float
            }

            aplicarRegla1245(jugador, numEjs, contTemp);
        }

        if (flag12) {
            if (jugador.getEjercitosDisponibles() > 0) {
                // Se ha aplicado R1 o R2 y aún quedan ejércitos disponibles => Se colocan todos estos siguiendo R3
                aplicarRegla368(jugador);
            }

        } else {            // No se han aplicado ni R1, ni R2 ni R3
            if (cumplenRegla4.size() > 1) {         // R5
                // Se busca el continente con mayor porcentaje de países ocupados y menos países frontera
                contTemp = buscarPorReglas25(cumplenRegla4);
                cumplenRegla1.clear();
                cumplenRegla4.add(contTemp);
            }

            if (cumplenRegla4.size() == 1) {
                flag45 = true;          // Se ha aplicado R4 y/o R5
                contTemp = cumplenRegla4.get(0);        // Un único elemento

                numEjs = Math.round(jugador.getEjercitosDisponibles() /
                        (2 * datosOcupacion.get(contTemp.getAbreviatura()) * contTemp.getListaPaises().size()));
                // La división no es entera: en datosOcupacion hay floats
                // Math.round() devuelve int y no long, porque el argumento es un float
                aplicarRegla1245(jugador, numEjs, contTemp);
            }

            if (flag45) {
                if (jugador.getEjercitosDisponibles() > 0) {
                    // Se aplica R6 hasta agotar los ejércitos disponibles
                    aplicarRegla368(jugador);
                }
            }
        }

        if (testRegla7 == 6) {     // El jugador ocupa todos los continentes con menos del 25% de los países
            flag7 = true;       // Se va a usar R7

            switch (jugadores.size()) {
                case 3:
                case 4:
                    numEjs = 2 * jugador.getPaises().size();
                    break;
                case 5:
                case 6:
                    numEjs = 3 * jugador.getPaises().size();
            }
            for (Pais pais : jugador.getPaises().values()) {
                repartirEjercitos(jugador, numEjs, pais.getAbreviatura(), false);
            }
        }


        if (flag7 && jugador.getEjercitosDisponibles() > 0) {       //R8
            aplicarRegla368(jugador);
        }
    }

    public void aplicarRegla1245(Jugador jugador, int numEjercitos, Continente continente)
            throws ExcepcionJugador, ExcepcionGeo {
        // Las excepciones se deben al uso de repartirEjercitos
        for (Pais pais : continente.getListaPaises()) {         // No hay un orden particular para los países
            if (pais.estaOcupadoPor(jugador)) {
                repartirEjercitos(jugador, numEjercitos, pais.getAbreviatura(), false);
            }
        }
    }

    public void aplicarRegla368(Jugador jugador) throws ExcepcionJugador, ExcepcionGeo {
        ArrayList<Continente> menosPaisesFrontera = new ArrayList<>(6);
        ArrayList<Pais> unicoEjercito = new ArrayList<>();

        // menosPaisesFrontera está predefinido
        menosPaisesFrontera.add(mapa.getContinente("Oceanía"));
        menosPaisesFrontera.add(mapa.getContinente("África"));
        menosPaisesFrontera.add(mapa.getContinente("AméricaSur"));
        menosPaisesFrontera.add(mapa.getContinente("AméricaNorte"));
        menosPaisesFrontera.add(mapa.getContinente("Europa"));
        menosPaisesFrontera.add(mapa.getContinente("Asia"));

        for (Continente cont : menosPaisesFrontera) {       // Se priorizan los continentes con menos países frontera
            for (Pais pais : cont.getListaPaises()) {
                if (pais.estaOcupadoPor(jugador) && pais.getNumEjercitos() == 1) {      // Países con un único ejército
                    unicoEjercito.add(pais);
                }
            }
        }

        if (unicoEjercito.size() >= 1) {        // Sin esta comprobación, podría tener lugar un bucle infinito
            while (jugador.getEjercitosDisponibles() > 0) {     // Se reparten todos los ejércitos disponibles
                // El while es necesario por si se debe recorrer el ArrayList unicoEjercito varias veces
                for (Pais pais : unicoEjercito) {
                    // Se coloca un ejército en cada país seleccionado
                    if (jugador.getEjercitosDisponibles() > 0) {
                        // Es necesario comprobar que haya ejercitos para cada reparto, o habrá un error
                        repartirEjercitos(jugador, 1, pais.getAbreviatura(), false);
                    }
                }
            }
        }
    }

    public Continente buscarPorReglas25(ArrayList<Continente> candidatos) {
        float max = 0.F;
        ArrayList<Continente> mayorPorcentaje = new ArrayList<>(6);

        for (Continente cont : candidatos) {
            // Se busca el continente que tenga el mayor porcentaje de países ocupados
            if ((cont.obtenerListaPaisesOcupados().size() / (float) cont.getListaPaises().size()) > max) {
                max = cont.obtenerListaPaisesOcupados().size() / (float) cont.getListaPaises().size();
                mayorPorcentaje.clear();    // Hay un nuevo máximo. Se borran los anteriores continentes seleccionados
                mayorPorcentaje.add(cont);
            } else if ((cont.obtenerListaPaisesOcupados().size() / (float) cont.getListaPaises().size()) == max) {
                mayorPorcentaje.add(cont);      // El continente coincide en el porcentaje con el que tenía el máximo
            }
        }

        Continente continente = mayorPorcentaje.get(0);      // Siempre existe al menos un elemento

        if (mayorPorcentaje.size() > 1) {       // Hay más de un continente con el mismo porcentaje de países ocupados
            int minFront = continente.getNumPaisesFrontera();         // Se guarda el número de países frontera
            for (int i = 1; i < mayorPorcentaje.size(); i++) {      // Se compara con el resto de continentes
                if (mayorPorcentaje.get(i).getNumPaisesFrontera() < minFront) {
                    minFront = mayorPorcentaje.get(i).getNumPaisesFrontera();
                    continente = mayorPorcentaje.get(i);
                }
            }
        }

        return continente;
    }

    public void cambiarCartas(String idCarta1, String idCarta2, String idCarta3) throws ExcepcionCarta {

        int ejercitosCambiados;
        String idCarta = "";
        Carta carta1, carta2, carta3;

        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    idCarta = idCarta1;
                    break;
                case 1:
                    idCarta = idCarta2;
                    break;
                case 2:
                    idCarta = idCarta3;
                    break;
            }
            String[] partes = idCarta.split("&");

            switch (partes[0]) {
                case "Granadero":
                case "Fusilero":
                case "DeCamello":
                case "DeCaballo":
                case "DeCampanha":
                case "Antiaerea":
                    break;
                default:
                    throw new ExcepcionCarta(consolaNormal.mensajeError(123));
            }

            if (!mapa.getPaises().containsKey(partes[1])) {
                throw new ExcepcionCarta(consolaNormal.mensajeError(123));
            }
        }

        if (jugadorActual().getCartas().containsKey(idCarta1)) {
            carta1 = jugadorActual().getCarta(idCarta1);
        } else {
            throw new ExcepcionCarta(consolaNormal.mensajeError(122));
        }

        if (jugadorActual().getCartas().containsKey(idCarta2)) {
            carta2 = jugadorActual().getCarta(idCarta2);
        } else {
            throw new ExcepcionCarta(consolaNormal.mensajeError(122));
        }


        if (jugadorActual().getCartas().containsKey(idCarta3)) {
            carta3 = jugadorActual().getCarta(idCarta3);
        } else {
            throw new ExcepcionCarta(consolaNormal.mensajeError(122));
        }


        //Asignar ejercitos dependiendo de la configuración de las cartas
        int suma = valorNumericoCartas(carta1.getTipo()) + valorNumericoCartas(carta2.getTipo()) +
                valorNumericoCartas(carta3.getTipo());

        /*
         * Explicación sobre el valor numérico de las cartas:
         *
         * Asignándole un entero adecuado a cada tipo, se puede simplificar significativamente esta parte
         * del código. En concreto, utilizando:
         *      Infantería = 1
         *      Caballería = 3
         *      Artillería = 6
         * Se puede comprobar que cada una de las posibles combinaciones de los 3 tipos, sumando sus
         * respectivos valores, devuelve un entero diferente.
         * Las combinaciones que nos interesan son 4: {Infantería, Infatería, Infantería},
         * {Caballería, Caballería, Caballería}, {Artillería, Artillería, Artillería} e
         * {Infantería, Caballería, Artillería}, a los que les corresponden las sumas 3, 9, 18 y 10.
         * En caso de que la suma total sea distinta, se corresponderá con una configuración de cambio
         * no válida y se devolverá un error.
         */

        switch (suma) {
            case 3:
                ejercitosCambiados = 6;
                break;
            case 9:
                ejercitosCambiados = 8;
                break;
            case 10:
                ejercitosCambiados = 12;
                break;
            case 18:
                ejercitosCambiados = 10;
                break;
            default:
                throw new ExcepcionCarta(consolaNormal.mensajeError(121));
        }


        ejercitosCambiados += carta1.obtenerRearme() +
                carta2.obtenerRearme() + carta3.obtenerRearme() + carta1.getRearme() + carta2.getRearme() +
                carta3.getRearme();

        //Rearme por tipo de carta, subtipo y pais asociado
        jugadorActual().aumentarEjercito(ejercitosCambiados);

        //Eliminar las cartas del mazo del jugador
        jugadorActual().quitarCarta(carta1);
        jugadorActual().quitarCarta(carta2);
        jugadorActual().quitarCarta(carta3);

        //Devolverlas al mazo de reparto
        carta1.devolverCartaMazo();
        carta2.devolverCartaMazo();
        carta3.devolverCartaMazo();

        ArrayList<String> imprime = new ArrayList<>();
        imprime.add(idCarta1);
        imprime.add(idCarta2);
        imprime.add(idCarta3);

        consolaNormal.imprimir("{"
                + consolaNormal.formatoConjuntoStrings("cartasCambio", imprime)
                + consolaNormal.formatoConjuntoStrings("cartasQuedan", jugadorActual().getCartas().keySet())
                + consolaNormal.formatoSimple("numeroEjercitosCambiados", ejercitosCambiados)
                + consolaNormal.formatoSimple("numEjercitosRearme", jugadorActual().calcularRearme())
                + "\n}\n");

        turnos.actualizarCartasCambiadas();
    }

    public void cambiarCartasTodas() throws ExcepcionCarta {
        int ejercitosCambiados = 0;
        ArrayList<Carta> cartas1Cambiadas = new ArrayList<>();
        ArrayList<Carta> aux = new ArrayList<>();
        ArrayList<String> imprime = new ArrayList<>();
        int max = 0;

        if (jugadorActual().getCartas().keySet().size() < 3) {
            throw new ExcepcionCarta(consolaNormal.mensajeError(120));
        }

        cartas1Cambiadas.addAll(jugadorActual().getCartas().values());
        for (int i = 0; i < cartas1Cambiadas.size(); i++) {
            for (int j = i + 1; j < cartas1Cambiadas.size(); j++) {
                for (int k = j + 1; k < cartas1Cambiadas.size(); k++) {
                    int suma = valorNumericoCartas(cartas1Cambiadas.get(i).getTipo()) +
                            valorNumericoCartas(cartas1Cambiadas.get(j).getTipo()) +
                            valorNumericoCartas(cartas1Cambiadas.get(k).getTipo());


                    /*
                     * Explicación sobre el valor numérico de las cartas:
                     *
                     * Asignándole un entero adecuado a cada tipo, se puede simplificar significativamente esta parte
                     * del código. En concreto, utilizando:
                     *      Infantería = 1
                     *      Caballería = 3
                     *      Artillería = 6
                     * Se puede comprobar que cada una de las posibles combinaciones de los 3 tipos, sumando sus
                     * respectivos valores, devuelve un entero diferente.
                     * Las combinaciones que nos interesan son 4: {Infantería, Infatería, Infantería},
                     * {Caballería, Caballería, Caballería}, {Artillería, Artillería, Artillería} e
                     * {Infantería, Caballería, Artillería}, a los que les corresponden las sumas 3, 9, 18 y 10.
                     * En caso de que la suma total sea distinta, se corresponderá con una configuración de cambio
                     * no válida y se devolverá un error.
                     */

                    switch (suma) {
                        case 3:
                            ejercitosCambiados = 6;
                            break;
                        case 9:
                            ejercitosCambiados = 8;
                            break;
                        case 10:
                            ejercitosCambiados = 12;
                            break;
                        case 18:
                            ejercitosCambiados = 10;
                            break;
                        default:
                            ejercitosCambiados = 0;

                    }

                    if (ejercitosCambiados != 0) {
                        ejercitosCambiados += cartas1Cambiadas.get(i).obtenerRearme() +
                                cartas1Cambiadas.get(j).obtenerRearme() + cartas1Cambiadas.get(k).obtenerRearme() +
                                cartas1Cambiadas.get(i).getRearme() + cartas1Cambiadas.get(j).getRearme() +
                                cartas1Cambiadas.get(k).getRearme();
                    }

                    if (ejercitosCambiados > max) {
                        max = ejercitosCambiados;
                        aux.clear();
                        aux.add(cartas1Cambiadas.get(i));
                        aux.add(cartas1Cambiadas.get(j));
                        aux.add(cartas1Cambiadas.get(k));
                    }
                }
            }
        }

        if (max == 0) throw new ExcepcionCarta(consolaNormal.mensajeError(121));
        jugadorActual().aumentarEjercito(max);

        //Ver como crear carta

        //Eliminar las cartas del mazo del jugador
        jugadorActual().quitarCarta(aux.get(0));
        jugadorActual().quitarCarta(aux.get(1));
        jugadorActual().quitarCarta(aux.get(2));

        //Devolverlas al mazo de reparto
        aux.get(0).devolverCartaMazo();
        aux.get(1).devolverCartaMazo();
        aux.get(2).devolverCartaMazo();

        imprime.add(aux.get(0).getIdCarta());
        imprime.add(aux.get(1).getIdCarta());
        imprime.add(aux.get(2).getIdCarta());

        //Imprimir resultado
        consolaNormal.imprimir("{"
                + consolaNormal.formatoConjuntoStrings("cartasCambio", imprime)
                + consolaNormal.formatoConjuntoStrings("cartasQuedan", jugadorActual().getCartas().keySet())
                + consolaNormal.formatoSimple("numeroEjercitosCambiados", max)
                + consolaNormal.formatoSimple("numeroEjercitosRearme", jugadorActual().calcularRearme())
                + "\n}\n");

        turnos.actualizarCartasCambiadas();
    }

    public int valorNumericoCartas(String id) {
        switch (id) {
            case "Infanteria":
                return 1;
            case "Caballeria":
                return 3;
            case "Artilleria":
                return 6;
        }
        return 0;
    }


    public Jugador jugadorActual() {
        return jugadores.get(turnos.getActual());
    }

    public void comprobarVictoria() {
        boolean victoria = false;

        switch (jugadorActual().getMision()) {
            case "M1":
                if (jugadorActual().getNumPaisesConquistados() >= 24) victoria = true;
                break;
            case "M2":
                if (jugadorActual().getNumPaisesConquistados2Ejs() >= 18) victoria = true;
                break;
            case "M31":
                if (jugadorActual().getContinentes().containsKey("Asia") &&
                        jugadorActual().getContinentes().containsKey("AméricaSur")) victoria = true;
                break;
            case "M32":
                if (jugadorActual().getContinentes().containsKey("Asia") &&
                        jugadorActual().getContinentes().containsKey("África")) victoria = true;
                break;
            case "M33":
                if (jugadorActual().getContinentes().containsKey("AméricaNorte") &&
                        jugadorActual().getContinentes().containsKey("África")) victoria = true;
                break;
            case "M34":
                if (jugadorActual().getContinentes().containsKey("AméricaNorte") &&
                        jugadorActual().getContinentes().containsKey("Oceanía")) victoria = true;
                break;
            case "M41":
                for (Jugador jugador : jugadores) {
                    if (jugador.getColor().equals("AMARILLO") && jugador.getPaises().isEmpty()) {
                        victoria = true;
                        break;
                    }
                }
                break;
            case "M42":
                for (Jugador jugador : jugadores) {
                    if (jugador.getColor().equals("AZUL") && jugador.getPaises().isEmpty()) {
                        victoria = true;
                        break;
                    }
                }
                break;
            case "M43":
                for (Jugador jugador : jugadores) {
                    if (jugador.getColor().equals("CYAN") && jugador.getPaises().isEmpty()) {
                        victoria = true;
                        break;
                    }
                }
                break;
            case "M44":
                for (Jugador jugador : jugadores) {
                    if (jugador.getColor().equals("ROJO") && jugador.getPaises().isEmpty()) {
                        victoria = true;
                        break;
                    }
                }
                break;
            case "M45":
                for (Jugador jugador : jugadores) {
                    if (jugador.getColor().equals("VERDE") && jugador.getPaises().isEmpty()) {
                        victoria = true;
                        break;
                    }
                }
                break;
            case "M46":
                for (Jugador jugador : jugadores) {
                    if (jugador.getColor().equals("VIOLETA") && jugador.getPaises().isEmpty()) {
                        victoria = true;
                        break;
                    }
                }
        }

        if (victoria) {
            consolaNormal.imprimir("¡Victoria de " + jugadorActual().getNombre() + "!");
            System.exit(0);
        }
    }

    public void siguienteJugador() {
        turnos.setActual((turnos.getActual() + 1) % jugadores.size());     // Se trabaja con módulo jugadores.size()
        if (turnos.juegoEmpezado()) {
            turnos.nuevoTurno();            // Se reinician los parámetros necesarios
            for (Carta carta : jugadorActual().getCartas().values()) {
                // Se actualiza el valor de rearme de cada carta
                if (jugadorActual().getPaises().containsKey(carta.getPais())) {
                    carta.setRearme(1);
                } else {
                    carta.setRearme(0);
                }
            }
            asignarEjercitos(jugadorActual());
        }
    }

    public void acabarTurno() {
        if (!turnos.juegoEmpezado()) {      // El juego aún no ha empezado
            //Si ya se le han repartido todos los ejércitos a cada jugador, empieza la partida
            boolean empezar = true;
            for (Jugador jugador : jugadores) {
                if (jugador.getEjercitosDisponibles() > 0) {
                    empezar = false;
                    break;
                }
            }
            if (empezar) {
                turnos.empezarJuego();
            }
        }

        // comprobarVictoria();     // No necesario
        siguienteJugador();         // Se pasa la acción al siguiente jugador por orden de introducción

        consolaNormal.imprimir("{"
                + consolaNormal.formatoSimple("nombre", jugadorActual().getNombre()) + ","
                + consolaNormal.formatoSimple("numeroEjercitosRearmar", jugadorActual().calcularRearme())
                + "\n}\n");
    }


    public void jugador() {
        consolaNormal.imprimir(jugadorActual().toString());
    }

    public void describirJugador(String nombreJugador) throws ExcepcionJugador {
        // Tan solo se conoce el nombre del jugador

        Jugador jugador = null;

        for (Jugador jug : jugadores) {
            if (jug.getNombre().equals(nombreJugador)) {
                // No podemos usar contains porque este utiliza equals, que también compara el color del jugador
                // (y no lo sabemos)
                jugador = jug;
            }
        }

        if (jugador == null) {           // El if dentro del bucle for-each no se ha activado nunca
            throw new ExcepcionJugador(consolaNormal.mensajeError(103));       // El jugador no existe
        }

        if (!jugadorActual().equals(jugador)) {         // No se mostrará la misión
            consolaNormal.imprimir(jugador.toString(true));
        } else {                                        // Se muestra la misión
            consolaNormal.imprimir(jugador.toString());
        }
    }

    public void describirPais(String abrevPais) throws ExcepcionGeo {
        consolaNormal.imprimir(mapa.obtenerPais(abrevPais).toString());
        // Si el país no existe, mapa.obtenerPais() lanza Excepcion109
    }

    public void describirContinente(String abrevCont) throws ExcepcionGeo {
        if (mapa.getContinentes().containsKey(abrevCont)) {
            consolaNormal.imprimir(mapa.getContinente(abrevCont).toString());
        } else {
            throw new ExcepcionGeo(consolaNormal.mensajeError(102));         // El continente no existe
        }
    }

    public void verMapa() {
        consolaNormal.imprimir(mapa.toString());
    }

    public void atacar(String abrevAtacante, String abrevDefensor)
            throws ExcepcionJugador, ExcepcionGeo {

        /******************************** Comprobación de errores ****************************************/

        Pais atacante = mapa.obtenerPais(abrevAtacante);            // Puede lanzar la Excepcion109

        if (atacante.getNumEjercitos() <= 1) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(124));    // No hay ejércitos suficientes
        }

        Pais defensor = mapa.obtenerPais(abrevDefensor);            // Puede lanzar la Excepcion109

        if (!atacante.tieneFronteraCon(defensor)) {
            throw new ExcepcionGeo(consolaNormal.mensajeError(112));     // Los países no son frontera
        } else if (defensor.estaOcupadoPor(jugadorActual())) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(111));     // El país pertenece al jugador
        } else if (!atacante.estaOcupadoPor(jugadorActual())) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(110));     // El país no pertenece al jugador
        }

        /****************************************** Atacar ***********************************************/

        ArrayList<Integer> dadosAtaque, dadosDefensa;
        ArrayList<Integer> infoAtaque = new ArrayList<>();
        ArrayList<Integer> infoDefensa = new ArrayList<>();

        infoAtaque.add(atacante.getNumEjercitos());
        infoDefensa.add(defensor.getNumEjercitos());

        switch (infoAtaque.get(0)) {        // Según el número de ejércitos del país atacante
            case 2:
                dadosAtaque = dados.tirarDados(1);      // Se tira un dado
                break;
            case 3:
                dadosAtaque = dados.tirarDados(2);      // Se tiran 2 dados
                break;
            default:
                dadosAtaque = dados.tirarDados(3);      // Se tiran 3 dados
        }

        if (infoDefensa.get(0) == 1) {                           // 1 ejército en el país defensor
            dadosDefensa = dados.tirarDados(1);         // Tira 1 dado
        } else {                                                 // Más de 1 ejército en el país defensor
            dadosDefensa = dados.tirarDados(2);         // Tira 2 dados
        }

        // Los dados se ordenan decrecientemente
        dadosAtaque.sort(Collections.reverseOrder());
        dadosDefensa.sort(Collections.reverseOrder());

        // Se aplica el extra del poder de ataque de los ejércitos
        atacante.getEjercito().ataque(dadosAtaque);

        for (int i = 0; i < (Math.min(dadosAtaque.size(), dadosDefensa.size())); i++) {
            // Se comparan solo los dados con valores más altos
            if (dadosDefensa.get(i) >= dadosAtaque.get(i)) {        // Gana el defensor
                atacante.quitarEjercitos(1);
            } else {         // Gana el atacante
                defensor.quitarEjercitos(1);
                /*
                 * No es necesario comprobar que el número de ejércitos en el país defensor sea positivo antes de quitar
                 * uno, porque las restricciones en la cantidad de dados que lanza impiden que se quede sin tropas en
                 * medio de una comparación.
                 */
            }
        }

        String contConquistado = "null";

        if (defensor.getNumEjercitos() == 0) {          // El país defensor es conquistado
            defensor.getJugador().perderPais(defensor);         // El jugador que ocupaba el país lo pierde

            int pasados;
            if (atacante.getNumEjercitos() - Math.min(infoAtaque.get(0), 3) < 1) {
                /*
                 * Se intenta pasar el número de tropas que tenía el país atacante el inicio, con un máximo de 3 tropas,
                 * es decir, Math.max(infoAtaque(get(0), 3).
                 *
                 * Si al país atacante le quedan menos de 4 ejércitos, hacer esta operación puede dejarlo sin tropas.
                 * En este caso, se pasan tantas como sea posible, es decir, atacante.getNumEjercitos() - 1
                 */
                pasados = atacante.getNumEjercitos() - 1;
                atacante.quitarEjercitos(atacante.getNumEjercitos() - 1);
            } else {    // No hay problemas
                pasados = Math.min(infoAtaque.get(0), 3);
                atacante.quitarEjercitos(Math.min(infoAtaque.get(0), 3));
            }

            defensor.ocuparPais(atacante.getJugador(), true,
                    pasados >= 2);
            // dosEjercitos es true si se ha conquistado con 2 o más ejércitos
            turnos.actualizarConquistado();         // Se indica que el jugador actual ha conseguido un país

            if (defensor.getJugador().getContinentes().containsKey(defensor.getContinente().getAbreviatura())) {
                // El jugador ha ocupado por completo el continente (ocuparPais actualiza esto automáticamente)
                contConquistado = defensor.getContinente().getNombre();
            }
            // Si el jugador que ha conquistado tiene el continente en su HashMap de continentes conquistados,
            // se imprimirá el nombre de dicho continente. En caso contrario, se imprimirá "null" (valor inicial)

            defensor.setNumEjercitos(pasados);
        }

        infoAtaque.add(atacante.getNumEjercitos());     // Número de tropas que quedan en el país atacante
        infoDefensa.add(defensor.getNumEjercitos());    // Número de tropas que quedan en el país defensor

        consolaNormal.imprimir("{\n"
                + consolaNormal.formatoConjuntoCaracter("dadosAtaque", '[', dadosAtaque) + ","
                + consolaNormal.formatoConjuntoCaracter("dadosDefensa", '[', dadosDefensa) + ","
                + consolaNormal.formatoConjuntoCaracter("ejercitosPaisAtaque", '[', infoAtaque)
                + ","
                + consolaNormal.formatoConjuntoCaracter("ejercitosPaisDefensa", '[', infoDefensa)
                + ","
                + consolaNormal.formatoSimple("paisAtaquePerteneceA", atacante.getJugador().getNombre()) + ","
                + consolaNormal.formatoSimple("paisDefensaPerteneceA", defensor.getJugador().getNombre()) + ","
                + consolaNormal.formatoSimple("continenteConquistado", contConquistado)
                + "\n}\n");

        turnos.actualizarAtacado();
    }


    public void atacarMan(String abrevAtacante, String rawDadosAtaque, String abrevDefensor, String rawDadosDefensa)
            throws ExcepcionJugador, ExcepcionGeo {

        /******************************** Comprobación de errores ****************************************/

        String[] dAtaque = rawDadosAtaque.split("x");
        String[] dDefensa = rawDadosDefensa.split("x");

        Pais atacante = mapa.obtenerPais(abrevAtacante);                // Puede lanzar la Excepcion109

        if (atacante.getNumEjercitos() <= 1) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(124));   // No hay ejércitos suficientes
        }

        // Dados ataque
        if (atacante.getNumEjercitos() == 2 && dAtaque.length > 1) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(124));      // No hay ejércitos suficientes
        } else if (atacante.getNumEjercitos() == 3 && dAtaque.length > 2) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(124));      // No hay ejércitos suficientes
        }

        Pais defensor = mapa.obtenerPais(abrevDefensor);                // Puede lanzar la Excepcion109

        //Dados defensa
        if (defensor.getNumEjercitos() == 1 && dDefensa.length > 1) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(124));    // No hay ejércitos suficientes
        } else if (!atacante.tieneFronteraCon(defensor)) {
            throw new ExcepcionGeo(consolaNormal.mensajeError(112));     // Los países no son frontera
        } else if (defensor.estaOcupadoPor(jugadorActual())) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(111));     // El país pertenece al jugador
        } else if (!atacante.estaOcupadoPor(jugadorActual())) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(110));     // El país no pertenece al jugador
        }

        /*********************************** Preparación de los dados *************************************/

        ArrayList<Integer> dadosAtaque = new ArrayList<>();
        ArrayList<Integer> dadosDefensa = new ArrayList<>();

        for (String value : dAtaque) {
            dadosAtaque.add(Integer.parseInt(value));
        }

        for (String s : dDefensa) {
            dadosDefensa.add(Integer.parseInt(s));
        }

        /****************************************** Atacar ***********************************************/

        ArrayList<Integer> infoAtaque = new ArrayList<>();
        ArrayList<Integer> infoDefensa = new ArrayList<>();

        infoAtaque.add(atacante.getNumEjercitos());
        infoDefensa.add(defensor.getNumEjercitos());

        // Se aplica el extra del poder de ataque de los ejércitos
        atacante.getEjercito().ataque(dadosAtaque);

        for (int i = 0; i < (Math.min(dadosAtaque.size(), dadosDefensa.size())); i++) {
            // Se comparan solo los dados con valores más altos
            if (dadosDefensa.get(i) >= dadosAtaque.get(i)) {        // Gana el defensor
                atacante.quitarEjercitos(1);
            } else {         // Gana el atacante
                defensor.quitarEjercitos(1);
                /*
                 * No es necesario comprobar que el número de ejércitos en el país defensor sea positivo antes de quitar
                 * uno, porque las restricciones en la cantidad de dados que lanza impiden que se quede sin tropas en
                 * medio de una comparación.
                 */
            }
        }

        String contConquistado = "null";

        if (defensor.getNumEjercitos() == 0) {          // El país defensor es conquistado
            defensor.getJugador().perderPais(defensor);         // El jugador que ocupaba el país lo pierde

            int pasados;
            if (atacante.getNumEjercitos() - Math.min(infoAtaque.get(0), 3) < 1) {
                /*
                 * Se intenta pasar el número de tropas que tenía el país atacante el inicio, con un máximo de 3 tropas,
                 * es decir, Math.max(infoAtaque(get(0), 3).
                 *
                 * Si al país atacante le quedan menos de 4 ejércitos, hacer esta operación puede dejarlo sin tropas.
                 * En este caso, se pasan tantas como sea posible, es decir, atacante.getNumEjercitos() - 1
                 */
                pasados = atacante.getNumEjercitos() - 1;
                atacante.quitarEjercitos(atacante.getNumEjercitos() - 1);
            } else {    // No hay problemas
                pasados = Math.min(infoAtaque.get(0), 3);
                atacante.quitarEjercitos(Math.min(infoAtaque.get(0), 3));
            }

            defensor.ocuparPais(atacante.getJugador(), true,
                    pasados >= 2);
            // dosEjercitos es true si se ha conquistado con 2 o más ejércitos
            turnos.actualizarConquistado();         // Se indica que el jugador actual ha conseguido un país

            if (defensor.getJugador().getContinentes().containsKey(defensor.getContinente().getAbreviatura())) {
                // El jugador ha ocupado por completo el continente (ocuparPais actualiza esto automáticamente)
                contConquistado = defensor.getContinente().getNombre();
            }
            // Si el jugador que ha conquistado tiene el continente en su HashMap de continentes conquistados,
            // se imprimirá el nombre de dicho continente. En caso contrario, se imprimirá "null" (valor inicial)

            defensor.setNumEjercitos(pasados);
        }

        infoAtaque.add(atacante.getNumEjercitos());     // Número de tropas que quedan en el país atacante
        infoDefensa.add(defensor.getNumEjercitos());    // Número de tropas que quedan en el país defensor

        consolaNormal.imprimir("{"
                + consolaNormal.formatoConjuntoCaracter("dadosAtaque", '[', dadosAtaque)
                + consolaNormal.formatoConjuntoCaracter("dadosDefensa", '[', dadosDefensa)
                + consolaNormal.formatoConjuntoCaracter("ejercitosPaisAtaque", '[', infoAtaque)
                + consolaNormal.formatoConjuntoCaracter("ejercitosPaisDefensa", '[', infoDefensa)
                + consolaNormal.formatoSimple("paisAtaquePerteneceA", atacante.getJugador().getNombre()) + ","
                + consolaNormal.formatoSimple("paisDefensaPerteneceA", defensor.getJugador().getNombre()) + ","
                + consolaNormal.formatoSimple("continenteConquistado", contConquistado)
                + "\n}\n");

        turnos.actualizarAtacado();
    }

    public void rearmar(String abrevPais1, int numEjercitos, String abrevPais2)
            throws ExcepcionJugador, ExcepcionGeo {

        /******************************** Comprobación de errores ****************************************/

        Pais origen = mapa.obtenerPais(abrevPais1);         // Puede lanzar la Excepcion109
        Pais destino = mapa.obtenerPais(abrevPais2);        // Puede lanzar la Excepcion109

        int ejsInicialesOrigen, ejsInicialesDestino;

        if ((ejsInicialesOrigen = origen.getNumEjercitos()) == 1) {
            throw new ExcepcionJugador(consolaNormal.mensajeError(124));     // No hay ejércitos disponibles
        } else if (!origen.tieneFronteraCon(destino)) {
            throw new ExcepcionGeo(consolaNormal.mensajeError(112));         // Los países no son frontera
        } else if (!origen.getJugador().equals(destino.getJugador())) {
            /*
             * No se cumprueba que origen.getJugador() != null porque en este punto del juego, todos los países están
             * asignados
             */
            throw new ExcepcionJugador(consolaNormal.mensajeError(110));     // El país no pertenece al jugador
        }

        /******************************************* Rearme ***********************************************/

        if (ejsInicialesOrigen <= numEjercitos) {
            numEjercitos = ejsInicialesOrigen - 1;          // El país origen no puede quedar sin ejércitos
        }

        ejsInicialesDestino = destino.getNumEjercitos();

        destino.anhadirEjercitos(numEjercitos);
        origen.quitarEjercitos(numEjercitos);

        consolaNormal.imprimir("{"
                + consolaNormal.formatoSimple("numeroEjercitosInicialesOrigen", ejsInicialesOrigen) + ","
                + consolaNormal.formatoSimple("numeroEjercitosInicialesDestino", ejsInicialesDestino) + ","
                + consolaNormal.formatoSimple("numeroEjercitosFinalesOrigen", origen.getNumEjercitos()) + ","
                + consolaNormal.formatoSimple("numeroEjercitosFinalesDestino", destino.getNumEjercitos())
                + "\n}\n");

        turnos.actualizarRearmado();            // Se indica que se ha rearmado en el turno
    }

    public void asignarCarta(String id_Carta) throws ExcepcionCarta {
        // Se indican Excepcion121 y Excepcion120 por el posible uso de cambiarCartasTodas()

        /******************* Comprobación de errores e inicialización de variables *******************************/

        String[] partes = id_Carta.split("&");

        Carta carta;
        switch (partes[0]) {
            case "Fusilero":
                carta = new Fusilero(partes[1]);
                break;
            case "Granadero":
                carta = new Granadero(partes[1]);
                break;
            case "DeCaballo":
                carta = new DeCaballo(partes[1]);
                break;
            case "DeCamello":
                carta = new DeCamello(partes[1]);
                break;
            case "DeCampanha":
                carta = new DeCampanha(partes[1]);
                break;
            case "Antiaerea":
                carta = new Antiaerea(partes[1]);
                break;
            default:
                // El identificador no sigue el formato correcto
                throw new ExcepcionCarta(consolaNormal.mensajeError(125));
        }

        if (!mapa.getPaises().containsKey(partes[1])) {
            // El identificador no sigue el formato correcto
            throw new ExcepcionCarta(consolaNormal.mensajeError(125));
        }

        ArrayList<String> repartidasTipo = carta.getListaRepartidasTipo();
        //Comprobamos si la carta asociada a ese tipo y a ese pais ya se ha repartido
        if (repartidasTipo.contains(carta.getIdTipo())) {
            throw new ExcepcionCarta(consolaNormal.mensajeError(126));   // Carta de equipamiento ya asignada
        }

        /********************************* Asignación de la carta ******************************************/

        jugadorActual().nuevaCarta(carta);     // El jugador obtiene una carta
        carta.anhadirCartaMazo();         // Se añade la carta creada a la lista de las repartidas
        if (jugadorActual().getPaises().containsKey(carta.getPais())) {
            // El país de la carta es controlado por el jugador
            carta.setRearme(1);
        } else {
            carta.setRearme(0);
        }

        //Imprimir resultado
        consolaNormal.imprimir("{"
                + consolaNormal.formatoSimple("tipoCarta", carta.getSubTipo()) + ","
                + consolaNormal.formatoSimple("paisAsociado", carta.getPais()) + ","
                + consolaNormal.formatoSimple("perteneceAJugador", jugadorActual().getNombre()) + ","
                + consolaNormal.formatoSimple("ejercitosRearme", carta.getRearme())
                + "\n}\n");

        if (jugadorActual().getCartas().keySet().size() > 6) {          // El máximo de cartas son 6
            cambiarCartasTodas();           // Se cambian las cartas de la mejor forma posible
        }

        turnos.actualizarCartaAsignada();               // Se ha asignado una carta en el turn
    }


    // Métodos necesario para la gui
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }

    public HashMap<String,Pais> getPaises(){
        return mapa.getPaises();
    }
}
