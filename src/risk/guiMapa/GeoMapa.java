package risk.guiMapa;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class GeoMapa extends JPanel {

    private final VentanaEdicionMapa ventanaEdicionMapa;
    private TitledBorder bordeGeoMapa;
    private ListenerCasillas listenerCasillas;

    private ArrayList<ArrayList<JButton>> botones;

    public GeoMapa(VentanaEdicionMapa ventanaEdicionMapa) {
        this.ventanaEdicionMapa = ventanaEdicionMapa;
        initComponentes();
        setupComponentes();
        layoutComponentes();
        addEventHandlers();
    }

    public void initComponentes() {
        bordeGeoMapa = BorderFactory.createTitledBorder("Elegir 42 casillas con países");

        botones = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            botones.add(new ArrayList<>());
            for (int j = 0; j < 11; j++) {
                botones.get(i).add(new JButton(i + " " + j));
            }
        }
    }

    public void setupComponentes(){
        setBorder(bordeGeoMapa);
    }

    public void layoutComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < 8; i++){
            restricciones.gridy = i;
            for (int j = 0; j < 11; j++){
                restricciones.gridx = j;
                add(botones.get(i).get(j), restricciones);
            }
        }
    }

    public void addEventHandlers(){
        listenerCasillas = new ListenerCasillas(ventanaEdicionMapa);
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 11; j++){
                botones.get(i).get(j).addActionListener(listenerCasillas);
            }
        }
    }

    public void setEditableFalse(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 11; j++){
                botones.get(i).get(j).setEnabled(false);
            }
        }
    }

    public ListenerCasillas getListenerCasillas(){ return listenerCasillas; }
}
