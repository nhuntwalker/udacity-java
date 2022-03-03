package ui;

import java.util.Scanner;

public class HotelApplication {
    private static final String separator = "-------------------------------------------------";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu(scanner);
        AdminMenu adminMenu = new AdminMenu(scanner);

        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();
        System.out.println(separator);
        mainMenu.printMenuItems();
        System.out.println(separator);

        String choice;
        boolean goOn = true;
        String menuType = "main";

        do {
            System.out.println("Please enter a number for the menu option");
            choice = scanner.nextLine();

            if (menuType.equals("main")) {
                if (choice.equals("4")) {
                    menuType = "admin";
                    adminMenu.printMenuItems();
                } else if (choice.equals("5")) {
                    goOn = false;
                } else {
                    mainMenu.selectMenuItem(choice);
                }
            } else {
                if (choice.equals("5")) {
                    menuType = "menu";
                    mainMenu.printMenuItems();
                } else {
                    adminMenu.selectMenuItem(choice);
                }
            }
        }
        while (goOn);
    }
}
