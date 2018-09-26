package model;

public interface IEmployee {

    float calculateSalaryByMonth();
    float calculateSalaryByYear();
    float calculateSalaryExtraordinaryMonth();
    float getTaxes(float base);

}
