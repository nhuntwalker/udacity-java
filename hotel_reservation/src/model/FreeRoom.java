package model;

/** A room that has no fee attached.
 *
 * @author Nicholas Hunt-Walker
 */
public class FreeRoom extends Room {
    public FreeRoom (String number, RoomType type) {
        super(number, 0.0, type);
    }

    @Override
    public String toString(){
        return "Room number: " + this.number + "\nType: " + this.type;
    }
}

