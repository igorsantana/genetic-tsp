package estruturais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Algoritmo {

    private static final String     TOKEN           = ".";
    private static final String     TOKEN_2         = ".X.";
    private static final List<Rota> rotas           = new ArrayList<>();
    private static final List<Rota> reprodutores    = new ArrayList<>();
    private static final List<Rota> filhosGerados= new ArrayList<>();
    
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

    public static void escolheReprodutores(int tamanhoTorneio, int tamanhoPopulacao) {
        List<Rota> torneio = new ArrayList<>();
        List<Integer> valores = new ArrayList<>();
        
        while (valores.size() < tamanhoTorneio) {
            int a = (int) (1 + (Math.random() * (tamanhoPopulacao - 1)));
            if (!valores.contains(a)) {
                valores.add(a);
                torneio.add(rotas.get(a));
            }
        }
        Comparator<Rota> c = (o1, o2) -> (int) (o1.getDistancia() - o2.getDistancia());
        Collections.sort(torneio, c);
        
        reprodutores.add(torneio.get(0));
        reprodutores.add(torneio.get(torneio.size() - 1));
    }

    public static void cruzamentoDosReprodutores() {
        filhosGerados.clear();
        Rota reprodutorPai = reprodutores.get(0);
        Rota reprodutorMae = reprodutores.get(1);
        String filhoGeradoUm   = ".";
        String filhoGeradoDois = ".";
        String[] arrayPai = reprodutorPai.getChaveNormalizada().split("\\.");
        String[] arrayMae = reprodutorMae.getChaveNormalizada().split("\\.");
        
        int limit    = (int) (arrayPai.length * 0.4);
        int indexPai = getPosition(limit, arrayPai.length);
        int indexMae = getPosition(limit, arrayMae.length);
        
        while (indexPai == indexMae) {
            indexMae = getPosition(limit, arrayMae.length);
        }
            
        // ========== GERACAO FILHO 1 =============== // 
        for (int i = indexPai - 1; i < (indexPai + limit); ++i) {
            filhoGeradoUm += (arrayPai[i] + TOKEN);
        }
        
        List<String> auxMae = Arrays.asList(arrayMae);
        
        Collections.reverse(auxMae);
        
        for (String elementoMae : auxMae) {
            if (!filhoGeradoUm.contains(TOKEN + elementoMae + TOKEN)) {
                filhoGeradoUm += (elementoMae + TOKEN);
            }
        }

        // ========== GERACAO FILHO 2 =============== // 
        for (int i = indexMae - 1; i < (indexMae + limit); ++i) {
            filhoGeradoDois += (arrayMae[i] + TOKEN);
        }
        
        List<String> auxPai = Arrays.asList(arrayPai);
                Collections.reverse(auxPai);
        
        
        for (String elementoPai : auxPai) {
            if (!filhoGeradoDois.contains(TOKEN + elementoPai + TOKEN)) {
                filhoGeradoDois += (elementoPai + TOKEN);
            }
        }

    reprodutores.clear();
    filhosGerados.add(new Rota(filhoGeradoUm));
    filhosGerados.add(new Rota(filhoGeradoDois));
    }

    static void geraMutacao(boolean primeiroDeveMutar, boolean segundoDeveMutar) {
        if(primeiroDeveMutar) filhosGerados.get(0).executaMutacao();
        if(segundoDeveMutar) filhosGerados.get(1).executaMutacao();
    }
    
    static void buscaLocal(){
        Rota baseBuscaLocal = filhosGerados.get(0);
        
        String[] vertices = baseBuscaLocal.getChave().split("\\.");
        Rota melhor = null;
        for (int i = 0; i < (vertices.length - 3); i++) {
            
            String[] vAux = vertices.clone();
            
            String aux = vAux[i];
            vAux[i] = vAux[i + 3];
            vAux[i + 3] = aux;
            
            String aux2 = vAux[i + 1];
            vAux[i + 1] = vAux[i + 2];
            vAux[i + 2] = aux2;
            Rota x = new Rota(vAux);
            if(x.getDistancia() < baseBuscaLocal.getDistancia()){
                melhor = x;
            }
        }
        if(melhor != null){
            filhosGerados.set(0, melhor);
        }
    }
    
    static void atualizaPopulacao(){
        Comparator<Rota> c = (o1, o2) -> (int) (o1.getDistancia() - o2.getDistancia());
        
        rotas.add(filhosGerados.get(0));
        rotas.add(filhosGerados.get(1));
        
        Collections.sort(rotas, c);
        
        rotas.remove(rotas.size() - 1);
        rotas.remove(rotas.size() - 2);
    }
    
    static int getPosition(int limit, int length) {
        int index  = (int) (1 + (Math.random() * length));
        while ((index + limit) >= length) {
            index  = (int) (1 + (Math.random() * length));
        }
        return index;
    }
    
    static void printaPopulacao(){
        System.out.println("-------");
        rotas.stream().forEach(a -> System.out.println(a.getDistancia() + " - " + a.getChave()));
        System.out.println("-------");    
        
    }

    static Rota pegaMenor() {
        Comparator<Rota> c = (o1, o2) -> (int) (o1.getDistancia() - o2.getDistancia());
        Collections.sort(rotas, c);
        return rotas.get(0);
    }
}
