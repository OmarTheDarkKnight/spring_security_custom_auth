package com.bat.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserDto userDto);
    User findUserByEmail(String email);
}
