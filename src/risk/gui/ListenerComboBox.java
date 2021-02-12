package risk.gui;

import risk.Juego;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ListenerComboBox implements ItemListener {

    private final Ventana ventanaRisk;
    private Juego juego;

    public ListenerComboBox(Ventana ventanaRisk){
        this.ventanaRisk = ventanaRisk;
        juego = ventanaRisk.getJuego();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(ventanaRisk.getEleccionInformacion())){
            switch((String) e.getItem()){
                case "obtener frontera":
                case "obtener color":
                case "obtener continente":
                case "describir pais":
                    ventanaRisk.prepararIntroduccionPais();
                    break;
                case "obtener paises":
                case "describir continente":
                    ventanaRisk.prepararIntroduccionContinente();
                    break;
                case "describir jugador":
                    ventanaRisk.prepararIntroduccionJugador();
                    break;
                case "ver mapa":
                    ventanaRisk.getEleccionInformacion().setVisible(false);
                    juego.verMapa();
                    break;
                case "jugador":
                    ventanaRisk.getEleccionInformacion().setVisible(false);
                    juego.jugador();
            }
        }
    }
}
