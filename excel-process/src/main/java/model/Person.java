package model;

import lombok.AllArgsConstructor;

import java.util.Date;


@AllArgsConstructor
public class Person extends AbstractPerson {

    public Person(String name, String surname, Date birthDate) {
        super(name, surname, birthDate);
    }

}
