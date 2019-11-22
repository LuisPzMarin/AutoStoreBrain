import java.io.File;
import java.util.*;

        import java.io.IOException;
        import org.jopendocument.dom.spreadsheet.MutableCell;
        import org.jopendocument.dom.spreadsheet.Sheet;
        import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class DataBaseManager {

    public void leer(File archivo) {

        Sheet hoja;
        try {
            //Getting the 0th sheet for manipulation| pass sheet name as string
            hoja = SpreadSheet.createFromFile(archivo).getSheet(0);
            /*
            //Get row count and column count
            int nColCount = hoja.getColumnCount();
            int nRowCount = hoja.getRowCount();

            System.out.println("Rows :" + nRowCount);
            System.out.println("Cols :" + nColCount);

            */
            TreeMap <Ref,Integer> Lista = new TreeMap<Ref, Integer>();
            //Iterating through each row of the selected sheet
            MutableCell celda = null;
            for (int nRowIndex = 0; nRowIndex < 30000; nRowIndex++) {
                //Iterating through each column
                Ref aux= new Ref();
                celda = hoja.getCellAt(1, nRowIndex);
                aux.setSKU(( celda.getX()));

                /**
                for (int nColIndex = 0; nColIndex <30000; nColIndex++) {

                    celda = hoja.getCellAt(nColIndex, nRowIndex);
                    System.out.print(celda.getValue() + " ");
                    if(celda.getValue()==null){
                        nRowIndex=hoja.getRowCount();
                        continue;
                    }
                }
                 **/
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}