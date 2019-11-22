public class Ref {
    //El SKU es el id por el cual nos referiremos al producto
    private int SKU;

    //El largo, ancho y alto viene dado por datos externo, y con ellos calcularemos la volumetria
    private double largo;
    private double ancho;
    private double alto;
    private double volumetria;

    //El peso es una variable que usaremos para comprobar que no sobrecargamos la caja
    private double peso;

    //La cantidad nos informa del número de ref. que tenemos iguales actualmente
    private int cantidad;

    //El número de vendidos nos dira si la ref. es de alta o baja rotación.
    private int vendidos;

    private int [] subgrupos;

    public Ref(int SKU, double largo, double ancho, double alto, double peso, int cantidad, int vendidos){
        this.SKU=SKU;
        this.largo=largo;
        this.ancho=ancho;
        this.alto=alto;
        this.peso=peso;
        this.cantidad=cantidad;
        this.volumetria=largo*alto*ancho;
    }

    public Ref(){
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setSubgrupos(int[] subgrupos) {
        this.subgrupos = subgrupos;
    }

    public void setVendidos(int vendidos) {
        this.vendidos = vendidos;
    }

    public void setVolumetria(double volumetria) {
        this.volumetria = volumetria;
    }

    public double getVolumetria() {
    return volumetria;
    }

    public int getSKU() {
        return SKU;
    }
}
