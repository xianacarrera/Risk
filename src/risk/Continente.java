package risk;

import java.util.ArrayList;

class Continente {
    //Atributos
    private String nombre;
    private int numeroEjercitos;
    private String color;
    private ArrayList<Pais> paises;

    Continente(String nombre) {
        setNombre(nombre);
        asignarColorContinente();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void guardarPaises(Pais pais){
        paises = new ArrayList<>();
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