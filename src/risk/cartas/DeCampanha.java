package risk.cartas;

public class DeCampanha extends Artilleria{
    public DeCampanha(String pais){
        super("DeCampanha", pais);
    }

    @Override
    public int obtenerRearme() {
        return 4;
    }
}
