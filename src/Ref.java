public class Ref {
    //El SKU es el id por el cual nos referiremos al producto
    private String SKU;

    //El maximo, medio y minimo viene dado por datos externo, y con ellos calcularemos la volumetria
    private int maximo;
    private int medio;
    private int minimo;
    private double volumetria;

    //El peso es una variable que usaremos para comprobar que no sobrecargamos la caja
    private double peso;

    //La cantidad nos informa del número de ref. que tenemos iguales actualmente
    private int stockReal;

    //El número de vendidos nos dira si la ref. es de alta o baja rotación.
    private int vendidos;

    //La ubicación actual de la pieza
    private String ubicacion;


    public Ref(String SKU, int maximo, int medio, int minimo, double peso, int stockReal, int vendidos, String ubicacion){
        this.SKU=SKU;
        this.maximo = maximo;
        this.medio = medio;
        this.minimo = minimo;
        this.peso=peso;
        this.stockReal=stockReal;
        this.volumetria= maximo * minimo * medio;
        this.vendidos=vendidos;
        this.ubicacion=ubicacion;
    }

    public Ref(){
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public void setMedio(int medio) {
        this.medio = medio;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public void setStockReal(int stockReal) {
        this.stockReal = stockReal;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }


    public void setVendidos(int vendidos) {
        this.vendidos = vendidos;
    }

    public void setVolumetria(double volumetria) {
        this.volumetria = volumetria;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getSKU() {
        return SKU;
    }

    public double getVolumetria() {
    return maximo*minimo*medio;
    }

    public double getPeso() {
        return peso;
    }

    public int getMaximo() {
        return maximo;
    }

    public int getMedio() {
        return medio;
    }

    public int getMinimo() {
        return minimo;
    }

    public void imprimirRef(){
        System.out.println("SKU: " + SKU+ " Stock Real: "+ stockReal+ " Máximo: " + maximo+" Medio: "+medio+
                " Minimo: "+minimo+" Peso: "+peso+" Ubicación: "+ ubicacion);
    }
}
