package com.practica1;

import java.awt.*;
import java.util.Random;


public class Main {

    private int numJug;
    private Dados dados;

    public static void main(String[] args) {

    }

    public void setNumJug(int numJug){
        this.numJug = numJug;
    }

    public int getNumJug(){
        return this.numJug;
    }

    public void crearJugadores(){
        switch(numJug){
            case 6:
                Jugador jugador6 = new Jugador();
            case 5:
                Jugador jugador5 = new Jugador();
            case 4:
                Jugador jugador4 = new Jugador();
            case 3:
                Jugador jugador3 = new Jugador();
                Jugador jugador2 = new Jugador();
                Jugador jugador1 = new Jugador();
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

class Jugador{

    private String nombre;
    private Color color;
    private int numEjercitos;   // No estoy segura de si esto haría falta, lo dejo de momento

    Jugador(String nombre, Color color){
        this.nombre = nombre;
        this.color = color;
        this.numEjercitos = 0;
    }


}
