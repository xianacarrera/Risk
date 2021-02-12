package risk.guiMapa;

import javax.swing.*;
import java.awt.*;

public class VentanaEdicionMapa extends JFrame {

    private JSplitPane split;
    private JLabel titulo;
    private PanelOpciones panelOpciones;
    private GeoMapa geoMapa;
    private ImagenMapa imagenMapa;

    public VentanaEdicionMapa(){
        initComponentes();
        setupComponentes();
        layoutComponentes();
    }

    public void initComponentes(){
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        titulo = new JLabel("Edición de mapas");
        geoMapa = new GeoMapa(this);
        panelOpciones = new PanelOpciones(this);
    }

    public void setupComponentes(){
        setSize(new Dimension(1000, 500));
        split.setLeftComponent(geoMapa);
        split.setRightComponent(panelOpciones);
        panelOpciones.setVisible(false);
    }

    public void layoutComponentes(){
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titulo, BorderLayout.NORTH);
        getContentPane().add(geoMapa, BorderLayout.WEST);
        getContentPane().add(panelOpciones, BorderLayout.EAST);
        getContentPane().add(split);
    }

    public void verMapaFinal(){
        geoMapa.setVisible(false);
        panelOpciones.setVisible(false);
        split.setVisible(false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titulo, BorderLayout.NORTH);
        imagenMapa = new ImagenMapa(panelOpciones.getMapa());
        getContentPane().add(imagenMapa, BorderLayout.CENTER);
    }

    public GeoMapa getGeoMapa(){
        return geoMapa;
    }

    public PanelOpciones getPanelOpciones(){
        return panelOpciones;
    }
}
