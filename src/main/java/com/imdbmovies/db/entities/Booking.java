package com.imdbmovies.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.imdbmovies.enums.BookingStatus;

@Entity
public class Booking
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;


    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus status;

    @Column(name  = "show_id")
    private int showId;

    @Column(name  = "user_id")
    private int userId;

    @Column(name  = "total_seats")
    private int totalSeats;


    public int getBookingId()
    {
        return bookingId;
    }

    public void setBookingId(int bookingId)
    {
        this.bookingId = bookingId;
    }

    public BookingStatus getStatus()
    {
        return status;
    }

    public void setStatus(BookingStatus status)
    {
        this.status = status;
    }

    public int getShowId()
    {
        return showId;
    }

    public void setShowId(int showId)
    {
        this.showId = showId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getTotalSeats()
    {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats)
    {
        this.totalSeats = totalSeats;
    }


}
