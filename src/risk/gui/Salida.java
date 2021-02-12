package risk.gui;

import com.sun.xml.internal.ws.resources.DispatchMessages;
import sun.security.util.ConstraintsParameters;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Salida extends JPanel {

    private JPanel panelResultados;
    private JPanel contenido;
    private JFrame frame;
    private JScrollPane scroll;
    private JTextArea zonaTexto;

    public static final Color verde = new Color(22, 47, 6);
    Font font = new Font("Agency FB",Font.PLAIN,20);


    public Salida(){
        definirComponentes();
        configurarComponentes();
        layoutComponentes();
    }

    public void definirComponentes(){
        panelResultados = new JPanel();
        zonaTexto = new JTextArea();
        scroll = new JScrollPane(panelResultados);
        contenido = new JPanel(null);
        frame = new JFrame();
    }

    public void configurarComponentes(){
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(20, 20, 350, 260);
        contenido.setPreferredSize( new Dimension(400, 300));
        contenido.setMinimumSize(getPreferredSize());
        zonaTexto.setBackground(getBackground());
        zonaTexto.setForeground(Color.red);
        zonaTexto.setFont(font);
        zonaTexto.setEditable(false);

        setBackground(verde);
    }

    public void layoutComponentes(){
        panelResultados.add(zonaTexto);
        contenido.add(scroll);
    }

    public void setTextoZonaTexto(String valor){
        zonaTexto.append(valor);

        // Se hace visible la zona del texto (disponible a partir de la primera llamada a setTextoZonaTexto)
        zonaTexto.setVisible(true);
        add(contenido);
    }
}

