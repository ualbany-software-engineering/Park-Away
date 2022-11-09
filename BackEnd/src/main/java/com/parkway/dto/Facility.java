package com.parkway.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id", nullable = false, unique = true)
    private Long facilityId;

    @Column(name = "name", nullable = false)
    private String facilityName;
}
