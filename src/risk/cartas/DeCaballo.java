package risk.cartas;

public class DeCaballo extends Caballeria{

    public DeCaballo(String pais){
        super("DeCaballo", pais);
    }

    @Override
    public int obtenerRearme() {
        return 3;
    }
}
