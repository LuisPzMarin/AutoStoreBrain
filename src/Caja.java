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
    public String getID(){
        return ID;
    }

     Ref getRefCaja(int index) {
        return refCaja[index];
    }

     boolean setNuevaRef(Ref nuevaRef) {

        for (int i = 0; i < refCaja.length; i++) {
            if( refCaja[i]==null){
                refCaja[i]= nuevaRef;
                return true;
            }
        }
        return false;
    }

     boolean ultimoHueco(){
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

     int numPiezasCaja(){
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

     int getDivisiones(){
        return refCaja.length;
    }

    int refsPorCaja(){
        int res=0;
        for (int i = 0; i <refCaja.length ; i++) {
            if(refCaja[i]!=null){
                if (refCaja[i].getPeso()>0){
                    res++;
                }
            }
        }
        return res;
    }


}
