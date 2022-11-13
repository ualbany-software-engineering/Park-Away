package com.parkway.controller;

import com.parkway.dto.Facility;
import com.parkway.dto.Parking;
import com.parkway.dto.ParkingFacility;
import com.parkway.model.PageResponse;
import com.parkway.model.SearchRequest;
import com.parkway.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {
    private ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<Parking>> getAllParking(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(parkingService.getAllParking(page, size), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parking>> getAllParking() {
        return new ResponseEntity<>(parkingService.getAllParking(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Parking> createNewParking(@RequestBody Parking parking) {
        return new ResponseEntity<>(parkingService.createNewParking(parking), HttpStatus.CREATED);
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<Parking> getParkingById(@PathVariable long parkingId) {
        return new ResponseEntity<>(parkingService.getParkingById(parkingId), HttpStatus.OK);
    }

    @PutMapping("/{parkingId}")
    public ResponseEntity<Parking> updateParking(@PathVariable long parkingId,
                                                 @RequestBody Parking parking) {
        parking.setParkingId(parkingId);
        return new ResponseEntity<>(parkingService.updateParking(parking), HttpStatus.OK);
    }

    @DeleteMapping("/{parkingId}")
    public ResponseEntity deleteParking(@PathVariable long parkingId) {
        parkingService.deleteParking(parkingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/{parkingId}/facility")
    public ResponseEntity<ParkingFacility> addFacility(@PathVariable long parkingId,
                                                       @RequestBody ParkingFacility parkingFacility) {
        if (parkingFacility.getParking() == null || parkingId != parkingFacility.getParking().getParkingId()) {
            throw new IllegalArgumentException("Invalid Parking details passed ");
        }
        return new ResponseEntity<>(parkingService.addFacility(parkingFacility), HttpStatus.CREATED);
    }

    @DeleteMapping("/{parkingId}/facility/{facilityId}")
    public ResponseEntity removeFacility(@PathVariable long parkingId,
                                         @PathVariable long facilityId) {
        parkingService.removeFacility(parkingId, facilityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{parkingId}/facility")
    public ResponseEntity<Set<Facility>> getFacilitiesByParking(@PathVariable long parkingId) {
        return new ResponseEntity<>(parkingService.getFacilitiesByParkingId(parkingId), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<Parking>> searchParkingByLocation(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int size,
                                                                         @RequestBody SearchRequest searchRequest) {
        return new ResponseEntity<>(parkingService.searchParkingByLocation(page, size, searchRequest), HttpStatus.CREATED);
    }

    @GetMapping("/airport")
    public ResponseEntity<PageResponse<Parking>> getParkingByAirport(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(parkingService.getParkingByAirport(page, size), HttpStatus.OK);
    }

    @GetMapping("/cruise")
    public ResponseEntity<PageResponse<Parking>> getParkingByCruise(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "100") int size) {
        return new ResponseEntity<>(parkingService.getParkingByCruise(page, size), HttpStatus.OK);
    }

    @GetMapping("/event")
    public ResponseEntity<PageResponse<Parking>> getParkingByEvent(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "100") int size) {
        return new ResponseEntity<>(parkingService.getParkingByEvent(page, size), HttpStatus.OK);
    }

}
