package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;
import service.RoomConflictException;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private final CustomerService cs;
    private final ReservationService rs;

    public AdminResource(){
        super();
        this.cs = CustomerService.getInstance();
        this.rs = ReservationService.getInstance();
    }
    public Customer getCustomer(String email) {
        return cs.getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms) throws RoomConflictException {
        for (IRoom room : rooms) {
            rs.addRoom(room);
        }
    }
    public Collection<IRoom> getAllRooms(){
        return rs.getAllRooms();
    }
    public boolean doesRoomExist(String roomNumber) {
        Collection<IRoom> rooms = getAllRooms();
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return true;
            }
        }
        return false;
    }
    public Collection<Customer> getAllCustomers(){
        return cs.getAllCustomers();
    }
    public void displayAllReservations(){
        rs.printAllReservations();
    }
    public int numReservations() { return rs.getReservationsCount(); }
    public int numRooms() { return rs.getRoomsCount(); }
    public int numCustomers() {return cs.getCustomerCount(); }
}
