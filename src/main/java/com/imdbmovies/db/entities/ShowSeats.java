package com.imdbmovies.db.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.imdbmovies.enums.SeatStatus;

@Entity
public class ShowSeats
{

    @EmbeddedId
    private ShowSeatId showSeatId;

    @Column(name  = "seat_status")
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Column(name  = "user_id")
    private Integer userId;



    public ShowSeatId getShowSeatId()
    {
        return showSeatId;
    }

    public void setShowSeatId(ShowSeatId showSeatId)
    {
        this.showSeatId = showSeatId;
    }

    public SeatStatus getSeatStatus()
    {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus)
    {
        this.seatStatus = seatStatus;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }



}
