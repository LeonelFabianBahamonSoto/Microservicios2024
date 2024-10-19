package com.fabianbah.authorized.service;

import com.fabianbah.authorized.entity.Password;

public interface PasswordService {
    Password createPassword( Password password );

    Password updatePassword( Password password );
}
