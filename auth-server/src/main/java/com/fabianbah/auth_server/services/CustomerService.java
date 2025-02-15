package com.fabianbah.auth_server.services;

import java.util.List;
import java.util.Optional;

import com.fabianbah.auth_server.dtos.CustomerPasswordDto;
import com.fabianbah.auth_server.entities.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerPasswordDto newCustomer) throws Exception;

    Customer updateCustomer(Customer updateCustomer);

    Optional<Customer> getCustomerByIdentification(long CustomerIdentification);

    Customer getCustomerByEmail(String email) throws Exception;

    List<Customer> getAllCustomers();

    void deleteCustomerByIdentification(long CustomerIdentification);
}
