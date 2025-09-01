package com.mt_analytics.mt_analytics.service;

import com.mt_analytics.mt_analytics.entity.Authentication;
import com.mt_analytics.mt_analytics.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final AuthenticationRepository authenticationRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Authentication> optionalAuthentication = authenticationRepository.findByEmail(username);

        if (optionalAuthentication.isEmpty()){
            throw new UsernameNotFoundException("User not found.");
        }
        return optionalAuthentication.get();
    }
}
