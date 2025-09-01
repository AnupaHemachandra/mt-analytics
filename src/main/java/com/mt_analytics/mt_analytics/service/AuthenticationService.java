package com.mt_analytics.mt_analytics.service;

import com.mt_analytics.mt_analytics.entity.Authentication;
import com.mt_analytics.mt_analytics.entity.GeneralUser;
import com.mt_analytics.mt_analytics.enums.LoginType;
import com.mt_analytics.mt_analytics.enums.Role;
import com.mt_analytics.mt_analytics.repository.AuthenticationRepository;
import com.mt_analytics.mt_analytics.repository.GeneralUserRepositoory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final GeneralUserRepositoory generalUserRepositoory;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Map<String, String> emailPasswordLogin(String email, String password) throws Exception {
        Optional<Authentication> optionalAuthentication = authenticationRepository.findByEmail(email);
        if (optionalAuthentication.isEmpty()){
            throw new UsernameNotFoundException("User not found.");
        }
        Authentication authentication = optionalAuthentication.get();
        if (authentication.getLoginType() != LoginType.EMAIL_PASSWORD){
            throw new Exception("Invalid login method.");
        }

        org.springframework.security.core.Authentication auth =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email, password));

        if (!auth.isAuthenticated()) {
//            logger.error(
//                    "Unable to create authentication token for admin {}", authRequestDto.getUsername());
            throw new BadCredentialsException("Invalid Password.");
        }

        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", jwtService.generateAccessToken(authentication));
        httpHeaders.put("Refresh-Token", jwtService.generateRefreshToken(authentication));

        return httpHeaders;
    }

    public Authentication saveAuthentication(Authentication authentication) throws Exception {
//        Optional<Authentication> optionalAuthentication = authenticationRepository.findByEmail(authentication.getEmail());
//        if (optionalAuthentication.isPresent()){
//            System.out.println(optionalAuthentication.get());
//            throw new Exception("User already exists.");
//        }
        return authenticationRepository.save(authentication);
    }

    @Transactional
    public Map<String, String> emailPasswordSignUp(GeneralUser generalUser) throws Exception {
        Authentication newAuthentication = generalUser.getAuthentication();
        Optional<Authentication> existingAuthentication = authenticationRepository.findByEmail(newAuthentication.getEmail());
        if (existingAuthentication.isPresent()){
            throw new Exception("User already exists.");
        }
        generalUserRepositoory.save(generalUser);

        newAuthentication.setGeneralUser(generalUser);
        newAuthentication.setLoginType(LoginType.EMAIL_PASSWORD);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.CUSTOMER);
        newAuthentication.setRoles(roles);
        newAuthentication.setPasswordHash(passwordEncoder.encode(newAuthentication.getPasswordHash()));

        Authentication authentication = authenticationRepository.save(newAuthentication);

        return emailPasswordLogin(authentication.getEmail(), authentication.getPassword());
    }

    public boolean isUserAvailable(String email){
        Optional<Authentication> optionalAuthentication = authenticationRepository.findByEmail(email);
        return optionalAuthentication.isPresent();
    }
}
