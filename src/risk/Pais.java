package risk;


import java.text.Normalizer;

class Pais {

    private String nombre;
    private String abreviatura;
    private boolean esFrontera;
    private Continente continente;
    private Jugador jugador;
    private int numEjercitos;
    private int numVecesOcupado;
    private Ejercito ejercito;
    private Formateo formateo;

    public Pais(String abreviatura, Continente continente) {
        setAbreviatura(abreviatura);
        setNombre();
        this.continente = continente;

        ejercito = new Ejercito(this);
        formateo = new Formateo();
    }

    public void ocuparPais(Jugador jugador){
        this.jugador = jugador;
        ejercito.setJugador(jugador);
    }

    public Continente getContinente() {
        return this.continente;
    }

    public Ejercito getEjercito(){
        return ejercito;
    }

    public int getNumEjercitos() {
        return ejercito.getNumTropas();
    }

    public void setNumEjercitos(int numTropas){
        ejercito.setNumTropas(numTropas);
    }

    public void añadirEjercitos(int numTropas){
        ejercito.añadirTropas(numTropas);
    }

    public void quitarEjercitos(int numTropas){
        ejercito.quitarTropas(numTropas);
    }

    public Jugador getJugador(){
        return jugador;
    }

    public boolean estaOcupadoPor(Jugador jugador){
        return this.jugador.equals(jugador);
    }

    public void setNombre() {
        switch (this.abreviatura) {
            case "Afgan":
                this.nombre = "Afganistán";
                break;
            case "China":
                this.nombre = "China";
                break;
            case "India":
                this.nombre = "India";
                break;
            case "Irkutsk":
                this.nombre = "Irkutsk";
                break;
            case "Japón":
                this.nombre = "Japón";
                break;
            case "Kamchatka":
                this.nombre = "Kamchatka";
                break;
            case "Mongolia":
                this.nombre = "Mongolia";
                break;
            case "OMedio":
                this.nombre = "Oriente Medio";
                break;
            case "Siberia":
                this.nombre = "Siberia";
                break;
            case "SAsiático":
                this.nombre = "Sudeste Asiático";
                break;
            case "Urales":
                this.nombre = "Urales";
                break;
            case "Yakustsk":
                this.nombre = "Yakustsk";
                break;
            case "ANorte":
                this.nombre = "África del Norte";
                break;
            case "AOriental":
                this.nombre = "África Oriental";
                break;
            case "Congo":
                this.nombre = "Congo";
                break;
            case "Egipto":
                this.nombre = "Egipto";
                break;
            case "Madagasca":
                this.nombre = "Madagascar";
                break;
            case "Sudáfrica":
                this.nombre = "Sudáfrica";
                break;
            case "Escandina":
                this.nombre = "Escandinavia";
                break;
            case "EurNorte":
                this.nombre = "Europa del Norte";
                break;
            case "EurOcc":
                this.nombre = "Europa Occidental";
                break;
            case "EurSur":
                this.nombre = "Europa del Sur";
                break;
            case "GBretaña":
                this.nombre = "Gran Bretaña";
                break;
            case "Islandia":
                this.nombre = "Islandia";
                break;
            case "Rusia":
                this.nombre = "Rusia";
                break;
            case "Alaska":
                this.nombre = "Alaska";
                break;
            case "Alberta":
                this.nombre = "Alberta";
                break;
            case "AmeCentra":
                this.nombre = "América Central";
                break;
            case "Groenlan":
                this.nombre = "Groenlandia";
                break;
            case "Ontario":
                this.nombre = "Ontario";
                break;
            case "Quebec":
                this.nombre = "Quebec";
                break;
            case "TNoroeste":
                this.nombre = "Territorios del Noroeste";
                break;
            case "USAEste":
                this.nombre = "Estados Unidos del Este";
                break;
            case "USAOeste":
                this.nombre = "Estados Unidos del Oeste";
                break;
            case "Venezuela":
                this.nombre = "Venezuela";
                break;
            case "Perú":
                this.nombre = "Perú";
                break;
            case "Brasil":
                this.nombre = "Brasil";
                break;
            case "Argentina":
                this.nombre = "Argentina";
                break;
            case "Indonesia":
                this.nombre = "Indonesia";
                break;
            case "NGuinea":
                this.nombre = "Nueva Guinea";
                break;
            case "AusOccid":
                this.nombre = "Australia Occidental";
                break;
            case "AusOrient":
                this.nombre = "Australia Oriental";
                break;
            default:
                System.out.println("ERROR");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setAbreviatura(String abreviatura) {
        if (abreviatura != null && (abreviatura.equals("Afgan")
                || abreviatura.equals("China")
                || abreviatura.equals("India")
                || abreviatura.equals("Irkutsk")
                || abreviatura.equals("Japón")
                || abreviatura.equals("Kamchatka")
                || abreviatura.equals("Mongolia")
                || abreviatura.equals("OMedio")
                || abreviatura.equals("Siberia")
                || abreviatura.equals("SAsiático")
                || abreviatura.equals("Urales")
                || abreviatura.equals("Yakustsk")
                || abreviatura.equals("ANorte")
                || abreviatura.equals("AOriental")
                || abreviatura.equals("Congo")
                || abreviatura.equals("Egipto")
                || abreviatura.equals("Madagasca")
                || abreviatura.equals("Sudáfrica")
                || abreviatura.equals("Escandina")
                || abreviatura.equals("EurNorte")
                || abreviatura.equals("EurOcc")
                || abreviatura.equals("EurSur")
                || abreviatura.equals("GBretaña")
                || abreviatura.equals("Islandia")
                || abreviatura.equals("Rusia")
                || abreviatura.equals("Alaska")
                || abreviatura.equals("Alberta")
                || abreviatura.equals("AmeCentra")
                || abreviatura.equals("Groenlan")
                || abreviatura.equals("Ontario")
                || abreviatura.equals("Quebec")
                || abreviatura.equals("TNoroeste")
                || abreviatura.equals("USAEste")
                || abreviatura.equals("USAOeste")
                || abreviatura.equals("Venezuela")
                || abreviatura.equals("Perú")
                || abreviatura.equals("Brasil")
                || abreviatura.equals("Argentina")
                || abreviatura.equals("Indonesia")
                || abreviatura.equals("NGuinea")
                || abreviatura.equals("AusOccid")
                || abreviatura.equals("AusOrient"))){
            this.abreviatura = abreviatura;
        } else {
            System.out.println("No es un país");
        }
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    /*
    public boolean esPaisFrontera(){
        //Conexiones directas

    }
     */

    /*
    public String toString(){
        String mensaje = "{\n"
                + formateo.formatoSimple("nombre", nombre)
                + formateo.formatoSimple("abreviatura", abreviatura)
                + formateo.formatoSimple("continente", continente.getNombre())
                + formateo.formatoConjunto("frontera", )
                + formateo.formatoSimple("jugador", jugador.getNombre())
                + formateo.formatoSimple("numeroEjercitos", numEjercitos)
                + formateo.formatoSimpleFinal("numeroVecesOcupado", numVecesOcupado)
                + "}\n";

        return mensaje;
    }
     */

}
