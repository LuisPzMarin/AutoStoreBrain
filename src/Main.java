public class Main {


    public static void main(String[] args) {
        System.out.println("Hola mundo!!");

        Caja box1= new Caja(4);
        System.out.println(box1.getDivisiones());
        Referencia ref1= new Referencia(1,10,20,4,20,1);
        System.out.println("La volumetria de ref1 es " + ref1.getVolumetria());


    }
}
