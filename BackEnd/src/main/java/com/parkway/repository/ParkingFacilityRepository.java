package com.parkway.repository;

import com.parkway.dto.ParkingFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingFacilityRepository extends JpaRepository<ParkingFacility, Long> {
    List<ParkingFacility> findParkingFacilitiesByParking_ParkingId(Long parkingId);
}
