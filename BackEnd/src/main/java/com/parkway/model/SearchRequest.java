package com.parkway.model;

import lombok.Data;

import java.util.Calendar;

@Data
public class SearchRequest {
    private Calendar startTime;
    private Calendar endTime;
    private Long locationId;
}
