package com.parkway.model;

import lombok.Data;

import java.util.Calendar;

@Data
public class BookingRequest {
    private Long parkingId;
    private Calendar startTime;
    private Calendar endTime;
}
