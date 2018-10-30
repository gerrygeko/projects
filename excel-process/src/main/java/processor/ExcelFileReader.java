package processor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class ExcelFileReader {

    // Load the workbook of a file loaded from the filePath passed
    public static Workbook getWorkbook(String filePath) {

        FileInputStream file;
        Workbook workbook = null;

        try {
            file = new FileInputStream(new File(filePath));
            workbook = new HSSFWorkbook(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    // Save the workbook into a file giving the filePath
    public static void saveWorkbook(Workbook workbook, String filePath) {
        // Write the output to the file
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e);;
        } catch (IOException e) {
            System.err.println("Impossible to read/write from file: " + e);
        }
    }

    // Get or, if not present, create a sheet with the given index, of the workbook passed
    public static Sheet getOrCreateSheetAt(Workbook workbook, int index) {
        Sheet sheet = workbook.getSheetAt(index);
        if (sheet == null) {
            sheet = workbook.createSheet("Default");
        }
        return sheet;
    }

    // Get or, if not present, create a sheet with the given index, of the workbook passed. The name passed correspond to the
    // name assigned at the sheet
    public static Sheet getOrCreateSheetAt(Workbook workbook, int index, String name) {
        Sheet sheet = null;
        int maxSheet = workbook.getNumberOfSheets();
        if (index >= maxSheet) {
            sheet = workbook.createSheet(name);
            return sheet;
        }
        return workbook.getSheetAt(index);
    }

    // Get or, if not present, create a sheet with the given index, of the workbook passed. The name passed correspond to the
    // name assigned at the sheet. Use the boolean clean if you want to clean the sheet before.
    public static Sheet getOrCreateSheetAt(Workbook workbook, int index, String name, boolean clean) {
        Sheet sheet = null;
        int maxSheet = workbook.getNumberOfSheets();
        if (index >= maxSheet) {
            sheet = workbook.createSheet(name);
            return sheet;
        }
        sheet = workbook.getSheetAt(index);

        // Clean the sheet you return or pass the same sheet
        sheet = clean ? cleanSheet(sheet) : sheet;
        return sheet;
    }

    public static Cell getOrCreateCell(Row row, int index) {
        Cell cell = null;
        int maxCell = row.getPhysicalNumberOfCells();
        if (index >= maxCell) {
            cell = row.createCell(index);
            return cell;
        }
        return row.getCell(index);
    }

    public static Row getOrCreateRow(Sheet sheet, int index) {
        Row row = null;
        int maxRow = sheet.getPhysicalNumberOfRows();
        if (index >= maxRow) {
            row = sheet.createRow(index);
            return row;
        }
        return sheet.getRow(index);
    }

    private static Sheet cleanSheet(Sheet sheet) {
        for (int index = sheet.getLastRowNum(); index >= sheet.getFirstRowNum(); index--) {
            sheet.removeRow( sheet.getRow(index));
        }
        return sheet;
    }

}
