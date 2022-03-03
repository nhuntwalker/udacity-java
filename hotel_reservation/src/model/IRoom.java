package model;

/** Common interface for all rooms in the hotel.
 *
 * @author Nicholas Hunt-Walker
 */
public interface IRoom {
    String getRoomNumber();
    Double getRoomPrice();
    RoomType getRoomType();
    boolean isFree();
}
