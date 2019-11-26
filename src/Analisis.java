import java.util.*;


public class Analisis {

    public static Integer MaxCaja= 603;
    public static Integer MedCaja= 403;
    public static Integer MinCaja= 310;
    public static Integer Peso= 30;

    public static ArrayList<Ref> descartarYAsignar(ArrayList<Ref> lista){

        ArrayList <Ref> descartadosPesoOTam = new ArrayList<>();
        ArrayList <Ref> descartadosNoDatos = new ArrayList<>();

        for (int i = 0; i <lista.size() ; i++) {


            if(lista.get(i).getMaximo()> Analisis.MaxCaja || lista.get(i).getMedio()> Analisis.MedCaja ||
                    lista.get(i).getMinimo()> Analisis.MinCaja || lista.get(i).getPeso()> Analisis.Peso){

                descartadosPesoOTam.add(lista.get(i));
                lista.remove(i);
            }else if(lista.get(i).getMaximo()==0) {
                descartadosNoDatos.add(lista.get(i));
                lista.remove(i);
            }else{
                if(opcionCaja(lista.get(i))==-1){
                   lista =dividirRef(lista, i);
                   i--;

                }else{

                    lista.get(i).setTipoCaja(opcionCaja(lista.get(i)));
                    lista.get(i).imprimirRef();
                }

            }
        }


        //Enviamos los descartados a DataBaseManagerExcel
        DataBaseManagerExcel.escribirExcel(descartadosPesoOTam,"Descartados por Peso o Tamaño");
        DataBaseManagerExcel.escribirExcel(descartadosNoDatos,"Descartados por falta de datos");


        return lista;
    }

    public static int opcionCaja(Ref refParaAnalisis) {

        //Primero calcularemos las medidas del la subdivisión de 16 e iremos probando desde
        //la más pequeña a la más grande
        int max16 = Analisis.MaxCaja / 8;
        int med16 = Analisis.MedCaja / 8;
        int min16 = Analisis.MinCaja / 8;
        int tipoCaja = 0;

        for (int i = 1; i <= 4; i = i * 2) {
            if (refParaAnalisis.getMaximo() < max16 * i && refParaAnalisis.getMedio() < med16 * i &&
                    refParaAnalisis.getVolumetria() < max16 * med16 * min16 * i) {
                return (16 / i);
            }

        }
        if (refParaAnalisis.getMaximo() < MaxCaja / 2 && refParaAnalisis.getMedio() < MedCaja &&
                refParaAnalisis.getVolumetria() < MaxCaja / 2 * MedCaja * MinCaja) {
            return 2;

        }

        if (refParaAnalisis.getMaximo() < MaxCaja && refParaAnalisis.getMedio() < MedCaja / 2 &&
                refParaAnalisis.getVolumetria() < MaxCaja * MedCaja / 2 * MinCaja) {
            return 3;

        }
        if (refParaAnalisis.getMaximo() < MaxCaja && refParaAnalisis.getMedio() < MedCaja &&
                refParaAnalisis.getVolumetria() < MaxCaja * MedCaja * MinCaja) {
            return 1;
        } else {
            return -1;
        }
    }

    public static ArrayList <Caja> [] empaquetar(){

    return null;
    }


    public static ArrayList <Ref> dividirRef(ArrayList<Ref> dividir, int i ){
        Ref[] result = new Ref[2];
        int stock=0;
        if (dividir.get(i).getStockReal()%2==0){
            stock=dividir.get(i).getStockReal()/2;
        }else{
            stock=dividir.get(i).getStockReal()/2 +1;
        }
        result [0]= new Ref( dividir.get(i).getSKU() + "A",  dividir.get(i).getMaximo(),  dividir.get(i).getMedio(),  dividir.get(i).getMinimo(),
                dividir.get(i).getPeso(),  stock,  dividir.get(i).getVendidos(), dividir.get(i).getUbicacion(), 0);

        result [1]= new Ref( dividir.get(i).getSKU() + "B",  dividir.get(i).getMaximo(),  dividir.get(i).getMedio(),  dividir.get(i).getMinimo(),
                dividir.get(i).getPeso(),  dividir.get(i).getStockReal()/2,  dividir.get(i).getVendidos(), dividir.get(i).getUbicacion(), 0);

        dividir.remove(i);
        dividir.add(i,result[0]);
        dividir.add(i+1,result[1]);
        return dividir;
    }
}

