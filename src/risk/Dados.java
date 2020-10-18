package risk;

import java.util.ArrayList;
import java.util.Random;

// Esta clase representa los dados del juego
class Dados {

    private Random rand; //??????? Seed

    public Dados(){
        rand = new Random();
    }

    // Devuelve el resultado total de una tirada de dados
    public ArrayList<Integer> tirarDados(int numDados) {
        ArrayList<Integer> tirada = new ArrayList<>();

        if (numDados > 0 && numDados < 4) {
            for (int i = 0; i < numDados; i++) {
                tirada.add(rand.nextInt(6) + 1);
                   /* nextInt genera un entero aleatorio, en este caso, entre 0 (incluido) y 6 (no incluido). Para
                    que el rango pase a estar entre 1 y 6, le sumo 1 al resultado: pasa a un rango del 1 (incluido)
                    al 7 (no incluido) - 1,2,3,4,5,6
                    */
            }
            return tirada;
        } else {
            // Número de dados incorrecto
            return null;
        }
    }

    // ???????????????????
    public int tirarDado(){
        return rand.nextInt(6) + 1;
    }
}