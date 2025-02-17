package com.fabianbah.auth_server.services.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fabianbah.auth_server.entities.Customer;
import com.fabianbah.auth_server.entities.Password;
import com.fabianbah.auth_server.repositories.PasswordRepository;
import com.fabianbah.auth_server.services.PasswordService;

@Service
@Transactional
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class,
            DataIntegrityViolationException.class })
    public Password createPassword(String password, Customer customer) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }

        if (customer == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        Password newPassword = new Password();
        String pwdEncoded = passwordEncoder.encode(password);
        newPassword.setPassword(pwdEncoded);
        newPassword.setCreationDate(customer.getCreationDate());
        newPassword.setCustomer(customer);

        return passwordRepository.save(newPassword);
    }

    @Override
    public Password getPasswordByCustomerId(long customerId) throws Exception {
        // Optional<Password> isPassword = passwordRepository.findByUserId(userId);

        // if( isPassword.isEmpty() ){
        // throw new RuntimeException("La contraseña no existe o no esta asociada a un
        // usuario.");
        // }

        // return isPassword.getClass();

        return passwordRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("La contraseña no existe o no está asociada a un usuario."));
    }

    @Override
    public Password updatePassword(String newPassword, long userIdentification) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
    }
}
