package risk;

public class Ejercito {

    private Pais pais;
    private Jugador jugador;
    private int numTropas;

    public Ejercito(Pais pais){
        this.pais = pais;
    }

    public int getNumTropas(){
        return numTropas;
    }

    public void setNumTropas(int numTropas){
        this.numTropas = numTropas;
    }

    public void añadirTropas(int numTropas){
        this.numTropas += numTropas;
    }

    public void quitarTropas(int numTropas){
        this.numTropas -= numTropas;
    }

    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }

    public String getColorEjercito(){
        return jugador.getColor();
    }

}
