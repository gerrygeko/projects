package model;

import java.util.Date;

public abstract class AbstractPerson {

    private String name;
    private String surname;
    private Date bornDate;

    public AbstractPerson(String name, String surname, Date bornDate) {
        this.name = name;
        this.surname = surname;
        this.bornDate = bornDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
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
