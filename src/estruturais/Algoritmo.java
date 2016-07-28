package estruturais;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Algoritmo {

    private static final String                 TOKEN = ".";
    private static final String                 TOKEN_2 = ".X.";
    private static final List<Rota>             rotas = new ArrayList<>();
    private static List<Rota>             reprodutores = new ArrayList<>();
    private static List<Rota>             filhosGerados = new ArrayList<>();
    
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
        Comparator<Rota> c = (o1, o2) -> (int) (o1.getDistancia() - o2.getDistancia());
        Collections.sort(rotas, c);
        
        reprodutores.add(rotas.get(0));
        reprodutores.add(rotas.get(1));
    }

    public static void cruzamentoDosReprodutores() {
        filhosGerados.clear();
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
        System.out.println("xxxxxxxx");
        System.out.println(reprodutorPai.getChave());
        System.out.println(reprodutorMae.getChave());
        System.out.println("----");
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
        reprodutores.clear();
        System.out.println(filhoGeradoUm);
        System.out.println(filhoGeradoDois);
        System.out.println("xxxxxxxx");
        filhosGerados.add(new Rota(filhoGeradoUm));
        filhosGerados.add(new Rota(filhoGeradoDois));
    }

    static void geraMutacao(boolean primeiroDeveMutar, boolean segundoDeveMutar) {
        if(primeiroDeveMutar) filhosGerados.get(0).executaMutacao();
        if(segundoDeveMutar) filhosGerados.get(1).executaMutacao();
    }
    
    static void atualizaPopulacao(){
        Comparator<Rota> c = (o1, o2) -> (int) (o1.getDistancia() - o2.getDistancia());
        
        Collections.sort(rotas, c);

        filhosGerados.add(rotas.get(rotas.size() - 1));
        filhosGerados.add(rotas.get(rotas.size() - 2));
        
        Collections.sort(filhosGerados, c);
        
        rotas.set(rotas.size() - 1, filhosGerados.get(1));
        rotas.set(rotas.size() - 2, filhosGerados.get(0));
    }
    
    static void printaPopulacao(){
        System.out.println("-------");
        rotas.stream().forEach(a -> System.out.println(a.getDistancia()));
        System.out.println("-------");    
        
    }

    static Rota pegaMenor() {
        Comparator<Rota> c = (o1, o2) -> (int) (o1.getDistancia() - o2.getDistancia());
        Collections.sort(rotas, c);
        return rotas.get(0);
    }
}
