package estruturais;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

class Main {
    private FileInputStream   file;
    private InputStreamReader input;
    private BufferedReader    reader;
    private static final String TOKEN_1 = ".";
    private static final String TOKEN_2 = ".X.";
    private static final double PORCENTAGEM = 0.1;
    private String sequencia   = ".";
    
    private void open() throws FileNotFoundException {
        this.file   = new FileInputStream("/tmp/guest-Qf8Gb5/NetBeansProjects/MOA/src/estruturais/arquivo.txt");
        this.input  = new InputStreamReader(this.file);
        this.reader = new BufferedReader(this.input);
    }
    
    public void read() throws IOException {
        this.open();
        
        String linha = this.reader.readLine();
        int n = Integer.parseInt(linha.trim());
        HashMap<String, Vertice> vertices = new HashMap<>();
        
        for (int i = 0; i < n; ++i) {
                     linha   = this.reader.readLine();
            String[] dados   = linha.split(" ");
            Vertice  vertice = new Vertice(dados[0], Double.parseDouble(dados[1]), Double.parseDouble(dados[2]));
            vertices.put(vertice.getLabel(), vertice);
            this.sequencia += vertice.getLabel() + TOKEN_1;
        }
        
        System.out.println(this.sequencia);
        
        int p = (int) (Math.random() * n);
        
        System.out.println("P = " + p);
        
        int x = (int) (n * PORCENTAGEM);
        
        for (int j = 0; j < x; ++j) {
            String backup = this.sequencia;
            for (int i = 0; i < p; ++i) {
                int a = (int) (Math.random() * n);
                int b = (int) (Math.random() * n);
                while (b == a) b = (int) (Math.random() * n);
            
                System.out.println("A = " + a);
                System.out.println("B = " + b);
                System.out.println(backup);
            
                    backup = backup.replace(TOKEN_1 + a + TOKEN_1, TOKEN_2);
                    backup = backup.replace(TOKEN_1 + b + TOKEN_1, TOKEN_1 + a + TOKEN_1);
                    backup = backup.replace(TOKEN_2, TOKEN_1 + b + TOKEN_1);
                   
                System.out.println(backup);
            }   
            System.out.println(TOKEN_1);
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