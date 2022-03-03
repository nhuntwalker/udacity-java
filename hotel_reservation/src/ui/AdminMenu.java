package ui;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {
    private final Map<String, String> menuItems;
    private final AdminResource admin;
    private final Scanner scanner;

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
        this.admin = new AdminResource();
        this.menuItems = new HashMap<>();

        menuItems.put("1", "1. See all Customers");
        menuItems.put("2", "2. See all Rooms");
        menuItems.put("3", "3. See all Reservations");
        menuItems.put("4", "4. Add a Room");
        menuItems.put("5", "5. Back to Main Menu");
    }

    public void printMenuItems(){
        for (String item : menuItems.values()) {
            System.out.println(item);
        }
    }
    public void selectMenuItem(String menuSelection){
        switch (menuSelection) {
            case "1" -> seeCustomers();
            case "2" -> seeRooms();
            case "3" -> seeReservations();
            case "4" -> addRooms();
            default -> printMenuItems();
        }
    }
    public void seeCustomers() {
        Collection<Customer> customers = this.admin.getAllCustomers();
        if (customers.size() == 0) {
            System.out.println("There are currently no customers.");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    public void seeRooms() {
        Collection<IRoom> rooms = this.admin.getAllRooms();
        if (rooms.size() == 0) {
            System.out.println("There are currently no rooms. Please choose option 4 to add a room.");
            return;
        }
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }
    public void seeReservations() {
        this.admin.displayAllReservations();
    }
    public void addRooms(){
        List<IRoom> rooms = new ArrayList<>();
        boolean done = false;
        while (!done) {
            rooms.add(buildRoom());

            System.out.println("Would you like to add another room? (Y)es/(N)o");
            String choice = this.scanner.nextLine().toLowerCase(Locale.ROOT);
            if (choice.equals("n") || choice.equals("no")) {
                done = true;
            } else if (choice.equals("y") || choice.equals("yes")) {
                System.out.println("Enter the information for the next room.");
            }
        }
        this.admin.addRoom(rooms);
    }

    public String getRoomNumber() {
        String roomNumber;
        do {
            System.out.println("Enter the room number.");
            roomNumber = this.scanner.nextLine();
            if (!this.admin.doesRoomExist(roomNumber)) {
                return roomNumber;
            }
            System.out.println("Room number " + roomNumber + " already exists. Try again.");
        } while (true);
    }
    public double getRoomPrice(){
        double roomPrice;
        do {
            System.out.println("Enter the room's price in USD (>= 0):");
            roomPrice = this.scanner.nextDouble();
            if (roomPrice == 0.0 || roomPrice > 0.0) {
                return roomPrice;
            }
            System.out.println(roomPrice + " is not a valid room price. Try again.");
        } while (true);
    }
    public RoomType getRoomType() {
        do {
            System.out.println("Enter the room's type. These are the room type options:");
            for (RoomType type : RoomType.values()) {
                System.out.println(type.name());
            }

            String inputType = this.scanner.nextLine().toUpperCase(Locale.ROOT);
            for (RoomType type : RoomType.values()) {
                if (type.name().equals(inputType)) {
                    return type;
                }
            }
        } while (true);
    }
    public IRoom buildRoom(){
        String roomNumber = getRoomNumber();
        double roomPrice = getRoomPrice();
        RoomType roomType = getRoomType();

        if (roomPrice == 0.0) {
            return new FreeRoom(roomNumber, roomType);
        } else {
            return new Room(roomNumber, roomPrice, roomType);
        }
    }
}
