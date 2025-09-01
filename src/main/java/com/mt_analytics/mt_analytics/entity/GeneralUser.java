package com.mt_analytics.mt_analytics.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class GeneralUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String addressLineOne;
    private String addressLineTwo;
    private String addressLineThree;
    private String addressLineCity;
    private String addressLineCountry;
    private String postalCode;
    @OneToOne
    private Authentication authentication;
    private boolean deleted;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @CreationTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
}
