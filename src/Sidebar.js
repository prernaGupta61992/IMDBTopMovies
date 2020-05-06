import React, { Component } from 'react'

class Sidebar extends Component {

	searchGenreMovies(e) {
		e.preventDefault();
		this.props.setGenre(e.target.name);
	}
	render() {
		const listGenres = this.props.genres.map((genre) =>
			<li>
				<a href="#" name = {genre.key} onClick={this.searchGenreMovies.bind(this)}>{genre.key}</a>
			</li>
		);
		return (
			<div className="vertical-menu">
				<div className="container">
					<section className="section-container">
						<header>Home</header>
					</section>
					<section className="section-container">
						<header>
							<a href="#">Top Rated movies</a>
						</header>
					</section>
					<section className="section-container">
						<header>Genre</header>
						<ul>
							{listGenres}
						</ul>
					</section>
				</div>
			</div>
		)
	}
}

export default Sidebar;