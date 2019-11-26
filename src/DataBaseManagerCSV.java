import org.apache.commons.csv.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class DataBaseManagerCSV {

    public static ArrayList[] leerCSV(String archivo, String archivoCaducables) throws IOException {

        //En primer lugar recorreremos el archivo de caducables, para luego comparar si las ref. son caducables
        List<String> caducables = new ArrayList<>();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(archivoCaducables), StandardCharsets.ISO_8859_1);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';'));
        ) {
            //En primer lugar analizaremos el head del documento para parsear los datos correctamente
            int head =1;
            for (CSVRecord csvRecord : csvParser) {
                if(head ==1){
                    head=0;
                    continue;
                }
                caducables.add(csvRecord.get(0));
                if(csvRecord.get(0).equals("")){
                    break;
                }
            }
        }



        //Creamos el Array que contendra las diferentes listas  que contendra todos los valores
        ArrayList[] contenedor = new ArrayList[4];
        //En el cero meteremos a HATO
        contenedor[0]= new ArrayList<Ref>();
        //En el cero meteremos a DAL
        contenedor[1]= new ArrayList<Ref>();
        //En el cero meteremos a DA
        contenedor[2]= new ArrayList<Ref>();
        //En el cero meteremos a HC
        contenedor[3]= new ArrayList<Ref>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(archivo), StandardCharsets.ISO_8859_1);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';'));
        ) {


            //Los datos se encuentran en formato europeo, por lo que hay que cambiar el formato predeterminado de Java
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number =null;

            //En primer lugar analizaremos el head del documento para parsear los datos correctamente
            int head =1;
            for (CSVRecord csvRecord : csvParser) {
                if(head ==1){
                    head=0;
                    continue;
                }

                //Creamos una referencia auxiliar que luego guardaremos en el ArrayList
                Ref aux= new Ref();

                //Leemos el id para comprobar si hay ref. o hemos llegado al final del documento
                String importador = csvRecord.get(0);
                String marca = csvRecord.get(1);
                String referencia = csvRecord.get(2);
                if(importador.equals("")){
                    break;
                }
                aux.setSKU(marca.concat("-").concat(referencia));
                aux.setImportador(importador);
                aux.setMarca(marca);
                aux.setReferencia(referencia);

                //Comprobaremos si la ref. tiene cantidad
                String stockDisponible=csvRecord.get(11);
                try {
                    number = format.parse(stockDisponible);
                } catch (ParseException e){
                    System.out.println("ERROR: No se pudo parsear el número");
                }

                if(number.doubleValue()== 0.0){
                    continue;
                }
                aux.setStockReal((int)(number.doubleValue()));

                //Leeremos y analizaremos cual es el Largo, el Ancho, el Alto y el peso. Si lo hubiese
                if(!csvRecord.get(3).equals("") && !csvRecord.get(4).equals("0")){

                    String largoSt=csvRecord.get(3);
                    String anchoSt=csvRecord.get(4);
                    String altoSt=csvRecord.get(5);
                    String peso=csvRecord.get(10);
                    Number number1 = null;
                    Number number2 = null;
                    Number number3 = null;
                    Number number4 = null;
                    try {
                        number1 = format.parse(largoSt);
                        number2 = format.parse(anchoSt);
                        number3 = format.parse(altoSt);
                        number4 = format.parse(peso);
                    } catch (ParseException e){
                        System.out.println("ERROR: No se pudo parsear el largo, alto y ancho");
                    }

                    Double max= Math.max(Math.max(number1.doubleValue(),number2.doubleValue()),number3.doubleValue());
                    Double min= Math.min(Math.min(number1.doubleValue(),number2.doubleValue()),number3.doubleValue());

                    Double med=0.0;
                    if((max==number1.doubleValue()||min==number1.doubleValue())&&(max==number2.doubleValue()||min==number2.doubleValue())){
                        med= number3.doubleValue();
                    }
                    if((max==number1.doubleValue()||min==number1.doubleValue())&&(max==number3.doubleValue()||min==number3.doubleValue())){
                        med= number2.doubleValue();
                    }

                    if((max==number2.doubleValue()||min==number2.doubleValue())&&(max==number3.doubleValue()||min==number3.doubleValue())){
                        med= number1.doubleValue();
                    }
                    aux.setMaximo(max.intValue());
                    aux.setMinimo(min.intValue());
                    aux.setMedio(med.intValue());
                    aux.setPeso(number4.doubleValue());

                    //INVENTAMOS LAS VENTAS POR FALTA DE DATOS

                    aux.setVendidos((int)(Math.random()*999 + 1));

                }
                //Cargaremos en la variable la ubicación de la pieza
                aux.setUbicacion(csvRecord.get(8));

                //Por último comprobaremos si la ref. se considera caducable o no

                if(caducables.contains(aux.getReferencia())){
                    aux.setCaducable(true);
                }else{
                    aux.setCaducable(false);
                }
                aux.imprimirRef();
                //Cargamos esta nueva referencia en el mapa
                switch ( importador ) {
                    case "HATO":
                        contenedor[0].add(aux);
                        break;
                    case "DAL":
                        contenedor[1].add(aux);
                        break;
                    case "DA":
                        contenedor[2].add(aux);
                        break;
                    case "HC":
                        contenedor[3].add(aux);

                        break;
                    default:
                        System.err.println("error" );
                        break;
                }


                }
            }
        return contenedor;
        }

    public static ArrayList <Ref> leerVentasCSV(String archivo,ArrayList <Ref> lista){


        return null;
        }

    }