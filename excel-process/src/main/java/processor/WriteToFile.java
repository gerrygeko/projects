package processor;


import model.WorkingDay;
import org.apache.poi.ss.usermodel.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WriteToFile {


    public static final int COL_MONTH = 5;
    private static final int COL_DAY = 0;
    private static final int COL_EARNED = 1;
    private static final int COL_HOURS = 2;
    public static final String STYLE_TITLE = "Title";
    public static final String STYLE_CENTER = "Center";
    public static final int COL_TOT_MONTH = 6;

    private static DateFormat dayFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
    private static Map<String, CellStyle> styles = new HashMap<>();

    public static void loadStyles(Workbook wb) {

        // Style for title cells
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put(STYLE_TITLE, style);

        //Style for normal center cells
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put(STYLE_CENTER, style);

    }

    public static void writePaymentSheet(Sheet sheet, ArrayList<WorkingDay> listWorkingDay, float rate) {
        createHeaders(sheet);
        int index = 1;
        CellStyle style = styles.get(STYLE_CENTER);
        for(WorkingDay day : listWorkingDay) {
            Row row = ExcelUtils.getOrCreateRow(sheet, index);

            Cell cell0 = ExcelUtils.getOrCreateCell(row, COL_DAY);
            Cell cell1 = ExcelUtils.getOrCreateCell(row, COL_EARNED);
            Cell cell2 = ExcelUtils.getOrCreateCell(row, COL_HOURS);

            cell0.setCellType(CellType.STRING);
            //cell0.setCellStyle(style);
            cell0.setCellValue(dayFormat.format(day.getDay()));

            cell1.setCellType(CellType.STRING);
            cell1.setCellStyle(style);
            cell1.setCellValue(day.getPayForDayFormatted(rate));

            cell2.setCellType(CellType.STRING);
            cell2.setCellStyle(style);
            cell2.setCellValue(day.getConvertedMinutesToHoursAndMinutesParsed());

            index ++;
        }
    }


    // TODO: Make this method more dynamic
    private static void createHeaders(Sheet sheet) {
        CellStyle style = styles.get(STYLE_TITLE);
        Row row = ExcelUtils.getOrCreateRow(sheet, 0);

        Cell cellCol0 = row.createCell(COL_DAY);
        Cell cellCol1 = row.createCell(COL_EARNED);
        Cell cellCol2 = row.createCell(COL_HOURS);
        Cell cellCol5 = row.createCell(COL_MONTH);
        Cell cellCol6 = row.createCell(COL_TOT_MONTH);

        cellCol0.setCellType(CellType.STRING);
        cellCol0.setCellStyle(style);
        cellCol1.setCellType(CellType.STRING);
        cellCol1.setCellStyle(style);
        cellCol2.setCellType(CellType.STRING);
        cellCol2.setCellStyle(style);
        cellCol5.setCellType(CellType.STRING);
        cellCol5.setCellStyle(style);
        cellCol6.setCellType(CellType.STRING);
        cellCol6.setCellStyle(style);

        cellCol0.setCellValue("Day");
        cellCol1.setCellValue("Earned");
        cellCol2.setCellValue("Hours");
        cellCol5.setCellValue("Month");
        cellCol6.setCellValue("Total");
    }

}

