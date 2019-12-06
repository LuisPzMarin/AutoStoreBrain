import java.util.ArrayList;
import java.util.Objects;

public class Ref  implements Comparable<Ref>{
    //El SKU es el id por el cual nos referiremos al producto
    private String importador;
    private String marca;
    private String referencia;
    private String SKU;
    private boolean caducable;

    //El maximo, medio y minimo viene dado por datos externo, y con ellos calcularemos la volumetria
    private int maximo;
    private int medio;
    private int minimo;

    //El peso es una variable que usaremos para comprobar que no sobrecargamos la caja
    private double peso;

    //La cantidad nos informa del número de ref. que tenemos iguales actualmente
    private int stockReal;

    //El número de vendidos nos dira si la ref. es de alta o baja rotación.
    private int vendidos;

    //La ubicación actual de la pieza
    private String ubicacion;

    //Variable tipo de caja que se asignara

    private int tipoCaja;

//ACTUALIZA METODO
    public Ref(String SKU, int maximo, int medio, int minimo, double peso, int stockReal, int vendidos,
               String ubicacion, int tipoCaja, String importador,String marca, String referencia, boolean caducable){
        this.SKU=SKU;
        this.maximo = maximo;
        this.medio = medio;
        this.minimo = minimo;
        this.peso=peso;
        this.stockReal=stockReal;
        this.vendidos=vendidos;
        this.ubicacion=ubicacion;
        this.tipoCaja=tipoCaja;
        this.importador=importador;
        this.marca=marca;
        this.referencia=referencia;
        this.caducable=caducable;

    }

    public Ref(){
    }

    public void setImportador(String importador) {
        this.importador = importador;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setCaducable(Boolean caducable) {
        this.caducable = caducable;
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


    public void setVenta(int venta) {
        this.vendidos= vendidos+venta;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setTipoCaja(int tipoCaja) {
        this.tipoCaja = tipoCaja;
    }

    public String getSKU() {
        return SKU;
    }

    public Boolean getCaducable() {
        return caducable;
    }

    public String getImportador() {
        return importador;
    }

    public String getMarca() {
        return marca;
    }

    public double getVolumetria() {
    return maximo*minimo*medio*stockReal;
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

    public int getTipoCaja() {
        return tipoCaja;
    }

    public int getStockReal() {
        return stockReal;
    }

    public int getVendidos() {
        return vendidos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void imprimirRef(){
        String aux="";
        switch (tipoCaja) {
            case 0:
                aux = "1";
                break;
            case 1:
                aux = "2A";
                break;
            case 2:
                aux = "2B";
                break;
            case 3:
                aux = "4";
                break;
            case 4:
                aux = "8";
                break;
            case 5:
                aux = "16";
                break;
            default:

                System.err.println("ERROR IMPRIMIENDO REF");
                System.exit(0);
                break;

        }
        System.out.println("SKU: " + SKU + " Importador: "+ importador + " Marca: " + marca +  " Stock Real: "+ stockReal+ " Máximo: " + maximo+" Medio: "+medio+
                " Caducable: "+ caducable+" Minimo: "+minimo+" Peso: "+peso+" Ubicacion: "+ ubicacion + " Tipo de Caja: "+ aux +
                " Stock Vendido: " + vendidos);
    }

    @Override
    public int compareTo(Ref e){
        if(e.getVendidos()>vendidos){
            return 1;
        }else if(e.getVendidos()==vendidos){
            return 0;
        }else{
            return -1;
        }
    }

    public int BuscarIndexRef(ArrayList <Ref> lista) {
        int resultado = -1;
        for (int i = 0; i <lista.size() ; i++) {
            if (SKU.equals(lista.get(i).SKU) ) {
                resultado = i;
                break;
            }
        }
        return resultado;
    }
}
