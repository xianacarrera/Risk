package risk;

public class Casilla {

    private int fila, columna;      // Innecesarias?
    private int tipoCasilla;
        // -1 -> no es océano
        // 0 -> océano predeterminado
        // 1 -> océano con conexión horizontal
        // 2 -> océano con conexión vertical
    private Pais pais;

    public Casilla(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }

    public Pais getPais(){
        return pais;
    }

    public void setPais(Pais pais){
        this.pais = pais;
    }

    public int getTipoCasilla(){
        return tipoCasilla;
    }

    public void setTipoCasilla(int tipoCasilla){
        if (tipoCasilla <= 2 && tipoCasilla >= -1){
            this.tipoCasilla = tipoCasilla;
        } else {
            System.out.println("Tipo de casilla no existente");
        }
    }

}
