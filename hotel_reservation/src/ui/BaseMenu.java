package ui;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseMenu {
    protected static final String separator = "-------------------------------------------------";
    protected Map<String, String> menuItems;

    public BaseMenu(){
        super();
        this.menuItems = new HashMap<>();
    }

    public void printMenuItems(){
        System.out.println(separator);
        for (String item : menuItems.values()) {
            System.out.println(item);
        }
        System.out.println(separator);
    }
    abstract void selectMenuItem(String menuSelection);
}
