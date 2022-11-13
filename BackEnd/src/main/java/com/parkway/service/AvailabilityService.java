package com.parkway.service;

import com.parkway.dto.Availability;
import com.parkway.dto.Booking;
import com.parkway.dto.Parking;
import com.parkway.model.BookingRequest;
import com.parkway.model.SearchRequest;
import com.parkway.repository.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

@Service
public class AvailabilityService {
    private AvailabilityRepository availabilityRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public void updateAvailability(Booking booking) {
        Calendar startTime = booking.getStartTime();
        Calendar endTime = booking.getEndTime();
        Calendar temp = startTime;
        while (endTime.compareTo(temp) > 0) {
            LocalDate date = getDate(temp);
            int hour = temp.get(Calendar.HOUR_OF_DAY);
            Availability availability = availabilityRepository.getAvailabilityByDateAndHourEquals(date, hour);
            if (availability != null && availability.getAvailability() > 0) {
                availability.setAvailability(availability.getAvailability() - 1);
                availabilityRepository.save(availability);
            } else {
                availabilityRepository.save(createAvailabilityBean(date, hour, booking.getParking()));
            }
            temp.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    public int getAvailabilityForSearch(SearchRequest request, int parkingCapacity) {
        Calendar startTime = request.getStartTime();
        Calendar endTime = request.getEndTime();
        Calendar temp = Calendar.getInstance();
        temp.setTimeZone(TimeZone.getTimeZone("UTC"));
        temp.setTime(startTime.getTime());
        int maxAvailability = parkingCapacity;
        while (endTime.compareTo(temp) > 0) {
            LocalDate date = getDate(temp);
            int hour = temp.get(Calendar.HOUR_OF_DAY);
            if (availabilityRepository.findAvailabilityByDateEqualsAndHourEquals(date, hour) != null) {
                int realAvailability = availabilityRepository.findAvailabilityByDateEqualsAndHourEquals(date, hour).getAvailability();
                if (realAvailability < maxAvailability) {
                    maxAvailability = realAvailability;
                }
            }
            temp.add(Calendar.HOUR_OF_DAY, 1);
        }
        return maxAvailability;
    }

    private LocalDate getDate(Calendar temp) {
        return LocalDate.ofInstant(temp.toInstant(), ZoneId.of("UTC"));
    }

    private Availability createAvailabilityBean(LocalDate date, int hour, Parking parking) {
        Availability availability = new Availability();
        availability.setDate(date);
        availability.setHour(hour);
        availability.setParking(parking);
        availability.setAvailability(parking.getCapacity() - 1);
        return availability;
    }

    public boolean bookingAvailable(BookingRequest bookingRequest) {
        Calendar startTime = bookingRequest.getStartTime();
        Calendar endTime = bookingRequest.getEndTime();
        Calendar temp = Calendar.getInstance();
        temp.setTimeZone(TimeZone.getTimeZone("UTC"));
        temp.setTime(startTime.getTime());
        while (endTime.compareTo(temp) > 0) {
            LocalDate date = getDate(temp);
            if (!isAvailable(date, temp.get(Calendar.HOUR_OF_DAY))) {
                return false;
            }
            temp.add(Calendar.HOUR, 1);
        }
        return true;
    }

    private boolean isAvailable(LocalDate date, int hour) {
        if (availabilityRepository.findAvailabilityByDateEqualsAndHourEquals(date, hour) != null &&
                availabilityRepository.findAvailabilityByDateEqualsAndHourEquals(date, hour).getAvailability() == 0) {
            return false;
        }
        return true;
    }
}
