package com.fabianbah.authorized.service;

import com.fabianbah.authorized.entity.Password;
import com.fabianbah.authorized.entity.User;

public interface PasswordService {
    Password createPassword( String password, User user );
    Password savePassword( Password password );
    Password updatePassword( Password password );
}
