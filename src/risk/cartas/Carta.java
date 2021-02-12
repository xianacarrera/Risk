package risk.cartas;

import java.util.ArrayList;
import java.util.HashMap;


// Clase que representa una carta. Incluyendo su tipo, pais y subtipo asociado. El rearme con el pais. Además de el
// mazo de cartas que se han repartido

// Esta clase junto con sus subclases infantería, caballería, artillería son abstractas ya que no existe una carta como
// tal existe una carta fusilero, granadero, ...

public abstract class Carta {
    private final String idCarta;
    private final String idTipo;
    private final String tipo;
    private final String pais;
    private final String subTipo;
    private static ArrayList<String> listaRepartidasTipo;
    private int rearme;


    public Carta(String tipo, String subtipo, String pais){

        // Este arrayList sirve para que no haya dos cartas del mismo tipo y distinto subtipo asignado
        // a un pais determinado
        if(listaRepartidasTipo == null)
            listaRepartidasTipo = new ArrayList<>();

        this.tipo = tipo;
        this.subTipo = subtipo;
        this.pais = pais;

        // El id de carta esta formado por el subtipo de la carta(Fusilero, DeCamello,...) & pais asociado
        this.idCarta = subtipo + "&" + pais;

        this.idTipo = tipo + "&" + pais;
    }

    // Getters

    //devuelve el rearme dado por el pais de la carta si pertenece o no al jugador
    public int getRearme(){ return this.rearme; }

    //devuelve el tipo de la carta (infanteria,caballeria, artilleria)
    public String getTipo(){
        return this.tipo;
    }

    //devuelve el subtipo de la carta (DeCaballo, antiaerea...)
    public String getSubTipo(){
        return this.subTipo;
    }

    //Devuelve el pais asociado a la carta
    public String getPais(){
        return this.pais;
    }

    //Devuelve el idCarta
    public String getIdCarta(){
        return this.idCarta;
    }

    public ArrayList<String> getListaRepartidasTipo(){return listaRepartidasTipo;}

    // Setters
    public void setRearme(int rearme){
        this.rearme=rearme;
    }

    // Otros métodos
    // Reparte una carta añadiéndola al hashMap
    public void anhadirCartaMazo(){
        listaRepartidasTipo.add(idTipo);
    }

    // Quitar una carta de hashMap de jugadores
    public void devolverCartaMazo() {
        // La carta se elimina del mazo, como si no se hubiera repartido
        listaRepartidasTipo.remove(idTipo);
    }

    // Devuelve el tipo de la carta y su pais
    public String getIdTipo(){
        return idTipo;
    }


    //funcion para obtener el rearme dependiendo del subtipo
    /*
    Infanteria:
        Fusilero   --> 2
        Granadero  --> 1
    Caballeria:
        DeCaballo  --> 3
        DeCamello  --> 2
    Artilleria:
        DeCampanha --> 4
        Antiaerea  --> 3
     */
    public abstract int obtenerRearme();
}