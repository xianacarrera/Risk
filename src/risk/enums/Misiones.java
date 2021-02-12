package risk.enums;

/*
 * Los distintos identificadores de misiones se han guardado como variables globales dentro de un enum, con el objetivo
 * de facilitar su gestión.
 */

public enum Misiones {
    M1, M2, M31, M32, M33, M34, M41, M42, M43, M44, M45, M46;

    // contiene comprueba si la String pasada como parámetro se corresponde con alguno de los identificadores de misión
    public static boolean contiene(String id) {
        // Se trata de un método estático. No se crean instancias de Misiones
        for (Misiones mision : values()) {
            if (mision.name().equals(id)) {      // El nombre de algún elemento se corresponde con la String pasada
                return true;
            }
        }
        return false;
    }
}
