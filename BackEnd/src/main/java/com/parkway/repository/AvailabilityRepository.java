package com.parkway.repository;

import com.parkway.dto.Availability;
import com.parkway.model.AvailabilityOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAvailabilitiesByDateEquals(Date date);

    AvailabilityOnly findAvailabilityByDateEqualsAndHourEquals(LocalDate date, int hour);

    Availability getAvailabilityByDateAndHourEquals(LocalDate date, int hour);
}
