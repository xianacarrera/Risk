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

    private Dados dados;

    // TODO: Convendría guardar los jugadores en una lista - preguntar si se puede usar ArrayList
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();


    public static void main(String[] args) {
        // Para poder lanzar los métodos hace falta una instancia de la clase Main, en este caso, risk
        Menu menu = new Menu();

        menu.inicializarContinentes();
        menu.crearMapa();
        menu.verMapa();
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

class Ejercito {

    private Jugador propietario;
    private Pais pais;

}

class Carta {


}

/*
class CartaMision implements Carta{

}
*/