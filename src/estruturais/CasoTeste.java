package estruturais;


public class CasoTeste {

   private int tamanhoPopulacao;
   private double taxaMutacao;
   private int local;

    public CasoTeste(int tamanhoPopulacao, double taxaMutacao, int local) {
        this.tamanhoPopulacao = tamanhoPopulacao;
        this.taxaMutacao = taxaMutacao;
        this.local = local;
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public void setTamanhoPopulacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public double getTaxaMutacao() {
        return taxaMutacao;
    }

    public void setTaxaMutacao(double taxaMutacao) {
        this.taxaMutacao = taxaMutacao;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "CASO DE TESTE: [POP: " + tamanhoPopulacao + "]" + "[MUTACAO: " + taxaMutacao + "]"
                + "[LOCAL: " + this.local + " ]";
    }
   
}
