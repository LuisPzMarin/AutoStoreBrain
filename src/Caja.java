public class Caja {

    private Ref[] refCaja;


    public Caja (int divisiones){
        refCaja = new Ref[divisiones];
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


    public int getDivisiones(){
        return refCaja.length;
    }
}
