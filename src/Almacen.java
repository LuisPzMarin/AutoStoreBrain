public class Almacen {

    private Caja [] P;


    public Almacen(){
        this.P = new Caja[6];
        P[0]=new Caja(1);

        P[1]=new Caja(2);

        P[2]=new Caja(2);

        P[3]=new Caja(4);

        P[4]=new Caja(8);

        P[5]=new Caja(16);
        /*
        * P1=0
        * P2A=1
        * P2B=2
        * P4=3
        * P8=4
        * P16=5
        * */
    }

    public Caja[] getP() {
        return P;
    }

    public void setP(Caja cajaVacia, int index) {
        this.P[index]=cajaVacia;
    }

    public void añadirRefACaja(Ref referencia, int aux){
        P[aux].setNuevaRef(referencia);

    }

    public void setSig(Caja sig, int aux){
        P[aux].setSiguiente(sig);
    }
    public void setAnt(Caja ant, int aux){
        P[aux].setAnterior(ant);
    }
}
