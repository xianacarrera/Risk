package risk.cartas;

public class DeCamello extends Caballeria{
    public DeCamello(String pais){
        super("DeCamello", pais);
    }

    @Override
    public int obtenerRearme() {
        return 2;
    }
}
