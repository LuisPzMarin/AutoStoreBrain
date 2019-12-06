import java.util.*;


public class Analisis {

    private static Integer MaxCaja = 603;
    private static Integer MedCaja = 403;
    private static Integer MinCaja = 310;
    private static Integer Peso = 30;

    static ArrayList<Ref> descartarYAsignar(ArrayList<Ref> lista) {

        ArrayList<Ref> descartadosPesoOTam = new ArrayList<>();
        ArrayList<Ref> descartadosNoDatos = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {

            if (lista.get(i).getMaximo() >= Analisis.MaxCaja || lista.get(i).getMedio() >= Analisis.MedCaja ||
                    lista.get(i).getMinimo() >= Analisis.MinCaja || lista.get(i).getPeso() >= Analisis.Peso) {
                descartadosPesoOTam.add(lista.get(i));
                lista.remove(i);
                i--;
            } else if (lista.get(i).getMaximo() == 0) {
                descartadosNoDatos.add(lista.get(i));
                lista.remove(i);
                i--;
            } else {
                lista.get(i).setTipoCaja(opcionCaja(lista.get(i)));
                if (lista.get(i).getTipoCaja() == -1) {
                    lista = dividirRef(lista, i);
                    i--;
                } else {
                        lista.get(i).setTipoCaja(opcionCaja(lista.get(i)));
                }

            }
        }


        //Enviamos los descartados a DataBaseManagerExcel
        DataBaseManagerExcel.escribirExcel(descartadosPesoOTam, "Descartados por Peso o Tamaño");
        DataBaseManagerExcel.escribirExcel(descartadosNoDatos, "Descartados por falta de datos");


        return lista;
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
        ArrayList<Ref> result = new ArrayList<>();
        int stock = 0;
        if (dividir.get(i).getStockReal() % 2 == 0) {
            stock = dividir.get(i).getStockReal() / 2;
        } else {
            stock = dividir.get(i).getStockReal() / 2 + 1;
        }
        result.add(new Ref("A-"+dividir.get(i).getSKU(), dividir.get(i).getMaximo(), dividir.get(i).getMedio(), dividir.get(i).getMinimo(),
                dividir.get(i).getPeso(), stock, dividir.get(i).getVendidos(), dividir.get(i).getUbicacion(), dividir.get(i).getTipoCaja(),
                dividir.get(i).getImportador(), dividir.get(i).getMarca(),dividir.get(i).getReferencia(),dividir.get(i).getCaducable()));

        result.add(new Ref("B-"+dividir.get(i).getSKU(), dividir.get(i).getMaximo(), dividir.get(i).getMedio(), dividir.get(i).getMinimo(),
                dividir.get(i).getPeso(), dividir.get(i).getStockReal() / 2, dividir.get(i).getVendidos(), dividir.get(i).getUbicacion(), dividir.get(i).getTipoCaja(),
                dividir.get(i).getImportador(), dividir.get(i).getMarca(),dividir.get(i).getReferencia(),dividir.get(i).getCaducable()));
        dividir.remove(i);
        dividir.add(i, result.get(0));
        dividir.add(i + 1, result.get(1));
        return dividir;
    }

    public static int opcionCaja(Ref refParaAnalisis) {

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
                    refParaAnalisis.getVolumetria() < max16 * med16 * min16 * i
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
                    CLA.getListAlm(tipoCaja).get(0).setID(lista.get(i).getImportador() +
                            "--" + "CAJA" + Analisis.tipoCajaADiv(tipoCaja) + "--" + arrayID[tipoCaja]);
                    arrayID[tipoCaja]++;
                    cerradas[tipoCaja].add(CLA.getListAlm(tipoCaja).get(0));

                    CLA.getListAlm(tipoCaja).remove(0);
                    CLA.getListAlm(tipoCaja).add(new Caja(tipoCajaADiv(tipoCaja)));

                }
            }
        System.out.println(counter);
        return cerradas;
    }
}


