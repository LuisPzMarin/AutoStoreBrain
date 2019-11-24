import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class ReferenciasNoValidas {



    public static Integer MaxCaja= 603;
    public static Integer MedCaja= 403;
    public static Integer MinCaja= 310;
    public static Integer Peso= 30;

    public static LinkedHashMap<Ref,Integer> pesoYTamaño(LinkedHashMap<Ref,Integer> lista){

        LinkedHashMap <Ref,Integer> descartados = new LinkedHashMap <>();
        Iterator ite = lista.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            Ref linea = (Ref)entry.getKey();

            if(linea.getMaximo()> ReferenciasNoValidas.MaxCaja || linea.getMedio()>ReferenciasNoValidas.MedCaja ||
            linea.getMinimo()> ReferenciasNoValidas.MinCaja || linea.getPeso()>ReferenciasNoValidas.Peso){

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
}
