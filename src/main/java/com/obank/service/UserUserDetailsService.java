package com.obank.service;

import com.obank.config.UserDetailsConfig;
import com.obank.entity.User;
import com.obank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.getReferenceByUsername(username));
        return user.map(UserDetailsConfig::new).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
