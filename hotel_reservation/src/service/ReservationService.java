package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private final HashMap<String, IRoom> roomsMap;
    private final HashMap<String, List<Reservation>> reservationsMap;

    public static Integer numReservations = 0;
    public static Integer numRooms = 0;

    public ReservationService(){
        super();
        this.roomsMap = new HashMap<>();
        this.reservationsMap = new HashMap<>();
    }
    public void addRoom(IRoom room) throws RoomConflictException {
        if (getARoom(room.getRoomNumber()) != null) {
            throw new RoomConflictException("Room with number " + room.getRoomNumber() + " already exists.");
        }
        roomsMap.put(room.getRoomNumber(), room);
        numRooms++;
    }
    public IRoom getARoom(String roomId) {
        return roomsMap.get(roomId);
    }
    public Collection<IRoom> getAllRooms() {
        return roomsMap.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = findRooms(checkInDate, checkOutDate);
        if (!availableRooms.contains(room)) {
            return null;
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        List<Reservation> customerReservations = reservationsMap.get(customer.getEmail());
        if (customerReservations == null) {
            customerReservations = new ArrayList<>();
        }
        customerReservations.add(reservation);
        reservationsMap.put(customer.getEmail(), customerReservations);
        numReservations++;
        return reservation;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<List<Reservation>> allCustomerReservations = reservationsMap.values();
        if (allCustomerReservations.size() == 0) {
            return roomsMap.values();
        }

        Set<IRoom> bookedRooms = new HashSet<>();
        for (List<Reservation> reservationList : allCustomerReservations) {
            for (Reservation res : reservationList) {
                if (
                        checkInDate.after(res.getCheckInDate()) && checkInDate.before(res.getCheckOutDate()) ||
                                checkOutDate.after(res.getCheckInDate()) && checkOutDate.before(res.getCheckOutDate()) ||
                                checkInDate.before(res.getCheckInDate()) && checkOutDate.after(res.getCheckOutDate()) ||
                                checkInDate.equals(res.getCheckInDate()) ||
                                checkInDate.equals(res.getCheckOutDate()) ||
                                checkOutDate.equals(res.getCheckInDate()) ||
                                checkOutDate.equals(res.getCheckOutDate())
                ) {
                    bookedRooms.add(res.getRoom());
                }
            }
        }

        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : roomsMap.values()) {
            if (!bookedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    public Collection<Reservation> getCustomersReservation(Customer customer){
        return reservationsMap.get(customer.getEmail());
    }
    public void printAllReservations(){
        for (String customerEmail : reservationsMap.keySet()) {
            List<Reservation> customerReservations = reservationsMap.get(customerEmail);
            for (Reservation reservation : customerReservations) {
                System.out.println(reservation);
            }
        }
    }
}
