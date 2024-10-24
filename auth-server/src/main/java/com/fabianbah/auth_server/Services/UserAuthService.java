package com.fabianbah.auth_server.Services;

import com.fabianbah.auth_server.dtos.TokenDto;
import com.fabianbah.auth_server.dtos.UserAuthDto;
import com.fabianbah.auth_server.entities.UserAuth;

public interface UserAuthService {
    TokenDto login( UserAuthDto userAuthDto );
    TokenDto validateToken( TokenDto tokenDto );
    void validPassword( UserAuthDto tokenDto, UserAuth userAuth );
}
