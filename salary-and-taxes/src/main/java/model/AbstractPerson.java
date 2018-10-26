package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public abstract class AbstractPerson {

    private String name;
    private String surname;
    private Date bornDate;

    public AbstractPerson(String name, String surname, Date bornDate) {
        this.name = name;
        this.surname = surname;
        this.bornDate = bornDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(name + "\n");
        sb.append("Surname: ");
        sb.append(surname + "\n");
        return sb.toString();
    }

}
