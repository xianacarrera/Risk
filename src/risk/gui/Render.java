package risk.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import risk.Juego;


public class Render extends DefaultTableCellRenderer {

    Color morado = new Color(87,35,100);
    Color azulClaro = new Color(23, 156, 179);
    Color verde = new Color(15, 111, 11);
    Juego juego;

    public Render(Juego juego){
        this.juego = juego;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        super.getTableCellRendererComponent(table,value, isSelected, hasFocus,row, column);
        String pais = String.valueOf(table.getValueAt(row,0));

        String continente = juego.getPaises().get(pais).getContinente().getAbreviatura();

        if(continente.equals("AméricaNorte")){//AmericaNorte
            this.setBackground(Color.blue);
            this.setForeground(Color.white);
        }else if(continente.equals("AméricaSur")){//AmericaSur
            this.setBackground(Color.red);
            this.setForeground(Color.black);
        } else if(continente.equals("Europa")) {//Europa
            this.setBackground(Color.yellow);
            this.setForeground(Color.black);
        }else if(continente.equals("África")) {//Africa
            this.setBackground(verde);
            this.setForeground(Color.black);
        }else if(continente.equals("Asia")) {//Asia
            this.setBackground(azulClaro);
            this.setForeground(Color.black);
        }else if(continente.equals("Oceanía")) {//Oceania
            this.setBackground(morado);
            this.setForeground(Color.black);
        }

        return this;
    }
}
