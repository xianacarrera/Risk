package risk.guiMapa;

import risk.Mapa;
import risk.gui.Ventana;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class PanelOpciones extends JPanel {

    private VentanaEdicionMapa ventanaEdicionMapa;

    private JPanel panel;
    private JTextField texto, resultado, error;
    private JTextField introducirNombrePais, introducirAbrevPais;
    private JTextField textFieldPais;
    private ArrayList<JTextField> continentes;
    private ArrayList<JTextField> abrevContinentes;
    private ArrayList<JComboBox> colorescontinentes;
    private JLabel casilla1, casilla2;
    private TitledBorder bordePanelOpciones;
    private JComboBox fila1, columna1, fila2, columna2, comboBoxContinentes;
    private JButton botonOk, botonOk2, botonLecturaManual, botonLecturaAuto, botonOkPais, botonOkAsignar;

    private ControlBotones controlBotones;

    private static final String[] filas = {"0", "1", "2", "3", "4", "5", "6", "7"};
    private static final String[] columnas = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] colores = {"Amarillo", "Azul", "Cyan", "Rojo", "Verde", "Violeta"};

    public PanelOpciones(VentanaEdicionMapa ventanaEdicionMapa){
        this.ventanaEdicionMapa = ventanaEdicionMapa;
        setLayout(new GridBagLayout());
        initComponents();
        setupComponents();
        layoutComponents();
        addEventHandlers();
    }

    public void initComponents(){
        bordePanelOpciones = BorderFactory.createTitledBorder("Opciones para componentes");
        botonOk = new JButton("Ok");
        fila1 = new JComboBox(filas);
        columna1 = new JComboBox(columnas);
        fila2 = new JComboBox(filas);
        columna2 = new JComboBox(columnas);
        texto = new JTextField("Seleccionar 5 pares de casillas con fronteras indirectas");
        resultado = new JTextField("Error. Las casillas no pueden ser adyacentes");
        error = new JTextField("Error. La frontera ya ha sido introducida");
        casilla1 = new JLabel("Casilla 1 (fila, columna)");
        casilla2 = new JLabel("Casilla 2 (fila, columna)");

        continentes = new ArrayList<>();
        continentes.add(new JTextField("Continente de 4 países"));
        continentes.add(new JTextField("Continente de 4 países"));
        continentes.add(new JTextField("Continente de 6 países"));
        continentes.add(new JTextField("Continente de 7 países"));
        continentes.add(new JTextField("Continente de 9 países"));
        continentes.add(new JTextField("Continente de 12 países"));

        abrevContinentes = new ArrayList<>();
        colorescontinentes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            abrevContinentes.add(new JTextField(20));
            colorescontinentes.add(new JComboBox(colores));
        }
        botonOk2 = new JButton("Ok");

        botonLecturaManual = new JButton("Introducir países manualmente");
        botonLecturaAuto = new JButton("Leer paises desde fichero paisesEdicionMapa.txt");
        introducirNombrePais = new JTextField(20);
        introducirAbrevPais = new JTextField(20);
        botonOkPais = new JButton("Ok");

        botonOkAsignar = new JButton("Ok");

        panel = new JPanel();
    }

    public void setupComponents(){
        panel.setBorder(bordePanelOpciones);
        panel.setPreferredSize(new Dimension(400, 450));
        resultado.setVisible(false);
        texto.setEditable(false);
        error.setVisible(false);
        error.setEditable(false);
        resultado.setEditable(false);

        for (int i = 0; i < 6; i++){
            continentes.get(i).setEditable(false);
        }
    }

    public void layoutComponents(){
        panel.setLayout(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.gridx = 0;
        restricciones.gridy = 0;
        panel.add(texto, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 1;
        panel.add(casilla1, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 1;
        panel.add(fila1, restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 1;
        panel.add(columna1, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 2;
        panel.add(casilla2, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 2;
        panel.add(fila2, restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 2;
        panel.add(columna2, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 3;
        panel.add(botonOk, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 4;
        panel.add(resultado, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 5;
        panel.add(error, restricciones);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
    }

    public void addEventHandlers(){
        controlBotones = new ControlBotones(this);
        botonOk.addActionListener(controlBotones);
        botonOk2.addActionListener(controlBotones);
        botonLecturaAuto.addActionListener(controlBotones);
        botonLecturaManual.addActionListener(controlBotones);
        botonOkPais.addActionListener(controlBotones);
        botonOkAsignar.addActionListener(controlBotones);
    }

    public void obtenerContinentes(){
        panel.removeAll();
        panel.repaint();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.weightx = 1;
        restricciones.weighty = 0;

        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.gridwidth = 3;
        texto.setText("Introducir datos sobre los 6 continentes (abreviatura, color): ");
        panel.add(texto, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 1;
        restricciones.gridwidth = 1;
        panel.add(continentes.get(0), restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 1;
        panel.add(abrevContinentes.get(0), restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 1;
        panel.add(colorescontinentes.get(0), restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 2;
        panel.add(continentes.get(1), restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 2;
        panel.add(abrevContinentes.get(1), restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 2;
        panel.add(colorescontinentes.get(1), restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 3;
        panel.add(continentes.get(2), restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 3;
        panel.add(abrevContinentes.get(2), restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 3;
        panel.add(colorescontinentes.get(2), restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 4;
        panel.add(continentes.get(3), restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 4;
        panel.add(abrevContinentes.get(3), restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 4;
        panel.add(colorescontinentes.get(3), restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 5;
        panel.add(continentes.get(4), restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 5;
        panel.add(abrevContinentes.get(4), restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 5;
        panel.add(colorescontinentes.get(4), restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 6;
        panel.add(continentes.get(5), restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 6;
        panel.add(abrevContinentes.get(5), restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 6;
        panel.add(colorescontinentes.get(5), restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 7;
        panel.add(botonOk2, restricciones);

        error.setText("Error. Todos los colores deben ser diferentes entre sí");
        restricciones.gridx = 0;
        restricciones.gridy = 8;
        restricciones.gridwidth = 3;
        panel.add(error, restricciones);
        error.setVisible(false);
    }

    public void introducirPaises(){
        // Se eliminan los componentes anteriores
        panel.removeAll();
        panel.repaint();

        // Se colocan los nuevos elementos (elección sobre la introducción de países) en el panel
        panel.setLayout(new GridLayout(2, 1));
        panel.add(botonLecturaManual);
        panel.add(botonLecturaAuto);
        panel.revalidate();
    }

    public void leerPaises(){
        // Se eliminan los componentes anteriores
        panel.removeAll();
        panel.repaint();

        // Se colocan los nuevos elementos
        panel.setLayout(new GridLayout(3, 2));
        casilla1.setText("Intoducir nombre");
        panel.add(casilla1);
        panel.add(introducirNombrePais);
        casilla2.setText("Introducir abreviatura");
        panel.add(casilla2);
        panel.add(introducirAbrevPais);
        panel.add(botonOkPais);
        panel.revalidate();
    }

    public void asignarPaisesConts(){
        panel.removeAll();
        panel.repaint();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.gridx = 0;
        restricciones.gridy = 0;
        texto.setText("Asignar países a sus respectivos continentes");
        panel.add(texto, restricciones);

        ArrayList<String> continentes = controlBotones.getAbrevConts();
        ArrayList<String> paises = controlBotones.getAbrevPaises();
        comboBoxContinentes = new JComboBox(continentes.toArray(new String[0]));
        textFieldPais = new JTextField(30);
        textFieldPais.setText(paises.get(0));
        textFieldPais.setEditable(false);

        restricciones.gridx = 0;
        restricciones.gridy = 1;
        panel.add(textFieldPais, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 1;
        panel.add(comboBoxContinentes, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 2;
        panel.add(botonOkAsignar, restricciones);

        panel.revalidate();
    }

    public void finalizar(){
        panel.removeAll();
        panel.repaint();
        panel.setLayout(new BorderLayout());
        texto.setText("¡Mapa creado!");
        panel.add(texto, BorderLayout.CENTER);
        panel.revalidate();
        ventanaEdicionMapa.verMapaFinal();
    }

    public JComboBox getFila1(){
        return fila1;
    }

    public JComboBox getColumna1(){
        return columna1;
    }

    public JComboBox getFila2(){
        return fila2;
    }

    public JComboBox getColumna2(){
        return columna2;
    }

    public JTextField getResultado(){
        return resultado;
    }

    public ArrayList<JTextField> getContinentes() {
        return continentes;
    }

    public ArrayList<JTextField> getAbrevContinentes() {
        return abrevContinentes;
    }

    public ArrayList<JComboBox> getColorescontinentes() {
        return colorescontinentes;
    }

    public JButton getBotonOk() {
        return botonOk;
    }

    public JButton getBotonOk2() {
        return botonOk2;
    }

    public JButton getBotonLecturaManual(){
        return botonLecturaManual;
    }

    public JButton getBotonLecturaAuto(){
        return botonLecturaAuto;
    }

    public JButton getBotonOkPais(){
        return botonOkPais;
    }

    public JTextField getIntroducirNombrePais(){
        return introducirNombrePais;
    }

    public JTextField getIntroducirAbrevPais(){
        return introducirAbrevPais;
    }

    public JTextField getTextFieldPais() {
        return textFieldPais;
    }

    public JComboBox getComboBoxContinentes() {
        return comboBoxContinentes;
    }

    public JButton getBotonOkAsignar() {
        return botonOkAsignar;
    }

    public Mapa getMapa() { return controlBotones.getMapa(); }

    public JTextField getError(){ return error; }
}
