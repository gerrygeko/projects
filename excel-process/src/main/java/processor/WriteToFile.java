package processor;


import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import model.WorkingDay;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WriteToFile {

    private static final int COLUMN_NUM = 3;

    private static DateFormat dayFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);

    public static void writeTimeWorked(Sheet sheet, ArrayList<WorkingDay> listWorkingDay) {
        int index = 1;
        for(WorkingDay day : listWorkingDay) {
            Row row = sheet.getRow(index);
            Cell cell = ExcelFileReader.getOrCreateCell(row, COLUMN_NUM);

            cell.setCellType(CellType.STRING);
            cell.setCellValue(day.getConvertedMinutesToHoursAndMinutesParsed());
            index ++;
        }
    }

    public static void writePayCalculation(Sheet sheet, ArrayList<WorkingDay> listWorkingDay, float rate) {
        createHeaders(sheet);
        int index = 1;
        for(WorkingDay day : listWorkingDay) {
            Row row = ExcelFileReader.getOrCreateRow(sheet, index);

            Cell cell0 = ExcelFileReader.getOrCreateCell(row, 0);
            Cell cell1 = ExcelFileReader.getOrCreateCell(row, 1);

            cell0.setCellType(CellType.STRING);

            try {
                System.out.println(day.getDay().toString());
                System.out.println(dayFormat.parse(day.getDay().toString()));
                cell0.setCellValue(dayFormat.parse(day.getDay().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cell1.setCellType(CellType.STRING);
            cell1.setCellValue(day.getPayForDay(rate));
            index ++;
        }
    }



    private static void createHeaders(Sheet sheet) {
        Row row = ExcelFileReader.getOrCreateRow(sheet, 0);

        Cell cellCol0 = row.createCell(0);
        Cell cellCol1 = row.createCell(1);

        cellCol0.setCellType(CellType.STRING);
        cellCol1.setCellType(CellType.STRING);

        cellCol0.setCellValue("Day");
        cellCol1.setCellValue("Earned");
    }

}

