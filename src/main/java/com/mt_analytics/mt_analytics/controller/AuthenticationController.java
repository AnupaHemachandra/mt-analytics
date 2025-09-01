package com.mt_analytics.mt_analytics.controller;

import com.mt_analytics.mt_analytics.dto.request.AuthRequestDto;
import com.mt_analytics.mt_analytics.dto.response.SuccessResponse;
import com.mt_analytics.mt_analytics.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/email-password")
    public ResponseEntity<SuccessResponse> emailPasswordLogin(@RequestBody AuthRequestDto authRequestDto) throws Exception {
        Map<String, String> authHeaders = authenticationService.emailPasswordLogin(authRequestDto.getUsername(), authRequestDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authHeaders.get("Authorization"));
        httpHeaders.add("Refresh-Token", authHeaders.get("Refresh-Token"));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(SuccessResponse.builder()
                        .message("User Logged in successfully.")
                        .statusCode(HttpStatus.OK.value())
                        .date(LocalDateTime.now())
                        .build());
    }
}
