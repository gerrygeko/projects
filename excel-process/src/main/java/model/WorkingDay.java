package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor
public class WorkingDay {


    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat timeFormat = new SimpleDateFormat("hh:mm");

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
        float sumForHours = hoursAndMinutesWorked.getHours() * rate;
        float sumForMinutes = hoursAndMinutesWorked.getMinutes() * (rate/60);
        return sumForHours + sumForMinutes;
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
        String hours = hoursAndMinutesWorked.getHours() + "";
        String minutes = hoursAndMinutesWorked.getMinutes() + "";
        return hours + ":" + minutes;
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
