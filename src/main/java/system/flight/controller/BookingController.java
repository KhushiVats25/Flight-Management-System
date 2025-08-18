package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.BookingRequestDTO;
import system.flight.dto.BookingUpdateDTO;
import system.flight.entities.Booking;
import system.flight.enums.BookingStatus;
import system.flight.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable int id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequestDTO dto) {
        return bookingService.createBooking(dto.getUserId(), dto.getAircraftId(), dto.getTotalAmount(),dto.getSeatName());
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable int id, @RequestBody BookingUpdateDTO dto) {
        if (dto.getBookingStatus() == null) {
            throw new IllegalArgumentException("Booking status is required.");
        }

        BookingStatus status;
        try {
            status = BookingStatus.valueOf(dto.getBookingStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Use CONFIRMED or CANCELLED.");
        }

        // If status is CONFIRMED, seat name must be provided
        if (status == BookingStatus.CONFIRMED) {
            if (dto.getSeatName() == null || dto.getSeatName().isEmpty()) {
                throw new IllegalArgumentException("Seat name is required when booking is confirmed.");
            }
        }

        return bookingService.updateBooking(id, dto);
    }




    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
    }
}