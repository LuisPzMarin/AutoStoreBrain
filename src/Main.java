import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.IOException;
import java.util.*;



public class Main {


    public static void main(String[] args) throws IOException, CsvValidationException {

        String archivo = "sku_master_DAL.csv";
        HashMap<Ref, Integer> Lista= DataBaseManagerCSV.leerCSV(archivo);



    }



}
