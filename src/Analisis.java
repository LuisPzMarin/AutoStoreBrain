import java.util.*;


public class Analisis {

    private static Integer MaxCaja = 603;
    private static Integer MedCaja = 403;
    private static Integer MinCaja = 310;
    private static Integer Peso = 30000;
    private static Double seguridadVolumetria=0.1;
    private static Integer seguridadMedida=5;

    static ArrayList<Ref>[] descartarYAsignar(ArrayList<Ref> lista, int maxCajasIguales) {

        //Creamos las variables que guardaran las ref. descartadas
        ArrayList<Ref> descartadosPeso = new ArrayList<>();
        ArrayList<Ref> descartadosTam = new ArrayList<>();
        ArrayList<Ref> descartadosNoDatos = new ArrayList<>();
        ArrayList<Ref> descartadosAltoStock = new ArrayList<>();

        //Recorremos la lista y valoramos cada ref. por separado
        for (int i = 0; i < lista.size(); i++) {

            //En primer lugar comprobaremos que el peso no supero el máximo de la caja
            if (lista.get(i).getPeso() >= Analisis.Peso) {
                descartadosPeso.add(lista.get(i));

                lista.remove(i);
                i--;
                continue;
            }
            if (lista.get(i).getMaximo() >= (Analisis.MaxCaja - seguridadMedida) ||
                    lista.get(i).getMedio() >= (Analisis.MedCaja - Analisis.seguridadMedida) ||
                    lista.get(i).getMinimo() >= (Analisis.MinCaja - seguridadMedida)) {
                descartadosTam.add(lista.get(i));
                lista.remove(i);
                i--;
                continue;
            }
            if (lista.get(i).getMaximo() == 0 || lista.get(i).getPeso() == 0) {
                descartadosNoDatos.add(lista.get(i));
                lista.remove(i);
                i--;
                continue;
            }
            lista.get(i).setTipoCaja(opcionCaja(lista.get(i)));
            if (lista.get(i).getTipoCaja() == -1) {
                if (maxCajasIguales > 1) {
                    if (lista.get(i).getPeso() * lista.get(i).getStockReal() > maxCajasIguales * Peso ||
                            lista.get(i).getVolumetria() * lista.get(i).getStockReal() >
                                    maxCajasIguales * MaxCaja * MedCaja * MinCaja) {
                        descartadosAltoStock.add(lista.get(i));
                        lista.remove(i);
                        i--;
                        continue;
                    }
                }
                lista = dividirRef(lista, i);
                i--;


            }
        }

        ArrayList<Ref> [] result = new ArrayList[5];
        result[0]=lista;
        result[1]=descartadosPeso;
        result[2]=descartadosNoDatos;
        result[3]=descartadosTam;
        result[4]=descartadosAltoStock;

        return result;
    }

    public static int tipoCajaADiv(int tipoCaja ){

        int aux=-1;
        switch (tipoCaja) {
            case 0:
                aux = 1;
                break;
            case 1:
                aux = 2;
                break;
            case 2:
                aux = 2;
                break;
            case 3:
                aux = 4;
                break;
            case 4:
                aux = 8;
                break;
            case 5:
                aux = 16;
                break;
            default:

                System.err.println("ERROR IMPRIMIENDO REF");
                System.exit(0);
                break;
        }
        return aux;
    }

    private static ArrayList<Ref> dividirRef(ArrayList<Ref> dividir, int i) {

        if(dividir.get(i).getStockReal()==1){
            return dividir;
        }
        Ref aux= new Ref(dividir.get(i).getSKU(), dividir.get(i).getMaximo(), dividir.get(i).getMedio(), dividir.get(i).getMinimo(),
                dividir.get(i).getPeso(), 1, dividir.get(i).getMovimientos(), dividir.get(i).getUbicacion(), dividir.get(i).getTipoCaja(),
                dividir.get(i).getImportador(), dividir.get(i).getMarca(),dividir.get(i).getReferencia(),dividir.get(i).getCaducable(),
                dividir.get(i).getDenominacion());
        dividir.get(i).setSKU("1."+dividir.get(i).getSKU());
        dividir.get(i).setStockReal(dividir.get(i).getStockReal()-1);

        while (opcionCaja(aux) != -1){

            aux.setStockReal(aux.getStockReal()+1);
            dividir.get(i).setStockReal(dividir.get(i).getStockReal()-1);

        }
        aux.setStockReal(aux.getStockReal()-1);
        dividir.get(i).setStockReal(dividir.get(i).getStockReal()+1);
        dividir.add(i, aux);

        return dividir;
    }

