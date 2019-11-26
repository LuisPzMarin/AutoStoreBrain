import com.opencsv.exceptions.CsvValidationException;
import java.util.Collections;
import java.io.IOException;
import java.util.*;



public class Main {


    public static void main(String[] args) throws IOException, CsvValidationException {
        //En primer lugar, creamos el objeto Almacen CLA que es el que guardara las cajas en proceso de llenado
        Almacen CLA = new Almacen();

        String archivoInventario = "Inventario.csv";
        String archivoCaducables = "Datos/Caducables.csv";
        ArrayList[] contenedor= DataBaseManagerCSV.leerCSV(archivoInventario,archivoCaducables);
        Collections.sort(contenedor[0]);

        //Eliminemos las piezas que superan el peso y las dimensiones de la caja

        //contenedor= Analisis.descartarYAsignar(contenedor);

        //ArrayList<Caja>[] result = Analisis.empaquetar();







    }



}
