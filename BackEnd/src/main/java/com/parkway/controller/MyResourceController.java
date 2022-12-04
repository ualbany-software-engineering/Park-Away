package com.parkway.controller;

import com.parkway.dto.Booking;
import com.parkway.dto.User;
import com.parkway.model.BookingRequest;
import com.parkway.model.PageResponse;
import com.parkway.model.PasswordChangeRequest;
import com.parkway.service.BookingService;
import com.parkway.service.LocationService;
import com.parkway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class MyResourceController {

    private LocationService locationService;

    private BookingService bookingService;

    private UserService userService;

    public MyResourceController(LocationService locationService, BookingService bookingService, UserService userService) {
        this.locationService = locationService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping("/booking")
    public ResponseEntity createBooking(Principal principal, @RequestBody BookingRequest bookingRequest) {
        return new ResponseEntity<>(bookingService.createBooking(principal, bookingRequest), HttpStatus.CREATED);
    }

    @GetMapping("/booking")
    public ResponseEntity<PageResponse<Booking>> listMyBookings(Principal principal,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(bookingService.listMyBookings(principal, page, size), HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getMyProfile(Principal principal) {
        return new ResponseEntity<>(userService.getProfile(principal), HttpStatus.OK);
    }

    @PostMapping("/profile/changepassword")
    public ResponseEntity updateMyPassword(Principal principal,
                                           @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return new ResponseEntity<>(userService.updatePassword(principal, passwordChangeRequest), HttpStatus.OK);
    }
}
