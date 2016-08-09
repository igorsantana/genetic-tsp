package estruturais;

import java.util.Comparator;

/**
 * <p>Classe <b>Vertice</b>.</p>
 * <p>Classe responsavel por representar uma <b>Rota</b>.</p>
 * @author Igor
 * @author Leandro
 * @since  01/08/2016
 */
final class Rota {
    private String chave;
    private double distancia;
    
    /**
     * Metodo construtor padrao da Classe.
     * @param chave Chave completa da Rota.
     */
    public Rota(String chave) {
        this.chave       = chave;
        String[] valores = chave.substring(1, chave.length() - 1).split("\\.");
        this.calculaDistancia(valores);        
    }
    
    /**
     * Metodo construtor alternativo da Classe.
     * @param valores Vetor com os Valores da Chave.
     */
    public Rota(String[] valores) {
        this.chave = "." + valores[0] + ".";
        for (int i = 1; i < valores.length; ++i) {
            this.chave += valores[i] + ".";
        }
        this.calculaDistancia(valores);
    }
    
    /**
     * Metodo responsavel por calcular e definir a Distancia da Rota.
     * @param valores Vetor com os Valores da Rota.
     */
    public void calculaDistancia(String[] valores) {
        for (int i = 1; i < valores.length ; ++i) {
            if (i != 1) {
                Vertice anterior = Main.getValueFromVertice(valores[i - 1]);
                this.distancia  += Main.getValueFromVertice(valores[i]).distancia(anterior);
            }
        }
    }
    
    /**
     * Metodo responsavel por retornar a Chave Normalizada (sem os Pontos no Inicio e Fim) da Rota.
     * @return Chave Normalizada da Rota.
     */
    public String getChaveNormalizada() {
        return this.chave.substring(1, this.chave.length() - 1);
    }
    
    /**
     * Metodo responsavel por retornar a Chave Completa da Rota.
     * @return Chave Completa da Rota.
     */
    public String getChave() {
        return this.chave;
    }

    /**
     * Metodo responsavel por definir a Chave Completa da Rota.
     * @param chave Chave Completa da Rota.
     */
    public void setChave(String chave) {
        this.chave = chave;
    }

    /**
     * Metodo responsavel por retornar a Distancia Total da Rota.
     * @return Distancia Total da Rota.
     */
    public double getDistancia() {
        return this.distancia;
    }

    /**
     * Metodo responsavel por definir a Distancia Total da Rota.
     * @param distancia Distancia Total da Rota.
     */
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    
    /**
     * Metodo responsavel por Executar a Mutacao da Chave.
     */
    public void executaMutacao() {
        String   backup  = this.getChave();
        String[] valores = this.getChaveNormalizada().split("\\.");
        
        int valorA = (int) (1 + (Math.random() * valores.length));
        int valorB = (int) (1 + (Math.random() * valores.length));
        
        String tokenizedA = "." + valorA + ".";
        String tokenizedB = "." + valorB + ".";
        
        backup = backup.replace(tokenizedA, ".X.");
        backup = backup.replace(tokenizedB, tokenizedA);
        backup = backup.replace(".X.", tokenizedB);
        
        this.setChave(backup);
    }

    /**
     * Metodo responsavel por retornar o Comparator da Rota.
     * @return Comparator da Rota.
     */
    public static Comparator<Rota> getComparator() {
        return (rota1, rota2) -> (int) (rota1.getDistancia() - rota2.getDistancia());
    }
    
    @Override
    public String toString() {
        return "Rota ( distancia=" + distancia + ")";
    }
}