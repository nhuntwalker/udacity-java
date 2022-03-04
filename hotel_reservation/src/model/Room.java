package model;

/** A room in the hotel.
 *
 * @author Nicholas Hunt-Walker
 */
public class Room implements IRoom {
    protected String number;
    protected Double price;
    protected RoomType type;

    public Room(String number, Double price, RoomType type){
        this.number = number;
        this.price = price;
        this.type = type;
    }

    public String getRoomNumber(){
        return number;
    }
    public Double getRoomPrice(){
        return price;
    }
    public RoomType getRoomType(){
        return type;
    }
    public boolean isFree(){
        return price == 0.0;
    }

    @Override
    public String toString(){
        return "Room number: " + this.number + " Price: " + this.price + " Type: " + this.type.name();
    }
}
