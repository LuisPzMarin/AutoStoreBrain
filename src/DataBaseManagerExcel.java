import java.io.File;
import java.util.*;
import java.io.IOException;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class DataBaseManagerExcel {

    public static void leerExcel(File archivo) {

        //Creamos la Hoja SKU MASTER
        Sheet hojaSkuMaster;
        System.out.println("PENE");
        try {
            //Abrimos el archivo y descargamos la hoja entera
            hojaSkuMaster = SpreadSheet.createFromFile(archivo).getSheet(0);
            //Creamos el TreeMap que contendra todos los valores
            TreeMap <Ref,Integer> Lista = new TreeMap<Ref, Integer>();

            System.out.println("PENE");

            //Iteraremos por cada fila guardando los valores
            for (int fila = 1; fila < 2; fila++) {

                //Creamos una referencia auxiliar que luego guardaremos en el hash
                Ref aux= new Ref();

                //Insertamos los valores leidos
                aux.setSKU(( hojaSkuMaster.getCellAt(0, fila).getTextValue()));
                aux.setStockReal(((Integer.parseInt(hojaSkuMaster.getCellAt(1, fila).getTextValue()))));


                System.out.println(hojaSkuMaster.getCellAt(1, fila).getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escribirExcel (LinkedHashMap<Ref,Integer> lista, String motivo){

    }
}