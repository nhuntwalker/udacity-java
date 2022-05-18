package ui;

import api.AdminResource;
import model.*;
import service.RoomConflictException;

import java.util.*;

public class AdminMenu extends BaseMenu {
    private final AdminResource admin;
    private final InputDevice input;
    private final Random randGen = new Random();

    public AdminMenu(InputDevice input) {
        super();
        this.input = input;
        this.admin = new AdminResource();

        menuItems.put("1", "1. See all Customers");
        menuItems.put("2", "2. See all Rooms");
        menuItems.put("3", "3. See all Reservations");
        menuItems.put("4", "4. Add a Room");
        menuItems.put("5", "5. Populate test data");
        menuItems.put("6", "6. Back to Main Menu");
    }

    public void selectMenuItem(String menuSelection){
        switch (menuSelection) {
            case "1" -> seeCustomers();
            case "2" -> seeRooms();
            case "3" -> seeReservations();
            case "4" -> addRooms();
            case "5" -> populateTestData();
            default -> printMenuItems();
        }
    }
    public void seeCustomers() {
        Collection<Customer> customers = admin.getAllCustomers();
        if (customers.size() == 0) {
            System.out.println("There are currently no customers.");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    public void seeRooms() {
        Collection<IRoom> rooms = admin.getAllRooms();
        if (rooms.size() == 0) {
            System.out.println("There are currently no rooms. Please choose option 4 to add a room.");
            return;
        }
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }
    public void seeReservations() {
        admin.displayAllReservations();
    }
    public void addRooms(){
        List<IRoom> rooms = new ArrayList<>();
        boolean done = false;
        while (!done) {
            rooms.add(buildRoom());

            System.out.println("Would you like to add another room? (Y)es/(N)o");
            String choice = input.getInput().toLowerCase(Locale.ROOT);
            if (choice.equals("n") || choice.equals("no")) {
                done = true;
            } else if (choice.equals("y") || choice.equals("yes")) {
                System.out.println("Enter the information for the next room.");
            }
        }
        try {
            this.admin.addRoom(rooms);
        } catch (RoomConflictException exc) {
            System.out.println("One of the rooms you attempted to create already exists.");
        }
    }

    public String getRoomNumber() {
        String roomNumber;
        do {
            System.out.println("Enter the room number.");
            roomNumber = input.getInput();
            if (!this.admin.doesRoomExist(roomNumber) && !roomNumber.equals("")) {
                return roomNumber;
            }
            System.out.println("Room number " + roomNumber + " already exists. Try again.");
        } while (true);
    }
    public double getRoomPrice(){
        double roomPrice;
        do {
            System.out.println("Enter the room's price in USD (>= 0):");

            try {
                roomPrice = Double.parseDouble(input.getInput());
                if (roomPrice == 0.0 || roomPrice > 0.0) {
                    return roomPrice;
                }
                System.out.println(roomPrice + " is not a valid room price. Try again.");
            } catch (InputMismatchException exc) {
                System.out.println("Please enter a numerical value for the room's price.");
            }

        } while (true);
    }
    public RoomType getRoomType() {
        do {
            System.out.println("Enter the room's type. These are the room type options:");
            for (RoomType type : RoomType.values()) {
                System.out.println((type.ordinal() + 1) + " for " + type.name().toLowerCase(Locale.ROOT) + " bed.");
            }

            int inputType = Integer.parseInt(input.getInput()) - 1;
            for (RoomType type : RoomType.values()) {
                if (type.ordinal() == inputType) {
                    return type;
                }
            }
            System.out.println(inputType + " is not a valid room type. Try again.");
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
    public void populateTestData() {
        generateRooms(10, false);
        generateRooms(5, true);
//        build customers
//        build reservations
    }
    private void generateRooms(int count, boolean isFree) {
        List<IRoom> newRooms = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            RoomType rType;
            if (i % 2 == 0) {
                rType = RoomType.SINGLE;
            } else {
                rType = RoomType.DOUBLE;
            }

            IRoom room;
            if (!isFree) {
                double price = randGen.nextInt(50, 500);
                room = new Room("10" + i, price, rType);
            } else {
                room = new FreeRoom("20" + i, rType);
            }
            newRooms.add(room);
        }
        try {
            this.admin.addRoom(newRooms);
        } catch (RoomConflictException exc) {
            System.out.println("One of the rooms you attempted to create already exists.");
        }
    }
}
