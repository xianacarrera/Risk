package risk.gui;

import risk.Juego;

import java.awt.*;
import javax.swing.*;


public class Ventana extends JFrame {
    
    //Definir los colores que haran de fondo de cada JPanel
    public static final Color GRIS1 = new Color(204,204,204);   
    public static final Color GRIS2 = new Color(100,210,200);   
    private GridLayout grid = new GridLayout(2,2);
    Font font = new Font("Agency FB",Font.PLAIN,20);

    
    private static tablaJugadores C2;
    private Imagen C1 = new Imagen("tablero.png");;
    private Cuadrante C3;
    private static Salida C4;

    public LayoutFaseInicial layoutFaseInicial;

    // Información sobre el jugador que tiene el turno
    private JLabel labelJugadorActual;
    private JTextField jugadorActual;

    // Botón inicial
    private JButton botonCrearMapa;

    // Botones principales
    private JButton botonInformacion, botonRepartirEjs, botonAtacar, botonRearmar, botonAsignarCarta,
            botonTerminarTurno;

    // Botones con opciones secundarias
    private JButton botonAtacarAuto, botonAtacarManual, botonCambiarCartas, botonCambiarCartasTodas,
            botonCambiarCartasManual;

    // Botones de aceptar introducción
    private JButton botonOk, botonOkRearmar, botonOkAsignarCarta, botonOkRepartirEjs, botonOkAtacar, botonOkCartas;

    // Elecciones
    private JComboBox eleccionInformacion, eleccionPais, eleccionContinente, eleccionJugador;
    private JComboBox pais1, pais2, pais3;
    private JComboBox subtipoCarta1, subtipoCarta2, subtipoCarta3;

    // Introducción manual de texto (por ejemplo, cifras)
    private JTextField introducirTexto, introducirTexto2;

    private ListenerBotones listenerBotones;

    private static Juego juego;

  
    public Ventana()    {
        try {
            juego = new Juego();
        } catch (Exception e){
            e.printStackTrace();
        }
        inicializarFrame();
    }
    
    public void inicializarFrame(){
        
        definirPaneles();

        //El programa acabará cuando se cierre la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //El tamaño inicial de la ventaana
        setPreferredSize(new Dimension(1000,700));
        setTitle("Risk");
        
        //Ajustar al grid
        pack();
        
        //Te situa la ventana
        setLocationByPlatform( true );
        
        //Se muestra la ventana
        setVisible(true);

        addEventHandlers();
    } 
    
    public void definirPaneles(){
            
        setLayout(grid);


        // Panel de Widgets (C3) **********************************************


        C3=new Cuadrante();
        add(C3);
        
        botonCrearMapa = new JButton("Crear mapa");
        botonCrearMapa.setFont(font);
        C3.add(botonCrearMapa);
                
        botonCrearMapa.setVisible(true);
        layoutFaseInicial = new LayoutFaseInicial(C3);

 
        // Mapa (C1) **********************************************************
        
        add(C1);
        C1.setVisible(false);

        
        // Tabla (C2) *********************************************************
        
        String[] Jug={"Paises","Gondorff", "Lonnegan", "Hooker"};

        C2=new tablaJugadores(this.getJuego());
        add(C2);

        
        // Salida (C4) *************************************************************
        
        C4 = new Salida();
        add(C4);
        
        
    }
    
    public void addEventHandlers(){
        listenerBotones = new ListenerBotones(this);

        botonCrearMapa.addActionListener(listenerBotones);
        layoutFaseInicial.getBotonPrincipal().addActionListener(listenerBotones);
        layoutFaseInicial.getDesdeFichero().addActionListener(listenerBotones);
        layoutFaseInicial.getManualmente().addActionListener(listenerBotones);
        layoutFaseInicial.getAnhadir().addActionListener(listenerBotones);
        layoutFaseInicial.getTerminar().addActionListener(listenerBotones);
    }

    public void iniciarPartida(){
        Label avisoInicio = new Label("Risk", JLabel.CENTER);
        C3.add(avisoInicio);
        this.revalidate();

        definirComponentes();
        setupComponentes();
        configurarLayout();
        prepararListenersJuego();
    }

