package risk.cartas;

public abstract class Caballeria extends Carta {

    //En este nivel se asigna el tipo caballeria ya que todas las subclases que la hereden serán de ese tipo
    public Caballeria(String tipo, String pais){
        super("Caballeria", tipo, pais);
    }

}
