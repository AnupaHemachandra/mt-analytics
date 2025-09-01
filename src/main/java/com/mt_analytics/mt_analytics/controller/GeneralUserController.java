package com.mt_analytics.mt_analytics.controller;

import com.mt_analytics.mt_analytics.dto.request.GeneralUserDto;
import com.mt_analytics.mt_analytics.dto.response.SuccessResponse;
import com.mt_analytics.mt_analytics.mapper.GeneralUserMapper;
import com.mt_analytics.mt_analytics.service.AuthenticationService;
import com.mt_analytics.mt_analytics.service.GeneralUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class GeneralUserController {
    private final AuthenticationService authenticationService;
    private final GeneralUserService generalUserService;
    private final GeneralUserMapper mapper;

    @PostMapping("/email-password")
    public ResponseEntity<SuccessResponse> createNewCustomer(@RequestBody GeneralUserDto generalUserDto) throws Exception {
        generalUserService.createUser(mapper.toEntity(generalUserDto));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("User created successfully.")
                .date(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
