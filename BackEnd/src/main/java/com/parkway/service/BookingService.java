package com.parkway.service;

import com.parkway.constants.Constants;
import com.parkway.dto.Booking;
import com.parkway.dto.Parking;
import com.parkway.dto.User;
import com.parkway.exception.BookingNotAvailableException;
import com.parkway.model.BookingRequest;
import com.parkway.model.BookingStatus;
import com.parkway.model.PageResponse;
import com.parkway.repository.BookingRepository;
import com.parkway.security.service.UserDetailsImpl;
import com.parkway.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.Principal;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private ParkingService parkingService;
    private UserService userService;

    private AvailabilityService availabilityService;

    private MailService mailService;

    public BookingService(BookingRepository bookingRepository,
                          MailService mailService,
                          ParkingService parkingService,
                          UserService userService,
                          AvailabilityService availabilityService) {
        this.bookingRepository = bookingRepository;
        this.mailService = mailService;
        this.parkingService = parkingService;
        this.userService = userService;
        this.availabilityService = availabilityService;
    }

    public PageResponse<Booking> listMyBookings(Principal principal, int page, int size) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Long userId = userDetails.getUserId();
        Pageable paging = PageRequest.of(page, size);
        Page<Booking> bookingPage = bookingRepository.findBookingByUser_UserId(userId, paging);
        bookingPage.get().forEach(booking -> booking.setMapLink(Constants.MAP_HOST +
                StringUtils.replace(booking.getParking().getParkingName(), " ", "+")));
        return Util.createPageResponse(bookingPage);
    }

    @Transactional(rollbackFor = Exception.class)
    public Booking createBooking(Principal principal, BookingRequest bookingRequest) {
        if (!availabilityService.bookingAvailable(bookingRequest)) {
            throw new BookingNotAvailableException("No availability for given timeslot");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Long userId = userDetails.getUserId();
        User user = userService.getUserById(userId);
        Parking parking = parkingService.getParkingById(bookingRequest.getParkingId());
        Booking booking = new Booking();
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());
        booking.setBookingStatus(BookingStatus.BOOKED);
        booking.setUser(user);
        booking.setParking(parking);
        Booking result = bookingRepository.save(booking);
         mailService.sendBookingMail(booking);
        availabilityService.updateAvailability(booking);
        return result;
    }
}
