package mock;

import model.AbstractPerson;
import model.Company;
import model.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MockData {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static Logger logger = Logger.getLogger(MockData.class.getName());

    private static Company company1 = new Company("TOPdesk", 21);
    private static Company company2 = new Company("DonatellaRestaurant", 20);
    private static Company company3 = new Company("TUDelft", 15);


    public static ArrayList<Company> getCompanies() {

        List<Company> companiesList = new ArrayList<Company>();
        Collections.addAll(companiesList, company1, company2, company3);

        return (ArrayList<Company>) companiesList;
    }

    public static ArrayList<AbstractPerson> getAllPersons() {

        List<AbstractPerson> personList = new ArrayList<AbstractPerson>();

        try {

            AbstractPerson person1 = new Employee("Gerardo", "Lamorte", sdf.parse("12-01-1990"), company1, sdf.parse("06-11-2017"), 2300, 100);
            AbstractPerson person2 = new Employee("Fabiana", "Iacullo", sdf.parse("24-12-1988"), company2, sdf.parse("08-04-2018"), 2000, 60);
            AbstractPerson person3 = new Employee("Rocco", "Giovinazzo", sdf.parse("14-08-1990"), company1, sdf.parse("06-11-2017"), 2300, 90);
            AbstractPerson person4 = new Employee("Panagiotis", "Tevheclir", sdf.parse("16-07-1985"), company3, sdf.parse("01-03-2018"), 2300, 80);
            AbstractPerson person5 = new Employee("Federico", "Esperandos", sdf.parse("12-05-1994"), company2, sdf.parse("06-08-2018"), 2000, 50);
            AbstractPerson person6 = new Employee("Roel", "Spilker", sdf.parse("04-04-1972"), company1, sdf.parse("06-11-2000"), 8000, 100);
            Collections.addAll(personList, person1, person2, person3, person4, person5, person6);
            Collections.addAll(company1.getListOfEmployers(), (Employee)person1, (Employee)person3, (Employee)person6);
            Collections.addAll(company2.getListOfEmployers(), (Employee)person2, (Employee)person5);
            Collections.addAll(company3.getListOfEmployers(), (Employee)person4);

        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Impossible to parse the date: " + e.getMessage());
        }

        return (ArrayList<AbstractPerson>) personList;
    }

}
