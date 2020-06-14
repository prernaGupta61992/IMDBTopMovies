package com.imdbmovies.repository;

import org.springframework.data.repository.CrudRepository;

import com.imdbmovies.db.entities.ShowSeatId;
import com.imdbmovies.db.entities.ShowSeats;

public interface ShowSeatRepository extends CrudRepository<ShowSeats, ShowSeatId>
{

}
