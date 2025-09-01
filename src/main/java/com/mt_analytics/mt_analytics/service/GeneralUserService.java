package com.mt_analytics.mt_analytics.service;

import com.mt_analytics.mt_analytics.entity.Authentication;
import com.mt_analytics.mt_analytics.entity.GeneralUser;
import com.mt_analytics.mt_analytics.enums.LoginType;
import com.mt_analytics.mt_analytics.enums.Role;
import com.mt_analytics.mt_analytics.repository.GeneralUserRepositoory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralUserService {
    private final AuthenticationService authenticationService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GeneralUserRepositoory generalUserRepositoory;

    public void createUser(GeneralUser generalUser) throws Exception {
        if (authenticationService.isUserAvailable(generalUser.getAuthentication().getEmail())){
            System.out.println("USER ALREADY EXISTS!!!!");
            throw new Exception("User already exists.");
        }

        Authentication authentication = generalUser.getAuthentication();
        authentication.setPasswordHash(passwordEncoder.encode(authentication.getPasswordHash()));
        List<Role> roles = new ArrayList<>();
        roles.add(Role.CUSTOMER);
        authentication.setRoles(roles);
        authentication.setLoginType(LoginType.EMAIL_PASSWORD);
        Authentication savedAuthentication = authenticationService.saveAuthentication(authentication);

        generalUser.setAuthentication(savedAuthentication);
        GeneralUser savedGeneralUser = generalUserRepositoory.save(generalUser);
        savedAuthentication.setGeneralUser(savedGeneralUser);
        authenticationService.saveAuthentication(savedAuthentication);

//        return authenticationService.emailPasswordLogin(savedAuthentication.getEmail(), savedAuthentication.getPassword());
    }
}
