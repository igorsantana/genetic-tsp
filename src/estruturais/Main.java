package estruturais;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;

class Main {
    private FileInputStream   file;
    private InputStreamReader input;
    private BufferedReader    reader;
    private static final String TOKEN_1 = ".";
    private static final String TOKEN_2 = ".X.";
    private static final double PORCENTAGEM = 0.1;
    private String sequencia   = ".";
    private static final HashMap<String, Vertice> VERTICES = new HashMap<>();
    
    public static Vertice getValueFromMap(String key){
        return VERTICES.get(key);
    }
    
    private void open() throws FileNotFoundException {
        this.file   = new FileInputStream("D:\\MOA\\src\\estruturais\\arquivo.txt");
        this.input  = new InputStreamReader(this.file);
        this.reader = new BufferedReader(this.input);
    }
    
    public void read() throws IOException {
        this.open();
        
        String linha = this.reader.readLine();
        int n = Integer.parseInt(linha.trim());
        
        for (int i = 0; i < n; ++i) {
            linha   = this.reader.readLine();
            String[] dados   = linha.split(" ");
            Vertice  vertice = new Vertice(dados[0], Double.parseDouble(dados[1]), Double.parseDouble(dados[2]));
            VERTICES.put(vertice.getLabel(), vertice);
            this.sequencia += vertice.getLabel() + TOKEN_1;
        }
        
        int tamanhoPopulacao = (int) (n * PORCENTAGEM);
        int populacaoInicial = (int) (Math.random() * n);
        PriorityQueue<Rota> rotas = new PriorityQueue<>((Rota o1, Rota o2) -> {
            return (int)(o1.getDistancia() - o2.getDistancia());
        });
        for (int j = 0; j < tamanhoPopulacao; ++j) {
            String backup = this.sequencia;
            for (int i = 0; i < populacaoInicial; ++i) {
                int a = (int) (1 + (Math.random() * (n - 1)));
                int b = (int) (1 + (Math.random() * (n - 1)));
                while (b == a) b = (int) (1 + (Math.random() * (n - 1)));
                
                String tokenizedA = TOKEN_1 + a + TOKEN_1;
                String tokenizedB = TOKEN_1 + b + TOKEN_1;
                
                backup = backup.replace(tokenizedA, TOKEN_2);
                backup = backup.replace(tokenizedB, tokenizedA);
                backup = backup.replace(TOKEN_2, tokenizedB);
            }   
            rotas.add(new Rota(backup));
        }
        this.close();
    }
    
    private void close() throws IOException {
        this.file.close();
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Main main = new Main();
        main.read();
    }
    
}