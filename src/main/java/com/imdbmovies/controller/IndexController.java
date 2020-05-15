package com.imdbmovies.controller;

import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.service.IndexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class IndexController {
    private final IndexService indexService;

    public IndexController(IndexService service) {
        this.indexService = service;
    }

    @PostMapping("/movie")
    public ResponseEntity createMovieDocument(
        @RequestBody MovieDocument document) throws Exception {
        ResponseEntity responseEntity = new ResponseEntity(indexService.createMovieDocuments(document), HttpStatus.CREATED);
        return responseEntity;
    }


    @PostMapping("/movieBulk")
    public ResponseEntity createBulkMovieDocuments() throws Exception {
        ResponseEntity responseEntity = new ResponseEntity(indexService.createBulkMovieDocuments(), HttpStatus.CREATED);
        return responseEntity;
    }


}
