
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


class DataBaseManagerExcel {



     static void escribirExcel (ArrayList<Caja>[] lista,ArrayList<Ref> [] salida, String importador,
                                boolean cad, String dirSalida){

         if(cad){ importador= importador.concat("(CAD)"); }

         if(lista[0].size()+lista[1].size() + lista[2].size()+ lista[3].size() +
                 lista[4].size()+salida[1].size()+ salida[2].size()+salida[3].size() == 0 ){
             System.out.println("No se creo el libro de " + importador + " por falta de datos");
             return;
         }

         /********************************************************************************************
          *
          * CREAMOS EL LIBRO EXCEL
          *
          * ******************************************************************************************/

        //Primero pedimos la hora de ejecución para marcar el documento de salida
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("dd-MM-HH-mm-ss");
        File excelNewFile=null;
        //Aqui indicamos la dirección de salida del excel
         excelNewFile = new File(dirSalida+ importador+ "--"+ hourFormat.format(date)+ ".xls");


        //Antes de crear el excel, comprobamos que este no exista
        if (!excelNewFile.exists()){
            try {
                excelNewFile.createNewFile();
            } catch (IOException ioe) {
                System.out.println("(Error al crear el fichero nuevo)" + ioe);
            }
        }

        OutputStream salidaExcel = null;
        try {

            salidaExcel = new FileOutputStream(excelNewFile);

            // Representación del más alto nivel de la hoja excel.
            HSSFWorkbook hssfWorkbookNew = new HSSFWorkbook();

            /********************************************************************************************
             *
             * CREAMOS LA HOJA DE RESUMEN
             *
             * ******************************************************************************************/

            HSSFSheet resumen;
            resumen = hssfWorkbookNew.createSheet("RESUMEN " + importador);

            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            HSSFRow fila = null;
            // Inicializo el objeto que leerá el valor de la celda
            HSSFCell celdaNueva;

            int aux=1;
            int totalRef=0;
            int totalCajas=0;
            for (int i = 0; i <=6 ; i++) {
                fila = resumen.createRow(i);
                switch (i){
                    case 0:
                        celdaNueva = fila.createCell(1,CellType.STRING);
                        celdaNueva.setCellValue("TIPO");
                        celdaNueva = fila.createCell(2,CellType.STRING);
                        celdaNueva.setCellValue("CAJAS");
                        celdaNueva = fila.createCell(3,CellType.STRING);
                        celdaNueva.setCellValue("REF.");
                        celdaNueva = fila.createCell(5,CellType.STRING);
                        celdaNueva.setCellValue("TIPO");
                        celdaNueva = fila.createCell(6,CellType.STRING);
                        celdaNueva.setCellValue("REF.");
                        break;
                    case 1:
                        celdaNueva = fila.createCell(5  , CellType.STRING);
                        celdaNueva.setCellValue("REF. sin Datos");
                        celdaNueva = fila.createCell(6, CellType.NUMERIC);
                        celdaNueva.setCellValue(salida[2].size());
                        break;
                    case 2:
                        celdaNueva = fila.createCell(1, CellType.STRING);
                        celdaNueva.setCellValue("CAJA P2 B");
                        celdaNueva = fila.createCell(5  , CellType.STRING);
                        celdaNueva.setCellValue("REF. por Peso");
                        celdaNueva = fila.createCell(6, CellType.NUMERIC);
                        celdaNueva.setCellValue(salida[1].size());
                        break;

                    case 3:

                        celdaNueva = fila.createCell(5  , CellType.STRING);
                        celdaNueva.setCellValue("REF. por Tamaño");
                        celdaNueva = fila.createCell(6, CellType.NUMERIC);
                        celdaNueva.setCellValue(salida[3].size());
                        break;

                    case 4:
                        celdaNueva = fila.createCell(5, CellType.STRING);
                        celdaNueva.setCellValue("TOTAL");
                        celdaNueva = fila.createCell(6, CellType.NUMERIC);
                        celdaNueva.setCellValue(salida[1].size()+salida[2].size()+salida[3].size()+salida[4].size());
                        break;
                    case 6:
                        celdaNueva = fila.createCell(1, CellType.STRING);
                        celdaNueva.setCellValue("TOTAL");
                        celdaNueva = fila.createCell(2, CellType.NUMERIC);
                        celdaNueva.setCellValue(totalCajas);
                        celdaNueva = fila.createCell(3, CellType.NUMERIC);
                        celdaNueva.setCellValue(totalRef);
                        break;
                }
                if(i!=6 && i!=0){
                    if (i != 2) {
                        celdaNueva = fila.createCell(1, CellType.STRING);
                        celdaNueva.setCellValue("CAJA P" + aux);
                        aux = aux * 2;
                    }
                    totalCajas+=lista[i].size();
                    int aux2 = Analisis.refsPorLista(lista[i]);
                    totalRef+=aux2;
                    celdaNueva = fila.createCell(2, CellType.NUMERIC);
                    celdaNueva.setCellValue(lista[i].size());
                    celdaNueva = fila.createCell(3, CellType.NUMERIC);
                    celdaNueva.setCellValue(aux2);
                }
            }


            /********************************************************************************************
             *
             * CREAMOS LAS HOJAS DE CERRADAS
             *
             * ******************************************************************************************/



            aux=1;
            for (int i = 0; i <6 ; i++) {
                HSSFSheet Cerradas;
                if(i==2){
                    Cerradas = hssfWorkbookNew.createSheet("CAJA TIPO P2 B");
                }else {
                    Cerradas = hssfWorkbookNew.createSheet("CAJA TIPO P" + aux);
                    aux=aux*2;
                }


                //-----------------------------------------------------------------
                fila = Cerradas.createRow(0);
                celdaNueva = fila.createCell(1,CellType.STRING);
                celdaNueva.setCellValue("TOTAL DE CAJAS");
                celdaNueva = fila.createCell(2,CellType.STRING);
                celdaNueva.setCellValue("TOTAL DE REFERENCIAS");
                celdaNueva = fila.createCell(3,CellType.STRING);
                celdaNueva.setCellValue("TOTAL DE STOCK ALMACENADO");

                fila = Cerradas.createRow(1);
                celdaNueva = fila.createCell(1, CellType.NUMERIC);
                celdaNueva.setCellValue(lista[i].size());
                if(lista[i].size()!=0){
                    celdaNueva = fila.createCell(2, CellType.NUMERIC);
                    celdaNueva.setCellValue(lista[i].size()*lista[i].get(0).getDivisiones());
                }else{
                    celdaNueva = fila.createCell(2, CellType.STRING);
                    celdaNueva.setCellValue("ERROR");
                }


                int counter=0;

                for (int j = 0; j <lista[i].size() ; j++) {
                    counter+=lista[i].get(j).numPiezasCaja();
                }
                celdaNueva = fila.createCell(3, CellType.NUMERIC);
                celdaNueva.setCellValue(counter);




                int contador=0;
                fila = Cerradas.createRow(3);
                celdaNueva = fila.createCell(1,CellType.STRING);
                celdaNueva.setCellValue("CAJA");
                celdaNueva = fila.createCell(2,CellType.STRING);
                celdaNueva.setCellValue("REFERENCIA");
                celdaNueva = fila.createCell(3,CellType.STRING);
                celdaNueva.setCellValue("MARCA");
                celdaNueva = fila.createCell(4,CellType.STRING);
                celdaNueva.setCellValue("DENOMINACIÓN");
                celdaNueva = fila.createCell(5,CellType.STRING);
                celdaNueva.setCellValue("STOCK");
                celdaNueva = fila.createCell(6,CellType.STRING);
                celdaNueva.setCellValue("UBICACIÓN");
                celdaNueva = fila.createCell(7,CellType.STRING);
                celdaNueva.setCellValue("PESO TOTAL (KG)");
                celdaNueva = fila.createCell(8,CellType.STRING);
                celdaNueva.setCellValue("LARGO");
                celdaNueva = fila.createCell(9,CellType.STRING);
                celdaNueva.setCellValue("ANCHO");
                celdaNueva = fila.createCell(10,CellType.STRING);
                celdaNueva.setCellValue("ALTO");
                celdaNueva = fila.createCell(11,CellType.STRING);
                celdaNueva.setCellValue("MOVIMIENTOS");

                for (int j = 0; j <lista[i].size() ; j++) {
                    for (int k = -1; k <lista[i].get(j).getDivisiones() ; k++) {
                        fila = Cerradas.createRow(j+5+contador);
                        if(k==-1){
                            celdaNueva = fila.createCell(1,CellType.STRING);
                            celdaNueva.setCellValue(lista[i].get(j).getID());
                        }else{
                            celdaNueva = fila.createCell(2, CellType.STRING);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getReferencia());
                            celdaNueva = fila.createCell(3, CellType.STRING);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getMarca());
                            celdaNueva = fila.createCell(4, CellType.STRING);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getDenominacion());
                            celdaNueva = fila.createCell(5, CellType.NUMERIC);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getStockReal());
                            celdaNueva = fila.createCell(6, CellType.NUMERIC);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getUbicacion());
                            celdaNueva = fila.createCell(7, CellType.NUMERIC);
                            celdaNueva.setCellValue((lista[i].get(j).getRefCaja(k).getPeso()*
                                    lista[i].get(j).getRefCaja(k).getStockReal())/1000);
                            celdaNueva = fila.createCell(8, CellType.NUMERIC);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getMaximo());
                            celdaNueva = fila.createCell(9, CellType.NUMERIC);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getMedio());
                            celdaNueva = fila.createCell(10, CellType.NUMERIC);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getMinimo());
                            celdaNueva = fila.createCell(11, CellType.NUMERIC);
                            celdaNueva.setCellValue(lista[i].get(j).getRefCaja(k).getMovimientos());
                        }

                        contador++;
                    }
                }

            }

            /********************************************************************************************
             *
             * CREAMOS LAS HOJAS DE DESCARTADAS
             *
             * ******************************************************************************************/


            HSSFSheet sinDatos;
            sinDatos = hssfWorkbookNew.createSheet("REF. sin datos");
            HSSFSheet peso;
            peso = hssfWorkbookNew.createSheet("REF. descartadas por Peso");
            HSSFSheet tam;
            tam = hssfWorkbookNew.createSheet("REF. descartadas por Tamaño");
            HSSFSheet altoStock = null;
            int check=3;
            if(salida[4].size()>0){
                check=4;
                altoStock = hssfWorkbookNew.createSheet("REF. descartadas por alto uso de cajas");
            }

            for (int p = 1; p <=check ; p++) {

                
                switch (p){
                    case 1:
                        fila = peso.createRow(0);
                        break;
                    case 2:
                        fila = sinDatos.createRow(0);
                        break;
                    case 3:
                        fila = tam.createRow(0);
                        break;
                    case 4:

                        fila = altoStock.createRow(0);
                        break;
                    default:
                        System.exit(0);
                        break;

                }
                        
                celdaNueva = fila.createCell(1,CellType.STRING);
                celdaNueva.setCellValue("TOTAL DE REFERENCIAS");
                celdaNueva = fila.createCell(2, CellType.NUMERIC);
                celdaNueva.setCellValue(salida[p].size());

                switch (p){
                    case 1:
                        fila = peso.createRow(3);
                        break;
                    case 2:
                        fila = sinDatos.createRow(3);
                        break;
                    case 3:
                        fila = tam.createRow(3);
                        break;
                    case 4:

                        fila = altoStock.createRow(3);
                        break;
                    default:
                        System.exit(0);
                        break;

                }


                celdaNueva = fila.createCell(1,CellType.STRING);
                celdaNueva.setCellValue("REFERENCIA");
                celdaNueva = fila.createCell(2,CellType.STRING);
                celdaNueva.setCellValue("MARCA");
                celdaNueva = fila.createCell(3,CellType.STRING);
                celdaNueva.setCellValue("DENOMINACIÓN");
                celdaNueva = fila.createCell(4,CellType.STRING);
                celdaNueva.setCellValue("STOCK");
                celdaNueva = fila.createCell(5,CellType.STRING);
                celdaNueva.setCellValue("UBICACIÓN");
                celdaNueva = fila.createCell(6,CellType.STRING);
                celdaNueva.setCellValue("LARGO");
                celdaNueva = fila.createCell(7,CellType.STRING);
                celdaNueva.setCellValue("ANCHO");
                celdaNueva = fila.createCell(8,CellType.STRING);
                celdaNueva.setCellValue("ALTO");

                celdaNueva = fila.createCell(9,CellType.STRING);
                celdaNueva.setCellValue("PESO UNI. (KG)");

                celdaNueva = fila.createCell(10,CellType.STRING);
                celdaNueva.setCellValue("PESO TOTAL (KG)");
                for (int i = 0; i <salida[p].size() ; i++) {

                    switch (p){
                        case 1:
                            fila = peso.createRow(i+4);
                            break;
                        case 2:
                            fila = sinDatos.createRow(i+4);
                            break;
                        case 3:
                            fila = tam.createRow(i+4);
                            break;
                        case 4:
                            fila = altoStock.createRow(i+4);
                            break;
                        default:
                            System.exit(0);
                            break;

                    }

                    celdaNueva = fila.createCell(1, CellType.STRING);
                    celdaNueva.setCellValue(salida[p].get(i).getReferencia());

                    celdaNueva = fila.createCell(2, CellType.STRING);
                    celdaNueva.setCellValue(salida[p].get(i).getMarca());

                    celdaNueva = fila.createCell(3, CellType.STRING);
                    celdaNueva.setCellValue(salida[p].get(i).getDenominacion());

                    celdaNueva = fila.createCell(4, CellType.NUMERIC);
                    celdaNueva.setCellValue(salida[p].get(i).getStockReal());

                    celdaNueva = fila.createCell(5, CellType.STRING);
                    celdaNueva.setCellValue(salida[p].get(i).getUbicacion());

                    celdaNueva = fila.createCell(6, CellType.NUMERIC);
                    celdaNueva.setCellValue(salida[p].get(i).getMaximo());

                    celdaNueva = fila.createCell(7, CellType.NUMERIC);
                    celdaNueva.setCellValue(salida[p].get(i).getMedio());

                    celdaNueva = fila.createCell(8, CellType.NUMERIC);
                    celdaNueva.setCellValue(salida[p].get(i).getMinimo());

                    celdaNueva = fila.createCell(9, CellType.NUMERIC);
                    celdaNueva.setCellValue(salida[p].get(i).getPeso()/1000);

                    celdaNueva = fila.createCell(10, CellType.NUMERIC);
                    celdaNueva.setCellValue(salida[p].get(i).getPeso()*
                            salida[p].get(i).getStockReal()/1000);
                }
            }



            /********************************************************************************************
             *
             * CERRAMOS EL LIBRO
             *
             * ******************************************************************************************/




            hssfWorkbookNew.write(salidaExcel);
            salidaExcel.close();
            System.out.println("Se ha generado el libro excel de " + importador);
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("No se encontró el fichero: " + fileNotFoundException);
        } catch (IOException ex) {
            System.err.println("Error al procesar el fichero: " + ex);

        }
    }

}