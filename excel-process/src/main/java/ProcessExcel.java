import model.WorkingDay;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProcessExcel {


    private static final String EXCEL_PROCESS_TEST_XLS = "local-resources\\test.xls";
    private static final int PAY_RATE_COLUMN = 4;
    private static final int DAY_INDEX = 0;
    private static final int START_INDEX = 1;
    private static final int FINISH_INDEX = 2;


    private static DateFormat readFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
    private static DateFormat dayFormat = new SimpleDateFormat("EEE MMM dd yyyy");
    private static DateFormat hourFormat = new SimpleDateFormat("HH:mm");

    public static void main(String[] args) {

        File xlsFile = new File(EXCEL_PROCESS_TEST_XLS);
        String pathFile = xlsFile.getAbsolutePath();
        System.out.println(pathFile);

        Workbook workbook = ExcelFileReader.getWorkbook(pathFile);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> mapOfRows = getRowsMap(sheet);

        // Code that need to be tweaked more
        Iterator it = mapOfRows.entrySet().iterator();
        float payRate = getPayRate(it); //get the first row and extract the pay rate
        System.out.println("Pay Rate for hour is: " + payRate);
        while(it.hasNext()) {
            WorkingDay workingDay = extractRow(it);
            System.out.println(workingDay.toString());
        }
    }

    // Process the single row where the informations needed are extracted
    private static WorkingDay extractRow(Iterator it) {

        WorkingDay workingDay = null;

        Map.Entry pair = (Map.Entry) it.next();
        ArrayList<String> list = (ArrayList<String>) pair.getValue();

        try {
            Date day = readFormat.parse(list.get(DAY_INDEX));
            Date startTime = readFormat.parse(list.get(START_INDEX));
            Date finishTime = readFormat.parse(list.get(FINISH_INDEX));

            String parsedDay = dayFormat.format(day);
            String parsedStartTime = hourFormat.format(startTime);
            String parsedFinishTime = hourFormat.format(finishTime);

            workingDay = new WorkingDay(dayFormat.parse(parsedDay), hourFormat.parse(parsedStartTime), hourFormat.parse(parsedFinishTime));
            workingDay.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return workingDay;
    }

    // Extract a map out of the excel file, where the key is the element 'i' of the row and the value is an ArrayList<String>
    // containing all the values of the cells on that row
    private static Map<Integer, List<String>> getRowsMap(Sheet sheet) {
        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            ArrayList<String> valueString = new ArrayList<String>();
            data.put(i, valueString);
            for (Cell cell : row) {
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        valueString.add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            valueString.add(cell.getDateCellValue() + "");
                        } else {
                            valueString.add(cell.getNumericCellValue() + "");
                        }
                        break;
                    case BOOLEAN:
                        valueString.add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        valueString.add(cell.getCellFormula() + "");
                        break;
                    case _NONE:
                        valueString.add(" ");
                }
            }
            i++;

        }
        return data;
    }

    // Process the first row of the file
    private static float getPayRate(Iterator it) {
        Map.Entry pair = (Map.Entry) it.next();
        ArrayList<String> row = (ArrayList<String>) pair.getValue();
        float payRate = Float.parseFloat(row.get(PAY_RATE_COLUMN));
        return payRate;
    }

}
