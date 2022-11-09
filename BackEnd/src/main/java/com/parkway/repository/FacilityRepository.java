package com.parkway.repository;

import com.parkway.dto.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
