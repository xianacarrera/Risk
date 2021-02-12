package risk.gui;

import javax.swing.*;
import java.awt.*;

public class cambiarCartas {
    private JButton botonCambiarCartas;
    private JButton botonCambiarCartasTodas;
    private JButton botonCambiarCartasManual;

    private JButton botonOkCartas;

    private JComboBox subtipoCarta1;
    private JComboBox subtipoCarta2;
    private JComboBox subtipoCarta3;

    Font font = new Font("Agency FB",Font.BOLD,30);

    public cambiarCartas(Cuadrante C) {
        botonCambiarCartas = new JButton("Cambiar cartas");
        botonCambiarCartas.setFont(font);

        botonCambiarCartasTodas = new JButton("Cambiar cartas todas");
        botonCambiarCartasTodas.setFont(font);

        botonCambiarCartasManual = new JButton("Cambiar cartas manualmente");
        botonCambiarCartasManual.setFont(font);

        botonOkCartas = new JButton("Ok");
        botonOkCartas.setFont(font);

        subtipoCarta1 = new JComboBox(Opciones.subtiposCartas);
        subtipoCarta1.setFont(font);

        subtipoCarta2 = new JComboBox(Opciones.subtiposCartas);
        subtipoCarta2.setFont(font);

        subtipoCarta3 = new JComboBox(Opciones.subtiposCartas);
        subtipoCarta3.setFont(font);

        C.add(botonCambiarCartas);
        C.add(botonCambiarCartasTodas);
        C.add(botonCambiarCartasManual);
        C.add(botonOkCartas);

        C.add(subtipoCarta1);
        C.add(subtipoCarta2);
        C.add(subtipoCarta3);

        botonCambiarCartasTodas.setVisible(false);
        botonCambiarCartasManual.setVisible(false);
        botonOkCartas.setVisible(false);
        subtipoCarta1.setVisible(false);
        subtipoCarta2.setVisible(false);
        subtipoCarta3.setVisible(false);
    }

    public JButton getBotonCambiarCartasManual(){ return botonCambiarCartasManual; }

    public JButton getBotonCambiarCartas(){ return botonCambiarCartas; }

    public JButton getBotonCambiarCartasTodas(){ return botonCambiarCartasTodas; }

    public JButton getBotonOkCartas(){ return botonOkCartas; }

    public JComboBox getSubtipoCarta1(){ return subtipoCarta1; }

    public JComboBox getSubtipoCarta2(){ return subtipoCarta2; }

    public JComboBox getSubtipoCarta3(){ return subtipoCarta3; }
}
