package com.parkway.controller;

import com.parkway.dto.Facility;
import com.parkway.model.PageResponse;
import com.parkway.service.FacilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/facility")
public class FacilityController {
    private FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<Facility>> getAllFacilities(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(facilityService.getAllFacilities(page, size), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Facility>> getAllFacilitiesWithoutPagination() {
        return new ResponseEntity<>(facilityService.getAllFacilities(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Facility> createNewFacility(@RequestBody Facility facility) {
        return new ResponseEntity<>(facilityService.createNewFacility(facility), HttpStatus.CREATED);
    }

    @GetMapping("/{facilityId}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable long facilityId) {
        return new ResponseEntity<>(facilityService.getFacilityById(facilityId), HttpStatus.OK);
    }

    @PutMapping("/{facilityId}")
    public ResponseEntity<Facility> updateFacility(@PathVariable long facilityId, @Valid @RequestBody Facility facility) {
        facility.setFacilityId(facilityId);
        return new ResponseEntity<>(facilityService.updateFacility(facility), HttpStatus.OK);
    }

    @DeleteMapping("/{facilityId}")
    public ResponseEntity deleteFacility(@PathVariable long facilityId) {
        facilityService.deleteFacility(facilityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
