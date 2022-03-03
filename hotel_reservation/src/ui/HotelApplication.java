package ui;

public class HotelApplication {
    public static void main(String[] args) {
        InputDevice input = new InputDevice();
        MainMenu mainMenu = new MainMenu(input);
        AdminMenu adminMenu = new AdminMenu(input);

        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();
        mainMenu.printMenuItems();

        String choice;
        boolean keepGoing = true;
        String menuType = "main";

        do {
            System.out.println("Please enter a number for the menu option");

            if (menuType.equals("main")) {
                choice = input.getInput();
                if (choice.equals("4")) {
                    System.out.println("Switching to the admin menu");
                    menuType = "admin";
                    adminMenu.printMenuItems();
                } else if (choice.equals("5")) {
                    keepGoing = false;
                } else {
                    mainMenu.selectMenuItem(choice);
                    mainMenu.printMenuItems();
                }
            } else {
                choice = input.getInput();
                if (choice.equals("5")) {
                    System.out.println("Returning to the main menu");
                    menuType = "main";
                    mainMenu.printMenuItems();
                } else {
                    adminMenu.selectMenuItem(choice);
                    adminMenu.printMenuItems();
                }
            }
        }
        while (keepGoing);
    }
}
