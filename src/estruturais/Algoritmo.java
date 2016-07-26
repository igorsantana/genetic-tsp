package estruturais;

import java.util.ArrayList;
import java.util.List;

public class Algoritmo {

    private static final String                 TOKEN = ".";
    private static final String                 TOKEN_2 = ".X.";
    private static final List<Rota>             rotas = new ArrayList<>();
    private static final List<Rota>             reprodutores = new ArrayList<>();
    private static final List<Rota>             filhosGerados = new ArrayList<>();
    
    public static void geraPopulacaoInicial(Integer n, Integer tamanhoPopulacao, Integer variacoes, String seqInicial){
        for (int j = 0; j < tamanhoPopulacao; ++j) {
            String backup = seqInicial;
            for (int i = 0; i < variacoes; ++i) {
                int a = (int) (1 + (Math.random() * (n - 1)));
                int b = (int) (1 + (Math.random() * (n - 1)));
                while (b == a) b = (int) (1 + (Math.random() * (n - 1)));
                
                String tokenizedA = TOKEN + a + TOKEN;
                String tokenizedB = TOKEN + b + TOKEN;
                
                backup = backup.replace(tokenizedA, TOKEN_2);
                backup = backup.replace(tokenizedB, tokenizedA);
                backup = backup.replace(TOKEN_2, tokenizedB);
            }   
            rotas.add(new Rota(backup));
        }
    }

    public static void escolheReprodutores() {
        Rota menor = null;
        Rota segundoMenor = null;
        Rota aux = null;
        for (Rota rota : rotas) {
            if(menor == null) menor = rota;
            if(segundoMenor == null) segundoMenor = rota;
            if(rota.getDistancia() < segundoMenor.getDistancia()) segundoMenor = rota;
            if(segundoMenor.getDistancia() < menor.getDistancia()){
                aux = menor;
                menor = segundoMenor;
                segundoMenor = aux;
                
            }
        }
        reprodutores.add(menor);
        reprodutores.add(segundoMenor);
    }

    public static void cruzamentoDosReprodutores() {
        Rota reprodutorPai = reprodutores.get(0);
        Rota reprodutorMae = reprodutores.get(1);
        String filhoGeradoUm = ".";
        String filhoGeradoDois = ".";
        String[] arrayPai = reprodutorPai.getChaveNormalizada().split("\\.");
        String[] arrayMae = reprodutorMae.getChaveNormalizada().split("\\.");
        Integer k =  0;
        Integer l =  0;
        Integer m =  0;
        Integer n =  0;
        for (int i = 0; i < arrayPai.length / 2; i++) {
            
            while (filhoGeradoUm.contains(TOKEN + arrayPai[k] + TOKEN)) k++;
            filhoGeradoUm += (arrayPai[k] + TOKEN);
            
            while (filhoGeradoUm.contains(TOKEN + arrayMae[l] + TOKEN)) l++;
            filhoGeradoUm += (arrayMae[l] + TOKEN);
//          --------------------------------------------------------------------
            while (filhoGeradoDois.contains(TOKEN + arrayMae[m] + TOKEN)) m++;
            filhoGeradoDois += (arrayMae[m] + TOKEN);
            
            while (filhoGeradoDois.contains(TOKEN + arrayPai[n] + TOKEN)) n++;
            filhoGeradoDois += arrayPai[n] + TOKEN;
        }
        filhosGerados.add(new Rota(filhoGeradoUm));
        filhosGerados.add(new Rota(filhoGeradoDois));
    }

    static void geraMutacao(boolean primeiroDeveMutar, boolean segundoDeveMutar) {
        if(primeiroDeveMutar) filhosGerados.get(0).executaMutacao();
        if(segundoDeveMutar) filhosGerados.get(1).executaMutacao();
    }
    
    static void atualizaPopulacao(){
        Rota pior = rotas.get(0);
        Integer indicePior  = 0;
        Rota segundoPior = rotas.get(0);
        Integer indiceSegundoPior = 0;
        Rota aux = null;
        Integer indiceAux;
        for (int i = 1; i < rotas.size(); i++) {
            if(rotas.get(i).getDistancia() > segundoPior.getDistancia()) {
                segundoPior = rotas.get(i);
                indiceSegundoPior = i;
            }
            if(segundoPior.getDistancia() > pior.getDistancia()){
                aux = pior;
                indiceAux = indicePior;
                pior = segundoPior;
                indicePior = indiceSegundoPior;
                segundoPior = aux;
                indiceSegundoPior = indiceAux;
            }
        }
        if(filhosGerados.get(0).getDistancia() < filhosGerados.get(1).getDistancia()){
            
        }
    }
}
