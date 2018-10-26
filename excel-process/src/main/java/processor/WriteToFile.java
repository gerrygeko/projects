package processor;


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
            Cell cell = row.getCell(COLUMN_NUM);

            if (cell == null)
                cell = row.createCell(COLUMN_NUM);

            cell.setCellType(CellType.STRING);
            cell.setCellValue(day.getConvertedMinutesToHoursAndMinutes());
            index ++;
        }
    }

}

