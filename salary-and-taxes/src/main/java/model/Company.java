package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Company {

    private String name;
    private int taxRate;
    private List<Employee> listOfEmployers= new ArrayList<Employee>();

    public Company(String name, int taxRate) {
        this.name = name;
        this.taxRate = taxRate;
    }

    public Double getTaxesFromEmployees() {
        return listOfEmployers.stream().mapToDouble(s->s.getTaxes(s.getMonthlySalary())).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name of company:");
        sb.append(name + "\n");
        sb.append("Taxes to collect: ");
        sb.append(getTaxesFromEmployees().toString() + "\n");
        return sb.toString();
    }

}
