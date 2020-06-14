package com.imdbmovies.request;

import java.util.List;

public class BookingRequestParams
{
    private int userid;
    private int totalSeats;
    private int showid;
    private int hallId;
    private List<String> seatNumber;

    public List<String> getSeatNumber()
    {
        return seatNumber;
    }
    public void setSeatNumber(List<String> seatNumber)
    {
        this.seatNumber = seatNumber;
    }
    public int getHallId()
    {
        return hallId;
    }
    public void setHallId(int hallId)
    {
        this.hallId = hallId;
    }
    public int getUserid()
    {
        return userid;
    }
    public void setUserid(int userid)
    {
        this.userid = userid;
    }
    public int getTotalSeats()
    {
        return totalSeats;
    }
    public void setTotalSeats(int totalSeats)
    {
        this.totalSeats = totalSeats;
    }
    public int getShowid()
    {
        return showid;
    }
    public void setShowid(int showid)
    {
        this.showid = showid;
    }


}
