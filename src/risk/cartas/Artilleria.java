package risk.cartas;

// Clase intermedia en la jerarquía de cartas. Clases derivadas:
public abstract class Artilleria extends Carta {
    //En este nivel se asigna el tipo artilleria ya que todas las subclases que la hereden serán de ese tipo
    public Artilleria(String tipo, String pais){
        super("Artilleria",tipo, pais);
    }

}
