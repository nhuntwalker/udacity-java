package model;

import utils.BaseTestRunner;

import java.util.Date;

public class TestModels extends BaseTestRunner {
    public static void main(String[] args) {
        // Customer tests
        testCreateCustomer();
        testCreateCustomerInvalidEmail();

        // Room tests
        testCreateRoom();
        testRoomGetters();
        testRoomIsFree();

        // Free room tests
        testCreateFreeRoom();

        // Reservation tests
        testCreateReservation();
        testReservationGetters();
    }

    public static void testCreateCustomer(){
        Customer customer = new Customer("first", "second", "j@domain.com");
        pass("Created customer: " + customer);
    }
    public static void testCreateCustomerInvalidEmail(){
        try {
            new Customer("first", "second", "invalid-email");
            fail("An invalid email wasn't detected as invalid");
        } catch (IllegalArgumentException exc) {
            pass("An invalid email is seen as invalid");
        }
    }
    public static void testCreateRoom(){
        Room room = new Room("101", 65.0, RoomType.SINGLE);
        pass("Created room: " + room);
    }
    public static void testRoomGetters(){
        String roomNumber = "101";
        double roomPrice = 65.0;
        RoomType roomType = RoomType.SINGLE;
        Room room = new Room(roomNumber, roomPrice, roomType);

        if (!room.getRoomNumber().equals(roomNumber)) {
            fail("Room number " + room.getRoomNumber() + " does not match expected room number " + roomNumber);
        }
        if (!(room.getRoomPrice() == roomPrice)) {
            fail("Room price " + room.getRoomPrice() + " does not match expected room price " + roomPrice);
        }
        if (!room.getRoomType().equals(roomType)) {
            fail("Room type " + room.getRoomType() + " does not match expected room type " + roomType);
        }
        pass("All getters print appropriately");
    }
    public static void testRoomIsFree() {
        String roomNumber = "101";
        double roomPrice = 65.0;
        RoomType roomType = RoomType.SINGLE;
        Room room = new Room(roomNumber, roomPrice, roomType);
        if (room.isFree()) {
            fail("Room with price of " + roomPrice + " should not be free");
        } else {
            pass("Room with price is not free");
        }

        Room freeRoom = new Room(roomNumber, 0.0, roomType);
        if (!freeRoom.isFree()) {
            fail("Room with price of " + 0.0 + " should be free");
        } else {
            pass("Room with no price is free");
        }
    }
    public static void testCreateFreeRoom(){
        String roomNumber = "101";
        RoomType roomType = RoomType.SINGLE;
        FreeRoom room = new FreeRoom(roomNumber, roomType);
        if (!room.isFree()) {
            fail("Free room should be free with .isFree()");
        }
        pass("Created free room: " + room);
    }
    public static Customer createCustomer(){
        return new Customer("first", "second", "j@domain.com");
    }
    public static Room createRoom(){
        String roomNumber = "101";
        double roomPrice = 65.0;
        RoomType roomType = RoomType.SINGLE;
        return new Room(roomNumber, roomPrice, roomType);
    }
    public static void testCreateReservation(){
        Customer c = createCustomer();
        Room r = createRoom();
        Date checkIn = new Date();
        Date checkOut = new Date();

        Reservation res = new Reservation(c, r, checkIn, checkOut);
        pass("Created reservation: " + res);
    }
    public static void testReservationGetters(){
        Customer c = createCustomer();
        Room r = createRoom();
        Date checkIn = new Date();
        Date checkOut = new Date();

        Reservation res = new Reservation(c, r, checkIn, checkOut);
        if (res.getRoom() != r) {
            fail("Room attached to reservation isn't room it was initialized with");
        }
        if (res.getCheckInDate() != checkIn) {
            fail("Reservation check-in date isn't date it was initialized with");
        }
        if (res.getCheckOutDate() != checkOut) {
            fail("Reservation check-out date isn't date it was initialized with");
        }
        pass("All reservation attributes are accurately retrieved with getters");
    }
}
