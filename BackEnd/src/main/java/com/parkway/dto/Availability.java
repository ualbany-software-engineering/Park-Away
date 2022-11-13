package com.parkway.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false, unique = true)
    private Long availabilityId;

    @Column(name = "hour", nullable = false)
    private int hour;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "availability", nullable = false)
    private int availability;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parking_id", referencedColumnName = "parking_id")
    private Parking parking;

}

