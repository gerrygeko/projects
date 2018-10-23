package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@AllArgsConstructor
public class WorkingDay {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Date day;
    private Date startTime;
    private Date finishTime;

}
