package com.fabianbah.auth_server.services.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fabianbah.auth_server.dtos.CustomerPasswordDto;
import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.entities.Role;
import com.fabianbah.auth_server.exception.BusinessException;
import com.fabianbah.auth_server.repositories.CustomerRepository;
import com.fabianbah.auth_server.services.CustomerService;
import com.fabianbah.auth_server.services.PasswordService;
import com.fabianbah.auth_server.services.RoleService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class,
            DataIntegrityViolationException.class })
    public Customer createCustomer(CustomerPasswordDto customerPasswordDto) throws Exception {
        Optional<Customer> isCustomer = customerRepository.findByIdentificationIdOrEmail(
                customerPasswordDto.getIdentificationId(),
                customerPasswordDto.getEmail());

        if (isCustomer.isPresent()) {
            throw new BusinessException("El usuario con ese ID o email ya existe.", HttpStatus.CONFLICT);
        }

        LocalDate currentDate = LocalDate.now();

        Customer customer = new Customer();
        customer.setIdentificationId(customerPasswordDto.getIdentificationId());
        customer.setFullName(customerPasswordDto.getFullName());
        customer.setLastName(customerPasswordDto.getLastName());
        customer.setEmail(customerPasswordDto.getEmail());
        customer.setCreationDate(currentDate);

        Role role = roleService.getRoleByName(customerPasswordDto.getRoleName());
        customer.setRoles(List.of(role));

        Customer createdCustomer = customerRepository.save(customer);

        passwordService.createPassword(customerPasswordDto.getPassword(), createdCustomer);

        return createdCustomer;
    }

    @Override
    public Customer updateCustomer(Customer updateCustomer) {
        this.getCustomerByIdentification(updateCustomer.getIdentificationId());

        return customerRepository.save(updateCustomer);
    }

    @Override
    public Optional<Customer> getCustomerByIdentification(long CustomerIdentification) {
        Optional<Customer> CustomerById = customerRepository.findByIdentificationId(CustomerIdentification);

        if (CustomerById.isEmpty()) {
            throw new BusinessException("El usuario consultado no existe", HttpStatus.CONFLICT);
        }

        return CustomerById;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String customerEmail) throws Exception {
        return customerRepository.findByEmail(customerEmail)
            .orElse( null );
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomerByIdentification(long customerIdentification) {

        this.getCustomerByIdentification(customerIdentification)
                .ifPresentOrElse(
                        u -> customerRepository.deleteByIdentificationId(customerIdentification),
                        () -> {
                            throw new BusinessException("El usuario a eliminar no se encontro", HttpStatus.CONFLICT);
                        });
    }

}
