package com.imdbmovies.db.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class MovieShow
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name  = "show_id")
    private int showId;

    @Column(name  = "movie_id")
    private int movieId;

    @Column(name  = "hall_id")
    private int hallId;

    @Column(name  = "total_seats")
    private int totalSeats;

    @Column(name  = "seats_available")
    private int seatsAvailable;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name  = "start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name  = "end_time")
    private Date endTime;

    public int getShowId()
    {
        return showId;
    }

    public void setShowId(int showId)
    {
        this.showId = showId;
    }



    public int getMovieId()
    {
        return movieId;
    }

    public void setMovieId(int movieId)
    {
        this.movieId = movieId;
    }



    public int getHallId()
    {
        return hallId;
    }

    public void setHallId(int hallId)
    {
        this.hallId = hallId;
    }

    public int getTotalSeats()
    {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats)
    {
        this.totalSeats = totalSeats;
    }

    public int getSeatsAvailable()
    {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable)
    {
        this.seatsAvailable = seatsAvailable;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }


}
