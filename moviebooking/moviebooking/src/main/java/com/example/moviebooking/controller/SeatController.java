package com.example.moviebooking.controller;

import com.example.moviebooking.model.Booking;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.service.BookingService;
import com.example.moviebooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookingService bookingService; // Inject BookingService

    @PostMapping
    public Seat addSeat(@RequestBody Seat seat) {
        return seatService.addSeat(seat);
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getSeats();
    }

    @PutMapping("/update/{seatId}")
    public Seat updateSeat(
            @PathVariable String seatId,
            @RequestParam String seatNumber,
            @RequestParam double newPrice
    ) {
        Seat seat = seatService.getSeatById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setSeatNumber(seatNumber);
        seat.setPrice(newPrice);
        return seatService.addSeat(seat);
    }

    @DeleteMapping("/delete/{seatId}")
    public void deleteSeat(@PathVariable String seatId) {
        seatService.deleteSeat(seatId);
    }

    @GetMapping("/by-movie-theatre")
    public List<Seat> getSeatsByMovieAndTheatre(@RequestParam String movieId, @RequestParam String theatreId) {
        return seatService.getSeatsByMovieAndTheatre(movieId, theatreId);
    }

    // New booking endpoint
    @PostMapping("/book")
    public ResponseEntity<String> bookSeats(@RequestBody BookingRequest bookingRequest) {
        boolean success = seatService.bookSeats(bookingRequest);
        if (success) {
            return ResponseEntity.ok("Seats booked successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error booking seats");
        }
    }

}
