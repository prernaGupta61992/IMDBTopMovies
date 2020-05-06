import React, { Component } from 'react'
import Typeaheadsearch from './Typeaheadsearch'


const Movie = (props)=> (
		<div>
			{props.currentMovie.movie_title}
		</div>

)

class Content extends Component {

	handleSearch(query) {
      	console.log("inside enter");
      	console.log(query);
        this.props.setInput(query);
	}

	render() {
		const searchResult = this.props.searchResult;
		const isSearch = this.props.autocomplete;
		const options = [];
		return (
		<div className="containerinput">
			<div className="search-input" 
			>
				<Typeaheadsearch searchAction={this.handleSearch.bind(this)}/>
			</div>
			{
				searchResult.map((user, i) =>
            	<Movie currentMovie={user}/>)
			}
			
		</div>
		)
	}
}



export default Content;