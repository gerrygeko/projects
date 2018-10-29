package processor;


import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import model.WorkingDay;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;

public class WriteToFile {

    public static final int COLUMN_NUM = 3;

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
            Row row = sheet.getRow(index);

            Cell cell0 = ExcelFileReader.getOrCreateCell(row, 0);
            Cell cell1 = ExcelFileReader.getOrCreateCell(row, 1);

            cell0.setCellType(CellType.STRING);
            cell0.setCellValue(day.getDay().toString());

            cell1.setCellType(CellType.NUMERIC);
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

