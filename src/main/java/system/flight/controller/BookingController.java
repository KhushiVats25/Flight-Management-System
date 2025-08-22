package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.BookingRequestDTO;
import system.flight.dto.BookingResponseDTO;
import system.flight.dto.BookingUpdateDTO;
import system.flight.dto.ApiResponseDTO;
import system.flight.enums.BookingStatus;
import system.flight.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> createBooking(@RequestBody BookingRequestDTO dto) {
        BookingResponseDTO createdBooking = bookingService.createBooking(dto);
        ApiResponseDTO<BookingResponseDTO> response = new ApiResponseDTO<>(
                201,
                "Booking created successfully",
                createdBooking
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BookingResponseDTO>>> getAllBookings() {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "All bookings retrieved successfully", bookings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> getBookingById(@PathVariable int id) {
        BookingResponseDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking details retrieved successfully", booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> updateBooking(@PathVariable int id, @RequestBody BookingUpdateDTO dto) {
        if (dto.getBookingStatus() == null) {
            throw new IllegalArgumentException("Booking status is required.");
        }

        BookingStatus status;
        try {
            status = BookingStatus.valueOf(dto.getBookingStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Use CONFIRMED or CANCELLED.");
        }

        if (status == BookingStatus.CONFIRMED) {
            if (dto.getSeatName() == null || dto.getSeatName().isEmpty()) {
                throw new IllegalArgumentException("Seat name is required when booking is confirmed.");
            }
        }

        BookingResponseDTO updatedBooking = bookingService.updateBooking(id, dto);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking updated successfully", updatedBooking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking deleted successfully", null));
    }
}