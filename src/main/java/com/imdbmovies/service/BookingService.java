package com.imdbmovies.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imdbmovies.db.entities.Booking;
import com.imdbmovies.db.entities.ShowSeatId;
import com.imdbmovies.db.entities.ShowSeats;
import com.imdbmovies.enums.BookingStatus;
import com.imdbmovies.enums.SeatStatus;
import com.imdbmovies.repository.BookingRepository;
import com.imdbmovies.repository.ShowSeatRepository;
import com.imdbmovies.request.BookingRequestParams;

@Service
public class BookingService
{
    private static final Logger log      = LoggerFactory.getLogger(BookingService.class);
    private BookingRepository bookingRepository;
    private ShowSeatRepository showSeatRepository;


    @Autowired
    BookingService(BookingRepository bookingRepository,ShowSeatRepository showSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public String createBooking(BookingRequestParams params) {

        boolean isBookingPossible = validateSeats(params.getShowid(), params.getTotalSeats(), params.getHallId(), params.getSeatNumber(), params.getUserid());
        if(isBookingPossible) {
            log.info("assigned  seats to user id " + params.getUserid() + " totol seats " + params.getTotalSeats());
            Booking booking = new Booking();
            booking.setShowId(params.getShowid());
            booking.setTotalSeats(params.getTotalSeats());
            booking.setUserId(params.getUserid());
            booking.setStatus(BookingStatus.PENDING_PAYEMENT);
            Booking bookingresponse = bookingRepository.save(booking);
            return Integer.toString(bookingresponse.getBookingId());
        } else {
            log.info("not possible to assign seats to  " + params.getUserid());
            return "Not psooble";
        }

    }

    private boolean validateSeats(int showId , int totalseats, int hallId, List<String> seatList, int userId) {
        boolean bookedSuccesfully = false;
        List<ShowSeats> availableSeats = new ArrayList<ShowSeats>();
        for(String seatNumber  : seatList) {
            ShowSeats  showSeat = showSeatRepository.findById(new ShowSeatId(showId, seatNumber)).get();
            if(showSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                availableSeats.add(showSeat);
            }
        }
        if(availableSeats.size() == totalseats) {
            synchronized(availableSeats) {
                for(ShowSeats showSeat : availableSeats) {
                    ShowSeats fectchedSeatStatus = showSeatRepository.findById(showSeat.getShowSeatId()).get();
                    if(!fectchedSeatStatus.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                        bookedSuccesfully=false;
                        break;
                    }
                    bookedSuccesfully=true;
                    showSeat.setSeatStatus(SeatStatus.BOOKED);
                    showSeat.setUserId(userId);
                    showSeatRepository.save(showSeat);
                }
            }
        }
        return bookedSuccesfully;
    }
}
