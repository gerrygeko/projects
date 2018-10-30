import model.Agenda;
import model.Person;
import model.WorkingDay;
import org.apache.poi.ss.usermodel.*;
import processor.ExcelUtils;
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
        Agenda myAgenda = new Agenda();

        File xlsFile = new File(EXCEL_PROCESS_TEST_XLS);
        String pathFile = xlsFile.getAbsolutePath();
        System.out.println(pathFile);

        Workbook workbook = ExcelUtils.getWorkbook(pathFile);
        Sheet sheet = ExcelUtils.getOrCreateSheetAt(workbook, FIRST_TAB_SHEET);

        Map<Integer, List<String>> mapOfRows = ExcelUtils.getRowsMap(sheet);
        System.out.println("Number of rows: " + mapOfRows.size());
        // Code that need to be tweaked more
        Iterator it = mapOfRows.entrySet().iterator();
        float payRate = ReadFromFile.getPayRate(it); //get the first row and extract the pay rate

        try {
            person = new Person("Fabiana", "Iacullo", bornFormat.parse("24/12/1988"), payRate);
            myAgenda.setPerson(person);
        } catch (ParseException e) {
            System.err.println("Born date you entered is not in the right format. Error in format: " + e);
        }

        System.out.println("Pay Rate for hour is: " + payRate);
        while(it.hasNext()) {
            WorkingDay workingDay = ReadFromFile.extractRow(it);

            // If we get a null WorkingDay we assume that there is no WorkingDay to extract anymore
            if(workingDay == null) { break; }

            myAgenda.getListWorkingDay().add(workingDay);
            System.out.println(workingDay.toString());
        }

        Sheet paymentSheet = ExcelUtils.getOrCreateSheetAt(workbook, SECOND_TAB_SHEET, "Payment", true);
        WriteToFile.loadStyles(workbook);
        WriteToFile.writePaymentSheet(paymentSheet, myAgenda.getListWorkingDay(), person.getPayRate());
        WriteToFile.writePaymentByMonth(paymentSheet, myAgenda.getMapSplitPerMonth(), person.getPayRate());
        myAgenda.getSumOfSalaryFormatted();
        myAgenda.getMapSplitPerMonth();
        ExcelUtils.saveWorkbook(workbook, pathFile);
    }


}
