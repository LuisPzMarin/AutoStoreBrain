public class Caja {

    private Referencia [] referenciasCaja;


    public Caja (int divisiones){
        referenciasCaja = new Referencia[divisiones];
    }

    public boolean setNuevaReferencia(Referencia nuevaReferencia) {

        for (int i = 0; i < referenciasCaja.length; i++) {
            if( referenciasCaja[i]==null){
                referenciasCaja[i]= nuevaReferencia;
                return true;
            }
        }
        return false;
    }

    public boolean setNuevaReferencia(Referencia nuevaReferencia, int pos) {
        referenciasCaja[pos]=nuevaReferencia;
        return true;
    }

    public int getDivisiones(){
        return referenciasCaja.length;
    }
}
