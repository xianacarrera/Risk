package risk.guiMapa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListenerCasillas implements ActionListener {

    private final VentanaEdicionMapa ventanaEdicionMapa;
    private static ArrayList<String> paises;
    private int cuenta;

    public ListenerCasillas(VentanaEdicionMapa ventanaEdicionMapa){
        this.ventanaEdicionMapa = ventanaEdicionMapa;
        paises = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cuenta++;
        JButton casilla = (JButton) e.getSource();
        paises.add(casilla.getText());
        casilla.setBackground(Color.DARK_GRAY);
        casilla.setEnabled(false);

        if (cuenta == 44){
            ventanaEdicionMapa.getGeoMapa().setEditableFalse();
            ventanaEdicionMapa.getPanelOpciones().setVisible(true);
            ventanaEdicionMapa.revalidate();
        }
    }

    public static ArrayList<String> getPaises(){
        return paises;
    }
}
