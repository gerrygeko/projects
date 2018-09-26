import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelFileReader {


    public Workbook getWorkbook(String filePath) {

        FileInputStream file = null;
        Workbook workbook = null;

        try {
            file = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

}
