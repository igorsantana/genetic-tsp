package estruturais;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Classe <b>Main</b>.</p>
 * <p>Classe responsavel por realizar a Leitura dos Dados e Aplicar o <b>Algoritmo Genetico</b>.</p>
 * @author Igor
 * @author Leandro
 * @since  30/07/2016
 */
class Main {
    private FileInputStream     file;
    private InputStreamReader   input;
    private BufferedReader      reader;
    private static Integer      qtdVertices;
    private static String       sequencia = ".";
    private static final String TOKEN     = ".";
    private static final HashMap<String, Vertice> VERTICES = new HashMap<> ();
    
    public static Vertice getValueFromVertice(String key) {
        return VERTICES.get(key);
    }
    
    private void open(int f) throws FileNotFoundException {
        this.file   = new FileInputStream("C:\\Users\\Milena\\Documents\\NetBeansProjects\\pcv-genetico\\src\\estruturais\\arquivo" + f +".txt");
        this.input  = new InputStreamReader(this.file);
        this.reader = new BufferedReader(this.input);
    }
    
    /**
     * Metodo responsavel por carregar os Dados do Arquivo.
     * @param file Numero do Arquivo a ser buscado.
     * @throws IOException 
     */
    public void read(int file) throws IOException {
        VERTICES.clear();
        this.open(file);
        
        String linha = this.reader.readLine();
        qtdVertices = Integer.parseInt(linha.trim());
        
        for (int i = 0; i < qtdVertices; ++i) {
            linha   = this.reader.readLine().replaceAll(" +", " ");
            
            String[] dados   = linha.split(" ");
            Vertice  vertice = new Vertice(dados[0], Double.parseDouble(dados[1]), Double.parseDouble(dados[2]));
            VERTICES.put(vertice.getLabel(), vertice);
            Main.sequencia += vertice.getLabel() + TOKEN;
        }
        this.close();
    }
    
    private void close() throws IOException {
        this.file.close();
    }
    
    private void executa (int tamPop, double taxaMutacao, long start, int best) {
        Algoritmo.geraPopulacaoInicial(qtdVertices, tamPop, qtdVertices / 2, sequencia);
        int menorInicial = (int) Algoritmo.pegaMenor().getDistancia();
        
        long limit    = start + 9000;
        do{
            Algoritmo.escolheReprodutores(tamPop / 2, tamPop);
            Algoritmo.cruzamentoDosReprodutores();
            
            boolean primeiroDeveMutar   = (Math.random() * 100) <= taxaMutacao;
            boolean segundoDeveMutar    = (Math.random() * 100) <= taxaMutacao;
            
            Algoritmo.geraMutacao(primeiroDeveMutar , segundoDeveMutar);
            Algoritmo.buscaLocal(best);
            Algoritmo.atualizaPopulacao();
            
            start = System.currentTimeMillis();
        } while(start < limit);
        System.out.format("%8d%12d%15s%14d%14d%15s\n",
                qtdVertices, tamPop, "%" + (int) taxaMutacao,
                menorInicial, (int) Algoritmo.pegaMenor().getDistancia(),
                (best == 0 ? "BEST" : "FIRST"));
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Main main = new Main();
        
        List<CasoTeste> tests = new ArrayList<>();
        int[] files = {1};
        int[] pops  = {15, 25, 50, 75, 100};
        double[] mutations = {15.0, 20.0};
        int[] improvs = {0, 1};
        
        for (int improv : improvs)
            for (double mutation : mutations) 
                for (int pop : pops) 
                    tests.add(new CasoTeste(pop, mutation, improv));
            
        
        
        
        
        for (int file : files){
            main.read(file);
            long start;
            System.out.println("ARQUIVO 0" + file);
            System.out.format("%s%s%s%s%s%s\n","[Num Vertices]" ,"[Tam. Pop.]", "[Tax. Mutação]", "[Menor Inicial]", "[Menor gerado]", "[Busca Local]");
            for (CasoTeste teste : tests) {
                start = System.currentTimeMillis();
                
                main.executa(teste.getTamanhoPopulacao(), teste.getTaxaMutacao(), start, teste.getLocal());
            }
        } 
    }   
}