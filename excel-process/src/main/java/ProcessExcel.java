import model.Agenda;
import model.Person;
import model.WorkingDay;
import org.apache.poi.ss.usermodel.*;
import processor.ExcelFileReader;
import processor.ReadFromFile;
import processor.WriteToFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProcessExcel {


    private static final String EXCEL_PROCESS_TEST_XLS = "local-resources\\test.xls";
    private static SimpleDateFormat bornFormat = new SimpleDateFormat("dd/MM/yyyy");


    public static void main(String[] args) {
        Person person = null;
        try {
            person = new Person("Fabiana", "Iacullo", bornFormat.parse("24/12/1988"));
        } catch (ParseException e) {
            System.err.println("Born date you entered is not in the right format. Error in format: " + e);
        }
        Agenda myAgenda = new Agenda(person);

        File xlsFile = new File(EXCEL_PROCESS_TEST_XLS);
        String pathFile = xlsFile.getAbsolutePath();
        System.out.println(pathFile);

        Workbook workbook = ExcelFileReader.getWorkbook(pathFile);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> mapOfRows = ReadFromFile.getRowsMap(sheet);
        // Code that need to be tweaked more
        Iterator it = mapOfRows.entrySet().iterator();
        float payRate = ReadFromFile.getPayRate(it); //get the first row and extract the pay rate
        System.out.println("Pay Rate for hour is: " + payRate);
        while(it.hasNext()) {
            WorkingDay workingDay = ReadFromFile.extractRow(it);
            myAgenda.getListWorkingDay().add(workingDay);
            System.out.println(workingDay.toString());
        }
        WriteToFile.writeTimeWorked(sheet, myAgenda.getListWorkingDay());
        ExcelFileReader.saveWorkbook(workbook, pathFile);
    }


}