    public void definirComponentes(){

        font = new Font("Agency FB",Font.PLAIN,15);

        labelJugadorActual = new JLabel("Jugador actual");
        jugadorActual = new JTextField(20);
        jugadorActual.setFont(font);

        botonInformacion = new JButton("Información");
        botonInformacion.setFont(font);

        botonOk = new JButton("Ok");
        botonOk.setFont(font);

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

        botonAtacar = new JButton("Atacar");
        botonAtacar.setFont(font);

        botonRearmar = new JButton("Rearmar");
        botonRearmar.setFont(font);

        botonAsignarCarta = new JButton("Asignar carta");
        botonAsignarCarta.setFont(font);

        botonOkAtacar = new JButton("Ok");
        botonOkAtacar.setFont(font);

        botonTerminarTurno = new JButton("Terminar turno");
        botonTerminarTurno.setFont(font);

        botonOkRearmar = new JButton("Ok");
        botonOkRearmar.setFont(font);

        botonOkAsignarCarta = new JButton("Ok");
        botonOkAsignarCarta.setFont(font);

        botonAtacarAuto = new JButton("Aleatorio");
        botonAtacarAuto.setFont(font);

        botonAtacarManual = new JButton("Trucar dados");
        botonAtacarManual.setFont(font);

        botonRepartirEjs = new JButton("Repartir ejércitos");
        botonRepartirEjs.setFont(font);

        botonOkRepartirEjs = new JButton("Ok");
        botonOkRepartirEjs.setFont(font);

        eleccionInformacion = new JComboBox(Opciones.informacion);
        eleccionInformacion.setFont(font);

        eleccionPais = new JComboBox(Opciones.paises);
        eleccionPais.setFont(font);

        eleccionContinente = new JComboBox(Opciones.continentes);
        eleccionContinente.setFont(font);

        Opciones opciones = new Opciones(this);         // Necesario inicializar para poder usar Juego como atributo
        eleccionJugador = new JComboBox(opciones.obtenerNombresJugadores());
        eleccionJugador.setFont(font);

        introducirTexto = new JTextField(20);
        introducirTexto.setFont(font);

        introducirTexto2 = new JTextField(20);
        introducirTexto2.setFont(font);

        pais1 = new JComboBox(Opciones.paises);
        pais1.setFont(font);

        pais2 = new JComboBox(Opciones.paises);
        pais2.setFont(font);

        pais3 = new JComboBox(Opciones.paises);
        pais3.setFont(font);
    }

    public void setupComponentes(){

        jugadorActual.setText(juego.jugadorActual().getNombre());

        eleccionInformacion.setVisible(false);
        eleccionPais.setVisible(false);
        eleccionContinente.setVisible(false);
        eleccionJugador.setVisible(false);
        botonOk.setVisible(false);

        pais1.setVisible(false);
        pais2.setVisible(false);
        pais3.setVisible(false);
        botonOkAtacar.setVisible(false);
        botonOkRearmar.setVisible(false);
        botonOkAsignarCarta.setVisible(false);
        botonAtacarAuto.setVisible(false);
        botonAtacarManual.setVisible(false);
        botonOkRepartirEjs.setVisible(false);

        botonCambiarCartasTodas.setVisible(false);
        botonCambiarCartasManual.setVisible(false);
        botonOkCartas.setVisible(false);
        subtipoCarta1.setVisible(false);
        subtipoCarta2.setVisible(false);
        subtipoCarta3.setVisible(false);

        introducirTexto.setEditable(true);
        introducirTexto.setVisible(false);
        introducirTexto2.setEditable(true);
        introducirTexto2.setVisible(false);

        //Crea la tabla
        C2.listaJugadores();
        C2.listaPaises();
        C2.inicializarDatos();
        C2.asignarDatosTabla();
        C2.crearTabla();
        C2.setPartidaIniciada();
    }

