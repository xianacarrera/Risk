package com.practica1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Main {

    private int numJug;
    private Dados dados;
    // TODO: No estoy segura de que los null aquí sean necesarios o si Java los pone automáticamente - preguntar.
    private Jugador jugador1 = null, jugador2 = null, jugador3 = null, jugador4 = null, jugador5 = null,
        jugador6 = null;

    // TODO: Convendría guardar los jugadores en una lista - preguntar si se puede usar ArrayList
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();


    // Estas variables aparecen aquí solo para pruebas. Ni caso:
    private String nombre6 = "Esto";
    private String nombre5 = "es";
    private String nombre4 = "un";
    private String nombre3 = "nombre";
    private String nombre2 = "inventado";
    private String nombre1 = "je";
    private Color color6 = Color.BLUE;
    private Color color5 = Color.BLUE;
    private Color color4 = Color.BLUE;
    private Color color3 = Color.BLUE;
    private Color color2 = Color.BLUE;
    private Color color1 = Color.BLUE;



    public static void main(String[] args) {
        // Para poder lanzar los métodos hace falta una instancia de la clase Main, en este caso, risk
        Main risk = new Main();

        risk.setNumJug(6);
        risk.crearJugadores();
        risk.crearDados();

        risk.pruebas();
    }

    public void pruebas(){
        // Código para pruebas varias. Aquí puede ir cualquier cosa, no hacer mucho caso.


        for (int i = 0; i < 10; i++) {
            System.out.println(dados.tirarDados(1));
        }

    }

    public void setNumJug(int numJug){
        this.numJug = numJug;
    }

    public int getNumJug(){
        return this.numJug;
    }

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

    public void crearDados(){
        dados = new Dados();
    }

    public void crearMapa(){


    }

    public void repartirEjercitos(){
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

    public void asignarCartasMision(){

    }

    public void asignarPaises(){

    }

    public void repartirCartasEjercito(){

    }

    public void jugarPartida(){

    }
}

// Esta clase representa los dados del juego
class Dados{

    // Devuelve el resultado total de una tirada de dados, cuyo número viene dado como parámetro
    public int tirarDados(int numDados){
        int sum = 0;
        Random rand = new Random();

        if (numDados > 0 && numDados < 4) {
            for (int i = 0; i < numDados; i++){
                   sum += rand.nextInt(6) + 1;
                   /* nextInt genera un entero aleatorio, en este caso, entre 0 (incluido) y 6 (no incluido). Para
                    que el rango pase a estar entre 1 y 6, le sumo 1 al resultado: pasa a un rango del 1 (incluido)
                    al 7 (no incluido) - 1,2,3,4,5,6
                    */
            }
            return sum;
        } else {
            // Número de dados incorrecto
            return -1;
        }
    }
}

class Continente{

}


class Casilla{

    private String pais;
    private int fila;
    private int col;
    private Continente lugar;       // Si la casilla es de mar, sería un Continente especial que represente el océano
    private Jugador ocupante;       // Jugador que está ocupando la casilla actualmente
    private int numEjercitos;       // El número de ejércitos que ocupan actualmente la casilla

    Casilla(String pais, int fila, int col, Continente lugar){
        // Es necesario pasar los atributos fila y col teniendo el nombre del pais? O sería mejor un switch aquí
        this.pais = pais;
        this.fila = fila;
        this.col = col;
        this.lugar = lugar;
        this.ocupante = null;       // Inicialmente, al crear el mapa, nadie ocupa la casilla.
        this.numEjercitos = 0;
        // No sé si es necesario poner ocupante a null y numEjercitos a 0, preguntar
    }

    public int getFila(){
        return this.fila;
    }

    public int getCol(){
        return this.col;
    }

    public Continente getLugar(){
        return this.lugar;
    }

    public void ocuparCasilla(Jugador jugador){
        this.ocupante = jugador;
    }

}

class Jugador{

    private String nombre;
    private Color color;
    private int numEjercitos;   // No estoy segura de si esto haría falta, lo dejo de momento

    Jugador(String nombre, Color color){
        this.nombre = nombre;
        this.color = color;
        this.numEjercitos = 0;
        // No estoy segura de si esto es necesario o Java pone numEjercitos automáticamente a 0
    }

    public String getNombre(){
        return this.nombre;
    }

    public Color getColor(){
        return this.color;
    }

    public void cambiarEjercito(int numTropas){
        /* El nombre es un poco ambiguo (de primeras añadirEjercito o algo así quedaría mejor), pero esta función
            se puede reutilizar para RESTAR tropas. Si un jugador pierde tropas, se le puede pasar un número negativo
            y ya. Haría falta poner un if para que el número de tropas no pueda ser negativo (si llega a 0, creo
            que el jugador pierde y punto).
        */

        numEjercitos += numTropas;
    }


}

class Ejercito{

    private Jugador propietario;
    private Casilla pais;

}

class Carta{


}

/*
class CartaMision implements Carta{

}
*/