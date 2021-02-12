package risk;

public interface Consola {

    /***
     *
     * @return Orden introducida por el usuario
     */
    String leer();

    /***
     *
     * @param out Mensaje a imprimir (por pantalla y por fichero)
     */
    void imprimir(String out);

}
