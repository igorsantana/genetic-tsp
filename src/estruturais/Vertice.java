package estruturais;

/**
 * <p>Classe <b>Vertice</b>.</p>
 * <p>Classe responsavel por representar um <b>Vertice</b> da Rota.</p>
 * @author Igor
 * @author Leandro
 * @since  01/08/2016
 */
class Vertice {
    private String label;
    private double x;
    private double y;

    /**
     * Metodo construtor padrao da Classe.
     * @param label Rotulo do Vertice.
     * @param x Coordenada X do Vertice.
     * @param y Coordenada Y do Vertice.
     */
    public Vertice(String label, double x, double y) {
        this.label = label.trim();
        this.x     = x;
        this.y     = y;
    }

    /**
     * Metodo responsavel por retornar o Label do Vertice.
     * @return Label do Vertice.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Metodo responsavel por definir o Label do Vertice.
     * @param label Label do Vertice.
     */
    public void setLabel(String label) {
        this.label = label.trim();
    }

    /**
     * Metodo responsavel por retornar a Coordenada X do Vertice.
     * @return Coordenada X do Vertice.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Metodo responsavel por definir a Coordenada X do Vertice.
     * @param x Coordenada X do Vertice.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Metodo responsavel por retornar a Coordenada Y do Vertice.
     * @return Coordenada Y do Vertice.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Metodo responsavel por definir a Coordenada Y do Vertice.
     * @param y Coordenada Y do Vertice.
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * Metodo responsavel por retornar a Distancia Cartesiana entre um Par de Vertices.
     * @param vertice Vertice a ser comparado.
     * @return Valor da Distancia Cartesiano.
     */
    public double distancia(Vertice vertice) {
        double distX = Math.abs(this.x - vertice.getX());
        double distY = Math.abs(this.y - vertice.getY());
        return Math.sqrt(((distX * distX)
                        + (distY * distY)));
                
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof Vertice == false) return false;
        Vertice other = (Vertice) object;
        return this.label.trim().equals(other.getLabel().trim());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
    
    @Override
    public String toString() {
        return this.label + " (" + this.x + ", " + this.y + ")";
    }
}