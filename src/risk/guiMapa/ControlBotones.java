package risk.guiMapa;

import risk.Mapa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ControlBotones implements ActionListener {

    private final PanelOpciones panelOpciones;
    private final ArrayList<String> fronterasIndirectas;
    private ArrayList<String> abrevConts;
    private ArrayList<String> coloresConts;
    private ArrayList<String> abrevPaises;
    private ArrayList<String> nombresPaises;
    private ArrayList<String> casillasPaises;
    private HashMap<String, Integer> restantes;
    private final ArrayList<String> paises;
    private int cuenta;
    private Mapa mapa;

    public ControlBotones(PanelOpciones panelOpciones) {
        this.panelOpciones = panelOpciones;
        fronterasIndirectas = new ArrayList<>();
        paises = ListenerCasillas.getPaises();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(panelOpciones.getBotonOk())) {
            panelOpciones.getError().setVisible(false);
            panelOpciones.getResultado().setVisible(false);

            String fila1 = (String) panelOpciones.getFila1().getSelectedItem();
            String fila2 = (String) panelOpciones.getFila2().getSelectedItem();
            String columna1 = (String) panelOpciones.getColumna1().getSelectedItem();
            String columna2 = (String) panelOpciones.getColumna2().getSelectedItem();

            if (!paises.contains(fila1 + " " + columna1) || !paises.contains(fila2 + " " + columna2)){
                panelOpciones.getError().setText("Error. Alguna casilla no fue seleccionada como país");
                panelOpciones.getError().setVisible(true);
                panelOpciones.revalidate();
                return;
            }

            if (columna1.equals(columna2) && (Math.abs(Integer.parseInt(fila1) - Integer.parseInt(fila2)) <= 1) ||
                    fila1.equals(fila2) && (Math.abs(Integer.parseInt(columna1) - Integer.parseInt(columna2)) <= 1)) {
                panelOpciones.getResultado().setText("Error. Las casillas no pueden ser adyacentes");
                panelOpciones.getResultado().setVisible(true);
                panelOpciones.revalidate();
                return;
            }

            String infoFrontera = fila1 + columna1 + fila2 + columna2;
            if (fronterasIndirectas.contains(infoFrontera)){
                panelOpciones.getError().setText("Error. La frontera ya ha sido introducida");
                panelOpciones.getError().setVisible(true);
                panelOpciones.revalidate();
                return;
            }

            fronterasIndirectas.add(fila1 + columna1 + fila2 + columna2);
            panelOpciones.getResultado().setText("Las casillas (" + fila1 + "," + columna1 + ") y (" + fila2 + "," +
                    columna2 + ") han sido conectadas.");
            panelOpciones.getResultado().setVisible(true);
            panelOpciones.revalidate();

            cuenta++;
            if (cuenta == 5) {
                panelOpciones.obtenerContinentes();
            }
        } else if (e.getSource().equals(panelOpciones.getBotonOk2())){
            panelOpciones.getError().setVisible(false);

            abrevConts = new ArrayList<>();
            coloresConts = new ArrayList<>();
            ArrayList<JTextField> continentesUser = panelOpciones.getAbrevContinentes();
            ArrayList<JComboBox> coloresUser = panelOpciones.getColorescontinentes();
            for (int i = 0; i < 6; i++){
                abrevConts.add(continentesUser.get(i).getText());
                String colorTemp = (String) coloresUser.get(i).getSelectedItem();
                if (coloresConts.contains(colorTemp)){
                    panelOpciones.getError().setVisible(true);
                    panelOpciones.revalidate();
                    return;
                }
                coloresConts.add(colorTemp);
            }

            panelOpciones.introducirPaises();

        } else if (e.getSource().equals(panelOpciones.getBotonLecturaAuto())){
            nombresPaises = new ArrayList<>();
            abrevPaises = new ArrayList<>();
            try {
                String linea;
                File fichero = new File("paisesEdicionMapa.txt");
                FileReader lector = new FileReader(fichero);
                BufferedReader bufferLector = new BufferedReader(lector);

                while ((linea = bufferLector.readLine()) != null) {
                    // Se lee, en orden, el nombre del país y su abreviatura
                    String[] infoPais = linea.split(" ");
                    abrevPaises.add(infoPais[0]);
                    nombresPaises.add(infoPais[1]);
                }
                panelOpciones.getBotonLecturaManual().setVisible(false);
                panelOpciones.getBotonLecturaAuto().setVisible(false);
                panelOpciones.asignarPaisesConts();

            } catch (Exception ex){
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(panelOpciones.getBotonLecturaManual())){
            nombresPaises = new ArrayList<>();
            abrevPaises = new ArrayList<>();
            panelOpciones.leerPaises();
            cuenta = 0;
        } else if (e.getSource().equals(panelOpciones.getBotonOkPais())){
            nombresPaises.add(panelOpciones.getIntroducirNombrePais().getText());
            abrevPaises.add(panelOpciones.getIntroducirAbrevPais().getText());
            panelOpciones.getIntroducirAbrevPais().setText("");
            panelOpciones.getIntroducirNombrePais().setText("");

            cuenta++;
            if (cuenta == 42){
                panelOpciones.getBotonLecturaManual().setVisible(false);
                panelOpciones.getBotonLecturaAuto().setVisible(false);
                panelOpciones.revalidate();
                panelOpciones.asignarPaisesConts();
            }
        } else if (e.getSource().equals(panelOpciones.getBotonOkAsignar())){
            if (mapa == null){          // Primera vez que se llama al botón
                try {
                    mapa = new Mapa(abrevConts, coloresConts);
                } catch (Exception eo){
                    eo.printStackTrace();
                }

                restantes = new HashMap<>();
                restantes.put(abrevConts.get(0), 4);
                restantes.put(abrevConts.get(1), 4);
                restantes.put(abrevConts.get(2), 6);
                restantes.put(abrevConts.get(3), 7);
                restantes.put(abrevConts.get(4), 9);
                restantes.put(abrevConts.get(5), 12);
                casillasPaises = ListenerCasillas.getPaises();
                cuenta = 0;
            }
            String abrevPais = panelOpciones.getTextFieldPais().getText();
            String abrevCont = (String) panelOpciones.getComboBoxContinentes().getSelectedItem();
            restantes.replace(abrevCont, restantes.get(abrevCont) - 1);

            String casilla = casillasPaises.get(0);
            casillasPaises.remove(0);
            String[] temp = casilla.split(" ");
            try {
                mapa.generarPais(abrevPais, nombresPaises.get(0),
                        abrevCont, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            nombresPaises.remove(0);

            if (restantes.get(abrevCont) == 0){
                panelOpciones.getComboBoxContinentes().removeItem(abrevCont);
            }

            cuenta++;
            if (cuenta == 42){
                panelOpciones.finalizar();
                mapa.generarFronterasEditadas();
                mapa.crearFronterasIndirectasEditadas(fronterasIndirectas);
            } else {
                panelOpciones.getTextFieldPais().setText(abrevPaises.get(cuenta));
            }
        }
    }

    public ArrayList<String> getAbrevConts() {
        return abrevConts;
    }

    public ArrayList<String> getAbrevPaises() {
        return abrevPaises;
    }

    public Mapa getMapa(){
        return mapa;
    }

}
