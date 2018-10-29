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
    public static final int FIRST_TAB_SHEET = 0;
    public static final int SECOND_TAB_SHEET = 1;
    private static SimpleDateFormat bornFormat = new SimpleDateFormat("dd/MM/yyyy");


    public static void main(String[] args) {
        Person person = null;
        Agenda myAgenda = new Agenda(person);

        File xlsFile = new File(EXCEL_PROCESS_TEST_XLS);
        String pathFile = xlsFile.getAbsolutePath();
        System.out.println(pathFile);

        Workbook workbook = ExcelFileReader.getWorkbook(pathFile);
        Sheet sheet = ExcelFileReader.getOrCreateSheetAt(workbook, FIRST_TAB_SHEET);

        Map<Integer, List<String>> mapOfRows = ReadFromFile.getRowsMap(sheet);
        // Code that need to be tweaked more
        Iterator it = mapOfRows.entrySet().iterator();
        float payRate = ReadFromFile.getPayRate(it); //get the first row and extract the pay rate

        try {
            person = new Person("Fabiana", "Iacullo", bornFormat.parse("24/12/1988"), payRate);
        } catch (ParseException e) {
            System.err.println("Born date you entered is not in the right format. Error in format: " + e);
        }

        System.out.println("Pay Rate for hour is: " + payRate);
        while(it.hasNext()) {
            WorkingDay workingDay = ReadFromFile.extractRow(it);
            myAgenda.getListWorkingDay().add(workingDay);
            System.out.println(workingDay.toString());
        }

        WriteToFile.writeTimeWorked(sheet, myAgenda.getListWorkingDay());
        Sheet paymentSheet = ExcelFileReader.getOrCreateSheetAt(workbook, SECOND_TAB_SHEET, "Payment");
        WriteToFile.writePayCalculation(paymentSheet, myAgenda.getListWorkingDay(), person.getPayRate());
        ExcelFileReader.saveWorkbook(workbook, pathFile);
    }


}
