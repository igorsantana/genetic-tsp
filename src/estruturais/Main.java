package estruturais;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

class Main {
    private FileInputStream     file;
    private InputStreamReader   input;
    private BufferedReader      reader;
    private static String       sequencia   = ".";
    private static Integer      qtdVertices;
    private static final String TOKEN_1 = ".";
    private static final double PORCENTAGEM = 0.5;
    private static final int    tamanhoPopulacao    = 100;
    private static final HashMap<String, Vertice> VERTICES = new HashMap<>();
    private static long start;
    
    public static Vertice getValueFromVertice(String key){
        return VERTICES.get(key);
    }
    
    private void open() throws FileNotFoundException {
        this.file   = new FileInputStream("/tmp/guest-swkjuG/NetBeansProjects/pcv-genetico/src/estruturais/arquivo.txt");
        this.input  = new InputStreamReader(this.file);
        this.reader = new BufferedReader(this.input);
    }
    
    public void read() throws IOException {
        this.open();
        start = System.currentTimeMillis();
        
        String linha = this.reader.readLine();
        
        qtdVertices = Integer.parseInt(linha.trim());
        
        for (int i = 0; i < qtdVertices; ++i) {
            linha   = this.reader.readLine().replaceAll(" +", " ");
            
            String[] dados   = linha.split(" ");
            
            Vertice  vertice = new Vertice(dados[0], Double.parseDouble(dados[1]), Double.parseDouble(dados[2]));
            
            VERTICES.put(vertice.getLabel(), vertice);
            
            Main.sequencia += vertice.getLabel() + TOKEN_1;
        }
        this.close();
    }
    
    private void close() throws IOException {
        this.file.close();
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Main main = new Main();
        main.read();
        
        int     tamanhoTorneio      = tamanhoPopulacao / 2;
        int     variacoes           = tamanhoPopulacao * 10;
        double  taxaDeMutacao       = 2.5;
        
        Algoritmo.geraPopulacaoInicial(qtdVertices, tamanhoPopulacao, variacoes, sequencia);
        
        System.out.println("Tamanho da entrada: " + VERTICES.size());
        System.out.println("Tamanho da população gerada: " + tamanhoPopulacao);
        System.out.println("Menor gerado da população: " + (int) Algoritmo.pegaMenor().getDistancia());
        
        long limit    = start + 9000;
        
        do{
            Algoritmo.escolheReprodutores(tamanhoTorneio, tamanhoPopulacao);
            Algoritmo.cruzamentoDosReprodutores();
            
            boolean primeiroDeveMutar   = (Math.random() * 100) <= taxaDeMutacao;
            boolean segundoDeveMutar    = (Math.random() * 100) <= taxaDeMutacao;
            
            Algoritmo.geraMutacao(primeiroDeveMutar , segundoDeveMutar);
            Algoritmo.buscaLocal();
            Algoritmo.atualizaPopulacao();
            start = System.currentTimeMillis();
        } while(start < limit);
        
        System.out.println("Menor valor encontrado: " + (int) Algoritmo.pegaMenor().getDistancia());
        System.out.println(Algoritmo.pegaMenor().getChaveNormalizada());
    }   
    
}