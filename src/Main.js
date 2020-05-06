import React, { Component } from 'react'
import Sidebar from './Sidebar'
import Content from './Content'

class Main extends Component {

	constructor(props) {
		super(props);
		this.setQuery = this.setQuery.bind(this);
		this.state = {
			query : ' ',
			searchPage : '',
			isSearching : '',
			autocomplete: '',
	 		error : '',
	 		movieList : [],
	 		genresList : []
		}
	}

	findGenres = async() => {
		try {
		  	let response = await fetch('http://localhost:8080/movie/aggregate/genres', {
			    method: 'POST',
				headers: {
				    Accept: 'application/json',
				    'Content-Type': 'application/json',
				},
		  		body: JSON.stringify({
				    aggField: "genres.analyzed",
				    aggName : "count_distinct"
		  		}),
		    });
		    const output = await response.json();
		    console.log(output);
		    this.setState({genresList : output.aggResult});
	  } catch (error) {
	    console.error(error);
	  }
	}

	searchQuery = async()=> {
	  try {
	  	let response = await fetch('http://localhost:8080/movie/search', {
		    method: 'POST',
			headers: {
			    Accept: 'application/json',
			    'Content-Type': 'application/json',
			},
	  		body: JSON.stringify({
			    query: this.state.query,
			    size:"10",
	            search : false,
	            searchType : this.state.searchType
	  		}),
	    });
	    const output = await response.json();
	    console.log(output);
	    output.map(movie=>{this.setState(prevState => ({movieList : [...prevState.movieList, movie]}))});
	  } catch (error) {
	    console.error(error);
	  }
	}

	setQuery = (input) => {
		this.setState({ query: input, searchType: "movie", movieList: []}, function () {
             console.log(this.state.query);
             this.searchQuery();
        });
	}

	searchGenreMovies = (genre) => {
		this.setState({ query: genre,  searchType: "genres", movieList: []}, function () {
             console.log(this.state.query);
             this.searchQuery();
        });
	}

	render() {
		this.findGenres();
		return(
			<div>
				<Sidebar genres = {this.state.genresList} setGenre={(genre) => this.searchGenreMovies(genre)}/>
        		<Content setInput={(input) => this.setQuery(input)} searchResult = {this.state.movieList}/>
			</div>
			
		)
	}
}

export default Main;