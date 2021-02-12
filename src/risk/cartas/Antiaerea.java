package risk.cartas;

public class Antiaerea extends Artilleria{
    public Antiaerea(String pais){
        super("Antiaerea", pais);
    }

    @Override
    public int obtenerRearme() {
        return 3;
    }
}
