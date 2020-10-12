package risk;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class Jugador {

    private String nombre;
    private String color;
    private String mision;
    private int numEjercitos;   // No estoy segura de si esto haría falta, lo dejo de momento
    private HashMap<String, Pais> paises;     // Países controlados por el jugador

    Jugador(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        paises = new HashMap<String, Pais>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre + ", color: " + color;
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
