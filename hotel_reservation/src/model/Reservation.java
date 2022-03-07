package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** The reservation for a single room
 *
 * @author Nicholas Hunt-Walker
 */
public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final String dateFmt = "MM/dd/yyyy";
    // Date formatter source, accessed 3 Mar 2022: https://www.baeldung.com/java-string-to-date#string-date
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFmt, Locale.ENGLISH);

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public IRoom getRoom() {
        return room;
    }

    @Override
    public String toString(){
        return "Reservation for " + customer.getEmail() + " in " + room + " from " + dateFormatter.format(checkInDate) + " to " + dateFormatter.format(checkOutDate);
    }
}
