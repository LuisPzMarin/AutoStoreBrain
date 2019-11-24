import java.util.*;


public class Analisis {

    public static Integer MaxCaja= 603;
    public static Integer MedCaja= 403;
    public static Integer MinCaja= 310;
    public static Integer Peso= 30;

    public static ArrayList<Ref> descartarYAsignar(ArrayList<Ref> lista){

        ArrayList <Ref> descartPesoOTamaño = new ArrayList<>();
        ArrayList <Ref> descartadosNoDatos = new ArrayList<>();


        for (int i = 0; i <lista.size() ; i++) {


            if(lista.get(i).getMaximo()> Analisis.MaxCaja || lista.get(i).getMedio()> Analisis.MedCaja ||
                    lista.get(i).getMinimo()> Analisis.MinCaja || lista.get(i).getPeso()> Analisis.Peso){

                descartPesoOTamaño.add(lista.get(i));
                lista.remove(i);
            }else if(lista.get(i).getMaximo()==0) {
                descartadosNoDatos.add(lista.get(i));
                lista.remove(i);
            }else{
                lista.get(i).setTipoCaja(opcionCaja(lista.get(i)));
                lista.get(i).imprimirRef();
            }
        }


        //Enviamos los descartados a DataBaseManagerExcel
        DataBaseManagerExcel.escribirExcel(descartPesoOTamaño,"Descartados por Peso o Tamaño");
        DataBaseManagerExcel.escribirExcel(descartadosNoDatos,"Descartados por falta de datos");


        return lista;
    }

    public static int opcionCaja(Ref refParaAnalisis){

        //Primero calcularemos las medidas del la subdivisión de 16 e iremos probando desde
        //la más pequeña a la más grande
        int max16= Analisis.MaxCaja/8;
        int med16= Analisis.MedCaja/8;
        int min16= Analisis.MinCaja/8;
        int tipoCaja=0;

        for (int i = 1; i <=4 ; i=i*2) {
            if(refParaAnalisis.getMaximo()< max16*i && refParaAnalisis.getMedio()<med16*i ){
                if(refParaAnalisis.getVolumetria()<max16*med16*min16*i){
                    return (16/i);
                }
            }
        }
        if(refParaAnalisis.getMaximo()< MaxCaja/2 && refParaAnalisis.getMedio()<MedCaja){
            if(refParaAnalisis.getVolumetria()<MaxCaja/2*MedCaja*MinCaja){
                return 2;
            }
        }

        if(refParaAnalisis.getMaximo()< MaxCaja && refParaAnalisis.getMedio()<MedCaja/2){
            if(refParaAnalisis.getVolumetria()<MaxCaja*MedCaja/2*MinCaja){
                return 3;
            }
        }
            return 1;
    }

    public static ArrayList <Caja> [] empaquetar(){

    return null;
    }
}
