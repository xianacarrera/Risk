package risk.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.io.File; // para importar archivos
import java.io.FileNotFoundException; // para considerar errores
import java.util.Scanner; // para leer archivos de texto

// Autores: Elena Fernandez del Sel, Xiana Carrera Alonso

public class Imagen extends Cuadrante{
    private final ImageIcon imagen;
    
 
    public Imagen(String nombreImagen){
        super();
        imagen=new ImageIcon(getClass().getResource(nombreImagen));
    }
 
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        // Cargar imagen mapa
        g2.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);        
                try{
            File archivo = new File("paises.txt");
            Scanner lectura = new Scanner(archivo);
            // Escanear archivo por línea, separando por espacios
            while (lectura.hasNextLine()){ 
                String[] dato = lectura.nextLine().split(" ");
                if (!dato[0].contains("oceano")){
                    int x = Integer.parseInt(dato[4]); // posición % de x en img
                    int y = Integer.parseInt(dato[5]); // posición % de y en img
                    int t = Integer.parseInt(dato[6]); // Tamaño del texto
                    int c = Integer.parseInt(dato[7]); // color B/N
                    Font font = new Font("Arial",Font.BOLD,getWidth()/t);
                    if(c==1){
                        g2.setColor(Color.WHITE); // Color Blanco si es oscuro
                    }else{
                        g2.setColor(Color.BLACK); // Color Negro si es claro
                    }
                    
                    g2.setFont(font); // definir fuentes
                    // Dibujar el texto de cada line
                    g2.drawString(dato[0],getWidth()*x/100 , getHeight()*y/100);
                }
            }
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
