package com.capstone.bookstore.services;

import com.capstone.bookstore.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting login for: " + username);
        return userRepository.findUserByUserName(username)
                .map(user -> {
                        System.out.println("Found user: " + user.getUserName());
                        return org.springframework.security.core.userdetails.User
                                    .withUsername(user.getUserName())
                                    .password(user.getPasswordHash()) // hashed password from DB
                                    .roles("USER")         // or whatever roles you want
                                    .build();
                    }
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