    public void prepararListenersJuego(){
        botonInformacion.addActionListener(listenerBotones);
        botonCambiarCartas.addActionListener(listenerBotones);
        botonCambiarCartasTodas.addActionListener(listenerBotones);
        botonCambiarCartasManual.addActionListener(listenerBotones);
        botonAtacar.addActionListener(listenerBotones);
        botonRearmar.addActionListener(listenerBotones);
        botonAsignarCarta.addActionListener(listenerBotones);
        botonTerminarTurno.addActionListener(listenerBotones);
        botonOkAtacar.addActionListener(listenerBotones);
        botonOkRearmar.addActionListener(listenerBotones);
        botonOkAsignarCarta.addActionListener(listenerBotones);
        botonAtacarManual.addActionListener(listenerBotones);
        botonAtacarAuto.addActionListener(listenerBotones);
        botonRepartirEjs.addActionListener(listenerBotones);
        botonOkRepartirEjs.addActionListener(listenerBotones);

        ListenerComboBox listenerComboBox = new ListenerComboBox(this);
        eleccionInformacion.addItemListener(listenerComboBox);
        botonOk.addActionListener(listenerBotones);
        botonOkCartas.addActionListener(listenerBotones);
    }

    public void prepararIntroduccionPais(){
        eleccionContinente.setVisible(false);
        eleccionJugador.setVisible(false);
        eleccionPais.setVisible(true);
        botonOk.setVisible(true);
    }

    public void prepararIntroduccionContinente(){
        eleccionPais.setVisible(false);
        eleccionJugador.setVisible(false);
        eleccionContinente.setVisible(true);
        botonOk.setVisible(true);
    }

    public void prepararIntroduccionJugador(){
        eleccionContinente.setVisible(false);
        eleccionPais.setVisible(false);
        eleccionJugador.setVisible(true);
        botonOk.setVisible(true);
    }

