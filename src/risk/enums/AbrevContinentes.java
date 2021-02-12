package risk.enums;

/*
 * Las abreviaturas válidas de los continentes se han guardado dentro de un enum para facilitar su gestión
 */
public enum AbrevContinentes {

    AméricaNorte, AméricaSur, Europa, África, Asia, Oceanía;

    // contiene comprueba si la String pasada como parámetro se corresponde con alguna de las abreviaturas
    public static boolean contiene(String abrev) {
        // Se trata de un método estático, pues será referenciado desde un contexto no estático
        for (AbrevContinentes elem : values()){
            if (elem.name().equals(abrev)){      //  Algún elemento se corresponde con la String pasada
                return true;
            }
        }
        return false;
    }
}
