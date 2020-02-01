

public class Ref  implements Comparable<Ref>{
    //El SKU es el id por el cual nos referiremos al producto
    private String importador;
    private String marca;
    private String referencia;
    private String SKU;
    private boolean caducable;
    private String denominacion;

    //El maximo, medio y minimo viene dado por datos externo, y con ellos calcularemos la volumetria
    private int maximo;
    private int medio;
    private int minimo;

    //El peso es una variable que usaremos para comprobar que no sobrecargamos la caja
    private double peso;

    //La cantidad nos informa del número de ref. que tenemos iguales actualmente
    private int stockReal;

    //El número de vendidos nos dira si la ref. es de alta o baja rotación.
    private int movimientos;

    //La ubicación actual de la pieza
    private String ubicacion;

    //Variable tipo de caja que se asignara
    private int tipoCaja;

    //Método constructor de referencias, se deben de incluir todos los parametros de la var o ninguno
    public Ref(String SKU, int maximo, int medio, int minimo, double peso, int stockReal, int movimientos,
               String ubicacion, int tipoCaja, String importador,String marca, String referencia, boolean caducable,
               String denominacion){
        this.SKU=SKU;
        this.maximo = maximo;
        this.medio = medio;
        this.minimo = minimo;
        this.peso=peso;
        this.stockReal=stockReal;
        this.movimientos = movimientos;
        this.ubicacion=ubicacion;
        this.tipoCaja=tipoCaja;
        this.importador=importador;
        this.marca=marca;
        this.referencia=referencia;
        this.caducable=caducable;
        this.denominacion=denominacion;
    }
    public Ref(){}

    void setImportador(String importador) {
        this.importador = importador;
    }

    void setMarca(String marca) {
        this.marca = marca;
    }

    void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    void setCaducable(Boolean caducable) {
        this.caducable = caducable;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    void setMedio(int medio) {
        this.medio = medio;
    }

    void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    void setStockReal(int stockReal) {
        this.stockReal = stockReal;
    }

    void setPeso(double peso) {
        this.peso = peso;
    }

    void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    void setMovimiento() {
        this.movimientos++;
    }

    void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    void setTipoCaja(int tipoCaja) {
        this.tipoCaja = tipoCaja;
    }

    String getSKU() {
        return SKU;
    }

    String getDenominacion() {
        return denominacion;
    }

    Boolean getCaducable() {
        return caducable;
    }

    String getImportador() {
        return importador;
    }

    String getMarca() {
        return marca;
    }
    //Volumetria real calculada gracias a la volumetria unitaria multiplicada por el stock
    double getVolumetria() {
    return maximo*minimo*medio*stockReal;
    }

    double getPeso() {
        return peso;
    }

    int getMaximo() {
        return maximo;
    }

    int getMedio() {
        return medio;
    }

    int getMinimo() {
        return minimo;
    }

    int getTipoCaja() {
        return tipoCaja;
    }

    int getStockReal() {
        return stockReal;
    }

    int getMovimientos() {
        return movimientos;
    }

    String getUbicacion() {
        return ubicacion;
    }

    String getReferencia() {
        return referencia;
    }
    //Método de uso interno para imprimir las ref. con todos sus parametros
    void imprimirRef(){
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
                " Stock Vendido: " + movimientos);
    }

    @Override
    public int compareTo(Ref e){
        return Integer.compare(e.getMovimientos(), movimientos);
    }

}
