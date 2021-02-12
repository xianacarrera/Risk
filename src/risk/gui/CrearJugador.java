package risk.gui;

import javax.swing.*;
import java.awt.*;

public class CrearJugador extends Cuadrante {

    private JButton botonPrincipal;
    private JButton desdeFichero;
    private JButton manualmente;
    private JLabel userPrompt1;
    private JLabel userPrompt2;
    private JTextField textField1;
    private JTextField textField2;
    private JButton anhadir;
    private JButton terminar;
    private JTextField jugadorActual;

    Font font = new Font("Agency FB", Font.BOLD, 30);

    public CrearJugador(Cuadrante C) {

       // C.setLayout(new GridLayout(9,1));

        botonPrincipal = new JButton("Crear jugadores");
        botonPrincipal.setFont(font);
        C.add(botonPrincipal);
        botonPrincipal.setVisible(false);

        desdeFichero = new JButton("Usar fichero");
        desdeFichero.setFont(font);
        C.add(desdeFichero);
        desdeFichero.setVisible(false);

        manualmente = new JButton("Introducir manualmente");
        manualmente.setFont(font);
        C.add(manualmente);
        manualmente.setVisible(false);

        userPrompt1 = new JLabel("Introducir nombre de jugador: ", JLabel.LEADING);
        userPrompt1.setFont(font);
        userPrompt1.setForeground(Color.white);
        C.add(userPrompt1);
        userPrompt1.setVisible(false);

        textField1 = new JTextField(20);
        textField1.setFont(font);
        C.add(textField1);
        textField1.setVisible(false);
        textField1.setEditable(true);

        userPrompt2 = new JLabel("Introducir color de jugador: ", JLabel.LEADING);
        userPrompt2.setFont(font);
        userPrompt2.setForeground(Color.white);
        C.add(userPrompt2);
        userPrompt2.setVisible(false);

        textField2 = new JTextField(20);
        textField2.setFont(font);
        C.add(textField2);
        textField2.setVisible(false);
        textField2.setEditable(true);

        anhadir = new JButton("Anhadir");
        anhadir.setFont(font);
        C.add(anhadir);
        anhadir.setVisible(false);

        terminar = new JButton("Terminar");
        terminar.setFont(font);
        C.add(terminar);
        terminar.setVisible(false);

        jugadorActual = new JTextField();
        jugadorActual.setFont(font);
        C.add(jugadorActual);
        jugadorActual.setVisible(false);

    }

    public JButton getBotonPrincipal() {
        return botonPrincipal;
    }

    public JButton getDesdeFichero() {
        return desdeFichero;
    }

    public JButton getManualmente() {
        return manualmente;
    }

    public JLabel getUserPrompt1() {
        return userPrompt1;
    }

    public JLabel getUserPrompt2() {
        return userPrompt2;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JButton getAnhadir() {
        return anhadir;
    }

    public JButton getTerminar() {
        return terminar;
    }

    public JTextField getJugadorActual() {
        return jugadorActual;
    }
}
