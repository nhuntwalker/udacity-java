package service;

public class RoomConflictException extends Exception {
    public RoomConflictException(String errorMessage) {
        super(errorMessage);
    }
}
