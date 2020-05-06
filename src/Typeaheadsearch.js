import { AsyncTypeahead } from 'react-bootstrap-typeahead';
import 'bootstrap/dist/css/bootstrap.css';
import 'react-bootstrap-typeahead/css/Typeahead.css';

import React, { Component } from 'react'


class Typeaheadsearch extends Component {
   constructor(props) {
     super(props);
    this.state = {
      isLoading : false,
      options : []
    }
    this.ref = React.createRef();
   }
  

  searchQuery = async(query)=> {
    try {
      this.setState({isLoading : true});
      let response = await fetch('http://localhost:8080/movie/search', {
        method: 'POST',
      headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
      },
        body: JSON.stringify({
          query: query,
          size:"10",
          search : true
        }),
      });
      const output = await response.json();
      const options = output.map(movie => ({
        movie_title : movie.movie_title
      }));

      this.setState({options : options, isLoading : false});
      console.log(output);
      
    } catch (error) {
      console.error(error);
    }
  }

  handleChange(selectedOptions) {
    //call the parent searchMovie Function and get the movie details
    this.ref.current.clear();
    this.props.searchAction(selectedOptions[0].movie_title);
  }
  
  render() {
    return (
    <AsyncTypeahead
      id="async-example"
      ref={this.ref}
      isLoading={this.state.isLoading}
      highlightOnlyResult="true"
      minLength={3}
      labelKey="movie_title"
      onSearch={this.searchQuery.bind(this)}
      options={this.state.options}
      placeholder="Search the movie..."
      onChange={this.handleChange.bind(this)}
      renderMenuItemChildren={(option, props) => (
        <div>
          <span>{option.movie_title}</span>
        </div>
      )}
    />
  );

  }
  
}

export default Typeaheadsearch;