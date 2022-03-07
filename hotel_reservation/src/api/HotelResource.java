package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerConflictException;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private final CustomerService cs;
    private final ReservationService rs;

    public HotelResource(){
        super();
        this.cs = CustomerService.getInstance();
        this.rs = ReservationService.getInstance();
    }
    public Customer getCustomer(String email) {return cs.getCustomer(email);}
    public void createACustomer(String email, String firstName, String lastName) throws CustomerConflictException {cs.addCustomer(email, firstName, lastName);}
    public IRoom getRoom(String roomNumber) {return rs.getARoom(roomNumber);}
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return rs.reserveARoom(cs.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }
    public Collection<Reservation> getCustomersReservations(String customerEmail){
        return rs.getCustomersReservation(cs.getCustomer(customerEmail));
    }
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return rs.findRooms(checkIn, checkOut);
    }
}
