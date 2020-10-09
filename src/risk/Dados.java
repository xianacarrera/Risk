package risk;

import java.util.Random;

// Esta clase representa los dados del juego
class Dados {

    // Devuelve el resultado total de una tirada de dados, cuyo número viene dado como parámetro
    public int tirarDados(int numDados) {
        int sum = 0;
        Random rand = new Random();

        if (numDados > 0 && numDados < 4) {
            for (int i = 0; i < numDados; i++) {
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