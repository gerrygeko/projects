package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public abstract class AbstractPerson {

    private final String name;
    private final String surname;
    private final Date bornDate;

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
