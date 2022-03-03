package model;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "j@domain.com");
        System.out.println(customer);

        try {
            new Customer("first", "second", "invalid-email");
        } catch (IllegalArgumentException exc) {
            System.out.println("An invalid email is seen as invalid");
        }
    }
}
