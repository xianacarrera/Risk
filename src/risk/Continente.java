package risk;

import java.util.ArrayList;
import java.util.HashMap;

class Continente {
    //Atributos
    private String nombre;
    private String abreviatura;
    private int numeroEjercitos;
    private int rearme;
    private String color;
    private ArrayList<Pais> paises;
    private HashMap<String, Jugador> jugadores;


    Continente(String nombre) {
        setNombre(nombre);
        setAbreviatura();
        asignarColorContinente();
        paises = new ArrayList<>();
        jugadores = new HashMap<>();
    }

    @Override
    public String toString() {
        String textoJugadores = "";     // Por qué poner aquí new String() ?? -> ver tema 1
        for (int i = 0; i < jugadores.size(); i++){
            if (i == 0){
                textoJugadores += " { \"" + jugadores.get(i).getNombre() + "\", " + jugadores.get(i).getNumEjercitos()
                        + " },\n";
            } else if (i != jugadores.size() - 1){
                textoJugadores += "\t\t\t{ \"" + jugadores.get(i).getNombre() + "\", " +
                        jugadores.get(i).getNumEjercitos() + " },\n";
            } else {
                textoJugadores += "\t\t\t{ \"" + jugadores.get(i).getNombre() + "\", " +
                        jugadores.get(i).getNumEjercitos() + " }\n";
            }
        }

        String mensaje =
                "{\n" +
                "  nombre: \""          + nombre            + "\",\n"   +
                "  abreviatura: \""     + abreviatura       + "\",\n"   +
                "  jugadores: ["        + textoJugadores    +
                "\t\t],\n"              +
                "  numeroEjercitos: "   + numeroEjercitos   + ",\n"     +
                "  rearmeContinente: "  + rearme            + "\n"      +
                "}\n";

        return mensaje;
    }

    public void setAbreviatura(){
        switch (nombre){
            case "América del Norte":
                abreviatura = "AméricaNorte";
                break;
            case "América del Sur":
                abreviatura = "AméricaSur";
                break;
            case "Europa":
            case "África":
            case "Asia":
            case "Australia":
                abreviatura = nombre;
                break;
        }
    }

    public void añadirJugador(String nombreJug, Jugador jugador){
        jugadores.put(nombreJug, jugador);
    }

    public String getNombre() {
        return this.nombre;
    }

    public void guardarPaises(Pais pais){
        // Implementarlo con un HashMap ?
        paises.add(pais);
    }

    public ArrayList<Pais> getPaises() {
        return paises;
    }

    public Pais getPaisAlf(int i) {
        return this.paises.get(i);
    }


    public void setNombre(String nombre) {
        if (nombre.equals("África")
                || nombre.equals("Europa")
                || nombre.equals("América del Norte")
                || nombre.equals("América del Sur")
                || nombre.equals("Australia")
                || nombre.equals("Asia")
                || nombre.equals("Océanos")) {
            this.nombre = nombre;
        } else {
            System.out.println("No es un continente");
        }
    }


    public String getColor() {
        return this.color;
    }

    public void asignarColorContinente() {
        switch (this.nombre) {
            case "Asia":
                this.color = "\033[46m";        // CYAN
                break;
            case "África":
                this.color = "\033[42m";        // VERDE
                break;
            case "Europa":
                this.color = "\033[43m";        // AMARILLO
                break;
            case "América del Norte":
                this.color = "\033[45m";        // VIOLETA
                break;
            case "América del Sur":
                this.color = "\033[41m";        // ROJO
                break;
            case "Australia":
                this.color = "\033[44m";        // AZUL
                break;
            case "Océanos":
                this.color = "\033[0m";       // RESET
                break;
            //No hay default porque esto ya se comprueba en setNombre

        }
    }


}