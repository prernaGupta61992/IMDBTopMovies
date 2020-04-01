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
	
	@GetMapping("/movieIndex")
    public boolean createMovieIndex() throws Exception {

        return service.createIndex();
    }
	
	@PostMapping("/customer")
    public ResponseEntity createProfile(
        @RequestBody Customer document) throws Exception {

        return 
            new ResponseEntity(service.createProfileDocument(document), HttpStatus.CREATED);
    }
	 

}
