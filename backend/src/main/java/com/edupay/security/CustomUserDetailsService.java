package com.edupay.security;

import com.edupay.entity.User;
import com.edupay.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String roleCode = (user.getRole() != null) ? user.getRole().getCode() : "STUDENT";
        Collection<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(roleCode.startsWith("ROLE_") ? roleCode : "ROLE_" + roleCode)
        );

        boolean isEnabled = user.getIsActive() != null && user.getIsActive();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                isEnabled,
                true, true, true,
                authorities
        );
    }
}
