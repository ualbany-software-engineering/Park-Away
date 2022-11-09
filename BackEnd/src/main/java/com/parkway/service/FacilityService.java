package com.parkway.service;

import com.parkway.dto.Facility;
import com.parkway.exception.ResourceNotFoundException;
import com.parkway.model.PageResponse;
import com.parkway.repository.FacilityRepository;
import com.parkway.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    private FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public Facility createNewFacility(Facility facility) {
        return facilityRepository.save(facility);
    }

    public Facility getFacilityById(long facilityId) {
        Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
        if (optionalFacility.isPresent()) {
            return optionalFacility.get();
        }
        throw new ResourceNotFoundException(facilityId + " not found");
    }

    public Facility updateFacility(Facility facility) {
        return facilityRepository.save(facility);
    }

    public void deleteFacility(long facilityId) {
        facilityRepository.deleteById(facilityId);
    }

    public PageResponse<Facility> getAllFacilities(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Facility> facilityPage = facilityRepository.findAll(paging);
        return Util.createPageResponse(facilityPage);
    }

    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }
}
