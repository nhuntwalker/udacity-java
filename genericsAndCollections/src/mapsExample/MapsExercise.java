package mapsExample;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class MapsExercise {
    public static void main(String[] args) {
        Map<String, Person> mapOfPeople = new HashMap<String, Person>();
        List<Person> people = new ArrayList<Person>();

        Person jim = new Person("jim", "jim.halpert@dundermifflin.com");
        Person pam = new Person("pam", "pam.beasley@dunermifflin.com");
        Person nathaniel = new Person("nathaniel", "nathaniel.huntwalker@gmail.com");
        Person kaiyah = new Person("kaiyah", "kaiyah.huntwalker@gmail.com");

        people.add(jim);
        people.add(pam);
        people.add(nathaniel);
        people.add(kaiyah);

        for (Person person : people) {
            mapOfPeople.put(person.getEmail(), person);
        }

        System.out.println(mapOfPeople.get("kaiyah.huntwalker@gmail.com"));
    }
}
