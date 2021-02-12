package risk.gui;

import risk.ConsolaNormal;
import risk.Juego;
import risk.Menu;
import risk.Turnos;
import risk.excepciones.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ListenerBotones implements ActionListener {

    private final Ventana ventanaRisk;
    private Juego juego;
    private Menu menu;
    private int fase;
    private ConsolaNormal consolaNormal;
    private Turnos turnos;
    private Salida salida;
    /*
     * fase = 1 --> crear jugadores
     * fase = 2 --> asignar misiones
     * fase = 3 --> asignar países
     * fase = 4 --> repartir ejércitos
     * fase = 5 --> juego
     */

    public ListenerBotones(Ventana ventana) {
        ventanaRisk = ventana;
        juego = ventana.getJuego();
        turnos = new Turnos();
        salida = ventana.getC4();
        try {
            menu = new Menu();
            consolaNormal = new ConsolaNormal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        try {

            if (evento.getSource().equals(ventanaRisk.getBotonCrearMapa())) {

                //Se crea el mapa
                juego.crearMapa();

                // El botón deja de funcionar
                ventanaRisk.getBotonCrearMapa().setVisible(false);

                //Se muestra el cuadrante con el mapa y las respectivas labels
                ventanaRisk.getC1().setVisible(true);

                // Se muestra el botón de crear jugadores
                ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setVisible(true);

                ventanaRisk.revalidate();       // Se recarga la ventana

                fase = 1;

                salida.setTextoZonaTexto("$> crear mapa\n");

            } else if (evento.getSource().equals(ventanaRisk.getLayoutFaseInicial().getBotonPrincipal())) {

                ventanaRisk.getLayoutFaseInicial().getDesdeFichero().setVisible(true);
                if (fase == 5) {   // Repartir ejércitos
                    ventanaRisk.getLayoutFaseInicial().getDesdeFichero().setText("Reparto automático");
                }
                ventanaRisk.getLayoutFaseInicial().getManualmente().setVisible(true);

                ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setEnabled(false);
                ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setBackground(Color.DARK_GRAY);

                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getLayoutFaseInicial().getDesdeFichero())) {
                ventanaRisk.getLayoutFaseInicial().getTerminar().setVisible(true);
                ventanaRisk.revalidate();

                switch (fase) {
                    case 1:
                        salida.setTextoZonaTexto("\n$> crear jugadores\n");
                        menu.crearJugadores(new File("jugadores.csv"));
                        break;
                    case 2:
                        salida.setTextoZonaTexto("\n$> asignar misiones\n");
                        menu.asignarMisiones(new File("misiones.csv"));
                        break;
                    case 3:
                        juego.asignarEjercitosIniciales();
                        fase++;
                    case 4:
                        salida.setTextoZonaTexto("\n$> asignar paises\n");
                        menu.asignarPaises(new File("paises.csv"));
                        break;
                    case 5:
                        salida.setTextoZonaTexto("\n$> repartir ejercitos\n");
                        juego.repartirEjercitosAut();
                }

            } else if (evento.getSource().equals(ventanaRisk.getLayoutFaseInicial().getManualmente())) {
                ventanaRisk.getLayoutFaseInicial().getUserPrompt1().setVisible(true);
                ventanaRisk.getLayoutFaseInicial().getUserPrompt2().setVisible(true);
                ventanaRisk.getLayoutFaseInicial().getTextField1().setVisible(true);
                ventanaRisk.getLayoutFaseInicial().getTextField2().setVisible(true);
                ventanaRisk.getLayoutFaseInicial().getAnhadir().setVisible(true);
                ventanaRisk.getLayoutFaseInicial().getTerminar().setVisible(true);

                switch (fase) {
                    case 2:
                        ventanaRisk.getLayoutFaseInicial().getUserPrompt1().setText("Introducir nombre de jugador: ");
                        ventanaRisk.getLayoutFaseInicial().getUserPrompt2().setText("Introducir id de misión: ");
                        break;
                    case 3:
                    case 4:
                        ventanaRisk.getLayoutFaseInicial().getUserPrompt2().setText("Introducir abreviatura de país: ");
                        break;
                    case 5:
                        ventanaRisk.getLayoutFaseInicial().getUserPrompt1().
                                setText("Introducir número de ejércitos a colocar: ");
                        ventanaRisk.getLayoutFaseInicial().getUserPrompt2().setText("Introdir abreviatura de país: ");

                }
                ventanaRisk.revalidate();


            } else if (evento.getSource().equals(ventanaRisk.getLayoutFaseInicial().getAnhadir())) {
                String entrada1 = ventanaRisk.getLayoutFaseInicial().getTextField1().getText();
                String entrada2 = ventanaRisk.getLayoutFaseInicial().getTextField2().getText();

                ventanaRisk.getLayoutFaseInicial().getTextField1().setText("");
                ventanaRisk.getLayoutFaseInicial().getTextField2().setText("");

                switch (fase) {
                    case 1:
                        salida.setTextoZonaTexto("\n$> crear jugador\n");
                        juego.crearJugador(entrada1, entrada2);
                        break;
                    case 2:
                        salida.setTextoZonaTexto("\n$> asignar mision\n");
                        juego.asignarMision(entrada1, entrada2);
                        break;
                    case 3:
                        juego.asignarEjercitosIniciales();
                        fase++;
                    case 4:
                        salida.setTextoZonaTexto("\n$> asignar pais\n");
                        juego.asignarPais(entrada1, entrada2);
                        break;
                    case 5:
                        salida.setTextoZonaTexto("\n$> repartir ejercitos\n");
                        juego.repartirEjercitos(juego.jugadorActual(), Integer.parseInt(entrada1), entrada2,
                                true);

                        ventanaRisk.getLayoutFaseInicial().getJugadorActual().setText(juego.jugadorActual().getNombre());

                }
            } else if (evento.getSource().equals(ventanaRisk.getLayoutFaseInicial().getTerminar())) {
                switch (fase) {
                    case 1:
                        // Se comprueba si el número de jugadores es adecuado. Si no, se genera una excepción
                        juego.comprobarJugadores();
                        ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setText("Asignar misiones");
                        break;
                    case 2:
                        if (turnos.getFaseInicial() == 4) {         // Se ha asignado misión a cada jugador
                            ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setText("Asignar paises");
                            juego.asignarEjercitosIniciales();
                        } else {
                            throw new ExcepcionMision(consolaNormal.mensajeError(118));
                        }
                        break;
                    case 4:
                        if (turnos.todosPaisesAsignados()) {
                            ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setText("Repartir ejércitos");
                            ventanaRisk.getLayoutFaseInicial().getJugadorActual().setVisible(true);
                            ventanaRisk.getLayoutFaseInicial().getJugadorActual().setText(juego.
                                    jugadorActual().getNombre());
                        } else {
                            throw new ExcepcionComando(consolaNormal.mensajeError(99));
                        }
                        break;
                    case 5:
                        salida.setTextoZonaTexto("\n$> acabar turno\n");
                        juego.acabarTurno();            // Empieza la partida automáticamente si es necesario
                        // No se comprueba el jugador del turno ha repartido ejércitos (puede "pasar" sin hacer nada)
                        if (!turnos.juegoEmpezado()) {
                            ventanaRisk.getLayoutFaseInicial().getJugadorActual().setText(juego.
                                    jugadorActual().getNombre());
                            return;
                        }

                        ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setVisible(false);
                        ventanaRisk.revalidate();
                        ventanaRisk.iniciarPartida();
                }
                fase++;

                ventanaRisk.getLayoutFaseInicial().getDesdeFichero().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getManualmente().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getTextField1().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getTextField2().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getUserPrompt1().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getUserPrompt2().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getAnhadir().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getTerminar().setVisible(false);
                ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setEnabled(true);
                ventanaRisk.getLayoutFaseInicial().getBotonPrincipal().setBackground(Color.WHITE);

                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonRepartirEjs())) {
                ventanaRisk.limpiar();

                ventanaRisk.getIntroducirTexto().setVisible(true);
                ventanaRisk.getPais1().setVisible(true);
                ventanaRisk.getBotonOkRepartirEjs().setVisible(true);
                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonOkRepartirEjs())) {
                ventanaRisk.limpiar();

                salida.setTextoZonaTexto("\n$> repartir ejercitos\n");
                if (turnos.sePuedeRepartirEjercitosTurno()) {
                    juego.repartirEjercitos(juego.jugadorActual(),
                            Integer.parseInt(ventanaRisk.getIntroducirTexto().getText()),
                            (String) ventanaRisk.getPais1().getSelectedItem(),
                            true);
                } else {
                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                }

            } else if (evento.getSource().equals(ventanaRisk.getBotonInformacion())) {
                ventanaRisk.limpiar();
                ventanaRisk.getEleccionInformacion().setVisible(true);
                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonCambiarCartas())) {
                ventanaRisk.limpiar();
                ventanaRisk.getBotonCambiarCartasTodas().setVisible(true);
                ventanaRisk.getBotonCambiarCartasManual().setVisible(true);
                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonCambiarCartasTodas())) {
                ventanaRisk.limpiar();
                salida.setTextoZonaTexto("\n$> cambiar cartas todas\n");
                juego.cambiarCartasTodas();

            } else if (evento.getSource().equals(ventanaRisk.getBotonCambiarCartasManual())) {
                ventanaRisk.limpiar();
                ventanaRisk.getSubtipoCarta1().setVisible(true);
                ventanaRisk.getSubtipoCarta2().setVisible(true);
                ventanaRisk.getSubtipoCarta3().setVisible(true);
                ventanaRisk.getPais1().setVisible(true);
                ventanaRisk.getPais2().setVisible(true);
                ventanaRisk.getPais3().setVisible(true);
                ventanaRisk.getBotonOkCartas().setVisible(true);
                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonAtacar())) {
                ventanaRisk.limpiar();
                ventanaRisk.getBotonAtacarAuto().setVisible(true);
                ventanaRisk.getBotonAtacarManual().setVisible(true);
                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonAtacarAuto())) {
                ventanaRisk.limpiar();
                ventanaRisk.getPais1().setVisible(true);
                ventanaRisk.getPais2().setVisible(true);
                ventanaRisk.getBotonOkAtacar().setVisible(true);
                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonAtacarManual())) {
                ventanaRisk.limpiar();
                ventanaRisk.getPais1().setVisible(true);
                ventanaRisk.getPais2().setVisible(true);
                ventanaRisk.getIntroducirTexto().setVisible(true);
                ventanaRisk.getIntroducirTexto2().setVisible(true);
                ventanaRisk.getBotonOkAtacar().setVisible(true);
                ventanaRisk.revalidate();

            } else if (evento.getSource().equals(ventanaRisk.getBotonOk())) {
                String eleccionInfo = (String) ventanaRisk.getEleccionInformacion().getSelectedItem();
                String abrevPais = null;
                String abrevCont = null;
                String nombreJugador = null;

                ventanaRisk.limpiar();

                switch (eleccionInfo) {
                    case "obtener frontera":
                        salida.setTextoZonaTexto("\n$> obtener frontera\n");
                        abrevPais = (String) ventanaRisk.getEleccionPais().getSelectedItem();
                        juego.obtenerFrontera(abrevPais);
                        break;
                    case "obtener continente":
                        salida.setTextoZonaTexto("\n$> obtener continente\n");
                        abrevPais = (String) ventanaRisk.getEleccionPais().getSelectedItem();
                        juego.obtenerContinente(abrevPais);
                        break;
                    case "obtener color":
                        salida.setTextoZonaTexto("\n$> obtener color\n");
                        abrevPais = (String) ventanaRisk.getEleccionPais().getSelectedItem();
                        juego.obtenerColor(abrevPais);
                        break;
                    case "obtener paises":
                        salida.setTextoZonaTexto("\n$> obtener paises\n");
                        abrevCont = (String) ventanaRisk.getEleccionContinente().getSelectedItem();
                        juego.obtenerPaises(abrevCont);
                        break;
                    case "describir jugador":
                        salida.setTextoZonaTexto("\n$> describir jugador\n");
                        nombreJugador = (String) ventanaRisk.getEleccionJugador().getSelectedItem();
                        juego.describirJugador(nombreJugador);
                        break;
                    case "describir pais":
                        salida.setTextoZonaTexto("\n$> describir pais\n");
                        abrevPais = (String) ventanaRisk.getEleccionPais().getSelectedItem();
                        juego.describirPais(abrevPais);
                        break;
                    case "describir continente":
                        salida.setTextoZonaTexto("\n$> describir continente\n");
                        abrevCont = (String) ventanaRisk.getEleccionContinente().getSelectedItem();
                        juego.describirContinente(abrevCont);
                }

            } else if (evento.getSource().equals(ventanaRisk.getBotonOkCartas())) {
                ventanaRisk.limpiar();

                String idCarta1 = null;
                String idCarta2 = null;
                String idCarta3 = null;

                idCarta1 = (String) ventanaRisk.getSubtipoCarta1().getSelectedItem();
                idCarta1 += (String) ventanaRisk.getPais1().getSelectedItem();
                idCarta2 = (String) ventanaRisk.getSubtipoCarta2().getSelectedItem();
                idCarta2 += (String) ventanaRisk.getPais2().getSelectedItem();
                idCarta3 = (String) ventanaRisk.getSubtipoCarta3().getSelectedItem();
                idCarta3 += (String) ventanaRisk.getPais3().getSelectedItem();

                salida.setTextoZonaTexto("\n$> cambiar cartas\n");
                if (turnos.sePuedeCambiarCartas()) {
                    juego.cambiarCartas(idCarta1, idCarta2, idCarta3);
                } else {
                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                }

            } else if (evento.getSource().equals(ventanaRisk.getBotonOkAtacar())) {
                salida.setTextoZonaTexto("\n$> atacar\n");
                if (turnos.sePuedeAtacar()) {
                    if (ventanaRisk.getIntroducirTexto().isVisible()) {
                        ventanaRisk.limpiar();
                        juego.atacarMan((String) ventanaRisk.getPais1().getSelectedItem(),
                                ventanaRisk.getIntroducirTexto().getText(),
                                (String) ventanaRisk.getPais2().getSelectedItem(),
                                ventanaRisk.getIntroducirTexto2().getText());
                    } else {
                        ventanaRisk.limpiar();
                        juego.atacar((String) ventanaRisk.getPais1().getSelectedItem(),
                                (String) ventanaRisk.getPais2().getSelectedItem());
                    }
                } else {
                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                }
            } else if (evento.getSource().equals(ventanaRisk.getBotonRearmar())) {
                ventanaRisk.limpiar();
                ventanaRisk.getPais1().setVisible(true);
                ventanaRisk.getIntroducirTexto().setVisible(true);
                ventanaRisk.getPais2().setVisible(true);
                ventanaRisk.getBotonOkRearmar().setVisible(true);

            } else if (evento.getSource().equals(ventanaRisk.getBotonOkRearmar())) {
                ventanaRisk.limpiar();

                salida.setTextoZonaTexto("\n$> rearmar\n");
                if (turnos.sePuedeRearmar()) {
                    juego.rearmar((String) ventanaRisk.getPais1().getSelectedItem(),
                            Integer.parseInt(ventanaRisk.getIntroducirTexto().getText()),
                            (String) ventanaRisk.getPais2().getSelectedItem());
                } else {
                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                }

            } else if (evento.getSource().equals(ventanaRisk.getBotonAsignarCarta())) {
                ventanaRisk.limpiar();
                ventanaRisk.getSubtipoCarta1().setVisible(true);
                ventanaRisk.getPais1().setVisible(true);
                ventanaRisk.getBotonOkAsignarCarta().setVisible(true);

            } else if (evento.getSource().equals(ventanaRisk.getBotonOkAsignarCarta())) {
                ventanaRisk.limpiar();

                salida.setTextoZonaTexto("\n$> asignar carta\n");
                if (turnos.sePuedeAsignarCarta()) {
                    juego.asignarCarta(ventanaRisk.getSubtipoCarta1().getSelectedItem() + "&" +
                            ventanaRisk.getPais1().getSelectedItem());
                } else {
                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                }

            } else if (evento.getSource().equals(ventanaRisk.getBotonTerminarTurno())) {
                ventanaRisk.limpiar();
                salida.setTextoZonaTexto("\n$> acabar turno\n");
                if (turnos.sePuedeAcabarTurno()) {
                    juego.acabarTurno();
                } else {
                    throw new ExcepcionComando(consolaNormal.mensajeError(99));
                }
                ventanaRisk.getJugadorActual().setText(juego.jugadorActual().getNombre());
            }

        } catch (ExcepcionGeo | ExcepcionCarta | ExcepcionJugador | ExcepcionMision | ExcepcionComando excepcion) {
            consolaNormal.imprimir(excepcion.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
