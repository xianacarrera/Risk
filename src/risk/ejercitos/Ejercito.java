package risk.ejercitos;

import java.util.ArrayList;

public abstract class Ejercito {

    private int numTropas;

    // Se puede utilizar el constructor por defecto de Java al ser numTropas el único atributo

    public int getNumTropas(){
        return numTropas;
    }

    public void setNumTropas(int numTropas){
        this.numTropas = numTropas;
    }

    public void anhadirTropas(int numTropas){
        this.numTropas += numTropas;
    }

    public void quitarTropas(int numTropas){
        this.numTropas -= numTropas;
    }

    public abstract int ataque(ArrayList<Integer> dados);
}
