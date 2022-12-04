package com.parkway.controller;

import com.parkway.dto.Location;
import com.parkway.model.PageResponse;
import com.parkway.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<Location>> getAllLocations(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(locationService.getAllLocations(page, size), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAllLocationsWithoutPagination() {
        return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Location> createNewLocation(@RequestBody Location location) {
        return new ResponseEntity<>(locationService.createNewLocation(location), HttpStatus.CREATED);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable long locationId) {
        return new ResponseEntity<>(locationService.getLocationById(locationId), HttpStatus.OK);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Location> updateLocation(@PathVariable long locationId,
                                                   @RequestBody Location location) {
        location.setLocationId(locationId);
        return new ResponseEntity<>(locationService.updateLocation(location), HttpStatus.OK);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity deleteLocation(@PathVariable long locationId) {
        locationService.deleteLocation(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
