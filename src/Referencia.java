public class Referencia {
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


    public Referencia(int SKU, double largo, double ancho, double alto, double peso, int cantidad){
        this.SKU=SKU;
        this.largo=largo;
        this.ancho=ancho;
        this.alto=alto;
        this.peso=peso;
        this.cantidad=cantidad;
        this.volumetria=largo*alto*ancho;
    }

    public double getVolumetria() {
    return volumetria;
    }
}
