package com.imdbmovies.document;

import lombok.Data;

@Data
public class MovieDocument {
    private String color;
    private String director_name;
    private int num_critic_for_reviews;
    private int duration;
    private int director_facebook_likes;
    private int actor_3_facebook_likes;
    private String actor_2_name;
    private int actor_1_facebook_likes;
    private int gross;
    private String genres;
    private String movie_title;
    private String actor_1_name;
    private int num_voted_users;
    private int cast_total_facebook_likes;
    private String actor_3_name;
    private int facenumber_in_poster;
    private String plot_keywords;
    private String movie_imdb_link;
    private int num_user_for_reviews;
    private String language;
    private String country;
    private String content_rating;
    private int budget;
    private int title_year;
    private int actor_2_facebook_likes;
    private float imdb_score;
    private float aspect_ratio;
    private int movie_facebook_likes;
    private String highlightedString;


    public String getHighlightedString() {
        return highlightedString;
    }

    public void setHighlightedString(final String highlightedString) {
        this.highlightedString = highlightedString;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(final String director_name) {
        this.director_name = director_name;
    }

    public int getNum_critic_for_reviews() {
        return num_critic_for_reviews;
    }

    public void setNum_critic_for_reviews(final int num_critic_for_reviews) {
        this.num_critic_for_reviews = num_critic_for_reviews;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getDirector_facebook_likes() {
        return director_facebook_likes;
    }

    public void setDirector_facebook_likes(final int director_facebook_likes) {
        this.director_facebook_likes = director_facebook_likes;
    }

    public int getActor_3_facebook_likes() {
        return actor_3_facebook_likes;
    }

    public void setActor_3_facebook_likes(final int actor_3_facebook_likes) {
        this.actor_3_facebook_likes = actor_3_facebook_likes;
    }

    public String getActor_2_name() {
        return actor_2_name;
    }

    public void setActor_2_name(final String actor_2_name) {
        this.actor_2_name = actor_2_name;
    }

    public int getActor_1_facebook_likes() {
        return actor_1_facebook_likes;
    }

    public void setActor_1_facebook_likes(final int actor_1_facebook_likes) {
        this.actor_1_facebook_likes = actor_1_facebook_likes;
    }

    public int getGross() {
        return gross;
    }

    public void setGross(final int gross) {
        this.gross = gross;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(final String genres) {
        this.genres = genres;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(final String movie_title) {
        this.movie_title = movie_title;
    }

    public String getActor_1_name() {
        return actor_1_name;
    }

    public void setActor_1_name(final String actor_1_name) {
        this.actor_1_name = actor_1_name;
    }

    public int getNum_voted_users() {
        return num_voted_users;
    }

    public void setNum_voted_users(final int num_voted_users) {
        this.num_voted_users = num_voted_users;
    }

    public int getCast_total_facebook_likes() {
        return cast_total_facebook_likes;
    }

    public void setCast_total_facebook_likes(final int cast_total_facebook_likes) {
        this.cast_total_facebook_likes = cast_total_facebook_likes;
    }

    public String getActor_3_name() {
        return actor_3_name;
    }

    public void setActor_3_name(final String actor_3_name) {
        this.actor_3_name = actor_3_name;
    }

    public int getFacenumber_in_poster() {
        return facenumber_in_poster;
    }

    public void setFacenumber_in_poster(final int facenumber_in_poster) {
        this.facenumber_in_poster = facenumber_in_poster;
    }

    public String getPlot_keywords() {
        return plot_keywords;
    }

    public void setPlot_keywords(final String plot_keywords) {
        this.plot_keywords = plot_keywords;
    }

    public String getMovie_imdb_link() {
        return movie_imdb_link;
    }

    public void setMovie_imdb_link(final String movie_imdb_link) {
        this.movie_imdb_link = movie_imdb_link;
    }

    public int getNum_user_for_reviews() {
        return num_user_for_reviews;
    }

    public void setNum_user_for_reviews(final int num_user_for_reviews) {
        this.num_user_for_reviews = num_user_for_reviews;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getContent_rating() {
        return content_rating;
    }

    public void setContent_rating(final String content_rating) {
        this.content_rating = content_rating;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getTitle_year() {
        return title_year;
    }

    public void setTitle_year(final int title_year) {
        this.title_year = title_year;
    }

    public int getActor_2_facebook_likes() {
        return actor_2_facebook_likes;
    }

    public void setActor_2_facebook_likes(final int actor_2_facebook_likes) {
        this.actor_2_facebook_likes = actor_2_facebook_likes;
    }

    public float getImdb_score() {
        return imdb_score;
    }

    public void setImdb_score(final float imdb_score) {
        this.imdb_score = imdb_score;
    }

    public float getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(final float aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public int getMovie_facebook_likes() {
        return movie_facebook_likes;
    }

    public void setMovie_facebook_likes(final int movie_facebook_likes) {
        this.movie_facebook_likes = movie_facebook_likes;
    }


}
