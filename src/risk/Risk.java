package risk;

import java.util.ArrayList;


public class Risk {

    private Dados dados;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();


    public static void main(String[] args) {
        // Para poder lanzar los métodos hace falta una instancia de la clase Main, en este caso, risk
        Menu menu = new Menu();

        menu.inicializarContinentes();
        menu.crearMapa();
        menu.verMapa();

        menu.crearJugador("jugadores.csv");
        menu.asignarPaises("asignaciones.csv");
    }

    public void crearDados() {
        dados = new Dados();
    }


    public void repartirEjercitos() {
        // Número de ejércitos a añadir en función del número de jugadores:
        // 6 -> 20 = 5 * 4 = 5 * (10 - 6)
        // 5 -> 25 = 5 * 5 = 5 * (10 - 5)
        // 4 -> 30 = 5 * 6 = 5 * (10 - 4)
        // 3 -> 35 = 5 * 7 = 5 * (10 - 3)

        if (jugadores.size() >= 3 && jugadores.size() <= 6) {
            for (Jugador jugador : jugadores) {
                jugador.setNumEjercitos(5 * (10 - jugadores.size()));
            }
        }
    }
}