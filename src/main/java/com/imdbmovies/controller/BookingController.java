package com.imdbmovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.imdbmovies.request.BookingRequestParams;
import com.imdbmovies.service.BookingService;

@RestController
public class BookingController
{
    private BookingService bookingService;

    @Autowired
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/movie/booking")
    public Object createBooking(@RequestBody final BookingRequestParams params) {
        return bookingService.createBooking(params);
    }


}
