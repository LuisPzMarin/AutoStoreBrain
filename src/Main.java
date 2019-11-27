import com.opencsv.exceptions.CsvValidationException;
import java.util.Collections;
import java.io.IOException;
import java.util.*;



public class Main {


    public static void main(String[] args) throws IOException, CsvValidationException {
        //En primer lugar, creamos el objeto Almacen CLA que es el que guardara las cajas en proceso de llenado
        Almacen CLA = new Almacen();

        String archivoInventario = "Datos/Inventario.csv";
        String archivoCaducables = "Datos/Caducables.csv";
        ArrayList[] contenedor= DataBaseManagerCSV.leerCSV(archivoInventario,archivoCaducables);


        //Eliminemos las piezas que superan el peso y las dimensiones de la caja, para despues ordenar por ventas
        //Collections.sort(contenedor[0]);

        Analisis.descartarYAsignar(contenedor[0]);
        Collections.sort(contenedor[0]);
        ArrayList[] cerradas0=Analisis.empaquetar(contenedor[0],CLA);
        for (int j = 0; j <cerradas0.length ; j++) {
            System.out.println("CAJAS DE TIPO " + j);
            for (int i = 0; i <cerradas0[j].size() ; i++) {
                Caja aux= (Caja)cerradas0[j].get(i);
                aux.imprimirCaja();
            }
        }

        Analisis.descartarYAsignar(contenedor[1]);
        Collections.sort(contenedor[1]);
        Analisis.descartarYAsignar(contenedor[2]);
        Collections.sort(contenedor[2]);
        Analisis.descartarYAsignar(contenedor[3]);
        Collections.sort(contenedor[3]);

/*
        for (int i = 0; i <empaquetar[3].size() ; i++) {
            empaquetar[3].get(i).imprimirCaja();
        }
*/
        //ArrayList<Caja>[] result = Analisis.empaquetar();







    }



}
