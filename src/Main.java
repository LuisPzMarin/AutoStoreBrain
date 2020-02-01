import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collections;
import java.io.IOException;
import java.util.*;



public class Main {


    public static void main(String[] args) throws Exception {
        /**VARIABLES QUE MANEJA EL USUARIO**/
        //Si la varible es true, eliminara las cajas con menos de 4 elementos
        boolean menosDe4=false  ;
        double seguridadVolumetria=0.1;
        int seguridadMedida=5;
        int numCajasMax=0;
        String dirsalida= "C:\\Users\\luisp\\Desktop\\TEST\\";


        //RUTAS DE LOS ARCHIVOS UTILIZADOS


        String[][] dirDB = new String[5][2];

        dirDB[0][0]= "Datos/compras_HATO.csv";
        dirDB[0][1]= "Datos/pedidos_HATO.csv";

        dirDB[1][0]= "Datos/compras_DAL.csv";
        dirDB[1][1]= "Datos/pedidos_DAL.csv";

        dirDB[2][0]= "Datos/compras_DA.csv";
        dirDB[2][1]= "Datos/pedidos_DA.csv";

        dirDB[3][0]= "Datos/compras_HY.csv";
        dirDB[3][1]= "Datos/pedidos_HY.csv";


        dirDB[4][0] = "Datos/Inventario.csv";
        dirDB[4][1] = "Datos/Caducables.csv";

        ejecucionPrincipal(dirDB, numCajasMax,menosDe4,dirsalida);




    }

    public static void ejecucionPrincipal(String[][] dirBD, int numCajasMax, boolean menosDe4, String dirsalida)
            throws Exception {

        /**Creamos un contenedor de listas que guarde las 8 listas que usaremos**/
        /**LEEMOS EL DOCUMENTO INVENTARIO Y GUARDAMOS LOS DATOS EN LISTAS
         * 0.HATO
         * 1.DAL
         * 2.DA
         * 3.HC
         * 4.HATO (C)
         * 5.DAL (C)
         * 6.DA (C)
         * 7.HC (C)
         * **/


        ArrayList[] contenedor= DataBaseManagerCSV.leerCSV(dirBD[4][0],dirBD[4][1]);

        for (int i = 0; i <4 ; i++) {

            /** 1º Leemos el documento pedidos y compras de el importador **/

            DataBaseManagerCSV.leerVentasCSV(contenedor[i],dirBD[i][0],dirBD[i][1]);
            DataBaseManagerCSV.leerVentasCSV(contenedor[4+i],dirBD[i][0],dirBD[i][1]);

            /** 2º Descartamos las ref. que no puedan incluirse en el autostore **/
            ArrayList<Ref> [] salida= Analisis.descartarYAsignar(contenedor[i],numCajasMax);
            ArrayList<Ref> [] salidaCad= Analisis.descartarYAsignar(contenedor[4+i],numCajasMax);

            /** 3º Ordenamos las listas por número de movimientos **/
            Collections.sort(salida[0]);
            Collections.sort(salidaCad[0]);

            /** 4º Dividimos las referencias en cajas **/
            Almacen CLA = new Almacen();
            ArrayList[] cerradas=Analisis.empaquetar(salida[0],CLA);
            CLA = new Almacen();
            ArrayList[] cerradasCad=Analisis.empaquetar(salidaCad[0],CLA);

            /** 5º Eliminamos las cajas con menos de cuatro items, si así se desea **/
            if(menosDe4){
                salida[4] =Analisis.menosDeCuatro(cerradas, salida[4]);
                salidaCad[4] =Analisis.menosDeCuatro(cerradasCad, salidaCad[4]);
            }
            DataBaseManagerExcel.escribirExcel(cerradas,salida,salida[0].get(0).getImportador(),
                    false,dirsalida);
            DataBaseManagerExcel.escribirExcel(cerradasCad,salidaCad,salida[0].get(0).getImportador(),
                    true,dirsalida);

        }


/*

        CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[1],archivoCompras_DAL,archivoPedidos_DAL);
        ArrayList<Ref> [] DALSalida= Analisis.descartarYAsignar(contenedor[1],numCajasMax);
        Collections.sort(contenedor[1]);
        ArrayList[] cerradasDAL=Analisis.empaquetar(contenedor[1],CLA);
        if(menosDe4){
            DALSalida[1] =Analisis.menosDeCuatro(cerradasDAL, DALSalida[1]);
        }
        DataBaseManagerExcel.escribirExcel(cerradasDAL,DALSalida,"DAL");


        CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[3],archivoCompras_HY,archivoPedidos_HY);
        ArrayList<Ref> [] HYSalida=Analisis.descartarYAsignar(contenedor[3],numCajasMax);
        Collections.sort(contenedor[3]);
        ArrayList[] cerradasHY=Analisis.empaquetar(contenedor[3],CLA);
        if(menosDe4){
            HYSalida[1] =Analisis.menosDeCuatro(cerradasHY, HYSalida[1]);
        }
        DataBaseManagerExcel.escribirExcel(cerradasHY,HYSalida,"HY");



        CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[2],archivoCompras_DA,archivoPedidos_DA);
        ArrayList<Ref> [] DASalida= Analisis.descartarYAsignar(contenedor[2],numCajasMax);
        Collections.sort(contenedor[2]);
        ArrayList[] cerradasDA=Analisis.empaquetar(contenedor[2],CLA);
        if(menosDe4){
            DASalida[1] =Analisis.menosDeCuatro(cerradasDA, DASalida[1]);
        }
        DataBaseManagerExcel.escribirExcel(cerradasDA,DASalida,"DA");





        CLA = new Almacen();
        DataBaseManagerCSV.leerVentasCSV(contenedor[3],archivoCompras_HY,archivoPedidos_HY);
        Analisis.descartarYAsignar(contenedor[3]);
        Collections.sort(contenedor[3]);
        ArrayList[] cerradasHY=Analisis.empaquetar(contenedor[3],CLA);

        for (int j = 0; j <cerradasHATO.length ; j++) {
            System.out.println("CAJAS DE TIPO " + Analisis.tipoCajaADiv(j));
            for (int i = 0; i <cerradasHATO[j].size() ; i++) {
                Caja aux= (Caja)cerradasHATO[j].get(i);
                aux.imprimirCaja();
            }
        }


        for (int j = 0; j <cerradasDAL.length ; j++) {
            System.out.println("CAJAS DE TIPO " + Analisis.tipoCajaADiv(j));
            for (int i = 0; i <cerradasDAL[j].size() ; i++) {
                Caja aux= (Caja)cerradasDAL[j].get(i);
                aux.imprimirCaja();
            }
        }

        for (int j = 0; j <cerradasDA.length ; j++) {
            System.out.println("CAJAS DE TIPO " + Analisis.tipoCajaADiv(j));
            for (int i = 0; i <cerradasDA[j].size() ; i++) {
                Caja aux= (Caja)cerradasDAL[j].get(i);
                aux.imprimirCaja();
            }
        }

        **/
    }



}
