package service;

import model.*;
import utils.BaseTestRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TestServices extends BaseTestRunner {
    public static void main(String[] args) {
        // test CustomerService
        testCreateCustomerService();
        testAddCustomer();
        testGetCustomer();
        testCustomerConflict();
        testGetAllCustomers();

        // test ReservationService
        testCreateReservationService();
        testAddRoom();
        testRoomConflict();
        testGetARoom();
        testGetAllRooms();
        testReserveARoom();
        testFindRooms();
        testGetCustomersReservation();
    }
    public static void testCreateCustomerService(){
        CustomerService cs = new CustomerService();
        pass("New CustomerService was able to be initialized: " + cs);
    }
    public static void testAddCustomer(){
        CustomerService cs = new CustomerService();
        try {
            cs.addCustomer("john@gmail.com", "john", "blank");
        } catch (CustomerConflictException exc) {
            fail("There was a conflict in customer accounts when there absolutely shouldn't have been");
        }
        if (CustomerService.numberOfCustomers != 1) {
            fail("A new customer was added but the count of customers didn't increase");
        }
        pass("A new customer was added and tracked within the CustomerService object");
    }
    public static void testGetCustomer(){
        CustomerService cs = new CustomerService();
        String email = "john@gmail.com";
        if (cs.getCustomer(email) != null) {
            fail("A customer was able to be added despite not having been entered");
        }
        pass("A customer that wasn't added wasn't found");

        try {
            cs.addCustomer(email, "john", "blank");
        } catch (CustomerConflictException exc) {
            fail("There was a conflict in customer accounts when there absolutely shouldn't have been");
        }
        if (cs.getCustomer(email) == null) {
            fail("A customer had been added and yet they weren't able to be retrieved");
        }
        pass("A customer that was added was found");
    }
    public static void testCustomerConflict(){
        String email = "john@gmail.com";
        CustomerService cs = new CustomerService();
        try {
            cs.addCustomer(email, "john", "blank");
        } catch (CustomerConflictException exc) {
            fail("A customer conflict occurred but there should be no customers");
        }
        try {
            cs.addCustomer(email, "john", "flank");
            fail("A customer with the same email shouldn't be able to be added twice without exception");
        } catch (CustomerConflictException exc) {
            pass("Two customer accounts can't exist with the same email.");
        }
    }
    public static void testGetAllCustomers(){
        CustomerService cs = new CustomerService();
        Collection<Customer> emptyCustomers = cs.getAllCustomers();
        if (emptyCustomers.size() != 0) {
            fail("Retrieved customers that shouldn't exist");
        }

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("john", "blank", "john@gmail.com"));
        customers.add(new Customer("nathaniel", "hunt-walker", "nathan@gmail.com"));
        customers.add(new Customer("kaiyah", "hodge", "kaiyah@gmail.com"));

        try {
            for (Customer c : customers) {
                cs.addCustomer(c.getEmail(), c.getFirstName(), c.getLastName());
            }
        } catch (CustomerConflictException exc){
            fail("A conflict in customer emails occurred despite there being no obvious conflict.");
        }

        Collection<Customer> retrieved = cs.getAllCustomers();
        if (retrieved.size() != customers.size()) {
            fail("Retrieved a different number of customers than was initially given");
        }
        List<String> emails = new ArrayList<>();
        for (Customer c : retrieved) {
            emails.add(c.getEmail());
        }
        for (Customer c : customers) {
            if (!emails.contains(c.getEmail())) {
                fail("Retrieved customers didn't include one of the customers that was added");
            }
        }
        pass("All customers that were added were able to be retrieved");
    }

    public static void testCreateReservationService(){
        ReservationService rs = new ReservationService();
        pass("Was able to create reservation service: " + rs);
    }
    public static void testAddRoom(){
        ReservationService rs = new ReservationService();
        Room room = new Room("101", 65.0, RoomType.SINGLE);
        try {
            rs.addRoom(room);
        } catch (RoomConflictException exc) {
            fail("There should have been no rooms in existence before this one.");
        }
        if (ReservationService.numRooms != 1) {
            fail("A room was added but not tracked");
        }
        pass("Room was successfully added and tracked");
    }
    public static void testRoomConflict(){
        ReservationService rs = new ReservationService();
        Room room1 = new Room("101", 65.0, RoomType.SINGLE);
        Room room2 = new Room("101", 65.0, RoomType.DOUBLE);
        try {
            rs.addRoom(room1);
            rs.addRoom(room2);
            fail("A second room shouldn't be able to be added with the same room number");
        } catch (RoomConflictException exc) {
            pass("Cannot add another room with the same room number");
        }
    }
    public static void testGetARoom(){
        ReservationService rs = new ReservationService();
        String roomId = "101";
        if (rs.getARoom(roomId) != null) {
            fail("A room was found even though none had been added");
        }

        Room room = new Room(roomId, 65.0, RoomType.SINGLE);
        try {
            rs.addRoom(room);
        } catch (RoomConflictException exc) {
            fail("A room should have been able to be added without conflict");
        }

        IRoom retrieved = rs.getARoom(roomId);
        if (retrieved == null) {
            fail("Should have been able to retrieve the added room");
        }
        pass("A room that has been added is able to be retrieved");
    }
    public static void testGetAllRooms(){
        ReservationService rs = new ReservationService();
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("101", 65.0, RoomType.SINGLE));
        rooms.add(new Room("102", 65.0, RoomType.DOUBLE));
        rooms.add(new Room("103", 75.0, RoomType.SINGLE));

        try {
            for (Room room : rooms) {
                rs.addRoom(room);
            }
        } catch (RoomConflictException exc) {
            fail("There was a conflict in adding rooms that shouldn't have happened.");
        }

        Collection<IRoom> retrieved = rs.getAllRooms();
        for (Room room : rooms) {
            if (!retrieved.contains(room)) {
                fail("A room that was retrieved was unable to be found amongst those added");
            }
        }
        pass("All rooms that were added were able to be retrieved");
    }
    public static void testReserveARoom(){
        Customer c = new Customer("john", "blank", "john@email.com");
        Room room = new Room("101", 65.0, RoomType.SINGLE);
        Date checkIn = new Date();
        Date checkOut = new Date();

        ReservationService rs = new ReservationService();
        Reservation res = rs.reserveARoom(c, room, checkIn, checkOut);
        if (res != null) {
            fail("Was able to make a reservation for a room that doesn't exist yet");
        }
        if (ReservationService.numReservations != 0) {
            fail("A reservation was counted when none was made");
        }

        // Make a reservation and room is available
        try {
            rs.addRoom(room);
        } catch (RoomConflictException exc) {
            fail("Attempted to add a room and got a conflict despite there being no rooms");
        }
        res = rs.reserveARoom(c, room, checkIn, checkOut);
        if (res == null) {
            fail("Was unable to reserve a room despite it being available");
        }
        if (res.getRoom() != room || res.getCheckInDate() != checkIn || res.getCheckOutDate() != checkOut) {
            fail("The reservation didn't have the specified info attached");
        }
        if (ReservationService.numReservations != 1) {
            fail("The number of reservations was not incremented when a reservation was added");
        }

        pass("A room was able to be reserved");
    }
    public static void testFindRooms(){
        Customer c = new Customer("john", "blank", "john@email.com");
        Date checkIn = new Date();
        Date checkOut = new Date();

        ReservationService rs = new ReservationService();
        Collection<IRoom> retrieved = rs.findRooms(checkIn, checkOut);
        if (retrieved.size() != 0) {
            fail("Rooms were found despite there being no rooms");
        }
        pass("When there are no rooms, no rooms can be found");

        Room room = new Room("101", 65.0, RoomType.SINGLE);
        try {
            rs.addRoom(room);
        } catch (RoomConflictException exc) {
            fail("Attempted to add a room and got a conflict despite there being no rooms");
        }

        retrieved = rs.findRooms(checkIn, checkOut);
        if (retrieved.size() != 1 || !retrieved.contains(room)) {
            fail("The one available and existing room wasn't found");
        }
        pass("The room that was available was found");

        rs.reserveARoom(c, room, checkIn, checkOut);
        retrieved = rs.findRooms(checkIn, checkOut);
        if (retrieved.size() != 0) {
            fail("The one room in existence was still seen as available despite being reserved.");
        }

        pass("Conflicting dates didn't return the an unavailable room.");
    }
    public static void testGetCustomersReservation(){
        Customer c = new Customer("john", "blank", "john@email.com");
        Room room = new Room("101", 65.0, RoomType.SINGLE);
        Date checkIn = new Date();
        Date checkOut = new Date();

        ReservationService rs = new ReservationService();
        Collection<Reservation> reservations = rs.getCustomersReservation(c);
        if (reservations != null) {
            fail("A customer's reservations were able to be retrieved despite there being none under that name");
        }
        pass("When there are no reservations at all, a customer's reservations are empty");

        try {
            rs.addRoom(room);
        } catch (RoomConflictException exc) {
            fail("Attempted to add a room and got a conflict despite there being no rooms");
        }

        Reservation res = rs.reserveARoom(c, room, checkIn, checkOut);
        reservations = rs.getCustomersReservation(c);
        if (reservations == null || reservations.size() != 1 || !reservations.contains(res)) {
            fail("When a reservation has been made, still can't get the customer's reservation");
        }
        pass("When a reservation has been made, that customer's reservations are returned");
    }
}
