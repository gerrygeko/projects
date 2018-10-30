package model;

import lombok.*;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

}
