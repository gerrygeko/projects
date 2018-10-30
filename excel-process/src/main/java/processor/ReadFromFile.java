package processor;

import model.WorkingDay;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadFromFile {

    private static final int PAY_RATE_COLUMN = 4;
    private static final int DAY_INDEX = 0;
    private static final int START_INDEX = 1;
    private static final int FINISH_INDEX = 2;


    private static DateFormat readFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
    private static DateFormat dayFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
    private static DateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    // Process the single row where the informations needed are extracted
    public static WorkingDay extractRow(Iterator it) {

        WorkingDay workingDay = null;

        Map.Entry pair = (Map.Entry) it.next();
        ArrayList<String> list = (ArrayList<String>) pair.getValue();

        // If the row has no values in no one of his cells, we assume is the last row and we return a null WorkingDay;
        if(list.size() == 0) { return workingDay; }

        try {
            Date day = readFormat.parse(list.get(DAY_INDEX));
            Date startTime = readFormat.parse(list.get(START_INDEX));
            Date finishTime = readFormat.parse(list.get(FINISH_INDEX));

            String parsedDay = dayFormat.format(day);
            String parsedStartTime = hourFormat.format(startTime);
            String parsedFinishTime = hourFormat.format(finishTime);

            workingDay = new WorkingDay(dayFormat.parse(parsedDay), hourFormat.parse(parsedStartTime), hourFormat.parse(parsedFinishTime));
            //workingDay.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return workingDay;
    }

    // Process the first row of the file
    public static float getPayRate(Iterator it) {
        Map.Entry pair = (Map.Entry) it.next();
        ArrayList<String> row = (ArrayList<String>) pair.getValue();
        float payRate = Float.parseFloat(row.get(PAY_RATE_COLUMN));
        return payRate;
    }

}
