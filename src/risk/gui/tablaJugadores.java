package risk.gui;

import risk.Juego;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import risk.Jugador;

/**
 *
 * @author Xiana Carrera Alonso Elena Fernández del Sel
 */
public class tablaJugadores extends JPanel {

    private JScrollPane scrollPane;
    private Juego juego;
    private String[] Jug;
    private ArrayList<String> paises;
    private Object[][] data;
    private JTable table;
    private int partidaIniciada;

    public tablaJugadores(Juego juego) {
        //Define un grid
        super(new GridLayout(1, 0));
        this.juego=juego;
        setBackground(Color.GRAY);
        partidaIniciada = 0;

    }

    public void listaJugadores(){
        ArrayList<Jugador> Juga = juego.getJugadores();
        Jug = new String[Juga.size()+1];
        Jug[0] = "Países";

        for(int k=1;k<Jug.length;k++){
            Jug[k] = Juga.get(k-1).getNombre();
        }

    }

    public void listaPaises(){
        //Leer paises de archivo
        paises = new ArrayList<>();

        try {
            //Aquí da un fallo si le pones la ruta completa funciona
            FileReader fr = new FileReader("paises.txt");
            BufferedReader bf = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = bf.readLine()) != null) {
                //Guardar la lista de paises en un arraylist
                String pais=linea.split(" ")[0];
                if (!pais.contains("oceano")){
                    paises.add(pais);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inicializarDatos(){
        data = new Object[paises.size()][Jug.length];
    }

    public void asignarDatosTabla(){

        //Se asignan los datos. Los ejércitos son 0 hay que vincularlo al programa
        for (int i = 0; i < paises.size(); i++) {
            for (int j = 0; j < Jug.length; j++) {
                if (j == 0) {
                    data[i][j] = paises.get(i);
                } else {
                    if(juego.getPaises().get(paises.get(i)).getJugador().getNombre().equals(Jug[j])){
                        data[i][j] = juego.getPaises().get(paises.get(i)).getNumEjercitos();
                    } else{
                        data[i][j] = 0;
                    }
                }
            }
        }
    }

    public void crearTabla(){
        Render c = new Render(juego);
        //Definimos la tabla


        table = new JTable(data, Jug);


        table.getColumnModel().getColumn(0).setCellRenderer(c);


        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Creamos el scroll y lo agregamos a la tabla
        scrollPane = new JScrollPane(table);

        //Agregamos el scroll al panel
        add(scrollPane);
    }

    public void modificarDatosTabla(){
        for (int i = 0; i < paises.size(); i++) {
            for (int j = 0; j < Jug.length; j++) {
                if (j != 0) {
                    if(juego.getPaises().get(paises.get(i)).getJugador().getNombre().equals(Jug[j])){
                        table.setValueAt(juego.getPaises().get(paises.get(i)).getNumEjercitos(),i,j);
                    } else{
                        table.setValueAt(0,i,j);
                    }
                }
            }
        }
    }

    public void modificarTabla(){
        modificarDatosTabla();
        table.repaint();
    }

    public void setPartidaIniciada(){
        partidaIniciada = 1;
    }

    public int getPartidaIniciada(){
        return partidaIniciada;
    }
}
