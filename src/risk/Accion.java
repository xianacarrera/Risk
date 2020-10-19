package risk;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Accion {

    Dados dados;
    Formateo formateo;

    public Accion(){
        dados = new Dados();
        formateo = new Formateo();

        jugar();
    }


    public void atacar(Pais atacante, Pais defensor){
        // if atacante.tieneFronteraCon(defensor)

        if (atacante.getNumEjercitos() <= 1){
            System.out.println("ERROR");
            return;
        }

        ArrayList<Integer> dadosAtaque, dadosDefensa;
        ArrayList<Integer> infoAtaque = new ArrayList<>();
        ArrayList<Integer> infoDefensa = new ArrayList<>();

        infoAtaque.add(atacante.getNumEjercitos());
        infoDefensa.add(defensor.getNumEjercitos());

        switch (infoAtaque.get(0)){
            case 2:
                dadosAtaque = dados.tirarDados(1);
                break;
            case 3:
                dadosAtaque = dados.tirarDados(2);
                break;
            default:
                dadosAtaque = dados.tirarDados(3);
        }

        switch (infoDefensa.get(0)){
            case 1:
                dadosDefensa = dados.tirarDados(1);
                break;
            default:
                dadosDefensa = dados.tirarDados(2);
        }

        Collections.sort(dadosAtaque, Collections.reverseOrder());
        Collections.sort(dadosDefensa, Collections.reverseOrder());

        for (int i = 0; i < (Math.min(dadosAtaque.size(), dadosDefensa.size())); i++){
            if (dadosDefensa.get(i) >= dadosAtaque.get(i)){
                atacante.quitarEjercitos(1);
            } else {
                defensor.quitarEjercitos(1);
            }
        }

        if (defensor.getNumEjercitos() == 0){
            defensor.ocuparPais(atacante.getJugador());
            atacante.setNumEjercitos(Math.max(infoAtaque.get(0) - 3, 1));
            // Siempre se intentan pasar 3 tropas. Si hay más de 3, no hay problema. Si hay menos, tendrá que quedar 1
            defensor.setNumEjercitos(infoAtaque.get(0) - infoAtaque.get(1));
        }

        infoAtaque.add(atacante.getNumEjercitos());
        infoDefensa.add(defensor.getNumEjercitos());

        System.out.println("{\n"
                + formateo.formatoConjuntoCaracter("dadosAtaque", '[', dadosAtaque)
                + formateo.formatoConjuntoCaracter("dadosDefensa", '[', dadosDefensa)
                + formateo.formatoConjuntoCaracter("ejercitosPaisAtaque", '{', infoAtaque)
                + formateo.formatoConjuntoCaracter("ejercitosPaisDefensa", '{', infoDefensa)
                + formateo.formatoSimple("paisAtaquePerteneceA", atacante.getJugador().getNombre())
                + formateo.formatoSimple("paisDefensaPerteneceA", defensor.getJugador().getNombre())
                + formateo.formatoSimple("continenteConquistado", "null")
                + "}\n");

        // TODO: continenteConquistado ???

    }
}
