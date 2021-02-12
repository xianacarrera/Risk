package risk.cartas;

public class Granadero extends Infanteria{
    public Granadero(String pais){
        super("Granadero", pais);
    }

    @Override
    public int obtenerRearme() {
        return 1;
    }
}
