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

    private ArrayList<WorkingDay> listWorkingDay = new ArrayList<>();
    private Person person;

    public static double getSalaryByMonth(ArrayList<WorkingDay> list, double rate) {
        double sum = list.stream().mapToDouble(day -> day.getPayForDay(rate)).sum();
        return sum;
    }

    public double getSumOfSalary() {
        double sum;
        sum = listWorkingDay.stream().mapToDouble(day -> day.getPayForDay(person.getPayRate())).sum();
        return sum;
    }

    public Map<Integer, List<WorkingDay>> getMapSplitPerMonth() {
        Map<Integer, List<WorkingDay>> result = listWorkingDay.stream().collect(groupingBy(date -> date.getDay().getMonth()));
        return result;

    }

}
