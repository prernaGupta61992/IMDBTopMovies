package com.imdbmovies.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.imdbmovies.db.entities.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer>
{

}
