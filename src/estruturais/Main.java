package estruturais;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * <p>Classe <b>Main</b>.</p>
 * <p>Classe responsavel por realizar a Leitura dos Dados e Aplicar o <b>Algoritmo Genetico</b>.</p>
 * @author Igor
 * @author Leandro
 * @since  30/07/2016
 */
class Main {
    private static Integer      qtdVertices;
    private static String       sequencia = ".";
    private static final String TOKEN     = ".";
    private static final HashMap<String, Vertice> VERTICES = new HashMap<> ();
    
    public static Vertice getValueFromVertice(String key) {
        return VERTICES.get(key);
    }
    
    /**
     * Metodo responsavel por carregar os Dados do Arquivo.
     * @param file Numero do Arquivo a ser buscado.
     * @throws IOException 
     */
    public void read() throws IOException {
        Scanner scan = new Scanner(System.in);
        String linha = scan.nextLine();
        qtdVertices = Integer.parseInt(linha.trim());
        
        for (int i = 0; i < qtdVertices; ++i) {
            linha   = scan.nextLine().replaceAll(" +", " ");
            String[] dados   = linha.split(" ");
            Vertice  vertice = new Vertice(dados[0], Double.parseDouble(dados[1]), Double.parseDouble(dados[2]));
            VERTICES.put(vertice.getLabel(), vertice);
            Main.sequencia += vertice.getLabel() + TOKEN;
        }
    }
    

    
    private void executa (int tamPop, double taxaMutacao, long start, int best) {
        Algoritmo.geraPopulacaoInicial(qtdVertices, tamPop, qtdVertices / 2, sequencia);
        
        long limit    = start + 9000;
        do{
            Algoritmo.escolheReprodutores(tamPop / 2, tamPop);
            Algoritmo.cruzamentoDosReprodutores();
            
            boolean primeiroDeveMutar   = (Math.random() * 100) <= taxaMutacao;
            boolean segundoDeveMutar    = (Math.random() * 100) <= taxaMutacao;
            
            Algoritmo.geraMutacao(primeiroDeveMutar, segundoDeveMutar);
            Algoritmo.buscaLocal(best);
            Algoritmo.atualizaPopulacao();
            
            start = System.currentTimeMillis();
        } while(start < limit);
        System.out.println(Algoritmo.pegaMenor().getDistancia());
//        System.out.format("%8d%12d%15s%14d%14d%15s\n",
//                qtdVertices, tamPop, "%" + (int) taxaMutacao,
//                menorInicial, (int) Algoritmo.pegaMenor().getDistancia(),
//                (best == 0 ? "BEST" : "FIRST"));
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        System.out.format("%s%s%s%s%s%s\n","[Num Vertices]" ,"[Tam. Pop.]", "[Tax. Mutação]", "[Menor Inicial]", "[Menor gerado]", "[Busca Local]");
        Main main = new Main();
        long start;
        start = System.currentTimeMillis();
        main.read();
        main.executa(15, 15.0, start, 1);
    }   
}
