import model.WorkingDay;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProcessExcel {


    private static final String EXCEL_PROCESS_TEST_XLS = "excel-process\\test.xls";
    private static final int PAY_RATE_COLUMN = 4;
    public static final int DAY_INDEX = 0;
    public static final int START_INDEX = 1;
    public static final int FINISH_INDEX = 2;


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

        //Code that need deleted. We are going to define a class that describe the sheet that has been loaded from the Excel.
        //This is just a test to print the content of the file.
        Iterator it = mapOfRows.entrySet().iterator();
        float payRate = getPayRate(it); //get the first row and extract the pay rate
        System.out.println("Pay Rate for hour is: " + payRate);
        while(it.hasNext()) {
            WorkingDay workingDay = extractRow(it);
            System.out.println(workingDay.toString());
        }
    }

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

    private static float getPayRate(Iterator it) {
        Map.Entry pair = (Map.Entry) it.next();
        ArrayList<String> row = (ArrayList<String>) pair.getValue();
        float payRate = Float.parseFloat(row.get(PAY_RATE_COLUMN));
        return payRate;
    }

}
