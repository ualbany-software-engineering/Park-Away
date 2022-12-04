package com.parkway.repository;

import com.parkway.dto.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Page<Parking> findParkingByLocation_LocationId(Long locationId, Pageable pageable);

    Page<Parking> findParkingByCategoryEqualsIgnoreCase(String category, Pageable pageable);
}
