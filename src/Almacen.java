public class Almacen {

    private Caja P1;
    private Caja P2;
    private Caja P4;
    private Caja P8;
    private Caja P16;


    public Almacen(){
        this.P1=new Caja(1);
        this.P2=new Caja(2);
        this.P4=new Caja(4);
        this.P8=new Caja(8);
        this.P16=new Caja(16);
    }

    public void setP1(Caja p1) {
        P1 = p1;
    }

    public void setP2(Caja p2) {
        P2 = p2;
    }
}
