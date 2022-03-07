package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for managing the store of customers.
 * Singleton pattern based on: https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 * @author Nick
 */
public class CustomerService {
    private static Map<String, Customer> customerMap;
    public static Integer numberOfCustomers = 0;
    private static final CustomerService instance = new CustomerService();

    private CustomerService(){
        super();
        customerMap = new HashMap<>();
    }
    public static CustomerService getInstance() {return instance;}
    public void addCustomer(String email, String firstName, String lastName) throws CustomerConflictException {
        if (getCustomer(email) != null) {
            throw new CustomerConflictException("Customer with email " + email + " already exists.");
        }
        customerMap.put(email, new Customer(firstName, lastName, email));
        numberOfCustomers++;
    }
    public Customer getCustomer(String customerEmail){
        return customerMap.get(customerEmail);
    }
    public Collection<Customer> getAllCustomers() {
        return customerMap.values();
    }
    protected void purgeCustomers(){
        customerMap = new HashMap<>();
        numberOfCustomers = 0;
    }
}