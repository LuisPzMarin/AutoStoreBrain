import com.opencsv.exceptions.CsvValidationException;
import java.util.Collections;
import java.io.IOException;
import java.util.*;



public class Main {


    public static void main(String[] args) throws IOException, CsvValidationException {
        //En primer lugar, creamos el objeto Almacen CLA que es el que guardara las cajas en proceso de llenado
        Almacen CLA = new Almacen();

        String archivoDAL = "sku_master_DAL.csv";
        String archivoHY = "";
        String archivoHATO = "";
        String archivoDA = "";


        ArrayList<Ref> lista= DataBaseManagerCSV.leerCSV(archivoDAL);
        Collections.sort(lista);

        //Eliminemos las piezas que superan el peso y las dimensiones de la caja

        lista= Analisis.descartarYAsignar(lista);

        //ArrayList<Caja>[] result = Analisis.empaquetar();







    }



}
