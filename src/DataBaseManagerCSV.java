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

    public static ArrayList<Ref> leerCSV(String archivo) throws IOException {
        //Creamos el TreeMap que contendra todos los valores
        ArrayList<Ref> lista = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(archivo), StandardCharsets.ISO_8859_1);

                CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';'));
        ) {

            int head =1;
            //Los datos se encuentran en formato europeo, por lo que hay que cambiar el formato predeterminado de Java
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number =null;
            for (CSVRecord csvRecord : csvParser) {
                if(head ==1){
                    head=0;
                    continue;
                }

                //Creamos una referencia auxiliar que luego guardaremos en el hash
                Ref aux= new Ref();

                //Leemos el id para comprobar si hay ref. o hemos llegado al final del documento
                String sku = csvRecord.get(0);
                if(sku.equals("")){
                    break;
                }
                aux.setSKU(sku);

                //Comprobaremos si la ref. tiene cantidad
                String stockReal=csvRecord.get(1);
                try {
                    number = format.parse(stockReal);
                } catch (ParseException e){
                    System.out.println("ERROR: No se pudo parsear el número");
                }

                if(number.doubleValue()== 0.0){
                    continue;
                }
                aux.setStockReal((int)(number.doubleValue()));

                //Leeremos y analizaremos cual es el Largo, el Ancho, el Alto y el peso. Si lo hubiese
                if(!csvRecord.get(4).equals("") && !csvRecord.get(4).equals("0")){

                    String largoSt=csvRecord.get(4);
                    String anchoSt=csvRecord.get(5);
                    String altoSt=csvRecord.get(6);
                    String peso=csvRecord.get(7);
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
                }
                //Por último cargaremos en la variable la ubicación de la pieza
                aux.setUbicacion(csvRecord.get(8));

                //Cargamos esta nueva referencia en el mapa

                lista.add(aux);

                //aux.imprimirRef();
                }
            }
        return lista;
        }
    }