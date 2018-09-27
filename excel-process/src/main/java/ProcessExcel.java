import org.apache.poi.ss.usermodel.*;

import java.util.*;

public class ProcessExcel {


    public static void main(String[] args) {

        Workbook workbook = ExcelFileReader.getWorkbook("D:\\workspace\\allmodules\\local-resources\\test.xls");
        Sheet sheet = workbook.getSheetAt(0);

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

        //Code that need deleted. We are going to define a class that describe the sheet that has been loaded from the Excel.
        //This is just a test to print the content of the file.

        Iterator it = data.entrySet().iterator();
        while(it.hasNext()) {
            StringBuilder sb = new StringBuilder();
            Map.Entry pair = (Map.Entry) it.next();

            sb.append("Row: " + pair.getKey() + "\n");
            ArrayList<String> list = (ArrayList<String>) pair.getValue();
            for(String value : list) {
                sb.append(value + " | ");
            }
            System.out.println(sb.toString());
        }

    }

}
