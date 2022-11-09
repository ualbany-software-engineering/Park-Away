package com.parkway.service;

import com.parkway.dto.Location;
import com.parkway.exception.ResourceNotFoundException;
import com.parkway.model.PageResponse;
import com.parkway.repository.LocationRepository;
import com.parkway.repository.ParkingFacilityRepository;
import com.parkway.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private LocationRepository locationRepository;
    private FacilityService facilityService;

    private ParkingFacilityRepository locationFacilityRepository;

    public LocationService(LocationRepository locationRepository,
                           FacilityService facilityService,
                           ParkingFacilityRepository locationFacilityRepository) {
        this.locationRepository = locationRepository;
        this.facilityService = facilityService;
        this.locationFacilityRepository = locationFacilityRepository;
    }

    public Location createNewLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location getLocationById(long locationId) {
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        if (optionalLocation.isPresent()) {
            return optionalLocation.get();
        }
        throw new ResourceNotFoundException(locationId + " not found");
    }

    public Location updateLocation(Location location) {
        Location tempLocation = getLocationById(location.getLocationId());
        BeanUtils.copyProperties(location, tempLocation, Util.getNullPropertyNames(location));
        return locationRepository.save(tempLocation);
    }

    public void deleteLocation(long locationId) {
        locationRepository.deleteById(locationId);
    }


    public PageResponse<Location> getAllLocations(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Location> locationPage = locationRepository.findAll(paging);
        return Util.createPageResponse(locationPage);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
