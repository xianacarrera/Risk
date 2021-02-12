package risk.ejercitos;

import java.util.ArrayList;

public class EjercitoAzul extends EjercitoBase {

    // Se puede usar el constructor por defecto de Java

    public int ataque(ArrayList<Integer> dados){
        if (dados.size() > 1) {     // Se tira más de un dado
            dados.set(0, dados.get(0) + 1);  // Se suma una unidad al dado con mayor valor
            // Se utiliza el hecho de que los dados están ordenados de mayor a menor
            return 1;
        }
        return 0;
    }
}