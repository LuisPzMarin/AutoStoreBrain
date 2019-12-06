import java.util.ArrayList;

public class Almacen {

    private ArrayList<ArrayList<Caja>> contenido;


    public Almacen(){
        this.contenido= new ArrayList<ArrayList<Caja>>();
        contenido.add(new ArrayList<Caja>());
        contenido.get(0).add(new Caja(1));

        contenido.add(new ArrayList<Caja>());
        contenido.get(1).add(new Caja(2));

        contenido.add(new ArrayList<Caja>());
        contenido.get(2).add(new Caja(2));

        contenido.add(new ArrayList<Caja>());
        contenido.get(3).add(new Caja(4));

        contenido.add(new ArrayList<Caja>());
        contenido.get(4).add(new Caja(8));

        contenido.add(new ArrayList<Caja>());
        contenido.get(5).add(new Caja(16));

        /*
        * P1=0
        * P2A=1
        * P2B=2
        * P4=3
        * P8=4
        * P16=5
        * */
    }

    public ArrayList<Caja> getListAlm(int tipo) {
        return contenido.get(tipo);
    }

    public void setCajaAlm(Caja cajaNueva, int tipo) {
        this.contenido.get(tipo).add(cajaNueva);
    }

    public void añadirRefACaja(int tipo, int puntero, Ref referencia){
        contenido.get(tipo).get(puntero).setNuevaRef(referencia);
    }

}
