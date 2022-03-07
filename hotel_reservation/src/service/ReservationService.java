package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import javax.print.attribute.HashPrintServiceAttributeSet;
import java.util.*;

/**
 * Service for managing the store and operations of reservations.
 * Singleton pattern based on: https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 * @author Nick
 */
public class ReservationService {
    private static HashMap<String, IRoom> roomsMap;
    private static HashMap<String, List<Reservation>> reservationsMap;
    private static final ReservationService instance = new ReservationService();

    public static Integer numReservations = 0;
    public static Integer numRooms = 0;

    private ReservationService(){
        super();
        roomsMap = new HashMap<>();
        reservationsMap = new HashMap<>();
    }
    public static ReservationService getInstance(){return instance;}
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
    protected void purgeReservations(){
        roomsMap = new HashMap<>();
        reservationsMap = new HashMap<>();
        numReservations = 0;
        numRooms = 0;
    }
}
