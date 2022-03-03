package collectionExample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingExample {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>();
        names.add("jamal");
        names.add("beatrice");
        names.add("kaiyah");
        names.add("nathaniel");

        Collections.sort(names);

        for (String name : names) {
            System.out.println(name);
        }
    }
}
