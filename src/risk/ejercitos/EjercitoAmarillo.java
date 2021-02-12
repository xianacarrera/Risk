package risk.ejercitos;

import java.util.ArrayList;

public class EjercitoAmarillo extends EjercitoBase {

    // Se puede usar el constructor por defecto de Java

    public int ataque(ArrayList<Integer> dados){
        if (dados.size() == 1) {     // Se tira un único dado
            dados.set(0, dados.get(0) + 2);         // Se suman dos unidades
            return 2;
        }
        return 0;
    }
}
