import com.opencsv.exceptions.CsvValidationException;
import java.util.Collections;
import java.io.IOException;
import java.util.*;



public class Main {


    public static void main(String[] args) throws IOException, CsvValidationException {
        //En primer lugar, creamos el objeto Almacen CLA que es el que guardara las cajas en proceso de llenado

        String archivoInventario = "Datos/Inventario.csv";
        String archivoCaducables = "Datos/Caducables.csv";

        String archivoCompras_HATO = "Datos/compras_HATO.csv";
        String archivoPedidos_HATO = "Datos/pedidos_HATO.csv";

        String archivoCompras_DAL = "Datos/compras_DAL.csv";
        String archivoPedidos_DAL = "Datos/pedidos_DAL.csv";

        String archivoCompras_DA = "Datos/compras_DA.csv";
        String archivoPedidos_DA = "Datos/pedidos_DA.csv";

        //QUE ESTA PASANDO ENTRE HY Y HC
        String archivoCompras_HY = "Datos/compras_HY.csv";
        String archivoPedidos_HY = "Datos/pedidos_HY.csv";

        ArrayList[] contenedor= DataBaseManagerCSV.leerCSV(archivoInventario,archivoCaducables);


        Almacen CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[0],archivoCompras_HATO,archivoPedidos_HATO);
        Analisis.descartarYAsignar(contenedor[0]);
        Collections.sort(contenedor[0]);
        ArrayList[] cerradasHATO=Analisis.empaquetar(contenedor[0],CLA);


        for (int j = 0; j <cerradasHATO.length ; j++) {
            System.out.println("CAJAS DE TIPO " + Analisis.tipoCajaADiv(j));
            for (int i = 0; i <cerradasHATO[j].size() ; i++) {
                Caja aux= (Caja)cerradasHATO[j].get(i);
                aux.imprimirCaja();
            }
        }

         CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[1],archivoCompras_DAL,archivoPedidos_DAL);
        Analisis.descartarYAsignar(contenedor[1]);
        Collections.sort(contenedor[1]);
        ArrayList[] cerradasDAL=Analisis.empaquetar(contenedor[1],CLA);

        for (int j = 0; j <cerradasDAL.length ; j++) {
            System.out.println("CAJAS DE TIPO " + Analisis.tipoCajaADiv(j));
            for (int i = 0; i <cerradasDAL[j].size() ; i++) {
                Caja aux= (Caja)cerradasDAL[j].get(i);
                aux.imprimirCaja();
            }
        }


        CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[2],archivoCompras_DA,archivoPedidos_DA);
        Analisis.descartarYAsignar(contenedor[2]);
        Collections.sort(contenedor[2]);
        ArrayList[] cerradasDA=Analisis.empaquetar(contenedor[2],CLA);


        for (int j = 0; j <cerradasDA.length ; j++) {
            System.out.println("CAJAS DE TIPO " + Analisis.tipoCajaADiv(j));
            for (int i = 0; i <cerradasDA[j].size() ; i++) {
                Caja aux= (Caja)cerradasDAL[j].get(i);
                aux.imprimirCaja();
            }
        }

        /*
        CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[3],archivoCompras_HY,archivoPedidos_HY);
        Analisis.descartarYAsignar(contenedor[3]);
        Collections.sort(contenedor[3]);
        ArrayList[] cerradasHY=Analisis.empaquetar(contenedor[3],CLA);
        */
    }



}
