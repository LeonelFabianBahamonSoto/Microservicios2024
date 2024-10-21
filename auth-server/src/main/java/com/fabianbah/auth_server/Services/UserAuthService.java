package com.fabianbah.auth_server.Services;

import com.fabianbah.auth_server.dtos.TokenDto;
import com.fabianbah.auth_server.dtos.UserAuthDto;

public interface UserAuthService {
    TokenDto login( UserAuthDto userAuthDto );
    TokenDto validateToken( TokenDto tokenDto );
}
