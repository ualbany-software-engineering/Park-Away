package com.parkway.dto;

import com.parkway.model.BookingStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false, unique = true)
    private Long bookingId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parking_id", referencedColumnName = "parking_id")
    private Parking parking;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", updatable = false)
    private Calendar startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = false)
    private Calendar endTime;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "booking_status", columnDefinition = "ENUM('BOOKED', 'FAILED')")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Calendar createdAt;

    @Transient
    private String mapLink;

}
