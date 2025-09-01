package com.mt_analytics.mt_analytics.dto.request;

import com.mt_analytics.mt_analytics.entity.Authentication;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GeneralUserDto {
    private long id;
    private Authentication authentication;
    private String firstName;
    private String middleName;
    private String lastName;
    private String addressLineOne;
    private String addressLineTwo;
    private String addressLineThree;
    private String addressLineCity;
    private String addressLineCountry;
    private String postalCode;
    private boolean deleted;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
}
