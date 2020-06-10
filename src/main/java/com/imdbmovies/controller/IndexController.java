package com.imdbmovies.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.service.IndexService;

@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private final IndexService indexService;

    public IndexController(final IndexService service) {
        this.indexService = service;
    }


    @PostConstruct
    public void init() {
        try {
            indexService.createBulkMovieDocuments();
        } catch (final Exception e) {
            log.error( " Error occured while creating indexing " +e.getMessage());
        }
    }

    @PostMapping("/movie")
    public ResponseEntity createMovieDocument(
            @RequestBody final MovieDocument document) throws Exception {
        final ResponseEntity responseEntity = new ResponseEntity(indexService.createMovieDocuments(document), HttpStatus.CREATED);
        return responseEntity;
    }
}
