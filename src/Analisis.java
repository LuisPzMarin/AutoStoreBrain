import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Analisis {

    public static Integer MaxCaja= 603;
    public static Integer MedCaja= 403;
    public static Integer MinCaja= 310;
    public static Integer Peso= 30;

    public static LinkedHashMap<Ref,Integer> refNoValidas(LinkedHashMap<Ref,Integer> lista){

        LinkedHashMap <Ref,Integer> descartados = new LinkedHashMap <>();
        Iterator ite = lista.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            Ref linea = (Ref)entry.getKey();

            if(linea.getMaximo()> Analisis.MaxCaja || linea.getMedio()> Analisis.MedCaja ||
            linea.getMinimo()> Analisis.MinCaja || linea.getPeso()> Analisis.Peso){

                descartados.put(linea,(Integer)entry.getValue());
            }
        }

        ite = descartados.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            Ref linea = (Ref)entry.getKey();
            lista.remove(linea);
            linea.imprimirRef();
        }

        //Enviamos los descartados a DataBaseManagerExcel
        DataBaseManagerExcel.escribirExcel(descartados,"Descartados");


        return lista;
    }

    public static Caja opcionCaja(Ref refParaAnalisis){

        //Primero calcularemos las medidas del la subdivisión de 16 e iremos probando desde
        //la más pequeña a la más grande
        int max16= Analisis.MaxCaja/8;
        int med16= Analisis.MedCaja/8;
        int min16= Analisis.MinCaja/8;
        int tipoCaja=0;
        /**
         *
         * SOLUCIONAR MAÑANA
        for (int i = 1; i <=4 ; i=i*2) {
            if(refParaAnalisis.getMaximo()< max16*i && refParaAnalisis.getMedio()<med16*i &&
                refParaAnalisis.getMinimo() < min16*i){
                tipoCaja=16/i

            }
        }
         **/

        return new Caja(1);
    }
}
