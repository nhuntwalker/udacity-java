package ui;

import api.HotelResource;

import java.util.HashMap;
import java.util.Map;

public class MainMenu {
    private final Map<String, String> menuItems;
    private final HotelResource hotel;

    public MainMenu() {
        this.hotel = new HotelResource();
        this.menuItems = new HashMap<>();

        menuItems.put("1", "1. Find and reserve a room");
        menuItems.put("2", "2. See my reservations");
        menuItems.put("3", "3. Create an account");
        menuItems.put("4", "4. Admin");
        menuItems.put("5", "5. Exit");
    }

    public void printMenuItems(){
        for (String item : menuItems.values()) {
            System.out.println(item);
        }
    }

    public void selectMenuItem(String menuSelection){

    }
}
