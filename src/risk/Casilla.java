package risk;

public class Casilla {

    private int tipoCasilla;
        // -1 -> no es océano
        // 0 -> océano predeterminado
        // 1 -> océano con conexión horizontal
        // 2 -> océano con conexión vertical
    private Pais pais;
    private static ConsolaNormal consolaNormal;

    /*
     * Casilla no necesita fila ni columna, porque estas se pueden gestionar a través de los índices del
     * ArrayList<ArrayList<>> del mapa. Se puede usar el constructor por defecto de Java
     */

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
            consolaNormal.imprimir("Tipo de casilla no existente");
        }
    }

}
