package risk.cartas;

public abstract class Infanteria extends Carta {
    public Infanteria(String tipo, String pais){
        //En este nivel se asigna el tipo infanteria ya que todas las subclases que la hereden serán de ese tipo
        super("Infanteria", tipo, pais);
    }


}
