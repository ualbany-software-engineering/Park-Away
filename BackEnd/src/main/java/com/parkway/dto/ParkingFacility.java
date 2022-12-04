package com.parkway.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "parking_facility")
public class ParkingFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parking_id", referencedColumnName = "parking_id")
    private Parking parking;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "facility_id", referencedColumnName = "facility_id")
    private Facility facility;
}
