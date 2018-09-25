package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee extends AbstractPerson implements IEmployee {

    private Company company;
    private Date employmentDate;
    private float monthlySalary;
    private int partTimeRate;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Employee(String name, String surname, Date bornDate, Company company, Date employmentDate, float monthlySalary, int partTimeRate) {
        super(name, surname, bornDate);
        this.company = company;
        this.employmentDate = employmentDate;
        this.monthlySalary = monthlySalary;
        this.partTimeRate = partTimeRate;
    }

    public Company getCompany() {
        return company;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public float getMonthlySalary() {
        return monthlySalary;
    }

    public int getPartTimeRate() {
        return partTimeRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Company name: ");
        sb.append(company.getName() + "\n");
        sb.append("Date of Employment: ");
        sb.append(dateFormat.format(employmentDate) + "\n");
        sb.append("Full-time monthly Salary: ");
        sb.append(monthlySalary + "\n");
        sb.append("Bruto/Netto" + "\n");
        sb.append("Monthly Salary: ");
        sb.append(calculateSalaryByMonth() + "/" + applyTaxes(calculateSalaryByMonth()) + "\n");
        sb.append("Annual Salary: ");
        sb.append(calculateSalaryByYear() + "/" + applyTaxes(calculateSalaryByYear()) + "\n");
        sb.append("Extraordinary Month: ");
        sb.append(calculateSalaryExtraordinaryMonth() + "/" + applyTaxes(calculateSalaryExtraordinaryMonth()) + "\n");
        return super.toString() + sb.toString();
    }

    public float calculateSalaryByMonth() {
        return (monthlySalary / 100) * partTimeRate;
    }

    public float calculateSalaryByYear() {
        return calculateSalaryByMonth() * 12 + calculateSalaryExtraordinaryMonth();
    }

    public float calculateSalaryExtraordinaryMonth() {
        float extraordanaryRateByMonth = (calculateSalaryByMonth() / 100) * 8;
        return extraordanaryRateByMonth * 12;
    }

    public float getTaxes(float base) {
        return (base / 100) * company.getTaxRate();
    }

    public float applyTaxes(float base) {
        return base - getTaxes(base);
    }

}
