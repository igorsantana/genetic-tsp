package estruturais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Classe <b>Algoritmo</b>.</p>
 * <p>Classe responsavel por definir o funcionamento do <b>Algoritmo Generico</b>.</p>
 * @author Igor
 * @author Leandro
 * @since  03/08/2016
 */
class Algoritmo {
    private static final String     TOKEN          = ".";
    private static final String     TOKEN_ELEMENTO = ".X.";
    private static final List<Rota> POPULACAO      = new ArrayList<>();
    private static final List<Rota> REPRODUTORES   = new ArrayList<>();
    private static final List<Rota> FILHOS_GERADOS = new ArrayList<>();
    
    /**
     * Metodo responsavel por gerar a Populacao Inicial do Algoritmo.
     * @param numeroVertices Numero Total de Vertices.
     * @param tamanhoPopulacao Tamanho da Populacao.
     * @param variacoes Numero de Variacoes.
     * @param rotaInicial Sequencia de Elementos da Rota Inicial.
     */
    public static void geraPopulacaoInicial(int numeroVertices, int tamanhoPopulacao, int variacoes, String rotaInicial) {
        POPULACAO.clear();
        for (int j = 0; j < tamanhoPopulacao; ++j) {
            String backup = rotaInicial;
            for (int i = 0; i < variacoes; ++i) {
                int a = (int) (1 + (Math.random() * (numeroVertices - 1)));
                int b = (int) (1 + (Math.random() * (numeroVertices - 1)));
                while (b == a) b = (int) (1 + (Math.random() * (numeroVertices - 1)));
                
                String tokenizedA = TOKEN + a + TOKEN;
                String tokenizedB = TOKEN + b + TOKEN;
                
                backup = backup.replace(tokenizedA, TOKEN_ELEMENTO);
                backup = backup.replace(tokenizedB, tokenizedA);
                backup = backup.replace(TOKEN_ELEMENTO, tokenizedB);
            }   
            POPULACAO.add(new Rota(backup));
        }
    }

    /**
     * Metodo responsavel por selecionar os Reprodutores.
     * @param tamanhoTorneio Tamanho do Torneio.
     * @param tamanhoPopulacao Tamanho da Populacao.
     */
    public static void escolheReprodutores(int tamanhoTorneio, int tamanhoPopulacao) {
        REPRODUTORES.clear();
        List<Rota>    torneio = new ArrayList<>();
        List<Integer> valores = new ArrayList<>();
        
        while (valores.size() < tamanhoTorneio) {
            int valor = (int) (1 + (Math.random() * (tamanhoPopulacao - 1)));
            if (!valores.contains(valor)) {
                valores.add(valor);
                torneio.add(POPULACAO.get(valor));
            }
        }
        
        Collections.sort(torneio, Rota.getComparator());
        
        REPRODUTORES.add(torneio.get(0));
        REPRODUTORES.add(torneio.get(torneio.size() - 1));
    }

    /**
     * Metodo responsavel por realizar o Cruzamento dos Reprodutores.
     */
    public static void cruzamentoDosReprodutores() {
        FILHOS_GERADOS.clear();
        
        Rota reprodutorPai = REPRODUTORES.get(0);
        Rota reprodutorMae = REPRODUTORES.get(1);
        
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
        
        REPRODUTORES.clear();
        FILHOS_GERADOS.add(new Rota(filhoGeradoUm));
        FILHOS_GERADOS.add(new Rota(filhoGeradoDois));
    }

    /**
     * Metodo responsavel por realizar a Mutacao da Rota pelos parametros passados.
     * @param primeiroDeveMutar Mutar Primeiro elemento.
     * @param segundoDeveMutar Mutar Segundo elemento.
     */
    static void geraMutacao(boolean primeiroDeveMutar, boolean segundoDeveMutar) {
        if (primeiroDeveMutar) FILHOS_GERADOS.get(0).executaMutacao();
        if (segundoDeveMutar)  FILHOS_GERADOS.get(1).executaMutacao();
    }
    
    /**
     * Metodo responsavel por realizar a Busca Local.
     * @param bestOrFirst Igual a 1 para First, Diferente de 1 para Best.
     */
    static void buscaLocal(int bestOrFirst) {
        Rota baseBuscaLocal = FILHOS_GERADOS.get(0);
        String[] vertices   = baseBuscaLocal.getChaveNormalizada().split("\\.");
        Rota melhor = baseBuscaLocal;
        Boolean shouldBreak = (bestOrFirst == 1);
        for (int i = 0; i < vertices.length - 3; i++) {
            String[] vAux = vertices.clone();
            vAux[i]      = vertices[i + 3];
            vAux[i + 1]  = vertices[i + 2];
            vAux[i + 2]  = vertices[i + 1];
            vAux[i + 3]  = vertices[i];
            Rota newRota = new Rota(vAux.clone());
            if (newRota.getDistancia() < melhor.getDistancia()) {
                melhor = newRota;
                if (shouldBreak) break;
            }
        }
        
        if (melhor.getDistancia() != baseBuscaLocal.getDistancia())
            FILHOS_GERADOS.set(0, melhor);
        
    }
    
    /**
     * Metodo responsavel por atualizar a Populacao.
     */
    static void atualizaPopulacao(){        
        POPULACAO.add(FILHOS_GERADOS.get(0));
        POPULACAO.add(FILHOS_GERADOS.get(1));
        
        Collections.sort(POPULACAO, Rota.getComparator());
        
        POPULACAO.remove(POPULACAO.size() - 1);
        POPULACAO.remove(POPULACAO.size() - 2);
    }
    
    /**
     * Metodo responsavel por retornar uma Posicao Aleatoria entre [1, limit], que seja menor ou igual a length.
     * @param  limit Limite Superior.
     * @param  length Tamanho Maximo.
     * @return Valor aleatorio entre [1, limit], menor ou igual a legth.
     */
    static int getPosition(int limit, int length) {
        int index  = (int) (1 + (Math.random() * length));
        while ((index + limit) >= length) 
            index  = (int) (1 + (Math.random() * length));
        return index;
    }

    /**
     * Metodo responsavel por retornar a Rota com Menor Distancia da Populacao.
     * @return Rota com Menor Distancia da Populacao.
     */
    static Rota pegaMenor() {
        Collections.sort(POPULACAO, Rota.getComparator());
        return POPULACAO.get(0);
    }
}
