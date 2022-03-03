package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private final Map<String, Customer> customerMap;
    public static Integer numberOfCustomers;

    public CustomerService(){
        super();
        this.customerMap = new HashMap<>();
    }
    public void addCustomer(String email, String firstName, String lastName) {
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
