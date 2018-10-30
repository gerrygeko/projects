package model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@RequiredArgsConstructor
public class Agenda {

    public static final String EURO_CHAR = "\u20AC";

    private static final DecimalFormat decimalFormat = new DecimalFormat("#####.##");

    private ArrayList<WorkingDay> listWorkingDay = new ArrayList<>();
    private Person person;

    public float getSumOfSalary() {
        float sum = 0;
        for( WorkingDay workingDay : listWorkingDay ) {
            sum += workingDay.getPayForDay(person.getPayRate());
        }
        System.out.println("Total salary: " + sum);
        return sum;
    }

    public String getSumOfSalaryFormatted() {
        float sum = getSumOfSalary();
        System.out.println("Total salary: " + decimalFormat.format(sum) + EURO_CHAR);
        return decimalFormat.format(sum) + EURO_CHAR;
    }

    public Map<Integer, List<WorkingDay>> getMapSplitPerMonth() {

        Map<Integer, List<WorkingDay>> result = listWorkingDay.stream().collect(groupingBy(date -> date.getDay().getMonth()));

        return result;

    }

}
