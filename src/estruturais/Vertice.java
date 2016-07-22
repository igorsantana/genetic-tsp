package estruturais;

/**
 *
 * @author guest-Qf8Gb5
 */
public class Vertice {
    private String label;
    private double x;
    private double y;

    public Vertice(String label, double x, double y) {
        this.label = label.trim();
        this.x     = x;
        this.y     = y;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label.trim();
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public double distancia(Vertice vertice) {
        double distX = Math.abs(this.x - vertice.getX());
        double distY = Math.abs(this.y - vertice.getY());
        return Math.sqrt(((distX * distX)
                        + (distY * distY)));
                
    }
    
    public static void main(String[] args) {
        System.out.println(Math.sqrt(9));
        System.out.println(Math.sqrt(9));
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