    public void configurarLayout(){
        C3.removeAll();
        C3.setLayout(new GridBagLayout());      // Más eficiente a la hora de manejar botones
        GridBagConstraints restricciones = new GridBagConstraints();        // restricciones del layout

        restricciones.weightx = 1;
        restricciones.weighty = 0;

        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        C3.add(labelJugadorActual, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 0;
        C3.add(jugadorActual, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 1;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        C3.add(botonInformacion, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 1;
        C3.add(eleccionInformacion, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 2;
        C3.add(botonCambiarCartas, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 2;
        C3.add(botonCambiarCartasTodas, restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 2;
        C3.add(botonCambiarCartasManual, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 3;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        C3.add(botonRepartirEjs, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 4;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        C3.add(botonAtacar, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 4;
        C3.add(botonAtacarAuto, restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 4;
        C3.add(botonAtacarManual, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 5;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        C3.add(botonRearmar, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 6;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        C3.add(botonAsignarCarta, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 7;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        C3.add(botonTerminarTurno, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 8;
        C3.add(eleccionContinente, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 8;
        C3.add(eleccionPais, restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 8;
        C3.add(eleccionJugador, restricciones);

        restricciones.gridx = 3;
        restricciones.gridy = 8;
        C3.add(introducirTexto, restricciones);

        restricciones.gridx = 4;
        restricciones.gridy = 8;
        C3.add(introducirTexto2, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 9;
        C3.add(pais1, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 9;
        C3.add(pais2, restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 9;
        C3.add(pais3, restricciones);

        restricciones.gridx = 3;
        restricciones.gridy = 9;
        C3.add(subtipoCarta1, restricciones);

        restricciones.gridx = 4;
        restricciones.gridx = 9;
        C3.add(subtipoCarta2, restricciones);

        restricciones.gridx = 5;
        restricciones.gridy = 9;
        C3.add(subtipoCarta3, restricciones);

        restricciones.gridx = 0;
        restricciones.gridy = 10;
        C3.add(botonOk, restricciones);

        restricciones.gridx = 1;
        restricciones.gridy = 10;
        C3.add(botonOkCartas, restricciones);

        restricciones.gridx = 2;
        restricciones.gridy = 10;
        C3.add(botonOkRepartirEjs, restricciones);

        restricciones.gridx = 3;
        restricciones.gridy = 10;
        C3.add(botonOkAtacar, restricciones);

        restricciones.gridx = 4;
        restricciones.gridy = 10;
        C3.add(botonOkRearmar, restricciones);

        restricciones.gridx = 5;
        restricciones.gridy = 10;
        C3.add(botonOkAsignarCarta, restricciones);

    }

    public void limpiar(){

        botonAtacarAuto.setVisible(false);
        botonAtacarManual.setVisible(false);
        botonCambiarCartasTodas.setVisible(false);
        botonCambiarCartasManual.setVisible(false);

        botonOk.setVisible(false);
        botonOkRearmar.setVisible(false);
        botonOkAsignarCarta.setVisible(false);
        botonOkRepartirEjs.setVisible(false);
        botonOkAtacar.setVisible(false);
        botonOkCartas.setVisible(false);

        eleccionInformacion.setVisible(false);
        eleccionPais.setVisible(false);
        eleccionContinente.setVisible(false);
        eleccionJugador.setVisible(false);

        pais1.setVisible(false);
        pais2.setVisible(false);
        pais3.setVisible(false);

        subtipoCarta1.setVisible(false);
        subtipoCarta2.setVisible(false);
        subtipoCarta3.setVisible(false);

        introducirTexto.setVisible(false);
        introducirTexto2.setVisible(false);

        revalidate();
    }

    public static void mostrarTexto(String out){

        C4.setTextoZonaTexto(out);
        if(C2.getPartidaIniciada()==1){
            C2.modificarTabla();
        }
    }


    //Getters de los botones
    public JButton getBotonCrearMapa(){
        return botonCrearMapa;
    }

    public JButton getBotonInformacion(){ return botonInformacion; }

    public JComboBox getEleccionInformacion(){ return eleccionInformacion; }

    public JComboBox getEleccionPais(){ return eleccionPais; }

    public JComboBox getEleccionContinente(){ return eleccionContinente; }

    public JComboBox getEleccionJugador() { return eleccionJugador; }


    public JButton getBotonOk(){ return botonOk; }

    public JButton getBotonTerminarTurno(){ return botonTerminarTurno; }

    public JComboBox getPais1(){ return pais1; }
    public JComboBox getPais2(){ return pais2; }
    public JComboBox getPais3(){ return pais3; }

    public JButton getBotonAtacar(){ return botonAtacar; }

    public Juego getJuego(){ return juego; }

    public JButton getBotonRearmar(){ return botonRearmar; }

    public JButton getBotonOkRearmar(){ return botonOkRearmar; }

    public JButton getBotonOkAtacar(){ return botonOkAtacar; }

    public JButton getBotonAsignarCarta(){ return botonAsignarCarta; }

    public JButton getBotonOkAsignarCarta(){ return botonOkAsignarCarta; }

    public JButton getBotonAtacarAuto() { return botonAtacarAuto; }

    public JButton getBotonAtacarManual(){ return botonAtacarManual; }

    public JButton getBotonRepartirEjs(){ return botonRepartirEjs; }

    public JButton getBotonOkRepartirEjs(){
        return botonOkRepartirEjs;
    }

    public JButton getBotonCambiarCartas(){
        return botonCambiarCartas;
    }

    public JButton getBotonCambiarCartasManual(){
        return botonCambiarCartasManual;
    }

    public JButton getBotonCambiarCartasTodas(){
        return botonCambiarCartasTodas;
    }

    public JButton getBotonOkCartas(){
        return botonOkCartas;
    }

    public JComboBox getSubtipoCarta1(){
        return subtipoCarta1;
    }

    public JComboBox getSubtipoCarta2(){
        return subtipoCarta2;
    }

    public JComboBox getSubtipoCarta3(){
        return subtipoCarta3;
    }

    public LayoutFaseInicial getLayoutFaseInicial(){return layoutFaseInicial;}

    public tablaJugadores getC2(){ return C2; }

    public Imagen getC1(){ return C1; }

    public JTextField getIntroducirTexto(){
        return introducirTexto;
    }

    public JTextField getIntroducirTexto2(){
        return introducirTexto2;
    }

    public JTextField getJugadorActual(){
        return jugadorActual;
    }

    public Salida getC4(){ return C4; }
}



