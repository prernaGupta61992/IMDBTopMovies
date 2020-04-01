package com.example.topIMDBMovies.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.topIMDBMovies.document.Customer;
import com.example.topIMDBMovies.service.IndexService;



@Controller
public class IndexController {
	
	
	private IndexService service;
	
	@Autowired
	public IndexController(IndexService service) {
		this.service = service;
	}
	
	@PostMapping("/movie")
    public ResponseEntity createMovieDocuments(
        @RequestBody Customer document) throws Exception {

        return 
            new ResponseEntity(service.createMovieDocuments(document), HttpStatus.CREATED);
    }
	 

}
