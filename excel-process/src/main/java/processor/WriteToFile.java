package processor;


import model.Agenda;
import model.WorkingDay;
import org.apache.poi.ss.usermodel.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class WriteToFile {


    private static final int COL_MONTH = 5;
    private static final int COL_DAY = 0;
    private static final int COL_EARNED = 1;
    private static final int COL_HOURS = 2;
    private static final String STYLE_TITLE = "Title";
    private static final String STYLE_CENTER = "Center";
    private static final int COL_TOT_MONTH = 6;
    private static final String EURO_CHAR = "\u20AC";

    private static DateFormat dayFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
    private static final DecimalFormat decimalFormat = new DecimalFormat("#####.##");

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

    // TODO: move logic about WorkingDay, out of this class
    public static void writePaymentSheet(Sheet sheet, ArrayList<WorkingDay> listWorkingDay, double rate) {
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
            cell1.setCellValue(appendEuroChar(decimalFormat.format(day.getPayForDay(rate))));

            cell2.setCellType(CellType.STRING);
            cell2.setCellStyle(style);
            cell2.setCellValue(day.getConvertedMinutesToHoursAndMinutesParsed());

            index ++;
        }
    }

    // TODO: Logic is working. Visualize the datas in the payment sheet. Use the keySet to represent the months. Consider moving this logic
    public static int writePaymentByMonth(Sheet sheet, Map<Integer, List<WorkingDay>> map, double rate) {
        int index = 1;
        CellStyle style = styles.get(STYLE_CENTER);
        Iterator it = map.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<WorkingDay> listOfSingleMonth = (ArrayList<WorkingDay>) pair.getValue();
            double sum = Agenda.getSalaryByMonth(listOfSingleMonth, rate);
            System.out.println("Sum of the month: " + sum);
            Row row = ExcelUtils.getOrCreateRow(sheet, index);
            Cell cellMonth = row.createCell(COL_MONTH);
            Cell cellMonthSalary = row.createCell(COL_TOT_MONTH);

            cellMonth.setCellStyle(style);
            cellMonth.setCellValue(listOfSingleMonth.get(0).getMonth());
            cellMonthSalary.setCellStyle(style);
            cellMonthSalary.setCellValue(appendEuroChar(decimalFormat.format(sum )));
            index ++;
        }
        return index;
    }

    public static void writeSalaryTotal(int index, Sheet sheet, Agenda agenda) {
        Row row = ExcelUtils.getOrCreateRow(sheet, index + 1);
        Cell cellLabelTotal = row.createCell(COL_MONTH);
        Cell cellSalaryTotal = row.createCell(COL_TOT_MONTH);
        cellLabelTotal.setCellStyle(styles.get(STYLE_TITLE));
        cellSalaryTotal.setCellStyle(styles.get(STYLE_CENTER));
        cellLabelTotal.setCellValue("Total");
        cellSalaryTotal.setCellValue(appendEuroChar(decimalFormat.format(agenda.getSumOfSalary())));
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
        cellCol6.setCellValue("Earned");
    }

    public static String appendEuroChar(String word) {
        return word.concat(" " + EURO_CHAR);
    }
}

