import java.io.File;
import java.util.*;



public class Main {


    public static void main(String[] args) {


        //String Dir = "OD DEMATIC - DATOS V1.1.ods";
        String Dir = "TEST.ods";
        File file = new File(Dir);

        DataBaseManager unC = new DataBaseManager();
        unC.leer(file);





        /**
        Ref ref1= new Ref(1,10,20,4,20,1, 0);


        Ref ref2= new Ref(2,10,20,4,20,1, 0);


        Ref ref3= new Ref(3,10,20,4,20,1, 0);



        Map<Ref,Integer> datos = new HashMap<>();

        datos.put(ref1,new Integer(1));
        datos.put(ref2,new Integer(3));
        datos.put(ref3,new Integer(2));

        Map<Ref,Integer> datos2 = sortByValue(datos);

        for (Ref aux: datos2.keySet()){
            System.out.println(aux.getSKU());
        }
        **/

        


    }



    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


}
