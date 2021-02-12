package risk.enums;

/*
 * Las abreviaturas válidas de los países se han guardado dentro de un enum para facilitar su gestión
 */
public enum Abreviaturas
{
    Afgan, China, India, Irkutsk, Japón, Kamchatka, Mongolia, OMedio, Siberia, SAsiático, Urales,
    Yakustsk, ANorte, AOriental, Congo, Egipto, Madagasca, Sudáfrica, Escandina, EurNorte,
    EurOcc, EurSur, GBretaña, Islandia, Rusia, Alaska, Alberta, AmeCentra, Groenlan,
    Ontario, Quebec, TNoroeste, USAEste, USAOeste, Venezuela, Perú, Brasil, Argentina,
    Indonesia, NGuinea, AusOccid, AusOrient;

    // contiene comprueba si la String pasada como parámetro se corresponde con alguna de las abreviaturas
    public static boolean contiene(String abrev) {
        // Se trata de un método estático. No se crean instancias de Abreviaturas
        for (Abreviaturas elem : values()){
            if (elem.name().equals(abrev)){      // Algún elemento se corresponde con la String pasada
                return true;
            }
        }
        return false;
    }
}
