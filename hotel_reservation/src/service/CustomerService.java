package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private final Map<String, Customer> customerMap;
    public static Integer numberOfCustomers = 0;

    public CustomerService(){
        super();
        this.customerMap = new HashMap<>();
    }
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
}