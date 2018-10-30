package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor
public class WorkingDay {


    private static final int DECIMAL_PLACE = 2;
    private static final String EURO_CHAR = "\u20AC";

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm");
    private static final DecimalFormat decimalFormat = new DecimalFormat("###.##");

    private Date day;
    private Date startTime;
    private Date finishTime;
    private Date hoursAndMinutesWorked;

    public WorkingDay (Date day, Date startTime, Date finishTime) {
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.hoursAndMinutesWorked = calculateConvertedMinutesToHoursAndMinutes();
    }

    public long getWorkingTimeInMinutes() {
        long workingTime = finishTime.getTime() - startTime.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(workingTime);
    }

    public float getPayForDay(float rate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hoursAndMinutesWorked);
        float sumForHours = calendar.get(Calendar.HOUR) * rate;
        float sumForMinutes = calendar.get(Calendar.MINUTE) * (rate/60);
        float pay = (sumForHours + sumForMinutes);
        return pay;
    }

    public String getPayForDayFormatted(float rate) {
        float pay = getPayForDay(rate);
        return decimalFormat.format(pay) + EURO_CHAR;
    }

    public Date calculateConvertedMinutesToHoursAndMinutes() {
        int totalMinutes = (int) getWorkingTimeInMinutes();
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        String stringTimeCalculated = hours + ":" + minutes;
        Date timeDate = new Date();
        try {
            timeDate = timeFormat.parse(stringTimeCalculated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeDate;
    }

    public String getConvertedMinutesToHoursAndMinutesParsed() {
        return timeFormat.format(hoursAndMinutesWorked);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Working day: ");
        sb.append(day + "\n");
        sb.append("Starting time: ");
        sb.append(startTime + "\t");
        sb.append("Finishing time: ");
        sb.append(finishTime + "\t");
        sb.append("Time worked: ");
        sb.append(getConvertedMinutesToHoursAndMinutesParsed() + "\n");
        return sb.toString();
    }
}
