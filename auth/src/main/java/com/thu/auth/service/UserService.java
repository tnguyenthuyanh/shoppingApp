package com.thu.auth.service;

import com.thu.auth.dao.UserDao;
import com.thu.auth.domain.entity.Permission;
import com.thu.auth.domain.entity.User;
import com.thu.auth.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.loadUserByUsername(email);

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // database user

        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getEmail())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        for (Permission permission :  user.getPermissions()){
            userAuthorities.add(new SimpleGrantedAuthority(permission.getValue()));
        }

        return userAuthorities;
    }

    public boolean canUseUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.loadUserByUsername(email);

        if (!userOptional.isPresent()){
            return true;
        }

        return false;
    }

    public void createNewUser(String email, String password) {
        userDao.createNewUser(email, password);
    }

}
