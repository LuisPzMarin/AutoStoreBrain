import java.util.*;


public class Analisis {

    private static Integer MaxCaja = 603;
    private static Integer MedCaja = 403;
    private static Integer MinCaja = 310;
    private static Integer Peso = 30;

    public static ArrayList<Ref> descartarYAsignar(ArrayList<Ref> lista) {

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


    public static ArrayList<Ref> dividirRef(ArrayList<Ref> dividir, int i) {
        ArrayList<Ref> result = new ArrayList<>();
        int stock = 0;
        if (dividir.get(i).getStockReal() % 2 == 0) {
            stock = dividir.get(i).getStockReal() / 2;
        } else {
            stock = dividir.get(i).getStockReal() / 2 + 1;
        }
        result.add(new Ref(dividir.get(i).getSKU().concat("A"), dividir.get(i).getMaximo(), dividir.get(i).getMedio(), dividir.get(i).getMinimo(),
                dividir.get(i).getPeso(), stock, dividir.get(i).getVendidos(), dividir.get(i).getUbicacion(), 0));

        result.add(new Ref(dividir.get(i).getSKU().concat("B"), dividir.get(i).getMaximo(), dividir.get(i).getMedio(), dividir.get(i).getMinimo(),
                dividir.get(i).getPeso(), dividir.get(i).getStockReal() / 2, dividir.get(i).getVendidos(), dividir.get(i).getUbicacion(), 0));

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

    public static ArrayList<Caja>[] empaquetar(ArrayList<Ref> lista, Almacen CLA) {

        ArrayList[] cerradas = new ArrayList[6];
        //Lista para cajas P1
        cerradas[0] = new ArrayList<Caja>();
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


        for (int i = 0; i < lista.size(); i++) {
            //lista.get(i).imprimirRef();
            int aux = -1;
            int tipoCaja = lista.get(i).getTipoCaja();
            switch (tipoCaja) {
                case 1:
                    aux = 0;
                    break;
                case 2:
                    aux = 1;
                    break;
                case 3:
                    aux = 2;
                    break;
                case 4:
                    aux = 3;
                    break;
                case 8:
                    aux = 4;
                    break;
                case 16:
                    aux = 5;
                    break;
                default:
                    System.err.println("ERROR: REVISAR TIPO DE CAJA DE: " + lista.get(i).getTipoCaja());
                    System.exit(0);
                    break;
            }

            //cerradas= empaquetarAuxiliar(CLA, cerradas, lista.get(i),aux);
            //Primero analizaremos si estamos ante el último hueco de la caja
            if (!CLA.getP()[aux].ultimoHueco()) {
                //Si todavia no nos enfrentamos a ese problema, metemos la ref y acabamos metodo
                CLA.añadirRefACaja(lista.get(i), aux);
            } else {
                //En caso contrario analizamos si se cumple el minimo de 4 piezas por caja
                if ((CLA.getP()[aux].numPiezasCaja() + lista.get(i).getStockReal()) >= 4 || aux == 0) {

                    //En el caso de que si, metemos Ref y cerramos caja, no antes sin reponer otra caja vacia
                    CLA.añadirRefACaja(lista.get(i), aux);
                    CLA.getP()[aux].setID(lista.get(i).getImportador() + "-P" + aux + (String.valueOf(cerradas.length - 1)));
                    cerradas[aux].add(CLA.getP()[aux]);

                    //Antes de crear una nueva caja, analizaremos si tenemos ya otra abierta

                    if (CLA.getP()[aux].getSiguiente() == null) {
                        if (CLA.getP()[aux].getAnterior() == null) {
                            CLA.setP(new Caja(tipoCaja), aux);
                        } else {
                            CLA.setP(CLA.getP()[aux].getAnterior(), aux);
                        }
                    } else {
                        CLA.setP(CLA.getP()[aux].getSiguiente(), aux);
                    }
                } else {
                    //Si el no se cumple el criterio de la cantidad, dejamos la caja abierta y creamos otra donde ir metiendo
                    if (CLA.getP()[aux].getSiguiente() == null) {
                        Caja proxSig = new Caja(tipoCaja);
                        proxSig.setNuevaRef(lista.get(i));
                        CLA.setSig(proxSig, aux);
                        CLA.getP()[aux].getSiguiente().setAnterior(CLA.getP()[aux]);
                    } else {
                        Caja proxSig = CLA.getP()[aux].getSiguiente();
                        proxSig.setNuevaRef(lista.get(i));
                        CLA.setSig(proxSig, aux);

                    }
                }
            }
        }

        //Procedemos a vaciar las cajas a medio llenar

   

        return cerradas;
    }
}


