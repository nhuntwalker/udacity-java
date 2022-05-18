package model;

import java.util.Locale;

/** A room in the hotel.
 *
 * @author Nicholas Hunt-Walker
 */
public class Room implements IRoom {
    private String number;
    private Double price;
    private RoomType type;

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
        return "number: " + this.number + " Price: " + String.format("$%.2f", this.price) + " Type: " + this.type.name().toLowerCase(Locale.ROOT);
    }
}
