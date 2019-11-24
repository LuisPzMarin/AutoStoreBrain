import com.opencsv.exceptions.CsvValidationException;

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


        LinkedHashMap<Ref, Integer> lista= DataBaseManagerCSV.leerCSV(archivoDAL);

        //Eliminemos las piezas que superan el peso y las dimensiones de la caja

        Caja aux= Analisis.opcionCaja(CLA,lista);
        lista= Analisis.refNoValidas(lista);







    }



}
