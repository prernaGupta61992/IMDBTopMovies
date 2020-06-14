package com.imdbmovies.repository;

import org.springframework.data.repository.CrudRepository;

import com.imdbmovies.db.entities.MovieShow;

public interface MovieShowRepository  extends CrudRepository<MovieShow, Integer>
{

}
