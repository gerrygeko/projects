package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Person extends AbstractPerson {

    @Getter
    @Setter
    private double payRate;

    public Person(String name, String surname, Date birthDate, double payRate) {
        super(name, surname, birthDate);
        this.payRate = payRate;
    }

}
