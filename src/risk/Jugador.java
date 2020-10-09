package risk;


import java.util.ArrayList;

class Jugador {

    private String nombre;
    private String color;
    private int numEjercitos;   // No estoy segura de si esto haría falta, lo dejo de momento
    private ArrayList<Pais> paises;     // Países controlados por el jugador

    Jugador(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        paises = new ArrayList<Pais>();
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre + ", color: " + color;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getColor() {
        return this.color;
    }

    public int getNumEjercitos() {
        return this.numEjercitos;
    }

    public void cambiarEjercito(int numTropas) {
        /* El nombre es un poco ambiguo (de primeras añadirEjercito o algo así quedaría mejor), pero esta función
            se puede reutilizar para RESTAR tropas. Si un jugador pierde tropas, se le puede pasar un número negativo
            y ya. Haría falta poner un if para que el número de tropas no pueda ser negativo (si llega a 0, creo
            que el jugador pierde y punto).
        */

        numEjercitos += numTropas;
    }


}