    private static int opcionCaja(Ref refParaAnalisis) {

        //Primero calcularemos las medidas del la subdivisión de 16 e iremos probando desde
        //la más pequeña a la más grande
        int max16 = Analisis.MaxCaja / 8;
        int med16 = Analisis.MedCaja / 8;
        int min16 = Analisis.MinCaja / 8;
        int peso16 = Analisis.Peso / 16;
        int volumetria16= max16*min16*med16;
        /*
         * P1=0
         * P2A=1
         * P2B=2
         * P4=3
         * P8=4
         * P16=5
         * */


        for (int i = 1; i <= 4; i = i * 2) {

            if (refParaAnalisis.getMaximo() < max16 * i && refParaAnalisis.getMedio() < med16 * i &&
                    refParaAnalisis.getVolumetria() < max16 * med16 * min16 * i *(1-seguridadVolumetria)
                    && refParaAnalisis.getPeso()*refParaAnalisis.getStockReal() < peso16*i ) {
                switch (i){
                    case 1:
                        return 5;

                    case 2:
                        return 4;

                    case 4:
                        return 3;
                    default:
                        System.err.println("ERROR: REVISAR TIPO DE CAJA DE: " + refParaAnalisis.getTipoCaja());
                        System.exit(0);
                        break;
                }
            }

        }
        if (refParaAnalisis.getMaximo() < MaxCaja / 2 && refParaAnalisis.getMedio() < MedCaja &&
                refParaAnalisis.getVolumetria() < MaxCaja / 2 * MedCaja * MinCaja
                && refParaAnalisis.getPeso()*refParaAnalisis.getStockReal() < Peso/2) {
            return 1;

        }

        if (refParaAnalisis.getMaximo() < MaxCaja && refParaAnalisis.getMedio() < MedCaja / 2 &&
                refParaAnalisis.getVolumetria() < MaxCaja * MedCaja / 2 * MinCaja
                && refParaAnalisis.getPeso()*refParaAnalisis.getStockReal() < Peso/2) {
            return 2;

        }
        if (refParaAnalisis.getMaximo() < MaxCaja && refParaAnalisis.getMedio() < MedCaja &&
                refParaAnalisis.getVolumetria() < MaxCaja * MedCaja * MinCaja
                && refParaAnalisis.getPeso()*refParaAnalisis.getStockReal() < Peso) {
            return 0;
        } else {
            return -1;
        }
    }

    public static ArrayList<Caja>[] empaquetar(ArrayList<Ref> lista, Almacen CLA) {
        int counter=0;
        ArrayList[] cerradas = new ArrayList[6];
        //Lista para cajas P1
        cerradas[0] = new ArrayList<Caja>();
        int cerradas0ID=0;
        //Lista para cajas P2A
        cerradas[1] = new ArrayList<Caja>();
        //Lista para cajas P2B
        cerradas[2] = new ArrayList<Caja>();
        //Lista para cajas P4
        cerradas[3] = new ArrayList<Caja>();
        //Lista para cajas P8
        cerradas[4] = new ArrayList<Caja>();
        //Lista para cajas P16
        cerradas[5] = new ArrayList<Caja>();

        int arrayID []= new int [6];
        arrayID[0]=0;
        arrayID[1]=0;
        arrayID[2]=0;
        arrayID[3]=0;
        arrayID[4]=0;
        arrayID[5]=0;

        for (int i = 0; i < lista.size(); i++) {
            //lista.get(i).imprimirRef();
            boolean aux = false;
            int tipoCaja = lista.get(i).getTipoCaja();

            //Primero analizaremos si estamos ante el último hueco de la caja
            if (!CLA.getListAlm(tipoCaja).get(0).ultimoHueco()) {
                //Si todavia no nos enfrentamos a ese problema, metemos la ref y acabamos metodo
                CLA.getListAlm(tipoCaja).get(0).setNuevaRef(lista.get(i));
                counter++;

            } else {

                    //En el caso de que si, metemos Ref y cerramos caja, no antes sin reponer otra caja vacia
                    CLA.getListAlm(tipoCaja).get(0).setNuevaRef(lista.get(i));
                    counter++;
                    if(tipoCaja==2){
                        CLA.getListAlm(tipoCaja).get(0).setID(lista.get(i).getImportador() +
                                "--" + "CAJA2B" + "--" + arrayID[tipoCaja]);

                    }else{
                        CLA.getListAlm(tipoCaja).get(0).setID(lista.get(i).getImportador() +
                                "--" + "CAJA" + Analisis.tipoCajaADiv(tipoCaja) + "--" + arrayID[tipoCaja]);

                    }
                    arrayID[tipoCaja]++;
                    cerradas[tipoCaja].add(CLA.getListAlm(tipoCaja).get(0));

                    CLA.getListAlm(tipoCaja).remove(0);
                    CLA.getListAlm(tipoCaja).add(new Caja(tipoCajaADiv(tipoCaja)));

                }
            }
        return cerradas;
    }


    public static ArrayList<Ref>menosDeCuatro(ArrayList<Caja> []lista, ArrayList<Ref> descartadas){

        for (int p = 0; p <lista.length ; p++) {
            for (int i = 0; i <lista[p].size(); i++) {
                if(lista[p].get(i).numPiezasCaja()<=4){
                    for (int j = 0; j <lista[p].get(i).getDivisiones() ; j++) {
                        descartadas.add(lista[p].get(i).getRefCaja(j));
                    }
                    lista[p].remove(i);
                    i--;
                }
            }
        }

        return descartadas;

    }

    static int refsPorLista(ArrayList<Caja> lista){
        int res=0;
        for (int i = 0; i <lista.size(); i++) {
            res+= lista.get(i).refsPorCaja();
        }
        return res;

    }
}



