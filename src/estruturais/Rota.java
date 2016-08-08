package estruturais;

/**
 *
 * @author guest-Qf8Gb5
 */
class Rota {
    private String chave;
    private double distancia = 0; 
    
    public Rota(String chave){
        this.chave = chave;
        // Remove o primeiro e o último caracter da string
        String[] c = chave.substring(1, chave.length()-1).split("\\.");
        for(int i = 1; i < c.length ; i++){
            if(i != 1){
                Vertice anterior = Main.getValueFromVertice(c[i - 1]);
                try{
                    this.distancia += Main.getValueFromVertice(c[i]).distancia(anterior);
                } catch(Exception e){}
            }
        }
        
    }

    public String getChaveNormalizada(){
        return this.chave.substring(1, this.chave.length()-1);
    }
    public String getChave() {
        return this.chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    
    public void executaMutacao(){
        String backup = this.getChave();
        String[] valores = this.getChaveNormalizada().split("\\.");
        int valor1 = (int) (Math.random() * valores.length);
        int valor2 = (int) (Math.random() * valores.length);
        
        String tokenizedA = "." + valor1 + ".";
        String tokenizedB = "." + valor2 + ".";
                
        backup = backup.replace(tokenizedA, ".X.");
        backup = backup.replace(tokenizedB, tokenizedA);
        backup = backup.replace(".X.", tokenizedB);
        
        this.setChave(backup);
        
    }

    @Override
    public String toString() {
        return "Rota ( distancia=" + distancia + ")";
    }
    
    
}
