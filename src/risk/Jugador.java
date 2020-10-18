package risk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class Jugador {

    private final String nombre;
    private final String color;
    private String mision;
    private int numEjercitos;   // No estoy segura de si esto haría falta, lo dejo de momento
    private int numEjercitosRearmar;
    private HashMap<String, Pais> paises;     // Países controlados por el jugador
    private Formateo formateo;

    Jugador(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        paises = new HashMap<String, Pais>();
        formateo = new Formateo();
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    /*
    @Override
    public String toString() {
        ArrayList<String> nombresPaises = new ArrayList<>();
        ArrayList<String> nombresContinentes = new ArrayList<>();

        for (Pais pais : paises.values()){
            nombresPaises.add(pais.getNombre());
            nombresContinentes.add(pais.getContinente().getNombre());
        }

        // Orden?

        String mensaje = "{\n" + formateo.formatoSimple("nombre", nombre)
                + formateo.formatoSimple("color", color)
                + formateo.formatoSimple("mision", mision)
                + formateo.formatoSimple("numeroEjercitos", numEjercitos)
                + formateo.formatoConjunto("paises", nombresPaises)
                + formateo.formatoConjunto("continentes",nombresContinentes)
                + formateo.formatoConjunto("cartas", )
                + formateo.formatoSimpleFinal("numeroEjercitosRearmar", numEjercitosRearmar)
                + "}\n";

        return mensaje;
    }
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null){
            return false;
        }

        if (getClass() != obj.getClass()){
            return false;
        }

        final Jugador otro = (Jugador) obj;

        if (!this.nombre.equals(otro.getNombre())){
            return false;
        }

        if (!this.color.equals(otro.getColor())){
            return false;
        }

        return true;
    }

    public String getMision(){
        return mision;
    }

    public void setMision(String mision){
        this.mision = mision;
    }

    public int getNumEjercitos() {
        return numEjercitos;
    }

    public void setNumEjercitos(int numTropas){
        numEjercitos = numTropas;
    }

    public void aumentarEjercito(int numTropas) {
        numEjercitos += numTropas;
    }

    public void reducirEjercito(int numTropas){
        numEjercitos -= numTropas;
    }

    public void asignarPais(Pais pais){
        paises.put(pais.getAbreviatura(), pais);
    }

}
