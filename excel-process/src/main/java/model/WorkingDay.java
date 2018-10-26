package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {


    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Date day;
    private Date startTime;
    private Date finishTime;

    public long getWorkingTimeInMinutes() {
        long workingTime = finishTime.getTime() - startTime.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(workingTime);
    }

    public String convertMinutesToHoursAndMinutes() {
        int totalMinutes = (int) getWorkingTimeInMinutes();
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return new String(hours + ":" + minutes);
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
        sb.append(convertMinutesToHoursAndMinutes());
        return sb.toString();
    }
}
