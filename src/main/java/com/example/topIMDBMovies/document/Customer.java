package com.example.topIMDBMovies.document;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Customer {
		
	    private String name;
		
	    private String organizer;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOrganizer() {
			return organizer;
		}

		public void setOrganizer(String organizer) {
			this.organizer = organizer;
		}
	    
	    
	    
}
