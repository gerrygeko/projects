package processor;


import model.WorkingDay;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WriteToFile {


    public static final int COL_DAY = 0;
    public static final int COL_EARNED = 1;
    public static final int COL_HOURS = 2;
    private static DateFormat dayFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);

    public static void writePaymentSheet(Sheet sheet, ArrayList<WorkingDay> listWorkingDay, float rate) {
        createHeaders(sheet);
        int index = 1;
        for(WorkingDay day : listWorkingDay) {
            Row row = ExcelUtils.getOrCreateRow(sheet, index);

            Cell cell0 = ExcelUtils.getOrCreateCell(row, COL_DAY);
            Cell cell1 = ExcelUtils.getOrCreateCell(row, COL_EARNED);
            Cell cell2 = ExcelUtils.getOrCreateCell(row, COL_HOURS);

            cell0.setCellType(CellType.STRING);
            cell0.setCellValue(dayFormat.format(day.getDay()));

            cell1.setCellType(CellType.STRING);
            cell1.setCellValue(day.getPayForDayFormatted(rate));

            cell2.setCellType(CellType.STRING);
            cell2.setCellValue(day.getConvertedMinutesToHoursAndMinutesParsed());

            index ++;
        }
    }

    private static void createHeaders(Sheet sheet) {
        Row row = ExcelUtils.getOrCreateRow(sheet, 0);

        Cell cellCol0 = row.createCell(COL_DAY);
        Cell cellCol1 = row.createCell(COL_EARNED);
        Cell cellCol2 = row.createCell(COL_HOURS);

        cellCol0.setCellType(CellType.STRING);
        cellCol1.setCellType(CellType.STRING);
        cellCol2.setCellType(CellType.STRING);

        cellCol0.setCellValue("Day");
        cellCol1.setCellValue("Earned");
        cellCol2.setCellValue("Hours");
    }

}

