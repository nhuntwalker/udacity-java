package service;

public class CustomerConflictException extends Exception {
    public CustomerConflictException(String errorMessage) {
        super(errorMessage);
    }
}
