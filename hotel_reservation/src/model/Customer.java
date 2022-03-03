package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** A customer of the hotel
 *
 * @author Nicholas Hunt-Walker
 */
public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer (String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        validateEmail(email);
        this.email = email;
    }

    /** Checks an input email to ensure that it follows the format xx@xx.xx. Throws an exception if not.
     *
     * @param email: email address for a customer
     * @throws IllegalArgumentException: if the email provided is malformed, an exception is thrown
     */
    private void validateEmail(String email) throws IllegalArgumentException {
        String emailCheck = "^(.+)@(.+).(.+)$";

        Pattern pattern = Pattern.compile(emailCheck);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("input email " + email + " is not valid.");
        }
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        return "First name: " + firstName + " Last name: " + lastName;
    }
}
