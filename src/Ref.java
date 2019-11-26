import java.util.ArrayList;
import java.util.Objects;

public class Ref  implements Comparable<Ref>{
    //El SKU es el id por el cual nos referiremos al producto
    private String importador;
    private String marca;
    private String referencia;
    private String SKU;
    private Boolean caducable;

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
               String ubicacion, int tipoCaja){
        this.SKU=SKU;
        this.maximo = maximo;
        this.medio = medio;
        this.minimo = minimo;
        this.peso=peso;
        this.stockReal=stockReal;
        this.vendidos=vendidos;
        this.ubicacion=ubicacion;
        this.tipoCaja=tipoCaja;
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


    public void setVendidos(int vendidos) {
        this.vendidos = vendidos;
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
        System.out.println("SKU: " + SKU + " Importador: "+ importador + " Marca: " + marca +  " Stock Real: "+ stockReal+ " Máximo: " + maximo+" Medio: "+medio+
                " Caducable: "+ caducable+" Minimo: "+minimo+" Peso: "+peso+" Ubicacion: "+ ubicacion + " Tipo de Caja: "+ tipoCaja +
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
