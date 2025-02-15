package com.fabianbah.auth_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabianbah.auth_server.dtos.CustomerPasswordDto;
import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.services.CustomerService;

@RequestMapping(path = "/users")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createUser")
    ResponseEntity<Customer> createUser(@RequestBody CustomerPasswordDto userPasswordDto) throws Exception {
        Customer user = customerService.createCustomer(userPasswordDto);

        return new ResponseEntity<Customer>(user, HttpStatus.CREATED);
    }

    @GetMapping("/getcustomerbyemail/{customerEmail}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String customerEmail) throws Exception {
        Customer customerObtained = customerService.getCustomerByEmail(customerEmail);

        if( customerObtained == null ){
            throw new RuntimeException( "El usuario asociado al email no existe" );
        }

        return new ResponseEntity<Customer>(customerObtained, HttpStatus.OK);
    }

    @GetMapping("/getAllCustomers")
    ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customersList = customerService.getAllCustomers();

        return new ResponseEntity<List<Customer>>(customersList, HttpStatus.OK);
    }
}