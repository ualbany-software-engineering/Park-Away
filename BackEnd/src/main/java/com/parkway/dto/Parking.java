package com.parkway.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "parking")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_id", nullable = false, unique = true)
    private Long parkingId;

    @Column(name = "name", nullable = false)
    private String parkingName;

    @Column(name = "hourly_price", nullable = false)
    private int hourlyPrice;

    @Column(name = "image_link", nullable = false)
    private String imageLink;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "parking_facility",
            joinColumns = {@JoinColumn(name = "parking_id", referencedColumnName = "parking_id")},
            inverseJoinColumns = {@JoinColumn(name = "facility_id", referencedColumnName = "facility_id")}
    )
    private Set<Facility> facilities = new HashSet<>();

    @Transient
    private int availability;

    @Transient
    private Double distance;

    @Transient
    private String unit;

    @Column(name = "category")
    private String category;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

}
