package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@RequiredArgsConstructor
public class Agenda {

    public static final String EURO_CHAR = "\u20AC";

    private static final DecimalFormat decimalFormat = new DecimalFormat("#####.##");

    private ArrayList<WorkingDay> listWorkingDay = new ArrayList<>();
    private Person person;

    public double getSumOfSalary() {
        double sum;
        sum = listWorkingDay.stream().mapToDouble(day -> day.getPayForDay(person.getPayRate())).sum();
        return sum;
    }

    public String getSumOfSalaryFormatted() {
        double sum = getSumOfSalary();
        System.out.println("Total salary: " + decimalFormat.format(sum) + EURO_CHAR);
        return decimalFormat.format(sum) + EURO_CHAR;
    }

    public Map<Integer, List<WorkingDay>> getMapSplitPerMonth() {
        Map<Integer, List<WorkingDay>> result = listWorkingDay.stream().collect(groupingBy(date -> date.getDay().getMonth()));
        return result;

    }

}
