package risk.guiMapa;

import risk.Casilla;
import risk.Mapa;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class ImagenMapa extends JPanel {

    private JPanel panel;
    private JPanel contenido;
    private final Mapa mapa;
    private TitledBorder bordeImagenMapa;
    private ArrayList<ArrayList<JTextArea>> casillasImagen;
    private JScrollPane scroll;

    public ImagenMapa(Mapa mapa) {
        this.mapa = mapa;
        initComponentes();
        setupComponentes();
        layoutComponentes();
    }

    public void initComponentes() {
        panel = new JPanel();
        contenido = new JPanel(null);
        bordeImagenMapa = BorderFactory.createTitledBorder("Mapa");
        scroll = new JScrollPane(panel);
        ArrayList<ArrayList<Casilla>> tablero = mapa.getCasillas();

        casillasImagen = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            casillasImagen.add(new ArrayList<>());
            for (int j = 0; j < 11; j++) {
                Casilla casilla = tablero.get(i).get(j);
                if (casilla.getTipoCasilla() != -1) {
                    casillasImagen.get(i).add(new JTextArea("----------\t"));
                } else {
                    String colorCont = casilla.getPais().getContinente().getColor();
                    casillasImagen.get(i).add(new JTextArea(casilla.getPais().getAbreviatura() + "\t"));
                    switch (colorCont){
                        case "AMARILLO":
                        case "Amarillo":
                            // Se usa naranja y no amarillo para que se pueda ver mejor el texto
                            casillasImagen.get(i).get(j).setForeground(java.awt.Color.ORANGE);
                            break;
                        case "AZUL":
                        case "Azul":
                            casillasImagen.get(i).get(j).setForeground(java.awt.Color.BLUE);
                            break;
                        case "CYAN":
                        case "Cyan":
                            casillasImagen.get(i).get(j).setForeground(java.awt.Color.CYAN);
                            break;
                        case "ROJO":
                        case "Rojo":
                            casillasImagen.get(i).get(j).setForeground(java.awt.Color.RED);
                            break;
                        case "VERDE":
                        case "Verde":
                            casillasImagen.get(i).get(j).setForeground(java.awt.Color.GREEN);
                            break;
                        case "VIOLETA":
                        case "Violeta":
                            casillasImagen.get(i).get(j).setForeground(java.awt.Color.MAGENTA);
                            break;
                    }
                }
                casillasImagen.get(i).get(j).setEditable(false);
            }
        }
    }

    public void setupComponentes() {
        panel.setPreferredSize(new Dimension(780, 435));
        panel.setBorder(bordeImagenMapa);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(20, 20, 740, 420);
        contenido.setPreferredSize( new Dimension(780, 435));
        contenido.setMinimumSize(getPreferredSize());

        setBackground(java.awt.Color.orange);
    }

    public void layoutComponentes() {
        add(contenido);
        contenido.add(scroll);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < 8; i++) {
            restricciones.gridx = i;
            for (int j = 0; j < 11; j++) {
                restricciones.gridy = j;
                panel.add(casillasImagen.get(i).get(j), restricciones);
            }
        }
    }
}
