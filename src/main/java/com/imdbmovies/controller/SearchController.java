package com.imdbmovies.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.request.SearchRequestParams;
import com.imdbmovies.service.SearchService;

@Controller
public class SearchController {
	
	
	private SearchService searchService;
	
	@Autowired
	public SearchController(SearchService service) {
		this.searchService = service;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/movie/search")
	public ResponseEntity searchDocuments(@RequestBody SearchRequestParams document) throws IOException {
		return 
	            new ResponseEntity(searchService.fetchAutoCompletedMovies(document), HttpStatus.OK);
	}
}
