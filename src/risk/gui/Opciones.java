package risk.gui;

import risk.Juego;
import risk.Jugador;
import java.util.ArrayList;

public class Opciones {

    Juego juego;

    public Opciones(Ventana ventana){
        juego = ventana.getJuego();
    }

    public static final String[] informacion = new String[]{
            "obtener frontera",
            "obtener continente",
            "obtener color",
            "obtener paises",
            "jugador",
            "describir jugador",
            "describir pais",
            "describir continente",
            "ver mapa"
    };

    public static final String[] paises = new String[]{
            "Alaska",
            "Alberta",
            "USAOeste",
            "TNoroeste",
            "Ontario",
            "USAEste",
            "AmeCentral",
            "Groenlan",
            "Quebec",
            "Venezuela",
            "Perú",
            "Argentina",
            "Brasil",
            "Islandia",
            "Escandina",
            "GBretaña",
            "EurOcc",
            "EurNorte",
            "EurSur",
            "Rusia",
            "ANorte",
            "Congo",
            "Egipto",
            "AOriental",
            "Sudáfrica",
            "Madagasca",
            "Siberia",
            "Yakustsk",
            "Urales",
            "Afgan",
            "OMedio",
            "Kamchatka",
            "Irkutsk",
            "Mongolia",
            "China",
            "India",
            "Japón",
            "SAsiático",
            "Indonesia",
            "AusOccid",
            "NGuinea",
            "AusOrien"
    };

    public static final String[] continentes = new String[]{
            "AméricaNorte",
            "AméricaSur",
            "Europa",
            "África",
            "Asia",
            "Oceanía"
    };

    public static final String[] subtiposCartas = new String[]{
            "Fusilero",
            "Granadero",
            "DeCaballo",
            "DeCamello",
            "DeCampanha",
            "Antiaerea"
    };

    public String[] obtenerNombresJugadores(){
        String[] nombres = new String[6];
        ArrayList<Jugador> jugadores = juego.getJugadores();
        for (int i = 0; i < jugadores.size(); i++){
            nombres[i] = jugadores.get(i).getNombre();
        }
        return nombres;
    }
}
