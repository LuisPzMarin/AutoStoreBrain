public class Caja {
    private String ID;
    private Ref[] refCaja;



    public Caja (int divisiones){
        refCaja = new Ref[divisiones];
        for (int i = 0; i <refCaja.length ; i++) {
            refCaja[i]=null;
        }
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean setNuevaRef(Ref nuevaRef) {

        for (int i = 0; i < refCaja.length; i++) {
            if( refCaja[i]==null){
                refCaja[i]= nuevaRef;
                return true;
            }
        }
        return false;
    }

    public boolean ultimoHueco(){
        int contador=0;
        for (int i = 0; i <refCaja.length ; i++) {

            if(refCaja[i]==null){
                contador+= 1;
            }
        }
        if(contador==1){
            return true;
        }


        return false;

    }

    public int numPiezasCaja(){
        int result=0;
        if(refCaja.length==0){
            return 0;
        }
        for (int i = 0; i <refCaja.length ; i++) {
            if(refCaja[i]==null){
                return result;
            }
            result+=refCaja[i].getStockReal();
        }
        return result;
    }

    public int getDivisiones(){
        return refCaja.length;
    }



    public void imprimirCaja(){
        if(refCaja[0]==null){
            System.out.println("ATENCIÓN: CAJA VACIA");
            return;
        }
        System.out.println("LA CAJA " + ID + " DEL IMPORTADOR " + refCaja[0].getImportador() + " CONTIENE:");
        for (int i = 0; i <refCaja.length ; i++) {
            if(refCaja[i]==null){
                System.out.println("ATENCIÓN: CAJA NO LLENA");
            }
            refCaja[i].imprimirRef();
        }

        System.out.println();
    }
}
