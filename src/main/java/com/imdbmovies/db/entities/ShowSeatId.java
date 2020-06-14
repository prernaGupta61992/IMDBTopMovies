package com.imdbmovies.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ShowSeatId implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -9182261642887887332L;

    @Column(name  = "show_id")
    protected int showId;

    @Column(name  = "seat_number")
    protected String seatNumber;

    public ShowSeatId() {

    }

    public ShowSeatId(int showId, String seatNumber) {
        this.showId = showId;
        this.seatNumber = seatNumber;
    }

    public int getShowId()
    {
        return showId;
    }

    public void setShowId(int showId)
    {
        this.showId = showId;
    }

    public String getSeatNumber()
    {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber)
    {
        this.seatNumber = seatNumber;
    }



}
