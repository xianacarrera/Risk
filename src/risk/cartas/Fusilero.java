package risk.cartas;

public class Fusilero extends Infanteria{
    public Fusilero(String pais){
        super("Fusilero", pais);
    }

    @Override
    public int obtenerRearme() {
        return 2;
    }

}
