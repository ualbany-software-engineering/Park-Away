package com.parkway.service;

import com.parkway.dto.Facility;
import com.parkway.dto.Parking;
import com.parkway.dto.ParkingFacility;
import com.parkway.exception.ResourceNotFoundException;
import com.parkway.model.PageResponse;
import com.parkway.model.SearchRequest;
import com.parkway.repository.ParkingFacilityRepository;
import com.parkway.repository.ParkingRepository;
import com.parkway.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    private ParkingRepository parkingRepository;
    private ParkingFacilityRepository parkingFacilityRepository;

    private AvailabilityService availabilityService;

    private FacilityService facilityService;
    private DistanceCalculatorService distanceCalculatorService;

    public ParkingService(ParkingRepository parkingRepository,
                          ParkingFacilityRepository parkingFacilityRepository,
                          FacilityService facilityService,
                          AvailabilityService availabilityService,
                          DistanceCalculatorService distanceCalculatorService) {
        this.parkingRepository = parkingRepository;
        this.parkingFacilityRepository = parkingFacilityRepository;
        this.facilityService = facilityService;
        this.availabilityService = availabilityService;
        this.distanceCalculatorService = distanceCalculatorService;
    }

    public PageResponse<Parking> getAllParking(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Parking> parkingPage = parkingRepository.findAll(paging);
        return Util.createPageResponse(parkingPage);
    }

    public List<Parking> getAllParking() {
        return parkingRepository.findAll();
    }

    public Parking createNewParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    public Parking getParkingById(long parkingId) {
        Optional<Parking> optionalParking = parkingRepository.findById(parkingId);
        if (optionalParking.isPresent()) {
            return optionalParking.get();
        }
        throw new ResourceNotFoundException(parkingId + " not found");
    }

    public Parking updateParking(Parking parking) {
        Parking tempParking = getParkingById(parking.getParkingId());
        BeanUtils.copyProperties(parking, tempParking, Util.getNullPropertyNames(parking));
        return parkingRepository.save(tempParking);
    }

    public void deleteParking(long parkingId) {
        parkingRepository.deleteById(parkingId);
    }

    public ParkingFacility addFacility(ParkingFacility parkingFacility) {
        List<ParkingFacility> parkingFacilities = parkingFacilityRepository.findParkingFacilitiesByParking_ParkingId(
                parkingFacility.getParking().getParkingId());
        List<ParkingFacility> filteredList = parkingFacilities.stream().filter(parFac ->
                        parFac.getFacility().getFacilityId().equals(parkingFacility.getFacility().getFacilityId()))
                .collect(Collectors.toList());
        if (filteredList.size() > 0) {
            throw new IllegalArgumentException("Facility already added");
        }
        parkingFacility.setFacility(facilityService.getFacilityById(parkingFacility.getFacility().getFacilityId()));
        parkingFacility.setParking(getParkingById(parkingFacility.getParking().getParkingId()));
        return parkingFacilityRepository.save(parkingFacility);
    }

    public void removeFacility(long parkingId, long facilityId) {
        List<ParkingFacility> parkingFacilities = parkingFacilityRepository.findParkingFacilitiesByParking_ParkingId(parkingId);
        List<ParkingFacility> filteredList = parkingFacilities.stream().filter(parFac ->
                        parFac.getFacility().getFacilityId().equals(facilityId))
                .collect(Collectors.toList());
        filteredList.forEach(parkFac -> parkingFacilityRepository.deleteById(parkFac.getId()));
    }

    public Set<Facility> getFacilitiesByParkingId(long parkingId) {
        return getParkingById(parkingId).getFacilities();
    }

    public PageResponse<Parking> searchParkingByLocation(int page, int size, SearchRequest searchRequest) {
        Pageable paging = PageRequest.of(page, size);
        Page<Parking> parkingPage = parkingRepository.findParkingByLocation_LocationId(searchRequest.getLocationId(), paging);
        parkingPage.get().forEach(parking -> {
            parking.setAvailability(availabilityService.getAvailabilityForSearch(searchRequest, parking.getCapacity()));
        });
        if (searchRequest.getLatitude() != null && searchRequest.getLongitude() != null) {
            parkingPage.get().forEach(parking -> {
                String res = distanceCalculatorService.getDistanceInMiles(searchRequest.getLatitude(), searchRequest.getLongitude(),
                        parking.getLatitude(), parking.getLongitude());
                if (res != "NA") {
                    parking.setDistance(Double.valueOf(res.substring(0, res.length() - 3)));
                    parking.setUnit(res.substring(res.length() - 3));
                } else {
                    parking.setDistance(-1D);
                }
            });
        }

        return Util.createPageResponseForSearch(parkingPage,
                parkingPage.get().sorted(Comparator.comparing(Parking::getDistance)).collect(Collectors.toList()));
    }

    public PageResponse<Parking> getParkingByAirport(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Parking> parkingPage = parkingRepository.findParkingByCategoryEqualsIgnoreCase("Airport", paging);
        return Util.createPageResponse(parkingPage);
    }

    public PageResponse<Parking> getParkingByCruise(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Parking> parkingPage = parkingRepository.findParkingByCategoryEqualsIgnoreCase("Cruise", paging);
        return Util.createPageResponse(parkingPage);
    }

    public PageResponse<Parking> getParkingByEvent(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Parking> parkingPage = parkingRepository.findParkingByCategoryEqualsIgnoreCase("Event", paging);
        return Util.createPageResponse(parkingPage);
    }
}
