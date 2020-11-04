package com.bat.user;

import com.bat.role.Role;
import com.bat.util.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(UserDto userDto) {
        User newUser = new User(userDto.getEmail(), userDto.getFirstName(), userDto.getLastName(),
                passwordEncoder.encode(userDto.getPassword()), Arrays.asList(roleDao.findByRole("ROLE_2")));
        userDao.insert(newUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.getByEmail(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities((Collection<Role>) user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
