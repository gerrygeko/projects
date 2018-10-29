package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Person extends AbstractPerson {

    @Getter
    @Setter
    private float payRate;

    public Person(String name, String surname, Date birthDate, float payRate) {
        super(name, surname, birthDate);
        this.payRate = payRate;
    }

}
