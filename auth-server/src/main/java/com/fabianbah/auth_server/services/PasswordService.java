package com.fabianbah.auth_server.services;

import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.entities.Password;

public interface PasswordService {
    Password getPasswordByCustomerId(long customerId) throws Exception;

    Password createPassword(String Password, Customer customer);

    Password updatePassword(String newPassword, long customerIdentification);
}
