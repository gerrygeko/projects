import mock.MockData;
import model.AbstractPerson;
import model.Company;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        ArrayList<AbstractPerson> personList = MockData.getAllPersons();
        ArrayList<Company> companyList = MockData.getCompanies();

        System.out.println(" ---------------------------------------");
        System.out.println("|       Information on Employees        |");
        System.out.println(" --------------------------------------- \n");

        for(AbstractPerson person : personList) {
            System.out.println(person.toString());
        }

        System.out.println(" ---------------------------------------");
        System.out.println("|         Taxes from Companies          |");
        System.out.println(" --------------------------------------- \n");

        for(Company company : companyList) {
            System.out.println(company.toString());
        }

        personList.stream().forEach(s->s.toString());
        AbstractPerson[] personListArrays = new AbstractPerson[personList.size()];
        personList.toArray(personListArrays);

        Stream<AbstractPerson> stream = Stream.of(personListArrays);
        stream.forEach(s->s.toString());
    }

}
