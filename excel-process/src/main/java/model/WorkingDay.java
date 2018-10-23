package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {


    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Date day;
    private Date startTime;
    private Date finishTime;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Working day: ");
        sb.append(day + "\n");
        sb.append("Starting time: ");
        sb.append(startTime + "\t");
        sb.append("Finishing time: ");
        sb.append(finishTime + "\n");
        return sb.toString();
    }
}
