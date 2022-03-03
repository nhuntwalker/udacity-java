package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class MainMenu extends BaseMenu {
    private final HotelResource hotel;
    private final InputDevice input;
    private final String dateFmt = "mm/dd/yyyy";
    // Date formatter source, accessed 3 Mar 2022: https://www.baeldung.com/java-string-to-date#string-date
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFmt, Locale.ENGLISH);

    public MainMenu(InputDevice input) {
        super();
        this.input = input;
        this.hotel = new HotelResource();

        menuItems.put("1", "1. Find and reserve a room");
        menuItems.put("2", "2. See my reservations");
        menuItems.put("3", "3. Create an account");
        menuItems.put("4", "4. Admin");
        menuItems.put("5", "5. Exit");
    }

    public void selectMenuItem(String menuSelection){
        switch (menuSelection) {
            case "1" -> findAndReserveRoom();
            case "2" -> checkReservation();
            case "3" -> createAccount();
            default -> printMenuItems();
        }
    }

    public Date getCheckInDate(){
        System.out.println("Enter Check-in date " + dateFmt + "example 03/14/2022");
        do {
            String inputDate = input.getInput();
            Date now = new Date();
            try {
                Date checkIn = dateFormatter.parse(inputDate);
                if (checkIn.before(now)) {
                    System.out.println("Check-in date is before today. Please enter a date after today.");
                    continue;
                }
                return checkIn;
            } catch (ParseException exc) {
                System.out.println(inputDate + " is an improperly-formatted date. Try again using the format " + dateFmt);
            }
        } while (true);
    }
    public Date getCheckOutDate(Date checkInDate) {
        System.out.println("Enter Check-out date " + dateFmt + "example 03/15/2022. Ensure that it is after the check-in date.");
        do {
            String inputDate = input.getInput();
            try {
                Date checkOut = dateFormatter.parse(inputDate);
                if (checkOut.before(checkInDate)) {
                    System.out.println("Check-out date is before the selected check-in date. Please enter a date after " + dateFormatter.format(checkInDate));
                    continue;
                }
                return checkOut;
            } catch (ParseException exc) {
                System.out.println(inputDate + " is an improperly-formatted date. Try again using the format " + dateFmt);
            }
            return checkInDate;
        } while (true);
    }
    public IRoom retrieveRoom(Collection<IRoom> rooms){
        do {
            System.out.println("\nWould you like to book a room? (Y)es/(N)o");
            String choice = input.getInput().toLowerCase(Locale.ROOT);
            if (choice.equals("y") || choice.equals("yes")) {
                break;
            } else if (choice.equals("n") || choice.equals("no")){
                return null;
            }
        } while (true);

        Customer customer;
        do {
            System.out.println("Do you have an account with us? (Y)es/(N)o");
            String choice = input.getInput().toLowerCase(Locale.ROOT);
            if (choice.equals("y") || choice.equals("yes")) {
                String email = getEmail();
                customer = hotel.getCustomer(email);
                if (customer == null) {
                    System.out.println("There isn't an account on record with your email " + email + ". Please create an account and return to reserve a room.");
                    return null;
                }
                break;
            } else if (choice.equals("n") || choice.equals("no")) {
                System.out.println("Please create an account before reserving a room");
                return null;
            }
        } while (true);

        IRoom desiredRoom;
        do {
            System.out.println("\nWhat room number would you like to reserve?");
            String desiredRoomNumber = input.getInput();

            for (IRoom room : rooms) {
                if (room.getRoomNumber().equals(desiredRoomNumber)) {
                    desiredRoom = room;
                    break;
                }
            }
            System.out.println("Room number " + desiredRoomNumber + " isn't available. Please select from the rooms displayed above.");
        } while (true);

        return desiredRoom;
    }
    public void findAndReserveRoom(){
        // get desired date
        Date checkInDate = getCheckInDate();
        Date checkOutDate = getCheckOutDate(checkInDate);

        // find available rooms
        Collection<IRoom> rooms = hotel.findARoom(checkInDate, checkOutDate);
        if (rooms.size() == 0) {
            System.out.println("There are no available rooms from " + dateFormatter.format(checkInDate) + " to " + dateFormatter.format(checkOutDate) + ". Please try again with a different period of time.");
            return;
        }

        System.out.println("\nCurrently the available rooms are:");
        for (IRoom room : rooms) {
            System.out.println(room);
        }

        // get a room
        IRoom room = retrieveRoom(rooms);

        // confirm reservation
    }

    public void checkReservation(){}

    public String getFirstName(){
        String firstName;
        do {
            System.out.println("Enter your first name:");
            firstName = input.getInput();
            if (!firstName.isEmpty()) {
                return firstName;
            }
            System.out.println("First name cannot be empty.");
        } while (true);
    }
    public String getLastName(){
        String lastName;
        do {
            System.out.println("Enter your last name:");
            lastName = input.getInput();
            if (!lastName.isEmpty()) {
                return lastName;
            }
            System.out.println("Last name cannot be empty.");
        } while (true);
    }
    public String getEmail(){
        String email;
        do {
            System.out.println("Enter your email (format: name@domain.com):");
            email = input.getInput();
            if (!email.isEmpty()) {
                return email;
            }
            System.out.println("Email cannot be empty.");
        } while (true);
    }
    public void createAccount(){
        do {
            String firstName = getFirstName();
            String lastName = getLastName();
            String email = getEmail();

            try {
                hotel.createACustomer(email, firstName, lastName);
                System.out.println("Successfully created your account!");
                System.out.println("Please select your next action.");
                return;
            } catch (IllegalArgumentException exc) {
                System.out.println("The input email " + email + " was invalid. Emails must be of the form name@domain.com");
                System.out.println("Please try to enter your information again.");
            }
        } while (true);
    }
}
