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
    private static final double PORCENTAGEM = 0.1;
    private static final HashMap<String, Vertice> VERTICES = new HashMap<>();
    
    public static Vertice getValueFromVertice(String key){
        return VERTICES.get(key);
    }
    
    private void open() throws FileNotFoundException {
        this.file   = new FileInputStream("/Users/igorsantana/NetBeansProjects/pcv-genetico/src/estruturais/arquivo.txt");
        this.input  = new InputStreamReader(this.file);
        this.reader = new BufferedReader(this.input);
    }
    
    public void read() throws IOException {
        this.open();
        
        String linha = this.reader.readLine();
        qtdVertices = Integer.parseInt(linha.trim());
        
        for (int i = 0; i < qtdVertices; ++i) {
            linha   = this.reader.readLine();
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
        
        int     tamanhoPopulacao    = (int) (qtdVertices * PORCENTAGEM);
        int     variacoes           = (int) (Math.random() * qtdVertices);
        double  taxaDeMutacao       = 1.5;
        
        
        Algoritmo.geraPopulacaoInicial(qtdVertices, tamanhoPopulacao, variacoes, sequencia);
        Rota menor = Algoritmo.pegaMenor();
        Algoritmo.printaPopulacao();
        int i = 0;
        do{
            Algoritmo.escolheReprodutores();
            Algoritmo.cruzamentoDosReprodutores();
            
            boolean primeiroDeveMutar   = (Math.random() * 100) <= taxaDeMutacao;
            boolean segundoDeveMutar    = (Math.random() * 100) <= taxaDeMutacao;
            
            Algoritmo.geraMutacao(primeiroDeveMutar , segundoDeveMutar);
            Algoritmo.atualizaPopulacao();
            i++;
//            if(menor.getDistancia() == Algoritmo.pegaMenor().getDistancia()){
//                break;
//            } 
//            menor = Algoritmo.pegaMenor();
        } while(i < 100);
        
        Algoritmo.printaPopulacao();
    }
    
}