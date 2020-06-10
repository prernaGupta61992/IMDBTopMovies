package com.imdbmovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.imdbmovies.request.AggregationRequestParams;
import com.imdbmovies.service.AggregationService;


@Controller
public class AggregationController {

  @Autowired
  private AggregationService aggregationService;

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/movie/aggregate/genres")
  public ResponseEntity getGenresAggregatedData(@RequestBody final AggregationRequestParams params) throws Exception {
    return new ResponseEntity(aggregationService.getGenresAggregatedData(params), HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/movie/aggregate/actors")
  public ResponseEntity getActorsAggregatedData() throws Exception {
    return new ResponseEntity(aggregationService.getActorsAggregatedData(), HttpStatus.OK);
  }

}
